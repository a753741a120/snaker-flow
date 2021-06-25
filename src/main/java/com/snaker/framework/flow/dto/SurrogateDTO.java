package com.snaker.framework.flow.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.snaker.engine.entity.Surrogate;

import java.util.List;

/**
 * @基本功能:
 * @program:demo
 * @author:pm
 * @create:2021-06-21 13:43:28
 **/
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SurrogateDTO {

    private Surrogate surrogate;
    private List<String> processNames;
}