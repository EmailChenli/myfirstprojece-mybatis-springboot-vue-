package com.eastcom.ripple.controller.computer;

import com.eastcom.ripple.common.util.ResultUtils;
import com.eastcom.ripple.common.vo.ResultVO;
import com.eastcom.ripple.service.computer.ComHistoricalRecordService;
import com.eastcom.ripple.vo.BaseSearchQueryVO;
import com.eastcom.ripple.vo.ComHistoricalRecordVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author Hongzh
 * @date 2020/09/23
 * @description 电脑历史控制器
 */
@Controller
@RequestMapping("/computer/history")
@Slf4j
public class ComHistoricalController {
    //引入业务层对象
    @Resource
    private ComHistoricalRecordService comHistoricalRecordService;

    /**
     * 查看所有电脑分配历史记录
     * @param queryVO 基础参数包装类
     * @return
     */
    @PostMapping("/searchHistoryByCondition")
    @ResponseBody
    public ResultVO searchHistoryByCondition(@RequestBody BaseSearchQueryVO queryVO){
        log.info("查询电脑分配历史记录的参数："+queryVO);
        PageInfo<ComHistoricalRecordVO> pageInfo = null;
        //准备查询 第X页 的pageSize条数据
        PageHelper.startPage(queryVO.getCurrentPage(),queryVO.getPageSize());
        try {
            //获取电脑分配历史记录数据
            List<ComHistoricalRecordVO> historicalRecordsVO = comHistoricalRecordService.findHistoryByCondition(queryVO.getKeyword());
            if (historicalRecordsVO == null){
                return ResultUtils.error("查询失败");
            }
            //将查询结果放入分页控件中
            pageInfo = new PageInfo<>(historicalRecordsVO);
        }finally {
            PageHelper.clearPage(); //清理 ThreadLocal 存储的分页参数,保证线程安全
        }
        return ResultUtils.success("查询成功",pageInfo);
    }

    /**
     * 根据条件查询数据，并导出表格到Csv文件
     * @param currentPage
     * @param pageSize
     * @param keyword
     * @param response
     * @return
     */
    @GetMapping("/exportDataToCsv/{currentPage}/{pageSize}")
    @ResponseBody
    public ResultVO exportDataToCsv(@PathVariable("currentPage") Integer currentPage, @PathVariable("pageSize") Integer pageSize,@RequestParam("keyword") String keyword, HttpServletResponse response){
        log.info("导出电脑分配历史记录的参数："+currentPage+"==="+pageSize+"===="+keyword);
        return comHistoricalRecordService.exportDataToCsv(currentPage,pageSize,keyword,response);
    }
}
