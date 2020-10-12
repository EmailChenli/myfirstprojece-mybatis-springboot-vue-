package com.eastcom.ripple.controller.computer;

import com.eastcom.ripple.common.util.ResultUtils;
import com.eastcom.ripple.common.vo.ResultVO;
import com.eastcom.ripple.entity.computer.ComEmpRecord;
import com.eastcom.ripple.service.computer.ComAssignService;
import com.eastcom.ripple.vo.AssignComQueryVO;
import com.eastcom.ripple.vo.AssignComVO;
import com.eastcom.ripple.vo.BaseSearchQueryVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Hongzh
 * @date 2020/09/15
 * @description 电脑分配控制器
 */
@RequestMapping("/computer/management")
@Controller
@Slf4j
public class ComAssignController {

    //引入业务层对象
    @Resource
    private ComAssignService comAssignService;

    /**
     * 查看所有电脑分配记录
     * @param queryVO 基础参数包装类
     * @return
     */
    @PostMapping("/searchAssignRecord")
    @ResponseBody
    public ResultVO searchAssignRecord(@RequestBody BaseSearchQueryVO queryVO){
        log.info("查询电脑分配记录的参数："+queryVO);
        PageInfo<AssignComVO> pageInfo = null;
        //准备查询 第X页 的pageSize条数据
        PageHelper.startPage(queryVO.getCurrentPage(),queryVO.getPageSize());
        try {
            //获取电脑分配记录数据
            List<AssignComVO> assignComRecords = comAssignService.findAllAssignComRecord();
            if (assignComRecords == null){
                return ResultUtils.error("查询失败");
            }
            //将查询结果放入分页控件中
            pageInfo = new PageInfo<>(assignComRecords);
        }finally {
            PageHelper.clearPage(); //清理 ThreadLocal 存储的分页参数,保证线程安全
        }
        return ResultUtils.success("查询成功",pageInfo);
    }


    /**
     * 分配电脑
     * @param query  参数
     * @return
     */
    @PostMapping("/assignComputer")
    @ResponseBody
    public ResultVO assignComputer(@RequestBody AssignComQueryVO query,  HttpServletRequest request){
        log.info("分配电脑的请求参数："+query);
        //进行插入操作
        String msg = comAssignService.assignComputer(query,request);
        //如果msg是分配失败，则返回错误方法
        if (msg.equals("分配失败")){
            return ResultUtils.error(msg);
        }
        //默认返回成功方法
        return ResultUtils.success(msg,null);
    }

    /**
     * 归还电脑
     * @param comEmpRecord
     * @return
     */
    @PostMapping("/returnComputer")
    @ResponseBody
    public ResultVO returnComputer(@RequestBody ComEmpRecord comEmpRecord, HttpServletRequest request){
        log.info("归还电脑的请求参数："+comEmpRecord);
        //进行插入操作
        String msg = comAssignService.returnComputer(comEmpRecord,request);
        //如果msg是归还失败，则返回错误方法
        if (msg.equals("归还失败")){
            return ResultUtils.error(msg);
        }
        //默认返回成功方法
        return ResultUtils.success(msg,null);
    }
}
