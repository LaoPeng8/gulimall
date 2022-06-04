package org.pjj.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.pjj.common.utils.PageUtils;
import org.pjj.gulimall.product.entity.CategoryEntity;

import java.util.List;
import java.util.Map;

/**
 * 商品三级分类
 *
 * @author PengJiaJun
 * @email 2779824672@qq.com
 * @date 2022-05-07 14:08:21
 */
public interface CategoryService extends IService<CategoryEntity> {

    PageUtils queryPage(Map<String, Object> params);

    // 查出所有分类以及子分类, 以树形结构封装返回 (三级分类)
    List<CategoryEntity> listWithTree();

    // 批量删除分类 (传入 分类id 集合)
    void removeMenuByIds(List<Long> asList);

    // 根据传入分类id 查询出 分类完整路径 Long [父id, 子id, 孙id]
    Long[] findCatelogPath(Long catelogId);
}

