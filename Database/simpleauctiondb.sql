-- phpMyAdmin SQL Dump
-- version 5.0.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th1 09, 2022 lúc 04:54 AM
-- Phiên bản máy phục vụ: 10.4.11-MariaDB
-- Phiên bản PHP: 7.4.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `simpleauctiondb`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `tblauctions`
--

CREATE TABLE `tblauctions` (
  `productName` varchar(255) NOT NULL,
  `userName` varchar(255) NOT NULL,
  `purchasePrice` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `tblauctions`
--

INSERT INTO `tblauctions` (`productName`, `userName`, `purchasePrice`) VALUES
('Trash 1', 'User_1', 10);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `tblproducts`
--

CREATE TABLE `tblproducts` (
  `productName` varchar(255) NOT NULL,
  `startingPrice` int(11) NOT NULL,
  `imageUrl` varchar(255) NOT NULL,
  `soldStatus` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `tblproducts`
--

INSERT INTO `tblproducts` (`productName`, `startingPrice`, `imageUrl`, `soldStatus`) VALUES
('devil fruit gomu gomu no', 999999, 'Gomu.png', 0),
('doraemon magic door', 700, 'CanhCuu.png', 0),
('Doreamon Time Travel Machine', 200, 'MayDu.png', 0),
('Dragon Ball Ball 1 Star', 200, 'DragonBall.png', 0),
('Gryffindor sword', 50, 'KiemHa.png', 0),
('The Elder Cloak', 300, 'AoChoang.png', 0),
('Thousand years game', 1000, 'Yugioh.png', 0),
('Trash 1', 10, 'trash.png', 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `tblusers`
--

CREATE TABLE `tblusers` (
  `userName` varchar(255) NOT NULL,
  `hashPassword` varchar(255) NOT NULL,
  `balance` int(11) NOT NULL,
  `lockStatus` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `tblusers`
--

INSERT INTO `tblusers` (`userName`, `hashPassword`, `balance`, `lockStatus`) VALUES
('User_1', '35cf06c1bd00feae0aaf9e7d187c3b89d26148e61dcb0dd0d71b40fb23b1e667', 500, 0),
('User_2', '12ac54b087466911d8c859b4d3622830910a2e968d7828853611d734d2518453', 50, 0),
('User_3', 'a68e78cf1dd4f3df6e3a43547f3b33fccd3ee5e38b6b189a09afadb831fde4ba', 93699999, 0);

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `tblauctions`
--
ALTER TABLE `tblauctions`
  ADD PRIMARY KEY (`productName`,`userName`),
  ADD KEY `userName` (`userName`);

--
-- Chỉ mục cho bảng `tblproducts`
--
ALTER TABLE `tblproducts`
  ADD PRIMARY KEY (`productName`);

--
-- Chỉ mục cho bảng `tblusers`
--
ALTER TABLE `tblusers`
  ADD PRIMARY KEY (`userName`);

--
-- Các ràng buộc cho các bảng đã đổ
--

--
-- Các ràng buộc cho bảng `tblauctions`
--
ALTER TABLE `tblauctions`
  ADD CONSTRAINT `tblauctions_ibfk_1` FOREIGN KEY (`userName`) REFERENCES `tblusers` (`userName`),
  ADD CONSTRAINT `tblauctions_ibfk_2` FOREIGN KEY (`productName`) REFERENCES `tblproducts` (`productName`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
