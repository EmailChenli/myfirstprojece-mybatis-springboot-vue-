package com.eastcom.ripple.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.eastcom.ripple.common.vo.ResultVO;
import com.eastcom.ripple.entity.MeetingRoom;

import java.util.Map;

/**
 * @author Lijj
 * @since 2020-09-15
 */
public interface MeetingRoomService extends IService<MeetingRoom> {

    ResultVO findAll(int page, int rows);

    ResultVO saveRoom(MeetingRoom meetingRoom);

    ResultVO updateRoom(MeetingRoom meetingRoom);

    ResultVO deleteRoom(String roomId);

    ResultVO searchRoom(Map<String, Object> paras, int page, int rows);

    ResultVO findByRoomId(String roomId);
}
