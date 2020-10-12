package com.eastcom.ripple.common.util;

import com.eastcom.ripple.common.vo.ResultCode;
import com.eastcom.ripple.common.vo.ResultVO;

/**
 * @author Chench
 * @date 2020/8/25 9:30
 * @description 返回统一结果的工具类
 */
public class ResultUtils {

    /**
     * 成功返回数据
     * @param data 响应数据
     * @return
     */
    public static ResultVO success(Object data){
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(ResultCode.SUCCESS);
        resultVO.setMessage("操作成功");
        resultVO.setData(data);
        return resultVO;
    }

    /**
     * 成功，自定义信息带返回数据
     * @param msg 自定义信息
     * @param data 响应数据
     * @return
     */
    public static ResultVO success(String msg,Object data){
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(ResultCode.SUCCESS);
        resultVO.setMessage(msg);
        resultVO.setData(data);
        return resultVO;
    }
    /**
     * 失败不带返回数据
     * @return
     */
    public static ResultVO error(){
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(ResultCode.ERROR);
        resultVO.setMessage("操作失败");
        resultVO.setData(null);
        return resultVO;
    }

    /**
     * 失败，返回自定义信息
     * @param msg 自定义信息
     * @return
     */
    public static ResultVO error(String msg){
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(ResultCode.ERROR);
        resultVO.setMessage(msg);
        resultVO.setData(null);
        return resultVO;
    }
}
