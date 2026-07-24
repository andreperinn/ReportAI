# ReportAI

Sistema que recebe documentos (PDF/CSV), usa IA para analisá-los, permite conversar com o documento e gera relatórios profissionais.

## Como rodar

```bash
npm install
npm run dev
```

Copie `.env.example` para `.env` e preencha as variáveis antes de rodar contra um back-end real.

## Estrutura de pastas

```
src/
  components/
    layout/     -> Header, Sidebar
    upload/     -> Upload de PDF/CSV
    chat/       -> Chat com o documento
    report/     -> Geração e visualização de relatórios
    common/     -> Botões, loaders, componentes reutilizáveis
  pages/        -> Telas (Home, Dashboard, DocumentView, ReportPage)
  hooks/        -> useDocument, useChat
  services/     -> Chamadas de API (documentService, aiService)
  context/      -> Estado global (documento ativo)
  routes/       -> Definição de rotas
  utils/        -> Funções auxiliares
```

## Fluxo principal

1. Usuário sobe um PDF/CSV em `Home`.
2. Back-end processa e extrai conteúdo do documento.
3. Usuário conversa com o documento em `DocumentView` (chat com IA).
4. Usuário gera um relatório profissional em `ReportPage`.

## Próximos passos sugeridos

- [ ] Definir contrato da API com o back-end (endpoints em `documentService.js` e `aiService.js`)
- [ ] Implementar preview real de PDF (ex: `react-pdf`) e tabela para CSV
- [ ] Estilizar componentes (Tailwind ou CSS Modules)
- [ ] Autenticação de usuário
- [ ] Exportação do relatório para PDF/DOCX
