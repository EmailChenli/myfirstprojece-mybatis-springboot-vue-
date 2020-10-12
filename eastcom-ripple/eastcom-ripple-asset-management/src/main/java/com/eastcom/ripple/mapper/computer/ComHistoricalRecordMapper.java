package com.eastcom.ripple.mapper.computer;

import com.eastcom.ripple.entity.computer.ComHistoricalRecord;
import com.eastcom.ripple.vo.ComHistoricalRecordVO;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @author Hongzh
 * @date 2020/09/15
 * @description 持久层接口：对电脑历史记录表进行各种操作
 */
public interface ComHistoricalRecordMapper {

    /**
     * 根据条件查询电脑分配历史记录信息
     * @param keyword
     * @return
     */
    List<ComHistoricalRecordVO> findHistoryByCondition(String keyword);
    /**
     * 根据电脑sn、工号、得到电脑时间，且归还时间为null，获得历史记录的ID
     * @param comSn
     * @param employeeNum
     * @param getComTime
     * @return
     */
    Integer findHistoricalId(@Param("comSn") String comSn, @Param("employeeNum") Long employeeNum, @Param("getComTime") Date getComTime);


    /**
     * 添加电脑分配历史记录
     * @param comHistoricalRecord
     * @return
     */
    int insertComHistoricalRecord(ComHistoricalRecord comHistoricalRecord);

    /**
     * 根据历史记录ID修改该记录归还电脑的时间
     * @param historicalId
     * @param returnTime
     * @return
     */
    int updateReturnComTime(@Param("historicalId") Integer historicalId, @Param("returnTime") Date returnTime,@Param("updateTime") Date updateTime);
}
