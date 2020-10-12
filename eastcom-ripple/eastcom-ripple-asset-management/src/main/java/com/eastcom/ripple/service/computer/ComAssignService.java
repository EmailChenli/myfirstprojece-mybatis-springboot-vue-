package com.eastcom.ripple.service.computer;

import com.eastcom.ripple.entity.computer.ComEmpRecord;
import com.eastcom.ripple.vo.AssignComQueryVO;
import com.eastcom.ripple.vo.AssignComVO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Hongzh
 * @date 2020/09/15
 * @description 业务层接口：对电脑管理进行分配、归还等业务处理
 */
public interface ComAssignService {

    /**
     * 查询所有分配电脑的记录
     * @return
     */
    List<AssignComVO> findAllAssignComRecord();

    /**
     * 分配电脑
     * @param queryVO
     * @return
     */
    String assignComputer(AssignComQueryVO queryVO, HttpServletRequest request);

    /**
     * 归还电脑
     * @param comEmpRecord
     * @return
     */
    String returnComputer(ComEmpRecord comEmpRecord, HttpServletRequest request);
}
