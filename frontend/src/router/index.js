import { createRouter, createWebHistory } from 'vue-router'
import Login from '../components/Login.vue'
import Dashboard from '../components/Dashboard.vue'
import Courses from '../components/Courses.vue'
import Exams from '../components/Exams.vue'
import StudentManagement from '../components/StudentManagement.vue'
import Admin from '../components/Admin.vue'
import { useAuthStore } from '../stores/authStore'

const routes = [
  {
    path: '/',
    redirect: '/login'
  },
  {
    path: '/login',
    name: 'Login',
    component: Login
  },
  {
    path: '/dashboard',
    name: 'Dashboard',
    component: Dashboard
  },
  {
    path: '/courses',
    name: 'Courses',
    component: Courses
  },
  {
    path: '/exams',
    name: 'Exams',
    component: Exams
  },
  {
    path: '/students',
    name: 'StudentManagement',
    component: StudentManagement
  },
  {
    path: '/admin',
    name: 'Admin',
    component: Admin
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach((to, from, next) => {
  const auth = useAuthStore()
  if (to.path !== '/login' && !auth.token) {
    next('/login')
  } else {
    next()
  }
})

export default router