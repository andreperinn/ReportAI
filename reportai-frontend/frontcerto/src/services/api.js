import axios from 'axios'

// Instância base do axios apontando para o back-end Spring Boot
export const api = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080/api',
})
