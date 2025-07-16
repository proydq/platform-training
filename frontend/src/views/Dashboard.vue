<template>
  <div class="dashboard-wrapper">
    <div class="container">
      <el-card class="navbar-card mb-3" body-style="{padding: '10px 20px'}">
        <el-row align="middle" justify="space-between">
          <div class="logo">
            <span class="logo-icon">🎓</span>
            <span class="logo-text">智能培训系统</span>
          </div>
          <el-menu mode="horizontal" class="nav-menu" :default-active="activeMenu">
            <el-menu-item index="dashboard">仪表盘</el-menu-item>
            <el-menu-item index="courses">我的课程</el-menu-item>
            <el-menu-item index="exam">考试中心</el-menu-item>
            <el-menu-item index="student">学员管理</el-menu-item>
            <el-menu-item index="admin">管理后台</el-menu-item>
            <el-menu-item index="help">帮助中心</el-menu-item>
          </el-menu>
          <div class="user-info">
            <el-avatar :size="40" class="avatar">王</el-avatar>
            <span class="username">管理员王总</span>
            <el-button class="gradient-btn" size="small">退出</el-button>
          </div>
        </el-row>
      </el-card>

    <el-row :gutter="20" class="stats-row mb-3">
      <el-col v-for="(stat, i) in stats" :key="i" :xs="24" :sm="12" :md="6" class="stat-col">
        <el-card class="stat-card" shadow="hover">
          <div class="stat-number">{{ stat.number }}</div>
          <div class="stat-label">{{ stat.label }}</div>
        </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="20" class="content-row">
      <el-col :xs="24" :md="14" :lg="14" class="mb-3">
        <el-card class="content-card" header="🔥 最新课程">
          <div v-for="(course, idx) in courses" :key="idx" class="course-item">
            <div class="course-info">
              <span class="course-icon">{{ course.icon }}</span>
              <div class="course-details">
                <div class="course-name">{{ course.name }}</div>
                <div class="course-meta">{{ course.teacher }} · {{ course.duration }}</div>
              </div>
            </div>
            <div class="course-actions">
              <el-button class="gradient-btn" size="medium">学习</el-button>
              <el-button class="gradient-btn" size="medium" plain>预览</el-button>
            </div>
          </div>
        </el-card>
      </el-col>

      <el-col :xs="24" :md="10" :lg="10" class="mb-3">
        <el-card class="content-card exam-card" header="📋 考试安排">
          <div v-for="(exam, idx) in exams" :key="idx" class="exam-item">
            <div class="exam-info">
              <div class="exam-name">{{ exam.name }}</div>
              <div class="exam-time">{{ exam.time }}</div>
            </div>
            <el-button class="gradient-btn" size="medium">开始考试</el-button>
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
  { label: '总学员数', number: 156 },
  { label: '课程总数', number: 48 },
  { label: '系统活跃度', number: '89%' },
  { label: '总学习时长', number: '2,340' }
]

const courses = [
  { icon: '📘', name: 'Vue 3 企业级应用', teacher: '张三', duration: '3h 20m' },
  { icon: '📗', name: 'Element Plus 快速入门', teacher: '李四', duration: '2h 45m' },
  { icon: '📙', name: 'TypeScript 基础', teacher: '王五', duration: '1h 30m' }
]

const exams = [
  { name: 'Vue 3 期末考试', time: '2024-12-31 18:00' },
  { name: 'Element Plus 认证', time: '2024-11-30 09:00' }
]
</script>

<style scoped>
.dashboard-wrapper {
  box-sizing: border-box;
  min-height: 100vh;
}

.container {
  max-width: 1280px;
  margin: 0 auto;
  padding: 30px 20px;
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

.navbar-card {
  min-height: 80px;
  background: rgba(255, 255, 255, 0.85);
  backdrop-filter: blur(6px);
}

.nav-menu {
  flex: 1;
  justify-content: center;
  border-bottom: none;
}

.nav-menu .el-menu-item.is-active {
  color: #8b2eff;
}


.logo {
  display: flex;
  align-items: center;
  font-weight: bold;
  font-size: 22px;
}

.logo-icon {
  font-size: 32px;
  margin-right: 10px;
}

.logo-text {
  background: linear-gradient(90deg, #4f86ff, #8b2eff);
  -webkit-background-clip: text;
  color: transparent;
  font-size: 24px;
  letter-spacing: 1px;
}

.user-info {
  display: flex;
  align-items: center;
  gap: 10px;
}

.username {
  margin: 0 10px;
  font-weight: 500;
}

.avatar {
  background: #fff;
  color: #666;
}

.gradient-btn {
  color: #fff;
  background: linear-gradient(90deg, #6a82fb, #a06af9);
  border: none;
  transition: transform 0.2s;
  border-radius: 12px;
  padding: 8px 18px;
}

.gradient-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.2);
}

.stats-row {
  margin-bottom: 20px;
}

.stat-col {
  margin-bottom: 20px;
}

.stat-card {
  text-align: center;
  width: 100%;
  padding: 20px 0;
  color: #fff;
}

.stat-number {
  font-size: 32px;
  font-weight: bold;
  color: #fff;
  margin-bottom: 8px;
}

.stat-label {
  margin-top: 8px;
  color: #f5f5f5;
}

.stat-col:nth-child(1) .stat-card {
  background: linear-gradient(135deg, #7b9dff, #8795ff);
}

.stat-col:nth-child(2) .stat-card {
  background: linear-gradient(135deg, #a56bff, #d476ff);
}

.stat-col:nth-child(3) .stat-card {
  background: linear-gradient(135deg, #6fafff, #8cc2ff);
}

.stat-col:nth-child(4) .stat-card {
  background: linear-gradient(135deg, #898dff, #c89aff);
}

.content-card {
  padding: 20px;
}

.exam-card {
  min-height: 300px;
}

.course-item,
.exam-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 20px;
  margin-bottom: 20px;
  border-radius: 12px;
  width: 100%;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
}

.course-item {
  background: #fdfdff;
}

.exam-item {
  background: #fff7e0;
}


.course-info {
  display: flex;
  align-items: center;
}

.course-icon {
  font-size: 28px;
  margin-right: 10px;
  padding: 4px;
}

.course-name,
.exam-name {
  font-weight: bold;
}

.course-meta,
.exam-time {
  font-size: 12px;
  color: #999;
}

.course-actions > .el-button:first-child {
  margin-right: 8px;
}

:global(html),
:global(body),
:global(#app) {
  height: 100%;
  margin: 0;
}

:global(body) {
  min-height: 100vh;
  background: linear-gradient(135deg, #4f86ff, #8b2eff);
  background-size: cover;
}
</style>
