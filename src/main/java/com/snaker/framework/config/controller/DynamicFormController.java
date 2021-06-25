package com.snaker.framework.config.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.github.pagehelper.PageInfo;
import com.snaker.framework.config.annotation.MethodLog;
import com.snaker.framework.config.entity.Field;
import com.snaker.framework.config.entity.Form;
import com.snaker.framework.config.service.DynamicFormManager;
import com.snaker.framework.security.shiro.ShiroUtils;
import com.snaker.framework.utils.DateUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.snaker.engine.helper.JsonHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 动态表单管理Controller
 * @author yuqs
 * @since 0.1
 */
@Api(tags = "动态表单管理接口")
@RestController
@RequestMapping(value = "/config/form")
public class DynamicFormController {
    public static final String PARA_PROCESSID = "processId";
    public static final String PARA_ORDERID = "orderId";
    public static final String PARA_TASKID = "taskId";

    @Autowired
    private DynamicFormManager dynamicFormManager;

    /**
     * 查询表单列表
     * @return
     */
    @MethodLog(desc = "动态表单列表",master = "动态表单管理")
    @ApiOperation(value = "动态表单列表",notes = "动态表单管理")
    @GetMapping("/list")
    public R<PageInfo<Form>> list(@RequestParam("pageNum")Integer pageNum, @RequestParam("pageSize")Integer pageSize) {
        return R.ok(dynamicFormManager.list(pageNum,pageSize));
    }

    /**
     * 获取Form
     * @param id id
     * @return bean
     */
    @MethodLog(desc = "根据ID获取表单单个信息",master = "动态表单管理")
    @ApiOperation(value = "根据ID获取表单单个信息",notes = "动态表单管理")
    @GetMapping("/get/{id}")
    public R<Form> get(@PathVariable("id") Long id) {
        return R.ok(dynamicFormManager.getById(id));
    }

    /**
     * 修改
     * @param id id
     * @param form from
     * @return boolean
     */
    @MethodLog(desc = "修改",master = "动态表单管理")
    @ApiOperation(value = "修改",notes = "动态表单管理")
    @PutMapping("/update/{id}")
    public boolean update(@PathVariable("id")Integer id, @RequestBody Form form) {
        form.setCreator(ShiroUtils.getUsername());
        form.setCreateTime(DateUtils.getCurrentTime());
        form.setFieldNum(0);
        form.setId(id);
        return dynamicFormManager.updateById(form);
    }

    /**
     * 保存操作
     * @param formId fromId
     * @param parseForm parseForm
     * @return boolean
     */
    @MethodLog(desc = "保存",master = "动态表单管理")
    @ApiOperation(value = "保存",notes = "动态表单管理")
    @PostMapping(value = "processor/{formId}")
    public Boolean processor(@PathVariable("formId") Long formId, @RequestParam("parseForm") String parseForm) {
        Form entity = null;
        try {
            //获取From 对象
            entity = dynamicFormManager.getById(formId);
            //转换成json
            Map map = JsonHelper.fromJson(parseForm, Map.class);
            Map<String, Object> datas = (Map<String, Object>)map.get("add_fields");
            Map<String, String> nameMap = dynamicFormManager.process(entity, datas);
            String template = (String)map.get("template");
            String parseHtml = (String)map.get("parse");
            if(!nameMap.isEmpty()) {
                for(Map.Entry<String, String> entry : nameMap.entrySet()) {
                    template = template.replaceAll(entry.getKey(), entry.getValue());
                    parseHtml = parseHtml.replaceAll(entry.getKey(), entry.getValue());
                }
            }
            entity.setOriginalHtml(template);
            entity.setParseHtml(parseHtml);
            dynamicFormManager.save(entity);
            return Boolean.TRUE;
        } catch(Exception e) {
            e.printStackTrace();
            return Boolean.FALSE;
        }
    }

    /**
     * 删除
     * @param id id
     * @return boolean
     */
    @MethodLog(desc = "删除",master = "动态表单管理")
    @ApiOperation(value = "删除",notes = "动态表单管理")
    @DeleteMapping("delete/{id}")
    public boolean delete(@PathVariable("id") Long id) {
        return dynamicFormManager.removeById(id);
    }


    /**
     * 获取表单
     * @param id id
     * @param orderId orderId
     * @return
     */
    @MethodLog(desc = "获取表单",master = "动态表单管理")
    @ApiOperation(value = "获取表单",notes = "动态表单管理")
    @GetMapping(value = "formData/{id}/{orderId}")
    public Map<String, Object> formData(@PathVariable("id") Long id, @PathVariable("orderId") String orderId) {
        Form entity = dynamicFormManager.getById(id);
        return dynamicFormManager.getDataByOrderId(entity, orderId);
    }

    /**
     * 录入数据
     * @param id id
     * @param processId processId
     * @param orderId orderId
     * @param taskId taskId
     * @return
     */
    @MethodLog(desc = "录入数据",master = "动态表单管理")
    @ApiOperation(value = "录入数据",notes = "动态表单管理")
    @RequestMapping(value = "use/{id}")
    public R<Map<Object, Object>> use(@PathVariable("id") Long id,
                                      @RequestParam(value = "processId",required = false) String processId,
                                      @RequestParam(value = "orderId",required = false) String orderId,
                                      @RequestParam(value = "taskId",required = false) String taskId) {
        Map<Object, Object> map = new HashMap<>();
//        model.addAttribute("form", );
//        model.addAttribute("processId", processId);
//        model.addAttribute("orderId", orderId);
//        model.addAttribute("taskId", taskId);
        map.put("form",dynamicFormManager.getById(id));
        map.put("processId",processId);
        map.put("orderId",orderId);
        map.put("taskId",taskId);
        return R.ok(map);
//        if(StringUtils.isEmpty(orderId) || StringUtils.isNotEmpty(taskId)) {
//            return "config/formUse";
//        } else {
//            //setAttr("result", Form.dao.getDataByOrderId(model, orderId));
//            return "config/formUseView";
//        }
    }
    /**
     * 提交流程
     * @param request request
     * @param formId id
     * @return
     */
    @MethodLog(desc = "提交流程",master = "动态表单管理")
    @ApiOperation(value = "提交流程",notes = "动态表单管理")
    @PostMapping("submit/{formId}")
    public boolean submit(HttpServletRequest request, @PathVariable("formId") Long formId) {
        String processId = request.getParameter(PARA_PROCESSID);
        String orderId = request.getParameter(PARA_ORDERID);
        String taskId = request.getParameter(PARA_TASKID);
        List<Field> fields = dynamicFormManager.getFields(formId);
        Map<String, Object> params = new HashMap<String, Object>();
        for(Field field : fields) {
            if(Field.FLOW.equals(field.getFlow())) {
                String name = field.getName();
                String type = field.getType();
                String paraValue = request.getParameter(name);
                Object value = null;
                if("text".equalsIgnoreCase(type)) {
                    value = paraValue;
                } else if("int".equalsIgnoreCase(type)) {
                    if(paraValue == null || "".equals(paraValue)) {
                        value = 0;
                    } else {
                        try {
                            value = Integer.parseInt(request.getParameter(name));
                        } catch (Exception e) {
                            value = 0;
                        }
                    }
                } else if("float".equalsIgnoreCase(type)) {
                    if(paraValue == null || "".equals(paraValue)) {
                        value = 0.0;
                    } else {
                        try {
                            value = Double.parseDouble(request.getParameter(name));
                        } catch(Exception e) {
                            value = 0.0;
                        }
                    }
                } else {
                    value = paraValue;
                }
                params.put(name, value);
            }
        }

        Map<String, String[]> paraMap = request.getParameterMap();
        return dynamicFormManager.submit(formId, fields, params, request, processId, orderId, taskId);
    }
}
