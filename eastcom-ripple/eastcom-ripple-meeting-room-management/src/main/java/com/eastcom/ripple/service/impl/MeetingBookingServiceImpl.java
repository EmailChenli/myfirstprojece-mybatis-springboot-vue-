package com.eastcom.ripple.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eastcom.ripple.common.util.ResultUtils;
import com.eastcom.ripple.common.vo.ResultVO;
import com.eastcom.ripple.entity.MeetingBooking;
import com.eastcom.ripple.mapper.MeetingBookingMapper;
import com.eastcom.ripple.service.MeetingBookingService;
import com.eastcom.ripple.vo.BookingStatusVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Lijj
 * @since 2020-09-15
 */
@Service
public class MeetingBookingServiceImpl extends ServiceImpl<MeetingBookingMapper, MeetingBooking> implements MeetingBookingService {

    @Autowired
    private MeetingBookingMapper meetingBookingMapper;

    @Override
    public ResultVO findByStatus(Map<String, Object> paras, Integer page, Integer rows) {
        try{
            int status = Integer.parseInt(paras.get("status").toString());
            Page<MeetingBooking> meetingBookingPage = new Page<>(page, rows);
            QueryWrapper<MeetingBooking> wrapper = new QueryWrapper<>();
            wrapper.eq("status",status);
            baseMapper.selectPage(meetingBookingPage,wrapper);
            List<MeetingBooking> list = meetingBookingPage.getRecords();
            Map<String,Object> map = new HashMap<>();
            map.put("count",meetingBookingPage.getTotal());
            map.put("list",list);
            return ResultUtils.success("查询成功",map);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResultUtils.error("查询失败");
    }

    @Transactional
    @Override
    public ResultVO updateBookingStatus(BookingStatusVo bookingStatusVo) {
        try{
            UpdateWrapper<MeetingBooking> wrapper = new UpdateWrapper<>();
            wrapper.eq("booking_id",bookingStatusVo.getBookingId())
                    .set("status",bookingStatusVo.getStatus())
                    .set("reject_reason",bookingStatusVo.getRejectReason());
            int count = meetingBookingMapper.update(null,wrapper);
            return count > 0 ? ResultUtils.success(count) : ResultUtils.error("修改失败");
        }catch(Exception e){
            e.printStackTrace();
        }
        return ResultUtils.error();
    }

    @Override
    public ResultVO searchNotApproved(Map<String, Object> paras, Integer page, Integer rows) {
        try{
            String para = (String)paras.get("para");
            int status = Integer.parseInt(paras.get("status").toString());
            Page<MeetingBooking> meetingBookingPage = new Page<>(page, rows);
            QueryWrapper<MeetingBooking> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("status",status);
            queryWrapper.and(wrapper -> wrapper.like("user_name",para)
                    .or().like("meeting_name",para)
                    .or().like("room_id",para));
            baseMapper.selectPage(meetingBookingPage,queryWrapper);
            List<MeetingBooking> list = meetingBookingPage.getRecords();
            Map<String,Object> map = new HashMap<>();
            map.put("list",list);
            map.put("count",meetingBookingPage.getTotal());
            return ResultUtils.success("查询成功",map);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResultUtils.error("查询失败");
    }

    @Override
    public ResultVO findApproved(Integer page, Integer rows) {
        try{
            Page<MeetingBooking> meetingBookingPage = new Page<>(page, rows);
            QueryWrapper<MeetingBooking> wrapper = new QueryWrapper<>();
            wrapper.eq("status",1)
                    .or().eq("status",2);
            baseMapper.selectPage(meetingBookingPage,wrapper);
            Map<String,Object> map = new HashMap<>();
            List<MeetingBooking> list = meetingBookingPage.getRecords();
            map.put("list",list);
            map.put("count",meetingBookingPage.getTotal());
            return ResultUtils.success("查询成功",map);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResultUtils.error("查询失败");
    }

    @Override
    public ResultVO searchApproved(Map<String, Object> paras, Integer page, Integer rows) {
        try{
            String para = (String)paras.get("para");
            Page<MeetingBooking> meetingBookingPage = new Page<>(page, rows);
            QueryWrapper<MeetingBooking> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("status",1).and(
                    wrapper -> wrapper.like("user_name",para)
                    .or().like("room_id",para)
                    .or().like("meeting_name",para));
            queryWrapper.or().eq("status",2).and(
                    wrapper -> wrapper.like("user_name",para)
                    .or().like("room_id",para)
                    .or().like("meeting_name",para));
            baseMapper.selectPage(meetingBookingPage,queryWrapper);
            Map<String,Object> map = new HashMap<>();
            List<MeetingBooking> list = meetingBookingPage.getRecords();
            map.put("count",meetingBookingPage.getTotal());
            map.put("list",list);
            return ResultUtils.success("查询成功",map);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResultUtils.error("查询失败");
    }
}
