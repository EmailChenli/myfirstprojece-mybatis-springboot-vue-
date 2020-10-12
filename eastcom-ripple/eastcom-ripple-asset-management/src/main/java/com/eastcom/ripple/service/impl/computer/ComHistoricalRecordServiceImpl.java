package com.eastcom.ripple.service.impl.computer;

import com.eastcom.ripple.common.util.ResultUtils;
import com.eastcom.ripple.common.vo.ResultVO;
import com.eastcom.ripple.entity.computer.ComHistoricalRecord;
import com.eastcom.ripple.mapper.computer.ComHistoricalRecordMapper;
import com.eastcom.ripple.service.computer.ComHistoricalRecordService;
import com.eastcom.ripple.util.CsvExportUtil;
import com.eastcom.ripple.vo.AssignComQueryVO;
import com.eastcom.ripple.vo.ComHistoricalRecordVO;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Hongzh
 * @date 2020/09/15
 * @description
 */
@Service
public class ComHistoricalRecordServiceImpl implements ComHistoricalRecordService {

    //引入持久层对象
    @Resource
    private ComHistoricalRecordMapper historicalRecordMapper;

    /**
     * 根据条件查询电脑分配历史记录信息
     * @param keyword
     * @return
     */
    @Override
    public List<ComHistoricalRecordVO> findHistoryByCondition(String keyword) {
        //根据关键字进行查询电脑分配历史记录
        return historicalRecordMapper.findHistoryByCondition(keyword);
    }

    /**
     * 添加电脑分配历史记录
     * @param queryVO
     * @param assetNum
     * @param employeeName
     * @return
     */
    @Transactional
    @Override
    public int insertComHistoricalRecord(AssignComQueryVO queryVO, String assetNum, String employeeName) {
        //声明一个历史纪录对象，对其进行赋值
        ComHistoricalRecord historicalRecord = new ComHistoricalRecord();
        historicalRecord.setHistoricalComSn(queryVO.getComSn());
        historicalRecord.setHistoricalAssetNum(assetNum);
        historicalRecord.setHistoricalEmpNum(queryVO.getEmployeeNum());
        historicalRecord.setHistoricalEmpName(employeeName);
        historicalRecord.setGetComTime(queryVO.getGetComTime());
        historicalRecord.setCreateTime(queryVO.getCreateTime());
        historicalRecord.setUpdateTime(queryVO.getUpdateTime());
        System.out.println(historicalRecord);
        //添加电脑分配历史记录
        return historicalRecordMapper.insertComHistoricalRecord(historicalRecord);
    }

    /**
     * 根据历史记录ID更新对应历史纪录归还电脑的时间
     * @param comSn
     * @param employeeNum
     * @param getComTime
     * @return
     */
    @Transactional
    @Override
    public int updateHistoricalReturnComTime(String comSn, Long employeeNum, Date getComTime,Date currentTime) {
        //先查出此次操作在历史纪录中对应的记录
        Integer historicalId = historicalRecordMapper.findHistoricalId(comSn,employeeNum,getComTime);
        //根据历史记录id，修改历史纪录对应记录的归还电脑时间
        return historicalRecordMapper.updateReturnComTime(historicalId,currentTime,currentTime);
    }

    /**
     * 根据条件查询数据，并导出表格到Csv文件
     * @param currentPage
     * @param pageSize
     * @param keyword
     * @param response
     * @return
     */
    @Override
    public ResultVO exportDataToCsv(Integer currentPage, Integer pageSize, String keyword, HttpServletResponse response) {
        //如果当前页和每页记录数不为空，则按分页进行查询数据
        if (currentPage > 0 && pageSize > 0){
            //准备查询 第X页 的pageSize条数据
            PageHelper.startPage(currentPage,pageSize);
        }
        //获取电脑分配历史记录数据
        List<ComHistoricalRecordVO> historicalRecordsVO = historicalRecordMapper.findHistoryByCondition(keyword);
        if (historicalRecordsVO == null){
            return ResultUtils.error("未发现数据，导出失败");
        }
        //进行导出操作
            //声明一个存储导出状态的布尔变量
            boolean exportStatus = false;
            //设置文件名
            String fileName = "电脑历史记录_";
            //设置表头
            String[] head = {"序号","电脑SN码","资产编号","工号","员工姓名","得到电脑时间","归还电脑时间"};
            //声明用于存储表格数据的集合
            List<Object[]> values = new ArrayList<>();
            for (ComHistoricalRecordVO record : historicalRecordsVO) {
                //将每一条记录转换为字符串数组，并存入表格数据的集合中
                values.add(record.toStringArray());
            }
            try {
                //设置响应请求头，并将文件名返回
                fileName = CsvExportUtil.responseSetProperties(fileName,response);
                //创建CSV文件，写入内容
                File file = CsvExportUtil.makeTempCSV(fileName, head, values);
                //下载文件，响应结果
                exportStatus = CsvExportUtil.downloadFile(response, file);
            } catch (IOException e) {
                e.printStackTrace();
                return ResultUtils.error("出现异常，导出失败");
            }
        //成功则返回null即可
        return exportStatus ? null:ResultUtils.error("出现异常，导出失败");
    }
}
