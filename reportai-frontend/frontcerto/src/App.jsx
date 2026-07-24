import AppRoutes from './routes/AppRoutes.jsx'
import Header from './components/layout/Header.jsx'

function App() {
  return (
    <div className="app-shell">
      <Header />
      <main className="app-content">
        <AppRoutes />
      </main>
    </div>
  )
}

export default App
