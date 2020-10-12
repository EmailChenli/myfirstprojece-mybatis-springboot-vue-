package com.eastcom.ripple.service.impl.computer;

import com.eastcom.ripple.common.vo.ResultVO;
import com.eastcom.ripple.dto.AssetDTO;
import com.eastcom.ripple.entity.computer.Computer;
import com.eastcom.ripple.mapper.asset.AssetMapper;
import com.eastcom.ripple.mapper.computer.ComputerMapper;
import com.eastcom.ripple.service.computer.ComputerService;
import com.eastcom.ripple.util.RemoteCallEmpUtils;
import com.eastcom.ripple.vo.ComputerVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Hongzh
 * @date 2020/09/10
 * @description 电脑业务层实现类
 */
@Service
@Slf4j
public class ComputerServiceImpl implements ComputerService {

    //引入持久层对象
    @Resource
    private ComputerMapper computerMapper;

    @Resource
    private AssetMapper assetMapper;

    //引入远程调用员工模块的工具类
    @Resource
    private RemoteCallEmpUtils remoteCallEmpUtils;

    /**
     * 根据条件查询电脑信息
     * @param keyword
     * @param comStatus
     * @return
     */
    @Override
    public List<ComputerVO> findComByCondition(String keyword, Integer comStatus, HttpServletRequest request) {
        //根据关键字和电脑状态进行查询资产信息数据
        List<Computer> computers = computerMapper.findComByCondition(keyword, comStatus);
        //声明用于存储抽取出来工号的List集合
        List<Long> employeeIds = new ArrayList<>();
        //转换为ComputerVO类型
        List<ComputerVO> computersVO = new ArrayList<>();
        for(Computer computer : computers){
            //声明一个中间变量，用于存储转换后的结果
            ComputerVO computerVO = new ComputerVO();
            //如果电脑持有人工号不为空，则将其抽取出来
            if (computer.getHolderNum() != null){
                employeeIds.add(computer.getHolderNum());
            }
            //开始转换
            BeanUtils.copyProperties(computer,computerVO);
            //转换后存入computersVO
            computersVO.add(computerVO);
        }
        //如果员工工号集合不为空，则批量查询员工名
        if (!employeeIds.isEmpty()) {
            //log.info("进入批量查询....");
            try{
                //获取token
                String token = request.getHeader("token");
                //调用批量查询员工名的方法
                ResultVO<Map<String, Map<String, String>>> res= remoteCallEmpUtils.getBatchQueryEmpName(employeeIds,token);
                //如果返回的结果，code为200，data不为null和data不为空集合，则说明查询成功
                if (res.getCode() == 200 && !res.getData().isEmpty() && res.getData() != null){
                    //获取返回结果中的data
                    Map<String, Map<String, String>> empNames = res.getData();
                    //遍历两个集合computers和computersVO，通过工号找到对应的姓名，并赋值到相应computerVO中的employeeName
                    for (int i = 0; i < computers.size(); i++) {
                        Long holderNum = computers.get(i).getHolderNum();
                        //如果工号不为空，则获取对应员工名，赋值到相应的computerVO中
                        if (holderNum != null){
                            String employeeName = empNames.get(holderNum.toString()).get("employeeName");
                            computersVO.get(i).setHolderName(employeeName);
                        }
                    }
                }else if (res.getCode() == 444) { //如果状态码为444，则说明查询失败
                    return null;
                }
            }catch (HttpClientErrorException e) {//如果远程调用出现异常，则返回null，代表出现失败
                return null;
            }
        }
        return computersVO;
    }

    /**
     * 添加电脑
     * @param computer
     * @return
     */
    @Transactional
    @Override
    public String insertComputer(Computer computer) {
        //根据电脑SN码查出来的记录
        List<Computer> recordByComSn = computerMapper.findComByComSn(computer.getComSn());
        //如果不为空，则说明有记录了
        if (!recordByComSn.isEmpty()){
            return "此电脑已有记录，请输入其他电脑信息！！";
        }
        //查找此资产编号对应的信息是否存在 ( 1 代表资产类别为电脑)
        List<AssetDTO> assetRecord = assetMapper.findByAssetNumAndType(computer.getAssetNum(), 1);
        if (assetRecord.isEmpty()){
            return "对不起，此资产编号并不存在，请重新输入！！";
        }
        //根据资产编号查出来的记录，判断是否为空
        List<Computer> recordByAssetNum = computerMapper.findComByAssetNum(computer.getAssetNum());
        if (!recordByAssetNum.isEmpty()){
            return "此资产编号已有记录，请输入其他编号";
        }
        //获取当前时间
        Date now = new Date();
        //设置创建和更新时间
        computer.setCreateTime(now);
        computer.setUpdateTime(now);
        //添加电脑
        int status = computerMapper.insertComputer(computer);
        if (status > 0){
            //表示添加成功
            //如果电脑名称不为空，那么更新对应资产信息的名称
            if (computer.getComName() != null && !computer.getComName().equals("null") && !computer.getComName().equals("")) {
                status = assetMapper.updateAssetName(assetRecord.get(0).getId(), computer.getComName(),now);
            }
        }
        return status > 0 ? "添加成功":"添加失败";
    }

    /**
     * 更改电脑信息
     * @param computer
     * @return
     */
    @Transactional
    @Override
    public String updateComputer(Computer computer) {
        //根据电脑SN码查出来的记录
        List<Computer> recordByComSn = computerMapper.findComByComSn(computer.getComSn());
        //如果为空，则返回提示信息
        if (recordByComSn.isEmpty()){
            return "对不起，并未发现此条电脑信息，请您重新刷新一下";
        }
        //获取当前时间
        Date now = new Date();
        //设置更新时间
        computer.setUpdateTime(now);
        //更新电脑
        int status = computerMapper.updateComputer(computer);
        //如果更新成功
        if (status > 0){
            //查找此资产编号对应的信息是否存在 ( 1 代表资产类别为电脑)
            List<AssetDTO> assetRecord = assetMapper.findByAssetNumAndType(computer.getAssetNum(), 1);
            if (!assetRecord.isEmpty()){
                //如果电脑名称不为空，那么更新对应资产信息的名称
                if (computer.getComName() != null && !computer.getComName().equals("null") && !computer.getComName().equals("")) {
                    status = assetMapper.updateAssetName(assetRecord.get(0).getId(), computer.getComName(),now);
                }
            }
        }
        return status > 0 ? "更新成功":"更新失败";
    }

    /**
     * 根据电脑SN码删除电脑
     * @param comSn
     * @return
     */
    @Transactional
    @Override
    public String deleteComputer(String comSn) {
        //根据电脑SN码查出来的记录
        List<Computer> recordByComSn = computerMapper.findComByComSn(comSn);
        //如果为空，则返回提示信息
        if (recordByComSn.isEmpty()){
            return "对不起，并未发现此条电脑信息，请您重新刷新一下";
        }
        return computerMapper.deleteComputer(comSn) > 0 ? "删除成功":"删除失败";
    }
}
