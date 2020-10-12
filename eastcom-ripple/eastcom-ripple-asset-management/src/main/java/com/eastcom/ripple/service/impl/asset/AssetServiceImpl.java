package com.eastcom.ripple.service.impl.asset;

import com.eastcom.ripple.dto.AssetDTO;
import com.eastcom.ripple.entity.asset.Asset;
import com.eastcom.ripple.mapper.asset.AssetMapper;
import com.eastcom.ripple.mapper.asset.AssetTypeMapper;
import com.eastcom.ripple.service.asset.AssetService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author Hongzh
 * @date 2020/09/11
 * @description 资产管理业务层实现类
 */
@Service
public class AssetServiceImpl implements AssetService {

    //注入持久层对象
    @Resource
    private AssetMapper assetMapper;

    @Resource
    private AssetTypeMapper assetTypeMapper;

    /**
     * 根据条件查询资产信息
     * @param keyword 关键字
     * @param assetTypeId 资产类型ID
     * @return
     */
    @Override
    public List<AssetDTO> findAssetByCondition(String keyword, Integer assetTypeId) {
        //根据关键字和资产类别ID进行查询资产信息数据
        return assetMapper.findAssetByCondition(keyword, assetTypeId);
    }

    /**
     * 添加资产
     * @param asset  资产对象
     * @return
     */
    @Transactional
    @Override
    public String insertAsset(Asset asset) {
        //根据资产编号和资产类别id查询资产信息
        List<AssetDTO> assetRecord = assetMapper.findByAssetNumAndType(asset.getAssetNum(), asset.getAssetTypeId());
        // 判断这条信息是否有记录
        if (!assetRecord.isEmpty()){
            return "对不起，此条资产信息已存在，请您重新输入！！";
        }
        //获取当前时间
        Date now = new Date();
        //设置创建和更新时间
        asset.setCreateTime(now);
        asset.setUpdateTime(now);
        return assetMapper.insertAsset(asset)!=0 ? "添加成功":"添加失败";
    }

    /**
     * 更新资产信息
     * @param asset  资产对象
     * @return
     */
    @Transactional
    @Override
    public String updateAsset(Asset asset) {
        //根据资产ID查询资产信息
        Asset record = assetMapper.findAssetById(asset.getId());
        if (record == null){
            return "对不起，并未发现此条资产信息，请您重新刷新一下";
        }
        //获取当前时间
        Date now = new Date();
        //设置更新时间
        asset.setUpdateTime(now);
        return assetMapper.updateAsset(asset)!=0 ? "更新成功":"更新失败";
    }

    /**
     * 删除资产信息
     * @param id 资产ID
     * @return
     */
    @Transactional
    @Override
    public String deleteAsset(Integer id) {
        //根据资产ID查询资产信息
        Asset record = assetMapper.findAssetById(id);
        if (record == null){
            return "对不起，并未发现此条资产信息，请您重新刷新一下";
        }
        return assetMapper.deleteAsset(id)!=0 ? "删除成功":"删除失败";
    }
}
