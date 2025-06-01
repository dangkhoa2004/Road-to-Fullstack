-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th6 01, 2025 lúc 07:40 AM
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
(3, 'Lê Văn C', '0912345678', 'c@gmail.com', '20 Phan Chu Trinh, Q.Hải An', '2025-05-28 21:36:31', '2025-05-28 21:36:31', NULL),
(4, 'Customer 4', '0901234004', 'customer4@example.com', '4 Đường Mẫu, Quận 5', '2025-05-28 21:36:31', '2025-05-28 21:36:31', NULL),
(5, 'Customer 5', '0901234005', 'customer5@example.com', '5 Đường Mẫu, Quận 6', '2025-05-28 21:36:31', '2025-05-28 21:36:31', NULL),
(6, 'Customer 6', '0901234006', 'customer6@example.com', '6 Đường Mẫu, Quận 7', '2025-05-28 21:36:31', '2025-05-28 21:36:31', NULL),
(7, 'Customer 7', '0901234007', 'customer7@example.com', '7 Đường Mẫu, Quận 8', '2025-05-28 21:36:31', '2025-05-28 21:36:31', NULL),
(8, 'Customer 8', '0901234008', 'customer8@example.com', '8 Đường Mẫu, Quận 9', '2025-05-28 21:36:31', '2025-05-28 21:36:31', NULL),
(9, 'Customer 9', '0901234009', 'customer9@example.com', '9 Đường Mẫu, Quận 10', '2025-05-28 21:36:31', '2025-05-28 21:36:31', NULL),
(10, 'Customer 10', '0901234010', 'customer10@example.com', '10 Đường Mẫu, Quận 1', '2025-05-28 21:36:31', '2025-05-28 21:36:31', NULL),
(11, 'Customer 11', '0901234011', 'customer11@example.com', '11 Đường Mẫu, Quận 2', '2025-05-28 21:36:31', '2025-05-28 21:36:31', NULL),
(12, 'Customer 12', '0901234012', 'customer12@example.com', '12 Đường Mẫu, Quận 3', '2025-05-28 21:36:31', '2025-05-28 21:36:31', NULL),
(13, 'Customer 13', '0901234013', 'customer13@example.com', '13 Đường Mẫu, Quận 4', '2025-05-28 21:36:31', '2025-05-28 21:36:31', NULL),
(14, 'Customer 14', '0901234014', 'customer14@example.com', '14 Đường Mẫu, Quận 5', '2025-05-28 21:36:31', '2025-05-28 21:36:31', NULL),
(15, 'Customer 15', '0901234015', 'customer15@example.com', '15 Đường Mẫu, Quận 6', '2025-05-28 21:36:31', '2025-05-28 21:36:31', NULL),
(16, 'Customer 16', '0901234016', 'customer16@example.com', '16 Đường Mẫu, Quận 7', '2025-05-28 21:36:31', '2025-05-28 21:36:31', NULL),
(17, 'Customer 17', '0901234017', 'customer17@example.com', '17 Đường Mẫu, Quận 8', '2025-05-28 21:36:31', '2025-05-28 21:36:31', NULL),
(18, 'Customer 18', '0901234018', 'customer18@example.com', '18 Đường Mẫu, Quận 9', '2025-05-28 21:36:31', '2025-05-28 21:36:31', NULL),
(19, 'Customer 19', '0901234019', 'customer19@example.com', '19 Đường Mẫu, Quận 10', '2025-05-28 21:36:31', '2025-05-28 21:36:31', NULL),
(20, 'Customer 20', '0901234020', 'customer20@example.com', '20 Đường Mẫu, Quận 1', '2025-05-28 21:36:31', '2025-05-28 21:36:31', NULL),
(21, 'Customer 21', '0901234021', 'customer21@example.com', '21 Đường Mẫu, Quận 2', '2025-05-28 21:36:31', '2025-05-28 21:36:31', NULL),
(22, 'Customer 22', '0901234022', 'customer22@example.com', '22 Đường Mẫu, Quận 3', '2025-05-28 21:36:31', '2025-05-28 21:36:31', NULL),
(23, 'Customer 23', '0901234023', 'customer23@example.com', '23 Đường Mẫu, Quận 4', '2025-05-28 21:36:31', '2025-05-28 21:36:31', NULL),
(24, 'Customer 24', '0901234024', 'customer24@example.com', '24 Đường Mẫu, Quận 5', '2025-05-28 21:36:31', '2025-05-28 21:36:31', NULL),
(25, 'Customer 25', '0901234025', 'customer25@example.com', '25 Đường Mẫu, Quận 6', '2025-05-28 21:36:31', '2025-05-28 21:36:31', NULL),
(26, 'Customer 26', '0901234026', 'customer26@example.com', '26 Đường Mẫu, Quận 7', '2025-05-28 21:36:31', '2025-05-28 21:36:31', NULL),
(27, 'Customer 27', '0901234027', 'customer27@example.com', '27 Đường Mẫu, Quận 8', '2025-05-28 21:36:31', '2025-05-28 21:36:31', NULL),
(28, 'Customer 28', '0901234028', 'customer28@example.com', '28 Đường Mẫu, Quận 9', '2025-05-28 21:36:31', '2025-05-28 21:36:31', NULL),
(29, 'Customer 29', '0901234029', 'customer29@example.com', '29 Đường Mẫu, Quận 10', '2025-05-28 21:36:31', '2025-05-28 21:36:31', NULL),
(30, 'Customer 30', '0901234030', 'customer30@example.com', '30 Đường Mẫu, Quận 1', '2025-05-28 21:36:31', '2025-05-28 21:36:31', NULL),
(31, 'Customer 31', '0901234031', 'customer31@example.com', '31 Đường Mẫu, Quận 2', '2025-05-28 21:36:31', '2025-05-28 21:36:31', NULL),
(32, 'Customer 32', '0901234032', 'customer32@example.com', '32 Đường Mẫu, Quận 3', '2025-05-28 21:36:31', '2025-05-28 21:36:31', NULL),
(33, 'Customer 33', '0901234033', 'customer33@example.com', '33 Đường Mẫu, Quận 4', '2025-05-28 21:36:31', '2025-05-28 21:36:31', NULL),
(34, 'Customer 34', '0901234034', 'customer34@example.com', '34 Đường Mẫu, Quận 5', '2025-05-28 21:36:31', '2025-05-28 21:36:31', NULL),
(35, 'Customer 35', '0901234035', 'customer35@example.com', '35 Đường Mẫu, Quận 6', '2025-05-28 21:36:31', '2025-05-28 21:36:31', NULL),
(36, 'Customer 36', '0901234036', 'customer36@example.com', '36 Đường Mẫu, Quận 7', '2025-05-28 21:36:31', '2025-05-28 21:36:31', NULL),
(37, 'Customer 37', '0901234037', 'customer37@example.com', '37 Đường Mẫu, Quận 8', '2025-05-28 21:36:31', '2025-05-28 21:36:31', NULL),
(38, 'Customer 38', '0901234038', 'customer38@example.com', '38 Đường Mẫu, Quận 9', '2025-05-28 21:36:31', '2025-05-28 21:36:31', NULL),
(39, 'Customer 39', '0901234039', 'customer39@example.com', '39 Đường Mẫu, Quận 10', '2025-05-28 21:36:31', '2025-05-28 21:36:31', NULL),
(40, 'Customer 40', '0901234040', 'customer40@example.com', '40 Đường Mẫu, Quận 1', '2025-05-28 21:36:31', '2025-05-28 21:36:31', NULL),
(41, 'Customer 41', '0901234041', 'customer41@example.com', '41 Đường Mẫu, Quận 2', '2025-05-28 21:36:31', '2025-05-28 21:36:31', NULL),
(42, 'Customer 42', '0901234042', 'customer42@example.com', '42 Đường Mẫu, Quận 3', '2025-05-28 21:36:31', '2025-05-28 21:36:31', NULL),
(43, 'Customer 43', '0901234043', 'customer43@example.com', '43 Đường Mẫu, Quận 4', '2025-05-28 21:36:31', '2025-05-28 21:36:31', NULL),
(44, 'Customer 44', '0901234044', 'customer44@example.com', '44 Đường Mẫu, Quận 5', '2025-05-28 21:36:31', '2025-05-28 21:36:31', NULL),
(45, 'Customer 45', '0901234045', 'customer45@example.com', '45 Đường Mẫu, Quận 6', '2025-05-28 21:36:31', '2025-05-28 21:36:31', NULL),
(46, 'Customer 46', '0901234046', 'customer46@example.com', '46 Đường Mẫu, Quận 7', '2025-05-28 21:36:31', '2025-05-28 21:36:31', NULL),
(47, 'Customer 47', '0901234047', 'customer47@example.com', '47 Đường Mẫu, Quận 8', '2025-05-28 21:36:31', '2025-05-28 21:36:31', NULL),
(48, 'Customer 48', '0901234048', 'customer48@example.com', '48 Đường Mẫu, Quận 9', '2025-05-28 21:36:31', '2025-05-28 21:36:31', NULL),
(49, 'Customer 49', '0901234049', 'customer49@example.com', '49 Đường Mẫu, Quận 10', '2025-05-28 21:36:31', '2025-05-28 21:36:31', NULL),
(50, 'Customer 50', '0901234050', 'customer50@example.com', '50 Đường Mẫu, Quận 1', '2025-05-28 21:36:31', '2025-05-28 21:36:31', NULL),
(51, 'Customer 51', '0901234051', 'customer51@example.com', '51 Đường Mẫu, Quận 2', '2025-05-28 21:36:31', '2025-05-28 21:36:31', NULL),
(52, 'Customer 52', '0901234052', 'customer52@example.com', '52 Đường Mẫu, Quận 3', '2025-05-28 21:36:31', '2025-05-28 21:36:31', NULL),
(53, 'Customer 53', '0901234053', 'customer53@example.com', '53 Đường Mẫu, Quận 4', '2025-05-28 21:36:31', '2025-05-28 21:36:31', NULL),
(54, 'Customer 54', '0901234054', 'customer54@example.com', '54 Đường Mẫu, Quận 5', '2025-05-28 21:36:31', '2025-05-28 21:36:31', NULL),
(55, 'Customer 55', '0901234055', 'customer55@example.com', '55 Đường Mẫu, Quận 6', '2025-05-28 21:36:31', '2025-05-28 21:36:31', NULL),
(56, 'Customer 56', '0901234056', 'customer56@example.com', '56 Đường Mẫu, Quận 7', '2025-05-28 21:36:31', '2025-05-28 21:36:31', NULL),
(57, 'Customer 57', '0901234057', 'customer57@example.com', '57 Đường Mẫu, Quận 8', '2025-05-28 21:36:31', '2025-05-28 21:36:31', NULL),
(58, 'Customer 58', '0901234058', 'customer58@example.com', '58 Đường Mẫu, Quận 9', '2025-05-28 21:36:31', '2025-05-28 21:36:31', NULL),
(59, 'Customer 59', '0901234059', 'customer59@example.com', '59 Đường Mẫu, Quận 10', '2025-05-28 21:36:31', '2025-05-28 21:36:31', NULL),
(60, 'Customer 60', '0901234060', 'customer60@example.com', '60 Đường Mẫu, Quận 1', '2025-05-28 21:36:31', '2025-05-28 21:36:31', NULL),
(61, 'Customer 61', '0901234061', 'customer61@example.com', '61 Đường Mẫu, Quận 2', '2025-05-28 21:36:31', '2025-05-28 21:36:31', NULL),
(62, 'Customer 62', '0901234062', 'customer62@example.com', '62 Đường Mẫu, Quận 3', '2025-05-28 21:36:31', '2025-05-28 21:36:31', NULL),
(63, 'Customer 63', '0901234063', 'customer63@example.com', '63 Đường Mẫu, Quận 4', '2025-05-28 21:36:31', '2025-05-28 21:36:31', NULL),
(64, 'Customer 64', '0901234064', 'customer64@example.com', '64 Đường Mẫu, Quận 5', '2025-05-28 21:36:31', '2025-05-28 21:36:31', NULL),
(65, 'Customer 65', '0901234065', 'customer65@example.com', '65 Đường Mẫu, Quận 6', '2025-05-28 21:36:31', '2025-05-28 21:36:31', NULL),
(66, 'Customer 66', '0901234066', 'customer66@example.com', '66 Đường Mẫu, Quận 7', '2025-05-28 21:36:31', '2025-05-28 21:36:31', NULL),
(67, 'Customer 67', '0901234067', 'customer67@example.com', '67 Đường Mẫu, Quận 8', '2025-05-28 21:36:31', '2025-05-28 21:36:31', NULL),
(68, 'Customer 68', '0901234068', 'customer68@example.com', '68 Đường Mẫu, Quận 9', '2025-05-28 21:36:31', '2025-05-28 21:36:31', NULL),
(69, 'Customer 69', '0901234069', 'customer69@example.com', '69 Đường Mẫu, Quận 10', '2025-05-28 21:36:31', '2025-05-28 21:36:31', NULL),
(70, 'Customer 70', '0901234070', 'customer70@example.com', '70 Đường Mẫu, Quận 1', '2025-05-28 21:36:31', '2025-05-28 21:36:31', NULL),
(71, 'Customer 71', '0901234071', 'customer71@example.com', '71 Đường Mẫu, Quận 2', '2025-05-28 21:36:31', '2025-05-28 21:36:31', NULL),
(72, 'Customer 72', '0901234072', 'customer72@example.com', '72 Đường Mẫu, Quận 3', '2025-05-28 21:36:31', '2025-05-28 21:36:31', NULL),
(73, 'Customer 73', '0901234073', 'customer73@example.com', '73 Đường Mẫu, Quận 4', '2025-05-28 21:36:31', '2025-05-28 21:36:31', NULL),
(74, 'Customer 74', '0901234074', 'customer74@example.com', '74 Đường Mẫu, Quận 5', '2025-05-28 21:36:31', '2025-05-28 21:36:31', NULL),
(75, 'Customer 75', '0901234075', 'customer75@example.com', '75 Đường Mẫu, Quận 6', '2025-05-28 21:36:31', '2025-05-28 21:36:31', NULL),
(76, 'Customer 76', '0901234076', 'customer76@example.com', '76 Đường Mẫu, Quận 7', '2025-05-28 21:36:31', '2025-05-28 21:36:31', NULL),
(77, 'Customer 77', '0901234077', 'customer77@example.com', '77 Đường Mẫu, Quận 8', '2025-05-28 21:36:31', '2025-05-28 21:36:31', NULL),
(78, 'Customer 78', '0901234078', 'customer78@example.com', '78 Đường Mẫu, Quận 9', '2025-05-28 21:36:31', '2025-05-28 21:36:31', NULL),
(79, 'Customer 79', '0901234079', 'customer79@example.com', '79 Đường Mẫu, Quận 10', '2025-05-28 21:36:31', '2025-05-28 21:36:31', NULL),
(80, 'Customer 80', '0901234080', 'customer80@example.com', '80 Đường Mẫu, Quận 1', '2025-05-28 21:36:31', '2025-05-28 21:36:31', NULL),
(81, 'Customer 81', '0901234081', 'customer81@example.com', '81 Đường Mẫu, Quận 2', '2025-05-28 21:36:31', '2025-05-28 21:36:31', NULL),
(82, 'Customer 82', '0901234082', 'customer82@example.com', '82 Đường Mẫu, Quận 3', '2025-05-28 21:36:31', '2025-05-28 21:36:31', NULL),
(83, 'Customer 83', '0901234083', 'customer83@example.com', '83 Đường Mẫu, Quận 4', '2025-05-28 21:36:31', '2025-05-28 21:36:31', NULL),
(84, 'Customer 84', '0901234084', 'customer84@example.com', '84 Đường Mẫu, Quận 5', '2025-05-28 21:36:31', '2025-05-28 21:36:31', NULL),
(85, 'Customer 85', '0901234085', 'customer85@example.com', '85 Đường Mẫu, Quận 6', '2025-05-28 21:36:31', '2025-05-28 21:36:31', NULL),
(86, 'Customer 86', '0901234086', 'customer86@example.com', '86 Đường Mẫu, Quận 7', '2025-05-28 21:36:31', '2025-05-28 21:36:31', NULL),
(87, 'Customer 87', '0901234087', 'customer87@example.com', '87 Đường Mẫu, Quận 8', '2025-05-28 21:36:31', '2025-05-28 21:36:31', NULL),
(88, 'Customer 88', '0901234088', 'customer88@example.com', '88 Đường Mẫu, Quận 9', '2025-05-28 21:36:31', '2025-05-28 21:36:31', NULL),
(89, 'Customer 89', '0901234089', 'customer89@example.com', '89 Đường Mẫu, Quận 10', '2025-05-28 21:36:31', '2025-05-28 21:36:31', NULL),
(90, 'Customer 90', '0901234090', 'customer90@example.com', '90 Đường Mẫu, Quận 1', '2025-05-28 21:36:31', '2025-05-28 21:36:31', NULL),
(91, 'Customer 91', '0901234091', 'customer91@example.com', '91 Đường Mẫu, Quận 2', '2025-05-28 21:36:31', '2025-05-28 21:36:31', NULL),
(92, 'Customer 92', '0901234092', 'customer92@example.com', '92 Đường Mẫu, Quận 3', '2025-05-28 21:36:31', '2025-05-28 21:36:31', NULL),
(93, 'Customer 93', '0901234093', 'customer93@example.com', '93 Đường Mẫu, Quận 4', '2025-05-28 21:36:31', '2025-05-28 21:36:31', NULL),
(94, 'Customer 94', '0901234094', 'customer94@example.com', '94 Đường Mẫu, Quận 5', '2025-05-28 21:36:31', '2025-05-28 21:36:31', NULL),
(95, 'Customer 95', '0901234095', 'customer95@example.com', '95 Đường Mẫu, Quận 6', '2025-05-28 21:36:31', '2025-05-28 21:36:31', NULL),
(96, 'Customer 96', '0901234096', 'customer96@example.com', '96 Đường Mẫu, Quận 7', '2025-05-28 21:36:31', '2025-05-28 21:36:31', NULL),
(97, 'Customer 97', '0901234097', 'customer97@example.com', '97 Đường Mẫu, Quận 8', '2025-05-28 21:36:31', '2025-05-28 21:36:31', NULL),
(98, 'Customer 98', '0901234098', 'customer98@example.com', '98 Đường Mẫu, Quận 9', '2025-05-28 21:36:31', '2025-05-28 21:36:31', NULL),
(99, 'Customer 99', '0901234099', 'customer99@example.com', '99 Đường Mẫu, Quận 10', '2025-05-28 21:36:31', '2025-05-28 21:36:31', NULL),
(100, 'Customer 100', '0901234100', 'customer100@example.com', '100 Đường Mẫu, Quận 1', '2025-05-28 21:36:31', '2025-05-28 21:36:31', NULL),
(101, 'Customer 101', '0901234101', 'customer101@example.com', '101 Đường Mẫu, Quận 2', '2025-05-28 21:36:31', '2025-05-28 21:36:31', NULL),
(102, 'Customer 102', '0901234102', 'customer102@example.com', '102 Đường Mẫu, Quận 3', '2025-05-28 21:36:31', '2025-05-28 21:36:31', NULL),
(103, 'Customer 103', '0901234103', 'customer103@example.com', '103 Đường Mẫu, Quận 4', '2025-05-28 21:36:31', '2025-05-28 21:36:31', NULL);

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
(1, 'Cao Đăng Khoa', 'admin', '$2a$10$JtWbThTzQhvXxFL90AdhoePJAx6xG4eUJkdBgrMGHhXcEWK49poDW', 2, '1234567890', '04dkhoa04@gmail.com', 1, '2025-05-22 06:52:27', '2025-05-31 18:52:48', 1),
(2, 'Employee 2', 'user2', '$2a$10$JtWbThTzQhvXxFL90AdhoePJAx6xG4eUJkdBgrMGHhXcEWK49poDW', 1, '2345671002', 'user2@example.com', 1, '2025-05-31 18:52:48', '2025-05-31 18:52:48', 1),
(3, 'Employee 3', 'user3', '$2a$10$JtWbThTzQhvXxFL90AdhoePJAx6xG4eUJkdBgrMGHhXcEWK49poDW', 2, '2345671003', 'user3@example.com', 1, '2025-05-31 18:52:48', '2025-05-31 18:52:48', 1),
(4, 'Employee 4', 'user4', '$2a$10$JtWbThTzQhvXxFL90AdhoePJAx6xG4eUJkdBgrMGHhXcEWK49poDW', 1, '2345671004', 'user4@example.com', 1, '2025-05-31 18:52:48', '2025-05-31 18:52:48', 1),
(5, 'Employee 5', 'user5', '$2a$10$JtWbThTzQhvXxFL90AdhoePJAx6xG4eUJkdBgrMGHhXcEWK49poDW', 2, '2345671005', 'user5@example.com', 1, '2025-05-31 18:52:48', '2025-05-31 18:52:48', 1),
(6, 'Employee 6', 'user6', '$2a$10$JtWbThTzQhvXxFL90AdhoePJAx6xG4eUJkdBgrMGHhXcEWK49poDW', 1, '2345671006', 'user6@example.com', 1, '2025-05-31 18:52:48', '2025-05-31 18:52:48', 1),
(7, 'Employee 7', 'user7', '$2a$10$JtWbThTzQhvXxFL90AdhoePJAx6xG4eUJkdBgrMGHhXcEWK49poDW', 2, '2345671007', 'user7@example.com', 1, '2025-05-31 18:52:48', '2025-05-31 18:52:48', 1),
(8, 'Employee 8', 'user8', '$2a$10$JtWbThTzQhvXxFL90AdhoePJAx6xG4eUJkdBgrMGHhXcEWK49poDW', 1, '2345671008', 'user8@example.com', 1, '2025-05-31 18:52:48', '2025-05-31 18:52:48', 1),
(9, 'Employee 9', 'user9', '$2a$10$JtWbThTzQhvXxFL90AdhoePJAx6xG4eUJkdBgrMGHhXcEWK49poDW', 2, '2345671009', 'user9@example.com', 1, '2025-05-31 18:52:48', '2025-05-31 18:52:48', 1),
(10, 'Employee 10', 'user10', '$2a$10$JtWbThTzQhvXxFL90AdhoePJAx6xG4eUJkdBgrMGHhXcEWK49poDW', 1, '2345671010', 'user10@example.com', 1, '2025-05-31 18:52:48', '2025-05-31 18:52:48', 1),
(11, 'Employee 11', 'user11', '$2a$10$JtWbThTzQhvXxFL90AdhoePJAx6xG4eUJkdBgrMGHhXcEWK49poDW', 2, '2345671011', 'user11@example.com', 1, '2025-05-31 18:52:48', '2025-05-31 18:52:48', 1),
(12, 'Employee 12', 'user12', '$2a$10$JtWbThTzQhvXxFL90AdhoePJAx6xG4eUJkdBgrMGHhXcEWK49poDW', 1, '2345671012', 'user12@example.com', 1, '2025-05-31 18:52:48', '2025-05-31 18:52:48', 1),
(13, 'Employee 13', 'user13', '$2a$10$JtWbThTzQhvXxFL90AdhoePJAx6xG4eUJkdBgrMGHhXcEWK49poDW', 2, '2345671013', 'user13@example.com', 1, '2025-05-31 18:52:48', '2025-05-31 18:52:48', 1),
(14, 'Employee 14', 'user14', '$2a$10$JtWbThTzQhvXxFL90AdhoePJAx6xG4eUJkdBgrMGHhXcEWK49poDW', 1, '2345671014', 'user14@example.com', 1, '2025-05-31 18:52:48', '2025-05-31 18:52:48', 1),
(15, 'Employee 15', 'user15', '$2a$10$JtWbThTzQhvXxFL90AdhoePJAx6xG4eUJkdBgrMGHhXcEWK49poDW', 2, '2345671015', 'user15@example.com', 1, '2025-05-31 18:52:48', '2025-05-31 18:52:48', 1),
(16, 'Employee 16', 'user16', '$2a$10$JtWbThTzQhvXxFL90AdhoePJAx6xG4eUJkdBgrMGHhXcEWK49poDW', 1, '2345671016', 'user16@example.com', 1, '2025-05-31 18:52:48', '2025-05-31 18:52:48', 1),
(17, 'Employee 17', 'user17', '$2a$10$JtWbThTzQhvXxFL90AdhoePJAx6xG4eUJkdBgrMGHhXcEWK49poDW', 2, '2345671017', 'user17@example.com', 1, '2025-05-31 18:52:48', '2025-05-31 18:52:48', 1),
(18, 'Employee 18', 'user18', '$2a$10$JtWbThTzQhvXxFL90AdhoePJAx6xG4eUJkdBgrMGHhXcEWK49poDW', 1, '2345671018', 'user18@example.com', 1, '2025-05-31 18:52:48', '2025-05-31 18:52:48', 1),
(19, 'Employee 19', 'user19', '$2a$10$JtWbThTzQhvXxFL90AdhoePJAx6xG4eUJkdBgrMGHhXcEWK49poDW', 2, '2345671019', 'user19@example.com', 1, '2025-05-31 18:52:48', '2025-05-31 18:52:48', 1),
(20, 'Employee 20', 'user20', '$2a$10$JtWbThTzQhvXxFL90AdhoePJAx6xG4eUJkdBgrMGHhXcEWK49poDW', 1, '2345671020', 'user20@example.com', 1, '2025-05-31 18:52:48', '2025-05-31 18:52:48', 1),
(21, 'Employee 21', 'user21', '$2a$10$JtWbThTzQhvXxFL90AdhoePJAx6xG4eUJkdBgrMGHhXcEWK49poDW', 2, '2345671021', 'user21@example.com', 1, '2025-05-31 18:52:48', '2025-05-31 18:52:48', 1),
(22, 'Employee 22', 'user22', '$2a$10$JtWbThTzQhvXxFL90AdhoePJAx6xG4eUJkdBgrMGHhXcEWK49poDW', 1, '2345671022', 'user22@example.com', 1, '2025-05-31 18:52:48', '2025-05-31 18:52:48', 1),
(23, 'Employee 23', 'user23', '$2a$10$JtWbThTzQhvXxFL90AdhoePJAx6xG4eUJkdBgrMGHhXcEWK49poDW', 2, '2345671023', 'user23@example.com', 1, '2025-05-31 18:52:48', '2025-05-31 18:52:48', 1),
(24, 'Employee 24', 'user24', '$2a$10$JtWbThTzQhvXxFL90AdhoePJAx6xG4eUJkdBgrMGHhXcEWK49poDW', 1, '2345671024', 'user24@example.com', 1, '2025-05-31 18:52:48', '2025-05-31 18:52:48', 1),
(25, 'Employee 25', 'user25', '$2a$10$JtWbThTzQhvXxFL90AdhoePJAx6xG4eUJkdBgrMGHhXcEWK49poDW', 2, '2345671025', 'user25@example.com', 1, '2025-05-31 18:52:48', '2025-05-31 18:52:48', 1),
(26, 'Employee 26', 'user26', '$2a$10$JtWbThTzQhvXxFL90AdhoePJAx6xG4eUJkdBgrMGHhXcEWK49poDW', 1, '2345671026', 'user26@example.com', 1, '2025-05-31 18:52:48', '2025-05-31 18:52:48', 1),
(27, 'Employee 27', 'user27', '$2a$10$JtWbThTzQhvXxFL90AdhoePJAx6xG4eUJkdBgrMGHhXcEWK49poDW', 2, '2345671027', 'user27@example.com', 1, '2025-05-31 18:52:48', '2025-05-31 18:52:48', 1),
(28, 'Employee 28', 'user28', '$2a$10$JtWbThTzQhvXxFL90AdhoePJAx6xG4eUJkdBgrMGHhXcEWK49poDW', 1, '2345671028', 'user28@example.com', 1, '2025-05-31 18:52:48', '2025-05-31 18:52:48', 1),
(29, 'Employee 29', 'user29', '$2a$10$JtWbThTzQhvXxFL90AdhoePJAx6xG4eUJkdBgrMGHhXcEWK49poDW', 2, '2345671029', 'user29@example.com', 1, '2025-05-31 18:52:48', '2025-05-31 18:52:48', 1),
(30, 'Employee 30', 'user30', '$2a$10$JtWbThTzQhvXxFL90AdhoePJAx6xG4eUJkdBgrMGHhXcEWK49poDW', 1, '2345671030', 'user30@example.com', 1, '2025-05-31 18:52:48', '2025-05-31 18:52:48', 1),
(31, 'Employee 31', 'user31', '$2a$10$JtWbThTzQhvXxFL90AdhoePJAx6xG4eUJkdBgrMGHhXcEWK49poDW', 2, '2345671031', 'user31@example.com', 1, '2025-05-31 18:52:48', '2025-05-31 18:52:48', 1),
(32, 'Employee 32', 'user32', '$2a$10$JtWbThTzQhvXxFL90AdhoePJAx6xG4eUJkdBgrMGHhXcEWK49poDW', 1, '2345671032', 'user32@example.com', 1, '2025-05-31 18:52:48', '2025-05-31 18:52:48', 1),
(33, 'Employee 33', 'user33', '$2a$10$JtWbThTzQhvXxFL90AdhoePJAx6xG4eUJkdBgrMGHhXcEWK49poDW', 2, '2345671033', 'user33@example.com', 1, '2025-05-31 18:52:48', '2025-05-31 18:52:48', 1),
(34, 'Employee 34', 'user34', '$2a$10$JtWbThTzQhvXxFL90AdhoePJAx6xG4eUJkdBgrMGHhXcEWK49poDW', 1, '2345671034', 'user34@example.com', 1, '2025-05-31 18:52:48', '2025-05-31 18:52:48', 1),
(35, 'Employee 35', 'user35', '$2a$10$JtWbThTzQhvXxFL90AdhoePJAx6xG4eUJkdBgrMGHhXcEWK49poDW', 2, '2345671035', 'user35@example.com', 1, '2025-05-31 18:52:48', '2025-05-31 18:52:48', 1),
(36, 'Employee 36', 'user36', '$2a$10$JtWbThTzQhvXxFL90AdhoePJAx6xG4eUJkdBgrMGHhXcEWK49poDW', 1, '2345671036', 'user36@example.com', 1, '2025-05-31 18:52:48', '2025-05-31 18:52:48', 1),
(37, 'Employee 37', 'user37', '$2a$10$JtWbThTzQhvXxFL90AdhoePJAx6xG4eUJkdBgrMGHhXcEWK49poDW', 2, '2345671037', 'user37@example.com', 1, '2025-05-31 18:52:48', '2025-05-31 18:52:48', 1),
(38, 'Employee 38', 'user38', '$2a$10$JtWbThTzQhvXxFL90AdhoePJAx6xG4eUJkdBgrMGHhXcEWK49poDW', 1, '2345671038', 'user38@example.com', 1, '2025-05-31 18:52:48', '2025-05-31 18:52:48', 1),
(39, 'Employee 39', 'user39', '$2a$10$JtWbThTzQhvXxFL90AdhoePJAx6xG4eUJkdBgrMGHhXcEWK49poDW', 2, '2345671039', 'user39@example.com', 1, '2025-05-31 18:52:48', '2025-05-31 18:52:48', 1),
(40, 'Employee 40', 'user40', '$2a$10$JtWbThTzQhvXxFL90AdhoePJAx6xG4eUJkdBgrMGHhXcEWK49poDW', 1, '2345671040', 'user40@example.com', 1, '2025-05-31 18:52:48', '2025-05-31 18:52:48', 1),
(41, 'Employee 41', 'user41', '$2a$10$JtWbThTzQhvXxFL90AdhoePJAx6xG4eUJkdBgrMGHhXcEWK49poDW', 2, '2345671041', 'user41@example.com', 1, '2025-05-31 18:52:48', '2025-05-31 18:52:48', 1),
(42, 'Employee 42', 'user42', '$2a$10$JtWbThTzQhvXxFL90AdhoePJAx6xG4eUJkdBgrMGHhXcEWK49poDW', 1, '2345671042', 'user42@example.com', 1, '2025-05-31 18:52:48', '2025-05-31 18:52:48', 1),
(43, 'Employee 43', 'user43', '$2a$10$JtWbThTzQhvXxFL90AdhoePJAx6xG4eUJkdBgrMGHhXcEWK49poDW', 2, '2345671043', 'user43@example.com', 1, '2025-05-31 18:52:48', '2025-05-31 18:52:48', 1),
(44, 'Employee 44', 'user44', '$2a$10$JtWbThTzQhvXxFL90AdhoePJAx6xG4eUJkdBgrMGHhXcEWK49poDW', 1, '2345671044', 'user44@example.com', 1, '2025-05-31 18:52:48', '2025-05-31 18:52:48', 1),
(45, 'Employee 45', 'user45', '$2a$10$JtWbThTzQhvXxFL90AdhoePJAx6xG4eUJkdBgrMGHhXcEWK49poDW', 2, '2345671045', 'user45@example.com', 1, '2025-05-31 18:52:48', '2025-05-31 18:52:48', 1),
(46, 'Employee 46', 'user46', '$2a$10$JtWbThTzQhvXxFL90AdhoePJAx6xG4eUJkdBgrMGHhXcEWK49poDW', 1, '2345671046', 'user46@example.com', 1, '2025-05-31 18:52:48', '2025-05-31 18:52:48', 1),
(47, 'Employee 47', 'user47', '$2a$10$JtWbThTzQhvXxFL90AdhoePJAx6xG4eUJkdBgrMGHhXcEWK49poDW', 2, '2345671047', 'user47@example.com', 1, '2025-05-31 18:52:48', '2025-05-31 18:52:48', 1),
(48, 'Employee 48', 'user48', '$2a$10$JtWbThTzQhvXxFL90AdhoePJAx6xG4eUJkdBgrMGHhXcEWK49poDW', 1, '2345671048', 'user48@example.com', 1, '2025-05-31 18:52:48', '2025-05-31 18:52:48', 1),
(49, 'Employee 49', 'user49', '$2a$10$JtWbThTzQhvXxFL90AdhoePJAx6xG4eUJkdBgrMGHhXcEWK49poDW', 2, '2345671049', 'user49@example.com', 1, '2025-05-31 18:52:48', '2025-05-31 18:52:48', 1),
(50, 'Employee 50', 'user50', '$2a$10$JtWbThTzQhvXxFL90AdhoePJAx6xG4eUJkdBgrMGHhXcEWK49poDW', 1, '2345671050', 'user50@example.com', 1, '2025-05-31 18:52:48', '2025-05-31 18:52:48', 1),
(51, 'Employee 51', 'user51', '$2a$10$JtWbThTzQhvXxFL90AdhoePJAx6xG4eUJkdBgrMGHhXcEWK49poDW', 2, '2345671051', 'user51@example.com', 1, '2025-05-31 18:52:48', '2025-05-31 18:52:48', 1),
(52, 'Employee 52', 'user52', '$2a$10$JtWbThTzQhvXxFL90AdhoePJAx6xG4eUJkdBgrMGHhXcEWK49poDW', 1, '2345671052', 'user52@example.com', 1, '2025-05-31 18:52:48', '2025-05-31 18:52:48', 1),
(53, 'Employee 53', 'user53', '$2a$10$JtWbThTzQhvXxFL90AdhoePJAx6xG4eUJkdBgrMGHhXcEWK49poDW', 2, '2345671053', 'user53@example.com', 1, '2025-05-31 18:52:48', '2025-05-31 18:52:48', 1),
(54, 'Employee 54', 'user54', '$2a$10$JtWbThTzQhvXxFL90AdhoePJAx6xG4eUJkdBgrMGHhXcEWK49poDW', 1, '2345671054', 'user54@example.com', 1, '2025-05-31 18:52:48', '2025-05-31 18:52:48', 1),
(55, 'Employee 55', 'user55', '$2a$10$JtWbThTzQhvXxFL90AdhoePJAx6xG4eUJkdBgrMGHhXcEWK49poDW', 2, '2345671055', 'user55@example.com', 1, '2025-05-31 18:52:48', '2025-05-31 18:52:48', 1),
(56, 'Employee 56', 'user56', '$2a$10$JtWbThTzQhvXxFL90AdhoePJAx6xG4eUJkdBgrMGHhXcEWK49poDW', 1, '2345671056', 'user56@example.com', 1, '2025-05-31 18:52:48', '2025-05-31 18:52:48', 1),
(57, 'Employee 57', 'user57', '$2a$10$JtWbThTzQhvXxFL90AdhoePJAx6xG4eUJkdBgrMGHhXcEWK49poDW', 2, '2345671057', 'user57@example.com', 1, '2025-05-31 18:52:48', '2025-05-31 18:52:48', 1),
(58, 'Employee 58', 'user58', '$2a$10$JtWbThTzQhvXxFL90AdhoePJAx6xG4eUJkdBgrMGHhXcEWK49poDW', 1, '2345671058', 'user58@example.com', 1, '2025-05-31 18:52:48', '2025-05-31 18:52:48', 1),
(59, 'Employee 59', 'user59', '$2a$10$JtWbThTzQhvXxFL90AdhoePJAx6xG4eUJkdBgrMGHhXcEWK49poDW', 2, '2345671059', 'user59@example.com', 1, '2025-05-31 18:52:48', '2025-05-31 18:52:48', 1),
(60, 'Employee 60', 'user60', '$2a$10$JtWbThTzQhvXxFL90AdhoePJAx6xG4eUJkdBgrMGHhXcEWK49poDW', 1, '2345671060', 'user60@example.com', 1, '2025-05-31 18:52:48', '2025-05-31 18:52:48', 1),
(61, 'Employee 61', 'user61', '$2a$10$JtWbThTzQhvXxFL90AdhoePJAx6xG4eUJkdBgrMGHhXcEWK49poDW', 2, '2345671061', 'user61@example.com', 1, '2025-05-31 18:52:48', '2025-05-31 18:52:48', 1),
(62, 'Employee 62', 'user62', '$2a$10$JtWbThTzQhvXxFL90AdhoePJAx6xG4eUJkdBgrMGHhXcEWK49poDW', 1, '2345671062', 'user62@example.com', 1, '2025-05-31 18:52:48', '2025-05-31 18:52:48', 1),
(63, 'Employee 63', 'user63', '$2a$10$JtWbThTzQhvXxFL90AdhoePJAx6xG4eUJkdBgrMGHhXcEWK49poDW', 2, '2345671063', 'user63@example.com', 1, '2025-05-31 18:52:48', '2025-05-31 18:52:48', 1),
(64, 'Employee 64', 'user64', '$2a$10$JtWbThTzQhvXxFL90AdhoePJAx6xG4eUJkdBgrMGHhXcEWK49poDW', 1, '2345671064', 'user64@example.com', 1, '2025-05-31 18:52:48', '2025-05-31 18:52:48', 1),
(65, 'Employee 65', 'user65', '$2a$10$JtWbThTzQhvXxFL90AdhoePJAx6xG4eUJkdBgrMGHhXcEWK49poDW', 2, '2345671065', 'user65@example.com', 1, '2025-05-31 18:52:48', '2025-05-31 18:52:48', 1),
(66, 'Employee 66', 'user66', '$2a$10$JtWbThTzQhvXxFL90AdhoePJAx6xG4eUJkdBgrMGHhXcEWK49poDW', 1, '2345671066', 'user66@example.com', 1, '2025-05-31 18:52:48', '2025-05-31 18:52:48', 1),
(67, 'Employee 67', 'user67', '$2a$10$JtWbThTzQhvXxFL90AdhoePJAx6xG4eUJkdBgrMGHhXcEWK49poDW', 2, '2345671067', 'user67@example.com', 1, '2025-05-31 18:52:48', '2025-05-31 18:52:48', 1),
(68, 'Employee 68', 'user68', '$2a$10$JtWbThTzQhvXxFL90AdhoePJAx6xG4eUJkdBgrMGHhXcEWK49poDW', 1, '2345671068', 'user68@example.com', 1, '2025-05-31 18:52:48', '2025-05-31 18:52:48', 1),
(69, 'Employee 69', 'user69', '$2a$10$JtWbThTzQhvXxFL90AdhoePJAx6xG4eUJkdBgrMGHhXcEWK49poDW', 2, '2345671069', 'user69@example.com', 1, '2025-05-31 18:52:48', '2025-05-31 18:52:48', 1),
(70, 'Employee 70', 'user70', '$2a$10$JtWbThTzQhvXxFL90AdhoePJAx6xG4eUJkdBgrMGHhXcEWK49poDW', 1, '2345671070', 'user70@example.com', 1, '2025-05-31 18:52:48', '2025-05-31 18:52:48', 1),
(71, 'Employee 71', 'user71', '$2a$10$JtWbThTzQhvXxFL90AdhoePJAx6xG4eUJkdBgrMGHhXcEWK49poDW', 2, '2345671071', 'user71@example.com', 1, '2025-05-31 18:52:48', '2025-05-31 18:52:48', 1),
(72, 'Employee 72', 'user72', '$2a$10$JtWbThTzQhvXxFL90AdhoePJAx6xG4eUJkdBgrMGHhXcEWK49poDW', 1, '2345671072', 'user72@example.com', 1, '2025-05-31 18:52:48', '2025-05-31 18:52:48', 1),
(73, 'Employee 73', 'user73', '$2a$10$JtWbThTzQhvXxFL90AdhoePJAx6xG4eUJkdBgrMGHhXcEWK49poDW', 2, '2345671073', 'user73@example.com', 1, '2025-05-31 18:52:48', '2025-05-31 18:52:48', 1),
(74, 'Employee 74', 'user74', '$2a$10$JtWbThTzQhvXxFL90AdhoePJAx6xG4eUJkdBgrMGHhXcEWK49poDW', 1, '2345671074', 'user74@example.com', 1, '2025-05-31 18:52:48', '2025-05-31 18:52:48', 1),
(75, 'Employee 75', 'user75', '$2a$10$JtWbThTzQhvXxFL90AdhoePJAx6xG4eUJkdBgrMGHhXcEWK49poDW', 2, '2345671075', 'user75@example.com', 1, '2025-05-31 18:52:48', '2025-05-31 18:52:48', 1),
(76, 'Employee 76', 'user76', '$2a$10$JtWbThTzQhvXxFL90AdhoePJAx6xG4eUJkdBgrMGHhXcEWK49poDW', 1, '2345671076', 'user76@example.com', 1, '2025-05-31 18:52:48', '2025-05-31 18:52:48', 1),
(77, 'Employee 77', 'user77', '$2a$10$JtWbThTzQhvXxFL90AdhoePJAx6xG4eUJkdBgrMGHhXcEWK49poDW', 2, '2345671077', 'user77@example.com', 1, '2025-05-31 18:52:48', '2025-05-31 18:52:48', 1),
(78, 'Employee 78', 'user78', '$2a$10$JtWbThTzQhvXxFL90AdhoePJAx6xG4eUJkdBgrMGHhXcEWK49poDW', 1, '2345671078', 'user78@example.com', 1, '2025-05-31 18:52:48', '2025-05-31 18:52:48', 1),
(79, 'Employee 79', 'user79', '$2a$10$JtWbThTzQhvXxFL90AdhoePJAx6xG4eUJkdBgrMGHhXcEWK49poDW', 2, '2345671079', 'user79@example.com', 1, '2025-05-31 18:52:48', '2025-05-31 18:52:48', 1),
(80, 'Employee 80', 'user80', '$2a$10$JtWbThTzQhvXxFL90AdhoePJAx6xG4eUJkdBgrMGHhXcEWK49poDW', 1, '2345671080', 'user80@example.com', 1, '2025-05-31 18:52:48', '2025-05-31 18:52:48', 1),
(81, 'Employee 81', 'user81', '$2a$10$JtWbThTzQhvXxFL90AdhoePJAx6xG4eUJkdBgrMGHhXcEWK49poDW', 2, '2345671081', 'user81@example.com', 1, '2025-05-31 18:52:48', '2025-05-31 18:52:48', 1),
(82, 'Employee 82', 'user82', '$2a$10$JtWbThTzQhvXxFL90AdhoePJAx6xG4eUJkdBgrMGHhXcEWK49poDW', 1, '2345671082', 'user82@example.com', 1, '2025-05-31 18:52:48', '2025-05-31 18:52:48', 1),
(83, 'Employee 83', 'user83', '$2a$10$JtWbThTzQhvXxFL90AdhoePJAx6xG4eUJkdBgrMGHhXcEWK49poDW', 2, '2345671083', 'user83@example.com', 1, '2025-05-31 18:52:48', '2025-05-31 18:52:48', 1),
(84, 'Employee 84', 'user84', '$2a$10$JtWbThTzQhvXxFL90AdhoePJAx6xG4eUJkdBgrMGHhXcEWK49poDW', 1, '2345671084', 'user84@example.com', 1, '2025-05-31 18:52:48', '2025-05-31 18:52:48', 1),
(85, 'Employee 85', 'user85', '$2a$10$JtWbThTzQhvXxFL90AdhoePJAx6xG4eUJkdBgrMGHhXcEWK49poDW', 2, '2345671085', 'user85@example.com', 1, '2025-05-31 18:52:48', '2025-05-31 18:52:48', 1),
(86, 'Employee 86', 'user86', '$2a$10$JtWbThTzQhvXxFL90AdhoePJAx6xG4eUJkdBgrMGHhXcEWK49poDW', 1, '2345671086', 'user86@example.com', 1, '2025-05-31 18:52:48', '2025-05-31 18:52:48', 1),
(87, 'Employee 87', 'user87', '$2a$10$JtWbThTzQhvXxFL90AdhoePJAx6xG4eUJkdBgrMGHhXcEWK49poDW', 2, '2345671087', 'user87@example.com', 1, '2025-05-31 18:52:48', '2025-05-31 18:52:48', 1),
(88, 'Employee 88', 'user88', '$2a$10$JtWbThTzQhvXxFL90AdhoePJAx6xG4eUJkdBgrMGHhXcEWK49poDW', 1, '2345671088', 'user88@example.com', 1, '2025-05-31 18:52:48', '2025-05-31 18:52:48', 1),
(89, 'Employee 89', 'user89', '$2a$10$JtWbThTzQhvXxFL90AdhoePJAx6xG4eUJkdBgrMGHhXcEWK49poDW', 2, '2345671089', 'user89@example.com', 1, '2025-05-31 18:52:48', '2025-05-31 18:52:48', 1),
(90, 'Employee 90', 'user90', '$2a$10$JtWbThTzQhvXxFL90AdhoePJAx6xG4eUJkdBgrMGHhXcEWK49poDW', 1, '2345671090', 'user90@example.com', 1, '2025-05-31 18:52:48', '2025-05-31 18:52:48', 1),
(91, 'Employee 91', 'user91', '$2a$10$JtWbThTzQhvXxFL90AdhoePJAx6xG4eUJkdBgrMGHhXcEWK49poDW', 2, '2345671091', 'user91@example.com', 1, '2025-05-31 18:52:48', '2025-05-31 18:52:48', 1),
(92, 'Employee 92', 'user92', '$2a$10$JtWbThTzQhvXxFL90AdhoePJAx6xG4eUJkdBgrMGHhXcEWK49poDW', 1, '2345671092', 'user92@example.com', 1, '2025-05-31 18:52:48', '2025-05-31 18:52:48', 1),
(93, 'Employee 93', 'user93', '$2a$10$JtWbThTzQhvXxFL90AdhoePJAx6xG4eUJkdBgrMGHhXcEWK49poDW', 2, '2345671093', 'user93@example.com', 1, '2025-05-31 18:52:48', '2025-05-31 18:52:48', 1),
(94, 'Employee 94', 'user94', '$2a$10$JtWbThTzQhvXxFL90AdhoePJAx6xG4eUJkdBgrMGHhXcEWK49poDW', 1, '2345671094', 'user94@example.com', 1, '2025-05-31 18:52:48', '2025-05-31 18:52:48', 1),
(95, 'Employee 95', 'user95', '$2a$10$JtWbThTzQhvXxFL90AdhoePJAx6xG4eUJkdBgrMGHhXcEWK49poDW', 2, '2345671095', 'user95@example.com', 1, '2025-05-31 18:52:48', '2025-05-31 18:52:48', 1),
(96, 'Employee 96', 'user96', '$2a$10$JtWbThTzQhvXxFL90AdhoePJAx6xG4eUJkdBgrMGHhXcEWK49poDW', 1, '2345671096', 'user96@example.com', 1, '2025-05-31 18:52:48', '2025-05-31 18:52:48', 1),
(97, 'Employee 97', 'user97', '$2a$10$JtWbThTzQhvXxFL90AdhoePJAx6xG4eUJkdBgrMGHhXcEWK49poDW', 2, '2345671097', 'user97@example.com', 1, '2025-05-31 18:52:48', '2025-05-31 18:52:48', 1),
(98, 'Employee 98', 'user98', '$2a$10$JtWbThTzQhvXxFL90AdhoePJAx6xG4eUJkdBgrMGHhXcEWK49poDW', 1, '2345671098', 'user98@example.com', 1, '2025-05-31 18:52:48', '2025-05-31 18:52:48', 1),
(99, 'Employee 99', 'user99', '$2a$10$JtWbThTzQhvXxFL90AdhoePJAx6xG4eUJkdBgrMGHhXcEWK49poDW', 2, '2345671099', 'user99@example.com', 1, '2025-05-31 18:52:48', '2025-05-31 18:52:48', 1),
(100, 'Employee 100', 'user100', '$2a$10$JtWbThTzQhvXxFL90AdhoePJAx6xG4eUJkdBgrMGHhXcEWK49poDW', 1, '2345671100', 'user100@example.com', 1, '2025-05-31 18:52:48', '2025-05-31 18:52:48', 1),
(101, 'Employee 101', 'user101', '$2a$10$JtWbThTzQhvXxFL90AdhoePJAx6xG4eUJkdBgrMGHhXcEWK49poDW', 2, '2345671101', 'user101@example.com', 1, '2025-05-31 18:52:48', '2025-05-31 18:52:48', 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `employee_permissions`
--

CREATE TABLE `employee_permissions` (
  `employee_id` bigint(20) NOT NULL,
  `permission_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

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
-- Cấu trúc bảng cho bảng `permissions`
--

CREATE TABLE `permissions` (
  `id` bigint(20) NOT NULL,
  `name` varchar(100) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `version` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `permissions`
--

INSERT INTO `permissions` (`id`, `name`, `description`, `created_at`, `updated_at`, `version`) VALUES
(1, 'view_products', 'Xem danh sách sản phẩm', NULL, NULL, NULL),
(2, 'edit_products', 'Sửa thông tin sản phẩm', NULL, NULL, NULL),
(3, 'delete_products', 'Xóa sản phẩm', NULL, NULL, NULL),
(4, 'view_invoices', 'Xem hóa đơn', NULL, NULL, NULL),
(5, 'create_invoices', 'Tạo hóa đơn', NULL, NULL, NULL),
(6, 'delete_invoices', 'Xóa hóa đơn', NULL, NULL, NULL),
(7, 'view_employees', 'Xem danh sách nhân viên', NULL, NULL, NULL),
(8, 'edit_employees', 'Sửa thông tin nhân viên', NULL, NULL, NULL),
(9, 'delete_employees', 'Xóa nhân viên', NULL, NULL, NULL),
(10, 'view_audit_logs', 'Xem nhật ký audit', NULL, NULL, NULL);

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
(1, 'BC123456789', 'Cà phê đen', 25000.00, 100, '\\images\\product\\coffe.webp', 2, 1, '2025-05-23 17:41:02', '2025-06-01 11:13:06', 1),
(2, 'BC123456788', 'Trà sữa thái xanh', 15000.00, 80, '\\images\\product\\thaixanh.png', 1, 1, '2025-05-23 17:41:02', '2025-06-01 11:24:19', 1),
(3, 'BC123456787', 'Sinh tố bơ', 35000.00, 50, '\\images\\product\\sinhtobo.png', 2, 0, '2025-05-23 17:41:02', '2025-06-01 11:14:40', 1),
(4, 'BC123456786', 'Bim Bim Oishi', 8000.00, 200, '\\images\\product\\bimbim.png', 3, 0, '2025-05-23 17:41:02', '2025-06-01 11:24:21', 1),
(5, 'BC2025000005', 'Bánh Mì', 25000.00, 55, '\\images\\product\\banhmi.png', 3, 1, '2025-05-30 12:00:00', '2025-06-01 11:24:26', 1),
(6, 'BC2025000006', 'Phở Bò', 35000.00, 56, '\\images\\product\\phobo.png', 1, 0, '2025-05-30 12:00:00', '2025-06-01 11:24:30', 1),
(7, 'BC2025000007', 'Bún Chả', 35000.00, 57, '\\images\\product\\buncha.png', 2, 1, '2025-05-30 12:00:00', '2025-06-01 11:24:35', 1),
(8, 'BC2025000008', 'Cà Phê Sữa', 35000.00, 58, '\\images\\product\\caphesua.png', 3, 0, '2025-05-30 12:00:00', '2025-06-01 11:24:37', 1),
(9, 'BC2025000009', 'Trà Đào', 15000.00, 59, '\\images\\product\\tradao.png', 1, 1, '2025-05-30 12:00:00', '2025-06-01 11:24:42', 1),
(10, 'BC2025000010', 'Nước Cam', 35000.00, 60, '\\images\\product\\nuoccam.png', 2, 0, '2025-05-30 12:00:00', '2025-06-01 11:24:45', 1),
(11, 'BC2025000011', 'Bia Hà Nội', 10000.00, 61, '\\images\\product\\biahanoi.webp', 3, 1, '2025-05-30 12:00:00', '2025-06-01 11:24:50', 1),
(12, 'BC2025000012', 'Cơm Tấm', 35000.00, 62, '\\images\\product\\comtam.png', 1, 0, '2025-05-30 12:00:00', '2025-06-01 11:24:53', 1),
(13, 'BC2025000013', 'Bún Bò Huế', 35000.00, 63, '\\images\\product\\bunbohue.png', 2, 1, '2025-05-30 12:00:00', '2025-06-01 11:24:55', 1);

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
(1, 'Nhân viên', '2025-05-22 06:52:01', '2025-06-01 12:36:14', 0),
(2, 'Quản trị viên', '2025-05-22 06:52:01', '2025-06-01 12:36:18', 0);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `role_permissions`
--

CREATE TABLE `role_permissions` (
  `role_id` bigint(20) NOT NULL,
  `permission_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `role_permissions`
--

INSERT INTO `role_permissions` (`role_id`, `permission_id`) VALUES
(1, 1),
(1, 2),
(1, 3),
(1, 4),
(1, 5),
(1, 6),
(1, 7),
(1, 8),
(1, 9),
(1, 10),
(2, 1),
(2, 2),
(2, 4),
(2, 5),
(2, 7),
(2, 8),
(2, 10);

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
-- Chỉ mục cho bảng `employee_permissions`
--
ALTER TABLE `employee_permissions`
  ADD PRIMARY KEY (`employee_id`,`permission_id`),
  ADD KEY `permission_id` (`permission_id`);

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
-- Chỉ mục cho bảng `permissions`
--
ALTER TABLE `permissions`
  ADD PRIMARY KEY (`id`);

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
-- Chỉ mục cho bảng `role_permissions`
--
ALTER TABLE `role_permissions`
  ADD PRIMARY KEY (`role_id`,`permission_id`),
  ADD KEY `permission_id` (`permission_id`);

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
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=104;

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
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=102;

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
-- AUTO_INCREMENT cho bảng `permissions`
--
ALTER TABLE `permissions`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

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
-- Các ràng buộc cho bảng `employee_permissions`
--
ALTER TABLE `employee_permissions`
  ADD CONSTRAINT `employee_permissions_ibfk_1` FOREIGN KEY (`employee_id`) REFERENCES `employees` (`id`),
  ADD CONSTRAINT `employee_permissions_ibfk_2` FOREIGN KEY (`permission_id`) REFERENCES `permissions` (`id`);

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
-- Các ràng buộc cho bảng `role_permissions`
--
ALTER TABLE `role_permissions`
  ADD CONSTRAINT `role_permissions_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`),
  ADD CONSTRAINT `role_permissions_ibfk_2` FOREIGN KEY (`permission_id`) REFERENCES `permissions` (`id`);

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
