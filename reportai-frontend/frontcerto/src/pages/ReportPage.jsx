import { useEffect, useState } from 'react'
import { useParams } from 'react-router-dom'
import ReportGenerator from '../components/report/ReportGenerator.jsx'
import ReportViewer from '../components/report/ReportViewer.jsx'
import { generateReport, getReport } from '../services/aiService.js'

// Geração e visualização do relatório final
function ReportPage() {
  const { id } = useParams()
  const [report, setReport] = useState(null)
  const [loading, setLoading] = useState(false)
  const [checkingExisting, setCheckingExisting] = useState(true)
  const [error, setError] = useState(null)

  useEffect(() => {
    getReport(id)
      .then(setReport)
      .catch(() => {
        // ainda não existe relatório gerado — comportamento normal, não é erro de verdade
      })
      .finally(() => setCheckingExisting(false))
  }, [id])

  const handleGenerate = async (style) => {
    setLoading(true)
    setError(null)
    try {
      const result = await generateReport(id, { style })
      setReport(result)
    } catch (err) {
      setError(err)
    } finally {
      setLoading(false)
    }
  }

  const errorMessage =
    error?.response?.data?.message || (error ? 'Não foi possível gerar o relatório. Tente novamente.' : null)

  return (
    <div className="page">
      <div className="page-header">
        <span className="eyebrow">Etapa 04 · Relatório</span>
        <h2 className="page-title">Relatório final</h2>
        <p className="page-subtitle">
          Escolha o estilo e gere um relatório profissional a partir do documento e da conversa.
        </p>
      </div>

      {!checkingExisting && <ReportGenerator onGenerate={handleGenerate} loading={loading} />}

      {errorMessage && <div className="form-error">{errorMessage}</div>}

      {checkingExisting && <p className="loading-state">Verificando relatórios anteriores...</p>}

      {report && (
        <div style={{ marginTop: 24 }}>
          <ReportViewer report={report} />
        </div>
      )}
    </div>
  )
}

export default ReportPage
