package com.snaker.framework.config.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 表单字段实体类
 * @author yuqs
 * @since 0.1
 */
@ApiModel("表单字段实体类")
@Data
@TableName("DF_FIELD")
public class Field implements Serializable {
    private static final long serialVersionUID = -1;
    public static final String FLOW = "1";
    private long id;
    @ApiModelProperty("字段名")
    private String name;
    @ApiModelProperty("插件")
    private String plugins;
    @ApiModelProperty("标题")
    private String title;
    @ApiModelProperty("类型")
    private String type;
    @ApiModelProperty("工作流")
    private String flow;
    @ApiModelProperty("表名称")
    private String tableName;
    @ApiModelProperty("表单ID")
    private long formId = 0;
}
