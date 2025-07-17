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
            <el-button type="primary" class="admin-btn">管理后台</el-button>
            <div class="user-info">
              <span class="user-name">管理员</span>
              <el-dropdown @command="handleCommand">
                <span class="user-avatar">王</span>
                <template #dropdown>
                  <el-dropdown-menu>
                    <el-dropdown-item command="logout">退出</el-dropdown-item>
                  </el-dropdown-menu>
                </template>
              </el-dropdown>
            </div>
          </div>
        </div>
      </div>
      
      <!-- 统计卡片区域 -->
      <div class="stats-section">
        <div class="stat-card">
          <div class="stat-number">156</div>
          <div class="stat-label">总学员</div>
        </div>
        <div class="stat-card">
          <div class="stat-number">48</div>
          <div class="stat-label">课程总数</div>
        </div>
        <div class="stat-card">
          <div class="stat-number">89%</div>
          <div class="stat-label">活跃率</div>
        </div>
        <div class="stat-card">
          <div class="stat-number">2,340</div>
          <div class="stat-label">总学习时长</div>
        </div>
      </div>
      
      <!-- 主要内容区域 -->
      <div class="content-section">
        <div class="content-card">
          <!-- 系统管理标题 -->
          <div class="page-header">
            <h2>⚙️ 系统管理</h2>
            <div class="header-actions">
              <el-button type="primary" @click="exportSystemData">
                📊 导出报表
              </el-button>
              <el-button type="default" @click="systemSettings">
                ⚙️ 系统设置
              </el-button>
              <el-button type="default" @click="backupData">
                💾 数据备份
              </el-button>
            </div>
          </div>
          
          <!-- 管理功能模块 -->
          <div class="admin-modules">
            <!-- 用户管理 -->
            <div class="module-card" @click="navigateToModule('users')">
              <div class="module-icon">👥</div>
              <div class="module-info">
                <h3>用户管理</h3>
                <p>管理系统用户、权限分配</p>
                <div class="module-stats">
                  <span>总用户: 156</span>
                  <span>在线: 23</span>
                </div>
              </div>
              <div class="module-arrow">→</div>
            </div>
            
            <!-- 课程管理 -->
            <div class="module-card" @click="navigateToModule('courses')">
              <div class="module-icon">📚</div>
              <div class="module-info">
                <h3>课程管理</h3>
                <p>课程内容、分类、发布管理</p>
                <div class="module-stats">
                  <span>总课程: 48</span>
                  <span>已发布: 42</span>
                </div>
              </div>
              <div class="module-arrow">→</div>
            </div>
            
            <!-- 考试管理 -->
            <div class="module-card" @click="navigateToModule('exams')">
              <div class="module-icon">📝</div>
              <div class="module-info">
                <h3>考试管理</h3>
                <p>试题库、考试安排、成绩统计</p>
                <div class="module-stats">
                  <span>题库: 1,250</span>
                  <span>考试: 15</span>
                </div>
              </div>
              <div class="module-arrow">→</div>
            </div>
            
            <!-- 数据统计 -->
            <div class="module-card" @click="navigateToModule('analytics')">
              <div class="module-icon">📊</div>
              <div class="module-info">
                <h3>数据统计</h3>
                <p>学习数据、使用情况分析</p>
                <div class="module-stats">
                  <span>报表: 12</span>
                  <span>更新: 今日</span>
                </div>
              </div>
              <div class="module-arrow">→</div>
            </div>
            
            <!-- 系统设置 -->
            <div class="module-card" @click="navigateToModule('settings')">
              <div class="module-icon">⚙️</div>
              <div class="module-info">
                <h3>系统设置</h3>
                <p>基础配置、安全设置</p>
                <div class="module-stats">
                  <span>配置: 正常</span>
                  <span>状态: 运行中</span>
                </div>
              </div>
              <div class="module-arrow">→</div>
            </div>
            
            <!-- 日志管理 -->
            <div class="module-card" @click="navigateToModule('logs')">
              <div class="module-icon">📋</div>
              <div class="module-info">
                <h3>日志管理</h3>
                <p>系统日志、操作记录</p>
                <div class="module-stats">
                  <span>今日: 1,234</span>
                  <span>错误: 0</span>
                </div>
              </div>
              <div class="module-arrow">→</div>
            </div>
          </div>
          
          <!-- 快速操作 -->
          <div class="quick-actions">
            <h3>快速操作</h3>
            <div class="action-buttons">
              <el-button type="primary" @click="createUser">
                👤 新建用户
              </el-button>
              <el-button type="success" @click="publishCourse">
                📚 发布课程
              </el-button>
              <el-button type="warning" @click="createExam">
                📝 创建考试
              </el-button>
              <el-button type="info" @click="viewReports">
                📊 查看报表
              </el-button>
              <el-button type="danger" @click="systemMaintenance">
                🔧 系统维护
              </el-button>
            </div>
          </div>
          
          <!-- 系统状态 -->
          <div class="system-status">
            <h3>系统状态</h3>
            <div class="status-grid">
              <div class="status-item">
                <div class="status-label">服务器状态</div>
                <div class="status-value good">正常</div>
              </div>
              <div class="status-item">
                <div class="status-label">数据库连接</div>
                <div class="status-value good">正常</div>
              </div>
              <div class="status-item">
                <div class="status-label">存储空间</div>
                <div class="status-value warning">78%</div>
              </div>
              <div class="status-item">
                <div class="status-label">内存使用</div>
                <div class="status-value good">45%</div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { ElMessage } from 'element-plus'

export default {
  name: 'Admin',
  data() {
    return {
      menuItems: [
        { icon: '📊', name: '仪表板', active: false },
        { icon: '📚', name: '我的课程', active: false },
        { icon: '📝', name: '考试中心', active: false },
        { icon: '👥', name: '学员管理', active: false },
        { icon: '⚙️', name: '管理后台', active: true }
      ]
    }
  },
  
  methods: {
    // 设置激活菜单
    setActiveMenu(index) {
      this.menuItems.forEach((item, i) => {
        item.active = i === index
      })
      
      const routes = ['dashboard', 'courses', 'exams', 'students', 'admin']
      if (routes[index] && routes[index] !== 'admin') {
        this.$router.push(`/${routes[index]}`)
      }
    },
    
    // 处理下拉菜单命令
    handleCommand(command) {
      if (command === 'logout') {
        this.handleLogout()
      }
    },
    
    // 退出登录
    handleLogout() {
      this.$router.push('/login')
    },
    
    // 导出系统数据
    exportSystemData() {
      ElMessage.success('正在导出系统报表...')
    },
    
    // 系统设置
    systemSettings() {
      ElMessage.info('打开系统设置')
    },
    
    // 数据备份
    backupData() {
      ElMessage.success('开始数据备份...')
    },
    
    // 导航到模块
    navigateToModule(module) {
      ElMessage.info(`进入${module}管理模块`)
    },
    
    // 快速操作方法
    createUser() {
      ElMessage.success('打开新建用户界面')
    },
    
    publishCourse() {
      ElMessage.success('打开课程发布界面')
    },
    
    createExam() {
      ElMessage.success('打开考试创建界面')
    },
    
    viewReports() {
      ElMessage.info('查看系统报表')
    },
    
    systemMaintenance() {
      ElMessage.warning('进入系统维护模式')
    }
  }
}
</script>

<style scoped>
/* 主容器 */
.dashboard-wrapper {
  min-height: 100vh;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  padding: 20px;
}

.dashboard-content {
  max-width: 1400px;
  margin: 0 auto;
}

/* 导航栏 */
.navbar {
  background: rgba(255, 255, 255, 0.95);
  border-radius: 15px;
  padding: 15px 25px;
  margin-bottom: 20px;
  backdrop-filter: blur(10px);
  box-shadow: 0 8px 32px rgba(0, 0, 0, 0.1);
}

.navbar-content {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.navbar-left {
  display: flex;
  align-items: center;
  gap: 10px;
}

.system-icon {
  font-size: 24px;
}

.system-name {
  font-size: 18px;
  font-weight: 600;
  color: #2c3e50;
}

.navbar-menu {
  display: flex;
  gap: 30px;
}

.menu-item {
  padding: 8px 16px;
  border-radius: 8px;
  cursor: pointer;
  transition: all 0.3s ease;
  font-weight: 500;
  color: #666;
}

.menu-item:hover {
  background: rgba(102, 126, 234, 0.1);
  color: #667eea;
}

.menu-item.active {
  background: linear-gradient(135deg, #667eea, #764ba2);
  color: white;
}

.navbar-right {
  display: flex;
  align-items: center;
  gap: 15px;
}

.admin-btn {
  background: linear-gradient(135deg, #667eea, #764ba2);
  border: none;
  border-radius: 8px;
}

.user-info {
  display: flex;
  align-items: center;
  gap: 10px;
}

.user-name {
  font-weight: 500;
  color: #2c3e50;
}

.user-avatar {
  width: 32px;
  height: 32px;
  border-radius: 50%;
  background: linear-gradient(135deg, #667eea, #764ba2);
  color: white;
  display: flex;
  align-items: center;
  justify-content: center;
  font-weight: 600;
  cursor: pointer;
}

/* 统计卡片 */
.stats-section {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  gap: 20px;
  margin-bottom: 20px;
}

.stat-card {
  background: rgba(255, 255, 255, 0.95);
  border-radius: 15px;
  padding: 25px;
  text-align: center;
  backdrop-filter: blur(10px);
  box-shadow: 0 8px 32px rgba(0, 0, 0, 0.1);
  transition: transform 0.3s ease;
}

.stat-card:hover {
  transform: translateY(-5px);
}

.stat-number {
  font-size: 36px;
  font-weight: 700;
  color: #667eea;
  margin-bottom: 8px;
}

.stat-label {
  font-size: 14px;
  color: #666;
  font-weight: 500;
}

/* 内容区域 */
.content-section {
  margin-top: 20px;
}

.content-card {
  background: rgba(255, 255, 255, 0.95);
  border-radius: 15px;
  padding: 30px;
  backdrop-filter: blur(10px);
  box-shadow: 0 8px 32px rgba(0, 0, 0, 0.1);
}

.content-card:hover {
  box-shadow: 0 12px 40px rgba(0, 0, 0, 0.15);
}

/* 页面标题 */
.page-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 30px;
  padding-bottom: 20px;
  border-bottom: 2px solid #f0f0f0;
}

.page-header h2 {
  margin: 0;
  color: #2c3e50;
  font-size: 24px;
}

.header-actions {
  display: flex;
  gap: 10px;
}

/* 管理模块 */
.admin-modules {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(350px, 1fr));
  gap: 20px;
  margin-bottom: 40px;
}

.module-card {
  background: #f8f9fa;
  border-radius: 12px;
  padding: 20px;
  display: flex;
  align-items: center;
  gap: 15px;
  cursor: pointer;
  transition: all 0.3s ease;
  border: 2px solid transparent;
}

.module-card:hover {
  background: #e3f2fd;
  border-color: #667eea;
  transform: translateY(-2px);
}

.module-icon {
  font-size: 32px;
  width: 60px;
  height: 60px;
  display: flex;
  align-items: center;
  justify-content: center;
  background: rgba(102, 126, 234, 0.1);
  border-radius: 12px;
}

.module-info {
  flex: 1;
}

.module-info h3 {
  margin: 0 0 5px 0;
  color: #2c3e50;
  font-size: 16px;
}

.module-info p {
  margin: 0 0 8px 0;
  color: #666;
  font-size: 14px;
}

.module-stats {
  display: flex;
  gap: 15px;
  font-size: 12px;
  color: #999;
}

.module-arrow {
  font-size: 20px;
  color: #667eea;
  font-weight: bold;
}

/* 快速操作 */
.quick-actions {
  margin-bottom: 40px;
}

.quick-actions h3 {
  margin-bottom: 20px;
  color: #2c3e50;
}

.action-buttons {
  display: flex;
  flex-wrap: wrap;
  gap: 15px;
}

/* 系统状态 */
.system-status h3 {
  margin-bottom: 20px;
  color: #2c3e50;
}

.status-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  gap: 15px;
}

.status-item {
  background: #f8f9fa;
  border-radius: 8px;
  padding: 15px;
  text-align: center;
}

.status-label {
  font-size: 14px;
  color: #666;
  margin-bottom: 8px;
}

.status-value {
  font-size: 16px;
  font-weight: 600;
}

.status-value.good {
  color: #28a745;
}

.status-value.warning {
  color: #ffc107;
}

.status-value.error {
  color: #dc3545;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .navbar-menu {
    display: none;
  }
  
  .page-header {
    flex-direction: column;
    gap: 15px;
    align-items: flex-start;
  }
  
  .header-actions {
    width: 100%;
    justify-content: flex-start;
    flex-wrap: wrap;
  }
  
  .admin-modules {
    grid-template-columns: 1fr;
  }
  
  .action-buttons {
    justify-content: center;
  }
}
</style>