<template>
  <view class="home">
    <view class="editor-box">
      <sp-editor 
        :toolbar-config="{
          excludeKeys: ['direction', 'date', 'lineHeight', 'letterSpacing', 'listCheck','underline','strike','marginTop','marginBottom','date','listCheck','listOrdered','listBullet','link','export','removeFormat','backgroundColor','alignJustify','scriptSub','scriptSuper'],
          iconSize: '18px'
        }"
        @init="initEditor"
        @input="inputOver" 
        @upinImage="upinImage" 
        @overMax="overMax"
        @exportHtml="exportHtml"
        ></sp-editor>
    </view>
  </view>
</template>

<script>
export default {
  data() {
    return {
      editorIns:null,
	  oldimage:'',
	  image:'',
	  data:'',
    }
  },
  methods: {
	// 获取输入内容
    inputOver(e) {
      // 可以在此处获取到编辑器已编辑的内容
	  e.html = e.html.replace(this.oldimage, this.image)
	  e.html = e.html.replace(this.oldimage, this.image)
	  this.data = e.html
	  this.$emit("content",this.data);
    },
    // 超出最大内容限制
    overMax(e) {
      uni.showToast({
      	title: '超出文字限制',
      	icon:'error',
      	duration: 1000
      });
    },
    initEditor(editor) {
      this.editorIns = editor // 保存编辑器实例
      this.editorIns.setContents({
        html: ''
      })
    },
    upinImage(tempFiles, editorCtx) {
		let that = this
		editorCtx.insertImage({
        src: tempFiles[0].path,
        width: '60%',
        success: function () {
			// 上传头像图片
			uni.uploadFile({
			  url: 'http://10.195.28.44:9090/tasks/taskCenter/uploadimage', 
			  filePath: tempFiles[0].path,
			  name: 'file', 
			  header: {
				  'Authorization': uni.getStorageSync('token')
			  },
			  success: (uploadRes) => {
				  that.oldimage = tempFiles[0].path
				  that.image = uploadRes.data
			  },
			});
		}
      })
    },
  }
}
</script>