drop database orderdb;
create database orderdb;
use orderdb;
CREATE TABLE `_order` (
  `id` int NOT NULL AUTO_INCREMENT,
	`user_id` varchar(36) NOT NULL,
	`creation_time` timestamp NOT NULL,
  `state` varchar(32) NOT NULL,
  PRIMARY KEY (`id`)
);
CREATE TABLE `orderitem` (
	`id` int NOT NULL AUTO_INCREMENT,
	`order_id` int NOT NULL,
	`catalog_item_id` int NOT NULL,
	PRIMARY KEY (`id`)
);
