package com.eastcom.ripple.service.computer;

import com.eastcom.ripple.common.vo.ResultVO;
import com.eastcom.ripple.vo.AssignComQueryVO;
import com.eastcom.ripple.vo.ComHistoricalRecordVO;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * @author Hongzh
 * @date 2020/09/15
 * @description
 */
public interface ComHistoricalRecordService {

    /**
     * 根据条件查询电脑分配历史记录信息
     * @param keyword
     * @return
     */
    List<ComHistoricalRecordVO> findHistoryByCondition(String keyword);

    /**
     * 添加电脑分配历史记录
     * @param queryVO
     * @param AssetNum
     * @param employeeName
     * @return
     */
    int insertComHistoricalRecord(AssignComQueryVO queryVO,String AssetNum,String employeeName);

    /**
     * 根据历史记录ID更新对应历史纪录归还电脑的时间
     * @param comSn
     * @param employeeNum
     * @param getComTime
     * @param currentTime
     * @return
     */
    int updateHistoricalReturnComTime(String comSn, Long employeeNum, Date getComTime,Date currentTime);

    /**
     * 根据条件查询数据，并导出表格到Csv文件
     * @param currentPage
     * @param pageSize
     * @param keyword
     * @param response
     * @return
     */
    ResultVO exportDataToCsv(Integer currentPage, Integer pageSize, String keyword, HttpServletResponse response);
}
