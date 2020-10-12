package com.eastcom.ripple.controller;

import com.eastcom.ripple.common.util.ResultUtils;
import com.eastcom.ripple.common.vo.ResultVO;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Hongzh
 * @date 2020/09/03
 * @description   测试环境是否配置成功
 */
@RestController
@Slf4j
@RequestMapping("/test")
public class TestController {

    @ApiOperation(value = "测试环境配置",httpMethod = "GET")
    @ApiImplicitParam(name = "id", dataType="Long", required = true, paramType="path")
    @GetMapping("/testEnvironment/{id}")
    public ResultVO testEnvironment(@PathVariable("id") Long id){
        log.info("测试环境接口 start...");
        log.info("参数为："+id);
        return ResultUtils.success("测试成功，可以放心进行开发！！");
    }
}
