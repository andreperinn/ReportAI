import { useEffect, useState } from 'react'
import { Link } from 'react-router-dom'
import { listDocuments } from '../services/documentService.js'
import { formatDate } from '../utils/formatters.js'

const STATUS_LABEL = {
  RECEIVED: 'Recebido',
  PROCESSING: 'Processando',
  PROCESSED: 'Pronto',
  FAILED: 'Falhou',
}

// Lista de documentos processados / histórico
function Dashboard() {
  const [documents, setDocuments] = useState(null)
  const [error, setError] = useState(null)

  useEffect(() => {
    listDocuments()
      .then(setDocuments)
      .catch(setError)
  }, [])

  return (
    <div className="page">
      <div className="page-header">
        <span className="eyebrow">Arquivo</span>
        <h2 className="page-title">Meus documentos</h2>
        <p className="page-subtitle">Tudo que você já enviou pro ReportAI, com o status de cada um.</p>
      </div>

      {error && <div className="form-error">Não foi possível carregar seus documentos.</div>}

      {!documents && !error && <p className="loading-state">Carregando...</p>}

      {documents && documents.length === 0 && (
        <div className="empty-state">
          Nenhum documento enviado ainda.{' '}
          <Link to="/">Envie o primeiro</Link>.
        </div>
      )}

      {documents && documents.length > 0 && (
        <div className="doc-list">
          {documents.map((doc, index) => (
            <Link key={doc.id} to={`/document/${doc.id}`} className="doc-row">
              <span className="doc-row-icon">{String(index + 1).padStart(2, '0')}</span>
              <div className="doc-row-main">
                <div className="doc-row-name">{doc.filename}</div>
                <div className="doc-row-meta">{formatDate(doc.createdAt)}</div>
              </div>
              <span className={`badge badge--${doc.status.toLowerCase()}`}>
                {STATUS_LABEL[doc.status] ?? doc.status}
              </span>
            </Link>
          ))}
        </div>
      )}
    </div>
  )
}

export default Dashboard
