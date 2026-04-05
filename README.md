# 💰 Finance Dashboard (Full Stack)

## 📌 Overview
This project is a Finance Dashboard system built using Spring Boot (backend) and React (frontend).  
It supports role-based access control and financial data management.

---

## 🛠 Tech Stack

### Backend
- Spring Boot
- Spring Security
- JPA / Hibernate
- MySQL / Oracle

### Frontend
- React (Vite)
- Axios
- CSS

---

## 👥 User Roles

| Role     | Access |
|----------|--------|
| Viewer   | View records only |
| Analyst  | View records + dashboard summary |
| Admin    | Full CRUD (records + users) |

---

## 🚀 Features

### 🔹 User Management
- Create users
- Assign roles (Viewer, Analyst, Admin)
- Manage user status

### 🔹 Financial Records
- Add income/expense
- Update records
- Delete records
- View all records
- Filter by:
  - Date
  - Category
  - Type

### 🔹 Dashboard Summary
- Total Income
- Total Expense
- Balance
- Category-wise summary
- Recent transactions

### 🔹 Access Control
- Viewer → Read only
- Analyst → Read + Summary
- Admin → Full control

---

## 📁 Project Structure
