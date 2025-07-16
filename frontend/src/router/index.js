import { createRouter, createWebHistory } from 'vue-router'
import LoginView from '../views/LoginView.vue'
import MainLayout from '../layout/MainLayout.vue'
import DashboardView from '../views/DashboardView.vue'
import CoursesView from '../views/CoursesView.vue'
import ExamsView from '../views/ExamsView.vue'
import StudentManagement from '../views/StudentManagement.vue'
import AdminView from '../views/AdminView.vue'

const routes = [
  { path: '/', redirect: '/login' },
  { path: '/login', component: LoginView },
  {
    path: '/app',
    component: MainLayout,
    children: [
      { path: 'dashboard', component: DashboardView },
      { path: 'courses', component: CoursesView },
      { path: 'exams', component: ExamsView },
      { path: 'students', component: StudentManagement },
      { path: 'admin', component: AdminView }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
