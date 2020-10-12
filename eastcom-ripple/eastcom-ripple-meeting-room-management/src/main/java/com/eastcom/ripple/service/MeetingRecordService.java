package com.eastcom.ripple.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.eastcom.ripple.common.vo.ResultVO;
import com.eastcom.ripple.entity.MeetingRecord;

import java.util.Map;

/**
 * @author Lijj
 * @since 2020-09-15
 */
public interface MeetingRecordService extends IService<MeetingRecord> {

    ResultVO findAll(int page, int rows);

    ResultVO deleteRecord(int recordId);

    ResultVO searchRecord(Map<String,Object> paras, int page, int rows);

    ResultVO findReserveTimes(int page, int rows,String token);
}
