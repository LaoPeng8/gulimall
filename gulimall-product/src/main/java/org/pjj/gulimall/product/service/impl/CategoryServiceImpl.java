package org.pjj.gulimall.product.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.pjj.common.utils.PageUtils;
import org.pjj.common.utils.Query;

import org.pjj.gulimall.product.dao.CategoryDao;
import org.pjj.gulimall.product.entity.CategoryEntity;
import org.pjj.gulimall.product.service.CategoryService;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

    @Autowired
    private CategoryDao categoryDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<CategoryEntity>()
        );

        return new PageUtils(page);
    }

    // 查出所有分类以及子分类, 以树形结构封装返回 (三级分类)
    @Override
    public List<CategoryEntity> listWithTree() {
        //1. 查询出所有分类
        List<CategoryEntity> allCategory = baseMapper.selectList(null);

        //2. 组装成父子的树形结构
        //2.1 找出所有一级分类
        ArrayList<CategoryEntity> level1Menus = allCategory.stream()
                .filter((categoryEntity) -> categoryEntity.getCatLevel() == 1)
                .map((menu) -> {
                    // 找出当前分类 的子分类以及子子分类, 放入当前分类的children中
//                    menu.setChildren(getChildren(menu, allCategory));//使用递归
                    menu.setChildren(getChildrenByFor(menu, allCategory)); //使用for
                    return menu;
                })
                .sorted((menu1, menu2) -> Integer.compare((menu1.getSort()==null?0:menu1.getSort()), (menu2.getSort()==null?0:menu2.getSort())))
                .collect(Collectors.toCollection(ArrayList::new));

        return level1Menus;
    }

    /**
     * 递归查找 某一分类 的 子分类 (感觉递归的好像是懂的, 但是好像又有一点点不懂, 于是在下面写了一个使用for循环完成的 getChildrenByFor())
     * @param root 当前分类(层级)
     * @param all 全部分类数据
     * @return
     */
    private List<CategoryEntity> getChildren(CategoryEntity root, List<CategoryEntity> all) {

        List<CategoryEntity> children = all.stream()
                .filter(currentCategory -> {
                    // 从全部分类数据中 过滤出 root菜单的子菜单
                    return currentCategory.getParentCid().equals(root.getCatId());
                })
                .map(currentCategory -> {
                    // 为root菜单中的每一个子菜单, 继续递归查找子子菜单, 并放入子菜单的children中
                    currentCategory.setChildren(getChildren(currentCategory, all)); //使用递归
                    return currentCategory;
                })
                .sorted((menu1, menu2) -> Integer.compare((menu1.getSort()==null?0:menu1.getSort()), (menu2.getSort()==null?0:menu2.getSort())))
                .collect(Collectors.toList());

        return children;
    }

    /**
     * 查找 某 一级分类 的 二级分类并返回二级分类集合 (二级分类中包含三级分类)
     * @param root 一级分类
     * @param all 全部分类数据
     * @return
     */
    private List<CategoryEntity> getChildrenByFor(CategoryEntity root, List<CategoryEntity> all) {

        ArrayList<CategoryEntity> entityTwoList = all.stream()
                .filter((entity) -> entity.getParentCid().equals(root.getCatId())) //过滤出一级分类的二级分类
                .map(entityTwo -> {
                    // 为二级分类 设置 三级分类, 并将设置过三级分类的二级分类返回
                    List<CategoryEntity> entityThree = all.stream()
                            .filter((allEntity) -> allEntity.getParentCid().equals(entityTwo.getCatId())) //过滤出二级分类的三级分类
                            .sorted((menu1, menu2) -> Integer.compare((menu1.getSort() == null ? 0 : menu1.getSort()), (menu2.getSort() == null ? 0 : menu2.getSort())))
                            .collect(Collectors.toList());
                    entityTwo.setChildren(entityThree);//给每一个二级分类 设置 三级分类

                    return entityTwo;
                })
                .sorted((menu1, menu2) -> Integer.compare((menu1.getSort()==null?0:menu1.getSort()), (menu2.getSort()==null?0:menu2.getSort())))
                .collect(Collectors.toCollection(ArrayList::new));

        return entityTwoList;
    }

    /**
     * 批量删除分类 (传入 分类id 集合)
     * @param asList
     */
    @Override
    public void removeMenuByIds(List<Long> asList) {

//        TODO 1. 检查当前删除的菜单, 是否被别的地方引用

        // 逻辑删除
        baseMapper.deleteBatchIds(asList);
    }

    /**
     * 根据传入分类id 查询出 分类完整路径
     * [父id, 子id, 孙id]
     * @param catelogId
     * @return
     */
    @Override
    public Long[] findCatelogPath(Long catelogId) {
        List<Long> pathList = new ArrayList<>();
        LinkedHashMap<String, Long> map = categoryDao.findCatelogPathById(catelogId);
        map.forEach((k, v) -> {
            if(v != 0) {
                pathList.add(v);
            }
        });

        Collections.reverse(pathList);//反转list

        Long[] paths = new Long[pathList.size()];
        for (int i = 0; i < pathList.size(); i++) {
            paths[i] = pathList.get(i);
        }

        return paths;
    }


}