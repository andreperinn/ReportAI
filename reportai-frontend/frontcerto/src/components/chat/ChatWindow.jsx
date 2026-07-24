import { useState, useRef, useEffect } from 'react'
import ChatMessage from './ChatMessage.jsx'

// Janela de chat para conversar com o documento
function ChatWindow({ messages = [], loading = false, disabled = false, onSend }) {
  const [draft, setDraft] = useState('')
  const messagesEndRef = useRef(null)
  const textareaRef = useRef(null)

  useEffect(() => {
    messagesEndRef.current?.scrollIntoView({ behavior: 'smooth' })
  }, [messages, loading])

  // Textarea cresce conforme o texto, até um limite, igual chats modernos
  useEffect(() => {
    const el = textareaRef.current
    if (!el) return
    el.style.height = 'auto'
    el.style.height = Math.min(el.scrollHeight, 160) + 'px'
  }, [draft])

  const submit = () => {
    const trimmed = draft.trim()
    if (!trimmed || disabled) return
    onSend?.(trimmed)
    setDraft('')
  }

  const handleKeyDown = (e) => {
    if (e.key === 'Enter' && !e.shiftKey) {
      e.preventDefault()
      submit()
    }
  }

  return (
    <div className="chat-window">
      <div className="chat-messages">
        {messages.length === 0 && !loading && (
          <p className="chat-empty">Nenhuma pergunta ainda. Pergunte algo sobre o documento abaixo.</p>
        )}
        {messages.map((msg) => (
          <ChatMessage key={msg.id} message={msg} />
        ))}
        {loading && (
          <div className="chat-turn chat-turn--assistant">
            <div className="chat-avatar" aria-hidden="true">IA</div>
            <div className="chat-message chat-message--pending">Analisando o documento...</div>
          </div>
        )}
        <div ref={messagesEndRef} />
      </div>

      <div className="chat-input-row">
        <textarea
          ref={textareaRef}
          rows={1}
          className="chat-input"
          placeholder="Pergunte algo sobre o documento..."
          value={draft}
          disabled={disabled}
          onChange={(e) => setDraft(e.target.value)}
          onKeyDown={handleKeyDown}
        />
        <button
          type="button"
          className="btn btn--primary chat-send"
          disabled={disabled || !draft.trim()}
          onClick={submit}
        >
          Enviar
        </button>
      </div>
    </div>
  )
}

export default ChatWindow
