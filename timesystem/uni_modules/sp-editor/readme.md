# sp-editor

## 使用方式

```
<template>
  <view class="home">
    <view class="editor-box">
      <sp-editor 
        :toolbar-config="{
          excludeKeys: ['direction', 'date', 'lineHeight', 'letterSpacing', 'listCheck'],
          iconSize: '18px'
        }"
        @init="initEditor"
        @input="inputOver" 
        @upinImage="upinImage" 
        @overMax="overMax"
        @addLink="addLink"
        @exportHtml="exportHtml"
        ></sp-editor>
    </view>
  </view>
</template>

<script>
export default {
  data() {
    return {
      editorIns: null
    }
  },
  methods: {
    /**
     * 获取输入内容
     * @param {Object} e {html,text} 内容的html文本，和text文本
     */
    inputOver(e) {
      // 可以在此处获取到编辑器已编辑的内容
      console.log('==== inputOver :', e)
    },
    /**
     * 超出最大内容限制
     * @param {Object} e {html,text} 内容的html文本，和text文本
     */
    overMax(e) {
      // 若设置了最大字数限制，可在此处触发超出限制的回调
      console.log('==== overMax :', e)
    },
    /**
     * 编辑器就绪
     * @param {Object} editor 编辑器实例，你可以自定义调用editor实例的方法
     * @tutorial editor组件 https://uniapp.dcloud.net.cn/component/editor.html#editor-%E7%BB%84%E4%BB%B6
     * @tutorial 相关api https://uniapp.dcloud.net.cn/api/media/editor-context.html
     */
    initEditor(editor) {
      this.editorIns = editor // 保存编辑器实例
      // 保存编辑器实例后，可以在此处获取后端数据，并赋值给编辑器初始化内容
      this.preRender()
    },
    preRender() {
      setTimeout(() => {
        // 异步获取后端数据后，初始化编辑器内容
        this.editorIns.setContents({
          html: `<div>    猫猫<img src="https://img.yzcdn.cn/vant/cat.jpeg"/>
                <img src="https://img.yzcdn.cn/vant/cat.jpeg"/>
                <img src="https://img.yzcdn.cn/vant/cat.jpeg"/>yaho giao</div>`
        })
      }, 1000)
    },
    /**
     * 直接运行示例工程插入图片无法正常显示的看这里
     * 因为插件默认采用云端存储图片的方式
     * 以$emit('upinImage', tempFiles, this.editorCtx)的方式回调
     * @param {Object} tempFiles
     * @param {Object} editorCtx
     */
    upinImage(tempFiles, editorCtx) {
      /**
       * 本地临时插入图片预览
       * 注意：这里仅是示例本地图片预览，因为需要将图片先上传到云端，再将图片插入到编辑器中
       * 正式开发时，还请将此处注释，并解开下面 使用 uniCloud.uploadFile 上传图片的示例方法 的注释
       * @tutorial https://uniapp.dcloud.net.cn/api/media/editor-context.html#editorcontext-insertimage
       */
      // #ifdef MP-WEIXIN
      // 注意微信小程序的图片路径是在tempFilePath字段中
      editorCtx.insertImage({
        src: tempFiles[0].tempFilePath,
        width: '80%', // 默认不建议铺满宽度100%，预留一点空隙以便用户编辑
        success: function () {}
      })
      // #endif

      // #ifndef MP-WEIXIN
      editorCtx.insertImage({
        src: tempFiles[0].path,
        width: '80%', // 默认不建议铺满宽度100%，预留一点空隙以便用户编辑
        success: function () {}
      })
      // #endif

      /**
       * 使用 uniCloud.uploadFile 上传图片的示例方法（可适用多选上传）
       * 正式开发环境中，请将上面 本地临时插入图片预览 注释后，模仿以下写法
       */
      // tempFiles.forEach(async (item) => {
      //   uni.showLoading({
      //     title: '上传中请稍后',
      //     mask: true
      //   })
      //   let upfile = await uniCloud.uploadFile({
      //     filePath: item.path,
      //     // 同名会导致报错 policy_does_not_allow_file_overwrite
      //     // cloudPath可由 想要存储的文件夹/文件名 拼接，若不拼文件夹名则默认存储在cloudstorage文件夹中
      //     cloudPath: `cloudstorage/${item.name}`,
      //     cloudPathAsRealPath: true
      //   })
      //   editorCtx.insertImage({
      //     src: upfile.fileID,
      //     width: '80%', // 默认不建议铺满宽度100%，预留一点空隙以便用户编辑
      //     success: function () {
      //       uni.hideLoading()
      //     }
      //   })
      // })
    },
    /**
     * 导出 - toolbar需要开启export工具
     * @param {string} e 导出的html内容
     */
    exportHtml(e) {
      uni.navigateTo({
        url: '/pages/out/out',
        success(res) {
          // 传至导出页面解析即可
          res.eventChannel.emit('e-transmit-html', {
            data: e
          })
        }
      })
    },
    /**
     * 添加超链接
     * @param {Object} e { text: '链接描述', href: '链接地址' }
     */
    addLink(e) {
      console.log('==== addLink :', e)
    }
  }
}
</script>

```

## prop参数
```
// 空白占位字样
placeholder: {
  type: String,
  default: '写点什么吧 ~'
},
// 是否只读
readOnly: {
  type: Boolean,
  default: false
},
// 最大字数限制，默认-1不限
maxlength: {
  type: Number,
  default: -1
},
// 工具栏配置 - 默认全工具栏 keys与excludeKeys两者皆为空，即为全工具
toolbarConfig: {
  type: Object,
  default: () => {
    return {
      keys: [], // 要显示的工具，优先级最大
      excludeKeys: [], // 除这些指定的工具外，其他都显示 // 默认两者皆为空，即为全工具
      iconSize: '20px' // 工具栏字体大小
    }
  }
}
```

#### 所有工具列表

`建议按需引入对应的工具，尽量简化富文本工具`

```
toolbarAllList: [
  'bold', // 加粗
  'italic', // 斜体
  'underline', // 下划线
  'strike', // 删除线
  'alignLeft', // 左对齐
  'alignCenter', // 居中对齐
  'alignRight', // 右对齐
  'alignJustify', // 两端对齐
  'lineHeight', // 行间距
  'letterSpacing', // 字间距
  'marginTop', // 段前距
  'marginBottom', // 段后距
  'fontFamily', // 字体
  'fontSize', // 字号
  'color', // 文字颜色
  'backgroundColor', // 背景颜色
  'date', // 日期
  'listCheck', // 待办
  'listOrdered', // 有序列表
  'listBullet', // 无序列表
  'indentInc', // 增加缩进
  'indentDec', // 减少缩进
  'divider', // 分割线
  'header', // 标题
  'scriptSub', // 下标
  'scriptSuper', // 上标
  'direction', // 文本方向
  'image', // 图片
  'link', // 超链接
  'undo', // 撤销
  'redo', // 重做
  'removeFormat', // 清除格式
  'clear', // 清空
  'export' // 导出
]
```

## emit方法
```
// 如上述使用方式示例中，已有详细描述，此处不再赘述
@init="initEditor"
@input="inputOver" 
@upinImage="upinImage" 
@overMax="overMax"
@exportHtml="exportHtml" // toolbar需要开启export工具
@addLink="addLink" // toolbar需要开启link工具
```

## 注意事项
1. 该组件在使用过程中推荐在外层套上个父盒子，并给父盒子高度，组件在封装时进行了高度计算，会自动撑满父盒子
2. 如遇到在内置浏览器中发生无法拖动调节颜色板的问题，只需调出开发者调试面板，点击重置左上角选择dom的箭头后，便能调出模拟器手势光标，便可正常拖动了
3. 有插入视频的需求，暂时可能无法实现，官方给予的回复是：目前各端的eidtor组件都不能直接插入视频，编辑时可以采用视频封面占位，并在图片中保存视频信息，在预览时再还原为视频。
4. 在H5中，由于官方editor内部是使用unpkg加载quill.min.js、image-resize.min.js两个依赖，可能会存在国内无法访问unpkg的资源，导致editor不正常的问题，在此我已将该两个依赖包放在了组件的 uni_modules/sp-editor/static 文件夹下，希望在H5端正常且稳定使用的小伙伴可以在index.html或template.h5.html中引入该本地cdn资源
  - 官方已给出了[H5 使用最佳实践](https://uniapp.dcloud.net.cn/component/editor.html#h5-%E4%BD%BF%E7%94%A8%E6%9C%80%E4%BD%B3%E5%AE%9E%E8%B7%B5)，主要两种方式：
    - 方案一：自行托管 CDN 资源，示例工程中使用该方案一（使用静态资源，或者把资源丢进自己服务器中，要注意的是，在vue3环境下直接在项目根目录下的index.html配置script标签引入资源即可，但是在vue2环境下需要在根目录下创建template.h5.html自定义模板，在该模板中配置资源引入，详情可见博客[uniapp内网部署 editor富文本报错问题](https://blog.csdn.net/heroboyluck/article/details/125336035)）
    - 方案二：装quill的1.3.7的node包 `npm i quill@1.3.7`
    - 关于此问题还可详见官方问答贴：[在 H5 中使用了uni-app 的 editor 组件异常的解决方案](https://ask.dcloud.net.cn/article/40900)
    - 注意此解决方式主要针对H5端，其他端不要做该操作

5. 由于官方editor组件中的insertText方法在不同平台是基于各平台对应的富文本技术实现，可能存在部分问题：
    - 在安卓app正常，但是其他平台在插入内容的时候会移动光标聚焦，导致焦点回滚到视口处
    - 初始化内容时建议使用setContents方法，而非insertText方法
    - 尽量避免使用insertText方法，因为可能导致意外的光标移动问题

6. 请阅读了解 [editor支持的标签](https://uniapp.dcloud.net.cn/component/editor.html#%E6%94%AF%E6%8C%81%E7%9A%84%E6%A0%87%E7%AD%BE)
7. 关于添加插入超链接的问题：
    - 在微信小程序中点击不跳转的问题，可见[uniapp的editor组件插入的a标签会被删除href属性](https://ask.dcloud.net.cn/question/151737)，经官方实测是微信小程序的问题，可能需要等微信官方修复了
    - 光标问题，因为uniapp官方的editor组件并没有开放插入a标签的api，我手搓了一个addLink的方法，该方法本质上使用的是setContents方法，将delta替换插入link，由于setContents会使光标默认回到开头位置，故我做了插入链接后使富文本失焦的操作，需要用户再手动点击使光标聚焦（属实也是无奈之举，目前可能是实现手动添加链接的最优解，除非官方开放添加链接的api）
    - 插入的超链接在编辑模式下不可跳转，因为此时还是正在编辑的富文本，需要使用好一点的富文本解析器来解析编辑好的富文本文档成和他们后才能点击跳转，如在示例工程中编辑完成后点击export工具导出解析后即可点击跳转

## 自定义扩展
1. 插件本身支持对各种自定义的工具进行扩展，可以模仿添加超链接或插入日期等工具的写法规范，进行自定义扩展开发
2. 本插件内部封装了一个addLink的方法，用于插入超链接(a标签)的工具，如若要自定义扩展功能：#话题#、 @别人，等类似的携带链接的功能，可以模仿该addLink的方式进行自定义扩展开发，最后可以通过export工具导出富文本，并使用富文本解析器解析（需要找个好点的解析器，有的简陋的解析器或者官方自带的rich-text组件无法解析部分标签如a标签）

## 写在最后
若对插件有任何疑问或者优化建议，欢迎在评论区留言，在插件市场中的私信消息本人可能不经常留意，导致没能及时回复，
可以加入本人的插件问答QQ交流群: 852637893，欢迎进群交流。

<img width="200" src="https://mp-0ecede5c-a993-48bf-ba4b-45d9a8c7e79b.cdn.bspapp.com/resource/qqqun.jpg" alt="交流群:852637893"/>