package org.pjj.gulimall.product.dao;

import org.apache.ibatis.annotations.Select;
import org.pjj.gulimall.product.entity.CategoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品三级分类
 * 
 * @author PengJiaJun
 * @email 2779824672@qq.com
 * @date 2022-05-07 14:08:21
 */
@Mapper
public interface CategoryDao extends BaseMapper<CategoryEntity> {

    @Select("select p1.cat_id as 'cat_id', p1.parent_cid as 'parent_cid', p2.parent_cid as 'parent_parent_cid' from `pms_category` p1\n" +
            "inner join pms_category p2 on p1.parent_cid = p2.cat_id\n" +
            "where p1.cat_id = #{id}")
    LinkedHashMap<String, Long> findCatelogPathById(Long id);
	
}
