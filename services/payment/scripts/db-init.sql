create database paymentdb;
use paymentdb;
CREATE TABLE `payment` (
  `id` int NOT NULL AUTO_INCREMENT,
  `catalogitemid` int NOT NULL,
	`orderitemid` int NOT NULL,
  PRIMARY KEY (`id`)
);
