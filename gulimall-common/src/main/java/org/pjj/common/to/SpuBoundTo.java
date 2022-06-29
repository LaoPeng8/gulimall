package org.pjj.common.to;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 在SpringCloud中 A服务调用B服务接口需要传递参数 是封装为一个对象, 转为Json传递给B服务 B服务再将Json转为对象处理
 * 该对象称之为 To, 与Vo类似, 由于To是 A服务需要 B服务也需要, 所以一般写在公共服务(Common)中
 * @author PengJiaJun
 * @Date 2022/06/29 20:22
 */
@Data
public class SpuBoundTo {

    private Long spuId;
    private BigDecimal growBounds;
    private BigDecimal buyBounds;

}
