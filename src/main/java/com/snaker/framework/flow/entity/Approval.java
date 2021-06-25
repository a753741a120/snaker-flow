package com.snaker.framework.flow.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;


/**
 * @author yuqs
 */
@Data
@TableName("FLOW_APPROVAL")
public class Approval extends FlowEntity {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1280430261161731105L;
	private String operator;
    private String operateTime;
    private String result;
    private String content;
    private String taskName;
}
