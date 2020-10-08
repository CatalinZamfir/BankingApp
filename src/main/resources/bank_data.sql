CREATE DATABASE IF NOT EXISTS `bank_data`;
USE `bank_data`;

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

DROP TABLE IF EXISTS `accounts`;

CREATE TABLE `accounts` (
  `account_no` int(8) NOT NULL AUTO_INCREMENT,
  `username` varchar(15) NOT NULL,
  `account_type` int(1) NOT NULL,
  `balance` int,
  `currency` int(1) NOT NULL,
  PRIMARY KEY (`account_no`),
  FOREIGN KEY (`username`) REFERENCES client_data(`username`),
  FOREIGN KEY (`account_type`) REFERENCES account_types(`account_type`),
  FOREIGN KEY (`currency`) REFERENCES currencies(`currency`)
);

DROP TABLE IF EXISTS `transactions`;

CREATE TABLE `transactions` (
  `reference_id` int(8) NOT NULL AUTO_INCREMENT,
  `transaction_type` int(1) NOT NULL,
  `account_no` int(8) NOT NULL,
  `transfer_amount` int,
  `foreign_account` varchar(34) NOT NULL,
  `date` date NOT NULL,
  PRIMARY KEY (`account_no`),
  FOREIGN KEY (`transaction_type`) REFERENCES transaction_types(`transaction_type`),
  FOREIGN KEY (`account_no`) REFERENCES accounts(`account_no`)
);

DROP TABLE IF EXISTS `account_types`;

CREATE TABLE `account_types` (
`account_type_id` int(1),
`account_type_name` varchar(15) NOT NULL
);

CREATE TABLE `currencies` (
`currency_id` int(1),
`currency_name` varchar(3) NOT NULL
);

CREATE TABLE `transaction_types` (
`transaction_type_id` int(1),
`transaction_type_name` varchar(15) NOT NULL
);