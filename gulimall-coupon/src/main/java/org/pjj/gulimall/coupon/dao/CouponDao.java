package org.pjj.gulimall.coupon.dao;

import org.pjj.gulimall.coupon.entity.CouponEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 优惠券信息
 * 
 * @author PengJiaJun
 * @email 2779824672@qq.com
 * @date 2022-05-07 16:51:58
 */
@Mapper
public interface CouponDao extends BaseMapper<CouponEntity> {
	
}
