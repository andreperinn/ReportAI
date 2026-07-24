# 📄 ReportAI

<p align="center">
  <img src="docs/banner.png" alt="ReportAI Banner" width="900">
</p>

<p align="center">

![Java](https://img.shields.io/badge/Java-21-orange?style=for-the-badge)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-4.x-green?style=for-the-badge)
![React](https://img.shields.io/badge/React-19-61DAFB?style=for-the-badge)
![Vite](https://img.shields.io/badge/Vite-7-646CFF?style=for-the-badge)
![Gemini](https://img.shields.io/badge/Google-Gemini-blue?style=for-the-badge)
![H2](https://img.shields.io/badge/Database-H2-blue?style=for-the-badge)
![License](https://img.shields.io/badge/License-MIT-success?style=for-the-badge)

</p>

---

# 🚀 Sobre

O **ReportAI** é uma aplicação Full Stack que utiliza Inteligência Artificial para analisar documentos enviados pelo usuário.

Após fazer upload de um **PDF** ou **CSV**, a aplicação:

- 📄 extrai automaticamente o texto;
- 🤖 permite conversar com o documento através de IA;
- 📊 gera relatórios profissionais em Markdown;
- 💾 salva o histórico da conversa.

A ideia do projeto foi reproduzir uma experiência semelhante ao Claude ou ChatGPT, porém focada na análise de documentos corporativos.

---

# ✨ Funcionalidades

- Upload de PDF
- Upload de CSV
- Extração automática de texto
- Chat contextual com IA
- Histórico de conversas
- Geração automática de relatórios
- API REST
- Banco de dados
- Interface moderna inspirada no Claude

---

# 📷 Screenshots

## Home

![Home](docs/home.jpeg)

---

## Chat

![Chat](docs/chat.jpeg)

---

## Relatório

![Relatório](docs/relatorio.jpeg)

---

# 🏗 Arquitetura

```
                React + Vite
                     │
                     │ HTTP / JSON
                     ▼
            Spring Boot REST API
                     │
        ┌────────────┼────────────┐
        │            │            │
     PDFBox     Commons CSV    Gemini API
        │            │            │
        └────────────┼────────────┘
                     │
                    H2
```

---

# 🛠 Tecnologias

## Frontend

- React
- Vite
- React Router
- Axios
- CSS

---

## Backend

- Java 21
- Spring Boot
- Spring Data JPA
- Hibernate
- Maven
- H2 Database
- Lombok
- Jackson
- Apache PDFBox
- Apache Commons CSV

---

## Inteligência Artificial

- Google Gemini API

---

# 📂 Estrutura do Projeto

```
reportai/

├── frontend/
│
│   ├── components/
│   ├── hooks/
│   ├── pages/
│   ├── services/
│   └── utils/
│
└── backend/
    │
    ├── controller/
    ├── service/
    ├── repository/
    ├── entity/
    ├── dto/
    ├── mapper/
    ├── ai/
    ├── config/
    └── util/
```

---

# ⚙ Fluxo da Aplicação

```text
Upload do documento

        │

        ▼

Extração do texto

        │

        ▼

Armazenamento

        │

        ▼

Envio para IA

        │

        ▼

Chat com documento

        │

        ▼

Relatório final
```

---

# 🚀 Como executar

## Backend

```bash
git clone https://github.com/andreperinn/ReportAI.git

cd reportai-backend

./mvnw spring-boot:run
```

---

## Frontend

```bash
cd reportai-frontend

npm install

npm run dev
```

---

## Variáveis de ambiente

```env
AI_API_KEY=SUA_CHAVE_DO_GEMINI
```

---

# 📡 API

## Upload

```
POST /api/documents
```

---

## Buscar documento

```
GET /api/documents/{id}
```

---

## Conversar

```
POST /api/documents/{id}/chat
```

---

## Histórico

```
GET /api/documents/{id}/chat
```

---

## Gerar relatório

```
POST /api/documents/{id}/report
```

---

# 💡 Principais Aprendizados

Durante o desenvolvimento foram aplicados conceitos como:

- Arquitetura REST
- Clean Architecture
- Spring Boot
- React Hooks
- Consumo de APIs
- Integração com IA
- Manipulação de PDF
- Persistência de dados
- Engenharia de Software

---

# 🔮 Melhorias Futuras

- Login de usuários
- PostgreSQL
- Docker
- Deploy na nuvem
- Processamento assíncrono
- Preview do PDF
- Exportação em PDF
- Dashboard de estatísticas

---

# 👨‍💻 Autor

## André Perin

Estudante de Engenharia de Software apaixonado por desenvolvimento Full Stack e Inteligência Artificial.

LinkedIn:
https://www.linkedin.com/in/andr%C3%A9-perin-a7242b2b7/

GitHub:
https://github.com/andreperinn

---

# ⭐ Gostou do projeto?

Se este projeto foi interessante, deixe uma ⭐ no repositório.
