import { useState, useRef } from 'react'

const PaperclipIcon = () => (
  <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="1.8">
    <path
      d="M21 12.5l-8.5 8.5a4.5 4.5 0 01-6.36-6.36l9.19-9.19a3 3 0 014.24 4.24l-9.19 9.19a1.5 1.5 0 01-2.12-2.12l8.5-8.5"
      strokeLinecap="round"
      strokeLinejoin="round"
    />
  </svg>
)

// Barra de anexar documento — substitui a antiga caixa de upload grande por
// algo mais parecido com o composer de um chat (clique ou arraste o arquivo).
function AttachComposer({ onAttach, loading }) {
  const [isDragging, setIsDragging] = useState(false)
  const inputRef = useRef(null)

  const handleFile = (file) => {
    if (!file || loading) return
    const validTypes = ['application/pdf', 'text/csv']
    if (!validTypes.includes(file.type)) {
      console.warn('Tipo de arquivo não suportado:', file.type)
      return
    }
    onAttach?.(file)
  }

  const handleDrop = (e) => {
    e.preventDefault()
    setIsDragging(false)
    if (loading) return
    handleFile(e.dataTransfer.files?.[0])
  }

  return (
    <div
      className={`attach-composer${isDragging ? ' is-dragging' : ''}${loading ? ' is-loading' : ''}`}
      onDragOver={(e) => {
        e.preventDefault()
        if (!loading) setIsDragging(true)
      }}
      onDragLeave={() => setIsDragging(false)}
      onDrop={handleDrop}
      onClick={() => !loading && inputRef.current?.click()}
      role="button"
      tabIndex={0}
    >
      <span className="attach-composer-icon">
        <PaperclipIcon />
      </span>
      <span className="attach-composer-label">
        {loading ? 'Lendo o documento...' : 'Anexe um PDF ou CSV para começar a conversa'}
      </span>
      <span className="attach-composer-hint">até 20MB</span>
      <input
        ref={inputRef}
        type="file"
        accept=".pdf,.csv"
        disabled={loading}
        onChange={(e) => handleFile(e.target.files?.[0])}
      />
    </div>
  )
}

export default AttachComposer
