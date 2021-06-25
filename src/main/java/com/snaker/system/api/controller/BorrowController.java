package com.snaker.system.api.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.snaker.system.api.entity.Borrow;
import com.snaker.system.api.service.impl.BorrowServiceImpl;
import com.snaker.framework.exception.BizRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/flow/borrow")
public class BorrowController {
	@Autowired
	private BorrowServiceImpl manager;
	
	@RequestMapping(value = "apply/{orderId}", method= RequestMethod.GET)
	public R<Borrow> apply(@PathVariable("orderId") String orderId) {
		return R.ok(manager.findByOrderId(orderId));
	}

	/**
	 * 处理流程
	 * @param processId processId
	 * @param model model
	 * @return
	 */
	@RequestMapping(value = "applySave/{processId}", method= RequestMethod.POST)
	public boolean applySave(@PathVariable("processId") String processId,
							 @RequestBody Borrow model) {
		try {
			manager.save(processId, model.getOrderId(), model.getTaskId(), model);
			return true;
		}catch (BizRuntimeException e){
			e.printStackTrace();
			log.error(e.getErrMsg(),"流程处理失败!");
			return false;
		}
//		/** 业务数据处理结束 */
//		return "redirect:/snaker/task/active";
	}
}
