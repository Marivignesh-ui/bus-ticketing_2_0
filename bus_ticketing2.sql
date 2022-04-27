-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 27, 2022 at 12:24 PM
-- Server version: 10.4.22-MariaDB
-- PHP Version: 7.4.27

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `bus_ticketing2`
--

-- --------------------------------------------------------

--
-- Table structure for table `blocked_tickets`
--

CREATE TABLE `blocked_tickets` (
  `id` varchar(70) NOT NULL,
  `bus_route_id` varchar(70) NOT NULL,
  `date` date NOT NULL,
  `is_blocked` tinyint(1) NOT NULL DEFAULT 1,
  `seat_id` varchar(70) NOT NULL,
  `seat_no` int(11) NOT NULL,
  `booked_at` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `blocked_tickets`
--

INSERT INTO `blocked_tickets` (`id`, `bus_route_id`, `date`, `is_blocked`, `seat_id`, `seat_no`, `booked_at`) VALUES
('54b3be32-0f65-4307-9828-246cf5f7615a', '364ccf62-edce-4a65-8321-cd2432a0c78b', '2022-04-15', 1, '364ccf62-edce-4a65-8321-cd2432a0c78b-2022-04-15-06', 6, '2022-04-17 10:56:59'),
('a6da811c-9dfe-46b5-bc3c-aa6bf45596f1', '364ccf62-edce-4a65-8321-cd2432a0c78b', '2022-04-15', 1, '364ccf62-edce-4a65-8321-cd2432a0c78b-2022-04-15-04', 4, '2022-04-15 19:08:29'),
('d522499b-2dce-4d36-8fe2-2a719eabae23', '364ccf62-edce-4a65-8321-cd2432a0c78b', '2022-04-15', 1, '364ccf62-edce-4a65-8321-cd2432a0c78b-2022-04-15-01', 1, '2022-04-15 11:27:13');

-- --------------------------------------------------------

--
-- Table structure for table `bus_master`
--

CREATE TABLE `bus_master` (
  `id` varchar(70) NOT NULL,
  `start_terminal` varchar(50) NOT NULL,
  `end_terminal` varchar(50) NOT NULL,
  `registration_no` varchar(20) NOT NULL,
  `travels_name` varchar(30) NOT NULL,
  `bus_type` varchar(45) NOT NULL,
  `total_seats` int(11) NOT NULL,
  `journey_type` varchar(40) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `bus_master`
--

INSERT INTO `bus_master` (`id`, `start_terminal`, `end_terminal`, `registration_no`, `travels_name`, `bus_type`, `total_seats`, `journey_type`) VALUES
('24f8e6cd-4b7c-4d6e-acd5-dffa309cfd81', 'dindigul', 'chennai', 'TN57CZ1720', 'Tesla Travels', 'ultra deluxe', 32, 'Alternative-1'),
('4b59cb97-0816-4e6a-9549-8294f2dbb746', 'chennai', 'dindigul', 'TN57CZ1721', 'Trump Travels', 'ultra deluxe', 32, 'Everyday'),
('dac3ac49-6f33-49f5-b430-d5424fd65a41', 'chennai', 'dindigul', 'TN57CZ1718', 'afasd Travels', 'ultra deluxe', 32, 'Everyday'),
('e171ec00-5996-4e8d-a5df-5c06edcc8902', 'chennai', 'dindigul', 'TN57CZ1711', 'Neruppuda', 'Ultra deluxe', 42, 'Alternative');

-- --------------------------------------------------------

--
-- Table structure for table `bus_routes_history`
--

CREATE TABLE `bus_routes_history` (
  `id` varchar(50) NOT NULL,
  `bus_id` varchar(50) NOT NULL,
  `date_of_journey` date NOT NULL,
  `remaining_tickets` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `bus_routes_history`
--

INSERT INTO `bus_routes_history` (`id`, `bus_id`, `date_of_journey`, `remaining_tickets`) VALUES
('01ad0a60-1246-4eda-b8b9-4d815751702c', 'dac3ac49-6f33-49f5-b430-d5424fd65a41', '2022-04-19', 32),
('21e73b7a-adf3-4b09-a4dd-14324829b46a', 'dac3ac49-6f33-49f5-b430-d5424fd65a41', '2022-04-21', 32),
('28db054d-51ef-4ba4-b010-dd80691e8a2f', 'dac3ac49-6f33-49f5-b430-d5424fd65a41', '2022-04-18', 32),
('364ccf62-edce-4a65-8321-cd2432a0c78b', 'e171ec00-5996-4e8d-a5df-5c06edcc8902', '2022-04-15', 29),
('5cf8feb2-2709-41fc-b777-61c2ec1023e6', 'dac3ac49-6f33-49f5-b430-d5424fd65a41', '2022-04-22', 32),
('7676899c-6725-4800-b78f-d65868fd43f9', 'dac3ac49-6f33-49f5-b430-d5424fd65a41', '2022-04-16', 32),
('9395f236-e9b3-4ad2-a1de-f07ca8765aa2', 'dac3ac49-6f33-49f5-b430-d5424fd65a41', '2022-04-20', 32),
('b9f2501a-4177-4edb-bac3-682a1c194f87', 'dac3ac49-6f33-49f5-b430-d5424fd65a41', '2022-04-17', 32),
('bf043193-a0b0-4ab1-abc0-ea716ca225f3', 'dac3ac49-6f33-49f5-b430-d5424fd65a41', '2022-04-23', 32);

-- --------------------------------------------------------

--
-- Table structure for table `tickets`
--

CREATE TABLE `tickets` (
  `id` varchar(70) NOT NULL,
  `bus_route_id` varchar(70) NOT NULL,
  `fare` decimal(10,0) NOT NULL,
  `date` date NOT NULL,
  `is_blocked` tinyint(1) NOT NULL,
  `seat_no` int(11) NOT NULL,
  `user_id` varchar(70) NOT NULL,
  `booked_at` datetime NOT NULL,
  `order_id` varchar(70) NOT NULL,
  `payment_status` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `tickets`
--

INSERT INTO `tickets` (`id`, `bus_route_id`, `fare`, `date`, `is_blocked`, `seat_no`, `user_id`, `booked_at`, `order_id`, `payment_status`) VALUES
('3c719339-3ed9-4d60-87c7-63eb643933b9', '364ccf62-edce-4a65-8321-cd2432a0c78b', '1200', '2022-04-15', 0, 3, '5ceae01c-ecb4-4aa6-b5cb-842f35187d5e', '2022-04-15 12:31:24', '62a23dfe-3dfe-4cfd-a717-62d8b1e6adef', 1),
('45305610-3317-4848-821c-731a479e332f', '364ccf62-edce-4a65-8321-cd2432a0c78b', '1200', '2022-04-15', 0, 5, '5ceae01c-ecb4-4aa6-b5cb-842f35187d5e', '2022-04-15 12:31:24', '62a23dfe-3dfe-4cfd-a717-62d8b1e6adef', 1),
('738eba95-8b1d-4e56-8a6c-e4e950cbe7f3', '364ccf62-edce-4a65-8321-cd2432a0c78b', '1200', '2022-04-15', 0, 2, '5ceae01c-ecb4-4aa6-b5cb-842f35187d5e', '2022-04-15 12:31:24', '62a23dfe-3dfe-4cfd-a717-62d8b1e6adef', 1),
('cf48d468-59fa-4a7a-ad6e-a5ffd1233239', '364ccf62-edce-4a65-8321-cd2432a0c78b', '1200', '2022-04-15', 0, 8, '5ceae01c-ecb4-4aa6-b5cb-842f35187d5e', '2022-04-15 05:30:00', '62a23dfe-3dfe-4cfd-a717-62d8b1e6adef', 1);

-- --------------------------------------------------------

--
-- Table structure for table `ticket_orders`
--

CREATE TABLE `ticket_orders` (
  `id` varchar(55) NOT NULL,
  `order_id` varchar(75) NOT NULL,
  `receipt` varchar(75) NOT NULL,
  `amount` double NOT NULL,
  `created_at` datetime NOT NULL,
  `user_id` varchar(75) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id` varchar(70) NOT NULL,
  `email` varchar(100) NOT NULL,
  `hashed_password` text NOT NULL,
  `first_name` varchar(30) NOT NULL,
  `last_name` varchar(30) NOT NULL,
  `mobile_number` varchar(10) NOT NULL,
  `aadhar_no` varchar(12) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `email`, `hashed_password`, `first_name`, `last_name`, `mobile_number`, `aadhar_no`) VALUES
('5ceae01c-ecb4-4aa6-b5cb-842f35187d5e', 'mari@gmail.com', 'pw2bqGZnCnz3AOrlj7vyPn42QTlLvXdVDLMPv6PGjdA=', 'Mari', 'Vignesh', '1478529630', '123456789102'),
('9bb1f17d-a48d-4d0a-bc71-5b9589bc736e', 'hari@gmail.com', '+Pu5ZJUc2x+SnAmnLvxHlsHupyA1iasEvIWqC9Mr2tY=', 'hari', 'prasath', '1478529630', '123456789102');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `blocked_tickets`
--
ALTER TABLE `blocked_tickets`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `seat_id` (`seat_id`),
  ADD KEY `FK_BKTIC_TO _ROU` (`bus_route_id`);

--
-- Indexes for table `bus_master`
--
ALTER TABLE `bus_master`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `registration_no` (`registration_no`);

--
-- Indexes for table `bus_routes_history`
--
ALTER TABLE `bus_routes_history`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK_ROU_TO_BUS` (`bus_id`);

--
-- Indexes for table `tickets`
--
ALTER TABLE `tickets`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK_TIC_TO_ROU` (`bus_route_id`),
  ADD KEY `FK_TIC_TO_USER` (`user_id`);

--
-- Indexes for table `ticket_orders`
--
ALTER TABLE `ticket_orders`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `blocked_tickets`
--
ALTER TABLE `blocked_tickets`
  ADD CONSTRAINT `FK_BKTIC_TO _ROU` FOREIGN KEY (`bus_route_id`) REFERENCES `bus_routes_history` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `bus_routes_history`
--
ALTER TABLE `bus_routes_history`
  ADD CONSTRAINT `FK_ROU_TO_BUS` FOREIGN KEY (`bus_id`) REFERENCES `bus_master` (`id`);

--
-- Constraints for table `tickets`
--
ALTER TABLE `tickets`
  ADD CONSTRAINT `FK_TIC_TO_ROU` FOREIGN KEY (`bus_route_id`) REFERENCES `bus_routes_history` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `FK_TIC_TO_USER` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
