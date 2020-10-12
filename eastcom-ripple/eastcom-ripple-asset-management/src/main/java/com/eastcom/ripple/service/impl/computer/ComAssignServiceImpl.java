package com.eastcom.ripple.service.impl.computer;

import com.eastcom.ripple.common.vo.ResultVO;
import com.eastcom.ripple.entity.computer.ComEmpRecord;
import com.eastcom.ripple.entity.computer.Computer;
import com.eastcom.ripple.mapper.computer.ComEmpRecordMapper;
import com.eastcom.ripple.mapper.computer.ComputerMapper;
import com.eastcom.ripple.service.computer.ComAssignService;
import com.eastcom.ripple.service.computer.ComHistoricalRecordService;
import com.eastcom.ripple.util.RemoteCallEmpUtils;
import com.eastcom.ripple.vo.AssignComQueryVO;
import com.eastcom.ripple.vo.AssignComVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Hongzh
 * @date 2020/09/15
 * @description 分配电脑业务层实现类
 */
@Service
@Slf4j
public class ComAssignServiceImpl implements ComAssignService {

    //引入业务层对象
    @Resource
    private ComHistoricalRecordService comHistoricalRecordService;

    //引入持久层对象
    @Resource
    private ComputerMapper computerMapper;

    @Resource
    private ComEmpRecordMapper comEmpRecordMapper;

    //引入远程调用员工模块的工具类
    @Resource
    private RemoteCallEmpUtils remoteCallEmpUtils;

    /**
     * 查询所有分配电脑的记录
     * @return
     */
    @Override
    public List<AssignComVO> findAllAssignComRecord() {
        return comEmpRecordMapper.findAllAssignComRecord();
    }

    /**
     * 分配电脑
     * @param queryVO
     * @return
     */
    @Transactional
    @Override
    public String assignComputer(AssignComQueryVO queryVO, HttpServletRequest request) {
        //获取电脑SN码和员工工号
        String comSn = queryVO.getComSn();
        Long employeeNum = queryVO.getEmployeeNum();

        //根据电脑SN码查出来的记录
        List<Computer> recordByComSn = computerMapper.findComByComSn(comSn);
        //如果为空，则返回提示信息
        if (recordByComSn.isEmpty()) {
            return "对不起，此电脑并不存在，请您前往电脑管理对其进行存取！";
        }
        //获取电脑状态
        Integer comStatus = recordByComSn.get(0).getComStatus();
        //如果状态>0，则表明已经被分配出去了
        if (comStatus > 0){
            return "对不起，此电脑已有人使用，请您重新分配电脑！";
        }
        //声明存储员工姓名的变量
        String employeeName = "";
        try{
            //获取token
            String token = request.getHeader("token");
            //根据员工工号查出来的记录
            ResultVO<Map<String,Object>> res = remoteCallEmpUtils.findEmployeeById(employeeNum,token);
            //如果为空，则返回提示信息
            if (res.getCode() == 200 && res.getData() == null){
                return "对不起，并未发现该员工信息，请您再次核查！！";
            }else if (res.getCode() == 200 && res.getData() != null){
                //获取员工名
               employeeName = res.getData().get("employeeName").toString();
            }else if (res.getCode() == 444){
                return "分配失败";
            }
        }catch (HttpClientErrorException e){
            return "分配失败";
        }

        //获取资产编号
        String assetNum = recordByComSn.get(0).getAssetNum();

        //获取当前时间
        Date now = new Date();
        //给queryVO对象赋值
        queryVO.setGetComTime(now);
        queryVO.setCreateTime(now);
        queryVO.setUpdateTime(now);

        //插入记录
        boolean insertComEmployeeRecordResult = comEmpRecordMapper.insertComEmpRecord(queryVO) > 0;
        //给电脑修改状态 (1 代表被拥有)
        boolean updateStatusResult = computerMapper.updateComStatus(comSn, 1,now) > 0;

        //给电脑添加持有人工号(电脑SN码，持有人工号)
        boolean updateHolderNumResult = computerMapper.updateHolderNum(comSn,employeeNum,now) > 0;

        //员工电脑数+1(员工工号，拥有电脑总数)
        //int updateOwnComSumResult = employeeMapper.updateOwnComSum(employee.getEmployeeNum(), employee.getOwnComSum() + 1);

        //插入历史记录
        boolean insertHistoricalRecordResult = comHistoricalRecordService.insertComHistoricalRecord(queryVO,assetNum,employeeName) > 0;

        boolean result = insertComEmployeeRecordResult && updateStatusResult && updateHolderNumResult && insertHistoricalRecordResult;
        return  result ? "分配成功":"分配失败";
    }

    /**
     * 归还电脑
     * @param comEmpRecord
     * @return
     */
    @Transactional
    @Override
    public String returnComputer(ComEmpRecord comEmpRecord,HttpServletRequest request) {
        //获取分配记录ID、电脑SN码和员工工号
        Integer recordId = comEmpRecord.getRecordId();
        String comSn = comEmpRecord.getRecordComSn();
        Long employeeNum = comEmpRecord.getRecordEmpNum();

        //判断记录是否存在
        List<ComEmpRecord> records = comEmpRecordMapper.findComEmpRecordById(recordId);
        if (records.isEmpty()){
            return "对不起，并未发现此条记录，请您在审查一次！";
        }

        //判断记录的归还时间是否为空，不是则说明电脑已归还
        if (records.get(0).getReturnComTime() != null){
            return "对不起，此电脑已归还，请您刷新一下！";
        }

        //根据电脑SN码查出来的记录
        List<Computer> recordByComSn = computerMapper.findComByComSn(comSn);
        //如果为空，则返回提示信息
        if (recordByComSn.isEmpty()) {
            return "对不起，出现异常，此电脑并不存在，请联系专业人员，进行数据检查！";
        }

        try{
            //获取token
            String token = request.getHeader("token");
            //根据员工工号查出来的记录
            ResultVO<Map<String,Object>> res = remoteCallEmpUtils.findEmployeeById(employeeNum,token);
            //如果为空，则返回提示信息
            if (res.getCode() == 200 && res.getData() == null){
                return "对不起，并未发现该员工信息，请您再次核查！！";
            }else if (res.getCode() == 444){
                return "归还失败";
            }
        }catch (HttpClientErrorException e){
            return "归还失败";
        }

        //获取此记录的得到电脑时间
        Date getComTime = records.get(0).getGetComTime();
        //获取当前时间
        Date now = new Date();

        //归还电脑，更改分配记录的归还电脑时间
        boolean updateReturnComResult = comEmpRecordMapper.updateReturnComTime(recordId, now,now) > 0;

        //给电脑修改状态，将被拥有状态转为空闲
        boolean updateComStatusResult = computerMapper.updateComStatus(comSn, 0,now) > 0;

        //将电脑绑定的持有人工号改为null
        boolean updateHolderNumResult = computerMapper.updateHolderNum(comSn, null,now) > 0;

        //员工电脑数-1(员工工号，拥有电脑总数)
        //int updateOwnComSumResult = employeeMapper.updateOwnComSum(employee.getEmployeeNum(),employee.getOwnComSum() - 1);

        //修改此次操作对应历史记录的归还电脑时间
        boolean updateHistoricalResult = comHistoricalRecordService.updateHistoricalReturnComTime(comSn, employeeNum,getComTime,now) > 0;

        boolean result = updateReturnComResult && updateComStatusResult && updateHolderNumResult && updateHistoricalResult;

        return  result ? "归还成功":"归还失败";
    }
}
