package com.eastcom.ripple.controller.asset;

import com.eastcom.ripple.common.util.ResultUtils;
import com.eastcom.ripple.common.vo.ResultVO;
import com.eastcom.ripple.service.asset.AssetTypeService;
import com.eastcom.ripple.vo.AssetTypeVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Hongzh
 * @date 2020/09/16
 * @description 资产类型控制器
 */
@RequestMapping("/type")
@Controller
@Slf4j
public class AssetTypeController {

    //引入业务层对象
    @Resource
    private AssetTypeService assetTypeService;

    /**
     * 获取所有资产类型
     * @return
     */
    @GetMapping("/getAssetTypeList")
    @ResponseBody
    public ResultVO getAssetTypeList(){
        List<AssetTypeVO> allAssetType = assetTypeService.findAllAssetType();
        log.info("资产类型的查询结果："+allAssetType);
        if (allAssetType != null){
            return ResultUtils.success("查询成功",allAssetType);
        }else {
            return ResultUtils.error("查询失败");
        }
    }
}
