package org.pjj.gulimall.ware.feign;

import org.pjj.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author PengJiaJun
 * @Date 2022/07/02 00:48
 */
@FeignClient("gulimall-product")
public interface ProductFeignService {

    /**
     *
     * /product/skuinfo/info/{skuId}           ->  @FeignClient("gulimall-product")  这个请求是直接请求服务得
     * /api/product/skuinfo/info/{skuId}       ->  @FeignClient("gulimall-gateway")  这个是通过gateway间接请求服务得
     *
     *
     * @param skuId
     * @return
     */
    @GetMapping("product/skuinfo/info/{skuId}")
    R info(@PathVariable("skuId") Long skuId);

}
