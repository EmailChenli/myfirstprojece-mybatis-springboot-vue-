package com.eastcom.ripple.controller.asset;

import com.eastcom.ripple.common.util.ResultUtils;
import com.eastcom.ripple.common.vo.ResultVO;
import com.eastcom.ripple.dto.AssetDTO;
import com.eastcom.ripple.entity.asset.Asset;
import com.eastcom.ripple.service.asset.AssetService;
import com.eastcom.ripple.vo.AssetQueryVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Hongzh
 * @date 2020/09/11
 * @description 资产控制器
 */
@Controller
@RequestMapping("/management/info")
@Slf4j
public class AssetController {

    //注入业务层对象
    @Resource
    private AssetService assetService;

    /**
     * 根据条件进行查询资产
     * @param queryVO 查询资产信息的条件参数包装类
     * @return
     */
    @PostMapping("/searchAssetByCondition")
    @ResponseBody
    public ResultVO searchAssetByCondition(@RequestBody AssetQueryVO queryVO){
        log.info("查询资产信息的请求参数："+queryVO);
        PageInfo<AssetDTO> pageInfo = null;
        //准备查询 第X页 的pageSize条数据
        PageHelper.startPage(queryVO.getCurrentPage(),queryVO.getPageSize());
        try {
            //获取资产信息数据
            List<AssetDTO> assetInfo = assetService.findAssetByCondition(queryVO.getKeyword(), queryVO.getAssetType());
            if (assetInfo == null){
                return ResultUtils.error("查询失败");
            }
            //将查询结果放入分页控件中
            pageInfo = new PageInfo<>(assetInfo);
        }finally {
            PageHelper.clearPage(); //清理 ThreadLocal 存储的分页参数,保证线程安全
        }
        return ResultUtils.success("查询成功",pageInfo);
    }

    /**
     * 添加资产
     * @param asset
     * @return
     */
    @PostMapping("/addAsset")
    @ResponseBody
    public ResultVO addAsset(@RequestBody Asset asset){
        log.info("\n"+"要添加的资产信息："+asset);
        //获取返回信息
        String msg = assetService.insertAsset(asset);
        //如果msg是添加失败，则返回错误方法
        if (msg.equals("添加失败")){
            return ResultUtils.error(msg);
        }
        //默认返回成功方法
        return ResultUtils.success(msg,null);
    }

    /**
     * 修改资产信息
     * @param asset
     * @return
     */
    @PostMapping("/modifyAsset")
    @ResponseBody
    public ResultVO modifyAsset(@RequestBody Asset asset){
        log.info("\n"+"要修改的资产信息："+asset);
        //获取返回信息
        String msg = assetService.updateAsset(asset);
        //如果msg是更新失败，则返回错误方法
        if (msg.equals("更新失败")){
            return ResultUtils.error(msg);
        }
        //默认返回成功方法
        return ResultUtils.success(msg,null);
    }

    /**
     * 删除资产信息
     * @param asset
     * @return
     */
    @PostMapping("/deleteAsset")
    @ResponseBody
    public ResultVO deleteAsset(@RequestBody Asset asset){
        log.info("\n"+"要删除的资产ID："+asset.getId());
        //获取返回信息
        String msg = assetService.deleteAsset(asset.getId());
        //如果msg是删除失败，则返回错误方法
        if (msg.equals("删除失败")){
            return ResultUtils.error(msg);
        }
        //默认返回成功方法
        return ResultUtils.success(msg,null);
    }
}
