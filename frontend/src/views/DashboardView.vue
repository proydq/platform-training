<script setup>
import { ref } from 'vue'
import { ElMessage } from 'element-plus'

const activeMenu = ref('dashboard')

function handleSelect(index) {
  activeMenu.value = index
}

function logout() {
  ElMessage.success('已退出登录')
}

const stats = [
  { number: '24', label: '已完成课程' },
  { number: '8', label: '待学习课程' },
  { number: '95%', label: '考试通过率' },
  { number: '156', label: '学习积分' }
]
</script>

<template>
  <el-container class="dashboard-container">
    <el-header class="nav-bar">
      <div class="nav-left">
        <div class="logo">🎓 智能培训系统</div>
        <el-menu mode="horizontal" :default-active="activeMenu" @select="handleSelect">
          <el-menu-item index="dashboard">仪表板</el-menu-item>
          <el-menu-item index="courses">我的课程</el-menu-item>
          <el-menu-item index="exams">考试中心</el-menu-item>
          <el-menu-item index="students">学员管理</el-menu-item>
        </el-menu>
      </div>
      <el-button class="logout-btn" type="text" @click="logout">退出</el-button>
    </el-header>
    <el-main>
      <div class="content-wrapper">
        <el-row :gutter="20" class="stats-grid">
          <el-col :xs="12" :sm="6" v-for="(item, i) in stats" :key="i">
            <el-card class="stat-card" shadow="hover">
              <div class="stat-number">{{ item.number }}</div>
              <div class="stat-label">{{ item.label }}</div>
            </el-card>
          </el-col>
        </el-row>
        <el-card class="main-placeholder" shadow="never">仪表板内容...</el-card>
      </div>
    </el-main>
  </el-container>
</template>

<style scoped>
.dashboard-container {
  min-height: 100vh;
  background: linear-gradient(135deg, #a18cd1, #fbc2eb);
}
.nav-bar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  position: sticky;
  top: 0;
  z-index: 10;
  background: rgba(255, 255, 255, 0.9);
  backdrop-filter: blur(10px);
  padding: 0 20px;
}
.nav-left {
  display: flex;
  align-items: center;
}
.logo {
  font-weight: 700;
  font-size: 20px;
  margin-right: 20px;
}
.content-wrapper {
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px;
}
.stat-card {
  text-align: center;
  border-radius: 12px;
}
.stat-number {
  font-size: 32px;
  font-weight: 700;
  margin-bottom: 6px;
}
.stat-label {
  font-size: 14px;
  color: #666;
}
.main-placeholder {
  margin-top: 30px;
  border-radius: 12px;
  text-align: center;
  padding: 40px;
}
</style>
