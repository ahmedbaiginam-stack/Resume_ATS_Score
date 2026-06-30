# 🚀 Resume ATS (Applicant Tracking System) with AI Feedback Engine

![Java](https://img.shields.io/badge/Java-17-blue?style=for-the-badge)
![Spring Boot](https://img.shields.io/badge/SpringBoot-3.0-green?style=for-the-badge)
![MySQL](https://img.shields.io/badge/MySQL-Database-orange?style=for-the-badge)
![Thymeleaf](https://img.shields.io/badge/Thymeleaf-Frontend-yellowgreen?style=for-the-badge)
![AI](https://img.shields.io/badge/Ollama-AI-purple?style=for-the-badge)
![Status](https://img.shields.io/badge/Project-Completed-success?style=for-the-badge)

---

## 📌 Overview

**Resume ATS** is an AI-powered full-stack web application designed to automate resume screening, skill extraction, and job matching using:

- Spring Boot (Backend)
- MySQL (Database)
- Thymeleaf (Frontend)
- Ollama AI (LLM-based feedback engine)

It intelligently analyzes resumes and provides **AI-driven career improvement suggestions**, simulating real-world ATS systems used in modern companies.

---

## 🎯 Key Features

- 📤 Upload Resume (PDF)
- 📄 Automatic Resume Parsing (Apache Tika)
- 🧠 Skill Extraction Engine
- 🔍 Job Description Matching System
- 🤖 AI Feedback using Ollama LLM
- 👤 Role-Based Access (Admin / User)
- 📊 Admin Analytics Dashboard
- 📚 Feedback History Tracking
- 📥 Export Reports (CSV / PDF)
- 🔎 Resume Search (Name, Skills, Email)

---

## 🏗️ System Architecture

```
Controller Layer  → Handles HTTP Requests
Service Layer     → Business Logic + AI Processing
Repository Layer  → Database Interaction (JPA)
View Layer        → Thymeleaf UI
AI Layer          → Ollama LLM Integration
```

---

## 🤖 AI Integration (Ollama)

This project integrates **Ollama LLM (Local AI Model)** to generate intelligent resume feedback.

### AI Capabilities:
- Resume quality evaluation
- Skill gap detection
- Job matching score
- Personalized improvement suggestions
- Career guidance recommendations

---

## ⚙️ Tech Stack

### Backend
- Java 17+
- Spring Boot
- Spring MVC
- Spring Security
- Spring Data JPA

### Frontend
- Thymeleaf
- HTML5
- Bootstrap 5

### Database
- MySQL

### AI Engine
- Ollama LLM (Local AI)

### Libraries
- Apache Tika (PDF Parsing)
- Apache Commons CSV (Export)
- iText PDF (Reports)

---

## 🔄 Workflow

1. User Login (Spring Security)
2. Upload Resume (PDF)
3. Extract Text using Apache Tika
4. Store structured data in MySQL
5. Select Job Description
6. Skill Matching Engine runs
7. AI Engine (Ollama) generates feedback
8. Results displayed to user/admin

---

## 👥 User Roles

### 🧑 User
- Upload Resume
- View AI Feedback
- Check Job Match Results
- View History

### 👨‍💼 Admin
- Manage All Resumes
- View Analytics Dashboard
- Export Data (CSV/PDF)
- View Feedback Logs

---

## 📸 Screenshots

### 🏠 User Dashboard
<img src="src/main/resources/static/screenshots/dashboard.png" width="750"/>

### 📤 Upload Resume
<img src="src/main/resources/static/screenshots/upload.png" width="750"/>

### 🔍 Jobs Page
<img src="src/main/resources/static/screenshots/jobs.png" width="750"/>

### 🤖 AI Feedback
<img src="src/main/resources/static/screenshots/feedback.png" width="750"/>

### 📚 Feedback History
<img src="src/main/resources/static/screenshots/feedback_history.png" width="750"/>

### 📊 Analytics
<img src="src/main/resources/static/screenshots/analytics.png" width="750"/>

### 🧑‍💼 Admin Dashboard
<img src="src/main/resources/static/screenshots/admin_dashboard.png" width="750"/>

### 📈 Admin Analytics Detail
<img src="src/main/resources/static/screenshots/admin_analytics.png" width="750"/>
---

## 📁 Project Structure

```
com.Resume.ATS
├── controller
├── service
├── repository
├── model
├── dto
├── security
├── util
├── config
└── templates
```

---

## 🚀 Setup Instructions

### 1️⃣ Clone Repository
```bash
git clone https://github.com/your-username/resume-ats.git
```

### 2️⃣ Import Project
- Open IntelliJ / Eclipse
- Import as Maven Project

### 3️⃣ Configure Database
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/ats_db
spring.datasource.username=root
spring.datasource.password=your_password
```

### 4️⃣ Run Project
```bash
mvn spring-boot:run
```

---

## 🌐 Application URL

```
http://localhost:8080
```

---

## 🚀 Future Enhancements

- AI Resume Ranking System
- Interview Question Generator
- Email Notifications
- Cloud Deployment (AWS / Azure)
- Advanced NLP Skill Detection
- Real-time AI Chat Assistant

---

## 👨‍💻 Author

**Ahmedbaig Inamdar**  
Full Stack Java Developer  
Spring Boot | MySQL | AI (Ollama) | Web Development

---

## ⭐ Highlights

✔ AI-powered ATS system  
✔ Real-world recruitment automation  
✔ Production-grade Spring Boot architecture  
✔ Ollama LLM integration  
✔ Role-based secure system
