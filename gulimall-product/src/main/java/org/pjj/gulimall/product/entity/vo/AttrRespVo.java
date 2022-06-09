package org.pjj.gulimall.product.entity.vo;

import lombok.Data;

/**
 * @author PengJiaJun
 * @Date 2022/06/05 16:11
 */
@Data
public class AttrRespVo extends AttrVo {

    /**
     *  所属分组名字
     */
    private String groupName;

    /**
     * 所属分类名字, '手机/数码/手机'
     */
    private String catelogName;

    /**
     * 分类完整路径 Long [父id, 子id, 孙id]
     */
    private Long[] catelogPath;

}
