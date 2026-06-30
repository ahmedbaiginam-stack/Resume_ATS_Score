📄 Resume ATS (Applicant Tracking System) with AI Feedback Engine
🚀 Overview

The Resume ATS (Applicant Tracking System) is a full-stack web application designed to automate resume screening, skill extraction, and job matching using Spring Boot + AI (Ollama LLM).

It allows users to upload resumes, extract structured information, compare it with job descriptions, and generate intelligent AI-powered feedback to improve resume quality and job matching accuracy.

🎯 Key Features
📤 Upload Resume (PDF format)
📄 Automatic Resume Parsing using Apache Tika
🧠 Skill Extraction from resumes
🔍 Job Description Matching
🤖 AI-powered Feedback using Ollama LLM
👤 Role-based Access (Admin / User)
📊 Resume Analytics Dashboard
📥 Export Data (CSV & PDF)
🔎 Search resumes by Name, Email, Skills
📚 Feedback History Tracking
🏗️ System Architecture

The application follows a layered Spring Boot architecture:

Controller Layer → Handles HTTP Requests
Service Layer    → Business Logic + AI Integration
Repository Layer → Database Access (JPA)
View Layer       → Thymeleaf Templates
🛠️ Tech Stack
Backend
Java 17+
Spring Boot
Spring MVC
Spring Security
Spring Data JPA
Frontend
Thymeleaf
HTML5
Bootstrap 5
Database
MySQL
AI Integration
Ollama LLM (Local AI Model)
Libraries
Apache Tika (PDF Parsing)
Apache Commons CSV (Export CSV)
iText PDF (PDF Reports)
🤖 AI Feature (Ollama Integration)

This project uses Ollama AI to enhance resume analysis.

AI Capabilities:
Resume quality evaluation
Skill gap analysis
Job-fit scoring
Personalized improvement suggestions
Human-like feedback generation

This makes the system more advanced than traditional keyword-based ATS systems.

🔄 Workflow
User logs in (Spring Security)
Uploads Resume (PDF)
Apache Tika extracts text
Resume is parsed into structured data
User selects Job Description
System compares skills + sends data to Ollama AI
AI generates feedback report
Result is displayed and stored
👥 User Roles
🧑 User
Upload resume
View analysis results
Check AI feedback
View history
👨‍💼 Admin
Manage all resumes
View analytics dashboard
Search resumes
Export data (CSV/PDF)
Delete or update resumes
View feedback history
📁 Project Structure
com.Resume.ATS
│
├── controller
├── service
├── repository
├── model
├── dto
├── security
├── util
├── config
└── templates (Thymeleaf UI)
📊 Core Modules
1. Resume Upload
Upload PDF resumes
Validate file type
Store in database
2. Resume Parsing
Extract text using Apache Tika
Parse:
Name
Email
Phone
Skills
Experience
3. Skill Matching Engine
Matches resume skills with job description
Calculates similarity score
4. AI Feedback Engine
Uses Ollama LLM
Generates:
Missing skills
Improvement suggestions
Career guidance
5. Admin Dashboard
View all resumes
Export reports
Manage system data
📸 Screenshots (Optional)

Add screenshots here if available

/screenshots/dashboard.png
/screenshots/upload.png
/screenshots/feedback.png
⚙️ Setup Instructions
1. Clone Repository
git clone https://github.com/your-username/resume-ats.git
2. Import Project
Open in IntelliJ / Eclipse
Load Maven dependencies
3. Configure Database

Update application.properties:

spring.datasource.url=jdbc:mysql://localhost:3306/ats_db
spring.datasource.username=root
spring.datasource.password=your_password
4. Run Project
mvn spring-boot:run
🌐 Access Application
http://localhost:8080
🚀 Future Enhancements
AI Resume Ranking System
Interview Question Generator
Email notifications for feedback
Cloud deployment (AWS / Azure)
Advanced NLP-based skill extraction
Resume scoring dashboard with charts
👨‍💻 Author

Ahmed Baig Inamdar
Full Stack Java Developer
Spring Boot | AI Integration | Web Applications

⭐ Project Highlights

✔ AI-powered ATS system
✔ Real-world recruitment automation
✔ Spring Boot enterprise architecture
✔ Ollama LLM integration
✔ Production-level project structure
