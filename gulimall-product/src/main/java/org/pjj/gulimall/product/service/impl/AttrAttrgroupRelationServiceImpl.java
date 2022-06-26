package org.pjj.gulimall.product.service.impl;

import org.pjj.gulimall.product.entity.vo.AttrGroupRelationVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.pjj.common.utils.PageUtils;
import org.pjj.common.utils.Query;

import org.pjj.gulimall.product.dao.AttrAttrgroupRelationDao;
import org.pjj.gulimall.product.entity.AttrAttrgroupRelationEntity;
import org.pjj.gulimall.product.service.AttrAttrgroupRelationService;


@Service("attrAttrgroupRelationService")
public class AttrAttrgroupRelationServiceImpl extends ServiceImpl<AttrAttrgroupRelationDao, AttrAttrgroupRelationEntity> implements AttrAttrgroupRelationService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrAttrgroupRelationEntity> page = this.page(
                new Query<AttrAttrgroupRelationEntity>().getPage(params),
                new QueryWrapper<AttrAttrgroupRelationEntity>()
        );

        return new PageUtils(page);
    }


    @Override
    public void saveRelationBatch(List<AttrGroupRelationVo> attrGroupRelationVoList) {
        ArrayList<AttrAttrgroupRelationEntity> attrAttrgroupRelationEntities = new ArrayList<>();
        attrGroupRelationVoList.stream().forEach((attrRelationVo) -> {
            AttrAttrgroupRelationEntity attrAttrgroupRelationEntity = new AttrAttrgroupRelationEntity();
            BeanUtils.copyProperties(attrRelationVo, attrAttrgroupRelationEntity);
            attrAttrgroupRelationEntities.add(attrAttrgroupRelationEntity);
        });

        this.saveBatch(attrAttrgroupRelationEntities);
    }

}