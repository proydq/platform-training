import http from '../utils/http'

export interface LoginDto {
  username: string
  password: string
}

export const loginApi = (data: LoginDto) =>
  http({ method: 'post', url: '/auth/login', data }) as Promise<{
    token: string
    user: { id: string; username: string }
  }>;

