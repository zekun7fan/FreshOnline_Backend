/*
 Navicat Premium Data Transfer

 Source Server         : freshonline
 Source Server Type    : MySQL
 Source Server Version : 80027
 Source Host           : localhost:3306
 Source Schema         : FreshOnline

 Target Server Type    : MySQL
 Target Server Version : 80027
 File Encoding         : 65001

 Date: 06/02/2022 20:27:01
*/

CREATE DATABASE `test` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE test
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for cart
-- ----------------------------
DROP TABLE IF EXISTS `cart`;
CREATE TABLE `cart` (
  `user_id` int NOT NULL,
  `goods_id` int NOT NULL,
  `count` decimal(10,2) unsigned NOT NULL,
  PRIMARY KEY (`user_id`,`goods_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category` (
  `id` int NOT NULL AUTO_INCREMENT,
  `parent_id` int NOT NULL,
  `name` varchar(45) NOT NULL,
  `level` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for favorite
-- ----------------------------
DROP TABLE IF EXISTS `favorite`;
CREATE TABLE `favorite` (
  `user_id` int NOT NULL,
  `goods_id` int NOT NULL,
  PRIMARY KEY (`user_id`,`goods_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for order
-- ----------------------------
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order` (
  `order_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `payment_method_id` int DEFAULT NULL,
  `order_time` timestamp NOT NULL,
  `pay_time` timestamp NULL DEFAULT NULL,
  `finish_time` timestamp NULL DEFAULT NULL,
  `status` tinyint NOT NULL,
  `location` varchar(500) NOT NULL,
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for payment_method
-- ----------------------------
DROP TABLE IF EXISTS `payment_method`;
CREATE TABLE `payment_method` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `payment_type` tinyint NOT NULL,
  `details` json DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for saled_goods
-- ----------------------------
DROP TABLE IF EXISTS `saled_goods`;
CREATE TABLE `saled_goods` (
  `order_id` int NOT NULL,
  `goods_id` int NOT NULL,
  `price` decimal(10,2) unsigned NOT NULL,
  `count` decimal(10,2) unsigned NOT NULL,
  `comment` text,
  `rate` int DEFAULT NULL,
  PRIMARY KEY (`order_id`,`goods_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for stocked_goods
-- ----------------------------
DROP TABLE IF EXISTS `stocked_goods`;
CREATE TABLE `stocked_goods` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `type` tinyint NOT NULL,
  `price` decimal(10,2) unsigned NOT NULL,
  `storage` decimal(10,2) unsigned NOT NULL,
  `sales` decimal(10,2) unsigned NOT NULL DEFAULT '0.00',
  `description` text,
  `onsale` tinyint NOT NULL,
  `sale_price` decimal(10,2) DEFAULT NULL,
  `rate` decimal(10,2) DEFAULT NULL,
  `rate_count` int NOT NULL DEFAULT '0',
  `brand` varchar(45) NOT NULL,
  `category_id` int NOT NULL,
  `is_new` tinyint NOT NULL,
  `pic` varchar(500) DEFAULT NULL,
  `active` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `type` tinyint DEFAULT NULL,
  `location` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

SET FOREIGN_KEY_CHECKS = 1;

CREATE DATABASE `FreshOnline` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE FreshOnline
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for cart
-- ----------------------------
DROP TABLE IF EXISTS `cart`;
CREATE TABLE `cart` (
  `user_id` int NOT NULL,
  `goods_id` int NOT NULL,
  `count` decimal(10,2) unsigned NOT NULL,
  PRIMARY KEY (`user_id`,`goods_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category` (
  `id` int NOT NULL AUTO_INCREMENT,
  `parent_id` int NOT NULL,
  `name` varchar(45) NOT NULL,
  `level` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for favorite
-- ----------------------------
DROP TABLE IF EXISTS `favorite`;
CREATE TABLE `favorite` (
  `user_id` int NOT NULL,
  `goods_id` int NOT NULL,
  PRIMARY KEY (`user_id`,`goods_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for order
-- ----------------------------
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order` (
  `order_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `payment_method_id` int DEFAULT NULL,
  `order_time` timestamp NOT NULL,
  `pay_time` timestamp NULL DEFAULT NULL,
  `finish_time` timestamp NULL DEFAULT NULL,
  `status` tinyint NOT NULL,
  `location` varchar(500) NOT NULL,
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for payment_method
-- ----------------------------
DROP TABLE IF EXISTS `payment_method`;
CREATE TABLE `payment_method` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `payment_type` tinyint NOT NULL,
  `details` json DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for saled_goods
-- ----------------------------
DROP TABLE IF EXISTS `saled_goods`;
CREATE TABLE `saled_goods` (
  `order_id` int NOT NULL,
  `goods_id` int NOT NULL,
  `price` decimal(10,2) unsigned NOT NULL,
  `count` decimal(10,2) unsigned NOT NULL,
  `comment` text,
  `rate` int DEFAULT NULL,
  PRIMARY KEY (`order_id`,`goods_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for stocked_goods
-- ----------------------------
DROP TABLE IF EXISTS `stocked_goods`;
CREATE TABLE `stocked_goods` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `type` tinyint NOT NULL,
  `price` decimal(10,2) unsigned NOT NULL,
  `storage` decimal(10,2) unsigned NOT NULL,
  `sales` decimal(10,2) unsigned NOT NULL DEFAULT '0.00',
  `description` text,
  `onsale` tinyint NOT NULL,
  `sale_price` decimal(10,2) DEFAULT NULL,
  `rate` decimal(10,2) DEFAULT NULL,
  `rate_count` int NOT NULL DEFAULT '0',
  `brand` varchar(45) NOT NULL,
  `category_id` int NOT NULL,
  `is_new` tinyint NOT NULL,
  `pic` varchar(500) DEFAULT NULL,
  `active` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `type` tinyint DEFAULT NULL,
  `location` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

SET FOREIGN_KEY_CHECKS = 1;

