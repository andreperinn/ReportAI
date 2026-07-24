import { api } from './api.js'

// Envia uma mensagem no chat sobre o documento e retorna a resposta da IA
export async function sendChatMessage(documentId, message) {
  const { data } = await api.post(`/documents/${documentId}/chat`, { message })
  return data.reply
}

// Busca o histórico de mensagens já trocadas com o documento
export async function getChatHistory(documentId) {
  const { data } = await api.get(`/documents/${documentId}/chat`)
  return data
}

// Solicita a geração do relatório final a partir da análise do documento
export async function generateReport(documentId, options = {}) {
  const { data } = await api.post(`/documents/${documentId}/report`, options)
  return data
}

// Busca o relatório mais recente já gerado para o documento (se existir)
export async function getReport(documentId) {
  const { data } = await api.get(`/documents/${documentId}/report`)
  return data
}
