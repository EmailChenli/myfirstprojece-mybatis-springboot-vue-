package com.eastcom.ripple.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.eastcom.ripple.common.vo.ResultVO;
import com.eastcom.ripple.entity.MeetingBooking;
import com.eastcom.ripple.vo.BookingStatusVo;

import java.util.Map;

/**
 * @author Lijj
 * @since 2020-09-15
 */
public interface MeetingBookingService extends IService<MeetingBooking> {

    ResultVO findByStatus(Map<String, Object> paras, Integer page, Integer rows);

    ResultVO updateBookingStatus(BookingStatusVo bookingStatusVo);

    ResultVO searchNotApproved(Map<String, Object> paras, Integer page, Integer rows);

    ResultVO findApproved(Integer page, Integer rows);

    ResultVO searchApproved(Map<String, Object> paras, Integer page, Integer rows);
}
