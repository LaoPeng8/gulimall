package org.pjj.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.pjj.common.utils.PageUtils;
import org.pjj.gulimall.product.entity.SkuInfoEntity;
import org.pjj.gulimall.product.entity.vo.Skus;

import java.util.List;
import java.util.Map;

/**
 * sku信息
 *
 * @author PengJiaJun
 * @email 2779824672@qq.com
 * @date 2022-05-07 14:08:21
 */
public interface SkuInfoService extends IService<SkuInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils queryPageByCondition(Map<String, Object> params);
}

