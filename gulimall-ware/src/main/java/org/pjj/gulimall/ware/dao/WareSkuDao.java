package org.pjj.gulimall.ware.dao;

import org.apache.ibatis.annotations.Param;
import org.pjj.gulimall.ware.entity.WareSkuEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品库存
 * 
 * @author PengJiaJun
 * @email 2779824672@qq.com
 * @date 2022-05-07 20:47:36
 */
@Mapper
public interface WareSkuDao extends BaseMapper<WareSkuEntity> {

    int addStock(@Param("skuId") Long skuId, @Param("wareId") Long wareId, @Param("skuNum") Integer skuNum);
}
