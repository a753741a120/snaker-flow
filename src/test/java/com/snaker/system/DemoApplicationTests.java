package com.snaker.system;


import com.baomidou.mybatisplus.extension.api.R;
import com.github.pagehelper.PageInfo;
import com.snaker.framework.config.controller.DictionaryController;
import com.snaker.framework.flow.controller.TaskController;
import com.snaker.framework.flow.dto.ProcessDTO;
import com.snaker.framework.flow.controller.ProcessController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.snaker.engine.entity.WorkItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = com.snaker.DemoApplication.class)
@WebAppConfiguration
@EnableAutoConfiguration
public class DemoApplicationTests {

    @Autowired
    private DictionaryController dictionaryController;

    @Autowired
    private ProcessController processController;

    @Autowired
    private TaskController taskController;

    @Test
    public void contextLoads() {
        R<PageInfo<ProcessDTO>> infoR = processController.processList(1, 5, "请假流程测试");
        System.out.println(infoR);
    }

    @Test
    public void userTaskList() {
//        R<PageInfo<WorkItem>> list = taskController.userTaskList(1, 5);
//        System.out.println(list);
    }
}

