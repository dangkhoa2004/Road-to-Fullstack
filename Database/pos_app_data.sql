
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
('Quản trị viên', 'admin', '$2a$10$Ufhd63povasdLO/01D5EVONyhLkTbrqJP/0cvpLoQnlAIVnaDvOly', 1, '0869938981', 'admin@example.com', TRUE), -- Đây là một hashed password giả định
('Nguyễn Thị Đào', 'dao_nv', '$2a$10$Ufhd63povasdLO/01D5EVONyhLkTbrqJP/0cvpLoQnlAIVnaDvOly', 2, '0987654321', 'dao.nguyen@example.com', TRUE);

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