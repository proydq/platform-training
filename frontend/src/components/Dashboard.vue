<template>
  <div class="dashboard-wrapper">
    <div class="dashboard-content">
      <!-- 顶部导航栏 -->
      <div class="navbar">
        <div class="navbar-content">
          <!-- 左侧系统名 -->
          <div class="navbar-left">
            <span class="system-icon">🎓</span>
            <span class="system-name">智能培训系统</span>
          </div>
          
          <!-- 中间菜单 -->
          <div class="navbar-menu">
            <div 
              v-for="(item, index) in menuItems" 
              :key="index"
              :class="['menu-item', { active: item.active }]"
              @click="setActiveMenu(index)"
            >
              {{ item.icon }} {{ item.name }}
            </div>
          </div>
          
          <!-- 右侧用户信息 -->
          <div class="navbar-right">
            <span class="username">管理员王总</span>
            <div class="avatar">王</div>
            <el-button type="text" size="small" class="logout-btn" @click="handleLogout">
              退出
            </el-button>
          </div>
        </div>
      </div>
      
      <!-- 统计卡片区域 -->
      <div class="stats-section">
        <div v-for="(stat, index) in statsData" :key="index" class="stat-card">
          <div class="stat-number">{{ stat.number }}</div>
          <div class="stat-label">{{ stat.label }}</div>
        </div>
      </div>
      
      <!-- 主要内容区域 -->
      <div class="content-section">
        <!-- 最新课程 -->
        <div class="content-card">
          <div class="card-header">
            <span class="card-icon">🔥</span>
            <span class="card-title">最新课程</span>
          </div>
          <div class="course-list">
            <div v-for="(course, index) in courseList" :key="index" class="course-item">
              <div class="course-info">
                <div class="course-icon">{{ course.icon }}</div>
                <div class="course-details">
                  <div class="course-name">{{ course.name }}</div>
                  <div class="course-meta">
                    <span class="instructor">讲师：{{ course.instructor }}</span>
                    <span class="duration">时长：{{ course.duration }}</span>
                  </div>
                </div>
              </div>
              <el-button type="primary" size="small" class="action-btn">
                {{ course.action }}
              </el-button>
            </div>
          </div>
        </div>
        
        <!-- 考试安排 -->
        <div class="content-card">
          <div class="card-header">
            <span class="card-icon">📋</span>
            <span class="card-title">考试安排</span>
          </div>
          <div class="exam-list">
            <div v-for="(exam, index) in examList" :key="index" class="exam-item">
              <div class="exam-info">
                <div class="exam-name">{{ exam.name }}</div>
                <div class="exam-deadline">截止时间：{{ exam.deadline }}</div>
              </div>
              <el-button type="primary" size="small" class="action-btn">
                {{ exam.action }}
              </el-button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script setup lang="ts">
import { reactive, ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import http from '../utils/http'               // ← 相对路径
import { useAuthStore } from '../stores/authStore'

/* ── 顶部导航菜单 ── */
const menuItems = reactive([
  { name: '仪表盘',  icon: '📊', active: true,  path: '/dashboard' },
  { name: '我的课程', icon: '📚', active: false, path: '/courses' },
  { name: '考试中心', icon: '📝', active: false, path: '/exams' },
  { name: '学员管理', icon: '👥', active: false, path: '/student-management' },
  { name: '管理后台', icon: '⚙️', active: false, path: '/admin' }
])

const router = useRouter()
function setActiveMenu(i: number) {
  menuItems.forEach((m, idx) => (m.active = idx === i))
  router.push(menuItems[i].path)
}

/* ── 统计卡片 ── */
const statsData = ref<{ number: number | string; label: string }[]>([])

onMounted(async () => {
  try {
    const res = await http.get('/stats/overview') // 后端需返回四个字段
    statsData.value = [
      { number: res.totalStudents,   label: '总学员数' },
      { number: res.totalCourses,    label: '课程总数' },
      { number: res.systemActivity,  label: '系统活跃度' },
      { number: res.totalStudyHours, label: '总学习时长' }
    ]
  } catch {
    // 接口未完成时占位，避免模板警告
    statsData.value = [
      { number: '-', label: '总学员数' },
      { number: '-', label: '课程总数' },
      { number: '-', label: '系统活跃度' },
      { number: '-', label: '总学习时长' }
    ]
  }
})

/* ── 占位列表，可后续接接口 ── */
const courseList = ref([])
const examList   = ref([])

/* ── 退出登录 ── */
const auth = useAuthStore()
function handleLogout() {
  auth.logout()
  ElMessage.success('已退出')
  router.push('/login')
}
</script>



<style>
html, body, #app {
  margin: 0;
  padding: 0;
  height: 100%;
  width: 100%;
  background: #667eea;
  font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, sans-serif;
}
</style>

<style scoped>
.dashboard-wrapper {
  min-height: 100vh;
  padding: 20px;
  box-sizing: border-box;
}

.dashboard-content {
  max-width: 1200px;
  margin: 0 auto;
  display: flex;
  flex-direction: column;
  gap: 20px;
}

/* 顶部导航栏 */
.navbar {
  background: rgba(255, 255, 255, 0.95);
  backdrop-filter: blur(10px);
  border-radius: 20px;
  padding: 0 30px;
  height: 70px;
  display: flex;
  align-items: center;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
}

.navbar-content {
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 100%;
}

.navbar-left {
  display: flex;
  align-items: center;
  gap: 12px;
}

.system-icon {
  font-size: 28px;
}

.system-name {
  font-size: 20px;
  font-weight: 600;
  color: #333;
}

.navbar-menu {
  display: flex;
  gap: 8px;
}

.menu-item {
  padding: 8px 16px;
  border-radius: 12px;
  cursor: pointer;
  transition: all 0.3s ease;
  font-weight: 500;
  font-size: 14px;
  color: #666;
}

.menu-item:hover {
  background: rgba(102, 126, 234, 0.1);
  color: #667eea;
}

.menu-item.active {
  background: #667eea;
  color: white;
}

.navbar-right {
  display: flex;
  align-items: center;
  gap: 12px;
}

.username {
  font-weight: 500;
  color: #333;
  font-size: 14px;
}

.avatar {
  width: 32px;
  height: 32px;
  border-radius: 50%;
  background: #667eea;
  color: white;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 14px;
  font-weight: 600;
}

.logout-btn {
  color: #666;
  font-size: 14px;
}

.logout-btn:hover {
  color: #667eea;
}

/* 统计卡片区域 */
.stats-section {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 20px;
}

.stat-card {
  background: rgba(255, 255, 255, 0.95);
  backdrop-filter: blur(10px);
  border-radius: 16px;
  padding: 30px 20px;
  text-align: center;
  transition: all 0.3s ease;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
}

.stat-card:hover {
  transform: translateY(-2px);
  box-shadow: 0 8px 30px rgba(0, 0, 0, 0.15);
}

.stat-number {
  font-size: 32px;
  font-weight: 700;
  color: #667eea;
  margin-bottom: 8px;
}

.stat-label {
  font-size: 14px;
  color: #666;
  font-weight: 500;
}

/* 主要内容区域 */
.content-section {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 20px;
}

.content-card {
  background: rgba(255, 255, 255, 0.95);
  backdrop-filter: blur(10px);
  border-radius: 16px;
  padding: 24px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
  transition: all 0.3s ease;
}

.content-card:hover {
  transform: translateY(-2px);
  box-shadow: 0 8px 30px rgba(0, 0, 0, 0.15);
}

.card-header {
  display: flex;
  align-items: center;
  gap: 8px;
  margin-bottom: 20px;
  padding-bottom: 16px;
  border-bottom: 1px solid rgba(0, 0, 0, 0.1);
}

.card-icon {
  font-size: 18px;
}

.card-title {
  font-size: 16px;
  font-weight: 600;
  color: #333;
}

/* 课程列表 */
.course-list {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.course-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 16px;
  background: rgba(102, 126, 234, 0.05);
  border-radius: 12px;
  transition: all 0.3s ease;
}

.course-item:hover {
  background: rgba(102, 126, 234, 0.1);
  transform: translateX(2px);
}

.course-info {
  display: flex;
  align-items: center;
  gap: 12px;
}

.course-icon {
  font-size: 20px;
  width: 40px;
  height: 40px;
  display: flex;
  align-items: center;
  justify-content: center;
  background: rgba(102, 126, 234, 0.1);
  border-radius: 8px;
}

.course-details {
  display: flex;
  flex-direction: column;
  gap: 4px;
}

.course-name {
  font-weight: 600;
  color: #333;
  font-size: 14px;
}

.course-meta {
  display: flex;
  gap: 12px;
  font-size: 12px;
  color: #666;
}

/* 考试列表 */
.exam-list {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.exam-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 16px;
  background: rgba(255, 193, 7, 0.1);
  border-radius: 12px;
  border-left: 4px solid #ffc107;
  transition: all 0.3s ease;
}

.exam-item:hover {
  background: rgba(255, 193, 7, 0.15);
  transform: translateX(2px);
}

.exam-info {
  display: flex;
  flex-direction: column;
  gap: 4px;
}

.exam-name {
  font-weight: 600;
  color: #333;
  font-size: 14px;
}

.exam-deadline {
  font-size: 12px;
  color: #666;
  font-weight: 500;
}

/* 通用按钮样式 */
.action-btn {
  background: #667eea;
  border: none;
  color: white;
  transition: all 0.3s ease;
  padding: 6px 16px;
  border-radius: 8px;
  font-size: 12px;
  min-width: 70px;
}

.action-btn:hover {
  background: #5a6fd8;
  transform: translateY(-1px);
}
</style>