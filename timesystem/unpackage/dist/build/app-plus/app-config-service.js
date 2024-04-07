
var isReady=false;var onReadyCallbacks=[];
var isServiceReady=false;var onServiceReadyCallbacks=[];
var __uniConfig = {"pages":["pages/news/news","pages/news/newsDetail","pages/news/missionList","pages/index/index","pages/mymission/myMission","pages/mymission/newMission","pages/personal/personal","pages/mymission/historyTask","pages/index/search","pages/index/searchList","pages/personal/aboutUs","pages/personal/accountData","pages/personal/familyDetail","pages/personal/familyList","pages/personal/timeCoinData","pages/personal/coinsLedgers","pages/login","pages/missionDetail","pages/mymission/nowmission","pages/mymission/historymission","pages/mymission/changemission"],"window":{"navigationBarTextStyle":"black","navigationBarTitleText":"时间银行系统","navigationBarBackgroundColor":"#F8F8F8","backgroundColor":"#F8F8F8","background":"#efeff4","scrollIndicator":"none"},"tabBar":{"color":"#000000","selectedColor":"#e16531","borderStyle":"black","backgroundColor":"#ffffff","list":[{"pagePath":"pages/news/news","iconPath":"static/buttompicture/news.png","selectedIconPath":"static/buttompicture/selectnews.png","text":"首页资讯"},{"pagePath":"pages/index/index","iconPath":"static/buttompicture/buttompic1.png","selectedIconPath":"static/buttompicture/selectbuttompic1.png","text":"任务大厅"},{"pagePath":"pages/mymission/myMission","iconPath":"static/buttompicture/buttompic2.png","selectedIconPath":"static/buttompicture/selectbuttompic2.png","text":"我的任务"},{"pagePath":"pages/personal/personal","iconPath":"static/buttompicture/buttompic3.png","selectedIconPath":"static/buttompicture/selectbuttompic3.png","text":"个人中心"}]},"darkmode":false,"nvueCompiler":"uni-app","nvueStyleCompiler":"uni-app","renderer":"auto","splashscreen":{"alwaysShowBeforeRender":true,"autoclose":false},"appname":"timesystem","compilerVersion":"3.99","entryPagePath":"pages/news/news","networkTimeout":{"request":60000,"connectSocket":60000,"uploadFile":60000,"downloadFile":60000}};
var __uniRoutes = [{"path":"/pages/news/news","meta":{"isQuit":true,"isTabBar":true},"window":{"navigationBarTitleText":"首页资讯","enablePullDownRefresh":true,"titleNView":false,"pullToRefresh":{"color":"#e6e6e6","offset":"295px","height":"50px","range":"100px"}}},{"path":"/pages/news/newsDetail","meta":{},"window":{"navigationBarTitleText":"首页资讯","titleNView":false}},{"path":"/pages/news/missionList","meta":{},"window":{"navigationBarTitleText":"分类任务列表","enablePullDownRefresh":true,"titleNView":false,"pullToRefresh":{"color":"#e6e6e6","offset":"295px","height":"50px","range":"100px"}}},{"path":"/pages/index/index","meta":{"isQuit":true,"isTabBar":true},"window":{"navigationBarTitleText":"任务大厅","enablePullDownRefresh":true,"titleNView":false,"pullToRefresh":{"color":"#e6e6e6","offset":"180px","height":"50px","range":"100px"}}},{"path":"/pages/mymission/myMission","meta":{"isQuit":true,"isTabBar":true},"window":{"navigationBarTitleText":"我的任务","enablePullDownRefresh":true,"titleNView":false,"pullToRefresh":{"color":"#e6e6e6","offset":"90px","height":"50px","range":"100px"}}},{"path":"/pages/mymission/newMission","meta":{},"window":{"navigationBarTitleText":"新增任务","titleNView":false}},{"path":"/pages/personal/personal","meta":{"isQuit":true,"isTabBar":true},"window":{"navigationBarTitleText":"个人中心","titleNView":false}},{"path":"/pages/mymission/historyTask","meta":{},"window":{"navigationBarTitleText":"历史任务","enablePullDownRefresh":true,"titleNView":false,"pullToRefresh":{"color":"#e6e6e6","offset":"70px","height":"50px","range":"100px"}}},{"path":"/pages/index/search","meta":{},"window":{"navigationBarTitleText":"搜索","titleNView":false}},{"path":"/pages/index/searchList","meta":{},"window":{"navigationBarTitleText":"搜索","titleNView":false}},{"path":"/pages/personal/aboutUs","meta":{},"window":{"navigationBarTitleText":"关于我们","titleNView":false}},{"path":"/pages/personal/accountData","meta":{},"window":{"navigationBarTitleText":"账号信息","titleNView":false}},{"path":"/pages/personal/familyDetail","meta":{},"window":{"navigationBarTitleText":"家庭详情","titleNView":false}},{"path":"/pages/personal/familyList","meta":{},"window":{"navigationBarTitleText":"家庭管理","titleNView":false}},{"path":"/pages/personal/timeCoinData","meta":{},"window":{"navigationBarTitleText":"时间币管理","titleNView":false}},{"path":"/pages/personal/coinsLedgers","meta":{},"window":{"navigationBarTitleText":"时间币账单","enablePullDownRefresh":true,"titleNView":false,"pullToRefresh":{"color":"#e6e6e6","offset":"90px","height":"50px","range":"100px"}}},{"path":"/pages/login","meta":{},"window":{"navigationBarTitleText":"登录","titleNView":false}},{"path":"/pages/missionDetail","meta":{},"window":{"navigationBarTitleText":"任务详情","titleNView":false}},{"path":"/pages/mymission/nowmission","meta":{},"window":{"navigationBarTitleText":"当前任务","titleNView":false}},{"path":"/pages/mymission/historymission","meta":{},"window":{"navigationBarTitleText":"历史任务","titleNView":false}},{"path":"/pages/mymission/changemission","meta":{},"window":{"navigationBarTitleText":"修改任务","titleNView":false}}];
__uniConfig.onReady=function(callback){if(__uniConfig.ready){callback()}else{onReadyCallbacks.push(callback)}};Object.defineProperty(__uniConfig,"ready",{get:function(){return isReady},set:function(val){isReady=val;if(!isReady){return}const callbacks=onReadyCallbacks.slice(0);onReadyCallbacks.length=0;callbacks.forEach(function(callback){callback()})}});
__uniConfig.onServiceReady=function(callback){if(__uniConfig.serviceReady){callback()}else{onServiceReadyCallbacks.push(callback)}};Object.defineProperty(__uniConfig,"serviceReady",{get:function(){return isServiceReady},set:function(val){isServiceReady=val;if(!isServiceReady){return}const callbacks=onServiceReadyCallbacks.slice(0);onServiceReadyCallbacks.length=0;callbacks.forEach(function(callback){callback()})}});
service.register("uni-app-config",{create(a,b,c){if(!__uniConfig.viewport){var d=b.weex.config.env.scale,e=b.weex.config.env.deviceWidth,f=Math.ceil(e/d);Object.assign(__uniConfig,{viewport:f,defaultFontSize:Math.round(f/20)})}return{instance:{__uniConfig:__uniConfig,__uniRoutes:__uniRoutes,global:void 0,window:void 0,document:void 0,frames:void 0,self:void 0,location:void 0,navigator:void 0,localStorage:void 0,history:void 0,Caches:void 0,screen:void 0,alert:void 0,confirm:void 0,prompt:void 0,fetch:void 0,XMLHttpRequest:void 0,WebSocket:void 0,webkit:void 0,print:void 0}}}});
