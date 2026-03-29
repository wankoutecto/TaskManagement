# Task Manager App

A simple application that allows users to create and manage tasks.

---

## 🚀 Features

- Add new tasks  
- Display tasks in a table  
- Simple and clean UI  

---

## 🛠 Tech Stack

- Frontend: React  
- Backend: Sprint boot
- Database: Postgres

---

## 💻 Running Locally

### 🔧 Backend

1️⃣ Clone the repository
```bash
git clone https://github.com/wankoutecto/TaskManagement.git
```
2️⃣ Set up PostgreSQL

Create a database (example: TaskManagement_db)

Update application.properties in src/main/resources/:
```bash
spring.datasource.url=jdbc:postgresql://localhost:5432/TaskManagement_db
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
```
3️⃣ Build & run backend

Navigate to the backend folder:
```bash
cd ../TaskManagement_backend
```
Windows (PowerShell):
```bash
.\mvnw.cmd clean install
.\mvnw.cmd spring-boot:run
```
Linux / WSL:
```bash
./mvnw clean install
./mvnw spring-boot:run
```

### 🎨 Frontend

Navigate to frontend folder
```bash
cd ../TaskManagement_frontend
```
1️⃣ Install dependencies
```bash
npm install
```
2️⃣ Start the frontend
```bash
npm run dev
```
3️⃣ Open in browser

http://localhost:5173/



