<template>
  <div class="dashboard-wrapper">
    <div class="container">
      <!-- Top Navigation -->
      <el-row class="mb-3">
        <el-col :span="24">
          <el-card class="navbar-card" body-style="{padding: '10px 20px'}">
            <el-row align="middle" justify="space-between">
              <el-col :span="4">
                <div class="logo">
                  <span class="logo-icon">🎓</span>
                  <span class="logo-text">智能培训系统</span>
                </div>
              </el-col>
            <el-col :span="14">
              <el-menu mode="horizontal" :default-active="activeMenu" class="nav-menu">
                <el-menu-item index="dashboard">仪表盘</el-menu-item>
                <el-menu-item index="courses">我的课程</el-menu-item>
                <el-menu-item index="exam">考试中心</el-menu-item>
                <el-menu-item index="student">学员管理</el-menu-item>
                <el-menu-item index="admin">管理后台</el-menu-item>
              </el-menu>
            </el-col>
            <el-col :span="6">
              <div class="user-info">
                <span class="username">管理员王总</span>
                <el-avatar :size="32" class="avatar" />
                <el-button class="gradient-btn" size="small">退出</el-button>
              </div>
            </el-col>
          </el-row>
          </el-card>
        </el-col>
      </el-row>

    <!-- Statistics -->
      <el-row :gutter="20" class="stats-row mb-3">
        <el-col
          v-for="(item,i) in stats"
          :key="i"
          :xs="24"
          :sm="12"
          :md="6"
          :lg="6"
          :xl="6"
        >
          <el-card class="stat-card" shadow="hover">
            <div class="stat-number">{{ item.number }}</div>
            <div class="stat-label">{{ item.label }}</div>
          </el-card>
        </el-col>
      </el-row>

    <!-- Content Cards -->
      <el-row :gutter="20" class="content-row">
        <el-col :xs="24" :md="12" :lg="12" :xl="12" class="mb-3">
          <el-card class="content-card" header="🔥 最新课程">
            <div v-for="(c, index) in courses" :key="index" class="course-item">
            <div class="course-info">
              <span class="course-icon">{{ c.icon }}</span>
              <div class="course-details">
                <div class="course-name">{{ c.name }}</div>
                <div class="course-meta">{{ c.teacher }} · {{ c.duration }}</div>
              </div>
            </div>
            <el-button class="gradient-btn" size="small">学习</el-button>
          </div>
          </el-card>
        </el-col>
        <el-col :xs="24" :md="12" :lg="12" :xl="12" class="mb-3">
          <el-card class="content-card exam-card" header="📋 考试安排">
            <div v-for="(exam, idx) in exams" :key="idx" class="exam-item">
              <div class="exam-info">
                <div class="exam-name">{{ exam.name }}</div>
                <div class="exam-deadline">截止：{{ exam.deadline }}</div>
              </div>
              <el-button class="gradient-btn" size="small">开始考试</el-button>
            </div>
          </el-card>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'

const activeMenu = ref('dashboard')

const stats = [
  { number: '156', label: '总学员数' },
  { number: '48', label: '课程总数' },
  { number: '89%', label: '系统活跃度' },
  { number: '2,340', label: '总学习时长' },
]

const courses = [
  { icon: '📘', name: 'Vue 3 企业级应用', teacher: '张三', duration: '3h 20m' },
  { icon: '📗', name: 'Element Plus 快速入门', teacher: '李四', duration: '2h 45m' },
]

const exams = [
  { name: 'Vue 3 期末考试', deadline: '2024-12-31 23:59' },
]
</script>

<style scoped>
.dashboard-wrapper {
  min-height: 100vh;
  padding: 20px;
  box-sizing: border-box;
  background: linear-gradient(135deg, #4f86ff, #8b2eff);
  overflow: hidden;
}

.container {
  max-width: 1200px;
  margin: 0 auto;
}
.mb-3 {
  margin-bottom: 20px;
}
.navbar-card,
.stat-card,
.content-card {
  border-radius: 16px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}
.logo {
  display: flex;
  align-items: center;
  font-size: 18px;
  font-weight: bold;
}
.logo-icon {
  font-size: 24px;
  margin-right: 6px;
}
.logo-text {
  background: linear-gradient(90deg, #4f86ff, #8b2eff);
  -webkit-background-clip: text;
  color: transparent;
}
.nav-menu {
  border-bottom: none;
}
.nav-menu .el-menu-item.is-active {
  color: #fff;
  background: linear-gradient(90deg, #6a82fb, #a06af9);
  border-radius: 8px;
}
.user-info {
  display: flex;
  align-items: center;
  justify-content: flex-end;
}
.username {
  margin-right: 10px;
}
.avatar {
  margin-right: 10px;
  background: #fff;
  color: #666;
}
.gradient-btn {
  color: #fff;
  background: linear-gradient(90deg, #6a82fb, #a06af9);
  border: none;
  transition: transform 0.2s;
}
.gradient-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.2);
}
.stat-card {
  text-align: center;
  transition: transform 0.2s;
}
.stat-card:hover {
  transform: translateY(-4px);
}
.stat-number {
  font-size: 32px;
  font-weight: bold;
  color: #333;
}
.stat-label {
  color: #666;
  margin-top: 10px;
}
.course-item,
.exam-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px 0;
  border-bottom: 1px solid #f0f0f0;
}
.course-item:last-child,
.exam-item:last-child {
  border-bottom: none;
}
.course-info {
  display: flex;
  align-items: center;
}
.course-icon {
  font-size: 24px;
  margin-right: 10px;
}
.course-name {
  font-weight: bold;
}
.course-meta {
  font-size: 12px;
  color: #999;
}
.exam-card {
  background-color: #fff7e0;
}
.exam-name {
  font-weight: bold;
}
.exam-deadline {
  font-size: 12px;
  color: #999;
}
</style>
