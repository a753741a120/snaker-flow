package com.snaker.framework.config.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

/**
 * 配置字典实体类
 * @author yuqs
 * @since 0.1
 */
@ApiModel("配置字典实体类")
@Data
@TableName("conf_dictionary")
public class Dictionary extends DictionaryEntity {

	/**
	 *
	 */
	private static final long serialVersionUID = -7610108657592274423L;
	@ApiModelProperty("主键")
	private Long id;
	/**
	 * 名称
	 */
	@ApiModelProperty("名称")
	@NotEmpty(message = "名称不能为空")
	private String name;
	/**
	 * 中文名称
	 */
	@ApiModelProperty("中文名称")
	@NotEmpty(message = "中文名称不能为空")
	private String cnName;
	/**
	 * 描述
	 */
	@ApiModelProperty("描述")
	private String description;

	/**
	 * 字典选项
	 */
	@ApiModelProperty("字典选项")
	@TableField(exist = false)
	private List<DictionaryItem> dictionaryItems = new ArrayList<DictionaryItem>();
}
