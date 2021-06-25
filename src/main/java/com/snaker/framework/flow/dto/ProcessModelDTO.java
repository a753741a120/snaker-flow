package com.snaker.framework.flow.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.snaker.engine.model.ProcessModel;

/**
 * @基本功能:
 * @program:demo
 * @author:pm
 * @create:2021-06-19 23:12:10
 **/
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class ProcessModelDTO extends ProcessModel {

    private String json;


}