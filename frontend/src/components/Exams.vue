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
            <div class="menu-item" @click="navigateTo('dashboard')">📊 仪表板</div>
            <div class="menu-item" @click="navigateTo('courses')">📚 我的课程</div>
            <div class="menu-item active">📝 考试中心</div>
            <div class="menu-item" @click="navigateTo('students')">👥 学员管理</div>
            <div class="menu-item" @click="navigateTo('admin')">⚙️ 管理后台</div>
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
      <div class="main-content">
        <!-- 左侧内容 -->
        <div class="left-content">
          <!-- 考试概览 -->
          <div class="exam-overview">
            <div class="section-header">
              <span class="section-icon">📊</span>
              <span class="section-title">考试概览</span>
            </div>
            
            <div class="overview-stats">
              <div class="overview-item">
                <div class="overview-number" style="color: #28a745;">15</div>
                <div class="overview-label">已通过</div>
              </div>
              <div class="overview-item">
                <div class="overview-number" style="color: #f57c00;">3</div>
                <div class="overview-label">待考试</div>
              </div>
              <div class="overview-item">
                <div class="overview-number" style="color: #c62828;">1</div>
                <div class="overview-label">未通过</div>
              </div>
              <div class="overview-item">
                <div class="overview-number" style="color: #1565c0;">92%</div>
                <div class="overview-label">平均分</div>
              </div>
            </div>
            
            <!-- 特考试项目 -->
            <div class="special-exam">
              <div class="exam-card special">
                <div class="exam-icon">🎯</div>
                <div class="exam-info">
                  <div class="exam-name">产品基础知识考试</div>
                  <div class="exam-meta">📅 截止：2025-01-20 18:00 | ⏱️ 限时：30分钟 | 🎯 及格：60分</div>
                  <div class="exam-tags">
                    <span class="tag urgent">🔥 重要考试</span>
                    <span class="exam-details">📝 A卷考试题型 | 📊 10道题</span>
                  </div>
                </div>
                <el-button type="primary" @click="startExam('product-exam')">开始考试</el-button>
              </div>
            </div>
          </div>
          
          <!-- 我的错题集 -->
          <div class="wrong-questions">
            <div class="section-header">
              <span class="section-icon">📚</span>
              <span class="section-title">我的错题集</span>
            </div>
            
            <div class="wrong-stats">
              <div class="wrong-summary">
                <span class="wrong-count">错题总计：<strong>12道</strong></span>
                <span class="mastery-rate">复习次数：<strong>8次</strong></span>
              </div>
              <div class="wrong-actions">
                <el-button size="small" @click="practiceWrongQuestions">开始错题练习</el-button>
                <el-button size="small" type="info" @click="exportWrongQuestions">导出错题</el-button>
              </div>
            </div>
            
            <div class="question-list">
              <div v-for="(question, index) in wrongQuestions" :key="index" class="question-item">
                <div class="question-header">
                  <div class="question-tags">
                    <span class="tag" :class="question.difficulty">{{ question.difficultyText }}</span>
                    <span class="question-source">来自：{{ question.source }}</span>
                    <span class="tag" :class="question.type">{{ question.typeText }}</span>
                  </div>
                  <span class="error-count">错误{{ question.errorCount }}次</span>
                </div>
                <div class="question-content">{{ question.content }}</div>
                <div class="question-options">
                  <div v-for="(option, optIndex) in question.options" :key="optIndex" class="option">
                    <span class="option-label">{{ option.label }}</span>
                    <span class="option-text" :class="{ correct: option.isCorrect, wrong: option.isWrong }">{{ option.text }}</span>
                  </div>
                </div>
                <div class="question-answer">
                  <span class="answer-label">💡 A解析：</span>
                  <span class="answer-text">{{ question.explanation }}</span>
                </div>
                <div class="question-actions">
                  <el-button size="small" type="text" @click="addToFavorites(question.id)">⭐ 收藏</el-button>
                  <el-button size="small" type="text" @click="addNote(question.id)">📝 学习笔记</el-button>
                  <el-button size="small" type="text" @click="markAsReviewed(question.id)">✅ 标记已掌握</el-button>
                </div>
              </div>
            </div>
            
            <div class="wrong-bottom-actions">
              <el-button type="primary" @click="practiceWrongQuestions">📝 开始错题练习</el-button>
              <el-button @click="exportWrongQuestions">📥 导出错题</el-button>
              <el-button @click="clearMasteredQuestions">🗑️ 清除已掌握</el-button>
            </div>
          </div>
        </div>
        
        <!-- 右侧内容 -->
        <div class="right-content">
          <!-- 考试记录 -->
          <div class="exam-records">
            <div class="section-header">
              <span class="section-icon">📈</span>
              <span class="section-title">考试记录</span>
            </div>
            
            <!-- 成绩筛选 -->
            <div class="record-filters">
              <div 
                v-for="(filter, index) in recordFilters" 
                :key="index"
                :class="['record-filter', { active: filter.active }]"
                @click="setRecordFilter(index)"
              >
                {{ filter.name }}
              </div>
            </div>
            
            <!-- 考试记录列表 -->
            <div class="record-list">
              <div v-for="(record, index) in examRecords" :key="index" class="record-item" :class="record.status">
                <div class="record-info">
                  <div class="record-name">{{ record.name }}</div>
                  <div class="record-meta">📅 考试时间：{{ record.date }} | ⏱️ 用时：{{ record.duration }}</div>
                  <div class="record-details">
                    <span class="tag" :class="record.status">{{ record.statusText }}</span>
                    <span class="record-accuracy">正确率：{{ record.accuracy }}</span>
                  </div>
                </div>
                <div class="record-score">
                  <div class="score-number" :class="record.status">{{ record.score }}</div>
                  <div class="score-label">分</div>
                </div>
                <div class="record-actions">
                  <el-button size="small" @click="viewExamReport(record.id)">📊 详细报告</el-button>
                  <el-button v-if="record.status === 'excellent'" size="small" @click="downloadCertificate(record.id)">📜 证书</el-button>
                  <el-button v-if="record.status === 'failed'" size="small" type="primary" @click="retakeExam(record.id)">🔄 重考</el-button>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      
      <!-- 练习模式 -->
      <div class="practice-modes">
        <div class="section-header">
          <span class="section-icon">🎯</span>
          <span class="section-title">练习模式</span>
        </div>
        
        <div class="practice-grid">
          <div v-for="(mode, index) in practiceModes" :key="index" class="practice-card" :style="{ background: mode.gradient }" @click="startPractice(mode.id)">
            <div class="practice-icon">{{ mode.icon }}</div>
            <div class="practice-title">{{ mode.title }}</div>
            <div class="practice-desc">{{ mode.description }}</div>
            <div class="practice-meta">{{ mode.meta }}</div>
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
          <div v-for="(achievement, index) in achievements" :key="index" class="achievement-card" :class="{ earned: achievement.earned }">
            <div class="achievement-icon">{{ achievement.icon }}</div>
            <div class="achievement-info">
              <div class="achievement-title">{{ achievement.title }}</div>
              <div class="achievement-desc">{{ achievement.description }}</div>
              <div v-if="achievement.earned" class="achievement-date">获得时间：{{ achievement.earnedDate }}</div>
              <div v-else class="achievement-progress">进度：{{ achievement.progress }}</div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { ElMessage } from 'element-plus'
import { useRouter } from 'vue-router'

const router = useRouter()

// 统计数据
const statsData = reactive([
  { number: '156', label: '总学习时长' },
  { number: '48', label: '课程总数' },
  { number: '89%', label: '考试通过率' },
  { number: '2,340', label: '总学习时长' }
])

// 记录筛选
const recordFilters = reactive([
  { name: '全部', active: true },
  { name: '✅ 已通过', active: false },
  { name: '❌ 未通过', active: false },
  { name: '🏆 优秀(90+)', active: false }
])

// 错题数据
const wrongQuestions = reactive([
  {
    id: 1,
    difficulty: 'hard',
    difficultyText: '🔴 困难',
    source: '用户体验设计考试',
    type: 'important',
    typeText: '⚠️ 易错',
    errorCount: 1,
    content: '用户画像的主要作用是什么？',
    options: [
      { label: 'A', text: '帮助产品经理了解用户', isCorrect: false, isWrong: true },
      { label: 'B', text: '正确答案：B. 帮助整个团队统一对用户', isCorrect: true, isWrong: false }
    ],
    explanation: '用户画像主要是帮助整个产品团队对目标用户形成统一认知，从而在产品设计和决策时保持一致性。'
  },
  {
    id: 2,
    difficulty: 'hard',
    difficultyText: '🔴 困难',
    source: '数据分析基础考试',
    type: 'error',
    typeText: '⚠️ 易错',
    errorCount: 1,
    content: '在数据分析中，什么是假设？',
    options: [
      { label: 'A', text: '你的答案：A. 数据准备', isCorrect: false, isWrong: true },
      { label: 'B', text: '正确答案：B. 在实际数据分析之前，数据分析师需要对数据的特征和分析结果做出预期', isCorrect: true, isWrong: false }
    ],
    explanation: '假设是指在数据分析过程中，分析师基于业务理解和经验对数据特征及分析结果的预期判断。'
  }
])

// 考试记录
const examRecords = reactive([
  {
    id: 1,
    name: '产品战略规划考试',
    date: '2025-01-15 14:30',
    duration: '26分钟',
    status: 'excellent',
    statusText: '🏆 优秀',
    accuracy: '14/15',
    score: 92
  },
  {
    id: 2,
    name: '用户体验设计考试',
    date: '2025-01-12 10:15',
    duration: '45分钟',
    status: 'failed',
    statusText: '❌ 未通过',
    accuracy: '5/10',
    score: 58
  },
  {
    id: 3,
    name: '数据分析基础考试',
    date: '2025-01-10 16:20',
    duration: '35分钟',
    status: 'excellent',
    statusText: '🏆 优秀',
    accuracy: '14/15',
    score: 95
  }
])

// 练习模式
const practiceModes = reactive([
  {
    id: 'random',
    icon: '🎲',
    title: '随机练习',
    description: '从所有题库中随机选择题目',
    meta: '题目数量：20题 | 预计用时：30分钟',
    gradient: 'linear-gradient(135deg, #667eea, #764ba2)'
  },
  {
    id: 'chapter',
    icon: '📖',
    title: '章节练习',
    description: '按课程章节进行专项练习',
    meta: '12个章节可选 | 自定义题目数量',
    gradient: 'linear-gradient(135deg, #28a745, #20c997)'
  },
  {
    id: 'mock',
    icon: '🏆',
    title: '模拟考试',
    description: '完全模拟真实考试环境',
    meta: '正式考试题型 | 严格计时',
    gradient: 'linear-gradient(135deg, #ffc107, #fd7e14)'
  },
  {
    id: 'speed',
    icon: '⚡',
    title: '极速挑战',
    description: '考验你的反应速度和准确性',
    meta: '60秒答题 | 排行榜竞争',
    gradient: 'linear-gradient(135deg, #e91e63, #ad1457)'
  }
])

// 学习成就
const achievements = reactive([
  {
    icon: '🏆',
    title: '学习达人',
    description: '连续学习7天',
    earned: true,
    earnedDate: '2025-01-15'
  },
  {
    icon: '📚',
    title: '知识大师',
    description: '完成20门课程',
    earned: true,
    earnedDate: '2025-01-12'
  },
  {
    icon: '🎯',
    title: '考试王者',
    description: '所有考试95分以上',
    earned: false,
    progress: '8/10'
  }
])

// 方法
const navigateTo = (route) => {
  if (route === 'courses') {
    router.push('/courses')
  } else if (route === 'dashboard') {
    router.push('/dashboard')
  } else if (route === 'students') {
    router.push('/students')
  } else if (route === 'admin') {
    router.push('/admin')
  }
  ElMessage.success(`导航到${route}`)
}

const handleLogout = () => {
  ElMessage.success('退出登录成功')
  router.push('/login')
}

const setRecordFilter = (index) => {
  recordFilters.forEach((filter, i) => {
    filter.active = i === index
  })
  ElMessage.success(`筛选：${recordFilters[index].name}`)
}

const startExam = (examId) => {
  ElMessage.success(`开始考试：${examId}`)
}

const practiceWrongQuestions = () => {
  ElMessage.success('开始错题练习')
}

const exportWrongQuestions = () => {
  ElMessage.success('导出错题集')
}

const clearMasteredQuestions = () => {
  ElMessage.success('清除已掌握题目')
}

const addToFavorites = (questionId) => {
  ElMessage.success('添加到收藏')
}

const addNote = (questionId) => {
  ElMessage.success('添加学习笔记')
}

const markAsReviewed = (questionId) => {
  ElMessage.success('标记为已掌握')
}

const viewExamReport = (recordId) => {
  ElMessage.success('查看详细报告')
}

const downloadCertificate = (recordId) => {
  ElMessage.success('下载证书')
}

const retakeExam = (recordId) => {
  ElMessage.success('重新考试')
}

const startPractice = (modeId) => {
  ElMessage.success(`开始${modeId}练习`)
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
.exam-overview, .wrong-questions, .exam-records, .practice-modes, .achievements {
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

/* 考试概览样式 */
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

/* 特考试项目 */
.special-exam {
  margin-top: 20px;
}

.exam-card {
  display: flex;
  align-items: center;
  gap: 16px;
  padding: 20px;
  background: linear-gradient(135deg, #fff3cd, #ffeaa7);
  border-radius: 12px;
  border-left: 4px solid #ffc107;
}

.exam-icon {
  font-size: 24px;
  width: 50px;
  height: 50px;
  display: flex;
  align-items: center;
  justify-content: center;
  background: rgba(255, 193, 7, 0.2);
  border-radius: 10px;
}

.exam-info {
  flex: 1;
}

.exam-name {
  font-weight: 600;
  font-size: 16px;
  color: #333;
  margin-bottom: 8px;
}

.exam-meta {
  font-size: 14px;
  color: #666;
  margin-bottom: 8px;
}

.exam-tags {
  display: flex;
  align-items: center;
  gap: 12px;
}

.tag {
  padding: 2px 8px;
  border-radius: 10px;
  font-size: 12px;
  font-weight: 500;
}

.tag.urgent {
  background: #dc3545;
  color: white;
}

.tag.hard {
  background: #dc3545;
  color: white;
}

.tag.important {
  background: #ffc107;
  color: #333;
}

.tag.error {
  background: #ffc107;
  color: #333;
}

.tag.excellent {
  background: #ffc107;
  color: #333;
}

.tag.failed {
  background: #dc3545;
  color: white;
}

.exam-details {
  color: #666;
  font-size: 14px;
}

/* 错题集样式 */
.wrong-stats {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
  padding: 16px;
  background: #f8f9fa;
  border-radius: 10px;
}

.wrong-summary {
  display: flex;
  gap: 20px;
  font-size: 14px;
  color: #666;
}

.wrong-actions {
  display: flex;
  gap: 8px;
}

.question-list {
  display: flex;
  flex-direction: column;
  gap: 16px;
  margin-bottom: 20px;
}

.question-item {
  background: white;
  padding: 16px;
  border-radius: 10px;
  border-left: 4px solid #ffc107;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.question-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 12px;
}

.question-tags {
  display: flex;
  align-items: center;
  gap: 8px;
}

.question-source {
  color: #666;
  font-size: 14px;
}

.error-count {
  color: #666;
  font-size: 12px;
}

.question-content {
  font-weight: 500;
  margin-bottom: 12px;
  color: #333;
}

.question-options {
  margin-bottom: 12px;
}

.option {
  display: flex;
  gap: 8px;
  margin-bottom: 6px;
  font-size: 14px;
}

.option-label {
  font-weight: 600;
  min-width: 20px;
}

.option-text.correct {
  color: #28a745;
}

.option-text.wrong {
  color: #dc3545;
}

.question-answer {
  background: #e3f2fd;
  padding: 12px;
  border-radius: 8px;
  margin-bottom: 12px;
  font-size: 14px;
}

.answer-label {
  font-weight: 600;
  color: #1976d2;
}

.answer-text {
  color: #333;
}

.question-actions {
  display: flex;
  gap: 8px;
}

.wrong-bottom-actions {
  display: flex;
  justify-content: center;
  gap: 12px;
  margin-top: 20px;
}

/* 考试记录样式 */
.record-filters {
  display: flex;
  gap: 8px;
  margin-bottom: 20px;
}

.record-filter {
  padding: 6px 12px;
  border-radius: 12px;
  font-size: 12px;
  cursor: pointer;
  transition: all 0.3s ease;
  background: #f5f5f5;
  color: #666;
}

.record-filter:hover {
  background: rgba(102, 126, 234, 0.1);
  color: #667eea;
}

.record-filter.active {
  background: #667eea;
  color: white;
}

.record-list {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.record-item {
  display: flex;
  align-items: center;
  gap: 16px;
  padding: 16px;
  border-radius: 10px;
  border-left: 4px solid #28a745;
}

.record-item.excellent {
  background: #e8f5e8;
  border-left-color: #ffc107;
}

.record-item.failed {
  background: #ffebee;
  border-left-color: #dc3545;
}

.record-info {
  flex: 1;
}

.record-name {
  font-weight: 600;
  margin-bottom: 4px;
  color: #333;
}

.record-meta {
  font-size: 14px;
  color: #666;
  margin-bottom: 6px;
}

.record-details {
  display: flex;
  align-items: center;
  gap: 10px;
}

.record-accuracy {
  color: #666;
  font-size: 14px;
}

.record-score {
  text-align: center;
  margin-right: 16px;
}

.score-number {
  font-size: 24px;
  font-weight: bold;
}

.score-number.excellent {
  color: #ffc107;
}

.score-number.failed {
  color: #dc3545;
}

.score-label {
  font-size: 12px;
  color: #666;
}

.record-actions {
  display: flex;
  flex-direction: column;
  gap: 6px;
}

/* 练习模式样式 */
.practice-modes {
  grid-column: 1 / -1;
}

.practice-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  gap: 20px;
}

.practice-card {
  color: white;
  padding: 20px;
  border-radius: 15px;
  text-align: center;
  cursor: pointer;
  transition: transform 0.3s ease;
}

.practice-card:hover {
  transform: translateY(-2px);
}

.practice-icon {
  font-size: 32px;
  margin-bottom: 10px;
}

.practice-title {
  font-size: 18px;
  font-weight: 600;
  margin-bottom: 10px;
}

.practice-desc {
  font-size: 14px;
  margin-bottom: 15px;
  opacity: 0.9;
}

.practice-meta {
  background: rgba(255, 255, 255, 0.2);
  padding: 8px;
  border-radius: 8px;
  font-size: 12px;
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
  border-radius: 15px;
  transition: all 0.3s ease;
  border: 2px dashed #6c757d;
  background: #f8f9fa;
  opacity: 0.7;
}

.achievement-card.earned {
  background: #d4edda;
  border: 2px solid #28a745;
  opacity: 1;
}

.achievement-card:hover {
  transform: translateY(-2px);
}

.achievement-icon {
  font-size: 48px;
}

.achievement-info {
  flex: 1;
}

.achievement-title {
  font-weight: 600;
  margin-bottom: 6px;
  color: #333;
}

.achievement-card.earned .achievement-title {
  color: #155724;
}

.achievement-desc {
  color: #666;
  font-size: 14px;
  margin-bottom: 8px;
}

.achievement-date {
  font-size: 12px;
  color: #155724;
}

.achievement-progress {
  font-size: 12px;
  color: #6c757d;
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
  
  .record-item {
    flex-direction: column;
    align-items: stretch;
    gap: 12px;
  }
  
  .record-actions {
    flex-direction: row;
    justify-content: center;
  }
}
</style>