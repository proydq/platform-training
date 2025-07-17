import http from '../utils/http'

export interface LoginDto {
  username: string
  password: string
}

export function loginApi(data: LoginDto) {
  return http.post<{ token: string; user: { id: string; username: string } }>(
    '/auth/login',
    data
  )
}
