package com.eastcom.ripple.controller.computer;

import com.eastcom.ripple.common.util.ResultUtils;
import com.eastcom.ripple.common.vo.ResultVO;
import com.eastcom.ripple.entity.computer.Computer;
import com.eastcom.ripple.service.computer.ComputerService;
import com.eastcom.ripple.vo.ComputerQueryVO;
import com.eastcom.ripple.vo.ComputerVO;
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
 * @date 2020/09/10
 * @description 电脑控制器
 */
@Controller
@RequestMapping("/computer/info")
@Slf4j
public class ComputerController {

    //引入业务层对象
    @Resource
    private ComputerService computerService;

    /**
     * 根据条件进行查询电脑信息
     * @param queryVO 查询电脑信息的条件参数包装类
     * @return
     */
    @PostMapping("/searchComByCondition")
    @ResponseBody
    public ResultVO searchComByCondition(@RequestBody ComputerQueryVO queryVO, HttpServletRequest request){
        log.info("查询电脑信息的请求参数："+queryVO);
        PageInfo<ComputerVO> pageInfo = null;
        //准备查询 第X页 的pageSize条数据
        PageHelper.startPage(queryVO.getCurrentPage(),queryVO.getPageSize());
        try {
            //获取电脑信息数据
            List<ComputerVO> computersVO = computerService.findComByCondition(queryVO.getKeyword(), queryVO.getComStatus(),request);
            if (computersVO == null){
                return ResultUtils.error("查询失败");
            }
            //将查询结果放入分页控件中
            pageInfo = new PageInfo<>(computersVO);
        }finally {
            PageHelper.clearPage(); //清理 ThreadLocal 存储的分页参数,保证线程安全
        }
        return ResultUtils.success("查询成功",pageInfo);
    }

    /**
     * 添加电脑
     * @param computer
     * @return
     */
    @PostMapping("/addComputer")
    @ResponseBody
    public ResultVO addComputer(@RequestBody Computer computer){
        log.info("请求参数："+computer);
        //进行插入操作
        String msg = computerService.insertComputer(computer);
        //如果msg是添加失败，则返回错误方法
        if (msg.equals("添加失败")){
            return ResultUtils.error(msg);
        }
        //默认返回成功方法
        return ResultUtils.success(msg,null);
    }

    /**
     * 修改电脑信息
     * @param computer
     * @return
     */
    @PostMapping("/modifyComputer")
    @ResponseBody
    public ResultVO modifyComputer(@RequestBody Computer computer){
        log.info("请求参数："+computer);
        //进行插入操作
        String msg = computerService.updateComputer(computer);
        //如果msg是更新失败，则返回错误方法
        if (msg.equals("更新失败")){
            return ResultUtils.error(msg);
        }
        //默认返回成功方法
        return ResultUtils.success(msg,null);
    }

    /**
     * 删除电脑
     * @param computer
     * @return
     */
    @PostMapping("/deleteComputer")
    @ResponseBody
    public ResultVO deleteComputer(@RequestBody Computer computer){
        log.info("\n请求参数："+computer.getComSn());
        //进行插入操作
        String msg = computerService.deleteComputer(computer.getComSn());
        //如果msg是删除失败，则返回错误方法
        if (msg.equals("删除失败")){
            return ResultUtils.error(msg);
        }
        //默认返回成功方法
        return ResultUtils.success(msg,null);
    }
}
