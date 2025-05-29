# POS Backend - Java Spring Boot 🏬

Đây là **phần backend** của dự án **ROAD-TO-FULLSTACK**, phục vụ quản lý **POS (Point of Sale)** – hệ thống bán hàng với các tính năng quản lý **sản phẩm, hóa đơn, khách hàng, nhân viên, tồn kho, ...**

👉 Mục tiêu:
✅ Xây dựng RESTful API ổn định, bảo mật và dễ mở rộng
✅ Áp dụng thực tế những kiến thức **Spring Boot**, **JPA**, **Security**, **JWT**, **Swagger**
✅ Tích hợp mượt mà với **frontend (Vue.js)** qua API chuẩn hóa JSON

---

## 🌟 Công Nghệ Chính

| Công Nghệ       | Mục Đích                              |
| --------------- | ------------------------------------- |
| Java 17+        | Ngôn ngữ chính                        |
| Spring Boot 3   | Framework backend chính               |
| Spring Data JPA | ORM (MySQL/PostgreSQL)                |
| Spring Security | Bảo mật, phân quyền với JWT           |
| Lombok          | Giảm thiểu boilerplate code           |
| MapStruct       | Mapping Entity ↔️ DTO                 |
| Swagger/OpenAPI | Tài liệu API (tự động cập nhật)       |
| EmailService    | Gửi email (reset password, thông báo) |

---

## 🗂️ Cấu Trúc Thư Mục Nổi Bật

```bash
src/main/java/com/pos/backend/
├── config/           # Cấu hình Spring Boot, Security, OpenAPI, DataInitializer
├── controller/       # REST Controllers: Auth, Product, Invoice, Customer, ...
├── dto/              # DTO: Request/Response tách biệt với Entity
├── model/            # Entity ánh xạ database
├── repository/       # Giao tiếp DB (JpaRepository)
├── security/         # JWT, UserDetailsService
├── service/          # Business logic (service & serviceImpl)
├── util/             # Các lớp tiện ích (constants, exceptions, ...)
└── PosBackendApplication.java  # Main class
```

---

## 🔥 Tính Năng Chính

✅ **Quản lý người dùng & phân quyền (Role)**
✅ **Quản lý sản phẩm, danh mục, tồn kho**
✅ **Quản lý hóa đơn (Invoice) & thanh toán**
✅ **Quản lý nhân viên, khách hàng, giảm giá**
✅ **Hệ thống bảo mật (Spring Security + JWT)**
✅ **Email: Reset mật khẩu**
✅ **Swagger API Documentation**
✅ **Global Exception Handling**
✅ **Logging (AuditLog)**
✅ **Tích hợp report (PDF, Excel, ...) - đang phát triển**

---

## 🚀 Hướng Dẫn Sử Dụng

### 1️⃣ Cài Đặt Dự Án

```bash
git clone https://github.com/your-username/pos-backend.git
cd pos-backend
```

### 2️⃣ Cấu Hình Database

File: `src/main/resources/application.properties`

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/pos_db
spring.datasource.username=root
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

jwt.secret=your_jwt_secret
jwt.expiration=3600000
```

> 📌 **Tip:** Có thể đổi sang `application.yml` nếu thích!

---

### 3️⃣ Chạy Ứng Dụng

```bash
./mvnw spring-boot:run
# hoặc
mvn spring-boot:run
```

### 4️⃣ Truy Cập Swagger

[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
👉 Tích hợp frontend dễ dàng, test API trực tiếp!

---

## 🔐 Bảo Mật & Xác Thực

* **/api/auth/register**: Đăng ký tài khoản
* **/api/auth/login**: Đăng nhập → nhận JWT token
* **Authorization:** `Bearer <token>` (truy cập các API private)
* **Spring Security:** Role-based (Admin, Cashier, Manager, ...)

---

## 🧪 Testing

* **Unit test:** `JUnit 5` & `Mockito` (service layer, repository)
* **Integration test:** Spring Boot Test (với H2 database hoặc testcontainers)
* File: `src/test/java/com/pos/backend/PosBackendApplicationTests.java`

---

## 💾 Database & Migrations

* **Database:** MySQL (hoặc PostgreSQL tuỳ chỉnh)
* **Migration:** (Tùy chọn) Có thể tích hợp **Flyway** hoặc **Liquibase** để versioning DB schema

---

## ⚡ Các Lớp Chính

| Layer          | Mục Đích                             |
| -------------- | ------------------------------------ |
| **Controller** | Xử lý request/response               |
| **Service**    | Logic nghiệp vụ chính                |
| **Repository** | Giao tiếp với DB                     |
| **DTO**        | Chuẩn hóa dữ liệu (response/request) |
| **Security**   | Bảo mật JWT, xác thực người dùng     |
| **Util**       | Tiện ích (exception, constants, ...) |

---

## 💡 Các Bước Triển Khai

✅ Viết API → Test bằng Postman/Swagger
✅ Mapping DTO ↔️ Entity
✅ Xử lý business logic (service/serviceImpl)
✅ Gửi email reset password (mailtrap.io/SMTP)
✅ Hoàn thiện security + phân quyền
✅ Viết unit test & integration test

---

## 🎯 Lessons Learned

✨ **DTO pattern**: Bảo mật & tách biệt entity/database
✨ **Global Exception Handling**: Đem lại API response consistent
✨ **Layered Architecture**: Dễ mở rộng & maintain
✨ **Swagger**: Giúp dev frontend dễ dàng tích hợp
✨ **Role-based Security**: Cách triển khai Spring Security + JWT

---

**🔥 Đây chỉ là phần backend trong hành trình “ROAD-TO-FULLSTACK”.**
Bạn muốn mình viết tiếp phần **CI/CD (Docker, GitHub Actions)** hay **hướng dẫn triển khai production (AWS, GCP)** không? 🚀✨
