package com.snaker.framework.flow.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.snaker.engine.entity.Order;

/**
 * <p>
 * 历史流程实例表
 * </p>
 *
 * @author pm
 * @since 2021-06-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wf_hist_order")
public class HistoryOrder extends Model<HistoryOrder> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    private String id;

    /**
     * 流程定义ID
     */
    @TableField("process_Id")
    private String processId;

    /**
     * 状态 0结束 1活动
     */
    @TableField("order_State")
    private Integer orderState;

    /**
     * 发起人
     */
    private String creator;

    /**
     * 发起时间
     */
    @TableField("create_Time")
    private String createTime;

    /**
     * 完成时间
     */
    @TableField("end_Time")
    private String endTime;

    /**
     * 期望完成时间
     */
    @TableField("expire_Time")
    private String expireTime;

    /**
     * 优先级
     */
    private Integer priority;

    /**
     * 父流程ID
     */
    @TableField("parent_Id")
    private String parentId;

    /**
     * 流程实例编号
     */
    @TableField("order_No")
    private String orderNo;

    /**
     * 附属变量json存储
     */
    private String variable;


    public static final String ID = "id";

    public static final String PROCESS_ID = "process_Id";

    public static final String ORDER_STATE = "order_State";

    public static final String CREATOR = "creator";

    public static final String CREATE_TIME = "create_Time";

    public static final String END_TIME = "end_Time";

    public static final String EXPIRE_TIME = "expire_Time";

    public static final String PRIORITY = "priority";

    public static final String PARENT_ID = "parent_Id";

    public static final String ORDER_NO = "order_No";

    public static final String VARIABLE = "variable";

    /**
     * 根据历史实例撤回活动实例
     * @return 活动实例对象
     */
    public Order undo() {
        Order order = new Order();
        order.setId(this.id);
        order.setProcessId(this.processId);
        order.setParentId(this.parentId);
        order.setCreator(this.creator);
        order.setCreateTime(this.createTime);
        order.setLastUpdator(this.creator);
        order.setLastUpdateTime(this.endTime);
        order.setExpireTime(this.expireTime);
        order.setOrderNo(this.orderNo);
        order.setPriority(this.priority);
        order.setVariable(this.variable);
        order.setVersion(0);
        return order;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
