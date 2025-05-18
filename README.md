
# 📦 Customer Subscription Management System

💼 Developed by **Ramya Kata** — Software Engineer

A full-stack subscription management platform designed to handle customer onboarding, plan selection, secure access, and subscription tracking. The app features **role-based dashboards** for Admin and Customers, with full authentication and microservice-based backend architecture.

---

## 🌟 Features

- 🎯 Secure Authentication and Authorization (JWT-based)
- 👥 Customer Registration and Management
- 📦 Plan Selection and Subscription Flow
-  📊 **Admin Dashboard** — accessible only with root credentials
- 📋 **Customer Dashboard** — users can view, select, and manage subscriptions
- 💳 Payment Gateway Integration (Dummy Payment Service)
- 🧠 Microservices Architecture using Spring Boot
- 🔎 Service Discovery using Eureka
- 🚪 API Gateway with centralized routing
- 🛠️ Frontend built with React.js 
- 📱 Fully mobile-responsive frontend


---

## 🔧 Tech Stack

### Backend
- Java 
- Spring Boot
- Spring Security
- Spring Cloud
- Eureka Server
- API Gateway (Spring Gateway)
- PostgreSQL
- MongoDB
- MySQL

### Frontend
- React.js 



---

## 📁 Project Structure




## Backend

- Developed multiple microservices: 
  - Authentication Service
  - Customer Service
  - Plan Service
  - Subscription Service
  - Payment Service
  - API Gateway
  - Service Registry
- Used Spring Boot, Spring Security, Spring Cloud, Eureka, etc.

## Frontend

- Built with [React.js] 
- Handles customer registration, plan selection, subscription management
- Communicates with backend via REST APIs

## 🧑‍💼 Roles & Access Flow

| Role     | Access Method     | Permissions                                                                                       |
|----------|------------------|----------------------------------------------------------------------------------------------------|
| **Admin**   | Login only (root credentials hardcoded) | Access to all customer data, plans, payments, and subscriptions          |
| **Customer** | Register → Login | View plans, subscribe/unsubscribe, view own subscriptions only                                 |



## How to Run

### Backend
1. Navigate to the backend service you want to run.
2. Use Maven to build and run:
   ```bash
   mvn clean install
   mvn spring-boot:run```

### Backend

1. Navigate to the frontend folder.

2. Install dependencies and start the frontend server:

	```bash
	   npm install
	   npm start```



 ## 📸 Screenshots

### 🔐 Login Page
![Login](./src/assets/screenshots/login.png)

### 📝 Register Page
![Register](./src/assets/screenshots/register.png)

### 📱 Customer – Plan Selection
![Plan Selection](./src/assets/screenshots/user-plan-selection.png)

### 🏠 Customer – Dashboard Overview
![User Dashboard](./src/assets/screenshots/user-dashboard.png)

### 👤 Customer – Update Profile
![Update Profile](./src/assets/screenshots/profile-update.png)

### 🧾 Admin – View Mobile Plans
![Admin Plans](./src/assets/screenshots/admin-view-plans.png)

### 🛠️ Admin – Update Mobile Plan
![Update Plan](./src/assets/screenshots/admin-update.png)

### ❌ Admin – Delete Mobile Plan
![Delete Plan](./src/assets/screenshots/admin-delete.png)

### 🔍 Admin – Fetch User Info
![Fetch User](./src/assets/screenshots/admin-fetch-user.png)
