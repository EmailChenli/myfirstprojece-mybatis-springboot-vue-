package com.eastcom.ripple.mapper.asset;

import com.eastcom.ripple.dto.AssetDTO;
import com.eastcom.ripple.entity.asset.Asset;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @author Hongzh
 * @date 2020/09/11
 * @description 持久层：对资产表进行各种操作
 */
public interface AssetMapper {

    /**
     * 根据条件查询资产信息
     * @param keyword
     * @param assetTypeId
     * @return
     */
    List<AssetDTO> findAssetByCondition(@Param("keyword") String keyword,@Param("assetTypeId") Integer assetTypeId);

    /**
     * 根据资产编号和资产类型查找资产记录
     * @param assetNum
     * @param assetType
     * @return
     */
    List<AssetDTO> findByAssetNumAndType(@Param("assetNum") String assetNum, @Param("assetType") Integer assetType);

    /**
     * 根据资产id查询资产信息
     * @param id
     * @return
     */
    AssetDTO findAssetById(Integer id);

    /**
     * 添加资产
     * @param asset
     * @return
     */
    int insertAsset(Asset asset);

    /**
     * 更新资产信息
     * @param asset
     * @return
     */
    int updateAsset(Asset asset);

    /**
     * 更新资产名称
     * @param id
     * @param assetName
     * @return
     */
    int updateAssetName(@Param("id") Integer id,@Param("assetName") String assetName,@Param("updateTime") Date updateTime);

    /**
     * 删除资产信息
     * @param id
     * @return
     */
    int deleteAsset(Integer id);
}
