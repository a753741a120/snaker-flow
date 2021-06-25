package com.snaker.system.api.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.snaker.framework.flow.service.SnakerEngineFacets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 请假流程Controller
 * 流程特点:多级审批流程、decision节点的表达式写法->${day > 2 ? 'transition5' : 'transition4'}
 * 业务数据:
 *     所有节点的业务数据均以json格式保存在order、task表的variable字段中
 *     建议业务数据以独立的表保存，通过orderId来关联
 * @author yuqs
 * @since 0.1
 */
@RestController
@RequestMapping(value = "/flow/leave")
public class LeaveController {

	@Autowired
    private SnakerEngineFacets facets;
	/**
	 * 请假申请路由方法
	 * @return
	 */
	@RequestMapping(value = "apply/{orderId}", method= RequestMethod.GET)
	public R<Map<String, Object>> apply(@PathVariable("orderId") String orderId, @RequestParam("taskName") String taskName) {
			//如果orderId非空、taskId为空，则表示申请步骤已提交，此时可获取申请数据
			//由于请假流程中的业务数据，是保存在任务表的variable字段中，所以通过flowData方法获取
			//如果业务数据保存在业务表中，需要业务表的orderId字段来关联流程，进而根据orderId查询出业务数据
			Map<String, Object> data = facets.flowData(orderId, taskName);
			return R.ok(data);
	}

}
