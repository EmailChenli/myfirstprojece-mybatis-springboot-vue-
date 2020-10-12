package com.eastcom.ripple.controller;


import com.eastcom.ripple.common.vo.ResultVO;
import com.eastcom.ripple.service.MeetingRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author Lijj
 * @since 2020-09-15
 */
@Api(value = "预订记录")
@Slf4j
@RestController
@RequestMapping("/meetingrecord")
public class MeetingRecordController {

    @Autowired
    private MeetingRecordService meetingRecordService;

    @ApiOperation(value = "查询所有预订记录")
    @GetMapping("/findAll/{page}/{rows}")
    public ResultVO findAll(@PathVariable int page,
                            @PathVariable int rows){
        log.info("查询所有预订记录，页数：" + page + "，行数：" + rows);
        return meetingRecordService.findAll(page, rows);
    }

    @ApiOperation(value = "删除预订记录")
    @GetMapping("/deleteRecord/{recordId}")
    public ResultVO deleteRecord(@PathVariable int recordId){
        log.info("删除预订记录，recordId：" + recordId);
        return meetingRecordService.deleteRecord(recordId);
    }

    @ApiOperation(value = "模糊查询预订记录",notes = "paras封装搜索条件para，模糊搜索字段：user_name，realname，room_id")
    @PostMapping("/searchRecord/{page}/{rows}")
    public ResultVO searchRecord(@PathVariable Integer page,
                                 @PathVariable Integer rows,
                                 @RequestBody Map<String,Object> paras){
        log.info("开始模糊查询预订记录");
        return meetingRecordService.searchRecord(paras, page, rows);
    }

    @ApiOperation(value = "查询预订次数")
    @GetMapping("/findReserveTimes/{page}/{rows}")
    public ResultVO findReserveTimes(@PathVariable int page,
                                     @PathVariable int rows,
                                     HttpServletRequest request){
        String token = request.getHeader("token");
        log.info("查询预订次数，页数：" + page + "，行数：" + rows);
        return meetingRecordService.findReserveTimes(page, rows, token);
    }
}

