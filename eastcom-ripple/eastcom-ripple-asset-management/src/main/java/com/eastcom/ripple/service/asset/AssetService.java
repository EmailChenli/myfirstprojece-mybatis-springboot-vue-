package com.eastcom.ripple.service.asset;

import com.eastcom.ripple.dto.AssetDTO;
import com.eastcom.ripple.entity.asset.Asset;

import java.util.List;

/**
 * @author Hongzh
 * @date 2020/09/11
 * @description 业务层接口：对资产进行业务处理
 */
public interface AssetService {

    /**
     * 根据条件查询资产信息
     * @param keyword 关键字
     * @param assetTypeId 资产类型ID
     * @return
     */
    List<AssetDTO> findAssetByCondition(String keyword, Integer assetTypeId);

    /**
     * 添加资产
     * @param asset  资产对象
     * @return
     */
    String insertAsset(Asset asset);

    /**
     * 更新资产信息
     * @param asset  资产对象
     * @return
     */
    String updateAsset(Asset asset);

    /**
     * 删除资产信息
     * @param id 资产ID
     * @retun
     * */
    String deleteAsset(Integer id);
}
