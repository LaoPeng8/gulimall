package org.pjj.gulimall.product.controller;

import java.util.Arrays;
import java.util.Map;

import org.pjj.common.valid.AddGroup;
import org.pjj.common.valid.UpdateGroup;
import org.pjj.common.valid.UpdateStatusGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.pjj.gulimall.product.entity.BrandEntity;
import org.pjj.gulimall.product.service.BrandService;
import org.pjj.common.utils.PageUtils;
import org.pjj.common.utils.R;


/**
 * 品牌
 *
 * @author PengJiaJun
 * @email 2779824672@qq.com
 * @date 2022-05-07 15:18:41
 */
@RestController
@RequestMapping("product/brand")
public class BrandController {
    @Autowired
    private BrandService brandService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("product:brand:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = brandService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{brandId}")
    //@RequiresPermissions("product:brand:info")
    public R info(@PathVariable("brandId") Long brandId){
		BrandEntity brand = brandService.getById(brandId);

        return R.ok().put("brand", brand);
    }

    /**
     * 保存
     * 只有在标注了 @Valid 后 实体类中的 @NotNull等JSR303校验注解才会生效
     *
     * 在需要校验的bean后紧跟一个BindingResult对象 就可以获取到校验的结果(校验结果全封装到 bindingResult中了.)
     *
     * 如果每个需要校验的方法都使用这种 BindingResult 的方式, 岂不是很麻烦 (很多重复的代码)
     * 所有可以采用统一异常 使用@ControllerAdvice来统一捕获异常 这里写在 GulimallExceptionControllerAdvice.java类
     *
     * 不需要分组校验可以采用时 可以采用注解 @Valid 表示 该实体类需要校验 那么实体类中的 @NotNull 等注解才会生效
     * 需要分组校验时需要采用注解 @Validated(value = AddGroup.class) 表示 该实体类需要校验 且 需要指定分组为: AddGroup.class
     * 即表示该实体类虽然需要校验 但是 只需要校验属于 AddGroup.class 分组内的.
     * 比如 下面 brandId属性中的 @Null 就是属于 AddGroup.class 分组的, 只有@Validated(value = AddGroup.class)时, 才需要校验 @Null
     *
     * @NotNull(message = "修改必须指定品牌id", groups = {UpdateGroup.class}) // 做品牌修改时brandId必须不能为null
     * @Null(message = "新增不能指定品牌id", groups = {AddGroup.class}) // 做品牌新增时brandId必须为null
     * 	private Long brandId;
     *
     * 	注意: 使用 @Validated(value = AddGroup.class) 后, 实体类中的 @NotNull 这种没有分组的 默认是不会校验的
     * 	只会校验 @NotBlank(groups = {AddGroup.class}) 这种分组的而且分组是对应的, 其他分类也不会校验
     *
     */
//    @RequestMapping("/save")
//    //@RequiresPermissions("product:brand:save")
//    public R save(@Valid @RequestBody BrandEntity brand, BindingResult bindingResult){
//        if(bindingResult.hasErrors()) {// 判断校验是否出错 (校验的对象不合法)
//            Map<String, String> errorMap = new HashMap<>();
//            //1、获取校验的错误信息
//            bindingResult.getFieldErrors().forEach((fieldError) -> {
//                String message = fieldError.getDefaultMessage();// 获取错误校验消息
//                String field = fieldError.getField();// 获取错误校验的属性
//                errorMap.put(field, message);
//            });
//
//
//            return R.error(400, "提交的数据不合法").put("data", errorMap);
//        }
//
//        brandService.save(brand);
//
//
//        return R.ok();
//    }
    // 统一异常处理, 如果JSR303校验出现问题, 就会抛出异常, 则会被GulimallExceptionControllerAdvice拦截到然后继续返回.
    @RequestMapping("/save")
    public R save(@Validated(value = AddGroup.class) @RequestBody BrandEntity brand){
        brandService.save(brand);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("product:brand:update")
    public R update(@RequestBody @Validated(value = {UpdateGroup.class}) BrandEntity brand){
		brandService.updateById(brand);

        return R.ok();
    }

    /**
     * 修改状态
     */
    @RequestMapping("/update/status")
    public R updateStatus(@RequestBody @Validated(value = {UpdateStatusGroup.class}) BrandEntity brand) {
        brandService.updateById(brand);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("product:brand:delete")
    public R delete(@RequestBody Long[] brandIds){
		brandService.removeByIds(Arrays.asList(brandIds));

        return R.ok();
    }

}
