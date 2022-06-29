package org.pjj.gulimall.product.service.impl;

import org.pjj.common.to.SkuReductionTo;
import org.pjj.common.to.SpuBoundTo;
import org.pjj.common.utils.R;
import org.pjj.gulimall.product.dao.SpuInfoDescDao;
import org.pjj.gulimall.product.entity.*;
import org.pjj.gulimall.product.entity.vo.BaseAttrs;
import org.pjj.gulimall.product.entity.vo.Bounds;
import org.pjj.gulimall.product.entity.vo.Skus;
import org.pjj.gulimall.product.entity.vo.SpuSaveVo;
import org.pjj.gulimall.product.feign.CouponFeignService;
import org.pjj.gulimall.product.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.pjj.common.utils.PageUtils;
import org.pjj.common.utils.Query;

import org.pjj.gulimall.product.dao.SpuInfoDao;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;


@Service("spuInfoService")
public class SpuInfoServiceImpl extends ServiceImpl<SpuInfoDao, SpuInfoEntity> implements SpuInfoService {

    @Autowired
    private SpuInfoDescService spuInfoDescService;

    @Autowired
    private SpuImagesService spuImagesService;

    @Autowired
    private ProductAttrValueService productAttrValueService;

    @Autowired
    private SkuInfoService skuInfoService;

    @Autowired
    private SkuImagesService skuImagesService;

    @Autowired
    private SkuSaleAttrValueService skuSaleAttrValueService;

    @Autowired
    private CouponFeignService couponFeignService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SpuInfoEntity> page = this.page(
                new Query<SpuInfoEntity>().getPage(params),
                new QueryWrapper<SpuInfoEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * TODO 还有很多东西 高级部分再完善 如: 事务, Feign超时后怎么操作.
     * 新增加商品
     * @param spuSaveVo
     */
    @Transactional
    @Override
    public void saveSpuInfo(SpuSaveVo spuSaveVo) {

        //1. 保存spu基本信息 pms_spu_info
        SpuInfoEntity spuInfoEntity = new SpuInfoEntity();
        BeanUtils.copyProperties(spuSaveVo, spuInfoEntity);
        spuInfoEntity.setCatelogId(spuSaveVo.getCatelogId());//我不懂为什么为什么数据库是catalog_id 前端传的是catelog_id  其他表也是catelog_id 算了懒得该数据库表了万一出问题了了, 就是这样把
        spuInfoEntity.setCreateTime(new Date());
        spuInfoEntity.setUpdateTime(new Date());
        this.saveBaseSpuInfo(spuInfoEntity);

        //2. 保存spu描述图片 pms_spu_info_desc
        SpuInfoDescEntity spuInfoDescEntity = new SpuInfoDescEntity();
        spuInfoDescEntity.setSpuId(spuInfoEntity.getId());
        List<String> descript = spuSaveVo.getDescript();
        spuInfoDescEntity.setDecript(String.join(",", descript));
        spuInfoDescService.saveSpuInfoDesc(spuInfoDescEntity);

        //3. 保存sup图片集 pms_spu_images
        List<String> images = spuSaveVo.getImages();
        spuImagesService.saveImages(spuInfoEntity.getId(), images);

        //4. 保存spu的规格参数 pms_product_attr_value
        List<BaseAttrs> baseAttrs = spuSaveVo.getBaseAttrs();
        productAttrValueService.saveBaseAttrs(spuInfoEntity.getId(), baseAttrs);

        //5. 保存spu的积分信息 gulimall-sms库中的 sms_spu_bounds
        Bounds bounds = spuSaveVo.getBounds();
        SpuBoundTo spuBoundTo = new SpuBoundTo();
        spuBoundTo.setSpuId(spuInfoEntity.getId());
        spuBoundTo.setBuyBounds(bounds.getBuyBounds());
        spuBoundTo.setGrowBounds(bounds.getGrowBounds());
        R r = couponFeignService.save(spuBoundTo);
        if(r.getCode() != 0) {
            log.error("远程保存spu积分信息失败");
        }

        //6. 保存当前spu对应的所有sku信息
        List<Skus> skus = spuSaveVo.getSkus();
        if(skus != null && skus.size() > 0) {
            skus.forEach((item) -> {
                //6.1 sku的基本信息 pms_sku_info
                SkuInfoEntity skuInfoEntity = new SkuInfoEntity();
                BeanUtils.copyProperties(item, skuInfoEntity);
                skuInfoEntity.setSpuId(spuInfoEntity.getId());
                skuInfoEntity.setCatalogId(spuInfoEntity.getCatelogId());
                skuInfoEntity.setBrandId(spuInfoEntity.getBrandId());
                skuInfoEntity.setSaleCount(0L);
                item.getImages().forEach((image) -> {
                    if(image.getDefaultImg() == 1) {
                        skuInfoEntity.setSkuDefaultImg(image.getImgUrl());
                    }
                });
                skuInfoService.save(skuInfoEntity);

                //6.2 sku的图片信息 pms_sku_images
                List<SkuImagesEntity> skuImages = item.getImages().stream().map((image) -> {
                    if (!StringUtils.isEmpty(image.getImgUrl())) {
                        SkuImagesEntity skuImagesEntity = new SkuImagesEntity();
                        skuImagesEntity.setSkuId(skuInfoEntity.getSkuId());
                        skuImagesEntity.setImgUrl(image.getImgUrl());
                        skuImagesEntity.setDefaultImg(image.getDefaultImg());

                        return skuImagesEntity;
                    }
                    return null;
                }).collect(Collectors.toList());
                skuImages.removeAll(Collections.singleton(null));//删除skuImages中所有的null值
                skuImagesService.saveBatch(skuImages);

                //6.3 sku的销售属性 pms_sku_sale_attr_value
                List<SkuSaleAttrValueEntity> skuSaleAttrValueEntities = item.getAttr().stream().map((attr) -> {
                    SkuSaleAttrValueEntity skuSaleAttrValueEntity = new SkuSaleAttrValueEntity();
                    skuSaleAttrValueEntity.setSkuId(skuInfoEntity.getSkuId());
                    skuSaleAttrValueEntity.setAttrId(attr.getAttrId());
                    skuSaleAttrValueEntity.setAttrName(attr.getAttrName());
                    skuSaleAttrValueEntity.setAttrValue(attr.getAttrValue());

                    return skuSaleAttrValueEntity;
                }).collect(Collectors.toList());
                skuSaleAttrValueService.saveBatch(skuSaleAttrValueEntities);

                //6.4 sku的优惠、满减等信息 gulimall-sms库中的 sms_sku_ladder & sms_sku_full_reduction & sms_member_price
                SkuReductionTo skuReductionTo = new SkuReductionTo();
                BeanUtils.copyProperties(item, skuReductionTo);
                skuReductionTo.setSkuId(skuInfoEntity.getSkuId());
                //最少满1件打折 或 最少满1元才满减, 才会将优惠信息保存至数据库否则就是无效数据不保存
                if(skuReductionTo.getFullCount() > 0 || skuReductionTo.getFullPrice().compareTo(new BigDecimal("0")) == 1){
                    R r1 = couponFeignService.saveSkuReduction(skuReductionTo);
                    if(r1.getCode() != 0) {
                        log.error("远程保存sku优惠信息失败");
                    }
                }

            });
        }

    }

    @Override
    public void saveBaseSpuInfo(SpuInfoEntity spuInfoEntity) {
        this.baseMapper.insert(spuInfoEntity);
    }

}