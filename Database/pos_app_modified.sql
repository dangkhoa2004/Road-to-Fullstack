-- Tạo cơ sở dữ liệu
CREATE DATABASE IF NOT EXISTS pos_app;
USE pos_app;

-- Bảng chức vụ
CREATE TABLE roles (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE -- Tên chức vụ phải là duy nhất
);

-- Bảng nhân viên
CREATE TABLE employees (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    username VARCHAR(50) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL, -- Đổi tên cột để rõ ràng hơn là lưu hash mật khẩu
    role_id INT NOT NULL, -- role_id không được NULL
    phone VARCHAR(20) UNIQUE, -- Số điện thoại nên là duy nhất
    email VARCHAR(100) UNIQUE, -- Email nên là duy nhất
    is_active BOOLEAN DEFAULT TRUE, -- Thêm cột trạng thái hoạt động (thay vì xóa hẳn)
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (role_id) REFERENCES roles(id)
);

-- Bảng phân loại sản phẩm
CREATE TABLE categories (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE, -- Tên danh mục phải duy nhất
    description TEXT,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Bảng sản phẩm
CREATE TABLE products (
    id INT AUTO_INCREMENT PRIMARY KEY,
    barcode VARCHAR(100) UNIQUE,
    name VARCHAR(100) NOT NULL,
    price DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    quantity INT NOT NULL DEFAULT 0,
    image_path VARCHAR(255),
    category_id INT,
    is_active BOOLEAN DEFAULT TRUE, -- Thêm cờ hoạt động cho sản phẩm
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, -- Thêm updated_at cho products
    FOREIGN KEY (category_id) REFERENCES categories(id)
);

-- Bảng khách hàng
CREATE TABLE customers (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    phone VARCHAR(20) UNIQUE,
    email VARCHAR(100) UNIQUE,
    address TEXT,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP, -- Thêm created_at cho customers
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP -- Thêm updated_at cho customers
);

-- Bảng bàn
CREATE TABLE tables (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE, -- Tên bàn phải duy nhất
    status ENUM('available', 'occupied', 'reserved', 'cleaning', 'out_of_service') DEFAULT 'available', -- Thêm trạng thái 'cleaning', 'out_of_service'
    capacity INT DEFAULT 0, -- Thêm sức chứa của bàn
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Bảng loại khuyến mãi (để linh hoạt hơn thay vì ENUM)
CREATE TABLE discount_types (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE, -- Ví dụ: 'Percent', 'Amount', 'Buy X Get Y'
    description TEXT
);

-- Bảng khuyến mãi
CREATE TABLE discounts (
    id INT AUTO_INCREMENT PRIMARY KEY,
    code VARCHAR(50) NOT NULL UNIQUE,
    description TEXT,
    discount_type_id INT NOT NULL, -- Sử dụng FK đến discount_types
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
    id INT AUTO_INCREMENT PRIMARY KEY,
    customer_id INT, -- Có thể NULL nếu là khách vãng lai
    employee_id INT NOT NULL, -- Nhân viên tạo hóa đơn
    table_id INT, -- Có thể NULL nếu là đơn mang đi
    discount_id INT, -- Có thể NULL nếu không áp dụng khuyến mãi
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
    id INT AUTO_INCREMENT PRIMARY KEY,
    invoice_id INT NOT NULL,
    product_id INT NOT NULL,
    quantity INT NOT NULL CHECK (quantity > 0), -- Số lượng phải dương
    unit_price DECIMAL(10,2) NOT NULL, -- Giá tại thời điểm bán (để phòng trường hợp giá sản phẩm thay đổi)
    item_total DECIMAL(12,2) NOT NULL, -- Tổng tiền của mục này (quantity * unit_price)
    FOREIGN KEY (invoice_id) REFERENCES invoices(id) ON DELETE CASCADE, -- Khi hóa đơn bị xóa, chi tiết hóa đơn cũng xóa theo
    FOREIGN KEY (product_id) REFERENCES products(id)
);

-- Bảng nhập kho
CREATE TABLE stock_in (
    id INT AUTO_INCREMENT PRIMARY KEY,
    product_id INT NOT NULL,
    quantity INT NOT NULL CHECK (quantity > 0),
    note TEXT,
    employee_id INT, -- Nhân viên nhập kho
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (product_id) REFERENCES products(id),
    FOREIGN KEY (employee_id) REFERENCES employees(id)
);

-- Bảng xuất kho
CREATE TABLE stock_out (
    id INT AUTO_INCREMENT PRIMARY KEY,
    product_id INT NOT NULL,
    quantity INT NOT NULL CHECK (quantity > 0),
    note TEXT,
    employee_id INT, -- Nhân viên xuất kho
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (product_id) REFERENCES products(id),
    FOREIGN KEY (employee_id) REFERENCES employees(id)
);

-- Bảng phương thức thanh toán (để linh hoạt hơn)
CREATE TABLE payment_methods (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE, -- Ví dụ: 'Cash', 'Card', 'Momo', 'ZaloPay', 'VNPay'
    description TEXT,
    is_active BOOLEAN DEFAULT TRUE
);

-- Bảng thanh toán
CREATE TABLE payments (
    id INT AUTO_INCREMENT PRIMARY KEY,
    invoice_id INT NOT NULL,
    payment_method_id INT NOT NULL, -- Sử dụng FK đến payment_methods
    amount DECIMAL(10,2) NOT NULL CHECK (amount > 0), -- Số tiền thanh toán phải dương
    paid_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    note TEXT,
    FOREIGN KEY (invoice_id) REFERENCES invoices(id) ON DELETE CASCADE, -- Khi hóa đơn bị xóa, thanh toán cũng xóa theo
    FOREIGN KEY (payment_method_id) REFERENCES payment_methods(id)
);

-- Bảng cấu hình hệ thống
CREATE TABLE settings (
    id INT PRIMARY KEY, -- Giữ id là 1 để chỉ có 1 hàng cấu hình
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
    id INT AUTO_INCREMENT PRIMARY KEY,
    employee_id INT NOT NULL,
    session_token VARCHAR(255) NOT NULL UNIQUE,
    login_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    logout_time DATETIME,
    ip_address VARCHAR(45),
    user_agent TEXT, -- Thay device_info thành user_agent để lưu thông tin trình duyệt/thiết bị chi tiết hơn
    FOREIGN KEY (employee_id) REFERENCES employees(id) ON DELETE CASCADE -- Khi nhân viên bị xóa, các phiên đăng nhập cũng bị xóa
);

-- Ghi lịch sử thao tác (Audit Log)
CREATE TABLE audit_logs (
    id INT AUTO_INCREMENT PRIMARY KEY,
    employee_id INT, -- Có thể NULL nếu là thao tác tự động hoặc không xác định
    action_type ENUM('INSERT', 'UPDATE', 'DELETE', 'LOGIN', 'LOGOUT', 'VIEW') NOT NULL, -- Thêm loại hành động 'VIEW'
    table_name VARCHAR(100) NOT NULL,
    record_id INT, -- ID của bản ghi bị ảnh hưởng (có thể NULL nếu action không liên quan đến 1 record cụ thể)
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

-- -------------------------
-- Dữ liệu mẫu ban đầu (đã được điều chỉnh theo cấu trúc mới)
-- -------------------------

-- Vai trò
INSERT INTO roles(name) VALUES ('Admin'), ('Nhân viên'), ('Quản lý kho');

-- Loại khuyến mãi
INSERT INTO discount_types(name, description) VALUES
('percent', 'Giảm giá theo phần trăm'),
('amount', 'Giảm giá theo số tiền cố định');

-- Phương thức thanh toán
INSERT INTO payment_methods(name, description, is_active) VALUES
('cash', 'Tiền mặt', TRUE),
('card', 'Thẻ ngân hàng', TRUE),
('momo', 'Ví Momo', TRUE),
('zalo', 'Ví ZaloPay', TRUE),
('vnpay', 'Cổng thanh toán VNPay', TRUE);

-- Nhân viên (MẬT KHẨU NÀY LÀ VÍ DỤ, TRONG THỰC TẾ PHẢI LÀ HASH!)
INSERT INTO employees(name, username, password_hash, role_id, phone, email, is_active)
VALUES
('Quản trị viên', 'admin', '$2b$10$hashedpasswordexample1234567890123456789012345678901234', 1, '0869938981', 'admin@example.com', TRUE), -- Đây là một hashed password giả định
('Nguyễn Thị Đào', 'dao_nv', '$2b$10$anotherhashedpasswordexample12345678901234567890', 2, '0987654321', 'dao.nguyen@example.com', TRUE);


-- Cài đặt hệ thống (giữ nguyên, nhưng có thể thêm các default)
INSERT INTO settings (id, store_name, address, phone, email, logo_path, tax_rate, currency, invoice_prefix, printer_name, default_language, backup_path)
VALUES (1, 'POS - Quản lý bán hàng', 'Số Nhà 4B66, Ngõ 296 Ngô Gia Tự, Phường Cát Bi, Quận Hải An, Hải Phòng', '0869938981', 'info@abcshop.vn', 'logo.png', 10.00, 'VND', 'INV-', 'Printer_POS_1', 'VI', 'D:/POS/backup/');

-- Phân loại sản phẩm
INSERT INTO categories(name, description)
VALUES
  ('Trà sữa', 'Các loại trà sữa truyền thống'),
  ('Đồ uống khác', 'Cà phê, sinh tố, nước trái cây'),
  ('Đồ ăn vặt', 'Các món ăn nhẹ, bánh, snack');

-- Sản phẩm
INSERT INTO products(barcode ,image_path ,name, price, quantity, category_id)
VALUES
  ('BC123456789','caphe.png','Cà phê đen', 25000.00, 100, (SELECT id FROM categories WHERE name = 'Đồ uống khác')),
  ('BC123456788','thaixanh.png','Trà sữa thái xanh', 30000.00, 80, (SELECT id FROM categories WHERE name = 'Trà sữa')),
  ('BC123456787','bo.png','Sinh tố bơ', 35000.00, 50, (SELECT id FROM categories WHERE name = 'Đồ uống khác')),
  ('BC123456786','bimbim.png','Bim Bim Oishi', 10000.00, 200, (SELECT id FROM categories WHERE name = 'Đồ ăn vặt'));


-- Khách hàng
INSERT INTO customers(name, phone, email, address)
VALUES
  ('Nguyễn Văn A', '0901234567', 'a@gmail.com', '12 Nguyễn Trãi, Q1'),
  ('Trần Thị B', '0907654321', 'b@gmail.com', '56 Lê Lợi, Q3'),
  ('Lê Văn C', '0912345678', 'c@gmail.com', '20 Phan Chu Trinh, Q.Hải An');

-- Thêm bàn
INSERT INTO tables(name, status, capacity)
VALUES ('Bàn 1', 'available', 4), ('Bàn 2', 'occupied', 2), ('Bàn 3', 'reserved', 6);

-- Thêm khuyến mãi
INSERT INTO discounts(code, description, discount_type_id, value, start_date, end_date, minimum_order_amount, maximum_discount_amount)
VALUES
('SALE10', 'Giảm 10% toàn bộ hóa đơn', (SELECT id FROM discount_types WHERE name = 'percent'), 10, NOW(), DATE_ADD(NOW(), INTERVAL 30 DAY), 100000.00, 50000.00),
('FREESHIP', 'Miễn phí vận chuyển', (SELECT id FROM discount_types WHERE name = 'amount'), 0, NOW(), DATE_ADD(NOW(), INTERVAL 15 DAY), 50000.00, 0.00);


-- Tạo hóa đơn (ví dụ tính toán total_amount đơn giản để test)
-- Hóa đơn 1: 1 Cà phê đen (25k) + 1 Trà sữa thái xanh (30k) = 55k
INSERT INTO invoices(customer_id, employee_id, table_id, discount_id, sub_total, discount_amount, tax_amount, total_amount, status)
VALUES (
    (SELECT id FROM customers WHERE name = 'Nguyễn Văn A'),
    (SELECT id FROM employees WHERE username = 'admin'),
    (SELECT id FROM tables WHERE name = 'Bàn 1'),
    (SELECT id FROM discounts WHERE code = 'SALE10'), -- Giả sử áp dụng SALE10
    55000.00, -- sub_total
    0.00,     -- discount_amount (sẽ được tính ở backend)
    0.00,     -- tax_amount (sẽ được tính ở backend)
    55000.00, -- total_amount (sẽ được tính ở backend)
    'pending'
);

-- Chi tiết hóa đơn 1
INSERT INTO invoice_items(invoice_id, product_id, quantity, unit_price, item_total)
VALUES
  ((SELECT id FROM invoices ORDER BY id DESC LIMIT 1), (SELECT id FROM products WHERE name = 'Cà phê đen'), 1, 25000.00, 25000.00),
  ((SELECT id FROM invoices ORDER BY id DESC LIMIT 1), (SELECT id FROM products WHERE name = 'Trà sữa thái xanh'), 1, 30000.00, 30000.00);

-- Giao dịch thanh toán 1
INSERT INTO payments(invoice_id, payment_method_id, amount)
VALUES
  ((SELECT id FROM invoices ORDER BY id DESC LIMIT 1), (SELECT id FROM payment_methods WHERE name = 'cash'), 55000.00);


-- Hóa đơn 2: khách vãng lai, không bàn
INSERT INTO invoices(customer_id, employee_id, table_id, discount_id, sub_total, discount_amount, tax_amount, total_amount, status)
VALUES (
    NULL,
    (SELECT id FROM employees WHERE username = 'dao_nv'),
    NULL,
    NULL,
    35000.00,
    0.00,
    0.00,
    35000.00,
    'completed'
);

-- Chi tiết hóa đơn 2
INSERT INTO invoice_items(invoice_id, product_id, quantity, unit_price, item_total)
VALUES
  ((SELECT id FROM invoices ORDER BY id DESC LIMIT 1), (SELECT id FROM products WHERE name = 'Sinh tố bơ'), 1, 35000.00, 35000.00);

-- Giao dịch thanh toán 2
INSERT INTO payments(invoice_id, payment_method_id, amount)
VALUES
  ((SELECT id FROM invoices ORDER BY id DESC LIMIT 1), (SELECT id FROM payment_methods WHERE name = 'momo'), 35000.00);


-- Nhập kho
INSERT INTO stock_in(product_id, quantity, note, employee_id)
VALUES
  ((SELECT id FROM products WHERE name = 'Cà phê đen'), 50, 'Nhập lô mới tháng 4', (SELECT id FROM employees WHERE username = 'admin')),
  ((SELECT id FROM products WHERE name = 'Trà sữa thái xanh'), 30, 'Nhập thêm do sắp hết', (SELECT id FROM employees WHERE username = 'dao_nv'));

-- Xuất kho
INSERT INTO stock_out(product_id, quantity, note, employee_id)
VALUES
  ((SELECT id FROM products WHERE name = 'Cà phê đen'), 5, 'Xuất cho chi nhánh 2', (SELECT id FROM employees WHERE username = 'admin')),
  ((SELECT id FROM products WHERE name = 'Sinh tố bơ'), 2, 'Xuất nhầm cần kiểm tra', (SELECT id FROM employees WHERE username = 'dao_nv'));

-- Lịch sử thao tác
INSERT INTO audit_logs(employee_id, action_type, table_name, record_id, action_time, old_data, new_data, description)
VALUES
  ((SELECT id FROM employees WHERE username = 'admin'), 'INSERT', 'products', (SELECT id FROM products WHERE name = 'Bim Bim Oishi'), NOW(), NULL, '{"barcode":"BC123456786","name":"Bim Bim Oishi","price":10000.0,"quantity":200}', 'Thêm sản phẩm Bim Bim Oishi'),
  ((SELECT id FROM employees WHERE username = 'dao_nv'), 'UPDATE', 'products', (SELECT id FROM products WHERE name = 'Cà phê đen'), NOW(), '{"quantity":100}', '{"quantity":95}', 'Cập nhật số lượng Cà phê đen sau bán hàng');