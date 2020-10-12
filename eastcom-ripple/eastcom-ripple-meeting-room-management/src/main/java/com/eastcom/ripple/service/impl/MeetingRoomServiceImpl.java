package com.eastcom.ripple.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eastcom.ripple.common.util.ResultUtils;
import com.eastcom.ripple.common.vo.ResultVO;
import com.eastcom.ripple.entity.MeetingRoom;
import com.eastcom.ripple.mapper.MeetingRoomMapper;
import com.eastcom.ripple.service.MeetingRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Lijj
 * @since 2020-09-15
 */
@Service
public class MeetingRoomServiceImpl extends ServiceImpl<MeetingRoomMapper, MeetingRoom> implements MeetingRoomService {

    @Autowired
    private MeetingRoomMapper meetingRoomMapper;

    @Override
    public ResultVO findAll(int page, int rows) {
        try{
            Page<MeetingRoom> meetingRoomPage = new Page<>(page, rows);
            baseMapper.selectPage(meetingRoomPage,null);
            List<MeetingRoom> list = meetingRoomPage.getRecords();
            Map<String,Object> map = new HashMap<>();
            map.put("list",list);
            map.put("count",meetingRoomPage.getTotal());
            return ResultUtils.success("查询成功",map);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResultUtils.error("查询失败");
    }

    @Override
    public ResultVO findByRoomId(String roomId) {
        try{
            QueryWrapper<MeetingRoom> wrapper = new QueryWrapper<>();
            wrapper.eq("room_id",roomId);
            MeetingRoom meetingRoom = meetingRoomMapper.selectOne(wrapper);
            Map<String,Object> map = new HashMap<>();
            map.put("meetingRoom",meetingRoom);
            return ResultUtils.success(map);
        }catch(Exception e){
            e.printStackTrace();
        }
        return ResultUtils.error();
    }

    @Override
    public ResultVO searchRoom(Map<String,Object> paras, int page, int rows) {
        try{
            String para = (String)paras.get("para");
            Page<MeetingRoom> meetingRoomPage = new Page<>(page, rows);
            QueryWrapper<MeetingRoom> wrapper = new QueryWrapper<>();
            wrapper.like("room_id",para)
                    .or().like("room_name",para);
            baseMapper.selectPage(meetingRoomPage,wrapper);
            List<MeetingRoom> list = meetingRoomPage.getRecords();
            Map<String,Object> map = new HashMap<>();
            map.put("count",meetingRoomPage.getTotal());
            map.put("list",list);
            return ResultUtils.success("查询成功",map);
        }catch(Exception e){
            e.printStackTrace();
        }
        return ResultUtils.error();
    }

    @Transactional
    @Override
    public ResultVO saveRoom(MeetingRoom meetingRoom) {
        String roomId = meetingRoom.getRoomId();
        String roomName = meetingRoom.getRoomName();
        QueryWrapper<MeetingRoom> wrapper = new QueryWrapper<>();
        wrapper.eq("room_id",roomId)
                .or().eq("room_name",roomName);
        if(meetingRoomMapper.selectCount(wrapper) > 0){
            return ResultUtils.error("该会议室编号或名称已存在！");
        }
        try{
            MeetingRoom mr = new MeetingRoom();
            mr.setRoomId(roomId);
            mr.setRoomName(roomName);
            mr.setRoomSize(Integer.parseInt(meetingRoom.getRoomSize().toString()));
            mr.setRoomStatus(0);
            mr.setDescription(meetingRoom.getDescription());
            mr.setCreateTime(new Date());
            mr.setUpdateTime(mr.getCreateTime());
            int count = meetingRoomMapper.insert(mr);
            return count > 0 ? ResultUtils.success("保存成功") : ResultUtils.error("保存失败");
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResultUtils.error();
    }

    @Transactional
    @Override
    public ResultVO updateRoom(MeetingRoom meetingRoom) {
        String roomId = meetingRoom.getRoomId();
        String roomName = meetingRoom.getRoomName();
        QueryWrapper<MeetingRoom> wrapper = new QueryWrapper<>();
        wrapper.eq("room_name",roomName)
                .ne("room_id",roomId);
        if(meetingRoomMapper.selectCount(wrapper) > 0){
            return ResultUtils.error("该会议室名称已存在！");
        }
        try{
            UpdateWrapper<MeetingRoom> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("room_id",roomId)
                        .set("room_name",roomName)
                        .set("room_size",Integer.parseInt(meetingRoom.getRoomSize().toString()))
                        .set("description",meetingRoom.getDescription())
                        .set("update_time",new Date());
            int count = meetingRoomMapper.update(null,updateWrapper);
            return count > 0 ? ResultUtils.success("修改成功") : ResultUtils.error("修改失败");
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResultUtils.error();
    }

    @Transactional
    @Override
    public ResultVO deleteRoom(String roomId) {
        QueryWrapper<MeetingRoom> wrapper = new QueryWrapper<>();
        wrapper.eq("room_id",roomId);
        try{
            int count = meetingRoomMapper.delete(wrapper);
            return count > 0 ? ResultUtils.success("删除成功") : ResultUtils.error("删除失败");
        }catch(Exception e){
            e.printStackTrace();
        }
        return ResultUtils.error();
    }

}
