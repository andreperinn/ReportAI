function ChatMessage({ message }) {
  const { role, content } = message
  const isUser = role === 'user'

  return (
    <div className={`chat-turn chat-turn--${role}`}>
      {!isUser && <div className="chat-avatar" aria-hidden="true">IA</div>}
      <div className={`chat-message chat-message--${role}`}>{content}</div>
    </div>
  )
}

export default ChatMessage
