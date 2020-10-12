import { requestAsset } from '@/utils/request.js'

export default {
    // 资产类型
    getAssetType() {
        return requestAsset({
            url: '/asset/type/getAssetTypeList',
            method: 'get',
        });
    },
    // 资产列表
    getAsset(query) {
        return requestAsset({
            url: '/asset/management/info/searchAssetByCondition',
            method: 'post',
            data:query
        });
    },
    // 添加资产
    addAsset(query) {
        return requestAsset({
            url: '/asset/management/info/addAsset',
            method: 'post',
            data:query
        });
    },
    // 修改资产
    updateAsset(query) {
        return requestAsset({
            url: '/asset/management/info/modifyAsset',
            method: 'post',
            data:query
        });
    },
    // 删除资产
    deleteAsset(assetId) {
      return requestAsset({
        url: '/asset/management/info/deleteAsset',
        method: 'post',
        data:{ id: assetId }
      });
    }
}
