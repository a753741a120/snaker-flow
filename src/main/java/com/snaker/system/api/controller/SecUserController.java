package com.snaker.system.api.controller;


import com.snaker.framework.config.annotation.MethodLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author liuxzh
 * @since 2018-12-20
 */
@Api(tags = "测试接口")
@RestController
@RequestMapping("/api/secUser")
public class SecUserController {


    @MethodLog(desc = "hello",master = "测试接口")
    @ApiOperation(value = "hello",notes = "测试接口")
    @GetMapping("/hello")
    public String hello(){
        return "Hello,World!";
    }

}
