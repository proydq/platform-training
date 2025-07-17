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

      <!-- 统计数据区域 -->
      <div class="stats-section">
        <div v-for="(stat, index) in dashboardStats" :key="index" class="stat-card">
          <div class="stat-number">{{ stat.number }}</div>
          <div class="stat-label">{{ stat.label }}</div>
        </div>
      </div>

      <!-- 主要内容区域 -->
      <div class="main-content">
        <!-- 左侧内容 -->
        <div class="left-content">
          <!-- 学习概览 -->
          <div class="learning-overview">
            <div class="section-header">
              <span class="section-icon">📊</span>
              <span class="section-title">学习概览</span>
            </div>
            <div class="overview-stats">
              <div v-for="(item, index) in overviewStats" :key="index" class="overview-item">
                <div class="overview-number" :style="{ color: item.color }">{{ item.number }}</div>
                <div class="overview-label">{{ item.label }}</div>
              </div>
            </div>
            <div class="overview-filters">
              <div v-for="(filter, index) in overviewFilters" :key="index" 
                   :class="['overview-filter', { active: filter.active }]"
                   @click="setOverviewFilter(index)">
                {{ filter.name }}
              </div>
            </div>
            <div class="recommended-course">
              <div class="course-header">
                <span class="recommend-icon">🎯</span>
                <span class="recommend-title">为你推荐</span>
              </div>
              <div class="course-card-recommend">
                <div class="course-icon">🚀</div>
                <div class="course-info">
                  <div class="course-name">AI产品设计实战</div>
                  <div class="course-desc">🤖 基于你的学习记录推荐 | 讲师：AI专家</div>
                </div>
                <el-button type="primary" size="small" @click="startRecommendedCourse">
                  开始学习
                </el-button>
              </div>
            </div>
          </div>

          <!-- 学习路径 -->
          <div class="learning-paths">
            <div class="section-header">
              <span class="section-icon">🛤️</span>
              <span class="section-title">学习路径</span>
            </div>
            <div class="paths-container">
              <div v-for="(path, index) in learningPaths" :key="index" 
                   :class="['path-card', path.type]">
                <div class="path-header">
                  <span class="path-icon">{{ path.icon }}</span>
                  <span class="path-title">{{ path.title }}</span>
                </div>
                <div class="path-content">
                  <div v-for="(course, idx) in path.courses" :key="idx" class="path-course">
                    <span class="course-bullet">{{ course.icon }}</span>
                    <span class="course-text">{{ course.name }}</span>
                    <span class="course-status">{{ course.status }}</span>
                  </div>
                </div>
                <div class="path-progress">
                  <span class="progress-text">{{ path.progress }}</span>
                </div>
                <el-button :type="path.buttonType" size="small" class="path-action">
                  {{ path.actionText }}
                </el-button>
              </div>
            </div>
          </div>
        </div>

        <!-- 右侧内容 -->
        <div class="right-content">
          <!-- 我的课程 -->
          <div class="my-courses">
            <div class="section-header">
              <span class="section-icon">📚</span>
              <span class="section-title">我的课程</span>
            </div>
            <div class="courses-subtitle">推荐课程优先</div>
            <div class="courses-list">
              <div v-for="(course, index) in myCourses" :key="index" class="course-item">
                <div class="course-left">
                  <div class="course-icon" :style="{ background: course.iconBg }">
                    {{ course.icon }}
                  </div>
                  <div class="course-details">
                    <div class="course-name">{{ course.name }}</div>
                    <div class="course-meta">
                      <span>讲师：{{ course.instructor }}</span>
                      <span>时长：{{ course.duration }}</span>
                      <span v-if="course.status === 'completed'">✅ {{ course.completedDate }}</span>
                    </div>
                    <div v-if="course.progress" class="course-progress">
                      <div class="progress-bar">
                        <div class="progress-fill" :style="{ width: course.progress + '%' }"></div>
                      </div>
                      <span class="progress-text">{{ course.progress }}%</span>
                    </div>
                  </div>
                </div>
                <div class="course-actions">
                  <el-button :type="course.buttonType" size="small" @click="handleCourseAction(course)">
                    {{ course.actionText }}
                  </el-button>
                  <el-button type="text" size="small" @click="toggleFavorite(course.id)">
                    {{ course.favorited ? '⭐' : '☆' }}
                  </el-button>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- 学习成就 -->
      <div class="achievements">
        <div class="section-header">
          <span class="section-icon">🏆</span>
          <span class="section-title">学习成就</span>
        </div>
        <div class="achievements-grid">
          <div v-for="(achievement, index) in achievements" :key="index" 
               :class="['achievement-card', achievement.status]">
            <div class="achievement-icon">{{ achievement.icon }}</div>
            <div class="achievement-info">
              <div class="achievement-title">{{ achievement.title }}</div>
              <div class="achievement-desc">{{ achievement.desc }}</div>
              <div v-if="achievement.date" class="achievement-date">获得时间：{{ achievement.date }}</div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive, computed } from 'vue'
import { ElMessage } from 'element-plus'
import { useRouter } from 'vue-router'

const router = useRouter()

// 菜单数据
const menuItems = reactive([
  { icon: '📊', name: '仪表板', active: false },
  { icon: '📚', name: '我的课程', active: true },
  { icon: '📝', name: '考试中心', active: false },
  { icon: '👥', name: '学员管理', active: false },
  { icon: '⚙️', name: '管理后台', active: false }
])

// 仪表板统计数据
const dashboardStats = reactive([
  { number: '156', label: '总学时数' },
  { number: '48', label: '课程总数' },
  { number: '89%', label: '考试通过率' },
  { number: '2,340', label: '总学习时长' }
])

// 学习概览统计
const overviewStats = reactive([
  { number: '24', label: '已完成', color: '#4CAF50' },
  { number: '8', label: '进行中', color: '#FF9800' },
  { number: '4', label: '待开始', color: '#9E9E9E' },
  { number: '156h', label: '学习时长', color: '#2196F3' }
])

// 概览筛选器
const overviewFilters = reactive([
  { name: '全部', active: true },
  { name: '进行中', active: false },
  { name: '已完成', active: false },
  { name: '待开始', active: false },
  { name: '⭐ 收藏', active: false }
])

// 学习路径数据
const learningPaths = reactive([
  {
    type: 'product',
    icon: '📊',
    title: '产品经理成长路径',
    courses: [
      { icon: '📊', name: '产品基础知识培训', status: '(已完成)' },
      { icon: '📈', name: '用户研究方法', status: '(进行中)' },
      { icon: '🎨', name: '产品设计实战', status: '(待开始)' },
      { icon: '📋', name: '数据驱动决策', status: '(待开始)' }
    ],
    progress: '40%',
    buttonType: 'primary',
    actionText: '继续路径'
  },
  {
    type: 'analysis',
    icon: '📈',
    title: '数据分析师路径',
    courses: [
      { icon: '📊', name: '数据分析基础', status: '(已完成)' },
      { icon: '📈', name: 'Excel数据分析', status: '(已完成)' },
      { icon: '🐍', name: 'Python数据分析', status: '(进行中)' },
      { icon: '🤖', name: '机器学习入门', status: '(待开始)' }
    ],
    progress: '60%',
    buttonType: 'success',
    actionText: '继续路径'
  }
])

// 我的课程数据
const myCourses = reactive([
  {
    id: 1,
    icon: '📱',
    name: '产品基础知识培训',
    instructor: '李经理',
    duration: '2小时',
    progress: 75,
    status: 'in-progress',
    iconBg: 'linear-gradient(135deg, #667eea, #764ba2)',
    buttonType: 'primary',
    actionText: '继续学习',
    favorited: false
  },
  {
    id: 2,
    icon: '✅',
    name: '数据分析基础',
    instructor: '王专家',
    duration: '3小时',
    status: 'completed',
    completedDate: '2025-01-10',
    iconBg: 'linear-gradient(135deg, #4CAF50, #45a049)',
    buttonType: 'success',
    actionText: '查看证书',
    favorited: true
  },
  {
    id: 3,
    icon: '📊',
    name: '高级用户研究方法',
    instructor: '刘教授',
    duration: '4小时',
    status: 'not-started',
    iconBg: 'linear-gradient(135deg, #9E9E9E, #757575)',
    buttonType: 'default',
    actionText: '查看详情',
    favorited: false
  }
])

// 学习成就数据
const achievements = reactive([
  {
    icon: '🏆',
    title: '学习达人',
    desc: '累计学习时长达到100小时',
    date: '2025-01-15',
    status: 'earned'
  },
  {
    icon: '📊',
    title: '知识大师',
    desc: '完成20门课程学习',
    date: '2025-01-12',
    status: 'earned'
  },
  {
    icon: '🎯',
    title: '考试王者',
    desc: '所有考试成绩均在90分以上',
    status: 'locked'
  }
])



// 方法：设置激活菜单
const setActiveMenu = (index) => {
  menuItems.forEach((item, i) => {
    item.active = i === index
  })
  
  // 根据菜单项导航
  if (index === 0) {
    router.push('/dashboard')
  } else if (index === 2) {
    router.push('/exams')
  } else if (index === 3) {
    router.push('/students')
  } else if (index === 4) {
    router.push('/admin')
  }
  
  ElMessage.success(`切换到：${menuItems[index].name}`)
}

// 方法：设置概览筛选器
const setOverviewFilter = (index) => {
  overviewFilters.forEach((filter, i) => {
    filter.active = i === index
  })
  ElMessage.success(`切换到：${overviewFilters[index].name}`)
}

// 方法：开始推荐课程
const startRecommendedCourse = () => {
  ElMessage.success('正在启动推荐课程：AI产品设计实战')
}

// 方法：处理课程操作
const handleCourseAction = (course) => {
  const actionMap = {
    'in-progress': `继续学习课程：${course.name}`,
    'completed': `查看课程证书：${course.name}`,
    'not-started': `查看课程详情：${course.name}`
  }
  
  ElMessage.success(actionMap[course.status] || `操作课程：${course.name}`)
}

// 方法：切换收藏状态
const toggleFavorite = (courseId) => {
  const course = myCourses.find(c => c.id === courseId)
  if (course) {
    course.favorited = !course.favorited
    ElMessage.success(course.favorited ? '已添加到收藏' : '已取消收藏')
  }
}

// 方法：退出登录
const handleLogout = () => {
  ElMessage.success('退出登录成功')
  router.push('/login')
}
</script>

<style>
html, body, #app {
  margin: 0;
  padding: 0;
  min-height: 100%;
  width: 100%;
  background: #667eea;
  font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, sans-serif;
}

body {
  display: block !important;
  place-items: unset !important;
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
.main-content {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 20px;
}

.left-content, .right-content {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

/* 通用区块样式 */
.learning-overview, .learning-paths, .my-courses, .achievements {
  background: rgba(255, 255, 255, 0.95);
  backdrop-filter: blur(10px);
  border-radius: 16px;
  padding: 24px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
}

/* 区块标题 */
.section-header {
  display: flex;
  align-items: center;
  gap: 8px;
  margin-bottom: 20px;
}

.section-icon {
  font-size: 20px;
}

.section-title {
  font-size: 18px;
  font-weight: 600;
  color: #333;
}

/* 学习概览样式 */
.overview-stats {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 16px;
  margin-bottom: 20px;
}

.overview-item {
  text-align: center;
}

.overview-number {
  font-size: 24px;
  font-weight: 700;
  margin-bottom: 4px;
}

.overview-label {
  font-size: 12px;
  color: #666;
}

.overview-filters {
  display: flex;
  gap: 8px;
  margin-bottom: 20px;
}

.overview-filter {
  padding: 6px 12px;
  border-radius: 12px;
  font-size: 12px;
  cursor: pointer;
  transition: all 0.3s ease;
  background: #f5f5f5;
  color: #666;
}

.overview-filter:hover {
  background: rgba(102, 126, 234, 0.1);
  color: #667eea;
}

.overview-filter.active {
  background: #667eea;
  color: white;
}

.recommended-course {
  margin-top: 16px;
}

.course-header {
  display: flex;
  align-items: center;
  gap: 8px;
  margin-bottom: 12px;
}

.recommend-icon {
  font-size: 16px;
}

.recommend-title {
  font-size: 14px;
  font-weight: 600;
  color: #333;
}

.course-card-recommend {
  display: flex;
  align-items: center;
  gap: 12px;
  padding: 16px;
  background: linear-gradient(135deg, #667eea, #764ba2);
  border-radius: 12px;
  color: white;
}

.course-card-recommend .course-icon {
  width: 40px;
  height: 40px;
  background: rgba(255, 255, 255, 0.2);
  border-radius: 8px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 20px;
}

.course-card-recommend .course-info {
  flex: 1;
}

.course-card-recommend .course-name {
  font-size: 14px;
  font-weight: 600;
  margin-bottom: 4px;
}

.course-card-recommend .course-desc {
  font-size: 12px;
  opacity: 0.9;
}

/* 学习路径样式 */
.paths-container {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.path-card {
  padding: 20px;
  border-radius: 12px;
  transition: all 0.3s ease;
}

.path-card.product {
  background: linear-gradient(135deg, #667eea, #764ba2);
  color: white;
}

.path-card.analysis {
  background: linear-gradient(135deg, #4CAF50, #45a049);
  color: white;
}

.path-card:hover {
  transform: translateY(-2px);
  box-shadow: 0 8px 25px rgba(0, 0, 0, 0.15);
}

.path-header {
  display: flex;
  align-items: center;
  gap: 8px;
  margin-bottom: 16px;
}

.path-icon {
  font-size: 20px;
}

.path-title {
  font-size: 16px;
  font-weight: 600;
}

.path-content {
  margin-bottom: 16px;
}

.path-course {
  display: flex;
  align-items: center;
  gap: 8px;
  margin-bottom: 8px;
  font-size: 12px;
}

.course-bullet {
  font-size: 14px;
}

.course-text {
  flex: 1;
}

.course-status {
  opacity: 0.8;
}

.path-progress {
  margin-bottom: 16px;
}

.progress-text {
  font-size: 14px;
  font-weight: 600;
}

.path-action {
  width: 100%;
}

/* 我的课程样式 */
.courses-subtitle {
  font-size: 12px;
  color: #666;
  margin-bottom: 16px;
}

.courses-list {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.course-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 16px;
  border-radius: 12px;
  background: #f8f9fa;
  transition: all 0.3s ease;
}

.course-item:hover {
  transform: translateY(-1px);
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
}

.course-left {
  display: flex;
  align-items: center;
  gap: 12px;
  flex: 1;
}

.course-item .course-icon {
  width: 40px;
  height: 40px;
  border-radius: 8px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
  font-size: 16px;
}

.course-details {
  flex: 1;
}

.course-item .course-name {
  font-size: 14px;
  font-weight: 600;
  color: #333;
  margin-bottom: 4px;
}

.course-item .course-meta {
  display: flex;
  gap: 12px;
  font-size: 11px;
  color: #666;
  margin-bottom: 8px;
}

.course-progress {
  display: flex;
  align-items: center;
  gap: 8px;
}

.progress-bar {
  flex: 1;
  height: 4px;
  background: #e9ecef;
  border-radius: 2px;
  overflow: hidden;
  min-width: 60px;
}

.progress-fill {
  height: 100%;
  background: #667eea;
  transition: width 0.3s ease;
}

.progress-text {
  font-size: 10px;
  color: #667eea;
  font-weight: 500;
}

.course-actions {
  display: flex;
  gap: 8px;
  align-items: center;
}

.favorited {
  color: #ffc107 !important;
}

/* 学习成就样式 */
.achievements {
  grid-column: 1 / -1;
}

.achievements-grid {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 20px;
}

.achievement-card {
  display: flex;
  align-items: center;
  gap: 16px;
  padding: 20px;
  border-radius: 12px;
  transition: all 0.3s ease;
}

.achievement-card.earned {
  background: linear-gradient(135deg, #FFD700, #FFA500);
  color: white;
}

.achievement-card.locked {
  background: #f8f9fa;
  color: #666;
  border: 2px dashed #ddd;
}

.achievement-card:hover {
  transform: translateY(-2px);
  box-shadow: 0 8px 25px rgba(0, 0, 0, 0.15);
}

.achievement-icon {
  font-size: 32px;
}

.achievement-info {
  flex: 1;
}

.achievement-title {
  font-size: 16px;
  font-weight: 600;
  margin-bottom: 4px;
}

.achievement-desc {
  font-size: 12px;
  opacity: 0.9;
  margin-bottom: 4px;
}

.achievement-date {
  font-size: 10px;
  opacity: 0.8;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .dashboard-wrapper {
    padding: 10px;
  }
  
  .stats-section {
    grid-template-columns: repeat(2, 1fr);
  }
  
  .main-content {
    grid-template-columns: 1fr;
  }
  
  .overview-stats {
    grid-template-columns: repeat(2, 1fr);
  }
  
  .achievements-grid {
    grid-template-columns: 1fr;
  }
  
  .course-item {
    flex-direction: column;
    align-items: stretch;
    gap: 12px;
  }
  
  .course-left {
    justify-content: flex-start;
  }
  
  .course-actions {
    justify-content: center;
  }
}
</style>