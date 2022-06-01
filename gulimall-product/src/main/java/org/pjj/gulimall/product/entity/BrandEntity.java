package org.pjj.gulimall.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import org.hibernate.validator.constraints.URL;
import org.pjj.common.valid.AddGroup;
import org.pjj.common.valid.UpdateGroup;
import org.pjj.common.valid.UpdateStatusGroup;
import org.pjj.common.valid.annotation.ListValue;

import javax.validation.constraints.*;

/**
 * 品牌
 * 
 * @author PengJiaJun
 * @email 2779824672@qq.com
 * @date 2022-05-07 14:08:21
 */
@Data
@TableName("pms_brand")
public class BrandEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 品牌id
	 */
	@TableId
	@NotNull(message = "修改必须指定品牌id", groups = {UpdateGroup.class, UpdateStatusGroup.class}) // 做品牌修改时brandId必须不能为null
	@Null(message = "新增不能指定品牌id", groups = {AddGroup.class}) // 做品牌新增时brandId必须为null
	private Long brandId;
	/**
	 * 品牌名
	 */
	@NotBlank(message = "品牌名必须提交", groups = {AddGroup.class, UpdateGroup.class}) // 做品牌新增时 和 品牌修改时 name 不能为空
	private String name;
	/**
	 * 品牌logo地址
	 */
	@URL(message = "logo必须是一个合法的url", groups = {AddGroup.class, UpdateGroup.class}) // 不管是 新增还是修改 只要传入了logo那么logo就必须是一个合法的url
															//  (如果没有传入logo则不由 @URL管, @URL只是传入了logo时才做校验为合法的url)
	@NotEmpty(groups = {AddGroup.class}) // 做新增操作时 logo不能为空()  (做修改操作时没有限制)
	private String logo;
	/**
	 * 介绍
	 */
	private String descript;
	/**
	 * 显示状态[0-不显示；1-显示]
	 */
	@NotNull(groups = {AddGroup.class, UpdateStatusGroup.class})
	@ListValue(vals = {0, 1}, groups = {AddGroup.class, UpdateStatusGroup.class}) // 自定义注解 规定属性的值只能是 vals 中的值
	private Integer showStatus;
	/**
	 * 检索首字母
	 */
	@Pattern(regexp = "^[a-zA-Z]$", message = "检索首字母必须是一个字母", groups = {AddGroup.class, UpdateGroup.class}) // 根据正则匹配元素
	@NotEmpty(groups = {AddGroup.class}) // 新增操作 不能为空 和 不能为空串
	private String firstLetter;
	/**
	 * 排序
	 */
	@NotNull(groups = {AddGroup.class}) // 不能为空
	@Min(value = 0, message = "排序必须大于等于0", groups = {AddGroup.class, UpdateGroup.class}) // 被注释的元素必须是一个数字, 其值最小不得小于value
	private Integer sort;

}
