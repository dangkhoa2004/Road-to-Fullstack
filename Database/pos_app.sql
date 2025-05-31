-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th5 31, 2025 lúc 07:26 AM
-- Phiên bản máy phục vụ: 10.4.32-MariaDB
-- Phiên bản PHP: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `pos_app`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `audit_logs`
--

CREATE TABLE `audit_logs` (
  `id` bigint(20) NOT NULL,
  `employee_id` bigint(20) DEFAULT NULL,
  `action_type` enum('DELETE','INSERT','LOGIN','LOGOUT','UPDATE','VIEW') NOT NULL,
  `table_name` varchar(100) NOT NULL,
  `record_id` bigint(20) DEFAULT NULL,
  `action_time` datetime DEFAULT current_timestamp(),
  `old_data` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL CHECK (json_valid(`old_data`)),
  `new_data` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL CHECK (json_valid(`new_data`)),
  `description` text DEFAULT NULL,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  `version` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `categories`
--

CREATE TABLE `categories` (
  `id` bigint(20) NOT NULL,
  `name` varchar(100) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `created_at` datetime DEFAULT current_timestamp(),
  `updated_at` datetime DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `version` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `categories`
--

INSERT INTO `categories` (`id`, `name`, `description`, `created_at`, `updated_at`, `version`) VALUES
(1, 'Trà sữa', 'Các loại trà sữa truyền thống', '2025-05-23 17:41:02', '2025-05-23 17:41:02', 1),
(2, 'Đồ uống khác', 'Cà phê, sinh tố, nước trái cây', '2025-05-23 17:41:02', '2025-05-23 17:41:02', 1),
(3, 'Đồ ăn vặt', 'Các món ăn nhẹ, bánh, snack', '2025-05-23 17:41:02', '2025-05-23 17:41:02', 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `customers`
--

CREATE TABLE `customers` (
  `id` bigint(20) NOT NULL,
  `name` varchar(100) NOT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `address` text DEFAULT NULL,
  `created_at` datetime DEFAULT current_timestamp(),
  `updated_at` datetime DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `version` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `customers`
--

INSERT INTO `customers` (`id`, `name`, `phone`, `email`, `address`, `created_at`, `updated_at`, `version`) VALUES
(1, 'Nguyễn Văn A', '0901234567', 'a@gmail.com', '12 Nguyễn Trãi, Q1', '2025-05-28 21:36:31', '2025-05-28 21:36:31', NULL),
(2, 'Trần Thị B', '0907654321', 'b@gmail.com', '56 Lê Lợi, Q3', '2025-05-28 21:36:31', '2025-05-28 21:36:31', NULL),
(3, 'Lê Văn C', '0912345678', 'c@gmail.com', '20 Phan Chu Trinh, Q.Hải An', '2025-05-28 21:36:31', '2025-05-28 21:36:31', NULL);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `discounts`
--

CREATE TABLE `discounts` (
  `id` bigint(20) NOT NULL,
  `code` varchar(50) NOT NULL,
  `description` text DEFAULT NULL,
  `discount_type_id` bigint(20) NOT NULL,
  `value` decimal(10,2) NOT NULL,
  `start_date` datetime NOT NULL,
  `end_date` datetime NOT NULL,
  `active` tinyint(1) DEFAULT 1,
  `minimum_order_amount` decimal(12,2) DEFAULT 0.00,
  `maximum_discount_amount` decimal(12,2) DEFAULT 0.00,
  `created_at` datetime DEFAULT current_timestamp(),
  `updated_at` datetime DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `version` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `discounts`
--

INSERT INTO `discounts` (`id`, `code`, `description`, `discount_type_id`, `value`, `start_date`, `end_date`, `active`, `minimum_order_amount`, `maximum_discount_amount`, `created_at`, `updated_at`, `version`) VALUES
(1, 'SALE10', 'Giảm 10% toàn bộ hóa đơn', 1, 10.00, '2025-05-28 16:58:28', '2025-06-27 16:58:28', 1, 100000.00, 50000.00, '2025-05-28 16:58:28', '2025-05-28 16:58:28', NULL),
(2, 'FREESHIP', 'Miễn phí vận chuyển', 2, 0.00, '2025-05-28 16:58:28', '2025-06-12 16:58:28', 1, 50000.00, 0.00, '2025-05-28 16:58:28', '2025-05-28 16:58:28', NULL);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `discount_types`
--

CREATE TABLE `discount_types` (
  `id` bigint(20) NOT NULL,
  `name` varchar(50) NOT NULL,
  `description` text DEFAULT NULL,
  `version` bigint(20) DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `discount_types`
--

INSERT INTO `discount_types` (`id`, `name`, `description`, `version`, `created_at`, `updated_at`) VALUES
(1, 'percent', 'Giảm giá theo phần trăm', NULL, NULL, NULL),
(2, 'amount', 'Giảm giá theo số tiền cố định', NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `employees`
--

CREATE TABLE `employees` (
  `id` bigint(20) NOT NULL,
  `name` varchar(100) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password_hash` varchar(255) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `is_active` tinyint(1) DEFAULT 1,
  `created_at` datetime DEFAULT current_timestamp(),
  `updated_at` datetime DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `version` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `employees`
--

INSERT INTO `employees` (`id`, `name`, `username`, `password_hash`, `role_id`, `phone`, `email`, `is_active`, `created_at`, `updated_at`, `version`) VALUES
(1, 'Cao Đăng Khoa', 'admin', '$2a$10$JtWbThTzQhvXxFL90AdhoePJAx6xG4eUJkdBgrMGHhXcEWK49poDW', 1, '1234567890', '04dkhoa04@gmail.com', 1, '2025-05-22 06:52:27', '2025-05-30 20:09:13', 1),
(4, 'John Doe', 'admin123', '$2a$10$PCke0EClQ.8ILZ3bt7AkHu59EdqWrSeUAsez8AxQtWmWJT2gkfmUO', 1, '12345678900', 'khoacdpp02847@fpt.edu.vn', 1, '2025-05-28 14:57:06', '2025-05-28 16:13:51', 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `invoices`
--

CREATE TABLE `invoices` (
  `id` bigint(20) NOT NULL,
  `customer_id` bigint(20) DEFAULT NULL,
  `employee_id` bigint(20) NOT NULL,
  `table_id` bigint(20) DEFAULT NULL,
  `discount_id` bigint(20) DEFAULT NULL,
  `sub_total` decimal(12,2) NOT NULL DEFAULT 0.00,
  `discount_amount` decimal(12,2) NOT NULL DEFAULT 0.00,
  `tax_amount` decimal(12,2) NOT NULL DEFAULT 0.00,
  `total_amount` decimal(12,2) NOT NULL DEFAULT 0.00,
  `status` enum('cancelled','completed','pending','refunded') NOT NULL,
  `note` text DEFAULT NULL,
  `created_at` datetime DEFAULT current_timestamp(),
  `updated_at` datetime DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `version` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `invoices`
--

INSERT INTO `invoices` (`id`, `customer_id`, `employee_id`, `table_id`, `discount_id`, `sub_total`, `discount_amount`, `tax_amount`, `total_amount`, `status`, `note`, `created_at`, `updated_at`, `version`) VALUES
(1, 1, 1, 1, 1, 25000.00, 0.00, 2500.00, 27500.00, 'pending', 'test invoice 01', '2025-05-31 05:08:41', '2025-05-31 05:08:41', 0);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `invoice_items`
--

CREATE TABLE `invoice_items` (
  `id` bigint(20) NOT NULL,
  `invoice_id` bigint(20) NOT NULL,
  `product_id` bigint(20) NOT NULL,
  `quantity` int(11) NOT NULL CHECK (`quantity` > 0),
  `unit_price` decimal(10,2) NOT NULL,
  `item_total` decimal(12,2) NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  `version` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `invoice_items`
--

INSERT INTO `invoice_items` (`id`, `invoice_id`, `product_id`, `quantity`, `unit_price`, `item_total`, `created_at`, `updated_at`, `version`) VALUES
(1, 1, 1, 1, 25000.00, 25000.00, '2025-05-31 05:08:41.000000', '2025-05-31 05:08:41.000000', 0);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `password_reset_tokens`
--

CREATE TABLE `password_reset_tokens` (
  `id` bigint(20) NOT NULL,
  `expiry_date` datetime(6) NOT NULL,
  `token` varchar(255) NOT NULL,
  `employee_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `payments`
--

CREATE TABLE `payments` (
  `id` bigint(20) NOT NULL,
  `invoice_id` bigint(20) NOT NULL,
  `payment_method_id` bigint(20) NOT NULL,
  `amount` decimal(10,2) NOT NULL CHECK (`amount` > 0),
  `paid_at` datetime DEFAULT current_timestamp(),
  `note` text DEFAULT NULL,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  `version` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `payment_methods`
--

CREATE TABLE `payment_methods` (
  `id` bigint(20) NOT NULL,
  `name` varchar(50) NOT NULL,
  `description` text DEFAULT NULL,
  `is_active` tinyint(1) DEFAULT 1,
  `version` bigint(20) DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `payment_methods`
--

INSERT INTO `payment_methods` (`id`, `name`, `description`, `is_active`, `version`, `created_at`, `updated_at`) VALUES
(1, 'cash', 'Tiền mặt', 1, NULL, NULL, NULL),
(2, 'card', 'Thẻ ngân hàng', 1, NULL, NULL, NULL),
(3, 'momo', 'Ví Momo', 1, NULL, NULL, NULL),
(4, 'zalo', 'Ví ZaloPay', 1, NULL, NULL, NULL),
(5, 'vnpay', 'Cổng thanh toán VNPay', 1, NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `products`
--

CREATE TABLE `products` (
  `id` bigint(20) NOT NULL,
  `barcode` varchar(100) DEFAULT NULL,
  `name` varchar(100) NOT NULL,
  `price` decimal(10,2) NOT NULL DEFAULT 0.00,
  `quantity` int(11) NOT NULL DEFAULT 0,
  `image_path` varchar(255) DEFAULT NULL,
  `category_id` bigint(20) DEFAULT NULL,
  `is_active` tinyint(1) DEFAULT 1,
  `created_at` datetime DEFAULT current_timestamp(),
  `updated_at` datetime DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `version` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `products`
--

INSERT INTO `products` (`id`, `barcode`, `name`, `price`, `quantity`, `image_path`, `category_id`, `is_active`, `created_at`, `updated_at`, `version`) VALUES
(1, 'BC123456789', 'Cà phê đen', 25000.00, 100, 'https://static.vecteezy.com/system/resources/thumbnails/041/643/200/small_2x/ai-generated-a-cup-of-coffee-and-a-piece-of-coffee-bean-perfect-for-food-and-beverage-related-designs-or-promoting-cozy-moments-png.png', 2, 1, '2025-05-23 17:41:02', '2025-05-23 18:51:34', 1),
(2, 'BC123456788', 'Trà sữa thái xanh', 30000.00, 80, 'https://png.pngtree.com/png-clipart/20220102/original/pngtree-green-milk-thai-tea-png-image_7003288.png', 1, 1, '2025-05-23 17:41:02', '2025-05-23 18:52:01', 1),
(3, 'BC123456787', 'Sinh tố bơ', 35000.00, 50, 'https://png.pngtree.com/png-vector/20240731/ourmid/pngtree-avocado-smoothie-with-isolated-on-transparent-background-png-image_13317561.png', 2, 0, '2025-05-23 17:41:02', '2025-05-23 18:52:12', 1),
(4, 'BC123456786', 'Bim Bim Oishi', 10000.00, 200, 'https://down-vn.img.susercontent.com/file/51b0f28104e4c45e4d96f8b0bad73945', 3, 0, '2025-05-23 17:41:02', '2025-05-23 18:52:33', 1),
(5, 'BC2025000005', 'Bánh Mì', 10500.00, 55, 'https://png.pngtree.com/png-vector/20240530/ourmid/pngtree-easy-and-nutritious-bread-egg-breakfast-options-for-busy-mornings-png-image_12554587.png', 3, 1, '2025-05-30 12:00:00', '2025-05-30 21:18:15', 1),
(6, 'BC2025000006', 'Phở Bò', 10600.00, 56, 'https://phovihoang.vn/wp-content/uploads/2018/01/48267.png', 1, 0, '2025-05-30 12:00:00', '2025-05-30 21:18:28', 1),
(7, 'BC2025000007', 'Bún Chả', 10700.00, 57, 'https://dailong.asia/page/download/bun-cha-ha-noi-png-rice-noodles-hanoi-KVGSis.png', 2, 1, '2025-05-30 12:00:00', '2025-05-30 21:18:44', 1),
(8, 'BC2025000008', 'Cà Phê Sữa', 10800.00, 58, 'https://khothietke.net/wp-content/uploads/2021/03/taifree1439-ca-phe-sua-tach-ca-phe-sua.png', 3, 0, '2025-05-30 12:00:00', '2025-05-30 21:19:03', 1),
(9, 'BC2025000009', 'Trà Đào', 10900.00, 59, 'https://png.pngtree.com/png-clipart/20231018/original/pngtree-iced-peach-tea-in-plastic-glass-watercolor-png-image_13342502.png', 1, 1, '2025-05-30 12:00:00', '2025-05-30 21:19:14', 1),
(10, 'BC2025000010', 'Nước Cam', 11000.00, 60, NULL, 2, 0, '2025-05-30 12:00:00', '2025-05-30 12:00:00', 1),
(11, 'BC2025000011', 'Bia Hà Nội', 11100.00, 61, NULL, 3, 1, '2025-05-30 12:00:00', '2025-05-30 12:00:00', 1),
(12, 'BC2025000012', 'Cơm Tấm', 11200.00, 62, NULL, 1, 0, '2025-05-30 12:00:00', '2025-05-30 12:00:00', 1),
(13, 'BC2025000013', 'Bún Bò Huế', 11300.00, 63, NULL, 2, 1, '2025-05-30 12:00:00', '2025-05-30 12:00:00', 1),
(14, 'BC2025000014', 'Bánh Xèo', 11400.00, 64, NULL, 3, 0, '2025-05-30 12:00:00', '2025-05-30 12:00:00', 1),
(15, 'BC2025000015', 'Nước Mía', 11500.00, 65, NULL, 1, 1, '2025-05-30 12:00:00', '2025-05-30 12:00:00', 1),
(16, 'BC2025000016', 'Bún Riêu', 11600.00, 66, NULL, 2, 0, '2025-05-30 12:00:00', '2025-05-30 12:00:00', 1),
(17, 'BC2025000017', 'Bánh Tráng Nướng', 11700.00, 67, NULL, 3, 1, '2025-05-30 12:00:00', '2025-05-30 12:00:00', 1),
(18, 'BC2025000018', 'Trà Sữa Trân Châu', 11800.00, 68, NULL, 1, 0, '2025-05-30 12:00:00', '2025-05-30 12:00:00', 1),
(19, 'BC2025000019', 'Sinh Tố Dâu', 11900.00, 69, NULL, 2, 1, '2025-05-30 12:00:00', '2025-05-30 12:00:00', 1),
(20, 'BC2025000020', 'Sinh Tố Xoài', 12000.00, 70, NULL, 3, 0, '2025-05-30 12:00:00', '2025-05-30 12:00:00', 1),
(21, 'BC2025000021', 'Cháo Gà', 12100.00, 71, NULL, 1, 1, '2025-05-30 12:00:00', '2025-05-30 12:00:00', 1),
(22, 'BC2025000022', 'Cháo Vịt', 12200.00, 72, NULL, 2, 0, '2025-05-30 12:00:00', '2025-05-30 12:00:00', 1),
(23, 'BC2025000023', 'Chè Ba Màu', 12300.00, 73, NULL, 3, 1, '2025-05-30 12:00:00', '2025-05-30 12:00:00', 1),
(24, 'BC2025000024', 'Mì Quảng', 12400.00, 74, NULL, 1, 0, '2025-05-30 12:00:00', '2025-05-30 12:00:00', 1),
(25, 'BC2025000025', 'Bánh Canh', 12500.00, 75, NULL, 2, 1, '2025-05-30 12:00:00', '2025-05-30 12:00:00', 1),
(26, 'BC2025000026', 'Súp Cua', 12600.00, 76, NULL, 3, 0, '2025-05-30 12:00:00', '2025-05-30 12:00:00', 1),
(27, 'BC2025000027', 'Sữa Đậu Nành', 12700.00, 77, NULL, 1, 1, '2025-05-30 12:00:00', '2025-05-30 12:00:00', 1),
(28, 'BC2025000028', 'Nước Dừa', 12800.00, 78, NULL, 2, 0, '2025-05-30 12:00:00', '2025-05-30 12:00:00', 1),
(29, 'BC2025000029', 'Trà Chanh', 12900.00, 79, NULL, 3, 1, '2025-05-30 12:00:00', '2025-05-30 12:00:00', 1),
(30, 'BC2025000030', 'Trà Gừng', 13000.00, 80, NULL, 1, 0, '2025-05-30 12:00:00', '2025-05-30 12:00:00', 1),
(31, 'BC2025000031', 'Soda Chanh', 13100.00, 81, NULL, 2, 1, '2025-05-30 12:00:00', '2025-05-30 12:00:00', 1),
(32, 'BC2025000032', 'Nước Ép Cà Rốt', 13200.00, 82, NULL, 3, 0, '2025-05-30 12:00:00', '2025-05-30 12:00:00', 1),
(33, 'BC2025000033', 'Nước Ép Ổi', 13300.00, 83, NULL, 1, 1, '2025-05-30 12:00:00', '2025-05-30 12:00:00', 1),
(34, 'BC2025000034', 'Bánh Ngọt', 13400.00, 84, NULL, 2, 0, '2025-05-30 12:00:00', '2025-05-30 12:00:00', 1),
(35, 'BC2025000035', 'Bánh Bao', 13500.00, 85, NULL, 3, 1, '2025-05-30 12:00:00', '2025-05-30 12:00:00', 1),
(36, 'BC2025000036', 'Bánh Chuối', 13600.00, 86, NULL, 1, 0, '2025-05-30 12:00:00', '2025-05-30 12:00:00', 1),
(37, 'BC2025000037', 'Sữa Tươi', 13700.00, 87, NULL, 2, 1, '2025-05-30 12:00:00', '2025-05-30 12:00:00', 1),
(38, 'BC2025000038', 'Sữa Chua', 13800.00, 88, NULL, 3, 0, '2025-05-30 12:00:00', '2025-05-30 12:00:00', 1),
(39, 'BC2025000039', 'Sữa Đặc', 13900.00, 89, NULL, 1, 1, '2025-05-30 12:00:00', '2025-05-30 12:00:00', 1),
(40, 'BC2025000040', 'Nước Ép Dưa Hấu', 14000.00, 90, NULL, 2, 0, '2025-05-30 12:00:00', '2025-05-30 12:00:00', 1),
(41, 'BC2025000041', 'Trà Sữa Matcha', 14100.00, 91, NULL, 3, 1, '2025-05-30 12:00:00', '2025-05-30 12:00:00', 1),
(42, 'BC2025000042', 'Sinh Tố Bơ', 14200.00, 92, NULL, 1, 0, '2025-05-30 12:00:00', '2025-05-30 12:00:00', 1),
(43, 'BC2025000043', 'Trà Sữa Sô Cô La', 14300.00, 93, NULL, 2, 1, '2025-05-30 12:00:00', '2025-05-30 12:00:00', 1),
(44, 'BC2025000044', 'Trà Đào Cam Sả', 14400.00, 94, NULL, 3, 0, '2025-05-30 12:00:00', '2025-05-30 12:00:00', 1),
(45, 'BC2025000045', 'Nước Ép Dứa', 14500.00, 95, NULL, 1, 1, '2025-05-30 12:00:00', '2025-05-30 12:00:00', 1),
(46, 'BC2025000046', 'Trà Đen', 14600.00, 96, NULL, 2, 0, '2025-05-30 12:00:00', '2025-05-30 12:00:00', 1),
(47, 'BC2025000047', 'Trà Xanh', 14700.00, 97, NULL, 3, 1, '2025-05-30 12:00:00', '2025-05-30 12:00:00', 1),
(48, 'BC2025000048', 'Trà Ô Long', 14800.00, 98, NULL, 1, 0, '2025-05-30 12:00:00', '2025-05-30 12:00:00', 1),
(49, 'BC2025000049', 'Soda Việt Quất', 14900.00, 99, NULL, 2, 1, '2025-05-30 12:00:00', '2025-05-30 12:00:00', 1),
(50, 'BC2025000050', 'Soda Dâu', 15000.00, 100, NULL, 3, 0, '2025-05-30 12:00:00', '2025-05-30 12:00:00', 1),
(51, 'BC2025000051', 'Sinh Tố Dứa', 15100.00, 101, NULL, 1, 1, '2025-05-30 12:00:00', '2025-05-30 12:00:00', 1),
(52, 'BC2025000052', 'Bánh Flan', 15200.00, 102, NULL, 2, 0, '2025-05-30 12:00:00', '2025-05-30 12:00:00', 1),
(53, 'BC2025000053', 'Chè Thái', 15300.00, 103, NULL, 3, 1, '2025-05-30 12:00:00', '2025-05-30 12:00:00', 1),
(54, 'BC2025000054', 'Chè Đậu Đen', 15400.00, 104, NULL, 1, 0, '2025-05-30 12:00:00', '2025-05-30 12:00:00', 1),
(55, 'BC2025000055', 'Bánh Mì Kẹp Thịt', 15500.00, 105, NULL, 2, 1, '2025-05-30 12:00:00', '2025-05-30 12:00:00', 1),
(56, 'BC2025000056', 'Bún Thịt Nướng', 15600.00, 106, NULL, 3, 0, '2025-05-30 12:00:00', '2025-05-30 12:00:00', 1),
(57, 'BC2025000057', 'Gỏi Cuốn', 15700.00, 107, NULL, 1, 1, '2025-05-30 12:00:00', '2025-05-30 12:00:00', 1),
(58, 'BC2025000058', 'Nước Mát', 15800.00, 108, NULL, 2, 0, '2025-05-30 12:00:00', '2025-05-30 12:00:00', 1),
(59, 'BC2025000059', 'Nước Tăng Lực', 15900.00, 109, NULL, 3, 1, '2025-05-30 12:00:00', '2025-05-30 12:00:00', 1),
(60, 'BC2025000060', 'Nước Suối', 16000.00, 110, NULL, 1, 0, '2025-05-30 12:00:00', '2025-05-30 12:00:00', 1),
(61, 'BC2025000061', 'Nước Khoáng', 16100.00, 111, NULL, 2, 1, '2025-05-30 12:00:00', '2025-05-30 12:00:00', 1),
(62, 'BC2025000062', 'Sữa Gạo', 16200.00, 112, NULL, 3, 0, '2025-05-30 12:00:00', '2025-05-30 12:00:00', 1),
(63, 'BC2025000063', 'Sữa Hạt', 16300.00, 113, NULL, 1, 1, '2025-05-30 12:00:00', '2025-05-30 12:00:00', 1),
(64, 'BC2025000064', 'Trà Sen', 16400.00, 114, NULL, 2, 0, '2025-05-30 12:00:00', '2025-05-30 12:00:00', 1),
(65, 'BC2025000065', 'Trà Hoa Cúc', 16500.00, 115, NULL, 3, 1, '2025-05-30 12:00:00', '2025-05-30 12:00:00', 1),
(66, 'BC2025000066', 'Trà Atiso', 16600.00, 116, NULL, 1, 0, '2025-05-30 12:00:00', '2025-05-30 12:00:00', 1),
(67, 'BC2025000067', 'Trà Lài', 16700.00, 117, NULL, 2, 1, '2025-05-30 12:00:00', '2025-05-30 12:00:00', 1),
(68, 'BC2025000068', 'Trà Vải', 16800.00, 118, NULL, 3, 0, '2025-05-30 12:00:00', '2025-05-30 12:00:00', 1),
(69, 'BC2025000069', 'Sinh Tố Cam', 16900.00, 119, NULL, 1, 1, '2025-05-30 12:00:00', '2025-05-30 12:00:00', 1),
(70, 'BC2025000070', 'Sinh Tố Chuối', 17000.00, 120, NULL, 2, 0, '2025-05-30 12:00:00', '2025-05-30 12:00:00', 1),
(71, 'BC2025000071', 'Nước Ép Táo', 17100.00, 121, NULL, 3, 1, '2025-05-30 12:00:00', '2025-05-30 12:00:00', 1),
(72, 'BC2025000072', 'Nước Ép Nho', 17200.00, 122, NULL, 1, 0, '2025-05-30 12:00:00', '2025-05-30 12:00:00', 1),
(73, 'BC2025000073', 'Soda Bạc Hà', 17300.00, 123, NULL, 2, 1, '2025-05-30 12:00:00', '2025-05-30 12:00:00', 1),
(74, 'BC2025000074', 'Soda Kiwi', 17400.00, 124, NULL, 3, 0, '2025-05-30 12:00:00', '2025-05-30 12:00:00', 1),
(75, 'BC2025000075', 'Soda Cam', 17500.00, 125, NULL, 1, 1, '2025-05-30 12:00:00', '2025-05-30 12:00:00', 1),
(76, 'BC2025000076', 'Sinh Tố Dưa Hấu', 17600.00, 126, NULL, 2, 0, '2025-05-30 12:00:00', '2025-05-30 12:00:00', 1),
(77, 'BC2025000077', 'Sinh Tố Cà Rốt', 17700.00, 127, NULL, 3, 1, '2025-05-30 12:00:00', '2025-05-30 12:00:00', 1),
(78, 'BC2025000078', 'Sinh Tố Mãng Cầu', 17800.00, 128, NULL, 1, 0, '2025-05-30 12:00:00', '2025-05-30 12:00:00', 1),
(79, 'BC2025000079', 'Trà Đào Sữa', 17900.00, 129, NULL, 2, 1, '2025-05-30 12:00:00', '2025-05-30 12:00:00', 1),
(80, 'BC2025000080', 'Trà Đen Sữa', 18000.00, 130, NULL, 3, 0, '2025-05-30 12:00:00', '2025-05-30 12:00:00', 1),
(81, 'BC2025000081', 'Nước Ép Dâu', 18100.00, 131, NULL, 1, 1, '2025-05-30 12:00:00', '2025-05-30 12:00:00', 1),
(82, 'BC2025000082', 'Nước Ép Dừa', 18200.00, 132, NULL, 2, 0, '2025-05-30 12:00:00', '2025-05-30 12:00:00', 1),
(83, 'BC2025000083', 'Nước Ép Mía', 18300.00, 133, NULL, 3, 1, '2025-05-30 12:00:00', '2025-05-30 12:00:00', 1),
(84, 'BC2025000084', 'Sữa Tươi Không Đường', 18400.00, 134, NULL, 1, 0, '2025-05-30 12:00:00', '2025-05-30 12:00:00', 1),
(85, 'BC2025000085', 'Sữa Tươi Có Đường', 18500.00, 135, NULL, 2, 1, '2025-05-30 12:00:00', '2025-05-30 12:00:00', 1),
(86, 'BC2025000086', 'Nước Lọc', 18600.00, 136, NULL, 3, 0, '2025-05-30 12:00:00', '2025-05-30 12:00:00', 1),
(87, 'BC2025000087', 'Nước Khoáng Có Gas', 18700.00, 137, NULL, 1, 1, '2025-05-30 12:00:00', '2025-05-30 12:00:00', 1),
(88, 'BC2025000088', 'Nước Tăng Lực Hương Cam', 18800.00, 138, NULL, 2, 0, '2025-05-30 12:00:00', '2025-05-30 12:00:00', 1),
(89, 'BC2025000089', 'Nước Giải Khát Vị Chanh', 18900.00, 139, NULL, 3, 1, '2025-05-30 12:00:00', '2025-05-30 12:00:00', 1),
(90, 'BC2025000090', 'Nước Ngọt Coca', 19000.00, 140, NULL, 1, 0, '2025-05-30 12:00:00', '2025-05-30 12:00:00', 1),
(91, 'BC2025000091', 'Nước Ngọt Pepsi', 19100.00, 141, NULL, 2, 1, '2025-05-30 12:00:00', '2025-05-30 12:00:00', 1),
(92, 'BC2025000092', 'Nước Ngọt 7Up', 19200.00, 142, NULL, 3, 0, '2025-05-30 12:00:00', '2025-05-30 12:00:00', 1),
(93, 'BC2025000093', 'Nước Ngọt Sting', 19300.00, 143, NULL, 1, 1, '2025-05-30 12:00:00', '2025-05-30 12:00:00', 1),
(94, 'BC2025000094', 'Nước Ngọt Mirinda', 19400.00, 144, NULL, 2, 0, '2025-05-30 12:00:00', '2025-05-30 12:00:00', 1),
(95, 'BC2025000095', 'Bánh Quy', 19500.00, 145, NULL, 3, 1, '2025-05-30 12:00:00', '2025-05-30 12:00:00', 1),
(96, 'BC2025000096', 'Bánh Quy Bơ', 19600.00, 146, NULL, 1, 0, '2025-05-30 12:00:00', '2025-05-30 12:00:00', 1),
(97, 'BC2025000097', 'Bánh Quy Sô Cô La', 19700.00, 147, NULL, 2, 1, '2025-05-30 12:00:00', '2025-05-30 12:00:00', 1),
(98, 'BC2025000098', 'Bánh Quy Dừa', 19800.00, 148, NULL, 3, 0, '2025-05-30 12:00:00', '2025-05-30 12:00:00', 1),
(99, 'BC2025000099', 'Bánh Quy Trà Xanh', 19900.00, 149, NULL, 1, 1, '2025-05-30 12:00:00', '2025-05-30 12:00:00', 1),
(100, 'BC2025000100', 'Bánh Gạo', 20000.00, 150, NULL, 2, 0, '2025-05-30 12:00:00', '2025-05-30 12:00:00', 1),
(101, 'BC2025000101', 'Bánh Tráng', 20100.00, 151, NULL, 3, 1, '2025-05-30 12:00:00', '2025-05-30 12:00:00', 1),
(102, 'BC2025000102', 'Bánh Rán', 20200.00, 152, NULL, 1, 0, '2025-05-30 12:00:00', '2025-05-30 12:00:00', 1),
(103, 'BC2025000103', 'Nước Ép Dưa Leo', 20300.00, 153, NULL, 2, 1, '2025-05-30 12:00:00', '2025-05-30 12:00:00', 1),
(104, 'BC2025000104', 'Sữa Tươi Hương Dâu', 20400.00, 154, NULL, 3, 0, '2025-05-30 12:00:00', '2025-05-30 12:00:00', 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `roles`
--

CREATE TABLE `roles` (
  `id` bigint(20) NOT NULL,
  `name` varchar(50) NOT NULL,
  `created_at` datetime DEFAULT current_timestamp(),
  `updated_at` datetime DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `version` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `roles`
--

INSERT INTO `roles` (`id`, `name`, `created_at`, `updated_at`, `version`) VALUES
(1, 'EMPLOYEE', '2025-05-22 06:52:01', '2025-05-22 06:52:01', 0),
(2, 'ADMIN', '2025-05-22 06:52:01', '2025-05-22 06:52:01', 0);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `settings`
--

CREATE TABLE `settings` (
  `id` bigint(20) NOT NULL,
  `store_name` varchar(100) NOT NULL,
  `address` text DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `logo_path` text DEFAULT NULL,
  `tax_rate` decimal(5,2) DEFAULT 0.00 CHECK (`tax_rate` >= 0 and `tax_rate` <= 100),
  `currency` varchar(10) DEFAULT 'VND',
  `invoice_prefix` varchar(20) DEFAULT NULL,
  `printer_name` varchar(100) DEFAULT NULL,
  `default_language` varchar(10) DEFAULT 'VI',
  `backup_path` text DEFAULT NULL,
  `created_at` datetime DEFAULT current_timestamp(),
  `updated_at` datetime DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `version` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `settings`
--

INSERT INTO `settings` (`id`, `store_name`, `address`, `phone`, `email`, `logo_path`, `tax_rate`, `currency`, `invoice_prefix`, `printer_name`, `default_language`, `backup_path`, `created_at`, `updated_at`, `version`) VALUES
(1, 'POS - Quản lý bán hàng', 'Số Nhà 4B66, Ngõ 296 Ngô Gia Tự, Phường Cát Bi, Quận Hải An, Hải Phòng', '0869938981', 'info@abcshop.vn', 'logo.png', 10.00, 'VND', 'INV-', 'Printer_POS_1', 'VI', 'D:/POS/backup/', '2025-05-28 16:58:13', '2025-05-28 16:58:13', NULL);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `stock_in`
--

CREATE TABLE `stock_in` (
  `id` bigint(20) NOT NULL,
  `product_id` bigint(20) NOT NULL,
  `quantity` int(11) NOT NULL CHECK (`quantity` > 0),
  `note` text DEFAULT NULL,
  `employee_id` bigint(20) DEFAULT NULL,
  `created_at` datetime DEFAULT current_timestamp(),
  `version` bigint(20) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `stock_in`
--

INSERT INTO `stock_in` (`id`, `product_id`, `quantity`, `note`, `employee_id`, `created_at`, `version`, `updated_at`) VALUES
(1, 1, 50, 'Nhập lô mới tháng 4', 1, '2025-05-28 16:58:56', NULL, NULL),
(2, 2, 30, 'Nhập thêm do sắp hết', NULL, '2025-05-28 16:58:56', NULL, NULL);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `stock_out`
--

CREATE TABLE `stock_out` (
  `id` bigint(20) NOT NULL,
  `product_id` bigint(20) NOT NULL,
  `quantity` int(11) NOT NULL CHECK (`quantity` > 0),
  `note` text DEFAULT NULL,
  `employee_id` bigint(20) DEFAULT NULL,
  `created_at` datetime DEFAULT current_timestamp(),
  `version` bigint(20) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `stock_out`
--

INSERT INTO `stock_out` (`id`, `product_id`, `quantity`, `note`, `employee_id`, `created_at`, `version`, `updated_at`) VALUES
(1, 1, 5, 'Xuất cho chi nhánh 2', 1, '2025-05-28 16:58:56', NULL, NULL),
(2, 3, 2, 'Xuất nhầm cần kiểm tra', NULL, '2025-05-28 16:58:56', NULL, NULL);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `tables`
--

CREATE TABLE `tables` (
  `id` bigint(20) NOT NULL,
  `name` varchar(50) NOT NULL,
  `status` enum('available','cleaning','occupied','out_of_service','reserved') NOT NULL,
  `capacity` int(11) DEFAULT 0,
  `created_at` datetime DEFAULT current_timestamp(),
  `updated_at` datetime DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `version` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `tables`
--

INSERT INTO `tables` (`id`, `name`, `status`, `capacity`, `created_at`, `updated_at`, `version`) VALUES
(1, 'Bàn 1', 'available', 4, '2025-05-28 16:58:28', '2025-05-28 16:58:28', NULL),
(2, 'Bàn 2', 'occupied', 2, '2025-05-28 16:58:28', '2025-05-28 16:58:28', NULL),
(3, 'Bàn 3', 'reserved', 6, '2025-05-28 16:58:28', '2025-05-28 16:58:28', NULL);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `user_sessions`
--

CREATE TABLE `user_sessions` (
  `id` bigint(20) NOT NULL,
  `employee_id` bigint(20) NOT NULL,
  `session_token` varchar(255) NOT NULL,
  `login_time` datetime DEFAULT current_timestamp(),
  `logout_time` datetime DEFAULT NULL,
  `ip_address` varchar(45) DEFAULT NULL,
  `user_agent` text DEFAULT NULL,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  `version` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `audit_logs`
--
ALTER TABLE `audit_logs`
  ADD PRIMARY KEY (`id`),
  ADD KEY `employee_id` (`employee_id`);

--
-- Chỉ mục cho bảng `categories`
--
ALTER TABLE `categories`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `name` (`name`);

--
-- Chỉ mục cho bảng `customers`
--
ALTER TABLE `customers`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `phone` (`phone`),
  ADD UNIQUE KEY `email` (`email`);

--
-- Chỉ mục cho bảng `discounts`
--
ALTER TABLE `discounts`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `code` (`code`),
  ADD KEY `discount_type_id` (`discount_type_id`);

--
-- Chỉ mục cho bảng `discount_types`
--
ALTER TABLE `discount_types`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `name` (`name`);

--
-- Chỉ mục cho bảng `employees`
--
ALTER TABLE `employees`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`),
  ADD UNIQUE KEY `phone` (`phone`),
  ADD UNIQUE KEY `email` (`email`),
  ADD KEY `role_id` (`role_id`);

--
-- Chỉ mục cho bảng `invoices`
--
ALTER TABLE `invoices`
  ADD PRIMARY KEY (`id`),
  ADD KEY `customer_id` (`customer_id`),
  ADD KEY `employee_id` (`employee_id`),
  ADD KEY `table_id` (`table_id`),
  ADD KEY `discount_id` (`discount_id`);

--
-- Chỉ mục cho bảng `invoice_items`
--
ALTER TABLE `invoice_items`
  ADD PRIMARY KEY (`id`),
  ADD KEY `invoice_id` (`invoice_id`),
  ADD KEY `product_id` (`product_id`);

--
-- Chỉ mục cho bảng `password_reset_tokens`
--
ALTER TABLE `password_reset_tokens`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK71lqwbwtklmljk3qlsugr1mig` (`token`),
  ADD UNIQUE KEY `UKma50iqjiloli72b9i97bi8gi8` (`employee_id`);

--
-- Chỉ mục cho bảng `payments`
--
ALTER TABLE `payments`
  ADD PRIMARY KEY (`id`),
  ADD KEY `invoice_id` (`invoice_id`),
  ADD KEY `payment_method_id` (`payment_method_id`);

--
-- Chỉ mục cho bảng `payment_methods`
--
ALTER TABLE `payment_methods`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `name` (`name`);

--
-- Chỉ mục cho bảng `products`
--
ALTER TABLE `products`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `barcode` (`barcode`),
  ADD KEY `category_id` (`category_id`);

--
-- Chỉ mục cho bảng `roles`
--
ALTER TABLE `roles`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `name` (`name`);

--
-- Chỉ mục cho bảng `settings`
--
ALTER TABLE `settings`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `stock_in`
--
ALTER TABLE `stock_in`
  ADD PRIMARY KEY (`id`),
  ADD KEY `product_id` (`product_id`),
  ADD KEY `employee_id` (`employee_id`);

--
-- Chỉ mục cho bảng `stock_out`
--
ALTER TABLE `stock_out`
  ADD PRIMARY KEY (`id`),
  ADD KEY `product_id` (`product_id`),
  ADD KEY `employee_id` (`employee_id`);

--
-- Chỉ mục cho bảng `tables`
--
ALTER TABLE `tables`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `name` (`name`);

--
-- Chỉ mục cho bảng `user_sessions`
--
ALTER TABLE `user_sessions`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `session_token` (`session_token`),
  ADD KEY `employee_id` (`employee_id`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `audit_logs`
--
ALTER TABLE `audit_logs`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `categories`
--
ALTER TABLE `categories`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT cho bảng `customers`
--
ALTER TABLE `customers`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT cho bảng `discounts`
--
ALTER TABLE `discounts`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT cho bảng `discount_types`
--
ALTER TABLE `discount_types`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT cho bảng `employees`
--
ALTER TABLE `employees`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT cho bảng `invoices`
--
ALTER TABLE `invoices`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT cho bảng `invoice_items`
--
ALTER TABLE `invoice_items`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT cho bảng `password_reset_tokens`
--
ALTER TABLE `password_reset_tokens`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT cho bảng `payments`
--
ALTER TABLE `payments`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `payment_methods`
--
ALTER TABLE `payment_methods`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT cho bảng `products`
--
ALTER TABLE `products`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=105;

--
-- AUTO_INCREMENT cho bảng `roles`
--
ALTER TABLE `roles`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT cho bảng `stock_in`
--
ALTER TABLE `stock_in`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT cho bảng `stock_out`
--
ALTER TABLE `stock_out`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT cho bảng `tables`
--
ALTER TABLE `tables`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT cho bảng `user_sessions`
--
ALTER TABLE `user_sessions`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- Các ràng buộc cho các bảng đã đổ
--

--
-- Các ràng buộc cho bảng `audit_logs`
--
ALTER TABLE `audit_logs`
  ADD CONSTRAINT `audit_logs_ibfk_1` FOREIGN KEY (`employee_id`) REFERENCES `employees` (`id`);

--
-- Các ràng buộc cho bảng `discounts`
--
ALTER TABLE `discounts`
  ADD CONSTRAINT `discounts_ibfk_1` FOREIGN KEY (`discount_type_id`) REFERENCES `discount_types` (`id`);

--
-- Các ràng buộc cho bảng `employees`
--
ALTER TABLE `employees`
  ADD CONSTRAINT `employees_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`);

--
-- Các ràng buộc cho bảng `invoices`
--
ALTER TABLE `invoices`
  ADD CONSTRAINT `invoices_ibfk_1` FOREIGN KEY (`customer_id`) REFERENCES `customers` (`id`),
  ADD CONSTRAINT `invoices_ibfk_2` FOREIGN KEY (`employee_id`) REFERENCES `employees` (`id`),
  ADD CONSTRAINT `invoices_ibfk_3` FOREIGN KEY (`table_id`) REFERENCES `tables` (`id`),
  ADD CONSTRAINT `invoices_ibfk_4` FOREIGN KEY (`discount_id`) REFERENCES `discounts` (`id`);

--
-- Các ràng buộc cho bảng `invoice_items`
--
ALTER TABLE `invoice_items`
  ADD CONSTRAINT `invoice_items_ibfk_1` FOREIGN KEY (`invoice_id`) REFERENCES `invoices` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `invoice_items_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`);

--
-- Các ràng buộc cho bảng `password_reset_tokens`
--
ALTER TABLE `password_reset_tokens`
  ADD CONSTRAINT `FKsw5coj33ux4dbhs9ollqlccfl` FOREIGN KEY (`employee_id`) REFERENCES `employees` (`id`);

--
-- Các ràng buộc cho bảng `payments`
--
ALTER TABLE `payments`
  ADD CONSTRAINT `payments_ibfk_1` FOREIGN KEY (`invoice_id`) REFERENCES `invoices` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `payments_ibfk_2` FOREIGN KEY (`payment_method_id`) REFERENCES `payment_methods` (`id`);

--
-- Các ràng buộc cho bảng `products`
--
ALTER TABLE `products`
  ADD CONSTRAINT `products_ibfk_1` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`);

--
-- Các ràng buộc cho bảng `stock_in`
--
ALTER TABLE `stock_in`
  ADD CONSTRAINT `stock_in_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`),
  ADD CONSTRAINT `stock_in_ibfk_2` FOREIGN KEY (`employee_id`) REFERENCES `employees` (`id`);

--
-- Các ràng buộc cho bảng `stock_out`
--
ALTER TABLE `stock_out`
  ADD CONSTRAINT `stock_out_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`),
  ADD CONSTRAINT `stock_out_ibfk_2` FOREIGN KEY (`employee_id`) REFERENCES `employees` (`id`);

--
-- Các ràng buộc cho bảng `user_sessions`
--
ALTER TABLE `user_sessions`
  ADD CONSTRAINT `user_sessions_ibfk_1` FOREIGN KEY (`employee_id`) REFERENCES `employees` (`id`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
