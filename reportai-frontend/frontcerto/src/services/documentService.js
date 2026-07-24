import { api } from './api.js'

// Envia o arquivo (PDF/CSV) para o back-end processar
export async function uploadDocument(file) {
  const formData = new FormData()
  formData.append('file', file)

  const { data } = await api.post('/documents', formData)
  return data
}

export async function getDocument(id) {
  const { data } = await api.get(`/documents/${id}`)
  return data
}

export async function listDocuments() {
  const { data } = await api.get('/documents')
  return data
}
