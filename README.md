# 🩺 ALog Med - Sistema de Gestão de Clínicas

O sistema **ALog Med** é uma plataforma completa projetada para automatizar o agendamento de consultas e exames, otimizar o fluxo de atendimento e fornecer uma gestão de dados eficiente para clínicas médicas, pacientes e doutores.

---
## 👥 Integrantes do Projeto

* **Diogo da Silva Almeida** 
* **Erika de Oliveira Nunes Carneiro** 
* **Gabriel de Lima Carreiro** *(Líder do Grupo)*
* **Nicolas Rodrigues Alves** *(Product Owner)* 
* **Pedro Ballastreri Sclaffani Sampaio** 
* **Ryan Jesus** 

---

## ✒️ Autoria e Desenvolvimento

Este projeto é o resultado do esforço colaborativo de uma equipe dedicada. Abaixo estão os integrantes e suas principais contribuições:

* **Nicolas Rodrigues Alves** ([NicolasRAlves](https://github.com/NicolasRAlves))
    * *Product Owner* e Desenvolvedor Full-Stack.
    * Responsável pela arquitetura e desenvolvimento do backend, criação de APIs, desenvolvimento de telas e componentes no frontend, design de UI/UX no Figma, e definição da estrutura geral do projeto.

* **Gabriel de Lima Carreiro** ([gabcrr](https://github.com/gabcrr))
    * *Líder do Grupo*.
    * Atuou na concepção do projeto, design de UI/UX no Figma, elaboração da documentação e apoio estratégico em todas as frentes de desenvolvimento.

* **Diogo da Silva Almeida** ([xDGxx](https://github.com/xDGxx))
    * Desenvolvedor Backend.
    * Contribuiu na implementação de regras de negócio, desenvolvimento de APIs e na elaboração da apresentação do projeto.

* **Pedro Ballastreri Sclaffani Sampaio** ([BallastreriPedro](https://github.com/BallastreriPedro))
    * Desenvolvedor Frontend.
    * Focado na construção de componentes, interfaces e na elaboração da apresentação do projeto.

* **Ryan Jesus** ([brucesantss](https://github.com/brucesantss))
    * Desenvolvedor Frontend.
    * Trabalhou na implementação das telas e na usabilidade da interface do usuário.

* **Erika de Oliveira Nunes Carneiro** ([Erikha-onc](https://github.com/Erikha-onc))
    * Documentação Técnica.
    * Responsável pela elaboração e organização da documentação do projeto e apoio geral à equipe.


---

A concepção e idealização do projeto foram lideradas por **Gabriel de Lima Carreiro** e **Nicolas Rodrigues Alves**.

  
---
## 🛠️ Tecnologias Utilizadas

* **Backend:** Java 17, Spring Boot 3, Spring Security, JPA/Hibernate, Maven
* **Frontend:** React 18, TypeScript, Vite, TailwindCSS, Axios
* **Banco de Dados:** MySQL
* **Autenticação:** JWT (JSON Web Tokens)

---
## 🌿 Estrutura de Branches (Git)

Para garantir a estabilidade do projeto, seguimos um fluxo de trabalho com branches específicas.

* **`main`**: É a branch de **produção**. Contém o código estável, testado e pronto para ser implantado. Ninguém deve enviar código diretamente para cá.
* **`backend/dev`**: É a branch de **desenvolvimento**. Todas as novas funcionalidades e correções são mescladas aqui primeiro. É a fonte de verdade para a próxima versão a ser lançada.

## 🚀 Como Executar o Projeto

Siga os passos abaixo para configurar e executar o ambiente de desenvolvimento localmente.

### Pré-requisitos
* Java JDK 17 ou superior
* Maven 3.8 ou superior
* Node.js 18 ou superior
* MySQL Server 8 ou superior

### 1. Backend (API Java)
```
# Navegue até a pasta do backend
cd alogmed-clinica

# Instale as dependências com o Maven
mvn clean install

# Antes de rodar, certifique-se de ter um banco de dados 'alogmed_db' criado
# e configure o acesso no arquivo 'application.properties'.

# Execute a aplicação
mvn spring-boot:run
```
O servidor do backend estará rodando em `http://localhost:8080`.

### 2. Frontend (Aplicação React)
```
# Em um novo terminal, navegue até a pasta do frontend
cd alogmed-frontend

# Instale as dependências do Node.js
npm install

# Execute o servidor de desenvolvimento
npm run dev
```
A aplicação frontend estará acessível em `http://localhost:5173`.


---
## 📋 Documentação da API

### Atores e Funcionalidades

O sistema possui 3 portais de acesso com funcionalidades distintas:

* **Portal do Paciente:** Permite agendar e gerenciar (confirmar/cancelar) consultas e exames, além de visualizar seu histórico médico e perfil.
* **Portal do Doutor:** Oferece ao médico a visualização de sua agenda, acesso aos prontuários dos pacientes e a capacidade de criar novos registros, incluindo diagnósticos e prescrições.
* **Portal Administrativo:** Centraliza a gestão de usuários (criação, edição, desativação) e a visualização completa de todos os agendamentos e relatórios gerenciais.

---
## 🧠 Regras de Negócio

* Um médico só pode ter 1 agendamento por horário.
* Consultas/exames só podem ser marcados em horários disponíveis.
* Médico não pode ser agendado em dias de ausência.
* Prontuário só pode ser criado após a realização da consulta.
* Paciente só pode finalizar consulta/exame após a data.
* Agendamentos devem ser confirmados para serem considerados ativos.
* Administração pode filtrar por data, status, médico e paciente nos relatórios.

