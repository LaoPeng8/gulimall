package org.pjj.gulimall.ware.entity.vo;

import lombok.Data;

import java.util.List;

/**
 * @author PengJiaJun
 * @Date 2022/07/01 13:52
 */
@Data
public class MergeVo {
    private Long purchaseId;//整单id
    private List<Long> items;//合并项集合
}
