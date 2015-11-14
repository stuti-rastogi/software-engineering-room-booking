-- phpMyAdmin SQL Dump
-- version 4.4.14
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Nov 14, 2015 at 07:54 PM
-- Server version: 5.6.26
-- PHP Version: 5.6.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `hotel`
--

-- --------------------------------------------------------

--
-- Table structure for table `bookings`
--

CREATE TABLE IF NOT EXISTS `bookings` (
  `AppNo` int(20) unsigned NOT NULL COMMENT 'Application number',
  `RoomNo` varchar(4) CHARACTER SET latin1 DEFAULT NULL COMMENT 'Room no. allotted (R102, C001)',
  `RoomType` tinyint(4) NOT NULL COMMENT 'Type of room (1:deluxe, 2:suite, 3:conference, 4:banquet)',
  `GuestName` varchar(40) CHARACTER SET latin1 NOT NULL COMMENT 'Booked by',
  `GuestID` varchar(5) CHARACTER SET latin1 NOT NULL COMMENT 'Guest''s registration number',
  `Start` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `End` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `Reason` varchar(100) CHARACTER SET latin1 DEFAULT NULL COMMENT 'Mentioned only in case of conference room and banquet',
  `Bill` int(11) NOT NULL DEFAULT '0',
  `Granted` tinyint(4) NOT NULL DEFAULT '1' COMMENT '0 for queue, 1 for granted, 2 for denied',
  `Timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `ReasonDenied` varchar(100) CHARACTER SET latin1 DEFAULT NULL COMMENT 'Mentioned only in case of denied',
  `Email` varchar(40) CHARACTER SET latin1 NOT NULL COMMENT 'Email ID of guest'
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=latin1 COLLATE=latin1_spanish_ci;

-- --------------------------------------------------------

--
-- Table structure for table `rooms`
--

CREATE TABLE IF NOT EXISTS `rooms` (
  `RoomNo` varchar(4) NOT NULL,
  `RoomType` tinyint(4) NOT NULL COMMENT '1:deluxe, 2:suite, 3:conference, 4:banquet',
  `isBooked` tinyint(4) NOT NULL DEFAULT '0' COMMENT '1:yes, 0:no'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `rooms`
--

INSERT INTO `rooms` (`RoomNo`, `RoomType`, `isBooked`) VALUES
('B001', 4, 0),
('C001', 3, 0),
('R101', 1, 0),
('R102', 1, 0),
('R103', 1, 0),
('R104', 1, 0),
('R105', 1, 0),
('R106', 1, 0),
('R107', 1, 0),
('R108', 1, 0),
('R109', 1, 0),
('R110', 1, 0),
('R201', 2, 0),
('R202', 2, 0),
('R203', 2, 0),
('R204', 2, 0),
('R205', 2, 0),
('R206', 2, 0),
('R207', 2, 0),
('R208', 2, 0),
('R209', 2, 0),
('R210', 2, 0);

-- --------------------------------------------------------

--
-- Table structure for table `tariff`
--

CREATE TABLE IF NOT EXISTS `tariff` (
  `RoomType` tinyint(4) NOT NULL COMMENT '1:deluxe, 2:suite, 3:conference, 4:banquet',
  `UnitCost` int(11) NOT NULL COMMENT 'per night for room, per hour for others'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tariff`
--

INSERT INTO `tariff` (`RoomType`, `UnitCost`) VALUES
(1, 5000),
(2, 8000),
(3, 3000),
(4, 4000);

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE IF NOT EXISTS `user` (
  `Name` varchar(40) COLLATE latin1_spanish_ci NOT NULL,
  `Username` varchar(20) COLLATE latin1_spanish_ci NOT NULL COMMENT 'user name for login',
  `ID` varchar(5) COLLATE latin1_spanish_ci NOT NULL,
  `Contact` varchar(40) COLLATE latin1_spanish_ci NOT NULL COMMENT 'Email ID',
  `Password` varchar(10) COLLATE latin1_spanish_ci NOT NULL,
  `IsAdmin` tinyint(1) NOT NULL DEFAULT '0' COMMENT '1 for admin, 0 for user',
  `Amount` int(11) NOT NULL DEFAULT '0' COMMENT 'Amount to be paid by the guest'
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_spanish_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`Name`, `Username`, `ID`, `Contact`, `Password`, `IsAdmin`, `Amount`) VALUES
('Rahul Malhotra', 'rahul', 'b0101', 'stuti.r.rastogi@gmail.com', 'rm0101', 0, 0),
('Rachit Rastogi', 'rrast', 'm0608', 'stuti.r.rastogi@gmail.com', 'rr0608', 0, 1000),
('Stuti Rastogi', 'stuti', 'm0802', 'stuti.r.rastogi@gmail.com', 'sr0802', 0, 4000),
('John Smith', 'johns', 'm1804', 'johnsmith.m0802@gmail.com', 'js221b', 1, 0);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `bookings`
--
ALTER TABLE `bookings`
  ADD PRIMARY KEY (`AppNo`);

--
-- Indexes for table `rooms`
--
ALTER TABLE `rooms`
  ADD PRIMARY KEY (`RoomNo`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD UNIQUE KEY `GuestID` (`ID`,`Contact`),
  ADD UNIQUE KEY `username` (`Username`),
  ADD FULLTEXT KEY `GuestName` (`Name`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `bookings`
--
ALTER TABLE `bookings`
  MODIFY `AppNo` int(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Application number',AUTO_INCREMENT=17;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
