create database orderdb;
use orderdb;
CREATE TABLE `order` (
  `id` int NOT NULL AUTO_INCREMENT,
	`userId` varchar(36) NOT NULL,
	`creationtime` timestamp NOT NULL,
  `state` varchar(32) NOT NULL,
  PRIMARY KEY (`id`)
);
CREATE TABLE `orderitem` (
	`id` int NOT NULL AUTO_INCREMENT,
	`orderid` int NOT NULL,
	`catalogitemid` int NOT NULL,
	PRIMARY KEY (`id`)
);
