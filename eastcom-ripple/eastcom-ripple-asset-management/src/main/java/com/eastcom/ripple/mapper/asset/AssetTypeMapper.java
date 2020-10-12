package com.eastcom.ripple.mapper.asset;

import com.eastcom.ripple.vo.AssetTypeVO;

import java.util.List;

/**
 * @author Hongzh
 * @date 2020/09/11
 * @description 持久层：对资产类型表进行各种操作
 */
public interface AssetTypeMapper {

    /**
     * 查找所有资产类型
     * @return
     */
    List<AssetTypeVO> findAllAssetType();

    /**
     * 根据资产类型名称获取类型ID
     * @param assetTypeName
     * @return
     */
    Integer findIdByAssetTypeName(String assetTypeName);
}
