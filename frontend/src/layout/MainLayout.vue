<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()

const menu = ref('dashboard')

function logout() {
  router.push('/login')
}
</script>

<template>
  <div class="layout">
    <header class="header">
      <div class="logo">🎓 智能培训系统</div>
      <el-menu mode="horizontal" :default-active="menu" @select="menu = $event" router>
        <el-menu-item index="dashboard" route="/app/dashboard">仪表板</el-menu-item>
        <el-menu-item index="courses" route="/app/courses">我的课程</el-menu-item>
        <el-menu-item index="exams" route="/app/exams">考试中心</el-menu-item>
        <el-menu-item index="students" route="/app/students">学员管理</el-menu-item>
        <el-menu-item index="admin" route="/app/admin">管理后台</el-menu-item>
      </el-menu>
      <el-button type="text" @click="logout">退出</el-button>
    </header>
    <main class="content">
      <router-view />
    </main>
  </div>
</template>

<style scoped>
.layout {
  padding: 20px;
}
.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}
.logo {
  font-weight: bold;
  font-size: 20px;
}
.content {
}
</style>
