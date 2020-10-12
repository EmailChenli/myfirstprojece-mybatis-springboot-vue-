package com.eastcom.ripple.util.job;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.eastcom.ripple.common.vo.ResultVO;
import com.eastcom.ripple.entity.MeetingBooking;
import com.eastcom.ripple.entity.MeetingRecord;
import com.eastcom.ripple.entity.MeetingRoom;
import com.eastcom.ripple.mapper.MeetingBookingMapper;
import com.eastcom.ripple.mapper.MeetingRecordMapper;
import com.eastcom.ripple.mapper.MeetingRoomMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.StopWatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


@Slf4j
@Service
public class MeetingRecordAutoDo {

    @Autowired
    private MeetingRecordMapper meetingRecordMapper;
    @Autowired
    private MeetingBookingMapper meetingBookingMapper;
    @Autowired
    private MeetingRoomMapper meetingRoomMapper;
    @Autowired
    private RestTemplate restTemplate;

    private final String GET_EMPLOYEEID = "http://localhost:8003/sys/account/findByAccountName";
    private final String UPDATE_RESERVE_TIMES = "http://localhost:8003/sys/employee/updateReserveTimes";

    @Transactional
    public void doSave() {
        try{
            Date now = new Date();
            StopWatch sw = StopWatch.createStarted();
            log.info("开始保存已完成的预订记录，" + now);
            QueryWrapper<MeetingBooking> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("status",3)
                    .and(wrapper -> wrapper.le("end_time",new Date()));
            List<MeetingBooking> meetingBookings =
                    meetingBookingMapper.selectList(queryWrapper);

            int num = 0;
            for(MeetingBooking m : meetingBookings){
                QueryWrapper<MeetingRecord> wrapper = new QueryWrapper<>();
                wrapper.eq("booking_id",m.getBookingId());
                QueryWrapper<MeetingBooking> mWrapper = new QueryWrapper<>();
                if(meetingRecordMapper.selectCount(wrapper) > 0){
                    log.info("预订记录已存在，bookingId：" + m.getBookingId());
                    mWrapper.eq("booking_id",m.getBookingId());
                    int n = meetingBookingMapper.delete(mWrapper);
                    if(n > 0){
                        log.info("删除成功，bookingId：" + m.getBookingId());
                        continue;
                    }else{
                        log.error("删除失败");
                        continue;
                    }
                }else{
                    //1）根据账号user_name(account_name)查找employee_id、employeeName
                    //2）更新用户预订次数
                    MeetingRecord meetingRecord = new MeetingRecord();
                    //headers
                    HttpHeaders requestHeaders = new HttpHeaders();
                    //body
                    //MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
                    //requestBody.add("roundid", "1");
                    //HttpEntity
                    HttpEntity<MultiValueMap> requestEntity = new HttpEntity<>(requestHeaders);
                    ResultVO resultVO = restTemplate.postForObject(GET_EMPLOYEEID + "/" + m.getUserName(),requestEntity,ResultVO.class);
                    if(resultVO.getCode() == 200){
                        Map<String,Object> map = (Map)resultVO.getData();
                        meetingRecord.setRealname(map.get("employeeName").toString());
                        meetingRecord.setBookingId(m.getBookingId());
                        meetingRecord.setMeetingName(m.getMeetingName());
                        meetingRecord.setRoomId(m.getRoomId());

                        QueryWrapper<MeetingRoom> roomQueryWrapper = new QueryWrapper<>();
                        roomQueryWrapper.eq("room_id",m.getRoomId());
                        MeetingRoom meetingRoom = meetingRoomMapper.selectOne(roomQueryWrapper);
                        meetingRecord.setRoomName(meetingRoom.getRoomName());

                        meetingRecord.setUserName(m.getUserName());
                        meetingRecord.setMeetingNumber(m.getMeetingNumber());
                        meetingRecord.setStartTime(m.getStartTime());
                        meetingRecord.setEndTime(m.getEndTime());
                        meetingRecord.setApplyTime(m.getApplyTime());
                        meetingRecord.setMeetingDescription(m.getMeetingDescription());
                        meetingRecord.setUpdateTime(now);
                        meetingRecord.setCreateTime(now);
                        int count = meetingRecordMapper.insert(meetingRecord);
                        if(count > 0){
                            mWrapper.eq("booking_id",m.getBookingId());
                            int n = meetingBookingMapper.delete(mWrapper);
                            if(n > 0)
                                log.info("移除已完成的预订记录成功，bookingId：" + m.getBookingId());
                            log.info("保存到历史预订记录成功，bookingId：" + m.getBookingId());
                            num++;
                            int employeeId = Integer.parseInt(map.get("employeeId").toString());
                            ResultVO resultVO2 = restTemplate.postForObject(UPDATE_RESERVE_TIMES + "/" + employeeId,requestEntity,ResultVO.class);
                            if(resultVO2.getCode() == 200
                                    && Integer.parseInt(resultVO2.getData().toString()) > 0){
                                log.info("更新预订次数成功，employeeId：" + employeeId + "，booking_id：" + m.getBookingId());
                            }else{
                                log.error("更新预订次数失败，employeeId：" + employeeId + "，booking_id：" + m.getBookingId());
                            }
                        }else{
                            log.error("保存失败");
                        }
                    }else{
                        log.error("获取员工id失败，bookingId：" + m.getBookingId());
                        continue;
                    }

                }
            }
            sw.stop();
            log.info("保存到历史预订记录成功，成功保存数：" + num + ",耗时：" + sw.getTime(TimeUnit.MILLISECONDS) + "ms");
        }catch(Exception e){
            log.error("更新失败");
            e.printStackTrace();
        }
    }

    @Transactional
    public void doChangeBookingStatus() {
        try{
            log.info("开始更新预订状态...");
            QueryWrapper<MeetingBooking> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("status",1)
                    .and(wrapper -> wrapper.le("end_time",new Date()));
            MeetingBooking meetingBooking = new MeetingBooking();
            meetingBooking.setStatus(3);
            int count = meetingBookingMapper.update(meetingBooking,queryWrapper);
            log.info("更新预订状态成功，更新记录数：" + count);
        }catch(Exception e){
            log.error("更新预订状态失败");
            e.printStackTrace();
        }
    }

    @Transactional
    public void doDelete() {
        try{
            log.info("开始删除未通过的预订记录...");
            QueryWrapper<MeetingBooking> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("status",2)
                    .and(wrapper -> wrapper.le("end_time",new Date()));
            int count = meetingBookingMapper.delete(queryWrapper);
            log.info("删除成功，记录数：" + count);
        }catch (Exception e){
            log.error("删除失败");
            e.printStackTrace();
        }

    }
}
