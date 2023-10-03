create database catalogdb;
use catalogdb;
CREATE TABLE `catalogitem` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `price` int NOT NULL,
  PRIMARY KEY (`id`)
);
insert into catalogitem (name, price) values ('catalog item 1', 999);
insert into catalogitem (name, price) values ('catalog item 2', 1999);
