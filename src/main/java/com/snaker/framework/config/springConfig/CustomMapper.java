package com.snaker.framework.config.springConfig;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @基本功能: 配置类 解决不能传输JSON问题
 * @program:demo
 * @author:pm
 * @create:2021-06-20 19:16:36
 **/
@Configuration
public class CustomMapper extends ObjectMapper {

    public CustomMapper() {
        this.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        // 设置 SerializationFeature.FAIL_ON_EMPTY_BEANS 为 false
        this.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    }
}