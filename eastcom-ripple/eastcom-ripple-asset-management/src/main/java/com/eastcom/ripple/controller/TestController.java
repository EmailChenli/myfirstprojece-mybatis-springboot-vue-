package com.eastcom.ripple.controller;

import com.eastcom.ripple.common.util.ResultUtils;
import com.eastcom.ripple.common.vo.ResultVO;
import com.eastcom.ripple.service.computer.ComHistoricalRecordService;
import com.eastcom.ripple.util.CsvExportUtil;
import com.eastcom.ripple.vo.ComHistoricalRecordVO;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Hongzh
 * @date 2020/09/03
 * @description   测试环境是否配置成功
 */
@RestController
@Slf4j
@RequestMapping("/test")
public class TestController {

    //引入业务层对象
    @Resource
    private ComHistoricalRecordService comHistoricalRecordService;

    @ApiOperation(value = "测试环境配置",httpMethod = "GET")
    @ApiImplicitParam(name = "id", dataType="Long", required = true, paramType="path")
    @GetMapping("/testEnvironment/{id}")
    public ResultVO testEnvironment(@PathVariable("id") Long id){
        log.info("测试环境接口 start...");
        log.info("参数为："+id);
        return ResultUtils.success("测试成功，可以放心进行开发！！");
    }

    @PostMapping("/testList")
    public ResultVO testList(@RequestBody List<Long> ids){
        log.info("测试接收List接口 start...");
        log.info("参数为："+ids);
        Long i = ids.get(0);
        System.out.println(i);
        return ResultUtils.success("测试成功，可以放心进行开发！！");
    }

    @GetMapping("/downloadAll")
    public String testDownLoadCSV(HttpServletResponse response){
        String[] head = {"序号","电脑SN码","资产编号","工号","员工姓名","得到电脑时间","归还电脑时间"};
        List<ComHistoricalRecordVO> historicalRecordsVO = comHistoricalRecordService.findHistoryByCondition("");
        List<Object[]> values = new ArrayList<>();
        for (ComHistoricalRecordVO record : historicalRecordsVO) {
            String[] value = record.toStringArray();
            //log.info("======"+ Arrays.toString(value));
            values.add(value);
        }
        log.info("======"+values);
        //设置文件名
        String fileName = "电脑历史记录_";
        try {
            fileName = CsvExportUtil.responseSetProperties(fileName,response);
            File file = CsvExportUtil.makeTempCSV(fileName, head, values);
            CsvExportUtil.downloadFile(response, file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
