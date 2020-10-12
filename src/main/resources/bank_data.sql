CREATE DATABASE IF NOT EXISTS `bank_data`;
USE `bank_data`;

DROP TABLE IF EXISTS `transactions`;
DROP TABLE IF EXISTS `account`;
DROP TABLE IF EXISTS `client_data`;

CREATE TABLE `client_data` (
  `username` varchar(15) NOT NULL,
  `password` varchar(45) NOT NULL,
  `first_name` varchar(45) NOT NULL,
  `last_name` varchar(45) NOT NULL,
  `cnp` int(13) NOT NULL,
  `email` varchar(45) NOT NULL,
  PRIMARY KEY (`username`)
);

CREATE TABLE `account` (
  `account_no` int(8) NOT NULL AUTO_INCREMENT,
  `username` varchar(15) NOT NULL,
  `account_type` int(1) NOT NULL,
  `balance` int,
  `currency` int(1) NOT NULL,
  PRIMARY KEY (`account_no`),
  FOREIGN KEY (`username`) REFERENCES client_data(`username`)
);

ALTER TABLE `account` AUTO_INCREMENT = 10000001;

CREATE TABLE `transactions` (
  `reference_id` int(8) NOT NULL AUTO_INCREMENT,
  `transaction_type` int(1) NOT NULL,
  `account_no` int(8) NOT NULL,
  `transfer_amount` int,
  `foreign_account` varchar(34) NOT NULL,
  `date` date NOT NULL,
  PRIMARY KEY (`reference_id`),
  FOREIGN KEY (`account_no`) REFERENCES account(`account_no`)
);