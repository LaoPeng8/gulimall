package org.pjj.gulimall.ware.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.pjj.gulimall.ware.entity.PurchaseDetailEntity;
import org.pjj.gulimall.ware.entity.PurchaseEntity;

/**
 * 采购信息
 * 
 * @author PengJiaJun
 * @email 2779824672@qq.com
 * @date 2022-05-07 20:47:36
 */
@Mapper
public interface PurchaseDetailDao extends BaseMapper<PurchaseDetailEntity> {
	
}
