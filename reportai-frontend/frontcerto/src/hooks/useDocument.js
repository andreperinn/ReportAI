import { useState, useCallback } from 'react'
import { uploadDocument, getDocument } from '../services/documentService.js'

// Hook para gerenciar upload/estado de um documento
export function useDocument() {
  const [document, setDocument] = useState(null)
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState(null)

  const upload = useCallback(async (file) => {
    setLoading(true)
    setError(null)
    try {
      const result = await uploadDocument(file)
      setDocument(result)
      return result
    } catch (err) {
      setError(err)
      throw err
    } finally {
      setLoading(false)
    }
  }, [])

  const fetchDocument = useCallback(async (id) => {
    setLoading(true)
    try {
      const result = await getDocument(id)
      setDocument(result)
    } finally {
      setLoading(false)
    }
  }, [])

  return { document, loading, error, upload, fetchDocument }
}
