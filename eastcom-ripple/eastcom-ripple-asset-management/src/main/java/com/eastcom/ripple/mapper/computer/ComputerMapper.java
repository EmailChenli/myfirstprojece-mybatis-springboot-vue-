package com.eastcom.ripple.mapper.computer;

import com.eastcom.ripple.entity.computer.Computer;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @author Hongzh
 * @date 2020/09/10
 * @description 持久层接口：对电脑表进行各种操作
 */
public interface ComputerMapper {

    /**
     * 根据条件查询电脑信息
     * @param keyword
     * @param comStatus
     * @return
     */
    List<Computer> findComByCondition(@Param("keyword") String keyword,@Param("comStatus") Integer comStatus);

    /**
     * 根据电脑SN码查询
     * @param comSn
     * @return
     */
    List<Computer> findComByComSn(String comSn);

    /**
     * 根据资产编号查询
     * @param assetNum
     * @return
     */
    List<Computer> findComByAssetNum(String assetNum);

    /**
     * 添加电脑
     * @param computer
     * @return
     */
    int insertComputer(Computer computer);

    /**
     * 根据电脑SN码删除电脑
     * @param comSn
     * @return
     */
    int deleteComputer(String comSn);

    /**
     * 更改电脑信息
     * @param computer
     * @return
     */
    int updateComputer(Computer computer);

    /**
     * 根据电脑SN码修改电脑状态
     * @param comSn
     * @param comStatus
     * @return
     */
    int updateComStatus(@Param("comSn") String comSn, @Param("comStatus") Integer comStatus,@Param("updateTime") Date updateTime);

    /**
     * 根据电脑SN码修改电脑持有人工号
     * @param comSn
     * @param holderNum
     * @return
     */
    int updateHolderNum(@Param("comSn") String comSn,@Param("holderNum") Long holderNum,@Param("updateTime") Date updateTime);
}
