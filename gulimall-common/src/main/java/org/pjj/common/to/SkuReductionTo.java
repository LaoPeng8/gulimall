package org.pjj.common.to;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author PengJiaJun
 * @Date 2022/06/29 20:48
 */
@Data
public class SkuReductionTo {

    private Long skuId;
    private int fullCount;//满几件
    private BigDecimal discount;//打几折
    private int countStatus;//满几件打几折这个优惠 是否可以叠加其他优惠

    private BigDecimal fullPrice;//满多少钱
    private BigDecimal reducePrice;//减多少钱
    private int priceStatus;//满多少钱减多少钱这个优惠 是否可以叠加其他优惠
    private List<MemberPrice> memberPrice;//会员价

}
