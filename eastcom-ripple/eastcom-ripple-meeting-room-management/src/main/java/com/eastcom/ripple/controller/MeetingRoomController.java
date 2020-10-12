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

import java.util.Map;

/**
 * @author Lijj
 * @since 2020-09-15
 */
@Api(value = "会议室")
@Slf4j
@RestController
@RequestMapping("/room")
public class MeetingRoomController {

    @Autowired
    private MeetingRoomService meetingRoomService;

    @ApiOperation(value="查找所有会议室信息")
    @GetMapping("/findAll/{page}/{rows}")
    public ResultVO findAll(@PathVariable int page,
                            @PathVariable int rows){
        log.info("查询所有会议室信息，页数：" + page + "，行数：" + rows);
        return meetingRoomService.findAll(page, rows);
    }

    @ApiOperation(value="模糊查询会议室信息", notes = "paras封装搜索条件para，模糊搜索字段：room_id，room_name")
    @PostMapping("/searchRoom/{page}/{rows}")
    public ResultVO searchRoom(@PathVariable Integer page,
                                 @PathVariable Integer rows,
                                 @RequestBody Map<String,Object> paras){
        log.info("开始模糊查询会议室信息");
        return meetingRoomService.searchRoom(paras, page, rows);
    }

    @ApiOperation(value="新增会议室")
    @PostMapping("/saveRoom")
    public ResultVO saveRoom(@RequestBody MeetingRoom meetingRoom){
        log.info("新增会议室：" + meetingRoom.getRoomId());
        return meetingRoomService.saveRoom(meetingRoom);
    }

    @ApiOperation(value="修改会议室")
    @PostMapping("/updateRoom")
    public ResultVO updateRoom(@RequestBody MeetingRoom meetingRoom){
        log.info("修改会议室：" + meetingRoom.getRoomId());
        return meetingRoomService.updateRoom(meetingRoom);
    }

    @ApiOperation(value="删除会议室")
    @GetMapping("/deleteRoom/{roomId}")
    public ResultVO deleteRoom(@PathVariable String roomId){
        log.info("删除会议室：" + roomId);
        return meetingRoomService.deleteRoom(roomId);
    }

}

