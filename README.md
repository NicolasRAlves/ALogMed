
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

## 📦 Estrutura do Projeto

### `config`
Configurações gerais do sistema.

### `controller`
Endpoints REST da aplicação.

### `dto`
Objetos de transferência de dados entre backend e frontend.

### `entity`
Modelos JPA representando as tabelas e relações do banco de dados.

### `exception`
Exceções personalizadas.

### `repository`
Interfaces de acesso ao banco.

### `security`
Configurações de autenticação/autorização (Spring Security).

### `service`
Lógica de negócio e regras da aplicação.

---

## 🧱 Entidades Principais

### `User (Usuário)`

| Campo         | Tipo        | Descrição |
|---------------|-------------|-----------|
| id            | Long        | Identificador |
| name, email   | String      | Nome e email |
| cpf, rg       | String      | Documentos |
| role          | Enum (Role) | PATIENT, DOCTOR, ADMIN, etc. |
| status        | Enum        | ACTIVE, INACTIVE |
| age, weight, height | Int/Double | Informações físicas |
| city, state   | String      | Endereço |
| crm, specialty| String      | CRM e especialidade do médico |
| password      | String      | Senha criptografada |

### `Appointment (Agendamento)`

| Campo           | Tipo        | Descrição |
|------------------|-------------|-----------|
| id               | Long        | Identificador |
| patient          | User        | Paciente agendado |
| doctor           | User        | Médico responsável |
| specialty        | Enum        | Especialidade da consulta |
| type             | Enum        | CONSULTA ou EXAME |
| date             | LocalDate   | Data do agendamento |
| time             | LocalTime   | Horário |
| hospital         | String      | Nome do hospital |
| hospitalAddress  | String      | Endereço do hospital |
| status           | Enum        | AGENDADA, CONFIRMADA, CANCELADA, FINALIZADA |
| notes            | String      | Observações |

### `MedicalRecord (Registro Médico)`

| Campo         | Tipo        | Descrição |
|---------------|-------------|-----------|
| id            | Long        | Identificador |
| patient       | User        | Paciente |
| doctor        | User        | Médico |
| appointment   | Appointment | Consulta associada |
| description   | String      | Texto do atendimento |
| diagnosis     | String      | Diagnóstico |
| createdAt     | LocalDateTime | Data de criação |

### `Prescription (Prescrição)`

| Campo     | Tipo      | Descrição |
|------------|-----------|-----------|
| id         | Long      | Identificador |
| record     | MedicalRecord | Prontuário vinculado |
| name       | String    | Nome do medicamento |
| posology   | String    | Posologia |
| startDate  | LocalDate | Início do tratamento |
| duration   | int       | Duração em dias |

### `DoctorAbsence (Ausência do Doutor)`

| Campo     | Tipo      | Descrição |
|------------|-----------|-----------|
| id         | Long      | Identificador |
| doctor     | User      | Médico ausente |
| date       | LocalDate | Dia de ausência |
| reason     | String    | Motivo (opcional) |

---

## 📋 Funcionalidades por Atores

### Médico
- `GET /api/users/patients` – lista pacientes
- `GET /api/appointments/my` – histórico de atendimentos
- `POST /api/appointments` – cria agendamento
- `PATCH /api/appointments/{id}/cancel` – cancela agendamento
- `POST /api/medical-records` – registra prontuário
- `POST /api/prescriptions` – registra prescrição
- `POST /api/doctors/absences` – marca ausência

### Paciente
- `POST /api/appointments` – agendar
- `PATCH /api/appointments/{id}/cancel` – cancelar
- `PATCH /api/appointments/{id}/confirm` – confirmar
- `PATCH /api/appointments/{id}/finish` – finalizar
- `GET /api/appointments/history` – histórico pessoal

### Administração
- `GET /api/appointments` – ver todos
- `GET /api/users` – listar usuários
- `POST /api/users` – cadastrar usuário
- `PUT /api/users/{id}` – editar usuário
- `PATCH /api/users/{id}/deactivate` – desativar usuário

---

## 🧠 Regras de Negócio

- Um médico só pode ter 1 agendamento por horário
- Consulta/exame só pode ser criada se o médico estiver disponível
- Consulta precisa ser confirmada para ser ativa
- Prontuário só pode ser criado após a consulta
- Paciente só finaliza consulta após a data
- Admin pode usar filtros por data, status, médico e paciente
