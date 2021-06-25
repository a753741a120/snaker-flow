package com.snaker.framework.flow.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.snaker.engine.entity.HistoryOrder;
import org.snaker.engine.entity.HistoryTask;

import java.util.List;

/**
 * @基本功能: order传输类
 * @program:demo
 * @author:pm
 * @create:2021-06-19 23:34:43
 **/
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class HistoryOrderDTO extends HistoryOrder {

    List<HistoryTask> historyTaskList;
}