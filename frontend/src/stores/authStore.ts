import { defineStore } from 'pinia'
import { loginApi, type LoginDto } from '../api/authApi'

interface User {
  id: string
  username: string
}

interface State {
  token: string
  user: User | null
}

export const useAuthStore = defineStore('auth', {
  state: (): State => ({
    token: localStorage.getItem('access_token') || '',
    user: localStorage.getItem('user')
      ? JSON.parse(localStorage.getItem('user') as string)
      : null,
  }),
  actions: {
    async login(data: LoginDto) {
      const res = await loginApi(data)
      this.token = res.token
      this.user = res.user
      localStorage.setItem('access_token', res.token)
      localStorage.setItem('user', JSON.stringify(res.user))
    },
    logout() {
      localStorage.removeItem('access_token')
      localStorage.removeItem('user')
      this.token = ''
      this.user = null
    },
  },
})
