-- MySQL dump 10.13  Distrib 8.0.27, for Win64 (x86_64)
--
-- Host: localhost    Database: freshtest
-- ------------------------------------------------------
-- Server version	8.0.27

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `stocked_goods`
--

DROP TABLE IF EXISTS `stocked_goods`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `stocked_goods` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `type` tinyint NOT NULL,
  `price` decimal(10,2) unsigned NOT NULL,
  `storage` decimal(10,2) unsigned NOT NULL,
  `sales` decimal(10,2) unsigned NOT NULL,
  `description` text,
  `onsale` tinyint NOT NULL,
  `sale_price` decimal(10,2) DEFAULT NULL,
  `rate` decimal(10,2) DEFAULT NULL,
  `rate_count` int NOT NULL DEFAULT '0',
  `brand` varchar(45) NOT NULL,
  `category_id` int NOT NULL,
  `is_new` tinyint NOT NULL,
  `pic` varchar(500) DEFAULT NULL,
  `active` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stocked_goods`
--

LOCK TABLES `stocked_goods` WRITE;
/*!40000 ALTER TABLE `stocked_goods` DISABLE KEYS */;
INSERT INTO `stocked_goods` VALUES (1,'bagged chicken wings',1,12.93,5.00,1.00,'on sale goods',1,NULL,5.00,31,'dfdf',2,1,'https://assets.epicurious.com/photos/5732526f1877f76a0e20831c/1:1/w_1600,c_limit/EP_05102016_PeruvianStyleRoastChicken_recipe_.jpg',1),(2,'bagged chicken wings',1,12.93,5.00,1.00,'not on sale',1,NULL,5.00,31,'dfdf',2,1,NULL,1),(3,'bagged chicken wings',1,12.93,5.00,1.00,'not on sale',1,NULL,5.00,31,'dfdf',2,1,NULL,1),(4,'bagged chicken wings',1,12.93,5.00,1.00,'not on sale',1,NULL,5.00,31,'dfdf',2,1,NULL,1),(5,'bagged chicken wings',1,12.93,5.00,1.00,'not on sale',1,NULL,5.00,31,'dfdf',2,1,NULL,1),(6,'bagged chicken wings',1,12.93,5.00,1.00,'not on sale',1,NULL,5.00,31,'dfdf',2,1,NULL,1),(7,'bagged chicken wings',1,12.93,5.00,1.00,'not on sale',1,NULL,5.00,31,'dfdf',2,1,NULL,1),(8,'bagged chicken wings',1,12.93,5.00,1.00,'not on sale',1,NULL,5.00,31,'dfdf',2,1,NULL,1),(9,'bagged chicken wings',1,12.93,5.00,1.00,'not on sale',1,NULL,5.00,31,'dfdf',2,1,NULL,1),(10,'bagged chicken wings',1,12.93,5.00,1.00,'not on sale',1,NULL,5.00,31,'dfdf',2,1,NULL,1),(11,'bagged chicken wings',1,12.93,5.00,1.00,'not on sale',1,NULL,5.00,31,'dfdf',2,1,NULL,1),(12,'bagged chicken wings',1,12.93,5.00,1.00,'not on sale',1,NULL,5.00,31,'dfdf',2,1,NULL,1),(13,'bagged chicken wings',1,12.93,5.00,1.00,'not on sale',1,NULL,5.00,31,'dfdf',2,1,NULL,1),(14,'bagged chicken wings',1,12.93,5.00,1.00,'not on sale',1,NULL,5.00,31,'dfdf',2,1,NULL,1),(15,'bagged chicken wings',1,12.93,5.00,1.00,'not on sale',1,NULL,5.00,31,'dfdf',2,1,NULL,1),(16,'bagged carrots',1,12.93,5.00,1.00,'not on sale',1,NULL,5.00,31,'dfdf',4,0,NULL,1);
/*!40000 ALTER TABLE `stocked_goods` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-02-24 18:50:37
