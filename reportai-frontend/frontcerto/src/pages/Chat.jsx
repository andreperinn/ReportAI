import { useEffect } from 'react'
import { useParams, Link } from 'react-router-dom'
import ChatWindow from '../components/chat/ChatWindow.jsx'
import AttachComposer from '../components/upload/AttachComposer.jsx'
import { useDocument } from '../hooks/useDocument.js'
import { useChat } from '../hooks/useChat.js'

// Tela única de conversa — sem :id na URL, mostra o estado "em branco"
// (anexar documento pra começar); com :id, já entra direto na conversa.
// A transição entre os dois estados acontece sem navegar pra outro lugar,
// igual um chat de verdade.
function Chat() {
  const { id: routeId } = useParams()
  const { document, loading: uploadLoading, error: uploadError, upload, fetchDocument } = useDocument()

  const activeId = document?.id ?? routeId

  useEffect(() => {
    if (routeId && !document) {
      fetchDocument(routeId)
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [routeId])

  const { messages, loading: chatLoading, error: chatError, sendMessage } = useChat(activeId)

  const handleAttach = async (file) => {
    try {
      await upload(file)
    } catch (err) {
      // erro já fica em uploadError, exibido abaixo
    }
  }

  const attachErrorMessage =
    uploadError?.response?.data?.message || (uploadError ? 'Não foi possível enviar o documento.' : null)
  const chatErrorMessage =
    chatError?.response?.data?.message || (chatError ? 'Não foi possível enviar a mensagem.' : null)

  // ---------- Estado "em branco": ainda não há documento anexado ----------
  if (!activeId) {
    return (
      <div className="chat-page chat-page--landing">
        <div className="chat-landing">
          <span className="chat-landing-mark">ReportAI</span>
          <h1 className="chat-greeting">Olá. Vamos analisar um documento?</h1>
          <p className="chat-greeting-sub">
            Anexe um PDF ou CSV abaixo — a IA lê o conteúdo e você já pode conversar sobre ele.
          </p>
          <AttachComposer onAttach={handleAttach} loading={uploadLoading} />
          {attachErrorMessage && <div className="form-error">{attachErrorMessage}</div>}
        </div>
      </div>
    )
  }

  // ---------- Conversa em andamento ----------
  return (
    <div className="chat-page">
      <div className="chat-page-header">
        <h2 className="chat-page-title">{document ? document.filename : `Documento ${activeId}`}</h2>
        <Link className="btn btn--secondary" to={`/document/${activeId}/report`}>
          Gerar relatório →
        </Link>
      </div>

      <ChatWindow messages={messages} loading={chatLoading} disabled={chatLoading} onSend={sendMessage} />

      {chatErrorMessage && <div className="form-error">{chatErrorMessage}</div>}
    </div>
  )
}

export default Chat
