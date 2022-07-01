package org.pjj.gulimall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.pjj.common.exception.GulimallException;
import org.pjj.gulimall.product.entity.AttrEntity;
import org.pjj.gulimall.product.entity.vo.BaseAttrs;
import org.pjj.gulimall.product.service.AttrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.pjj.common.utils.PageUtils;
import org.pjj.common.utils.Query;

import org.pjj.gulimall.product.dao.ProductAttrValueDao;
import org.pjj.gulimall.product.entity.ProductAttrValueEntity;
import org.pjj.gulimall.product.service.ProductAttrValueService;
import org.springframework.transaction.annotation.Transactional;


@Service("productAttrValueService")
public class ProductAttrValueServiceImpl extends ServiceImpl<ProductAttrValueDao, ProductAttrValueEntity> implements ProductAttrValueService {

    @Autowired
    private AttrService attrService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ProductAttrValueEntity> page = this.page(
                new Query<ProductAttrValueEntity>().getPage(params),
                new QueryWrapper<ProductAttrValueEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void saveBaseAttrs(Long id, List<BaseAttrs> baseAttrs) {
        if(baseAttrs == null || baseAttrs.size() == 0) {
            throw new GulimallException();
        }else {
            List<ProductAttrValueEntity> collect = baseAttrs.stream().map((item) -> {
                ProductAttrValueEntity productAttrValueEntity = new ProductAttrValueEntity();
                productAttrValueEntity.setSpuId(id);
                productAttrValueEntity.setAttrId(item.getAttrId());
                productAttrValueEntity.setQuickShow(item.getShowDesc());
                productAttrValueEntity.setAttrValue(item.getAttrValues());
                AttrEntity attrEntity = attrService.getById(item.getAttrId());
                productAttrValueEntity.setAttrName(attrEntity.getAttrName());
                return productAttrValueEntity;
            }).collect(Collectors.toList());

            this.saveBatch(collect);
        }
    }

    @Override
    public List<ProductAttrValueEntity> baseAttrlistforspu(Long spuId) {
        List<ProductAttrValueEntity> entities = this.baseMapper.selectList(new QueryWrapper<ProductAttrValueEntity>().eq("spu_id", spuId));
        return entities;
    }

    @Transactional
    @Override
    public void updateSpuAttr(Long spuId, List<ProductAttrValueEntity> entities) {
        // 由于修改提交得值, 可能存在之前没有得值. 也有可能原本存在得值, 修改后没有了
        // 所有不能直接update, 正确做法为 先删除掉之前得全部值, 再添加入需要修改得值(插入)

        this.baseMapper.delete(new UpdateWrapper<ProductAttrValueEntity>().eq("spu_id", spuId));

        List<ProductAttrValueEntity> collect = entities.stream().map((entity) -> {
            entity.setSpuId(spuId);
            return entity;
        }).collect(Collectors.toList());
        this.saveBatch(collect);
    }

}