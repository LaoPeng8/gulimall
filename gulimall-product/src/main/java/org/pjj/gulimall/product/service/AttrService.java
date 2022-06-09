package org.pjj.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.pjj.common.utils.PageUtils;
import org.pjj.gulimall.product.entity.AttrEntity;
import org.pjj.gulimall.product.entity.vo.AttrRespVo;
import org.pjj.gulimall.product.entity.vo.AttrVo;

import java.util.Map;

/**
 * 商品属性
 *
 * @author PengJiaJun
 * @email 2779824672@qq.com
 * @date 2022-05-07 14:08:21
 */
public interface AttrService extends IService<AttrEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveAttr(AttrVo attr);

    PageUtils queryBaseAttrPage(Long catelogId, Map<String, Object> params);

    AttrRespVo getAttrInfo(Long attrId);

    void updateAttr(AttrVo attr);
}

