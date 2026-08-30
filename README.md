# Petition Platform

A secure web-based platform to enable citizens to file and track petitions, with an integrated committee dashboard for evaluation by Admin, analytics, and publishing. Both Admin dashboard and Citizens dashboard have different views.

## 📌 Features

- 🔐 **End-to-End User Authentication** with Spring Security & JWT
- 📮 **Petitioner Portal** for submitting and viewing petition status
- 🧑‍⚖️ **Committee Dashboard** for reviewing, archiving, or approving petitions
- 📊 **Analytics & Word Clouds** using Chart.js, ngx-charts, and D3
- 🔎 **Public Open Data Access** to all published petitions
- 📷 **QR Code BioID Scanner** for quick registration
- 🌒 **Dark Mode & Responsive UI** with SCSS-based theming

## 🛠️ Tech Stack

**Backend**  
- Java 17, Spring Boot (MVC + Security + JPA)  
- PostgreSQL  
- RESTful APIs with role-based access  

**Frontend**  
- Angular 19  
- ngx-charts, Chart.js, D3 for visualization  
- HTML5 QR Code scanner  
- Modular SCSS styling  

## ⚙️ Installation

### Backend

```bash
cd slpp-backend
./mvnw spring-boot:run
```

### Admin Credentials
email-id: admin@petition.parliament.sr
password: 2025%shangrila

