import { Routes, Route } from 'react-router-dom'
import Chat from '../pages/Chat.jsx'
import Dashboard from '../pages/Dashboard.jsx'
import ReportPage from '../pages/ReportPage.jsx'

function AppRoutes() {
  return (
    <Routes>
      {/* Mesma tela pros dois casos: sem :id é o estado "em branco" (anexar
          documento pra começar); com :id já entra direto na conversa. */}
      <Route path="/" element={<Chat />} />
      <Route path="/document/:id" element={<Chat />} />
      <Route path="/document/:id/report" element={<ReportPage />} />
      <Route path="/dashboard" element={<Dashboard />} />
    </Routes>
  )
}

export default AppRoutes
