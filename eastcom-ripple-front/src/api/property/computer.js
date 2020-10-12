import { requestAsset } from '@/utils/request.js'

export default {
  // 电脑列表
  getComputer(query) {
    return requestAsset({
      url: '/asset/computer/info/searchComByCondition',
      method: 'post',
      data:query
    });
  },
  // 添加电脑
  addComputer(query) {
    return requestAsset({
      url: '/asset/computer/info/addComputer',
      method: 'post',
      data:query
    });
  },
  // 修改电脑
  updateComputer(query) {
    return requestAsset({
      url: '/asset/computer/info/modifyComputer',
      method: 'post',
      data:query
    });
  },
  // 删除电脑
  deleteComputer(comSn) {
    return requestAsset({
      url: '/asset/computer/info/deleteComputer',
      method: 'post',
      data:{ comSn: comSn }
    });
  },

  /**
   * 电脑发还管理
   */
  // 查找电脑分配记录
  getAssignComRecord(query) {
    return requestAsset({
      url: '/asset/computer/management/searchAssignRecord',
      method: 'post',
      data:query
    });
  },
  //分配电脑
  assignComputer(query) {
    return requestAsset({
      url: '/asset/computer/management/assignComputer',
      method: 'post',
      data:query
    });
  },
  //归还电脑
  returnComputer(query) {
    return requestAsset({
      url: '/asset/computer/management/returnComputer',
      method: 'post',
      data:query
    });
  },
  /**
   * 电脑分配历史记录
   */
  // 电脑列表
  getHistoricalRecord(query) {
    return requestAsset({
      url: '/asset/computer/history/searchHistoryByCondition',
      method: 'post',
      data: query
    });
  },
  exportToCsv(query){
    return requestAsset({
      url: '/asset/computer/history/exportDataToCsv/'+query.currentPage+'/'+query.pageSize+'?keyword='+query.keyword,
      method: 'get',
      // params: query.keyword
      responseType: 'arraybuffer'
    });
  }
}
