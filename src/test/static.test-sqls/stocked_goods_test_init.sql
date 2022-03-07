-- MySQL dump 10.13  Distrib 8.0.27, for macos11 (x86_64)
--
-- Host: localhost    Database: test
-- ------------------------------------------------------
-- Server version	8.0.28

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
-- Dumping data for table `stocked_goods`
--

LOCK TABLES `stocked_goods` WRITE;
/*!40000 ALTER TABLE `stocked_goods` DISABLE KEYS */;
INSERT INTO `stocked_goods` VALUES (1,'beef1',2,20.33,10.33,2.20,'beef things',1,18.33,0.00,0,'TNT',123,0,'/usr/balabala',1),
                                   (2,'pork',2,22.33,20.33,3.33,'pork things',1,18.33,5.00,2,'abc',124,0,'/usr/else',1),
                                   (3,'lamp',2,33.00,50.00,5.00,'lamp things',0,0.00,3.00,5,'bcd',125,0,'/usr/lalala',1),
                                   (4,'beef2',2,21.33,10.33,2.20,'beef things',0,0.00,4.32,20,'TNT',123,0,'/usr/balabala',1),
                                   (5,'beef3',2,22.33,10.33,2.20,'beef things',0,0.00,0.00,0,'TNT',123,0,'/usr/balabala',1),
                                   (6,'beef4',2,20.33,10.33,2.20,'beef things',0,0.00,0.00,0,'ABC',123,0,'/usr/balabala',1),
                                   (7,'beef5',2,20.33,10.33,2.20,'beef things',0,0.00,0.00,0,'NOTNT',123,0,'/usr/balabala',1);
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

-- Dump completed on 2022-02-25 19:37:52
