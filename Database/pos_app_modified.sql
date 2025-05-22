-- Tạo cơ sở dữ liệu
CREATE DATABASE IF NOT EXISTS pos_app;
USE pos_app;

-- Bảng chức vụ
CREATE TABLE roles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE, -- Tên chức vụ phải là duy nhất
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Bảng nhân viên
CREATE TABLE employees (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    username VARCHAR(50) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL, -- Đổi tên cột để rõ ràng hơn là lưu hash mật khẩu
    role_id BIGINT  NOT NULL, -- role_id không được NULL
    phone VARCHAR(20) UNIQUE, -- Số điện thoại nên là duy nhất
    email VARCHAR(100) UNIQUE, -- Email nên là duy nhất
    is_active BOOLEAN DEFAULT TRUE, -- Thêm cột trạng thái hoạt động (thay vì xóa hẳn)
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (role_id) REFERENCES roles(id)
);

-- Bảng phân loại sản phẩm
CREATE TABLE categories (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE, -- Tên danh mục phải duy nhất
    description TEXT,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Bảng sản phẩm
CREATE TABLE products (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    barcode VARCHAR(100) UNIQUE,
    name VARCHAR(100) NOT NULL,
    price DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    quantity INT NOT NULL DEFAULT 0,
    image_path VARCHAR(255),
    category_id BIGINT,
    is_active BOOLEAN DEFAULT TRUE, -- Thêm cờ hoạt động cho sản phẩm
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, -- Thêm updated_at cho products
    FOREIGN KEY (category_id) REFERENCES categories(id)
);

-- Bảng khách hàng
CREATE TABLE customers (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    phone VARCHAR(20) UNIQUE,
    email VARCHAR(100) UNIQUE,
    address TEXT,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP, -- Thêm created_at cho customers
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP -- Thêm updated_at cho customers
);

-- Bảng bàn
CREATE TABLE tables (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE, -- Tên bàn phải duy nhất
    status ENUM('available', 'occupied', 'reserved', 'cleaning', 'out_of_service') DEFAULT 'available', -- Thêm trạng thái 'cleaning', 'out_of_service'
    capacity INT DEFAULT 0, -- Thêm sức chứa của bàn
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Bảng loại khuyến mãi (để linh hoạt hơn thay vì ENUM)
CREATE TABLE discount_types (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE, -- Ví dụ: 'Percent', 'Amount', 'Buy X Get Y'
    description TEXT
);

-- Bảng khuyến mãi
CREATE TABLE discounts (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    code VARCHAR(50) NOT NULL UNIQUE,
    description TEXT,
    discount_type_id BIGINT NOT NULL, -- Sử dụng FK đến discount_types
    value DECIMAL(10,2) NOT NULL,
    start_date DATETIME NOT NULL,
    end_date DATETIME NOT NULL,
    active BOOLEAN DEFAULT TRUE,
    minimum_order_amount DECIMAL(12,2) DEFAULT 0.00, -- Thêm điều kiện áp dụng
    maximum_discount_amount DECIMAL(12,2) DEFAULT 0.00, -- Giới hạn mức giảm tối đa
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (discount_type_id) REFERENCES discount_types(id)
);

-- Bảng hóa đơn
CREATE TABLE invoices (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    customer_id BIGINT, -- Có thể NULL nếu là khách vãng lai
    employee_id BIGINT NOT NULL, -- Nhân viên tạo hóa đơn
    table_id BIGINT, -- Có thể NULL nếu là đơn mang đi
    discount_id BIGINT, -- Có thể NULL nếu không áp dụng khuyến mãi
    sub_total DECIMAL(12,2) NOT NULL DEFAULT 0.00, -- Tổng tiền trước giảm giá và thuế
    discount_amount DECIMAL(12,2) NOT NULL DEFAULT 0.00, -- Số tiền giảm giá
    tax_amount DECIMAL(12,2) NOT NULL DEFAULT 0.00, -- Số tiền thuế
    total_amount DECIMAL(12,2) NOT NULL DEFAULT 0.00, -- Tổng số tiền cuối cùng (sau giảm giá, cộng thuế)
    status ENUM('pending', 'completed', 'cancelled', 'refunded') DEFAULT 'pending', -- Trạng thái hóa đơn
    note TEXT,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (customer_id) REFERENCES customers(id),
    FOREIGN KEY (employee_id) REFERENCES employees(id),
    FOREIGN KEY (table_id) REFERENCES tables(id),
    FOREIGN KEY (discount_id) REFERENCES discounts(id)
);

-- Bảng chi tiết hóa đơn
CREATE TABLE invoice_items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    invoice_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    quantity INT NOT NULL CHECK (quantity > 0), -- Số lượng phải dương
    unit_price DECIMAL(10,2) NOT NULL, -- Giá tại thời điểm bán (để phòng trường hợp giá sản phẩm thay đổi)
    item_total DECIMAL(12,2) NOT NULL, -- Tổng tiền của mục này (quantity * unit_price)
    FOREIGN KEY (invoice_id) REFERENCES invoices(id) ON DELETE CASCADE, -- Khi hóa đơn bị xóa, chi tiết hóa đơn cũng xóa theo
    FOREIGN KEY (product_id) REFERENCES products(id)
);

-- Bảng nhập kho
CREATE TABLE stock_in (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_id BIGINT NOT NULL,
    quantity INT NOT NULL CHECK (quantity > 0),
    note TEXT,
    employee_id BIGINT, -- Nhân viên nhập kho
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (product_id) REFERENCES products(id),
    FOREIGN KEY (employee_id) REFERENCES employees(id)
);

-- Bảng xuất kho
CREATE TABLE stock_out (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_id BIGINT NOT NULL,
    quantity INT NOT NULL CHECK (quantity > 0),
    note TEXT,
    employee_id BIGINT, -- Nhân viên xuất kho
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (product_id) REFERENCES products(id),
    FOREIGN KEY (employee_id) REFERENCES employees(id)
);

-- Bảng phương thức thanh toán (để linh hoạt hơn)
CREATE TABLE payment_methods (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE, -- Ví dụ: 'Cash', 'Card', 'Momo', 'ZaloPay', 'VNPay'
    description TEXT,
    is_active BOOLEAN DEFAULT TRUE
);

-- Bảng thanh toán
CREATE TABLE payments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    invoice_id BIGINT NOT NULL,
    payment_method_id BIGINT NOT NULL, -- Sử dụng FK đến payment_methods
    amount DECIMAL(10,2) NOT NULL CHECK (amount > 0), -- Số tiền thanh toán phải dương
    paid_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    note TEXT,
    FOREIGN KEY (invoice_id) REFERENCES invoices(id) ON DELETE CASCADE, -- Khi hóa đơn bị xóa, thanh toán cũng xóa theo
    FOREIGN KEY (payment_method_id) REFERENCES payment_methods(id)
);

-- Bảng cấu hình hệ thống
CREATE TABLE settings (
    id BIGINT PRIMARY KEY, -- Giữ id là 1 để chỉ có 1 hàng cấu hình
    store_name VARCHAR(100) NOT NULL,
    address TEXT,
    phone VARCHAR(20),
    email VARCHAR(100),
    logo_path TEXT,
    tax_rate DECIMAL(5,2) DEFAULT 0.00 CHECK (tax_rate >= 0 AND tax_rate <= 100), -- Tỷ lệ thuế từ 0-100%
    currency VARCHAR(10) DEFAULT 'VND',
    invoice_prefix VARCHAR(20),
    printer_name VARCHAR(100),
    default_language VARCHAR(10) DEFAULT 'VI',
    backup_path TEXT,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Quản lý phiên đăng nhập người dùng
CREATE TABLE user_sessions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    employee_id BIGINT NOT NULL,
    session_token VARCHAR(255) NOT NULL UNIQUE,
    login_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    logout_time DATETIME,
    ip_address VARCHAR(45),
    user_agent TEXT, -- Thay device_info thành user_agent để lưu thông tin trình duyệt/thiết bị chi tiết hơn
    FOREIGN KEY (employee_id) REFERENCES employees(id) ON DELETE CASCADE -- Khi nhân viên bị xóa, các phiên đăng nhập cũng bị xóa
);

-- Ghi lịch sử thao tác (Audit Log)
CREATE TABLE audit_logs (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    employee_id BIGINT, -- Có thể NULL nếu là thao tác tự động hoặc không xác định
    action_type ENUM('INSERT', 'UPDATE', 'DELETE', 'LOGIN', 'LOGOUT', 'VIEW') NOT NULL, -- Thêm loại hành động 'VIEW'
    table_name VARCHAR(100) NOT NULL,
    record_id BIGINT, -- ID của bản ghi bị ảnh hưởng (có thể NULL nếu action không liên quan đến 1 record cụ thể)
    action_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    old_data JSON, -- Thay TEXT bằng JSON để lưu trữ dữ liệu có cấu trúc hơn
    new_data JSON, -- Thay TEXT bằng JSON
    description TEXT, -- Thêm mô tả chi tiết hơn về hành động
    FOREIGN KEY (employee_id) REFERENCES employees(id)
);

-- Thêm cột version vào bảng sản phẩm để kiểm soát xung đột khi chỉnh sửa (Optimistic Locking)
ALTER TABLE products ADD COLUMN version INT DEFAULT 1;
ALTER TABLE customers ADD COLUMN version INT DEFAULT 1;
ALTER TABLE invoices ADD COLUMN version INT DEFAULT 1;
ALTER TABLE categories ADD COLUMN version INT DEFAULT 1; -- Thêm cho category
ALTER TABLE employees ADD COLUMN version INT DEFAULT 1; -- Thêm cho employee