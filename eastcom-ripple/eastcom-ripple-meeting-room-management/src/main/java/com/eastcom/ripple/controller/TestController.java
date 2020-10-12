package com.eastcom.ripple.controller;

import com.eastcom.ripple.common.util.ResultUtils;
import com.eastcom.ripple.common.vo.ResultVO;
import com.eastcom.ripple.entity.MeetingRoom;
import com.eastcom.ripple.service.MeetingRoomService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Hongzh
 * @date 2020/09/03
 * @description   测试环境是否配置成功
 */
@Api(tags="MeetingRoom测试类")
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

    @Autowired
    private MeetingRoomService meetingRoomService;

    @ApiOperation(value="测试会议室接口")
    @PostMapping("/testMeetingRoom")
    @ResponseBody
    public ResultVO testMeetingRoom(@RequestBody MeetingRoom meetingRoom){

        return ResultUtils.success("请求成功",meetingRoom);
    }

    @ApiOperation(value="测试-查找会议室")
    @PostMapping("/testMeetingRoom/{roomId}")
    @ResponseBody
    public ResultVO testMeetingRoom(@PathVariable("roomId") String roomId){

        return meetingRoomService.findByRoomId(roomId);
    }

    @ApiOperation(value = "设置用户头像", notes = "设置当前用户头像")
    @PutMapping("/profiles")
    public ResultVO setUserProfile(@RequestParam(required = true) MultipartFile profile) {
        //userService.updUserProfile(profile)
        return ResultUtils.success("");
    }
}
