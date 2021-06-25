package com.snaker.framework.config.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 表单实体类
 * @author yuqs
 * @since 0.1
 */
@ApiModel("表单实体类")
@Data
@TableName("DF_FORM")
public class Form implements Serializable {
    @ApiModelProperty("主键")
    private long id;
    @ApiModelProperty("名称")
    private String name;
    @ApiModelProperty("显示名称")
    private String displayName;
    @ApiModelProperty("类别")
    private String type;
    @ApiModelProperty("创建人")
    private String creator;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("原始文本")
    private String originalHtml;
    @ApiModelProperty("解析文本")
    private String parseHtml;
    @ApiModelProperty("字段数")
    private int fieldNum = 0;
}
