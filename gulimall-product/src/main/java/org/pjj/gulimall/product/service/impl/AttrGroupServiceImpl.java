package org.pjj.gulimall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.pjj.gulimall.product.dao.AttrAttrgroupRelationDao;
import org.pjj.gulimall.product.entity.AttrAttrgroupRelationEntity;
import org.pjj.gulimall.product.entity.vo.AttrGroupRelationVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.pjj.common.utils.PageUtils;
import org.pjj.common.utils.Query;

import org.pjj.gulimall.product.dao.AttrGroupDao;
import org.pjj.gulimall.product.entity.AttrGroupEntity;
import org.pjj.gulimall.product.service.AttrGroupService;
import org.springframework.util.StringUtils;


@Service("attrGroupService")
public class AttrGroupServiceImpl extends ServiceImpl<AttrGroupDao, AttrGroupEntity> implements AttrGroupService {

    @Autowired
    private AttrAttrgroupRelationDao attrAttrgroupRelationDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrGroupEntity> page = this.page(
                new Query<AttrGroupEntity>().getPage(params),
                new QueryWrapper<AttrGroupEntity>() // 没有where条件
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params, Long catelogId) {

        // select * from pms_attr_group where ?
        QueryWrapper<AttrGroupEntity> wrapper = new QueryWrapper<AttrGroupEntity>();

        String key = (String) params.get("key");
        if(!StringUtils.isEmpty(key)) {// key不为空, 才需要条件查询key
            wrapper.and((obj) -> {
                //select * from pms_attr_group where (attr_group_id = key or attr_group_name like %key%) and ?
                obj.eq("attr_group_id", key).or().like("attr_group_name", key);
            });
        }

        //传入的 catelogId为0, 则表示查询所有
        if(null == catelogId || catelogId == 0) {
            // select * from pms_attr_group where (attr_group_id = key or attr_group_name like %key%)
            IPage<AttrGroupEntity> page = this.baseMapper.selectPage(new Query<AttrGroupEntity>().getPage(params), wrapper);
            return new PageUtils(page);
        }

        // 传入的 catelogId有值, 则按照catelogId进行查询
        // select * from pms_attr_group where (attr_group_id = key or attr_group_name like %key%) and catelog_id = ?
        wrapper.eq("catelog_id", catelogId);
        IPage<AttrGroupEntity> page = this.baseMapper.selectPage(new Query<AttrGroupEntity>().getPage(params), wrapper);

        return new PageUtils(page);
    }

    /**
     * 根据属性id 与 属性分组id 从属性分组中删除属性 (批量删除)
     * @param attrGroupRelationVoList
     */
    @Override
    public void deleteRelationBatch(List<AttrGroupRelationVo> attrGroupRelationVoList) {
        List<AttrAttrgroupRelationEntity> entityList = attrGroupRelationVoList.stream()
                .map((attrGroupRelationVo) -> {
                    AttrAttrgroupRelationEntity attrAttrgroupRelationEntity = new AttrAttrgroupRelationEntity();
                    BeanUtils.copyProperties(attrGroupRelationVo, attrAttrgroupRelationEntity);
                    return attrAttrgroupRelationEntity;
                }).collect(Collectors.toList());

        attrAttrgroupRelationDao.deleteBatchRelation(entityList);


    }

}