📘 Blog Backend
📝 Giới thiệu

Đây là Backend API cho ứng dụng Blog, được xây dựng bằng Spring Boot với các chức năng:

Đăng ký, đăng nhập (Authentication & Authorization với Spring Security + JWT).

CRUD bài viết (tạo, sửa, xóa, xem bài viết).

Quản lý người dùng.

Xử lý phân trang, lazy loading.

Xử lý ngoại lệ tập trung với Global Exception Handling.

Customize Response để phản hồi dữ liệu thống nhất.

DatabaseInitializer tạo sẵn dữ liệu mẫu giúp dễ dàng chạy thử.

🚀 Công nghệ sử dụng

Java 17

Spring Boot 3.x

Spring Security + JWT

Spring Data JPA / Hibernate

MySQL (có thể thay bằng PostgreSQL hoặc H2)

Jakarta Validation (validate dữ liệu đầu vào)

Maven/Gradle (quản lý dependencies)


🔑 Chức năng chính
Authentication & Authorization

Đăng ký tài khoản với validate email + password.

Đăng nhập, trả về JWT token.

Xác thực bằng JWT trong các request bảo mật.

Quản lý bài viết (Post)

Tạo bài viết (yêu cầu đăng nhập).

Cập nhật / Xóa bài viết (chỉ tác giả mới được phép).

Xem danh sách bài viết (public).

Phân trang, lazy loading khi gọi API.

Quản lý người dùng (User)

Xem thông tin tài khoản.

Xem danh sách bài viết của chính mình.

Exception Handling

GlobalException.java để bắt toàn bộ lỗi.

Custom exception (PermissionException, IdInvalidException, StorageException...).

Customize Response

Toàn bộ phản hồi từ API đều được format qua FormatRestResponse.java để đảm bảo:

Cấu trúc thống nhất (status, message, data).

Dễ dàng debug và tích hợp frontend.

DatabaseInitializer

Khi khởi chạy ứng dụng, DatabaseInitializer.java sẽ tự động:

Tạo user mẫu.

Sinh bài viết mẫu.

Điều này giúp có thể chạy và test ngay website mà không cần nhập liệu thủ công.

⚙️ Cấu hình Database

Trong file application.properties:

spring.datasource.url=jdbc:mysql://localhost:3306/blogdb
spring.datasource.username=root
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

Ứng dụng sẽ chạy tại:
👉 http://localhost:8080

📌 API chính
Auth

POST /api/v1/auth/register – Đăng ký

POST /api/v1/auth/login – Đăng nhập

POST /api/v1/auth/logout - Đăng xuất

Post

GET /api/v1/posts – Lấy danh sách bài viết (có phân trang)

POST /api/v1/posts – Tạo bài viết

PUT /api/v1/posts – Cập nhật bài viết

DELETE /api/v1/posts/{id} – Xóa bài viết

User

GET /api/v1/auth/account – Thông tin user hiện tại

GET /api/v1/users – Tạo user

