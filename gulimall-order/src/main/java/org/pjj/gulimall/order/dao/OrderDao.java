package org.pjj.gulimall.order.dao;

import org.pjj.gulimall.order.entity.OrderEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单
 * 
 * @author PengJiaJun
 * @email 2779824672@qq.com
 * @date 2022-05-07 20:25:52
 */
@Mapper
public interface OrderDao extends BaseMapper<OrderEntity> {
	
}
