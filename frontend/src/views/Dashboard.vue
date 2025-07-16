<template>
  <div class="dashboard-wrapper">
    <el-row justify="center" class="mb-3">
      <el-col :span="22">
        <el-card class="navbar-card" body-style="{padding: '20px 30px'}">
          <el-row align="middle" justify="space-between">
            <div class="logo">
              <span class="logo-icon">🎓</span>
              <span class="logo-text">智能培训系统</span>
            </div>
            <div class="user-info">
              <el-avatar :size="40" class="avatar" />
              <span class="username">管理员王总</span>
              <el-button class="gradient-btn" size="medium">退出</el-button>
            </div>
          </el-row>
        </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="20" justify="center" class="stats-row mb-3">
      <el-col v-for="(stat, i) in stats" :key="i" :xs="12" :sm="6" :md="6" class="stat-col">
        <el-card class="stat-card" shadow="hover">
          <div class="stat-number">{{ stat.number }}</div>
          <div class="stat-label">{{ stat.label }}</div>
        </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="20" justify="center" class="content-row">
      <el-col :xs="24" :md="14" class="mb-3">
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

      <el-col :xs="24" :md="8" class="mb-3">
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
</template>

<script setup>
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
  max-width: 1200px;
  margin: 0 auto;
  min-height: 100vh;
  padding: 30px 20px;
  box-sizing: border-box;
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
}

.user-info {
  display: flex;
  align-items: center;
}

.username {
  margin: 0 10px;
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
}

.gradient-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.2);
}

.stats-row {
  margin-bottom: 20px;
}

.stat-col {
  display: flex;
  justify-content: center;
  margin-bottom: 20px;
}

.stat-card {
  text-align: center;
  width: 220px;
  padding: 20px 0;
}

.stat-number {
  font-size: 32px;
  font-weight: bold;
  color: #333;
  margin-bottom: 8px;
}

.stat-label {
  margin-top: 8px;
  color: #666;
}

.content-card {
  padding: 20px;
}

.course-item,
.exam-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 20px;
  margin-bottom: 20px;
  border-radius: 12px;
}

.course-item {
  background: #f8faff;
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
</style>

<style>
html,
body,
#app {
  height: 100%;
  margin: 0;
  overflow: hidden;
}
body {
  background: linear-gradient(135deg, #4f86ff, #8b2eff);
  background-size: cover;
}
</style>
