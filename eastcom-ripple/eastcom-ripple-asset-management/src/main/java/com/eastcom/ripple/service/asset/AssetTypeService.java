package com.eastcom.ripple.service.asset;

import com.eastcom.ripple.vo.AssetTypeVO;

import java.util.List;

/**
 * @author Hongzh
 * @date 2020/09/16
 * @description 业务层接口：对资产类型进行各种业务处理
 */
public interface AssetTypeService {
    /**
     * 查找所有资产类型
     * @return
     */
    List<AssetTypeVO> findAllAssetType();
}
