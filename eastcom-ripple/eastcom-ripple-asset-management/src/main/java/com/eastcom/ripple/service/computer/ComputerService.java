package com.eastcom.ripple.service.computer;

import com.eastcom.ripple.entity.computer.Computer;
import com.eastcom.ripple.vo.ComputerVO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Hongzh
 * @date 2020/09/10
 * @description 业务层接口：对电脑信息进行各种业务处理
 */
public interface ComputerService {

    /**
     * 根据条件查询电脑信息
     * @param keyword
     * @param comStatus
     * @return
     */
    List<ComputerVO> findComByCondition(String keyword, Integer comStatus, HttpServletRequest request);

    /**
     * 添加电脑
     * @param computer
     * @return
     */
    String insertComputer(Computer computer);

    /**
     * 更改电脑信息
     * @param computer
     * @return
     */
    String updateComputer(Computer computer);

    /**
     * 根据电脑SN码删除电脑
     * @param comSn
     * @return
     */
    String deleteComputer(String comSn);
}
