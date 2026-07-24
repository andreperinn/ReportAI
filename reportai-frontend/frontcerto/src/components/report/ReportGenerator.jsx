import { useState } from 'react'

const STYLES = [
  { value: 'EXECUTIVE', label: 'Executivo' },
  { value: 'DETAILED', label: 'Detalhado' },
  { value: 'SUMMARY', label: 'Resumo' },
]

// Aciona a geração do relatório via IA a partir do documento analisado
function ReportGenerator({ onGenerate, loading }) {
  const [style, setStyle] = useState('DETAILED')

  return (
    <div>
      <div className="report-style-picker">
        {STYLES.map((s) => (
          <button
            key={s.value}
            type="button"
            className={`report-style-option${style === s.value ? ' is-selected' : ''}`}
            onClick={() => setStyle(s.value)}
            disabled={loading}
          >
            {s.label}
          </button>
        ))}
      </div>

      <button className="btn btn--primary" onClick={() => onGenerate(style)} disabled={loading}>
        {loading ? 'Gerando relatório...' : 'Gerar relatório'}
      </button>
    </div>
  )
}

export default ReportGenerator
