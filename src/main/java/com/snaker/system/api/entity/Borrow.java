package com.snaker.system.api.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import com.snaker.framework.flow.entity.FlowEntity;
import lombok.Data;

@Data
@TableName("FLOW_BORROW")
public class Borrow extends FlowEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2523484310463519530L;
	private String operator;
	private String description;
	private Double amount;
	private String operateTime;
	private String repaymentDate;
}
