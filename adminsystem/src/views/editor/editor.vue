<template>
  <div class="main">
<!--    资讯标题-->
    <div class="title">
      <span>资讯标题</span>
      <div>
        <el-input v-model="title"></el-input>
      </div>
    </div>

<!--    资讯主体-->
    <div class="body">
      <span>资讯标题</span>
      <div >
        <mavon-editor id="editor" v-model="content"/>
      </div>
    </div>

    <div style="display:flex;flex-direction: row;justify-content: center;margin-top: 30px;width: 100%">
      <el-button style="width: 100px">取消发布</el-button>
      <el-button type="primary" style="width: 100px">确认发布</el-button>
    </div>

  </div>
</template>

<script>
import { mavonEditor } from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'

export default {
  name: "editor",
  data(){
    return {
      title:'',
      content:'',
    }
  },
  components:{
    mavonEditor,
  }
}
</script>

<style scoped>
.main {
  display: flex;
  flex-direction: column;
  width: 80vw;
  height: 80vh;
  padding: 5vh 0 0 1vw;
}
.title {
  display: flex;
  flex-direction: row;
  justify-content: flex-start;
}
.title span {
  width: 100px;
  font-size: 18px;
  font-weight: bold;
}
.title div {
  width: 70vw;
}
.body {
  display: flex;
  flex-direction: row;
  justify-content: flex-start;
  margin-top: 30px;
}
.body span {
  width: 100px;
  font-size: 18px;
  font-weight: bold;
}
#editor {
  width: 75vw;
  height: 55vh;
}
</style>