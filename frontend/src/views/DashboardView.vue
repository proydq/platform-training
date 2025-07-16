<script setup>
import { ref } from 'vue'
import { ElMessage } from 'element-plus'
import {
  Menu as MenuIcon,
  Notebook,
  Edit,
  UserFilled,
  Setting,
  Fire,
  Reading,
  Document
} from '@element-plus/icons-vue'

const activeMenu = ref('dashboard')

function handleSelect(index) {
  activeMenu.value = index
}

function logout() {
  ElMessage.success('已退出登录')
}

const stats = [
  { number: '156', label: '总学员数' },
  { number: '48', label: '课程总数' },
  { number: '89%', label: '系统活跃度' },
  { number: '2340', label: '总学习时长' }
]

const latestCourses = [
  { title: '产品基础知识培训', teacher: '李经理', duration: '2小时', type: '学习' },
  { title: '市场分析与调研', teacher: '王总监', duration: '1.5小时', type: '预览' }
]

const examSchedule = {
  title: '产品知识考试',
  deadline: '2025-01-20'
}
</script>

<template>
  <div class="dashboard-container">
    <el-header class="nav-bar">
      <div class="nav-left">
        <div class="logo">🎓 智能培训系统</div>
        <el-menu
          mode="horizontal"
          :default-active="activeMenu"
          @select="handleSelect"
        >
          <el-menu-item index="dashboard">
            <el-icon><MenuIcon /></el-icon>
            仪表板
          </el-menu-item>
          <el-menu-item index="courses">
            <el-icon><Notebook /></el-icon>
            我的课程
          </el-menu-item>
          <el-menu-item index="exams">
            <el-icon><Edit /></el-icon>
            考试中心
          </el-menu-item>
          <el-menu-item index="students">
            <el-icon><UserFilled /></el-icon>
            学员管理
          </el-menu-item>
          <el-menu-item index="admin">
            <el-icon><Setting /></el-icon>
            管理后台
          </el-menu-item>
        </el-menu>
      </div>
      <div class="nav-right">
        <span class="user-name">管理员王总</span>
        <el-button type="text" @click="logout">退出</el-button>
      </div>
    </el-header>
    <el-main>
      <div class="content-wrapper">
        <el-row :gutter="20" class="stats-row">
          <el-col
            v-for="(item, i) in stats"
            :key="i"
            :xs="24"
            :sm="12"
            :md="6"
          >
            <el-card class="stat-card" shadow="hover">
              <div class="stat-number">{{ item.number }}</div>
              <div class="stat-label">{{ item.label }}</div>
            </el-card>
          </el-col>
        </el-row>

        <el-row :gutter="20" class="content-row">
          <el-col :xs="24" :md="12">
            <el-card class="course-wrapper" shadow="hover">
              <template #header>
                <div class="card-header">
                  <el-icon><Fire /></el-icon>
                  <span>最新课程</span>
                </div>
              </template>
              <div class="course-list">
                <el-card
                  v-for="(course, index) in latestCourses"
                  :key="index"
                  class="course-item"
                  shadow="hover"
                >
                  <div class="course-content">
                    <el-icon class="course-icon"><Reading /></el-icon>
                    <div class="info">
                      <div class="title">{{ course.title }}</div>
                      <div class="meta">
                        {{ course.teacher }} · {{ course.duration }}
                      </div>
                    </div>
                    <el-button type="primary" size="small">{{ course.type }}</el-button>
                  </div>
                </el-card>
              </div>
            </el-card>
          </el-col>
          <el-col :xs="24" :md="12">
            <el-card class="exam-wrapper" shadow="hover">
              <template #header>
                <div class="card-header">
                  <el-icon><Document /></el-icon>
                  <span>考试安排</span>
                </div>
              </template>
              <div class="exam-card">
                <div class="exam-title">{{ examSchedule.title }}</div>
                <div class="exam-deadline">截止日期：{{ examSchedule.deadline }}</div>
                <el-button type="primary" size="small">开始考试</el-button>
              </div>
            </el-card>
          </el-col>
        </el-row>
      </div>
    </el-main>
  </div>
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
  background: rgba(255, 255, 255, 0.9);
  backdrop-filter: blur(10px);
  padding: 0 20px;
  position: sticky;
  top: 0;
  z-index: 10;
}
.nav-left {
  display: flex;
  align-items: center;
}
.logo {
  font-size: 20px;
  font-weight: 700;
  margin-right: 20px;
}
.nav-right {
  display: flex;
  align-items: center;
  gap: 10px;
}
.user-name {
  color: #333;
}
.content-wrapper {
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px 10px;
}
.stat-card {
  text-align: center;
  border-radius: 12px;
}
.stat-number {
  font-size: 32px;
  font-weight: 700;
}
.stat-label {
  color: #666;
  margin-top: 4px;
}
.course-wrapper,
.exam-wrapper {
  border-radius: 12px;
}
.card-header {
  display: flex;
  align-items: center;
  gap: 6px;
  font-weight: 600;
}
.course-list {
  display: flex;
  flex-direction: column;
  gap: 12px;
}
.course-item {
  border-radius: 12px;
}
.course-content {
  display: flex;
  align-items: center;
  justify-content: space-between;
}
.course-icon {
  font-size: 24px;
  margin-right: 10px;
}
.info {
  flex: 1;
}
.title {
  font-weight: 600;
  margin-bottom: 4px;
  color: #333;
}
.meta {
  color: #999;
  font-size: 12px;
}
.exam-card {
  background: #fff7df;
  border-radius: 12px;
  padding: 20px;
  text-align: center;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 10px;
}
.exam-title {
  font-weight: 600;
  color: #333;
}
.exam-deadline {
  color: #a18cd1;
  font-size: 14px;
}
.el-button {
  border-radius: 20px;
}
</style>
