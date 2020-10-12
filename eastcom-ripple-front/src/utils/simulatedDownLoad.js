/**
 * 解决axios请求下载，没有弹出下载框的问题
 * @param res
 */
export function createHyperlinkLabel(res) {
  var disposition = res.headers['content-disposition'];
  //console.log(disposition);
  //获取默认下载文件名
  var fileName = decodeURI(disposition.substring(disposition.indexOf('fileName=') + 9, disposition.length));
  let blob = new Blob([res.data], { type: 'text/csv' }); // 假设文件为csv
  let link = document.createElement('a');
  var url = window.URL.createObjectURL(blob);
  link.href = url;
  link.download = fileName;
  link.click();
  //移除标签
  link.remove();
  //释放创建的URL
  window.URL.revokeObjectURL(url);
}
