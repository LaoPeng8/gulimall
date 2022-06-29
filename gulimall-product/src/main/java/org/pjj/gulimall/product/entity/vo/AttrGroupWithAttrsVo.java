package org.pjj.gulimall.product.entity.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.pjj.gulimall.product.entity.AttrEntity;

import java.util.List;

/**
 * @author PengJiaJun
 * @Date 2022/06/27 16:50
 */
@Data
public class AttrGroupWithAttrsVo {

    // AttrGroup的基本信息
    /**
     * 分组id
     */
    @TableId
    private Long attrGroupId;
    /**
     * 组名
     */
    private String attrGroupName;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 描述
     */
    private String descript;
    /**
     * 组图标
     */
    private String icon;
    /**
     * 所属分类id
     */
    private Long catelogId;

    // 每个AttrGroup下的所有Attr
    private List<AttrEntity> attrs;

}
