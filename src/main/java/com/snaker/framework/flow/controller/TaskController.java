package com.snaker.framework.flow.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.snaker.framework.config.annotation.MethodLog;
import com.snaker.framework.exception.BizRuntimeException;
import com.snaker.framework.flow.service.HistoryOrderService;
import com.snaker.framework.flow.service.SnakerEngineFacets;
import com.snaker.framework.security.shiro.ShiroUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.snaker.engine.access.Page;
import org.snaker.engine.access.QueryFilter;
import org.snaker.engine.entity.HistoryOrder;
import org.snaker.engine.entity.Surrogate;
import org.snaker.engine.entity.Task;
import org.snaker.engine.entity.WorkItem;
import org.snaker.engine.model.TaskModel.TaskType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Snaker流程引擎常用Controller
 * @author yuqs
 * @since 0.1
 */
@Api(tags = "流程引擎接口")
@RestController
@RequestMapping(value = "/snaker/task")
public class TaskController {
	private static final Logger log = LoggerFactory.getLogger(TaskController.class);
	@Autowired
	private SnakerEngineFacets facets;

	@Autowired
	private HistoryOrderService orderService;


	/**
	 * 协办任务管理列表
	 * @return
	 */
	@MethodLog(desc = "查询协办任务列表",master = "流程引擎管理")
	@ApiOperation(value = "查询协办任务列表",notes = "")
	@ApiResponse(response = Surrogate.class,code = 0,message = "code=0时返回")
	@RequestMapping(value = "list", method=RequestMethod.GET)
	public R<Object> homeTaskList() {
		Map<Object, Object> map = new HashMap<>();
//		List<String> list = ShiroUtils.getGroups();
//		list.add(ShiroUtils.getUsername());
//		log.info(list.toString());
//		String[] assignees = new String[list.size()];
//		list.toArray(assignees);
		
		Page<WorkItem> majorPage = new Page<WorkItem>(5);
		Page<WorkItem> aidantPage = new Page<WorkItem>(3);
		Page<HistoryOrder> ccorderPage = new Page<HistoryOrder>(3);
		List<WorkItem> majorWorks = facets.getEngine()
				.query()
				.getWorkItems(majorPage, new QueryFilter()
//				.setOperators(assignees)
				.setTaskType(TaskType.Major.ordinal()));
		List<WorkItem> aidantWorks = facets.getEngine()
				.query()
				.getWorkItems(aidantPage, new QueryFilter()
//				.setOperators(assignees)
				.setTaskType(TaskType.Aidant.ordinal()));
		List<HistoryOrder> ccWorks = facets.getEngine()
				.query()
				.getCCWorks(ccorderPage, new QueryFilter()
//				.setOperators(assignees)
				.setState(1));
		map.put("majorWorks",majorWorks);
		map.put("majorTotal", majorPage.getTotalCount());
		map.put("aidantWorks", aidantWorks);
		map.put("aidantTotal", aidantPage.getTotalCount());
		map.put("ccorderWorks", ccWorks);
		map.put("ccorderTotal", ccorderPage.getTotalCount());
		return R.ok(map);
	}
	
	/**
	 * 根据当前用户查询待办任务列表
	 * @return
	 */
	@MethodLog(desc = "查询待办任务列表",master = "流程引擎管理")
	@ApiOperation(value = "查询待办任务列表",notes = "")
	@ApiResponse(response = WorkItem.class,code = 0,message = "code=0时返回")
	@RequestMapping(value = "user", method=RequestMethod.GET)
	public R<List<WorkItem>> userTaskList(@RequestParam("pageNo")Integer pageNo, @RequestParam("pageSize")Integer pageSize) {
		Page<WorkItem> page = new Page<>();
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
//		List<WorkItem> workItems = facets.getEngine().query().getWorkItems(page,new QueryFilter().setOperator(ShiroUtils.getUsername()));
		List<WorkItem> workItems = facets.getEngine().query().getWorkItems(page,new QueryFilter());
		return R.ok(workItems);
	}

	/**
	 * 保存
	 * @param orderId orderId
	 * @param taskName taskName
	 * @param operator operator
	 * @return
	 * @throws BizRuntimeException
	 */
	@MethodLog(desc = "保存",master = "流程引擎管理")
	@ApiOperation(value = "保存",notes = "")
	@ApiResponse(response = Task.class,code = 0,message = "code=0时返回")
    @PutMapping(value = "save/{orderId}")
    public boolean addTaskActor(@PathVariable("orderId") Integer orderId,
								@RequestParam("taskName") String taskName,
								@RequestParam("operator") String operator) throws BizRuntimeException{
        List<Task> tasks = facets.getEngine().query().getActiveTasks(new QueryFilter().setOrderId(String.valueOf(orderId)));
        try {
			for(Task task : tasks) {
				if(task.getTaskName().equalsIgnoreCase(taskName) && StringUtils.isNotEmpty(operator)) {
					facets.getEngine().task().addTaskActor(task.getId(), operator);
					return true;
				}
			}

		}catch (BizRuntimeException e){
        	e.printStackTrace();
        	log.error(e.getErrMsg(),"添加失败!");
        	return false;
		}
        return false;
    }

    @RequestMapping(value = "tip/{orderId}", method=RequestMethod.GET)
    public R<Map<String, String>> addTaskActor(@PathVariable("orderId") String orderId, String taskName) {
        List<Task> tasks = facets.getEngine().query().getActiveTasks(new QueryFilter().setOrderId(orderId));
        StringBuilder builder = new StringBuilder();
        String createTime = "";
        for(Task task : tasks) {
            if(task.getTaskName().equalsIgnoreCase(taskName)) {
                String[] actors = facets.getEngine().query().getTaskActorsByTaskId(task.getId());
                for(String actor : actors) {
                    builder.append(actor).append(",");
                }
                createTime = task.getCreateTime();
            }
        }
        if(builder.length() > 0) {
            builder.deleteCharAt(builder.length() - 1);
        }
        Map<String, String> data = new HashMap<String, String>();
        data.put("actors", builder.toString());
        data.put("createTime", createTime);
        return R.ok(data);
    }
	
	/**
	 * 活动任务查询列表
	 * @return
	 */
	@MethodLog(desc = "活动任务列表",master = "流程引擎管理")
	@ApiOperation(value = "活动任务列表",notes = "")
	@ApiResponse(response = WorkItem.class,code = 0,message = "code=0时返回")
	@RequestMapping(value = "active/more", method=RequestMethod.GET)
	public R<Map<Object, Object>> activeTaskList(@RequestParam("pageNo")Integer pageNo,
												 @RequestParam("pageSize")Integer pageSize,
												 @RequestParam(value = "taskType",required = false) Integer taskType) {
//		List<String> list = ShiroUtils.getGroups();
//		list.add(ShiroUtils.getUsername());
//		log.info(list.toString());
//		String[] assignees = new String[list.size()];
//		list.toArray(assignees);
//		facets.getEngine().query().getWorkItems(page, new QueryFilter().setOperators(assignees).setTaskType(taskType));
		Page<WorkItem> page = new Page<>();
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		facets.getEngine().query().getWorkItems(page, new QueryFilter().setTaskType(taskType));
		Map<Object, Object> map = new HashMap<>();
		map.put("page",page);
		map.put("taskType",taskType);
		return R.ok(map);
	}
	
	/**
	 * 活动任务查询列表
	 * @return
	 */
	@MethodLog(desc = "活动任务历史流程列表",master = "流程引擎管理")
	@ApiOperation(value = "活动任务历史流程列表",notes = "")
	@ApiResponse(response = com.snaker.framework.flow.entity.HistoryOrder.class,code = 0,message = "code=0时返回")
	@RequestMapping(value = "active/ccmore", method=RequestMethod.GET)
	public R<PageInfo<com.snaker.framework.flow.entity.HistoryOrder>> activeCCList(@RequestParam("pageNo")Integer pageNo, @RequestParam("pageSize")Integer pageSize) {
//		Page<HistoryOrder> page = new Page<>();
//		page.setPageNo(pageNo);
//		page.setPageSize(pageSize);
//		List<String> list = ShiroUtils.getGroups();
//		list.add(ShiroUtils.getUsername());
//		log.info(list.toString());
//		String[] assignees = new String[list.size()];
//		list.toArray(assignees);
//		facets.getEngine()
//				.query()
//				.getCCWorks(page, new QueryFilter()
//				.setOperators(assignees)
//				.setState(1));
		PageHelper.startPage(pageNo,pageSize);
		LambdaQueryWrapper<com.snaker.framework.flow.entity.HistoryOrder> qw = new LambdaQueryWrapper<>();
		List<com.snaker.framework.flow.entity.HistoryOrder> list = orderService.list(qw);
		return R.ok(new PageInfo<com.snaker.framework.flow.entity.HistoryOrder>(list));
	}
	
	/**
	 * 历史完成任务查询列表
	 * @return
	 */
	@MethodLog(desc = "历史完成任务列表",master = "流程引擎管理")
	@ApiOperation(value = "历史完成任务列表",notes = "")
	@ApiResponse(response = WorkItem.class,code = 0,message = "code=0时返回")
	@RequestMapping(value = "history", method=RequestMethod.GET)
	public R<Page<WorkItem>> historyTaskList(@RequestParam("pageNo")Integer pageNo, @RequestParam("pageSize")Integer pageSize) {
		Page<WorkItem> page = new Page<>();
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
//		facets.getEngine().query().getHistoryWorkItems(page, new QueryFilter().setOperator(ShiroUtils.getUsername()));
		facets.getEngine().query().getHistoryWorkItems(page, new QueryFilter());
		return R.ok(page);
	}
}
