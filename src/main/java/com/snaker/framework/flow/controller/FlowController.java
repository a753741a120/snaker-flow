package com.snaker.framework.flow.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.snaker.framework.config.annotation.MethodLog;
import com.snaker.framework.flow.dto.ProcessDTO;
import com.snaker.framework.flow.entity.Approval;
import com.snaker.framework.flow.service.ApprovalManager;
import com.snaker.framework.flow.service.HistoryOrderService;
import com.snaker.framework.flow.service.SnakerEngineFacets;
import com.snaker.framework.security.shiro.ShiroUtils;
import com.snaker.framework.utils.ConvertUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.snaker.engine.access.Page;
import org.snaker.engine.access.QueryFilter;
import org.snaker.engine.entity.HistoryOrder;
import org.snaker.engine.entity.Order;
import org.snaker.engine.entity.Process;
import org.snaker.engine.entity.Task;
import org.snaker.engine.model.TaskModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author yuqs
 * @since 2.0
 */
@Api(tags = "流程实例管理接口")
@RestController
@RequestMapping(value = "/snaker/flow")
public class FlowController {
    public static final String PARA_PROCESSID = "processId";
    public static final String PARA_ORDERID = "orderId";
    public static final String PARA_TASKID = "taskId";
    public static final String PARA_TASKNAME = "taskName";
    public static final String PARA_METHOD = "method";
    public static final String PARA_NEXTOPERATOR = "nextOperator";
    public static final String PARA_NODENAME = "nodeName";
    public static final String PARA_CCOPERATOR = "ccOperator";
    @Autowired
    private SnakerEngineFacets facets;
    @Autowired
    private ApprovalManager manager;

    @Autowired
    private HistoryOrderService orderService;
    /**
     * 流程实例查询
     * @return
     */
    @MethodLog(desc = "流程实例列表",master = "流程实例管理")
    @ApiOperation(value = "流程实例列表",notes = "流程实例管理")
    @RequestMapping(value = "order", method= RequestMethod.GET)
    public R<Page<HistoryOrder>> order(@RequestParam("pageNo") Integer pageNo, @RequestParam("pageSize") Integer pageSize) {
        Page<HistoryOrder> page = new Page<>();
        page.setPageNo(pageNo);
        page.setPageSize(pageSize);
        facets.getEngine().query().getHistoryOrders(page, new QueryFilter());
        return R.ok(page);
    }

    /**
     * 抄送实例已读
     * @param id
     * @param url
     * @return
     */

//    @RequestMapping(value = "ccread")
//    public String ccread(String id, String url) {
//        List<String> list = ShiroUtils.getGroups();
//        list.add(ShiroUtils.getUsername());
//        String[] assignees = new String[list.size()];
//        list.toArray(assignees);
//        facets.getEngine().order().updateCCStatus(id, assignees);
//        return "redirect:" + url;
//    }

//    @SuppressWarnings("unchecked")
//	@RequestMapping(value = "process")
//    public String process(HttpServletRequest request) {
//        Map<String, Object> params = new HashMap<String, Object>();
//        Enumeration<String> paraNames = request.getParameterNames();
//        while (paraNames.hasMoreElements()) {
//            String element = paraNames.nextElement();
//            int index = element.indexOf("_");
//            String paraValue = request.getParameter(element);
//            if(index == -1) {
//                params.put(element, paraValue);
//            } else {
//                char type = element.charAt(0);
//                String name = element.substring(index + 1);
//                Object value = null;
//                switch(type) {
//                    case 'S':
//                        value = paraValue;
//                        break;
//                    case 'I':
//                        value = ConvertUtils.convertStringToObject(paraValue, Integer.class);
//                        break;
//                    case 'L':
//                        value = ConvertUtils.convertStringToObject(paraValue, Long.class);
//                        break;
//                    case 'B':
//                        value = ConvertUtils.convertStringToObject(paraValue, Boolean.class);
//                        break;
//                    case 'D':
//                        value = ConvertUtils.convertStringToObject(paraValue, Date.class);
//                        break;
//                    case 'N':
//                        value = ConvertUtils.convertStringToObject(paraValue, Double.class);
//                        break;
//                    default:
//                        value = paraValue;
//                        break;
//                }
//                params.put(name, value);
//            }
//        }
//        String processId = request.getParameter(PARA_PROCESSID);
//        String orderId = request.getParameter(PARA_ORDERID);
//        String taskId = request.getParameter(PARA_TASKID);
//        String nextOperator = request.getParameter(PARA_NEXTOPERATOR);
//        if (StringUtils.isEmpty(orderId) && StringUtils.isEmpty(taskId)) {
//            facets.startAndExecute(processId, ShiroUtils.getUsername(), params);
//        } else {
//            String methodStr = request.getParameter(PARA_METHOD);
//            int method;
//            try {
//                method = Integer.parseInt(methodStr);
//            } catch(Exception e) {
//                method = 0;
//            }
//            switch(method) {
//                case 0://任务执行
//                    facets.execute(taskId, ShiroUtils.getUsername(), params);
//                    break;
//                case -1://驳回、任意跳转
//                    facets.executeAndJump(taskId, ShiroUtils.getUsername(), params, request.getParameter(PARA_NODENAME));
//                    break;
//                case 1://转办
//                    if(StringUtils.isNotEmpty(nextOperator)) {
//                        facets.transferMajor(taskId, ShiroUtils.getUsername(), nextOperator.split(","));
//                    }
//                    break;
//                case 2://协办
//                    if(StringUtils.isNotEmpty(nextOperator)) {
//                        facets.transferAidant(taskId, ShiroUtils.getUsername(), nextOperator.split(","));
//                    }
//                    break;
//                default:
//                    facets.execute(taskId, ShiroUtils.getUsername(), params);
//                    break;
//            }
//        }
//        String ccOperator = request.getParameter(PARA_CCOPERATOR);
//        if(StringUtils.isNotEmpty(ccOperator)) {
//            facets.getEngine().order().createCCOrder(orderId, ShiroUtils.getUsername(), String.valueOf(ccOperator.split(",")));
//        }
//        return "redirect:/snaker/task/active";
//    }

    /**
     * 通用的流程展现页面入口
     * 将流程中的各环节表单以tab+iframe方式展现
     * @return
     */
    @MethodLog(desc = "将流程中的各环节表单以tab+iframe方式展现",master = "流程实例管理")
    @ApiOperation(value = "将流程中的各环节表单以tab+iframe方式展现",notes = "流程实例管理")
    @GetMapping(value = "/all")
    public R<ProcessDTO> all(@RequestParam("processId") String processId,
                             @RequestParam("orderId") String orderId,
                             @RequestParam("taskId") String taskId) {
        ProcessDTO processDTO = new ProcessDTO();
        if(StringUtils.isNotEmpty(processId)) {
            Process process = facets.getEngine().process().getProcessById(processId);
            BeanUtils.copyProperties(process,processDTO);
        }
        if(StringUtils.isNotEmpty(orderId)) {
            Order order = facets.getEngine().query().getOrder(orderId);
            processDTO.setOrder(order);
        }
        if(StringUtils.isNotEmpty(taskId)) {
            Task task = facets.getEngine().query().getTask(taskId);
            processDTO.setTask(task);
        }

        return R.ok(processDTO);
    }

    /**
     * 节点信息以json格式返回
     * all页面以节点信息构造tab及加载iframe
     */
    @MethodLog(desc = "节点信息",master = "流程实例管理")
    @ApiOperation(value = "节点信息",notes = "流程实例管理")
    @GetMapping(value = "node/{processId}")
    public Object node(@PathVariable("processId") String processId) {
        Process process = facets.getEngine().process().getProcessById(processId);
        List<TaskModel> models = process.getModel().getModels(TaskModel.class);
        List<TaskModel> viewModels = new ArrayList<TaskModel>();
        for(TaskModel model : models) {
            TaskModel viewModel = new TaskModel();
            viewModel.setName(model.getName());
            viewModel.setDisplayName(model.getDisplayName());
            viewModel.setForm(model.getForm());
            viewModels.add(viewModel);
        }
        return viewModels;
    }

    /**
     * 由于审批类流程在各业务系统中经常出现，至此本方法是统一审批的url
     * 如果审批环节能够统一，建议使用该方法返回统一审批页面
     * @return
     */
    @MethodLog(desc = "流程审批列表",master = "流程实例管理")
    @ApiOperation(value = "流程审批列表",notes = "流程实例管理")
    @GetMapping(value = "getApprovalList/{orderId}/{taskName}")
    public R<List<Approval>> getApprovalList(@PathVariable("orderId") String orderId,@PathVariable("taskName") String taskName) {

//		//如果任务ID为空则回到审批环节
//        if(StringUtils.isNotEmpty(taskId)) {
//            return "snaker/approval";
//        } else {
//            model.addAttribute("approvals", manager.findByFlow(orderId, taskName));
//            return "snaker/approvalView";
//        }
        return R.ok(manager.findByFlow(orderId, taskName));
    }

//    /**
//     * 审批环节的提交处理
//     * 其中审批表可根据具体审批的业务进行定制，此处仅仅是举例
//     */
//    @RequestMapping(value = "doApproval", method = RequestMethod.POST)
//    public String doApproval(Approval model) {
//        model.setOperateTime(new DateTime().toString("yyyy-MM-dd HH:mm:ss"));
//        model.setOperator(ShiroUtils.getUsername());
//        manager.save(model);
//
//        Map<String, Object> params = new HashMap<String, Object>();
//        params.put("result", model.getResult());
//        facets.execute(model.getTaskId(), ShiroUtils.getUsername(), params);
//        return "redirect:/snaker/task/active";
//    }
}
