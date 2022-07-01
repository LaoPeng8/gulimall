package org.pjj.gulimall.ware.service.impl;

import org.pjj.common.utils.R;
import org.pjj.gulimall.ware.feign.ProductFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.pjj.common.utils.PageUtils;
import org.pjj.common.utils.Query;

import org.pjj.gulimall.ware.dao.WareSkuDao;
import org.pjj.gulimall.ware.entity.WareSkuEntity;
import org.pjj.gulimall.ware.service.WareSkuService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;


@Service("wareSkuService")
public class WareSkuServiceImpl extends ServiceImpl<WareSkuDao, WareSkuEntity> implements WareSkuService {

    @Autowired
    private WareSkuDao wareSkuDao;

    @Autowired
    private ProductFeignService productFeignService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<WareSkuEntity> wareSkuEntityQueryWrapper = new QueryWrapper<>();

        String wareId = (String) params.get("wareId");
        if(!StringUtils.isEmpty(wareId) && !"0".equals(wareId)) {
            wareSkuEntityQueryWrapper.eq("ware_id", wareId);
        }

        String skuId = (String) params.get("skuId");
        if(!StringUtils.isEmpty(skuId) && !"0".equals(skuId)) {
            wareSkuEntityQueryWrapper.eq("sku_id", skuId);
        }

        IPage<WareSkuEntity> page = this.page(
                new Query<WareSkuEntity>().getPage(params),
                wareSkuEntityQueryWrapper
        );

        return new PageUtils(page);
    }

    @Transactional
    @Override
    public void addStock(Long skuId, Long wareId, Integer skuNum) {


        //判断 如果该商品还没有库存记录, 那么即是新增操作
        List<WareSkuEntity> wareSkuEntities = wareSkuDao.selectList(new QueryWrapper<WareSkuEntity>().eq("sku_id", skuId).eq("ware_id", wareId));
        if(wareSkuEntities == null || wareSkuEntities.size() == 0) {
            WareSkuEntity wareSkuEntity = new WareSkuEntity();
            wareSkuEntity.setSkuId(skuId);
            wareSkuEntity.setWareId(wareId);
            wareSkuEntity.setStock(skuNum);
            wareSkuEntity.setStockLocked(0);

            // 远程查询 sku_name, 如果失败整个事务无需回滚, 自己catch掉异常(不抛出异常, 则@Transactional就不会回滚)
            // TODO 还可以用什么办法让异常出现以后不回滚? 高级部分老师再讲解 (个人感觉需要使用 消息队列)
            try {
                R info = productFeignService.info(skuId);
                if(info.getCode() == 0) {
                    Map<String, Object> skuInfo = (Map<String, Object>) info.get("skuInfo");
                    String skuName = (String) skuInfo.get("skuName");
                    wareSkuEntity.setSkuName(skuName);
                }
            } catch (Exception e) {

            }

            wareSkuDao.insert(wareSkuEntity);
        }else {
            //如果该商品已经有了库存记录, 那么即是修改操作
            wareSkuDao.addStock(skuId, wareId, skuNum);
        }

    }

}