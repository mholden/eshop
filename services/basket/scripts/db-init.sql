create database basketdb;
use basketdb;
CREATE TABLE `basketitem` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` varchar(36) NOT NULL,
  `catalog_item_id` int NOT NULL,
  PRIMARY KEY (`id`)
);
