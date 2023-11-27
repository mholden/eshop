drop database paymentdb;
create database paymentdb;
use paymentdb;
CREATE TABLE `payment` (
  `id` int NOT NULL AUTO_INCREMENT,
	`order_item_id` int NOT NULL,
  PRIMARY KEY (`id`)
);
