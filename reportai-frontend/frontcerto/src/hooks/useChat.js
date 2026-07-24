import { useState, useCallback, useEffect } from 'react'
import { sendChatMessage, getChatHistory } from '../services/aiService.js'

// Hook para gerenciar o chat com o documento
export function useChat(documentId) {
  const [messages, setMessages] = useState([])
  const [loading, setLoading] = useState(false)
  const [loadingHistory, setLoadingHistory] = useState(true)
  const [error, setError] = useState(null)

  useEffect(() => {
    if (!documentId) return

    let cancelled = false

    getChatHistory(documentId)
      .then((history) => {
        if (cancelled) return
        setMessages(history.map((m) => ({ id: m.id, role: m.role.toLowerCase(), content: m.content })))
      })
      .catch((err) => {
        if (!cancelled) setError(err)
      })
      .finally(() => {
        if (!cancelled) setLoadingHistory(false)
      })

    return () => {
      cancelled = true
    }
  }, [documentId])

  const sendMessage = useCallback(async (content) => {
    const userMessage = { id: Date.now(), role: 'user', content }
    setMessages((prev) => [...prev, userMessage])
    setLoading(true)
    setError(null)
    try {
      const reply = await sendChatMessage(documentId, content)
      setMessages((prev) => [...prev, { id: Date.now() + 1, role: 'assistant', content: reply }])
    } catch (err) {
      setError(err)
      throw err
    } finally {
      setLoading(false)
    }
  }, [documentId])

  return { messages, loading, loadingHistory, error, sendMessage }
}
