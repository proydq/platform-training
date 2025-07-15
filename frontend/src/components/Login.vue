<template>
  <div class="page-wrapper">
    <div class="login-container">
      <div class="login-card">
        <div class="login-logo">🎓</div>
        <h1 class="login-title">智能培训系统</h1>
        <p class="login-subtitle">公司内部产品培训平台</p>
        <el-form ref="loginForm" :model="form" :rules="rules" label-position="top">
        <el-form-item label="用户名" prop="username">
          <el-input v-model="form.username" placeholder="请输入用户名或邮箱" />
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="form.password" type="password" placeholder="请输入密码" show-password />
        </el-form-item>
        <div class="login-options">
          <el-checkbox v-model="form.remember">记住我</el-checkbox>
          <a href="#" class="forgot-password">忘记密码？</a>
        </div>
        <el-form-item>
          <el-button type="primary" class="login-btn" :loading="loading" @click="handleLogin">登录</el-button>
        </el-form-item>
      </el-form>
      <div class="register-link">
        还没有账号？<a href="#">联系管理员开通</a>
      </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { ElMessage } from 'element-plus'
import { useRouter } from 'vue-router'

const form = reactive({
  username: '',
  password: '',
  remember: false,
})

const rules = {
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }],
}

const loading = ref(false)
const loginForm = ref(null)
const router = useRouter()

function handleLogin() {
  loginForm.value.validate(async (valid) => {
    if (!valid) return
    loading.value = true
    await new Promise((resolve) => setTimeout(resolve, 1000))
    if (form.username === 'admin' && form.password === '123456') {
      ElMessage.success('欢迎登录')
      router.push('/dashboard')
    } else {
      ElMessage.error('用户名或密码错误')
    }
    loading.value = false
  })
}
</script>

<style scoped>
.page-wrapper {
  height: 100vh;
  width: 100vw;
  display: flex;
  justify-content: center;
  align-items: center;
}

.login-container {
  width: 100%;
  max-width: 450px;
}

.login-card {
  width: 100%;
  max-width: 450px;
  background: #fff;
  border-radius: 20px;
  padding: 40px 30px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  text-align: center;
}

.login-logo {
  font-size: 48px;
  margin-bottom: 10px;
}

.login-title {
  margin: 0;
  font-weight: bold;
  font-size: 24px;
  background: linear-gradient(90deg, #4f86ff, #8b2eff);
  -webkit-background-clip: text;
  color: transparent;
}

.login-subtitle {
  margin-bottom: 30px;
  color: #666;
  font-size: 14px;
}

.login-options {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.forgot-password {
  font-size: 12px;
  color: #409eff;
}

.register-link {
  margin-top: 20px;
  font-size: 12px;
}

.login-btn {
  width: 100%;
  color: #fff;
  background: linear-gradient(90deg, #6a82fb, #a06af9);
  border: none;
  transition: transform 0.2s;
}

.login-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.2);
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

