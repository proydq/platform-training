<script setup>
import { ref, computed } from 'vue'
import { ElMessage } from 'element-plus'
import {
  Menu as MenuIcon,
  Notebook,
  Edit,
  UserFilled,
  Setting,
  Search,
  Star,
  Document,
  View,
  Lock
} from '@element-plus/icons-vue'

const activeMenu = ref('courses')

function handleSelect(index) {
  activeMenu.value = index
}

function logout() {
  ElMessage.success('已退出登录')
}

const stats = [
  { number: '12', label: '已完成', color: '#67c23a' },
  { number: '3', label: '进行中', color: '#409eff' },
  { number: '5', label: '待开始', color: '#e6a23c' },
  { number: '48h', label: '学习时长', color: '#909399' },
  { number: '2', label: '收藏', color: '#f56c6c' }
]

const statusOptions = ['全部', '进行中', '已完成', '待开始', '收藏']
const activeStatus = ref('全部')

const recommend = {
  title: '高级产品设计',
  teacher: '张老师',
  reason: '结合最新趋势，提升设计思维'
}

const search = ref('')

const courses = ref([
  {
    id: 1,
    icon: '📘',
    name: 'Vue3 入门',
    teacher: '王老师',
    duration: '3h',
    videos: 12,
    status: 'inProgress',
    progress: 40,
    favorite: true
  },
  {
    id: 2,
    icon: '📙',
    name: '产品实战',
    teacher: '李老师',
    duration: '2h',
    videos: 8,
    status: 'completed',
    progress: 100,
    favorite: false
  },
  {
    id: 3,
    icon: '📕',
    name: '高级算法',
    teacher: '陈老师',
    duration: '4h',
    videos: 16,
    status: 'locked',
    progress: 0,
    favorite: false
  },
  {
    id: 4,
    icon: '📗',
    name: '市场营销技巧',
    teacher: '赵老师',
    duration: '2.5h',
    videos: 10,
    status: 'todo',
    progress: 0,
    favorite: false
  }
])

function toggleFavorite(course) {
  course.favorite = !course.favorite
}

const displayCourses = computed(() => {
  return courses.value.filter((c) => {
    const matchStatus =
      activeStatus.value === '全部' ||
      (activeStatus.value === '进行中' && c.status === 'inProgress') ||
      (activeStatus.value === '已完成' && c.status === 'completed') ||
      (activeStatus.value === '待开始' && c.status === 'todo') ||
      (activeStatus.value === '收藏' && c.favorite)

    const matchSearch = c.name.toLowerCase().includes(search.value.toLowerCase())
    return matchStatus && matchSearch
  })
})

const paths = [
  {
    name: '新人入门路径',
    courses: [
      { name: '公司介绍', status: 'done' },
      { name: '产品培训', status: 'doing' },
      { name: '市场营销', status: 'todo' }
    ],
    progress: 60,
    color: 'linear-gradient(135deg, #a18cd1 0%, #fbc2eb 100%)'
  },
  {
    name: '高级进阶路径',
    courses: [
      { name: '高级设计', status: 'todo' },
      { name: '数据分析', status: 'todo' },
      { name: '团队协作', status: 'todo' }
    ],
    progress: 20,
    color: 'linear-gradient(135deg, #84fab0 0%, #8fd3f4 100%)'
  }
]

const achievements = [
  {
    icon: '🎯',
    title: '学习达人',
    desc: '累计学习50小时',
    time: '2025-01-10',
    progress: 80
  },
  {
    icon: '📚',
    title: '知识大师',
    desc: '完成30门课程',
    time: '2025-02-05',
    progress: 60
  },
  {
    icon: '🏆',
    title: '考试王者',
    desc: '通过所有考试',
    time: '2025-03-01',
    progress: 40
  }
]
</script>

<template>
  <div class="courses-container">
    <el-header class="nav-bar">
      <div class="nav-left">
        <div class="logo">🎓 智能培训系统</div>
        <el-menu mode="horizontal" :default-active="activeMenu" @select="handleSelect">
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
        <el-avatar class="avatar" size="32">
          <el-icon><UserFilled /></el-icon>
        </el-avatar>
        <el-button type="text" @click="logout">退出</el-button>
      </div>
    </el-header>

    <el-main>
      <div class="content-wrapper">
        <section class="overview">
          <el-row :gutter="20" class="stats-row">
            <el-col v-for="(item, i) in stats" :key="i" :xs="12" :sm="8" :md="4">
              <el-card class="stat-card" shadow="hover" :style="{ background: item.color }">
                <div class="stat-number">{{ item.number }}</div>
                <div class="stat-label">{{ item.label }}</div>
              </el-card>
            </el-col>
          </el-row>
          <div class="status-filter">
            <el-button
              v-for="s in statusOptions"
              :key="s"
              size="small"
              round
              :type="activeStatus === s ? 'primary' : 'default'"
              @click="activeStatus = s"
            >
              {{ s }}
            </el-button>
          </div>
        </section>

        <section class="recommend">
          <el-card class="recommend-card" shadow="hover">
            <div class="recommend-content">
              <div class="recommend-info">
                <div class="title">{{ recommend.title }}</div>
                <div class="teacher">{{ recommend.teacher }}</div>
                <div class="reason">{{ recommend.reason }}</div>
              </div>
              <el-button type="primary">开始学习</el-button>
            </div>
          </el-card>
        </section>

        <section class="my-courses">
          <div class="section-header">
            <h2>我的课程</h2>
            <el-input v-model="search" placeholder="搜索课程" class="search-input">
              <template #prefix>
                <el-icon><Search /></el-icon>
              </template>
            </el-input>
          </div>
          <el-card
            v-for="course in displayCourses"
            :key="course.id"
            class="course-card"
            shadow="hover"
          >
            <div class="course-item">
              <div class="left">
                <div class="course-icon">{{ course.icon }}</div>
                <div class="course-info">
                  <div class="name">{{ course.name }}</div>
                  <div class="meta">
                    {{ course.teacher }} · {{ course.duration }} · {{ course.videos }}节
                  </div>
                  <el-progress
                    v-if="course.status === 'inProgress'"
                    :percentage="course.progress"
                    :stroke-width="8"
                    show-text="false"
                    class="progress"
                  />
                  <el-tag type="success" v-else-if="course.status === 'completed'">已完成</el-tag>
                  <el-tag type="info" v-else-if="course.status === 'todo'">待开始</el-tag>
                  <el-tag type="warning" v-else-if="course.status === 'locked'">待解锁</el-tag>
                </div>
              </div>
              <div class="actions">
                <el-button v-if="course.status === 'inProgress'" type="primary" size="small">继续学习</el-button>
                <el-button v-else-if="course.status === 'completed'" size="small">复习</el-button>
                <el-button v-else-if="course.status === 'todo'" type="primary" size="small">开始学习</el-button>
                <el-button v-else-if="course.status === 'locked'" disabled size="small">暂未解锁</el-button>
                <el-button type="text" size="small" @click="toggleFavorite(course)">
                  <el-icon><Star /></el-icon>
                </el-button>
                <el-button type="text" size="small">
                  <el-icon><Document /></el-icon>
                </el-button>
                <el-button type="text" size="small">
                  <el-icon><View /></el-icon>
                </el-button>
              </div>
            </div>
          </el-card>
        </section>

        <section class="paths">
          <h2>学习路径</h2>
          <el-row :gutter="20">
            <el-col v-for="(path, i) in paths" :key="i" :xs="24" :md="12">
              <el-card class="path-card" shadow="hover" :style="{ background: path.color }">
                <div class="path-title">{{ path.name }}</div>
                <ul class="path-courses">
                  <li v-for="(c, idx) in path.courses" :key="idx">
                    <span>{{ c.name }}</span>
                    <span v-if="c.status === 'done'">✅</span>
                    <span v-else-if="c.status === 'doing'">📘</span>
                    <span v-else>🕒</span>
                  </li>
                </ul>
                <el-progress :percentage="path.progress" />
              </el-card>
            </el-col>
          </el-row>
        </section>

        <section class="achievements">
          <h2>学习成就</h2>
          <el-row :gutter="20">
            <el-col v-for="(ach, i) in achievements" :key="i" :xs="24" :sm="8">
              <el-card class="achievement-card" shadow="hover">
                <div class="ach-icon">{{ ach.icon }}</div>
                <div class="ach-title">{{ ach.title }}</div>
                <div class="ach-desc">{{ ach.desc }}</div>
                <div class="ach-time">{{ ach.time }}</div>
                <el-progress :percentage="ach.progress" />
              </el-card>
            </el-col>
          </el-row>
        </section>
      </div>
    </el-main>
  </div>
</template>

<style scoped>
.courses-container {
  min-height: 100vh;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}
.nav-bar {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  height: 60px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 30px;
  background: #fff;
  border-radius: 0 0 12px 12px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
  z-index: 100;
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
.avatar {
  background-color: #eceff4;
}
.user-name {
  color: #333;
}
.content-wrapper {
  max-width: 1200px;
  margin: 0 auto;
  padding: 80px 20px 20px;
  display: flex;
  flex-direction: column;
  gap: 30px;
}
.stat-card {
  border-radius: 12px;
  text-align: center;
  color: #fff;
}
.stat-number {
  font-size: 28px;
  font-weight: 700;
}
.stat-label {
  font-size: 14px;
}
.status-filter {
  margin-top: 20px;
  display: flex;
  gap: 10px;
  justify-content: center;
}
.recommend-card {
  border-radius: 12px;
  background: linear-gradient(135deg, #6dd5ed 0%, #2193b0 100%);
  color: #fff;
}
.recommend-content {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.recommend-info .title {
  font-size: 18px;
  font-weight: 600;
  margin-bottom: 8px;
}
.recommend-info .teacher {
  font-size: 14px;
  margin-bottom: 4px;
}
.recommend-info .reason {
  font-size: 13px;
  color: #f0f0f0;
}
.section-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 10px;
}
.search-input {
  width: 220px;
}
.course-card {
  border-radius: 12px;
  margin-bottom: 20px;
}
.course-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  flex-wrap: wrap;
  gap: 10px;
}
.course-icon {
  font-size: 32px;
  margin-right: 15px;
}
.course-info {
  flex: 1;
  min-width: 200px;
}
.course-info .name {
  font-weight: 600;
  margin-bottom: 4px;
}
.course-info .meta {
  font-size: 13px;
  color: #666;
  margin-bottom: 6px;
}
.actions {
  display: flex;
  align-items: center;
  gap: 6px;
}
.progress {
  width: 200px;
  margin-top: 4px;
}
.path-card {
  border-radius: 12px;
  color: #fff;
}
.path-title {
  font-weight: 600;
  margin-bottom: 6px;
}
.path-courses {
  list-style: none;
  padding: 0;
  margin: 0 0 10px 0;
  display: flex;
  flex-direction: column;
  gap: 4px;
}
.path-courses li {
  display: flex;
  justify-content: space-between;
  font-size: 14px;
}
.achievement-card {
  border-radius: 12px;
  text-align: center;
}
.ach-icon {
  font-size: 32px;
  margin-bottom: 10px;
}
.ach-title {
  font-weight: 600;
  margin-bottom: 4px;
}
.ach-desc {
  font-size: 14px;
  color: #666;
  margin-bottom: 4px;
}
.ach-time {
  font-size: 12px;
  color: #999;
  margin-bottom: 10px;
}
.el-button {
  border-radius: 20px;
}
</style>
