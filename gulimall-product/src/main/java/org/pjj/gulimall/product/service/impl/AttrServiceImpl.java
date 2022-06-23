package org.pjj.gulimall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.pjj.common.constant.ProductConstant;
import org.pjj.gulimall.product.dao.AttrAttrgroupRelationDao;
import org.pjj.gulimall.product.dao.AttrGroupDao;
import org.pjj.gulimall.product.dao.CategoryDao;
import org.pjj.gulimall.product.entity.AttrAttrgroupRelationEntity;
import org.pjj.gulimall.product.entity.AttrGroupEntity;
import org.pjj.gulimall.product.entity.CategoryEntity;
import org.pjj.gulimall.product.entity.vo.AttrRespVo;
import org.pjj.gulimall.product.entity.vo.AttrVo;
import org.pjj.gulimall.product.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.pjj.common.utils.PageUtils;
import org.pjj.common.utils.Query;

import org.pjj.gulimall.product.dao.AttrDao;
import org.pjj.gulimall.product.entity.AttrEntity;
import org.pjj.gulimall.product.service.AttrService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;


@Service("attrService")
public class AttrServiceImpl extends ServiceImpl<AttrDao, AttrEntity> implements AttrService {

    @Autowired
    private AttrAttrgroupRelationDao attrAttrgroupRelationDao;

    @Autowired
    private AttrGroupDao attrGroupDao;

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private CategoryService categoryService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrEntity> page = this.page(
                new Query<AttrEntity>().getPage(params),
                new QueryWrapper<AttrEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * 插入 attr 表的同时 根据 attrVo 对象中的 attrGroupId, 在 attr 与 attr_group的关联表中插入关联数据
     * @param attr
     */
    @Transactional
    @Override
    public void saveAttr(AttrVo attr) {
        AttrEntity attrEntity = new AttrEntity();
        BeanUtils.copyProperties(attr, attrEntity);

        //插入 attr 表
        this.baseMapper.insert(attrEntity);

        // 只有当 attrtype == 1 是才向属性与属性分组表插入关联关系(因为 1 为基本属性, 0为销售属性, 销售属性没有属性分组, 所以不需要插入关联表)
        if(attrEntity.getAttrType() == ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode()) {
            AttrAttrgroupRelationEntity attrAttrgroupRelationEntity = new AttrAttrgroupRelationEntity();
            attrAttrgroupRelationEntity.setAttrId(attrEntity.getAttrId());//attrEntity中的attrId是mybatis自增后返回的, attr中是没有attrId的.
            attrAttrgroupRelationEntity.setAttrGroupId(attr.getAttrGroupId());
            //插入 attr_attrgroup_relation 表 (关联表)
            attrAttrgroupRelationDao.insert(attrAttrgroupRelationEntity);
        }

    }


    @Override
    public PageUtils queryBaseAttrPage(Long catelogId, Map<String, Object> params, String type) {
        QueryWrapper<AttrEntity> queryWrapper = new QueryWrapper<>();

        String key = (String) params.get("key");
        if(!StringUtils.isEmpty(key)) {
            queryWrapper.or((wrapper) -> {
                wrapper.eq("attr_id", key).or().like("attr_name", key);
            });
        }

        if(null != catelogId && catelogId != 0) {
            queryWrapper.eq("catelog_id", catelogId);
        }

        if(!"sale".equalsIgnoreCase(type)) {//如果传入的type不等于"sale"则都视为"base" (base 为基本属性 1, sale 为销售属性 0)
            type = "base";
        }
        queryWrapper.eq("attr_type", "base".equalsIgnoreCase(type) ? ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode() : ProductConstant.AttrEnum.ATTR_TYPE_SALE.getCode());
        IPage<AttrEntity> attrEntityIPage = this.baseMapper.selectPage(new Query<AttrEntity>().getPage(params), queryWrapper);


        PageUtils pageUtils = new PageUtils(attrEntityIPage);
        List<AttrEntity> records = attrEntityIPage.getRecords();//取出需要修改的记录

        List<AttrRespVo> result = records.stream() // 对records集合中的每一个元素 设置所属分组名称, 设置所属分类名称
                .map(attrEntity -> {
                    AttrRespVo attrRespVo = new AttrRespVo();
                    BeanUtils.copyProperties(attrEntity, attrRespVo);//copy attrEntity中的基本属性

                    //设置所属分组名称, 设置所属分类名称
                    AttrAttrgroupRelationEntity attrGroupId = attrAttrgroupRelationDao.selectOne(
                            // 根据 attr_id 从 attr_attrgroup_relation关联表中查出, 该 attr 属于哪个 attrgroup 的 id
                            new QueryWrapper<AttrAttrgroupRelationEntity>().select("attr_group_id").eq("attr_id", attrRespVo.getAttrId())
                    );

                    AttrGroupEntity attrGroupName = null;
                    if(attrGroupId != null) {
                        attrGroupName = attrGroupDao.selectOne(
                                // 根据 attrgroup_id 查询出该分组的名称
                                new QueryWrapper<AttrGroupEntity>().select("attr_group_name").eq("attr_group_id", attrGroupId.getAttrGroupId())
                        );
                    }else{
                        attrGroupName = new AttrGroupEntity();
                        attrGroupName.setAttrGroupName("（空）");
                    }

                    CategoryEntity categoryName = categoryDao.selectOne(
                            // 根据 catelogId 查询出分类名称
                            new QueryWrapper<CategoryEntity>().select("name").eq("cat_id", attrRespVo.getCatelogId())
                    );
                    if(null == categoryName) {
                        categoryName = new CategoryEntity();
                        categoryName.setName("（空）");
                    }

                    attrRespVo.setGroupName(attrGroupName.getAttrGroupName());//设置 所属分组名称
                    attrRespVo.setCatelogName(categoryName.getName());//设置 所属分类名称

                    return attrRespVo;
                }).collect(Collectors.toList());

        pageUtils.setList(result);//将最新的(修改后的集合)放入返回数据中

        return pageUtils;
    }

    @Override
    public AttrRespVo getAttrInfo(Long attrId) {
        //查询出 attr 基本信息
        AttrEntity attrEntity = this.baseMapper.selectOne(new QueryWrapper<AttrEntity>().eq("attr_id", attrId));

        AttrRespVo attrRespVo = new AttrRespVo();
        BeanUtils.copyProperties(attrEntity, attrRespVo);

        // 根据 分类id 查出 分类完整路径
        Long[] catelogPath = categoryService.findCatelogPath(attrRespVo.getCatelogId());
        attrRespVo.setCatelogPath(catelogPath);

        // 只有当 attrtype == 1 是才查询关联关系(因为 1 为基本属性, 0为销售属性, 销售属性没有属性分组, 所以不需要查询关联表)
        if(attrEntity.getAttrType() == ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode()) {
            // 根据 attr_id 从 attr_attrgroup_relation关联表中查出, 该 attr 属于哪个 attrgroup 的 id
            AttrAttrgroupRelationEntity attrGroupId = attrAttrgroupRelationDao.selectOne(
                    new QueryWrapper<AttrAttrgroupRelationEntity>().select("attr_group_id").eq("attr_id", attrRespVo.getAttrId())
            );
            if(null != attrGroupId) {
                attrRespVo.setAttrGroupId(attrGroupId.getAttrGroupId());
            }
        }

        return attrRespVo;
    }

    @Transactional
    @Override
    public void updateAttr(AttrVo attr) {

        // 修改基本信息
        AttrEntity attrEntity = new AttrEntity();
        BeanUtils.copyProperties(attr, attrEntity);
        this.updateById(attrEntity);

        // 只有当 attrtype == 1 是才修改关联关系(因为 1 为基本属性, 0为销售属性, 销售属性没有属性分组, 所以不需要修改关联表)
        if(attrEntity.getAttrType() == ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode()) {
            // 修改分组
            AttrAttrgroupRelationEntity attrAttrgroupRelationEntity = new AttrAttrgroupRelationEntity();
            attrAttrgroupRelationEntity.setAttrGroupId(attr.getAttrGroupId());
            attrAttrgroupRelationDao.update(
                    attrAttrgroupRelationEntity, new UpdateWrapper<AttrAttrgroupRelationEntity>().eq("attr_id", attrEntity.getAttrId())
            );
        }

    }

    /**
     * 根据分组id查找关联得所有 基本属性(attr_type 为 1 即是基本属性)
     * @param attrgroupId
     * @return
     */
    @Override
    public List<AttrEntity> getAttrRelation(Long attrgroupId) {
        // 根据分组id查询出所有属于该分组得 基本属性id
        List<AttrAttrgroupRelationEntity> entityList = attrAttrgroupRelationDao.selectList(
                new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_group_id", attrgroupId)
        );
        // 将实体类集合转为 基本属性id集合
        List<Long> attrIds = entityList.stream()
                .map((attr) -> {
                    return attr.getAttrId();
                }).collect(Collectors.toList());

        if(attrIds == null || attrIds.size() == 0) {
            return null;
        }
        // 查询出所有得 属性
        List<AttrEntity> attrEntities = this.baseMapper.selectBatchIds(attrIds);
        // 过滤出基本属性
        List<AttrEntity> collect = attrEntities.stream().filter((attr) -> {
            return attr.getAttrType().equals(ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode());
        }).collect(Collectors.toList());
        return collect;
    }

    /**
     * 获取当前属性分组没有关联得属性
     *
     * 1. 当前分组只能关联自己所属分类得属性
     * 2. 当前分组只能关联别得分组没有引用的属性
     *
     * @param attrgroupId
     * @param params
     * @return
     */
    @Override
    public PageUtils getNoRelationAttr(Long attrgroupId, Map<String, Object> params) {
        // 1. 当前分组只能关联自己所属分类得属性
        AttrGroupEntity attrGroupEntity = attrGroupDao.selectById(attrgroupId);
        Long catelogId = attrGroupEntity.getCatelogId();

        // 2. 当前分组只能关联别得分组没有引用的属性
        // 2.1 当前分类下的其他分组(包括自己 (如果不加入则 当前分组已经关联过的属性, 最后也会被查出来(正确的是只有没有被关联的属性才会被查出来)))
        List<AttrGroupEntity> otherGroupList = attrGroupDao.selectList(new QueryWrapper<AttrGroupEntity>().eq("catelog_id", catelogId));
        List<Long> otherGroupIds = otherGroupList.stream().map(AttrGroupEntity::getAttrGroupId).collect(Collectors.toList());


        // 2.2 这些分组关联的属性
        List<AttrAttrgroupRelationEntity> attrRelationList = attrAttrgroupRelationDao.selectList(new QueryWrapper<AttrAttrgroupRelationEntity>().in("attr_group_id", otherGroupIds));
        List<Long> removeIds = attrRelationList.stream().map(AttrAttrgroupRelationEntity::getAttrId).collect(Collectors.toList());

        // 2.3 从当前分类的所有属性中移除这些属性
        QueryWrapper<AttrEntity> queryWrapper = new QueryWrapper<AttrEntity>()
                .eq("catelog_id", catelogId)
                .eq("attr_type", ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode());
        if(removeIds != null && removeIds.size() > 0) {//避免空指针 (如果removeIds为空, 则说明其他分组没有关联属性, 也就不需要将 被关注的属性排除)
            queryWrapper.notIn("attr_id", removeIds);
        }
        // 模糊查询条件不为空, 则在wrapper中加入模糊查询条件
        String key = (String) params.get("key");
        if(!StringUtils.isEmpty(key)) {
            queryWrapper.and((wrapper) -> {
                wrapper.eq("attr_id", key).or().like("attr_name", key);
            });
        }

        IPage<AttrEntity> attrEntityIPage = this.baseMapper.selectPage(new Query<AttrEntity>().getPage(params), queryWrapper);

        return new PageUtils(attrEntityIPage);
    }

}