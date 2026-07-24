import { Link } from 'react-router-dom'

function Header() {
  return (
    <header className="app-header">
      <Link to="/" style={{ textDecoration: 'none' }}>
        <h1>ReportAI</h1>
      </Link>
      <nav style={{ display: 'flex', alignItems: 'center', gap: 20 }}>
        <Link to="/dashboard" className="btn btn--ghost">
          Meus documentos
        </Link>
        <span className="masthead-tag">Dossiê Digital</span>
      </nav>
    </header>
  )
}

export default Header
