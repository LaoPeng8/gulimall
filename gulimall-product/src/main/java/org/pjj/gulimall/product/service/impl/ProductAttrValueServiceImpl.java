package org.pjj.gulimall.product.service.impl;

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

}