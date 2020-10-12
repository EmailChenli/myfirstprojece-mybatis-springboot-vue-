package com.eastcom.ripple.controller;

import com.eastcom.ripple.common.util.ResultUtils;
import com.eastcom.ripple.common.vo.ResultVO;
import com.eastcom.ripple.entity.MeetingBooking;
import com.eastcom.ripple.service.MeetingBookingService;
import com.eastcom.ripple.vo.BookingStatusVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author Lijj
 * @since 2020-09-15
 */
@Api(value = "预订会议")
@Slf4j
@RestController
@RequestMapping("/meetingbooking")
public class MeetingBookingController {

    @Autowired
    private MeetingBookingService meetingBookingService;

    @ApiOperation(value = "查询未审批预订记录")
    @PostMapping("/findByStatus/{page}/{rows}")
    public ResultVO findByStatus(@PathVariable Integer page,
                                 @PathVariable Integer rows,
                                 @RequestBody Map<String,Object> paras){
        log.info("开始查询未审批预订记录");
        return meetingBookingService.findByStatus(paras, page, rows);
    }

    @ApiOperation(value = "修改预订状态")
    @PostMapping("/updateBookingStatus")
    public ResultVO updateBookingStatus(@RequestBody BookingStatusVo bookingStatusVo){
        log.info("开始修改预订状态");
        return meetingBookingService.updateBookingStatus(bookingStatusVo);
    }

    @ApiOperation(value = "模糊查询未审批预订记录")
    @PostMapping("/searchNotApproved/{page}/{rows}")
    public ResultVO searchNotApproved(@PathVariable Integer page,
                                            @PathVariable Integer rows,
                                            @RequestBody Map<String,Object> paras){
        log.info("开始修改预订状态");
        return meetingBookingService.searchNotApproved(paras, page, rows);
    }

    @ApiOperation(value = "查询已审批预订记录")
    @PostMapping("/findApproved/{page}/{rows}")
    public ResultVO findApproved(@PathVariable Integer page,
                                 @PathVariable Integer rows){
        log.info("开始查询已审批预订记录");
        return meetingBookingService.findApproved(page, rows);
    }

    @ApiOperation(value = "模糊查询已审批预订记录")
    @PostMapping("/searchApproved/{page}/{rows}")
    public ResultVO searchApproved(@PathVariable Integer page,
                                   @PathVariable Integer rows,
                                   @RequestBody Map<String,Object> paras){
        log.info("开始模糊查询已审批预订记录");
        return meetingBookingService.searchApproved(paras, page, rows);
    }

}

