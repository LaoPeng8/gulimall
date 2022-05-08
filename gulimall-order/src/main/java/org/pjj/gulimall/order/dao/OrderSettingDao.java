package org.pjj.gulimall.order.dao;

import org.pjj.gulimall.order.entity.OrderSettingEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单配置信息
 * 
 * @author PengJiaJun
 * @email 2779824672@qq.com
 * @date 2022-05-07 20:25:52
 */
@Mapper
public interface OrderSettingDao extends BaseMapper<OrderSettingEntity> {
	
}
