package com.eastcom.ripple.service.impl.asset;

import com.eastcom.ripple.mapper.asset.AssetTypeMapper;
import com.eastcom.ripple.service.asset.AssetTypeService;
import com.eastcom.ripple.vo.AssetTypeVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Hongzh
 * @date 2020/09/16
 * @description
 */
@Service
public class AssetTypeServiceImpl implements AssetTypeService {

    //引入持久层对象
    @Resource
    private AssetTypeMapper assetTypeMapper;

    /**
     * 查找所有资产类型
     * @return
     */
    @Override
    public List<AssetTypeVO> findAllAssetType() {
        return assetTypeMapper.findAllAssetType();
    }
}
