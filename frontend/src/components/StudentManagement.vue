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
        <div class="content-card">
          <!-- 页面标题和操作按钮 -->
          <div class="page-header">
            <h2>👥 学员管理</h2>
            <div class="header-actions">
              <el-button type="primary" @click="exportStudentData">
                📥 导出数据
              </el-button>
              <el-button type="default" @click="sendBatchNotification">
                📢 批量通知
              </el-button>
              <el-button type="default" @click="addNewStudent">
                ➕ 添加学员
              </el-button>
            </div>
          </div>
          
          <!-- 学员搜索和筛选 -->
          <div class="student-filters">
            <el-input
              v-model="searchKeyword"
              placeholder="搜索学员姓名或邮箱..."
              @input="searchStudents"
              class="search-input"
            >
              <template #prefix>
                <span>🔍</span>
              </template>
            </el-input>
            
            <el-select v-model="departmentFilter" placeholder="全部部门" @change="filterStudents">
              <el-option label="全部部门" value=""></el-option>
              <el-option label="产品部" value="product"></el-option>
              <el-option label="技术部" value="technology"></el-option>
              <el-option label="市场部" value="marketing"></el-option>
              <el-option label="设计部" value="design"></el-option>
              <el-option label="人事部" value="hr"></el-option>
            </el-select>
            
            <el-select v-model="statusFilter" placeholder="全部状态" @change="filterStudents">
              <el-option label="全部状态" value=""></el-option>
              <el-option label="活跃" value="active"></el-option>
              <el-option label="不活跃" value="inactive"></el-option>
              <el-option label="已完成" value="completed"></el-option>
              <el-option label="待开始" value="pending"></el-option>
            </el-select>
            
            <el-select v-model="progressFilter" placeholder="全部进度" @change="filterStudents">
              <el-option label="全部进度" value=""></el-option>
              <el-option label="高进度(80%+)" value="high"></el-option>
              <el-option label="中等进度(50-80%)" value="medium"></el-option>
              <el-option label="低进度(<50%)" value="low"></el-option>
            </el-select>
          </div>
          
          <!-- 学员统计概览 -->
          <div class="student-stats">
            <div class="stat-item">
              <div class="stat-value">156</div>
              <div class="stat-desc">总学员数</div>
            </div>
            <div class="stat-item">
              <div class="stat-value">89%</div>
              <div class="stat-desc">活跃率</div>
            </div>
            <div class="stat-item">
              <div class="stat-value">78%</div>
              <div class="stat-desc">平均完成率</div>
            </div>
            <div class="stat-item">
              <div class="stat-value">85</div>
              <div class="stat-desc">平均分</div>
            </div>
          </div>
          
          <!-- 学员列表 -->
          <div class="student-table-container">
            <el-table 
              :data="filteredStudents" 
              style="width: 100%"
              @selection-change="handleSelectionChange"
            >
              <el-table-column type="selection" width="55"></el-table-column>
              
              <el-table-column label="学员信息" min-width="200">
                <template #default="scope">
                  <div class="student-info">
                    <div class="student-avatar">{{ scope.row.avatar }}</div>
                    <div class="student-details">
                      <div class="student-name">{{ scope.row.name }}</div>
                      <div class="student-email">{{ scope.row.email }}</div>
                    </div>
                  </div>
                </template>
              </el-table-column>
              
              <el-table-column label="部门" width="120">
                <template #default="scope">
                  {{ getDepartmentName(scope.row.department) }}
                </template>
              </el-table-column>
              
              <el-table-column label="学习进度" width="150">
                <template #default="scope">
                  <div class="progress-container">
                    <el-progress 
                      :percentage="scope.row.progress" 
                      :color="getProgressColor(scope.row.progress)"
                      :stroke-width="8"
                    ></el-progress>
                  </div>
                </template>
              </el-table-column>
              
              <el-table-column label="考试成绩" width="100">
                <template #default="scope">
                  <div class="score-display">
                    <div class="score-number" :style="{ color: getScoreColor(scope.row.avgScore) }">
                      {{ scope.row.avgScore }}
                    </div>
                    <div class="score-unit">分</div>
                  </div>
                </template>
              </el-table-column>
              
              <el-table-column label="状态" width="100">
                <template #default="scope">
                  <el-tag :type="getStatusType(scope.row.status)">
                    {{ getStatusText(scope.row.status) }}
                  </el-tag>
                </template>
              </el-table-column>
              
              <el-table-column label="最后活跃" width="120">
                <template #default="scope">
                  {{ scope.row.lastActive }}
                </template>
              </el-table-column>
              
              <el-table-column label="操作" width="200">
                <template #default="scope">
                  <el-button size="small" type="primary" @click="viewStudentDetail(scope.row)">
                    详情
                  </el-button>
                  <el-button size="small" type="default" @click="sendMessageToStudent(scope.row)">
                    消息
                  </el-button>
                  <el-button size="small" type="default" @click="sendReminderToStudent(scope.row)">
                    提醒
                  </el-button>
                </template>
              </el-table-column>
            </el-table>
          </div>
          
          <!-- 分页 -->
          <div class="pagination-container">
            <div class="pagination-info">
              <span>共 <strong>{{ totalStudents }}</strong> 名学员</span>
              <span style="margin-left: 20px;">每页显示 
                <el-select v-model="pageSize" @change="handlePageSizeChange" style="width: 80px;">
                  <el-option label="10" :value="10"></el-option>
                  <el-option label="20" :value="20"></el-option>
                  <el-option label="50" :value="50"></el-option>
                </el-select> 条
              </span>
            </div>
            <el-pagination
              v-model:current-page="currentPage"
              :page-size="pageSize"
              :total="totalStudents"
              layout="prev, pager, next"
              @current-change="handleCurrentChange"
            />
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'StudentManagement',
  data() {
    return {
      // 菜单项
      menuItems: [
        { name: '仪表板', icon: '📊', active: false },
        { name: '我的课程', icon: '📚', active: false },
        { name: '考试中心', icon: '📝', active: false },
        { name: '学员管理', icon: '👥', active: true },
        { name: '管理后台', icon: '⚙️', active: false }
      ],
      
      // 统计数据
      statsData: [
        { number: '156', label: '总学员数' },
        { number: '48', label: '课程总数' },
        { number: '89%', label: '系统活跃度' },
        { number: '2,340', label: '总学习时长' }
      ],
      
      // 搜索和筛选
      searchKeyword: '',
      departmentFilter: '',
      statusFilter: '',
      progressFilter: '',
      
      // 分页
      currentPage: 1,
      pageSize: 20,
      totalStudents: 156,
      
      // 选中的学员
      selectedStudents: [],
      
      // 学员数据
      studentData: [
        {
          id: 1,
          name: '张三',
          email: 'zhangsan@company.com',
          department: 'product',
          progress: 85,
          avgScore: 92,
          status: 'active',
          lastActive: '2小时前',
          avatar: '张'
        },
        {
          id: 2,
          name: '李四',
          email: 'lisi@company.com',
          department: 'technology',
          progress: 72,
          avgScore: 88,
          status: 'active',
          lastActive: '1天前',
          avatar: '李'
        },
        {
          id: 3,
          name: '王五',
          email: 'wangwu@company.com',
          department: 'marketing',
          progress: 45,
          avgScore: 76,
          status: 'inactive',
          lastActive: '3天前',
          avatar: '王'
        },
        {
          id: 4,
          name: '赵六',
          email: 'zhaoliu@company.com',
          department: 'design',
          progress: 95,
          avgScore: 96,
          status: 'completed',
          lastActive: '1小时前',
          avatar: '赵'
        },
        {
          id: 5,
          name: '钱七',
          email: 'qianqi@company.com',
          department: 'hr',
          progress: 15,
          avgScore: 65,
          status: 'pending',
          lastActive: '1周前',
          avatar: '钱'
        }
      ]
    }
  },
  
  computed: {
    filteredStudents() {
      let filtered = this.studentData
      
      // 搜索过滤
      if (this.searchKeyword) {
        const keyword = this.searchKeyword.toLowerCase()
        filtered = filtered.filter(student => 
          student.name.toLowerCase().includes(keyword) ||
          student.email.toLowerCase().includes(keyword)
        )
      }
      
      // 部门过滤
      if (this.departmentFilter) {
        filtered = filtered.filter(student => student.department === this.departmentFilter)
      }
      
      // 状态过滤
      if (this.statusFilter) {
        filtered = filtered.filter(student => student.status === this.statusFilter)
      }
      
      // 进度过滤
      if (this.progressFilter) {
        filtered = filtered.filter(student => {
          if (this.progressFilter === 'high') return student.progress >= 80
          if (this.progressFilter === 'medium') return student.progress >= 50 && student.progress < 80
          if (this.progressFilter === 'low') return student.progress < 50
          return true
        })
      }
      
      return filtered
    }
  },
  
  methods: {
    // 菜单切换
    setActiveMenu(index) {
      this.menuItems.forEach((item, i) => {
        item.active = i === index
      })
      
      // 这里可以添加路由跳转逻辑
      const routes = ['dashboard', 'courses', 'exams', 'student-management', 'admin']
      if (routes[index] && routes[index] !== 'student-management') {
        this.$router.push(`/${routes[index]}`)
      }
    },
    
    // 退出登录
    handleLogout() {
      this.$router.push('/login')
    },
    
    // 搜索学员
    searchStudents() {
      this.currentPage = 1
    },
    
    // 筛选学员
    filterStudents() {
      this.currentPage = 1
    },
    
    // 选择变化
    handleSelectionChange(selection) {
      this.selectedStudents = selection
    },
    
    // 分页变化
    handleCurrentChange(page) {
      this.currentPage = page
    },
    
    // 每页数量变化
    handlePageSizeChange(size) {
      this.pageSize = size
      this.currentPage = 1
    },
    
    // 导出学员数据
    exportStudentData() {
      if (this.selectedStudents.length === 0) {
        this.$message.warning('请选择要导出的学员')
        return
      }
      this.$message.success('学员数据导出完成！')
    },
    
    // 批量通知
    sendBatchNotification() {
      if (this.selectedStudents.length === 0) {
        this.$message.warning('请选择要通知的学员')
        return
      }
      this.$message.success(`已向 ${this.selectedStudents.length} 名学员发送通知`)
    },
    
    // 添加新学员
    addNewStudent() {
      this.$message.info('添加新学员功能正在开发中...')
    },
    
    // 查看学员详情
    viewStudentDetail(student) {
      this.$message.info(`正在查看 ${student.name} 的详细信息...`)
    },
    
    // 发送消息
    sendMessageToStudent(student) {
      this.$message.success(`消息已发送给 ${student.name}`)
    },
    
    // 发送提醒
    sendReminderToStudent(student) {
      this.$message.success(`学习提醒已发送给 ${student.name}`)
    },
    
    // 获取部门名称
    getDepartmentName(dept) {
      const names = {
        'product': '产品部',
        'technology': '技术部',
        'marketing': '市场部',
        'design': '设计部',
        'hr': '人事部'
      }
      return names[dept] || dept
    },
    
    // 获取进度颜色
    getProgressColor(progress) {
      if (progress >= 80) return '#67c23a'
      if (progress >= 50) return '#e6a23c'
      return '#f56c6c'
    },
    
    // 获取分数颜色
    getScoreColor(score) {
      if (score >= 90) return '#67c23a'
      if (score >= 80) return '#e6a23c'
      if (score >= 60) return '#f56c6c'
      return '#909399'
    },
    
    // 获取状态类型
    getStatusType(status) {
      const types = {
        'active': 'success',
        'inactive': 'warning',
        'completed': 'success',
        'pending': 'info'
      }
      return types[status] || 'info'
    },
    
    // 获取状态文本
    getStatusText(status) {
      const texts = {
        'active': '活跃',
        'inactive': '不活跃',
        'completed': '已完成',
        'pending': '待开始'
      }
      return texts[status] || status
    }
  }
}
</script>

<style scoped>
/* 主容器 */
.dashboard-wrapper {
  min-height: 100vh;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  padding: 0;
}

.dashboard-content {
  max-width: 1400px;
  margin: 0 auto;
  padding: 20px;
}

/* 导航栏样式 */
.navbar {
  background: rgba(255, 255, 255, 0.95);
  backdrop-filter: blur(10px);
  border-radius: 15px;
  margin-bottom: 30px;
  box-shadow: 0 8px 32px rgba(0, 0, 0, 0.1);
}

.navbar-content {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 15px 30px;
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
  gap: 30px;
}

.menu-item {
  padding: 10px 20px;
  border-radius: 25px;
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
  box-shadow: 0 4px 15px rgba(102, 126, 234, 0.3);
}

.navbar-right {
  display: flex;
  align-items: center;
  gap: 15px;
}

.username {
  font-weight: 500;
  color: #333;
}

.avatar {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  background: linear-gradient(135deg, #667eea, #764ba2);
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
  font-weight: 600;
}

.logout-btn {
  color: #666 !important;
}

/* 统计卡片 */
.stats-section {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  gap: 20px;
  margin-bottom: 30px;
}

.stat-card {
  background: rgba(255, 255, 255, 0.95);
  backdrop-filter: blur(10px);
  border-radius: 15px;
  padding: 25px;
  text-align: center;
  box-shadow: 0 8px 32px rgba(0, 0, 0, 0.1);
  transition: transform 0.3s ease;
}

.stat-card:hover {
  transform: translateY(-5px);
}

.stat-number {
  font-size: 32px;
  font-weight: 700;
  color: #333;
  margin-bottom: 8px;
}

.stat-label {
  color: #666;
  font-size: 14px;
}

/* 内容区域 */
.content-section {
  display: grid;
  gap: 30px;
}

.content-card {
  background: rgba(255, 255, 255, 0.95);
  backdrop-filter: blur(10px);
  border-radius: 15px;
  padding: 30px;
  box-shadow: 0 8px 32px rgba(0, 0, 0, 0.1);
}

.content-card:hover {
  transform: translateY(-2px);
  transition: transform 0.3s ease;
}

/* 页面头部 */
.page-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 30px;
}

.page-header h2 {
  margin: 0;
  color: #333;
  font-size: 24px;
}

.header-actions {
  display: flex;
  gap: 10px;
}

/* 筛选区域 */
.student-filters {
  display: flex;
  gap: 15px;
  margin-bottom: 20px;
  align-items: center;
}

.search-input {
  flex: 1;
}

/* 学员统计 */
.student-stats {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(120px, 1fr));
  gap: 15px;
  margin-bottom: 30px;
  padding: 20px;
  background: rgba(102, 126, 234, 0.1);
  border-radius: 10px;
}

.stat-item {
  text-align: center;
}

.stat-value {
  font-size: 24px;
  font-weight: bold;
  color: #333;
  margin-bottom: 5px;
}

.stat-desc {
  color: #666;
  font-size: 14px;
}

/* 表格容器 */
.student-table-container {
  margin-bottom: 20px;
}

/* 学员信息 */
.student-info {
  display: flex;
  align-items: center;
  gap: 12px;
}

.student-avatar {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  background: linear-gradient(135deg, #667eea, #764ba2);
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
  font-weight: 600;
}

.student-details {
  flex: 1;
}

.student-name {
  font-weight: 600;
  color: #333;
  margin-bottom: 4px;
}

.student-email {
  color: #666;
  font-size: 12px;
}

/* 进度条 */
.progress-container {
  padding: 0 10px;
}

/* 分数显示 */
.score-display {
  text-align: center;
}

.score-number {
  font-size: 18px;
  font-weight: bold;
}

.score-unit {
  font-size: 12px;
  color: #666;
}

/* 分页 */
.pagination-container {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-top: 20px;
}

.pagination-info {
  display: flex;
  align-items: center;
}
</style>