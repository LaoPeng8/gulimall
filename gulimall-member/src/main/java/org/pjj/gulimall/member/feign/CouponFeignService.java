package org.pjj.gulimall.member.feign;

import org.pjj.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author PengJiaJun
 * @Date 2022/5/8 18:05
 */
@FeignClient(value = "gulimall-coupon")
public interface CouponFeignService {

    @RequestMapping("/coupon/coupon/member/list")
    R memberCoupons();

}
