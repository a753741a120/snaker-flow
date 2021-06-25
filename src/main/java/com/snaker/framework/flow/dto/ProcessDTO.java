package com.snaker.framework.flow.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.snaker.engine.entity.Order;
import org.snaker.engine.entity.Task;
import org.snaker.engine.model.ProcessModel;

import java.io.Serializable;
import java.sql.Blob;

/**
 * @基本功能: Process传输类
 * @program:demo
 * @author:pm
 * @create:2021-06-19 22:49:32
 **/
@ApiModel("流程定义数据传输类")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class ProcessDTO implements Serializable {

    private static final long serialVersionUID = 6541688543201014542L;
    /**
     * 主键ID
     */
    @ApiModelProperty("主键")
    private String id;
    /**
     * 版本
     */
    @ApiModelProperty("版本")
    private Integer version;
    /**
     * 流程定义名称
     */
    @ApiModelProperty("流程定义名称")
    private String name;
    /**
     * 流程定义显示名称
     */
    @ApiModelProperty("流程定义显示名称")
    private String displayName;
    /**
     * 流程定义类型（预留字段）
     */
    @ApiModelProperty("流程定义类型（预留字段）")
    private String type;
    /**
     * 当前流程的实例url（一般为流程第一步的url）
     * 该字段可以直接打开流程申请的表单
     */
    @ApiModelProperty("当前流程的实例url（一般为流程第一步的url）该字段可以直接打开流程申请的表单")
    private String instanceUrl;
    /**
     * 是否可用的开关
     */
    @ApiModelProperty("是否可用的开关 1、可用，0、不可用")
    private Integer state;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty("创建时间")
    private String createTime;
    /**
     * 创建人
     */
    @ApiModelProperty("创建人")
    private String creator;
    /**
     * 流程定义模型
     */
    @ApiModelProperty("流程定义模型")
    private ProcessModel models;

    /**
     * 流程定义xml
     */
    @TableField(exist = false)
    @ApiModelProperty("转换后的流程定义xml")
    private String dBContent;

    /**
     * 流程定义xml
     */
    @ApiModelProperty("流程定义xml")
    private Blob contents;
    /**
     * 流程定义字节数组
     */
    @ApiModelProperty("流程定义字节数组")
    private byte[] bytes;

    @TableField(exist = false)
    @ApiModelProperty("流程工作单")
    private Order order;
    @TableField(exist = false)
    @ApiModelProperty("任务")
    private Task task;

}