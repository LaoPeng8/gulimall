package org.pjj.gulimall.product.service.impl;

import org.pjj.common.exception.GulimallException;
import org.pjj.gulimall.product.entity.SpuInfoEntity;
import org.pjj.gulimall.product.entity.vo.Skus;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.pjj.common.utils.PageUtils;
import org.pjj.common.utils.Query;

import org.pjj.gulimall.product.dao.SkuInfoDao;
import org.pjj.gulimall.product.entity.SkuInfoEntity;
import org.pjj.gulimall.product.service.SkuInfoService;
import org.springframework.util.StringUtils;


@Service("skuInfoService")
public class SkuInfoServiceImpl extends ServiceImpl<SkuInfoDao, SkuInfoEntity> implements SkuInfoService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SkuInfoEntity> page = this.page(
                new Query<SkuInfoEntity>().getPage(params),
                new QueryWrapper<SkuInfoEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryPageByCondition(Map<String, Object> params) {
        QueryWrapper<SkuInfoEntity> spuInfoEntityQueryWrapper = new QueryWrapper<>();

        String catelogId = (String) params.get("catelogId");
        if(!StringUtils.isEmpty(catelogId) && !catelogId.equals("0")) {
            spuInfoEntityQueryWrapper.eq("catelog_id", catelogId);
        }

        String brandId = (String) params.get("brandId");
        if(!StringUtils.isEmpty(brandId) && !brandId.equals("0")) {
            spuInfoEntityQueryWrapper.eq("brand_id", brandId);
        }

        String min = (String) params.get("min");
        String max = (String) params.get("max");
        if(!StringUtils.isEmpty(min) && !min.equals("0")) {
            spuInfoEntityQueryWrapper.ge("price", min);
        }
        if(!StringUtils.isEmpty(max) && !max.equals("0")) {
            spuInfoEntityQueryWrapper.le("price", max);
        }

        String key = (String) params.get("key");
        if(!StringUtils.isEmpty(key)) {
            spuInfoEntityQueryWrapper.and((wrapper) -> {
                wrapper.like("sku_id", key).or().like("sku_name", key);
            });
        }

        //该getPage中封装了 page对象分页current与limit, 还封装了排序sidx排序字段, order排序方式 desc/asc
        IPage<SkuInfoEntity> page = new Query<SkuInfoEntity>().getPage(params);
        this.page(page, spuInfoEntityQueryWrapper);

        return new PageUtils(page);
    }

}