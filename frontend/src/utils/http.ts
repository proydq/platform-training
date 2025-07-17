import axios, { AxiosRequestConfig, AxiosInstance } from 'axios'
import { ElMessage } from 'element-plus'

const baseURL = import.meta.env.VITE_API_BASE || 'http://localhost:8080/api/v1'

const instance: AxiosInstance = axios.create({ baseURL })

instance.interceptors.request.use((config) => {
  const token = localStorage.getItem('access_token')
  if (token) {
    config.headers = config.headers || {}
    config.headers.Authorization = `Bearer ${token}`
  }
  return config
})

instance.interceptors.response.use(
  (res) => {
    if (res.data.code === 200) {
      return res.data.data
    }
    ElMessage.error(res.data.message)
    return Promise.reject(res)
  },
  (err) => {
    const message = err.response?.data?.message || err.message
    ElMessage.error(message)
    return Promise.reject(err)
  }
)
export default function http<T = any>(config: AxiosRequestConfig): Promise<T> {
  return instance(config)
}
