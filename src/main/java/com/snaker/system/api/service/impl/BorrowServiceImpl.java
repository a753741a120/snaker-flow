package com.snaker.system.api.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.snaker.system.api.dao.BorrowDao;
import com.snaker.system.api.entity.Borrow;
import com.snaker.framework.flow.service.SnakerEngineFacets;
import com.snaker.framework.security.shiro.ShiroUtils;
import com.snaker.framework.utils.DateUtils;
import org.apache.commons.lang.StringUtils;
import org.snaker.engine.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class BorrowServiceImpl extends ServiceImpl<BorrowDao, Borrow> {
    @Autowired
    private SnakerEngineFacets facets;
    @Autowired
    private BorrowDao dao;

    public void save(String processId, String orderId, String taskId, Borrow model) {
        /** 流程数据构造开始 */
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("apply.operator", ShiroUtils.getUsername());
        params.put("approval.operator", ShiroUtils.getUsername());
        /** 流程数据构造结束 */

        /**
         * 启动流程并且执行申请任务
         */
        if (StringUtils.isEmpty(orderId) && StringUtils.isEmpty(taskId)) {
            Order order = facets.startAndExecute(processId, ShiroUtils.getUsername(), params);
            /** 业务数据处理开始*/
            model.setOrderId(order.getId());
            model.setOperateTime(DateUtils.getCurrentDay());
            model.setOperator(ShiroUtils.getFullname());
            save(model);
        } else {
            facets.execute(taskId, ShiroUtils.getUsername(), params);
            /** 业务数据处理开始*/
            model.setOperator(ShiroUtils.getFullname());
            save(model);
        }
    }

	/**
	 * 根据主键ID删除对应的
	 * @param id
	 */
	public void delete(Long id) {
		dao.deleteById(id);
	}
	
	/**
	 * 根据主键ID获取实体
	 * @param id
	 * @return
	 */
	public Borrow get(Long id) {
		return dao.selectById(id);
	}
	
	/**
	 * 获取所有记录
	 * @return
	 */
	public List<Borrow> getAll() {
		return dao.selectList(new LambdaQueryWrapper<>());
	}
	
	public Borrow findByOrderId(String orderId) {
		LambdaQueryWrapper<Borrow> qw = new LambdaQueryWrapper<>();
		qw.eq(Borrow::getId,orderId);
		List<Borrow> results = dao.selectList(qw);
		if(results != null && results.size() > 0) {
			return results.get(0);
		} else {
			return null;
		}
	}
}
