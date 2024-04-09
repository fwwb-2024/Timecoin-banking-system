const { defineConfig } = require('@vue/cli-service')
const path = require("path");
module.exports = defineConfig({
  transpileDependencies: true
})

module.exports = {
  assetsDir: 'static',
  parallel: false,
  productionSourceMap: false,
  publicPath: './',
  configureWebpack: {
    resolve: {
      alias: {
        '@': path.resolve(__dirname,'src')
      }
    }
  }
};
