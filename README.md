# 🩺 Documentação Backend – ALog Med

---

## 🎯 Objetivo

O sistema **ALog Med** visa automatizar o agendamento de consultas e exames, otimizar o atendimento ao paciente e fornecer uma gestão eficiente para clínicas médicas.

---

## 👥 Atores do Sistema

### 🩺 Médico
- Consultar lista de pacientes
- Visualizar seu histórico de atendimentos
- Agendar consultas para um paciente
- Registrar **prontuário** e **exames**
- Prescrever **medicação** com nome, posologia, início e duração
- Cancelar agendamentos
- Informar **indisponibilidades**
- Ver detalhes da consulta, incluindo data, horário e local

### 🧍 Paciente
- Agendar **consulta** ou **exame**
- Selecionar **especialidade** e **data**
- O sistema direciona para um médico **disponível**
- Pode cancelar, confirmar ou finalizar um agendamento
- Acessar histórico de atendimentos

### 🧑‍💼 Administração
- Gerenciar todos os usuários
- Criar, editar e desativar usuários
- Agendar e cancelar consultas
- Visualizar agendamentos com filtros por médico, data, paciente e status
- Acompanhar históricos de atendimento

---

## 🧱 Entidades

### `User (Usuário)`

| Campo           | Tipo        | Descrição                            |
|------------------|-------------|----------------------------------------|
| id               | Long        | Identificador único                   |
| name             | String      | Nome do usuário                       |
| email            | String      | E-mail                                |
| password         | String      | Senha criptografada                   |
| cpf, rg          | String      | Documentos                            |
| role             | Enum        | PATIENT, DOCTOR, ADMIN, etc.          |
| status           | Enum        | ACTIVE, INACTIVE                      |
| age, weight, height | int/double | Informações físicas               |
| state, city      | String      | Endereço                              |
| crm              | String      | Registro médico (se for médico)       |
| specialty        | String      | Especialidade médica                  |

---

### `Appointment (Agendamento)`

| Campo           | Tipo        | Descrição                            |
|------------------|-------------|----------------------------------------|
| id               | Long        | Identificador                         |
| patient          | User        | Paciente responsável                  |
| doctor           | User        | Médico designado                      |
| type             | Enum        | CONSULTA ou EXAME                     |
| specialty        | Enum        | Especialidade                         |
| date             | LocalDate   | Data do agendamento                   |
| time             | LocalTime   | Horário do agendamento                |
| hospital         | String      | Nome do hospital                      |
| hospitalAddress  | String      | Endereço do hospital                  |
| status           | Enum        | AGENDADA, CONFIRMADA, CANCELADA, FINALIZADA |
| notes            | String      | Observações gerais                    |

---

### `MedicalRecord (Registro Médico)`

| Campo         | Tipo            | Descrição                             |
|----------------|------------------|----------------------------------------|
| id             | Long             | Identificador                         |
| patient        | User             | Paciente atendido                     |
| doctor         | User             | Médico responsável                    |
| appointment    | Appointment      | Consulta associada                    |
| description    | String           | Relato da consulta                    |
| diagnosis      | String           | Diagnóstico médico                    |
| createdAt      | LocalDateTime    | Data de criação do prontuário        |

---

### `Prescription (Prescrição Médica)`

| Campo         | Tipo         | Descrição                             |
|----------------|--------------|----------------------------------------|
| id             | Long         | Identificador                         |
| record         | MedicalRecord| Referência ao prontuário              |
| name           | String       | Nome do medicamento                   |
| posology       | String       | Posologia (ex: 2x ao dia)             |
| startDate      | LocalDate    | Início do tratamento                  |
| duration       | int          | Duração em dias                       |

---

### `DoctorAbsence (Indisponibilidade Médica)`

| Campo         | Tipo      | Descrição                                |
|----------------|-----------|-------------------------------------------|
| id             | Long      | Identificador                            |
| doctor         | User      | Médico que estará ausente                |
| date           | LocalDate | Data da ausência                         |
| reason         | String    | Motivo (opcional)                        |

---

## 🔐 Segurança

### `AuthController (Autenticação)`
- `POST /api/auth/login`: autentica usuário e retorna token JWT
- `POST /api/auth/refresh`: gera novo token a partir do refresh token

### JWT Token
- `JwtTokenProvider`: gera e valida tokens JWT
- `JwtAuthenticationFilter`: intercepta requisições com token Bearer
- `SecurityConfig`: configura rotas protegidas

---

## 📊 Relatórios

### `ReportController`
- `GET /api/reports/summary`: visão geral de agendamentos
- `GET /api/reports/top-doctors`: médicos com mais atendimentos
- `GET /api/reports/cancellations`: taxa de cancelamento

### `ReportService`
- Implementa lógica com filtros por data, status, etc.
- Utiliza `AppointmentRepository` com queries personalizadas

---

## 📋 Funcionalidades por Perfil

### Médico
| Ação                        | Método | Rota                                      |
|-----------------------------|--------|-------------------------------------------|
| Listar pacientes            | GET    | `/api/users/patients`                     |
| Ver histórico de consultas  | GET    | `/api/appointments/my`                    |
| Agendar consulta            | POST   | `/api/appointments`                       |
| Cancelar consulta           | PATCH  | `/api/appointments/{id}/cancel`           |
| Criar prontuário            | POST   | `/api/medical-records`                    |
| Prescrever medicamento      | POST   | `/api/prescriptions`                      |
| Registrar ausência          | POST   | `/api/doctors/absences`                   |

---

### Paciente
| Ação                        | Método | Rota                                      |
|-----------------------------|--------|-------------------------------------------|
| Agendar consulta/exame      | POST   | `/api/appointments`                       |
| Cancelar agendamento        | PATCH  | `/api/appointments/{id}/cancel`           |
| Confirmar agendamento       | PATCH  | `/api/appointments/{id}/confirm`          |
| Finalizar consulta          | PATCH  | `/api/appointments/{id}/finish`           |
| Ver histórico               | GET    | `/api/appointments/history`               |

---

### Administração
| Ação                        | Método | Rota                                      |
|-----------------------------|--------|-------------------------------------------|
| Ver todos os agendamentos   | GET    | `/api/appointments`                       |
| Listar usuários             | GET    | `/api/users`                              |
| Cadastrar usuário           | POST   | `/api/users`                              |
| Editar usuário              | PUT    | `/api/users/{id}`                         |
| Desativar usuário           | PATCH  | `/api/users/{id}/deactivate`              |
| Acessar relatório geral     | GET    | `/api/reports/summary`                    |
| Ver médicos com mais atend. | GET    | `/api/reports/top-doctors`                |
| Ver taxa de cancelamento    | GET    | `/api/reports/cancellations`              |

---

## 🧠 Regras de Negócio

- Um médico só pode ter 1 agendamento por horário
- Consultas/exames só podem ser marcados em horários disponíveis
- Médico não pode ser agendado em dias de ausência
- Prontuário só pode ser criado após a realização da consulta
- Paciente só pode finalizar consulta/exame após a data
- Agendamentos devem ser confirmados para serem considerados ativos
- Administração pode filtrar por data, status, médico e paciente nos relatórios


---

## 👥 Integrantes do Projeto

- **Diogo da Silva Almeida**
- **Erika de Oliveira Nunes Carneiro**
- **Gabriel de Lima Carreiro** *(Líder do grupo)*
- **Nicolas Rodrigues Alves** *(Product Owner)*
- **Pedro Ballastreri Sclaffani Sampaio**
- **Ryan Jesus**

---

## 🛠️ Estruturação e Desenvolvimento

A estrutura do backend está sendo desenvolvida e organizada por **Nicolas Rodrigues Alves**, que atua como **Product Owner (PO)** do projeto.
