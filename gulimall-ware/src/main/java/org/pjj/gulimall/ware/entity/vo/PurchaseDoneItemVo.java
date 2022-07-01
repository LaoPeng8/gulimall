package org.pjj.gulimall.ware.entity.vo;

import lombok.Data;

/**
 * @author PengJiaJun
 * @Date 2022/07/01 23:00
 */
@Data
public class PurchaseDoneItemVo {

    private Long itemId;
    private Integer status;
    private String reason;

}
