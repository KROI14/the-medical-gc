-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Oct 05, 2020 at 03:55 AM
-- Server version: 10.4.13-MariaDB
-- PHP Version: 7.4.7

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `themedicalgcs`
--

-- --------------------------------------------------------

--
-- Table structure for table `bank`
--

CREATE TABLE `bank` (
  `AccNum` varchar(10) NOT NULL,
  `BankName` varchar(100) NOT NULL,
  `Firstname` varchar(100) NOT NULL,
  `Lastname` varchar(100) NOT NULL,
  `Money` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `bank`
--

INSERT INTO `bank` (`AccNum`, `BankName`, `Firstname`, `Lastname`, `Money`) VALUES
('14186', 'BDO Unibank INC', 'Michael', 'Jordan', 1043),
('75405', 'Bank of the Phil Islands', 'Magic', 'Johnson', 35000),
('77805', 'Land Bank of The Philippines', 'Larry', 'Bird', 39696),
('85414', 'Phil National Bank', 'Dennis', 'Rodman', 50000),
('93129', 'Metropolitan Bank and TCO', 'Wilt', 'Chamberlain', 45000);

-- --------------------------------------------------------

--
-- Table structure for table `bill`
--

CREATE TABLE `bill` (
  `BillID` varchar(10) NOT NULL,
  `PatientID` varchar(10) NOT NULL,
  `Illness` varchar(100) NOT NULL,
  `MedCondition` varchar(100) NOT NULL,
  `Xray` varchar(3) NOT NULL,
  `CTScan` varchar(3) NOT NULL,
  `CovidTest` varchar(3) NOT NULL,
  `Room` varchar(100) NOT NULL,
  `Days` varchar(3) NOT NULL,
  `Tax` varchar(100) NOT NULL,
  `TotalPrice` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `bill`
--

INSERT INTO `bill` (`BillID`, `PatientID`, `Illness`, `MedCondition`, `Xray`, `CTScan`, `CovidTest`, `Room`, `Days`, `Tax`, `TotalPrice`) VALUES
('BIL-0000', 'PAT-0000', 'N/A', 'N/A', '0', '0', '0', 'N/A', '0', '0', '10'),
('BIL-0001', 'PAT-0001', 'Dislocated Elbow', 'Moderate', '2', '1', '1', 'Ward-₱500/day', '3', '660.0', '6160.0'),
('BIL-0002', 'PAT-0002', 'Ankle sprain', 'Severe', '2', '1', '1', 'ICU-₱1500/day', '5', '1380.0', '12880.0'),
('BIL-0003', 'PAT-0003', 'Ankle Sprain', 'Severe', '2', '1', '1', 'ICU-₱1500/day', '5', '1380.0', '12880.0'),
('BIL-0004', 'PAT-0004', 'Ankle Sprain', 'Severe', '2', '1', '1', 'ICU-₱1500/day', '5', '1380.0', '12880.0');

-- --------------------------------------------------------

--
-- Table structure for table `insurance`
--

CREATE TABLE `insurance` (
  `InsuranceNum` varchar(10) NOT NULL,
  `Name` varchar(100) NOT NULL,
  `Discount` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `insurance`
--

INSERT INTO `insurance` (`InsuranceNum`, `Name`, `Discount`) VALUES
('270', 'Sun life', '20'),
('694', 'Maxicare', '40'),
('993', 'Medicard', '30');

-- --------------------------------------------------------

--
-- Table structure for table `patient`
--

CREATE TABLE `patient` (
  `PatientID` varchar(10) NOT NULL,
  `Firstname` varchar(100) NOT NULL,
  `Lastname` varchar(100) NOT NULL,
  `Age` varchar(3) NOT NULL,
  `Gender` varchar(10) NOT NULL,
  `Address` varchar(100) NOT NULL,
  `Contact` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `patient`
--

INSERT INTO `patient` (`PatientID`, `Firstname`, `Lastname`, `Age`, `Gender`, `Address`, `Contact`) VALUES
('PAT-0000', 'Pedro', 'Penduko', '90', 'Male', 'Davao', '09285321261'),
('PAT-0001', 'Lebron', 'James', '40', 'Male', 'USA', '0987654321'),
('PAT-0002', 'Scottie', 'Pippen', '40', 'Male', 'Ohio, USA', '0987654321'),
('PAT-0003', 'Scottie', 'Pippen', '40', 'Male', 'Ohio, USA', '0987654321'),
('PAT-0004', 'Scottie', 'Pippen', '40', 'Male', 'Ohio, USA', '0987654321');

-- --------------------------------------------------------

--
-- Table structure for table `payment`
--

CREATE TABLE `payment` (
  `PaymentID` varchar(10) NOT NULL,
  `BankAcc` varchar(10) NOT NULL,
  `BillID` varchar(10) NOT NULL,
  `InsuranceName` varchar(100) NOT NULL,
  `Discount` varchar(100) NOT NULL,
  `AmountPaid` varchar(100) NOT NULL,
  `Balance` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `payment`
--

INSERT INTO `payment` (`PaymentID`, `BankAcc`, `BillID`, `InsuranceName`, `Discount`, `AmountPaid`, `Balance`) VALUES
('PAY-0000', '14186', 'BIL-0000', 'N/A', '0', '', '0'),
('PAY-0001', '14186', 'BIL-0001', 'Medicard', '1848.0', '4312.0', '0.0'),
('PAY-0002', '77805', 'BIL-0002', 'Sun life', '2576.0', '10304.0', '0.0'),
('PAY-0003', '77805', 'BIL-0004', 'Sun life', '2576.0', '10304.0', '0.0');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `bank`
--
ALTER TABLE `bank`
  ADD PRIMARY KEY (`AccNum`);

--
-- Indexes for table `bill`
--
ALTER TABLE `bill`
  ADD PRIMARY KEY (`BillID`),
  ADD KEY `PatientID` (`PatientID`);

--
-- Indexes for table `insurance`
--
ALTER TABLE `insurance`
  ADD PRIMARY KEY (`InsuranceNum`);

--
-- Indexes for table `patient`
--
ALTER TABLE `patient`
  ADD PRIMARY KEY (`PatientID`);

--
-- Indexes for table `payment`
--
ALTER TABLE `payment`
  ADD PRIMARY KEY (`PaymentID`),
  ADD KEY `BankAcc` (`BankAcc`),
  ADD KEY `BillID` (`BillID`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `bill`
--
ALTER TABLE `bill`
  ADD CONSTRAINT `bill_ibfk_1` FOREIGN KEY (`PatientID`) REFERENCES `patient` (`PatientID`);

--
-- Constraints for table `payment`
--
ALTER TABLE `payment`
  ADD CONSTRAINT `payment_ibfk_1` FOREIGN KEY (`BankAcc`) REFERENCES `bank` (`AccNum`),
  ADD CONSTRAINT `payment_ibfk_2` FOREIGN KEY (`BillID`) REFERENCES `bill` (`BillID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
