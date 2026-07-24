import { createContext, useContext, useState } from 'react'

// Contexto global para compartilhar o documento ativo entre telas
const DocumentContext = createContext(null)

export function DocumentProvider({ children }) {
  const [activeDocument, setActiveDocument] = useState(null)

  return (
    <DocumentContext.Provider value={{ activeDocument, setActiveDocument }}>
      {children}
    </DocumentContext.Provider>
  )
}

export function useDocumentContext() {
  const ctx = useContext(DocumentContext)
  if (!ctx) throw new Error('useDocumentContext deve ser usado dentro de DocumentProvider')
  return ctx
}
