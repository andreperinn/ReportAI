import { formatDate } from '../../utils/formatters.js'

// Conversor leve de Markdown -> JSX (só o suficiente pro que o Gemini gera:
// #/##/### para títulos, - para listas, **negrito** inline). Evita depender
// de uma lib externa só pra isso.
function renderMarkdown(text) {
  const lines = text.split('\n')
  const blocks = []
  let listBuffer = []

  const flushList = () => {
    if (listBuffer.length > 0) {
      blocks.push(
        <ul key={`list-${blocks.length}`}>
          {listBuffer.map((item, i) => (
            <li key={i}>{renderInline(item)}</li>
          ))}
        </ul>
      )
      listBuffer = []
    }
  }

  lines.forEach((line, index) => {
    const trimmed = line.trim()

    if (trimmed.startsWith('### ')) {
      flushList()
      blocks.push(<h3 key={index}>{renderInline(trimmed.slice(4))}</h3>)
    } else if (trimmed.startsWith('## ')) {
      flushList()
      blocks.push(<h2 key={index}>{renderInline(trimmed.slice(3))}</h2>)
    } else if (trimmed.startsWith('# ')) {
      flushList()
      blocks.push(<h1 key={index}>{renderInline(trimmed.slice(2))}</h1>)
    } else if (trimmed.startsWith('- ') || trimmed.startsWith('* ')) {
      listBuffer.push(trimmed.slice(2))
    } else if (trimmed === '') {
      flushList()
    } else {
      flushList()
      blocks.push(<p key={index}>{renderInline(trimmed)}</p>)
    }
  })

  flushList()
  return blocks
}

// Negrito **assim** dentro de uma linha
function renderInline(text) {
  const parts = text.split(/(\*\*[^*]+\*\*)/g)
  return parts.map((part, i) =>
    part.startsWith('**') && part.endsWith('**') ? <strong key={i}>{part.slice(2, -2)}</strong> : part
  )
}

// Exibe o relatório gerado (preview antes de exportar)
function ReportViewer({ report }) {
  if (!report) return null

  return (
    <div className="report-paper">
      <span className="report-timestamp">Gerado em {formatDate(report.createdAt)}</span>
      {renderMarkdown(report.content)}
    </div>
  )
}

export default ReportViewer
