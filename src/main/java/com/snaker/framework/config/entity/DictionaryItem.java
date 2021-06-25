package com.snaker.framework.config.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 配置字典选项实体类
 * @author yuqs
 * @since 0.1
 */
@ApiModel("配置字典选项实体类")
@Data
@TableName("CONF_DICTITEM")
public class DictionaryItem extends DictionaryEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1382491728106297904L;
	/**
	 * 字典选项名称
	 */
	@ApiModelProperty("字典选项名称")
	private String name;
	/**
	 * 编码（手动设置）
	 */
	@ApiModelProperty("编码（手动设置）")
	private String code;
	/**
	 * 描述
	 */
	@ApiModelProperty("描述")
	private String description;
	/**
	 * 字段选项排序字段
	 */
	@ApiModelProperty("字段选项排序字段")
	private Integer orderby;
	/**
	 * 配置字典实体（关联）
	 */
	@ApiModelProperty("配置字典实体（关联）")
	private Dictionary dictionary;
}
