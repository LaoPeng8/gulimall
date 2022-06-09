package org.pjj.gulimall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
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

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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

        AttrAttrgroupRelationEntity attrAttrgroupRelationEntity = new AttrAttrgroupRelationEntity();
        attrAttrgroupRelationEntity.setAttrId(attrEntity.getAttrId());//attrEntity中的attrId是mybatis自增后返回的, attr中是没有attrId的.
        attrAttrgroupRelationEntity.setAttrGroupId(attr.getAttrGroupId());
        //插入 attr_attrgroup_relation 表 (关联表)
        attrAttrgroupRelationDao.insert(attrAttrgroupRelationEntity);
    }


    @Override
    public PageUtils queryBaseAttrPage(Long catelogId, Map<String, Object> params) {
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

        // 根据 attr_id 从 attr_attrgroup_relation关联表中查出, 该 attr 属于哪个 attrgroup 的 id
        AttrAttrgroupRelationEntity attrGroupId = attrAttrgroupRelationDao.selectOne(
                new QueryWrapper<AttrAttrgroupRelationEntity>().select("attr_group_id").eq("attr_id", attrRespVo.getAttrId())
        );
        if(null != attrGroupId) {
            attrRespVo.setAttrGroupId(attrGroupId.getAttrGroupId());
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

        // 修改分组
        AttrAttrgroupRelationEntity attrAttrgroupRelationEntity = new AttrAttrgroupRelationEntity();
        attrAttrgroupRelationEntity.setAttrGroupId(attr.getAttrGroupId());
        attrAttrgroupRelationDao.update(
                attrAttrgroupRelationEntity, new UpdateWrapper<AttrAttrgroupRelationEntity>().eq("attr_id", attrEntity.getAttrId())
        );
    }

}