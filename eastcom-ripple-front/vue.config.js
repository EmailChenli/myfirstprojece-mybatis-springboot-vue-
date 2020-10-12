module.exports = {
  publicPath: '/',
  //将静态资源文件放到static
  assetsDir: 'static',
  // 将构建好的文件输出到哪里
  outputDir: 'dist',

  // 是否为生产环境构建生成 source map？
  productionSourceMap: false,

  devServer: {
    open: process.platform === 'darwin',
    host: '0.0.0.0',
    port: 9009,
    https: false,
    hotOnly: false,
    proxy: null, // 设置代理
    before: app => {}
  }
};
