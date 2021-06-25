/* Copyright 2012-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.snaker.framework.flow.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.snaker.framework.enums.ResultEnums;
import com.snaker.framework.exception.BizRuntimeException;
import com.snaker.framework.flow.SnakerHelper;
import com.snaker.framework.flow.dto.HistoryOrderDTO;
import com.snaker.framework.flow.dto.ProcessDTO;
import com.snaker.framework.flow.service.SnakerEngineFacets;
import com.snaker.framework.security.shiro.ShiroUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.snaker.engine.access.QueryFilter;
import org.snaker.engine.entity.HistoryOrder;
import org.snaker.engine.entity.HistoryTask;
import org.snaker.engine.entity.Process;
import org.snaker.engine.entity.Task;
import org.snaker.engine.helper.AssertHelper;
import org.snaker.engine.helper.StreamHelper;
import org.snaker.engine.helper.StringHelper;
import org.snaker.engine.model.ProcessModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 流程定义
 *
 * @author yuqs
 * @since 0.1
 */
@Api(tags = "流程定义接口")
@RestController
@RequestMapping(value = "/snaker/process")
public class ProcessController {

    private static Log log = LogFactory.getLog(ProcessController.class);
    @Autowired
    private SnakerEngineFacets facets;

    /**
     * 流程定义查询列表 初始化
     *
     * @return
     */
    @ApiOperation(value = "流程定义列表",notes = "流程定义管理")
    @GetMapping(value = "list")
    public R<PageInfo<ProcessDTO>> processList(@RequestParam("pageNo") Integer pageNo,
                                               @RequestParam("pageSize") Integer pageSize,
                                               @RequestParam("displayName") String displayName) {
        ArrayList<ProcessDTO> dtos = new ArrayList<>();
        PageHelper.startPage(pageNo,pageSize);
        QueryFilter filter = new QueryFilter();
        if(StringHelper.isNotEmpty(displayName)) {
            filter.setDisplayName(displayName);
        }
        List<Process> processs = facets.getEngine().process().getProcesss(filter);
        processs.forEach(item->{
            ProcessDTO dto = new ProcessDTO();
            BeanUtils.copyProperties(item,dto);
            Blob b = item.getContent();
            try {
                String blobString = new String(b.getBytes(1, (int) b.length()), StandardCharsets.UTF_8);//blob 转 String
                dto.setDBContent(blobString);
                dto.setModels(item.getModel());
                dtos.add(dto);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        return R.ok(new PageInfo<>(dtos));
    }

    /**
     * 设计流程定义[web流程设计器]
     *
     * @return
     */
    @ApiOperation(value = "设计流程定义[web流程设计器]",notes = "流程定义管理")
    @GetMapping("designer/{processId}")
    public R<Map<Object, Object>> processDesigner(@PathVariable("processId") String processId) {
        Map<Object, Object> map = new HashMap<>();
        Process process = facets.getEngine().process().getProcessById(String.valueOf(processId));
        AssertHelper.notNull(process);
        ProcessModel processModel = process.getModel();
        if (processModel != null) {
            String json = SnakerHelper.getModelJson(processModel);
            map.put("json", json);
        }
        map.put("processId", processId);
        return R.ok(map);
    }

    /**
     * 编辑流程定义
     *
     * @return
     */
    @ApiOperation(value = "编辑流程定义列表展示",notes = "流程定义管理")
    @RequestMapping(value = "edit/{id}", method = RequestMethod.GET)
    public R<ProcessDTO> processEdit(@PathVariable("id") String id) {
        ProcessDTO dto = new ProcessDTO();
        Process process = facets.getEngine().process().getProcessById(id);
        BeanUtils.copyProperties(process,dto);
        if (process.getDBContent() != null) {
            try {
                String xml = StringHelper.textXML(new String(process.getDBContent(), "UTF-8"));
                dto.setDBContent(xml);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return R.ok(dto);
    }

    /**
     * 根据流程定义ID，删除流程定义
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "根据流程定义ID，删除流程定义",notes = "流程定义管理")
    @DeleteMapping(value = "delete/{id}")
    public boolean processDelete(@PathVariable("id") Integer id) {
        facets.getEngine().process().undeploy(String.valueOf(id));
        return true;
    }

    /**
     * 添加流程定义后的部署
     *
     * @param snakerFile 文件
     * @param processId processId
     * @return
     */
    @ApiOperation(value = "添加流程定义后的部署",notes = "流程定义管理")
    @RequestMapping(value = "deploy/{processId}", method = RequestMethod.POST)
    public void processDeploy(@RequestParam(value = "snakerFile") MultipartFile snakerFile, @PathVariable("processId") Integer processId) throws BizRuntimeException {
        InputStream input = null;
        try {
            input = snakerFile.getInputStream();
            if (StringUtils.isNotEmpty(String.valueOf(processId))) {
                facets.getEngine().process().redeploy(String.valueOf(processId), input);
            } else {
                facets.getEngine().process().deploy(input);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new BizRuntimeException(ResultEnums.ERROR);
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 保存流程定义[web流程设计器]
     *
     * @param model
     * @return
     */
    @ApiOperation(value = "保存流程定义[web流程设计器]",notes = "流程定义管理")
    @PutMapping(value = "deployXml/{id}")
    public boolean processDeploy(@PathVariable("id") String id,@RequestParam("model") String model) {
        InputStream input = null;
        try {
            String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" + SnakerHelper.convertXml(model);
            System.out.println("model xml=\n" + xml);
            input = StreamHelper.getStreamFromString(xml);
            if (StringUtils.isNotEmpty(id)) {
                facets.getEngine().process().redeploy(id, input);
            } else {
                facets.getEngine().process().deploy(input);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

    /**
     * 启动流程
     * @param model model
     * @param processName processName
     * @return
     */
    @ApiOperation(value = "启动流程",notes = "流程定义管理")
    @RequestMapping(value = "start", method = RequestMethod.GET)
    public boolean processStart(Model model, String processName) {
        facets.startInstanceByName(processName, null, ShiroUtils.getUsername(), null);
        //这里前端做重定向
//        return "redirect:/snaker/process/list";
        return true;
    }

    @ApiOperation(value = "json",notes = "流程定义管理")
    @RequestMapping(value = "json", method = RequestMethod.GET)
    public R<Object> json(String processId, String orderId) {
        Process process = facets.getEngine().process().getProcessById(processId);
        AssertHelper.notNull(process);
        ProcessModel model = process.getModel();
        Map<String, String> jsonMap = new HashMap<String, String>();
        if (model != null) {
            jsonMap.put("process", SnakerHelper.getModelJson(model));
        }

        if (StringUtils.isNotEmpty(orderId)) {
            List<Task> tasks = facets.getEngine().query().getActiveTasks(new QueryFilter().setOrderId(orderId));
            List<HistoryTask> historyTasks = facets.getEngine().query().getHistoryTasks(new QueryFilter().setOrderId(orderId));
            jsonMap.put("state", SnakerHelper.getStateJson(model, tasks, historyTasks));
        }
        log.info(jsonMap.get("state"));
        //{"historyRects":{"rects":[{"paths":["TO 任务1"],"name":"开始"},{"paths":["TO 分支"],"name":"任务1"},{"paths":["TO 任务3","TO 任务4","TO 任务2"],"name":"分支"}]}}
        return R.ok(jsonMap);
    }


    /**
     * 查看流程图
     * @param orderId
     * @return
     */
    @ApiOperation(value = "查看流程图",notes = "流程定义管理")
    @GetMapping("/display/{orderId}/{processId}")
    public R<HistoryOrderDTO> display(@PathVariable("orderId") String orderId,@PathVariable("processId")String processId) {
        HistoryOrderDTO dto = new HistoryOrderDTO();
        HistoryOrder order = facets.getEngine().query().getHistOrder(orderId);
        BeanUtils.copyProperties(order,dto);
        List<HistoryTask> tasks = facets.getEngine().query().getHistoryTasks(new QueryFilter().setOrderId(orderId));
        dto.setHistoryTaskList(tasks);
        return R.ok(dto);
    }

}
