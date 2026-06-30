# 📄 Resume ATS (Applicant Tracking System) with AI Feedback Engine (Ollama AI)

## 🚀 Overview

Resume ATS is an intelligent full-stack web application designed to automate resume screening, parsing, and job matching using **Spring Boot + MySQL + AI (Ollama LLM)**.

The system extracts structured data from resumes (PDF), matches it with job descriptions, and generates **AI-powered feedback** to help candidates improve their profiles and increase job matching accuracy.

This project simulates a real-world Applicant Tracking System (ATS) used in modern recruitment platforms.

---

## 🎯 Problem Statement

Manual resume screening is time-consuming, inconsistent, and lacks personalized feedback.

This project solves it by:
- Automating resume parsing
- Matching skills with job descriptions
- Generating AI-based feedback using Ollama LLM
- Providing role-based dashboards (Admin/User)

---

## ✨ Key Features

- 📤 Resume Upload (PDF)
- 📄 Resume Parsing (Apache Tika)
- 🧠 Skill Extraction
- 🔍 Job Matching System
- 🤖 AI Feedback using Ollama
- 👤 Role-Based Access Control
- 📊 Admin Dashboard
- 📁 Resume Management System
- 📥 Export Data (CSV/PDF)
- 📚 Feedback History Tracking

---

## 🏗️ System Architecture

- Controller Layer → Request handling
- Service Layer → Business logic + AI processing
- Repository Layer → Database access (JPA)
- View Layer → Thymeleaf UI
- AI Layer → Ollama LLM integration

---

## 🛠️ Tech Stack

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

### AI
- Ollama LLM (Local AI Model)

### Libraries
- Apache Tika
- Apache Commons CSV
- iText PDF

---

## 🤖 AI Integration (Ollama)

This project uses **Ollama AI (Local LLM)** to generate intelligent resume feedback.

### AI Features:
- Resume quality analysis
- Skill gap detection
- Job match scoring
- Personalized suggestions
- Career improvement tips

---

## 🔄 Workflow

1. User logs in
2. Uploads resume (PDF)
3. Apache Tika extracts text
4. Data is parsed into structured format
5. User selects job description
6. System runs:
   - Skill matching
   - AI analysis (Ollama)
7. Feedback is generated
8. Results displayed + stored

---

## 👥 User Roles

### 🧑 USER
- Upload resume
- View AI feedback
- Check job matching results
- View history

### 👨‍💼 ADMIN
- Manage resumes
- View analytics
- Export reports (CSV/PDF)
- View feedback history

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

## ⚙️ Setup Instructions

### 1. Clone Project
```bash
git clone https://github.com/your-username/resume-ats.git
```

### 2. Open in IDE
- IntelliJ / Eclipse
- Import as Maven Project

### 3. Configure Database
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/ats_db
spring.datasource.username=root
spring.datasource.password=your_password
```

### 4. Run Project
```bash
mvn spring-boot:run
```

---

## 🌐 Access
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

---

## 👨‍💻 Author

**Ahmedbaig Inamdar**  
Full Stack Java Developer  
Spring Boot | AI | MySQL | Web Development

---

## ⭐ Highlights

✔ AI-powered ATS  
✔ Real-world recruitment system  
✔ Ollama LLM integration  
✔ Secure role-based access  
✔ Production-level architecture
