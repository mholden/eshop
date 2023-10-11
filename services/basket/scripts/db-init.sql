create database basketdb;
use basketdb;
CREATE TABLE `basketitem` (
  `id` int NOT NULL AUTO_INCREMENT,
  `userid` int NOT NULL,
  `catalogitemid` int NOT NULL,
  PRIMARY KEY (`id`)
);
