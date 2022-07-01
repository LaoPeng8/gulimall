package org.pjj.gulimall.ware.entity.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author PengJiaJun
 * @Date 2022/07/01 22:57
 */
@Data
public class PurchaseDoneVo {

    @NotNull
    private Long id;

    private PurchaseDoneItemVo items;

}
