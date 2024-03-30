<template>
  <div>
    <div class="table">
      <el-table :data="tableData" style="width: 100%">
        <el-table-column prop="newsTitle" label="资讯标题" width="300"></el-table-column>
        <el-table-column prop="newsAuthorName" label="发布人" width="250"></el-table-column>
        <el-table-column prop="newsTime" label="发布时间" width="300"></el-table-column>
        <el-table-column width="150">
          <template v-slot:default="scope">
            <el-button type="primary" @click="watchDetail(scope.row.newsID)">修改资讯</el-button>
          </template>
        </el-table-column>
        <el-table-column width="150">
          <template v-slot:default="scope">
            <el-button @click="deleteNews(scope.row.newsID)">删除资讯</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <el-dialog title="修改资讯" width="1000px" :visible.sync="dialogTableVisible" style="text-align: center">
      <div class="detail">
        <!--    资讯标题-->
        <div class="title">
          <span>资讯标题</span>
          <div class="title-input">
            <el-input v-model="title"></el-input>
          </div>
        </div>

        <!--    资讯头图-->
        <div class="title">
          <span>资讯头图</span>
          <div class="title-image">
            <el-upload
                class="avatar-uploader"
                action=""
                drag
                :auto-upload="false"
                :show-file-list="false"
                :on-change="changeUpload">
              <img v-if="newsPhoto" :src="newsPhoto"></img>
              <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload>
          </div>
        </div>

        <!--    图片裁剪-->
        <el-dialog title="图片剪裁" :visible.sync="dialogVisible" append-to-body>
          <div class="cropper-content">
            <div class="cropper">
              <vueCropper
                  ref="cropper"
                  :img="option.img"
                  :outputSize="option.size"
                  :outputType="option.outputType"
                  :info="true"
                  :full="option.full"
                  :canMove="option.canMove"
                  :canMoveBox="option.canMoveBox"
                  :original="option.original"
                  :autoCrop="option.autoCrop"
                  :fixed="option.fixed"
                  :fixedNumber="option.fixedNumber"
                  :centerBox="option.centerBox"
                  :infoTrue="option.infoTrue"
                  :fixedBox="option.fixedBox"
              ></vueCropper>
            </div>
          </div>
          <div slot="footer" class="dialog-footer">
            <el-button @click="dialogVisible = false">取 消</el-button>
            <el-button type="primary" @click="finish" >确认</el-button>
          </div>
        </el-dialog>

        <!--    资讯主体-->
        <div class="body">
          <span>资讯内容</span>
          <div>
            <mavon-editor v-model="detail" id="editor" ref="mavonEditorRef" @imgAdd="addImg" @change="changeData"/>
          </div>
        </div>

        <div style="display:flex;flex-direction: row;justify-content: center;margin-top: 30px;width: 100%">
          <el-button @click="cancel" style="width: 100px">取消修改</el-button>
          <el-button @click="changeNew" type="primary" style="width: 100px">确认修改</el-button>
        </div>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { mavonEditor } from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'
import {changeNewsDetail, deleteNews, getnews, getnewsDetail, upPic} from "@/api/api";
import TurndownService from 'turndown';

export default {
  name: "manageEditor",
  data(){
    return {
      pages:0,
      tableData:[],

      // 显示资讯详情
      dialogTableVisible:false,

      // 资讯详情内容
      newID:'',
      title:'',
      content:'',
      newsPhoto:'',
      oldimage:'',
      newimage:'',

      // md格式
      detail:'',
      // 头图上传以及裁剪相关
      dialogVisible: false,
      option: {
        img: '', // 裁剪图片的地址
        outputSize: 0.8, // 裁剪生成图片的质量
        outputType: 'png', // 裁剪生成图片的格式
        canScale: false, // 图片是否允许滚轮缩放
        autoCrop: true, // 是否默认生成截图框
        autoCropWidth: 400, // 默认生成截图框宽度
        autoCropHeight: 200, // 默认生成截图框高度
        fixed: true, // 是否开启截图框宽高固定比例
        fixedNumber: [7, 5], // 截图框的宽高比例
        full: true, // 是否输出原图比例的截图
        canMoveBox: true, // 截图框能否拖动
        original: false, // 上传图片按照原始比例渲染
        centerBox: false, // 截图框是否被限制在图片里面
        infoTrue: true // true 为展示真实输出图片宽高 false 展示看到的截图框宽高
      },
      fileName: "",
    }
  },
  created() {
    this.reload()
  },
  methods:{
    reload(){
      getnews(this.pages).then((res)=>{
        for(let i=0;i<res.data.length;i++){
          this.tableData.push({newsID:res.data[i].newsID,newsAuthorName:res.data[i].newsAuthorName,newsTitle:res.data[i].newsTitle,newsTime:res.data[i].newsTime})
        }
        // 加载全部数据
        if((this.pages+10) == this.tableData.length){
          this.pages += 10
          this.reload()
        }
        else {
          this.pages -= 10
        }
      })
    },
    // 查看资讯详情
    watchDetail(newID){
      getnewsDetail(newID).then((res)=>{
        this.newID = newID
        this.title = res.data.newsTitle
        this.newsPhoto = res.data.newsPhoto
        this.content = res.data.newsDetail

        // 将html文件转换成md文件
        let turndownService = new TurndownService();
        this.detail = turndownService.turndown(res.data.newsDetail)

        this.dialogTableVisible = true
      })
    },
    // 上传头图
    changeUpload(file, fileList) {
      if (file.size > 1048576) {
        this.$message.error('图片超出1M!')
        return false
      }
      this.fileName = file.name
      let url = URL.createObjectURL(file.raw)
      // 上传成功后将图片地址赋值给裁剪框显示图片
      this.$nextTick(() => {
        this.option.img = url
        this.dialogVisible = true
      })
    },
    // 点击裁剪，这一步是可以拿到处理后的地址
    finish() {
      this.$refs.cropper.getCropBlob(async (data) => {
        let file = new window.File([data], this.fileName, {type: 'image/png'})
        let formData = new FormData();
        formData.append('file',file);
        upPic(formData).then((res) => {
          this.newsPhoto = res.data
          this.dialogVisible = false
        })
      })
    },
    // 上传富文本图片
    addImg(name, $file){
      if($file.size > 1048576){
        alert('图片超出1M!')
      }
      else {
        let formData = new FormData()
        formData.append('file', $file)
        upPic(formData).then((res) => {
          this.oldimage = $file.miniurl
          this.newimage = res.data
          this.content = this.content.replace(this.oldimage, this.newimage)
        })
      }
    },
    changeData(value, render) {
      this.content = render;
    },
    // 取消修改
    cancel(){
      this.dialogTableVisible = false
      this.tableData = []
      this.pages = 0
      this.reload()
    },
    // 修改任务
    changeNew(){
      setTimeout(() => {
        if(this.title && this.content){
          const now = new Date();
          const year = now.getFullYear();
          const month = (now.getMonth() + 1).toString().padStart(2, '0');
          const day = now.getDate().toString().padStart(2, '0');
          let currentTime = `${year}.${month}.${day}`;

          let temp = {
            newsID:this.newID,
            newsTitle: this.title,
            newsAuthorID: localStorage.adminID,
            newsAuthorName: localStorage.adminName,
            newsTime: currentTime,
            newsDetail: this.content,
            newsPhoto: this.newsPhoto,
          }
          changeNewsDetail(temp).then((res)=>{
            if(res.data == '修改成功'){
              alert('修改成功')
            }
            else {
              alert('发布失败')
            }
            this.dialogTableVisible = false

            this.tableData=[]
            this.pages = 0
            this.reload()
          })
        }
        else {
          alert('标题和内容不能为空')
        }
      }, 300)
    },
    deleteNews(newsID){
      deleteNews(newsID).then((res)=>{
        if(res.data == '删除成功'){
          alert('删除成功')
        }
        else {
          alert('删除失败')
        }

        this.tableData=[]
        this.pages = 0
        this.reload()
      })
    }
  },
  components:{
    mavonEditor,
  }
}
</script>

<style scoped>
.table {
  display: flex;
  margin: 10px 0 0 20px;
  user-select:none;
}
.detail {
  width: 960px;
  height: 440px;
  display: flex;
  flex-direction: row;
  justify-content: flex-start;
  flex-wrap: wrap;
  overflow-y: auto;
}

.title {
  display: flex;
  flex-direction: row;
  justify-content: flex-start;
  align-items: center;
  width: 100%;
  margin-bottom: 30px;
}
.title span {
  width: 100px;
  font-size: 18px;
  font-weight: bold;
}
.title-input {
  width: 30vw;
}
.title-image img{
  height: 200px;
}
.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  line-height: 178px;
  text-align: center;
  user-select: none;
}
.cropper {
  text-align: center;
  width: auto;
  height: 400px;
}
.body {
  display: flex;
  flex-direction: row;
  justify-content: flex-start;
}
.body span {
  width: 100px;
  font-size: 18px;
  font-weight: bold;
}
#editor {
  width: 600px;
  height: 300px;
}
</style>