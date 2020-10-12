package com.eastcom.ripple.mapper.computer;

import com.eastcom.ripple.entity.computer.ComEmpRecord;
import com.eastcom.ripple.vo.AssignComQueryVO;
import com.eastcom.ripple.vo.AssignComVO;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @author Hongzh
 * @date 2020/09/15
 * @description 持久层接口：对电脑与员工一对一记录表进行各种操作
 */
public interface ComEmpRecordMapper {


    /**
     * 查询所有分配电脑的记录
     * @return
     */
    List<AssignComVO> findAllAssignComRecord();

    /**
     * 根据id查询分配电脑记录
     * @param recordId
     * @return
     */
    List<ComEmpRecord> findComEmpRecordById(Integer recordId);

    /**
     * 添加电脑与员工一对一记录
     * @param queryVO
     * @return
     */
    int insertComEmpRecord(AssignComQueryVO queryVO);

    /**
     * 根据记录ID修改其归还电脑时间
     * @param recordId
     * @param returnComTime
     * @return
     */
    int updateReturnComTime(@Param("recordId") Integer recordId, @Param("returnComTime") Date returnComTime,@Param("updateTime") Date updateTime);
}
