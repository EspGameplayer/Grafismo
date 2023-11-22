CREATE DATABASE  IF NOT EXISTS `grafismo` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `grafismo`;
-- MySQL dump 10.13  Distrib 5.5.16, for Win32 (x86)
--
-- Host: localhost    Database: grafismo
-- ------------------------------------------------------
-- Server version	5.5.29

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `action_key`
--

DROP TABLE IF EXISTS `action_key`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `action_key` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `action` varchar(255) NOT NULL,
  `jhi_keys` varchar(255) NOT NULL,
  `graphic_element_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ux_action_key__graphic_element_id` (`graphic_element_id`),
  CONSTRAINT `fk_action_key__graphic_element_id` FOREIGN KEY (`graphic_element_id`) REFERENCES `graphic_element` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `action_key`
--

LOCK TABLES `action_key` WRITE;
/*!40000 ALTER TABLE `action_key` DISABLE KEYS */;
/*!40000 ALTER TABLE `action_key` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `association`
--

DROP TABLE IF EXISTS `association`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `association` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `info` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `association`
--

LOCK TABLES `association` WRITE;
/*!40000 ALTER TABLE `association` DISABLE KEYS */;
INSERT INTO `association` VALUES (1,'FIFA$UEFA$RFEF (ESP)$RFAF (Andalucía)$Delegación de Huelva'),(2,'FIFA$UEFA$RFEF (ESP)$RFAF (Andalucía)$Delegación de Sevilla'),(3,'FIFA$UEFA$RFEF (ESP)$RFAF (Andalucía)$Delegación de Cádiz'),(4,'FIFA$UEFA$RFEF (ESP)$RFAF (Andalucía)$Delegación de Córdoba'),(5,'FIFA$UEFA$RFEF (ESP)$RFAF (Andalucía)$Delegación de Málaga'),(6,'FIFA$UEFA$RFEF (ESP)$RFAF (Andalucía)$Delegación de Jaén'),(7,'FIFA$UEFA$RFEF (ESP)$RFAF (Andalucía)$Delegación de Granada'),(8,'FIFA$UEFA$RFEF (ESP)$RFAF (Andalucía)$Delegación de Almería');
/*!40000 ALTER TABLE `association` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `broadcast`
--

DROP TABLE IF EXISTS `broadcast`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `broadcast` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `misc_data` varchar(255) DEFAULT NULL,
  `match_id` bigint(20) NOT NULL,
  `system_configuration_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ux_broadcast__match_id` (`match_id`),
  KEY `fk_broadcast__system_configuration_id` (`system_configuration_id`),
  CONSTRAINT `fk_broadcast__match_id` FOREIGN KEY (`match_id`) REFERENCES `jhi_match` (`id`),
  CONSTRAINT `fk_broadcast__system_configuration_id` FOREIGN KEY (`system_configuration_id`) REFERENCES `system_configuration` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `broadcast`
--

LOCK TABLES `broadcast` WRITE;
/*!40000 ALTER TABLE `broadcast` DISABLE KEYS */;
INSERT INTO `broadcast` VALUES (1,'',1,1);
/*!40000 ALTER TABLE `broadcast` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `broadcast_personnel_member`
--

DROP TABLE IF EXISTS `broadcast_personnel_member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `broadcast_personnel_member` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `graphics_name` varchar(255) NOT NULL,
  `long_graphics_name` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  `person_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ux_broadcast_personnel_member__person_id` (`person_id`),
  CONSTRAINT `fk_broadcast_personnel_member__person_id` FOREIGN KEY (`person_id`) REFERENCES `person` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `broadcast_personnel_member`
--

LOCK TABLES `broadcast_personnel_member` WRITE;
/*!40000 ALTER TABLE `broadcast_personnel_member` DISABLE KEYS */;
INSERT INTO `broadcast_personnel_member` VALUES (1,'Jesús Gascó','Jesús Gascó','COMENTATOR',1),(2,'José Antonio Domínguez','José Antonio Domínguez','CAMERAMAN',2),(3,'José Antonio Domínguez Jr.','José Antonio Domínguez Jr.','PRODUCER',3);
/*!40000 ALTER TABLE `broadcast_personnel_member` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `competition`
--

DROP TABLE IF EXISTS `competition`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `competition` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `graphics_name` varchar(255) DEFAULT NULL,
  `type` varchar(255) NOT NULL,
  `colour` varchar(255) DEFAULT NULL,
  `regulations` varchar(255) DEFAULT NULL,
  `details` varchar(255) DEFAULT NULL,
  `misc_data` varchar(255) DEFAULT NULL,
  `parent_id` bigint(20) DEFAULT NULL,
  `country_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_competition__parent_id` (`parent_id`),
  KEY `fk_competition__country_id` (`country_id`),
  CONSTRAINT `fk_competition__country_id` FOREIGN KEY (`country_id`) REFERENCES `country` (`id`),
  CONSTRAINT `fk_competition__parent_id` FOREIGN KEY (`parent_id`) REFERENCES `competition` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `competition`
--

LOCK TABLES `competition` WRITE;
/*!40000 ALTER TABLE `competition` DISABLE KEYS */;
INSERT INTO `competition` VALUES (1,'División de Honor Sénior 2022/23','División de Honor Andaluza','LEAGUE','','Formato:\nLiga de 16 equipos, a ida y vuelta. Las victorias otorgan 3 puntos, los empates 1 y las derrotas 0.\n\nRegulaciones:\nCada 5 tarjetas amarillas acarrearán 1 partido de sanción.','','',NULL,1);
/*!40000 ALTER TABLE `competition` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `country`
--

DROP TABLE IF EXISTS `country`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `country` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `abb` varchar(255) NOT NULL,
  `flag` longblob,
  `flag_content_type` varchar(255) DEFAULT NULL,
  `population` int(11) DEFAULT NULL,
  `census_year` int(11) DEFAULT NULL,
  `denonym` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=212 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `country`
--

LOCK TABLES `country` WRITE;
/*!40000 ALTER TABLE `country` DISABLE KEYS */;
INSERT INTO `country` VALUES (1,'España','ESP','../fake-data/blob/hipster.png','image/png',47615034,2022,'Español'),(2,'Afganistán','AFG','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Afgano'),(3,'Albania','ALB','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Albanés, Albano'),(4,'Argelia','ALG','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Argelino'),(5,'Samoa Americana','ASA','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Samoano americano, Samoano estadounidense'),(6,'Andorra','AND','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Andorrano'),(7,'Angola','ANG','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Angoleño'),(8,'Anguila','AIA','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Anguilense'),(9,'Antigua y Barbuda','ATG','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Antiguano, Antigüense, Barbudeño, Barbudense, Antiguobarbudeño, Antiguobarbudense'),(10,'Argentina','ARG','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Argentino'),(11,'Armenia','ARM','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Armenio'),(12,'Aruba','ARU','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Arubano, Arubeño'),(13,'Australia','AUS','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Australiano'),(14,'Austria','AUT','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Austríaco'),(15,'Azerbaiyán','AZE','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Azerbaiyano, Azerí'),(16,'Bahamas','BAH','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Bahameño, Bahamés'),(17,'Baréin','BHR','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Bareiní'),(18,'Bangladés','BAN','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Bangladesí'),(19,'Barbados','BRB','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Barbadense'),(20,'Bielorrusia','BLR','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Bielorruso'),(21,'Bélgica','BEL','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Belga'),(22,'Belice','BLZ','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Beliceño'),(23,'Benín','BEN','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Beninés'),(24,'Bermudas','BER','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Bermudeño'),(25,'Bután','BHU','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Butanés'),(26,'Bolivia','BOL','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Boliviano'),(27,'Bosnia y Herzegovina','BIH','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Bosnioherzegovino, Bosnio, Bosníaco'),(28,'Botsuana','BOT','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Botsuano, Botsuanés'),(29,'Brasil','BRA','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Brasileño, Brasilero'),(30,'Islas Vírgenes Británicas','VGB','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Virgenense británico, Anglo-virgenense'),(31,'Brunéi','BRU','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Bruneano'),(32,'Bulgaria','BUL','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Búlgaro'),(33,'Burkina Faso','BFA','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Burkinés'),(34,'Burundi','BDI','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Burundés'),(35,'Camboya','CAM','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Camboyano'),(36,'Camerún','CMR','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Camerunés'),(37,'Canadá','CAN','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Canadiense'),(38,'Cabo Verde','CPV','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Caboverdiano'),(39,'Islas Caimán','CAY','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Caimanés'),(40,'República Centroafricana','CTA','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Centroafricano'),(41,'Chad','CHA','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Chadiano'),(42,'Chile','CHI','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Chileno'),(43,'China','CHN','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Chino'),(44,'Taiwán','TPE','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Taiwanés'),(45,'Colombia','COL','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Colombiano'),(46,'Comoras','COM','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Comorense'),(47,'República del Congo','CGO','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Congoleño, Congolés'),(48,'Islas Cook','COK','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Cookiano'),(49,'Costa Rica','CRC','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Costarricense, Tico'),(50,'Croacia','CRO','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Croata'),(51,'Cuba','CUB','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Cubano'),(52,'Curazao','CUW','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Curazoleño, Curazaleño'),(53,'Chipre','CYP','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Chipriota'),(54,'República Checa','CZE','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Checo'),(55,'Dinamarca','DEN','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Danés, Dinamarqués'),(56,'Yibuti','DJI','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Yibutiano, Yibutí, Yibutiense'),(57,'Dominica','DMA','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Dominiqués'),(58,'República Dominicana','DOM','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Dominicano, Quisqueyano'),(59,'República Democrática del Congo','COD','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Congoleño, Congolés'),(60,'Ecuador','ECU','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Ecuatoriano'),(61,'Egipto','EGY','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Egipcio'),(62,'El Salvador','SLV','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Salvadoreño, Cuzcatleco, Cuscatleco'),(63,'Inglaterra','ENG','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Inglés'),(64,'Guinea Ecuatorial','EQG','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Ecuatoguineano'),(65,'Eritrea','ERI','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Eritreo'),(66,'Estonia','EST','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Estonio, Estoniano'),(67,'Suazilandia','SWZ','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Suazi'),(68,'Etiopía','ETH','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Etíope, Etiopí, Abisinio'),(69,'Islas Feroe','FRO','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Feroés'),(70,'Fiyi','FIJ','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Fiyiano'),(71,'Finlandia','FIN','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Finlandés, Finés'),(72,'Francia','FRA','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Francés, Galo'),(73,'Gabón','GAB','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Gabonés'),(74,'Gambia','GAM','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Gambiano'),(75,'Georgia','GEO','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Georgiano'),(76,'Alemania','GER','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Alemán, Germano, Tudesco, Teutón'),(77,'Ghana','GHA','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Ghanés'),(78,'Gibraltar','GIB','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Gibraltareño'),(79,'Grecia','GRE','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Griego, Heleno'),(80,'Granada','GRN','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Granadino'),(81,'Guam','GUM','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Guameño'),(82,'Guatemala','GUA','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Guatemalteco'),(83,'Guinea','GUI','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Guineano'),(84,'Guinea-Bisáu','GNB','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Bisauguineano'),(85,'Guyana','GUY','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Guyanés'),(86,'Haití','HAI','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Haitiano'),(87,'Honduras','HON','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Hondureño, Catracho'),(88,'Hong Kong','HKG','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Hongkonés'),(89,'Hungría','HUN','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Húngaro'),(90,'Islandia','ISL','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Islandés'),(91,'India','IND','../fake-data/blob/hipster.png','image/png',NULL,NULL,'India'),(92,'Indonesia','IDN','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Indonesio'),(93,'Irán','IRN','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Iraní'),(94,'Irak','IRQ','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Iraquí'),(95,'Israel','ISR','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Israelí'),(96,'Italia','ITA','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Italiano'),(97,'Costa de Marfil','CIV','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Marfileño'),(98,'Jamaica','JAM','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Jamaicano, Jamaiquino'),(99,'Japón','JPN','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Japonés, Nipón'),(100,'Jordania','JOR','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Jordano'),(101,'Kazajistán','KAZ','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Kazajo, Kazako'),(102,'Kenia','KEN','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Keniano, Keniata'),(103,'Kosovo','KOS','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Kosovar'),(104,'Kuwait','KUW','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Kuwaití'),(105,'Kirguistán','KZG','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Kirguís, Kirguiso'),(106,'Laos','LAO','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Laosiano'),(107,'Letonia','LVA','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Letón'),(108,'Líbano','LBN','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Libanés'),(109,'Lesoto','LES','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Lesotense'),(110,'Liberia','LBR','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Liberiano'),(111,'Libia','LBY','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Libio'),(112,'Liechtenstein','LIE','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Liechtensteiniano'),(113,'Lituania','LTU','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Lituano'),(114,'Luxemburgo','LUX','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Luxemburgués'),(115,'Macao','MAC','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Macaense'),(116,'Madagascar','MAD','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Malgache'),(117,'Malaui','MWI','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Malauí'),(118,'Malasia','MAS','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Malasio, Malayo'),(119,'Maldivas','MDV','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Maldivo'),(120,'Malí','MLI','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Maliense, Malí'),(121,'Malta','MLT','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Maltés'),(122,'Mauritania','MTN','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Mauritano'),(123,'Mauricio','MRI','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Mauriciano'),(124,'México','MEX','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Mexicano, Mejicano'),(125,'Moldavia','MDA','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Moldavo'),(126,'Mongolia','MNG','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Mongol'),(127,'Montenegro','MNE','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Montenegrino'),(128,'Montserrat','MSR','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Montserratense'),(129,'Marruecos','MAR','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Marroquí, Marroquín'),(130,'Mozambique','MOZ','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Mozambiqueño'),(131,'Birmania','MYA','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Birmano, Myanma'),(132,'Namibia','NAM','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Namibio'),(133,'Nepal','NEP','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Nepalí, Nepalés'),(134,'Países Bajos','NED','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Neerlandés'),(135,'Nueva Caledonia','NCL','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Neocaledonio'),(136,'Nueva Zelanda','NZL','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Neozelandés, Kiwi'),(137,'Nicaragua','NCA','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Nicaragüense'),(138,'Níger','NIG','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Nigerino'),(139,'Nigeria','NGA','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Nigeriano'),(140,'Corea del Norte','PRK','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Norcoreano, Coreano del Norte'),(141,'Macedonia del Norte','MKD','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Macedonio'),(142,'Irlanda del Norte','NIR','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Norirlandés'),(143,'Noruega','NOR','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Noruego'),(144,'Omán','OMA','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Omaní'),(145,'Pakistán','PAK','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Pakistaní, Paquistaní'),(146,'Palestina','PLE','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Palestino'),(147,'Panamá','PAN','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Panameño'),(148,'Papúa Nueva Guinea','PNG','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Papú, Papú neoguineano, Papuano'),(149,'Paraguay','PAR','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Paraguayo'),(150,'Perú','PER','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Peruano'),(151,'Filipinas','PHI','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Filipino'),(152,'Polonia','POL','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Polaco, Polonés, Polandés'),(153,'Portugal','POR','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Portugués, Luso'),(154,'Puerto Rico','PUR','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Puertorriqueño, Portorriqueño, Boricua, Borinqueño, Borincano'),(155,'Catar','QAT','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Catarí, Qatarí'),(156,'Irlanda','IRL','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Irlandés'),(157,'Rumanía','ROU','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Rumano'),(158,'Rusia','RUS','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Ruso'),(159,'Ruanda','RWA','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Ruandés'),(160,'San Cristóbal y Nieves','SKN','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Sancristobaleño'),(161,'Santa Lucía','LCA','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Santalucense'),(162,'San Vicente y las Granadinas','VIN','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Sanvicentino'),(163,'Samoa','SAM','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Samoano'),(164,'San Marino','SMR','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Sanmarinense'),(165,'Santo Tomé y Príncipe','STP','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Santotomense'),(166,'Arabia Saudita','KSA','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Saudí, Saudita'),(167,'Escocia','SCO','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Escocés'),(168,'Senegal','SEN','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Senegalés'),(169,'Serbia','SRB','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Serbio'),(170,'Seychelles','SEY','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Seychelense, Seychellense'),(171,'Sierra Leona','SLE','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Sierraleonés'),(172,'Singapur','SGP','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Singapurense'),(173,'Eslovaquia','SVK','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Eslovaco'),(174,'Eslovenia','SVN','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Esloveno'),(175,'Islas Salomón','SOL','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Salomonense'),(176,'Somalia','SOM','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Somalí'),(177,'Sudáfrica','RSA','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Sudafricano, Surafricano'),(178,'Corea del Sur','KOR','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Surcoreano, Coreano del Sur'),(179,'Sudán del Sur','SSD','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Sursudanés, Sudsudanés'),(180,'Sri Lanka','SRI','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Esrilanqués, Ceilanés, Ceilandés'),(181,'Sudán','SDN','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Sudanés'),(182,'Surinam','SUR','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Surinamés'),(183,'Suecia','SWE','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Sueco'),(184,'Suiza','SUI','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Suizo, Helvético'),(185,'Siria','SYR','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Sirio'),(186,'Tahití','TAH','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Tahitiano'),(187,'Tayikistán','TJK','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Tayiko'),(188,'Tanzania','TAN','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Tanzano'),(189,'Tailandia','THA','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Tailandés'),(190,'Timor Oriental','TLS','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Timorense'),(191,'Togo','TOG','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Togolés'),(192,'Tonga','TGA','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Tongano'),(193,'Trinidad y Tobago','TRI','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Trinitense'),(194,'Túnez','TUN','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Tunecino'),(195,'Turquía','TUR','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Turco'),(196,'Turkmenistán','TKM','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Turkmeno, Turcomano'),(197,'Islas Turcas y Caicos','TCA','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Turcocaiqueño'),(198,'Uganda','UGA','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Ugandés'),(199,'Ucrania','UKR','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Ucraniano, Ucranio'),(200,'Emiratos Árabes Unidos','UAE','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Emiratí'),(201,'Estados Unidos','USA','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Estadounidense, Estadunidense, Norteamericano'),(202,'Uruguay','URU','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Uruguayo, Oriental'),(203,'Islas Vírgenes de los EEUU','VIR','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Virgenense estadounidense'),(204,'Uzbekistán','UZB','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Uzbeko, Uzbeco'),(205,'Vanuatu','VAN','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Vanuatense'),(206,'Venezuela','VEN','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Venezolano'),(207,'Vietnam','VIE','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Vietnamita'),(208,'Gales','WAL','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Galés'),(209,'Yemen','YEM','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Yemení, Yemenita'),(210,'Zambia','ZAM','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Zambiano'),(211,'Zimbabue','ZIM','../fake-data/blob/hipster.png','image/png',NULL,NULL,'Zimbabuense');
/*!40000 ALTER TABLE `country` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `databasechangelog`
--

DROP TABLE IF EXISTS `databasechangelog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `databasechangelog` (
  `ID` varchar(255) NOT NULL,
  `AUTHOR` varchar(255) NOT NULL,
  `FILENAME` varchar(255) NOT NULL,
  `DATEEXECUTED` datetime NOT NULL,
  `ORDEREXECUTED` int(11) NOT NULL,
  `EXECTYPE` varchar(10) NOT NULL,
  `MD5SUM` varchar(35) DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `COMMENTS` varchar(255) DEFAULT NULL,
  `TAG` varchar(255) DEFAULT NULL,
  `LIQUIBASE` varchar(20) DEFAULT NULL,
  `CONTEXTS` varchar(255) DEFAULT NULL,
  `LABELS` varchar(255) DEFAULT NULL,
  `DEPLOYMENT_ID` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `databasechangelog`
--

LOCK TABLES `databasechangelog` WRITE;
/*!40000 ALTER TABLE `databasechangelog` DISABLE KEYS */;
INSERT INTO `databasechangelog` VALUES ('00000000000001','jhipster','config/liquibase/changelog/00000000000000_initial_schema.xml','2023-08-28 23:03:00',1,'EXECUTED','8:e41c802136c15bdb6d0b9c3ddf790aca','createTable tableName=jhi_user; createTable tableName=jhi_authority; createTable tableName=jhi_user_authority; addPrimaryKey tableName=jhi_user_authority; createTable tableName=jhi_persistent_token; addForeignKeyConstraint baseTableName=jhi_user_a...','',NULL,'4.6.1',NULL,NULL,'3256580477'),('20230828205808-1','jhipster','config/liquibase/changelog/20230828205808_added_entity_Broadcast.xml','2023-08-28 23:03:00',2,'EXECUTED','8:ff4c72644baaa65f87c887f5f9f020f5','createTable tableName=broadcast','',NULL,'4.6.1',NULL,NULL,'3256580477'),('20230828205808-1-relations','jhipster','config/liquibase/changelog/20230828205808_added_entity_Broadcast.xml','2023-08-28 23:03:00',3,'EXECUTED','8:393405c82a2db8bf92e71da88e2da095','createTable tableName=rel_broadcast__broadcast_personnel_member; addPrimaryKey tableName=rel_broadcast__broadcast_personnel_member','',NULL,'4.6.1',NULL,NULL,'3256580477'),('20230828205808-1-data','jhipster','config/liquibase/changelog/20230828205808_added_entity_Broadcast.xml','2023-08-28 23:03:00',4,'EXECUTED','8:e5834e9a2f106e29b7955c77b45e34a7','loadData tableName=broadcast','',NULL,'4.6.1','faker',NULL,'3256580477'),('20230828205809-1','jhipster','config/liquibase/changelog/20230828205809_added_entity_Team.xml','2023-08-28 23:03:00',5,'EXECUTED','8:cf313664ab66bb816b400d660758c931','createTable tableName=team','',NULL,'4.6.1',NULL,NULL,'3256580477'),('20230828205809-1-relations','jhipster','config/liquibase/changelog/20230828205809_added_entity_Team.xml','2023-08-28 23:03:01',6,'EXECUTED','8:d24a8c35f70725412d9fc101fa8dcd5b','createTable tableName=rel_team__venue; addPrimaryKey tableName=rel_team__venue','',NULL,'4.6.1',NULL,NULL,'3256580477'),('20230828205809-1-data','jhipster','config/liquibase/changelog/20230828205809_added_entity_Team.xml','2023-08-28 23:03:01',7,'EXECUTED','8:477a0b26fb4f0cf3717e15b268f55064','loadData tableName=team','',NULL,'4.6.1','faker',NULL,'3256580477'),('20230828205810-1','jhipster','config/liquibase/changelog/20230828205810_added_entity_Person.xml','2023-08-28 23:03:01',8,'EXECUTED','8:830c4896b787abb0982ed34b4c2e75eb','createTable tableName=person','',NULL,'4.6.1',NULL,NULL,'3256580477'),('20230828205810-1-data','jhipster','config/liquibase/changelog/20230828205810_added_entity_Person.xml','2023-08-28 23:03:01',9,'EXECUTED','8:e6fa313bda042517a0740767b34fe577','loadData tableName=person','',NULL,'4.6.1','faker',NULL,'3256580477'),('20230828205811-1','jhipster','config/liquibase/changelog/20230828205811_added_entity_Player.xml','2023-08-28 23:03:01',10,'EXECUTED','8:2e58925c5fd9b0bd8a270be00e7f6245','createTable tableName=player','',NULL,'4.6.1',NULL,NULL,'3256580477'),('20230828205811-1-relations','jhipster','config/liquibase/changelog/20230828205811_added_entity_Player.xml','2023-08-28 23:03:01',11,'EXECUTED','8:3c76fee56e47d935b6778895e04cbe99','createTable tableName=rel_player__position; addPrimaryKey tableName=rel_player__position','',NULL,'4.6.1',NULL,NULL,'3256580477'),('20230828205811-1-data','jhipster','config/liquibase/changelog/20230828205811_added_entity_Player.xml','2023-08-28 23:03:01',12,'EXECUTED','8:e1dab5cf10a10679235007a8954c47bf','loadData tableName=player','',NULL,'4.6.1','faker',NULL,'3256580477'),('20230828205812-1','jhipster','config/liquibase/changelog/20230828205812_added_entity_StaffMember.xml','2023-08-28 23:03:01',13,'EXECUTED','8:7d83629559fefe026099f68956607b73','createTable tableName=staff_member','',NULL,'4.6.1',NULL,NULL,'3256580477'),('20230828205812-1-data','jhipster','config/liquibase/changelog/20230828205812_added_entity_StaffMember.xml','2023-08-28 23:03:01',14,'EXECUTED','8:8e62a9def1275ae35d679d429cb243c1','loadData tableName=staff_member','',NULL,'4.6.1','faker',NULL,'3256580477'),('20230828205813-1','jhipster','config/liquibase/changelog/20230828205813_added_entity_Referee.xml','2023-08-28 23:03:01',15,'EXECUTED','8:37103d24f48d87ea54c0868ed0df9e76','createTable tableName=referee','',NULL,'4.6.1',NULL,NULL,'3256580477'),('20230828205813-1-data','jhipster','config/liquibase/changelog/20230828205813_added_entity_Referee.xml','2023-08-28 23:03:01',16,'EXECUTED','8:69dd16a2ba5fdd2cda7006afcdb20d00','loadData tableName=referee','',NULL,'4.6.1','faker',NULL,'3256580477'),('20230828205814-1','jhipster','config/liquibase/changelog/20230828205814_added_entity_BroadcastPersonnelMember.xml','2023-08-28 23:03:01',17,'EXECUTED','8:4b127ed142ef9eda22636b5b2c3e9568','createTable tableName=broadcast_personnel_member','',NULL,'4.6.1',NULL,NULL,'3256580477'),('20230828205814-1-data','jhipster','config/liquibase/changelog/20230828205814_added_entity_BroadcastPersonnelMember.xml','2023-08-28 23:03:01',18,'EXECUTED','8:04ff4d137b364787f0c4c2b5f0de32ca','loadData tableName=broadcast_personnel_member','',NULL,'4.6.1','faker',NULL,'3256580477'),('20230828205815-1','jhipster','config/liquibase/changelog/20230828205815_added_entity_Association.xml','2023-08-28 23:03:01',19,'EXECUTED','8:6d6ed3ca74f039caab881e9de8a7bc6c','createTable tableName=association','',NULL,'4.6.1',NULL,NULL,'3256580477'),('20230828205815-1-data','jhipster','config/liquibase/changelog/20230828205815_added_entity_Association.xml','2023-08-28 23:03:01',20,'EXECUTED','8:e6d7865b002bd2af3368099033252a45','loadData tableName=association','',NULL,'4.6.1','faker',NULL,'3256580477'),('20230828205816-1','jhipster','config/liquibase/changelog/20230828205816_added_entity_Venue.xml','2023-08-28 23:03:01',21,'EXECUTED','8:883e4afc83e06412d35e37dbb548ebbf','createTable tableName=venue','',NULL,'4.6.1',NULL,NULL,'3256580477'),('20230828205816-1-data','jhipster','config/liquibase/changelog/20230828205816_added_entity_Venue.xml','2023-08-28 23:03:01',22,'EXECUTED','8:7ef64ec6c1a10c5e84c30bcd11905497','loadData tableName=venue','',NULL,'4.6.1','faker',NULL,'3256580477'),('20230828205817-1','jhipster','config/liquibase/changelog/20230828205817_added_entity_Location.xml','2023-08-28 23:03:01',23,'EXECUTED','8:b0999d5293c46718ef28a076fa43967b','createTable tableName=location','',NULL,'4.6.1',NULL,NULL,'3256580477'),('20230828205817-1-data','jhipster','config/liquibase/changelog/20230828205817_added_entity_Location.xml','2023-08-28 23:03:01',24,'EXECUTED','8:eeee35468a3f05de5a8460ec1bfe37ee','loadData tableName=location','',NULL,'4.6.1','faker',NULL,'3256580477'),('20230828205818-1','jhipster','config/liquibase/changelog/20230828205818_added_entity_Shirt.xml','2023-08-28 23:03:01',25,'EXECUTED','8:703cce30c170fefb086438c200c11e78','createTable tableName=shirt','',NULL,'4.6.1',NULL,NULL,'3256580477'),('20230828205818-1-data','jhipster','config/liquibase/changelog/20230828205818_added_entity_Shirt.xml','2023-08-28 23:03:01',26,'EXECUTED','8:6e26ec449a0d44cc745e1c28ee35e1c7','loadData tableName=shirt','',NULL,'4.6.1','faker',NULL,'3256580477'),('20230828205819-1','jhipster','config/liquibase/changelog/20230828205819_added_entity_Match.xml','2023-08-28 23:03:01',27,'EXECUTED','8:3b5c4e2ab5d93f74be8264de6a6c9ac2','createTable tableName=jhi_match; dropDefaultValue columnName=moment, tableName=jhi_match','',NULL,'4.6.1',NULL,NULL,'3256580477'),('20230828205819-1-relations','jhipster','config/liquibase/changelog/20230828205819_added_entity_Match.xml','2023-08-28 23:03:01',28,'EXECUTED','8:1401918bdf8dbf27b03e07d4c37481ae','createTable tableName=rel_jhi_match__referee; addPrimaryKey tableName=rel_jhi_match__referee','',NULL,'4.6.1',NULL,NULL,'3256580477'),('20230828205819-1-data','jhipster','config/liquibase/changelog/20230828205819_added_entity_Match.xml','2023-08-28 23:03:01',29,'EXECUTED','8:1d53bc2255440894bd136fe0a3dde35d','loadData tableName=jhi_match','',NULL,'4.6.1','faker',NULL,'3256580477'),('20230828205820-1','jhipster','config/liquibase/changelog/20230828205820_added_entity_MatchPlayer.xml','2023-08-28 23:03:01',30,'EXECUTED','8:31f35ed1e41e048fe264369f9c7fab83','createTable tableName=match_player','',NULL,'4.6.1',NULL,NULL,'3256580477'),('20230828205820-1-data','jhipster','config/liquibase/changelog/20230828205820_added_entity_MatchPlayer.xml','2023-08-28 23:03:01',31,'EXECUTED','8:8040a913bd4b5f84f6e77a005aaf7232','loadData tableName=match_player','',NULL,'4.6.1','faker',NULL,'3256580477'),('20230828205821-1','jhipster','config/liquibase/changelog/20230828205821_added_entity_Lineup.xml','2023-08-28 23:03:01',32,'EXECUTED','8:096357fbe1cb8a622f14d9c72334a648','createTable tableName=lineup','',NULL,'4.6.1',NULL,NULL,'3256580477'),('20230828205821-1-relations','jhipster','config/liquibase/changelog/20230828205821_added_entity_Lineup.xml','2023-08-28 23:03:01',33,'EXECUTED','8:730905f2481346a082731fadcedfd96d','createTable tableName=rel_lineup__match_player; addPrimaryKey tableName=rel_lineup__match_player','',NULL,'4.6.1',NULL,NULL,'3256580477'),('20230828205821-1-data','jhipster','config/liquibase/changelog/20230828205821_added_entity_Lineup.xml','2023-08-28 23:03:01',34,'EXECUTED','8:cf8e74548f410e1544ce24ad010b5e64','loadData tableName=lineup','',NULL,'4.6.1','faker',NULL,'3256580477'),('20230828205822-1','jhipster','config/liquibase/changelog/20230828205822_added_entity_Formation.xml','2023-08-28 23:03:01',35,'EXECUTED','8:42018531e79ea96fcef347b1acd8e7a6','createTable tableName=formation','',NULL,'4.6.1',NULL,NULL,'3256580477'),('20230828205822-1-data','jhipster','config/liquibase/changelog/20230828205822_added_entity_Formation.xml','2023-08-28 23:03:01',36,'EXECUTED','8:2bfa4137067415440173e1a619178589','loadData tableName=formation','',NULL,'4.6.1','faker',NULL,'3256580477'),('20230828205823-1','jhipster','config/liquibase/changelog/20230828205823_added_entity_TemplateFormation.xml','2023-08-28 23:03:01',37,'EXECUTED','8:18697dc21f6f3cbb4025b9fa19bab9e8','createTable tableName=template_formation','',NULL,'4.6.1',NULL,NULL,'3256580477'),('20230828205823-1-data','jhipster','config/liquibase/changelog/20230828205823_added_entity_TemplateFormation.xml','2023-08-28 23:03:01',38,'EXECUTED','8:4fce86a6fbec5cc76afafa6b9bddd9a2','loadData tableName=template_formation','',NULL,'4.6.1','faker',NULL,'3256580477'),('20230828205824-1','jhipster','config/liquibase/changelog/20230828205824_added_entity_TeamPlayer.xml','2023-08-28 23:03:01',39,'EXECUTED','8:4554805d34634616dcce22e6dc4e8d9a','createTable tableName=team_player','',NULL,'4.6.1',NULL,NULL,'3256580477'),('20230828205824-1-data','jhipster','config/liquibase/changelog/20230828205824_added_entity_TeamPlayer.xml','2023-08-28 23:03:01',40,'EXECUTED','8:7da654f67451a0656162ebf3c391d537','loadData tableName=team_player','',NULL,'4.6.1','faker',NULL,'3256580477'),('20230828205825-1','jhipster','config/liquibase/changelog/20230828205825_added_entity_TeamStaffMember.xml','2023-08-28 23:03:01',41,'EXECUTED','8:35ea2cae6cca0db6e3a7bfbed0931462','createTable tableName=team_staff_member','',NULL,'4.6.1',NULL,NULL,'3256580477'),('20230828205825-1-data','jhipster','config/liquibase/changelog/20230828205825_added_entity_TeamStaffMember.xml','2023-08-28 23:03:01',42,'EXECUTED','8:f7e4e11ee68873582702fb77775a2564','loadData tableName=team_staff_member','',NULL,'4.6.1','faker',NULL,'3256580477'),('20230828205826-1','jhipster','config/liquibase/changelog/20230828205826_added_entity_MatchAction.xml','2023-08-28 23:03:01',43,'EXECUTED','8:b600de6f66d349c794694dd16f97c5e1','createTable tableName=match_action','',NULL,'4.6.1',NULL,NULL,'3256580477'),('20230828205826-1-relations','jhipster','config/liquibase/changelog/20230828205826_added_entity_MatchAction.xml','2023-08-28 23:03:01',44,'EXECUTED','8:6168df143f11fda4787ec7ea6160a34a','createTable tableName=rel_match_action__match_player; addPrimaryKey tableName=rel_match_action__match_player','',NULL,'4.6.1',NULL,NULL,'3256580477'),('20230828205826-1-data','jhipster','config/liquibase/changelog/20230828205826_added_entity_MatchAction.xml','2023-08-28 23:03:01',45,'EXECUTED','8:1902c03ad1b595def69f728174c76745','loadData tableName=match_action','',NULL,'4.6.1','faker',NULL,'3256580477'),('20230828205827-1','jhipster','config/liquibase/changelog/20230828205827_added_entity_Sponsor.xml','2023-08-28 23:03:01',46,'EXECUTED','8:421f8a79b188e7fe383e8d50e389c301','createTable tableName=sponsor','',NULL,'4.6.1',NULL,NULL,'3256580477'),('20230828205827-1-data','jhipster','config/liquibase/changelog/20230828205827_added_entity_Sponsor.xml','2023-08-28 23:03:01',47,'EXECUTED','8:e4a8b48c07ee93f1d7808d1a899331aa','loadData tableName=sponsor','',NULL,'4.6.1','faker',NULL,'3256580477'),('20230828205828-1','jhipster','config/liquibase/changelog/20230828205828_added_entity_Competition.xml','2023-08-28 23:03:01',48,'EXECUTED','8:d673ed57a06a67ab06999dafc62b178e','createTable tableName=competition','',NULL,'4.6.1',NULL,NULL,'3256580477'),('20230828205828-1-relations','jhipster','config/liquibase/changelog/20230828205828_added_entity_Competition.xml','2023-08-28 23:03:01',49,'EXECUTED','8:235bafde6ea11ae154d8120cb1cf1316','createTable tableName=rel_competition__team; addPrimaryKey tableName=rel_competition__team; createTable tableName=rel_competition__referee; addPrimaryKey tableName=rel_competition__referee','',NULL,'4.6.1',NULL,NULL,'3256580477'),('20230828205828-1-data','jhipster','config/liquibase/changelog/20230828205828_added_entity_Competition.xml','2023-08-28 23:03:01',50,'EXECUTED','8:26d266aeda813be96cd9efb556e3462c','loadData tableName=competition','',NULL,'4.6.1','faker',NULL,'3256580477'),('20230828205829-1','jhipster','config/liquibase/changelog/20230828205829_added_entity_Matchday.xml','2023-08-28 23:03:01',51,'EXECUTED','8:f124cb0240ba16ecf178071ded88d0b4','createTable tableName=matchday; dropDefaultValue columnName=moment, tableName=matchday','',NULL,'4.6.1',NULL,NULL,'3256580477'),('20230828205829-1-data','jhipster','config/liquibase/changelog/20230828205829_added_entity_Matchday.xml','2023-08-28 23:03:01',52,'EXECUTED','8:ef7186e961e9c4669ec28732c6202152','loadData tableName=matchday','',NULL,'4.6.1','faker',NULL,'3256580477'),('20230828205830-1','jhipster','config/liquibase/changelog/20230828205830_added_entity_Deduction.xml','2023-08-28 23:03:01',53,'EXECUTED','8:19537e7c3ce016ffbe7475184cd53c07','createTable tableName=deduction; dropDefaultValue columnName=moment, tableName=deduction','',NULL,'4.6.1',NULL,NULL,'3256580477'),('20230828205830-1-data','jhipster','config/liquibase/changelog/20230828205830_added_entity_Deduction.xml','2023-08-28 23:03:01',54,'EXECUTED','8:8976c529567a84366d4d1656a8fb9815','loadData tableName=deduction','',NULL,'4.6.1','faker',NULL,'3256580477'),('20230828205831-1','jhipster','config/liquibase/changelog/20230828205831_added_entity_Suspension.xml','2023-08-28 23:03:01',55,'EXECUTED','8:ec131436f4c318e5d30494476de533ae','createTable tableName=suspension; dropDefaultValue columnName=moment, tableName=suspension','',NULL,'4.6.1',NULL,NULL,'3256580477'),('20230828205831-1-data','jhipster','config/liquibase/changelog/20230828205831_added_entity_Suspension.xml','2023-08-28 23:03:02',56,'EXECUTED','8:3a83c77c66fcd08609a4cb422e538613','loadData tableName=suspension','',NULL,'4.6.1','faker',NULL,'3256580477'),('20230828205832-1','jhipster','config/liquibase/changelog/20230828205832_added_entity_Injury.xml','2023-08-28 23:03:02',57,'EXECUTED','8:005b0a5db4aaee49e7c94c59922c1fb2','createTable tableName=injury; dropDefaultValue columnName=moment, tableName=injury; dropDefaultValue columnName=est_comeback_date, tableName=injury','',NULL,'4.6.1',NULL,NULL,'3256580477'),('20230828205832-1-data','jhipster','config/liquibase/changelog/20230828205832_added_entity_Injury.xml','2023-08-28 23:03:02',58,'EXECUTED','8:593a20ea3e01125085be97c08a678c94','loadData tableName=injury','',NULL,'4.6.1','faker',NULL,'3256580477'),('20230828205833-1','jhipster','config/liquibase/changelog/20230828205833_added_entity_Season.xml','2023-08-28 23:03:02',59,'EXECUTED','8:a5213c4af1d1f60d98178053104e139c','createTable tableName=season','',NULL,'4.6.1',NULL,NULL,'3256580477'),('20230828205833-1-data','jhipster','config/liquibase/changelog/20230828205833_added_entity_Season.xml','2023-08-28 23:03:02',60,'EXECUTED','8:07f039a6660c7f0d08cf94780e0ac100','loadData tableName=season','',NULL,'4.6.1','faker',NULL,'3256580477'),('20230828205834-1','jhipster','config/liquibase/changelog/20230828205834_added_entity_SystemConfiguration.xml','2023-08-28 23:03:02',61,'EXECUTED','8:853958d7cb0eac1b6fe82cfffd360954','createTable tableName=system_configuration','',NULL,'4.6.1',NULL,NULL,'3256580477'),('20230828205834-1-data','jhipster','config/liquibase/changelog/20230828205834_added_entity_SystemConfiguration.xml','2023-08-28 23:03:02',62,'EXECUTED','8:1adaad7a25b1dd6f8d9dfe0dadddcc9e','loadData tableName=system_configuration','',NULL,'4.6.1','faker',NULL,'3256580477'),('20230828205835-1','jhipster','config/liquibase/changelog/20230828205835_added_entity_ActionKey.xml','2023-08-28 23:03:02',63,'EXECUTED','8:4f079dffcef13d8503942d5a2b43c81e','createTable tableName=action_key','',NULL,'4.6.1',NULL,NULL,'3256580477'),('20230828205835-1-data','jhipster','config/liquibase/changelog/20230828205835_added_entity_ActionKey.xml','2023-08-28 23:03:02',64,'EXECUTED','8:74466eb87e1bac31b00cc4c802609f12','loadData tableName=action_key','',NULL,'4.6.1','faker',NULL,'3256580477'),('20230828205836-1','jhipster','config/liquibase/changelog/20230828205836_added_entity_Position.xml','2023-08-28 23:03:02',65,'EXECUTED','8:e0565113fabe841de63db95c61808242','createTable tableName=position','',NULL,'4.6.1',NULL,NULL,'3256580477'),('20230828205836-1-relations','jhipster','config/liquibase/changelog/20230828205836_added_entity_Position.xml','2023-08-28 23:03:02',66,'EXECUTED','8:6994ea443e70cc58d03c3a674c2b3e9d','createTable tableName=rel_position__parent; addPrimaryKey tableName=rel_position__parent','',NULL,'4.6.1',NULL,NULL,'3256580477'),('20230828205836-1-data','jhipster','config/liquibase/changelog/20230828205836_added_entity_Position.xml','2023-08-28 23:03:02',67,'EXECUTED','8:ce6789f307a44e43584f72f80cce9783','loadData tableName=position','',NULL,'4.6.1','faker',NULL,'3256580477'),('20230828205837-1','jhipster','config/liquibase/changelog/20230828205837_added_entity_Country.xml','2023-08-28 23:03:02',68,'EXECUTED','8:0a9dcb034b9392d9c640359c7db32262','createTable tableName=country','',NULL,'4.6.1',NULL,NULL,'3256580477'),('20230828205837-1-data','jhipster','config/liquibase/changelog/20230828205837_added_entity_Country.xml','2023-08-28 23:03:02',69,'EXECUTED','8:cd3aa9fb50a1d98bede353eca40ba666','loadData tableName=country','',NULL,'4.6.1','faker',NULL,'3256580477'),('20230828205838-1','jhipster','config/liquibase/changelog/20230828205838_added_entity_GraphicElement.xml','2023-08-28 23:03:02',70,'EXECUTED','8:460792f4a662e91ee11e8652b43c683d','createTable tableName=graphic_element','',NULL,'4.6.1',NULL,NULL,'3256580477'),('20230828205838-1-data','jhipster','config/liquibase/changelog/20230828205838_added_entity_GraphicElement.xml','2023-08-28 23:03:02',71,'EXECUTED','8:b24fe619809a445af649f8836b9e7e80','loadData tableName=graphic_element','',NULL,'4.6.1','faker',NULL,'3256580477'),('20230828205808-2','jhipster','config/liquibase/changelog/20230828205808_added_entity_constraints_Broadcast.xml','2023-08-28 23:03:02',72,'EXECUTED','8:fec00c570346bea829b716dee6d7b629','addForeignKeyConstraint baseTableName=broadcast, constraintName=fk_broadcast__match_id, referencedTableName=jhi_match; addForeignKeyConstraint baseTableName=broadcast, constraintName=fk_broadcast__system_configuration_id, referencedTableName=syste...','',NULL,'4.6.1',NULL,NULL,'3256580477'),('20230828205809-2','jhipster','config/liquibase/changelog/20230828205809_added_entity_constraints_Team.xml','2023-08-28 23:03:02',73,'EXECUTED','8:7d688463aca23d9dfd23530f9eca02af','addForeignKeyConstraint baseTableName=team, constraintName=fk_team__parent_id, referencedTableName=team; addForeignKeyConstraint baseTableName=team, constraintName=fk_team__preferred_formation_id, referencedTableName=formation; addForeignKeyConstr...','',NULL,'4.6.1',NULL,NULL,'3256580477'),('20230828205810-2','jhipster','config/liquibase/changelog/20230828205810_added_entity_constraints_Person.xml','2023-08-28 23:03:02',74,'EXECUTED','8:010dd7c4a3aea879c3f98921d9a20ebc','addForeignKeyConstraint baseTableName=person, constraintName=fk_person__nationality_id, referencedTableName=country; addForeignKeyConstraint baseTableName=person, constraintName=fk_person__birthplace_id, referencedTableName=location','',NULL,'4.6.1',NULL,NULL,'3256580477'),('20230828205811-2','jhipster','config/liquibase/changelog/20230828205811_added_entity_constraints_Player.xml','2023-08-28 23:03:02',75,'EXECUTED','8:3e0ca2177df84d2cf40b0e9396a57c12','addForeignKeyConstraint baseTableName=player, constraintName=fk_player__person_id, referencedTableName=person; addForeignKeyConstraint baseTableName=rel_player__position, constraintName=fk_rel_player__position__player_id, referencedTableName=playe...','',NULL,'4.6.1',NULL,NULL,'3256580477'),('20230828205812-2','jhipster','config/liquibase/changelog/20230828205812_added_entity_constraints_StaffMember.xml','2023-08-28 23:03:02',76,'EXECUTED','8:c09bfa9ccb8e682894234034f92fd058','addForeignKeyConstraint baseTableName=staff_member, constraintName=fk_staff_member__person_id, referencedTableName=person','',NULL,'4.6.1',NULL,NULL,'3256580477'),('20230828205813-2','jhipster','config/liquibase/changelog/20230828205813_added_entity_constraints_Referee.xml','2023-08-28 23:03:02',77,'EXECUTED','8:45460a6606773dd1dd58b2b670d8e04b','addForeignKeyConstraint baseTableName=referee, constraintName=fk_referee__person_id, referencedTableName=person; addForeignKeyConstraint baseTableName=referee, constraintName=fk_referee__association_id, referencedTableName=association','',NULL,'4.6.1',NULL,NULL,'3256580477'),('20230828205814-2','jhipster','config/liquibase/changelog/20230828205814_added_entity_constraints_BroadcastPersonnelMember.xml','2023-08-28 23:03:02',78,'EXECUTED','8:99fc24d2ba335611bc95ef2f388dad28','addForeignKeyConstraint baseTableName=broadcast_personnel_member, constraintName=fk_broadcast_personnel_member__person_id, referencedTableName=person','',NULL,'4.6.1',NULL,NULL,'3256580477'),('20230828205816-2','jhipster','config/liquibase/changelog/20230828205816_added_entity_constraints_Venue.xml','2023-08-28 23:03:02',79,'EXECUTED','8:9fcfa27ede8537b1e7d8f7ede732ba8d','addForeignKeyConstraint baseTableName=venue, constraintName=fk_venue__location_id, referencedTableName=location','',NULL,'4.6.1',NULL,NULL,'3256580477'),('20230828205817-2','jhipster','config/liquibase/changelog/20230828205817_added_entity_constraints_Location.xml','2023-08-28 23:03:02',80,'EXECUTED','8:9e8c50151bed6734261896b0aed6fed2','addForeignKeyConstraint baseTableName=location, constraintName=fk_location__country_id, referencedTableName=country','',NULL,'4.6.1',NULL,NULL,'3256580477'),('20230828205818-2','jhipster','config/liquibase/changelog/20230828205818_added_entity_constraints_Shirt.xml','2023-08-28 23:03:02',81,'EXECUTED','8:24f92e88fbb8f75c8d75c7fce7f64813','addForeignKeyConstraint baseTableName=shirt, constraintName=fk_shirt__team_id, referencedTableName=team; addForeignKeyConstraint baseTableName=shirt, constraintName=fk_shirt__season_id, referencedTableName=season','',NULL,'4.6.1',NULL,NULL,'3256580477'),('20230828205819-2','jhipster','config/liquibase/changelog/20230828205819_added_entity_constraints_Match.xml','2023-08-28 23:03:02',82,'EXECUTED','8:b879a1c007ef69f8167087cbe59ce3bb','addForeignKeyConstraint baseTableName=jhi_match, constraintName=fk_jhi_match__motm_id, referencedTableName=match_player; addForeignKeyConstraint baseTableName=jhi_match, constraintName=fk_jhi_match__home_lineup_id, referencedTableName=lineup; addF...','',NULL,'4.6.1',NULL,NULL,'3256580477'),('20230828205820-2','jhipster','config/liquibase/changelog/20230828205820_added_entity_constraints_MatchPlayer.xml','2023-08-28 23:03:02',83,'EXECUTED','8:25242410694a1d3268b757438c6512a1','addForeignKeyConstraint baseTableName=match_player, constraintName=fk_match_player__team_player_id, referencedTableName=team_player; addForeignKeyConstraint baseTableName=match_player, constraintName=fk_match_player__position_id, referencedTableNa...','',NULL,'4.6.1',NULL,NULL,'3256580477'),('20230828205821-2','jhipster','config/liquibase/changelog/20230828205821_added_entity_constraints_Lineup.xml','2023-08-28 23:03:02',84,'EXECUTED','8:8c1af3c3589d6d52c945cadf44d6fb4b','addForeignKeyConstraint baseTableName=lineup, constraintName=fk_lineup__captain_id, referencedTableName=match_player; addForeignKeyConstraint baseTableName=lineup, constraintName=fk_lineup__dt_id, referencedTableName=team_staff_member; addForeignK...','',NULL,'4.6.1',NULL,NULL,'3256580477'),('20230828205823-2','jhipster','config/liquibase/changelog/20230828205823_added_entity_constraints_TemplateFormation.xml','2023-08-28 23:03:02',85,'EXECUTED','8:6570319aad68664feb531d49205c5c88','addForeignKeyConstraint baseTableName=template_formation, constraintName=fk_template_formation__formation_id, referencedTableName=formation','',NULL,'4.6.1',NULL,NULL,'3256580477'),('20230828205824-2','jhipster','config/liquibase/changelog/20230828205824_added_entity_constraints_TeamPlayer.xml','2023-08-28 23:03:02',86,'EXECUTED','8:20b56bc7981ab3369a005608935afeb1','addForeignKeyConstraint baseTableName=team_player, constraintName=fk_team_player__team_id, referencedTableName=team; addForeignKeyConstraint baseTableName=team_player, constraintName=fk_team_player__player_id, referencedTableName=player','',NULL,'4.6.1',NULL,NULL,'3256580477'),('20230828205825-2','jhipster','config/liquibase/changelog/20230828205825_added_entity_constraints_TeamStaffMember.xml','2023-08-28 23:03:02',87,'EXECUTED','8:7e2027b4ddc0ab15d9900e6aa7910dd0','addForeignKeyConstraint baseTableName=team_staff_member, constraintName=fk_team_staff_member__team_id, referencedTableName=team; addForeignKeyConstraint baseTableName=team_staff_member, constraintName=fk_team_staff_member__staff_member_id, referen...','',NULL,'4.6.1',NULL,NULL,'3256580477'),('20230828205826-2','jhipster','config/liquibase/changelog/20230828205826_added_entity_constraints_MatchAction.xml','2023-08-28 23:03:03',88,'EXECUTED','8:0e4a76412ab0d9e66bb0234c9a4eeff2','addForeignKeyConstraint baseTableName=match_action, constraintName=fk_match_action__match_id, referencedTableName=jhi_match; addForeignKeyConstraint baseTableName=rel_match_action__match_player, constraintName=fk_rel_match_action__match_player__ma...','',NULL,'4.6.1',NULL,NULL,'3256580477'),('20230828205828-2','jhipster','config/liquibase/changelog/20230828205828_added_entity_constraints_Competition.xml','2023-08-28 23:03:03',89,'EXECUTED','8:c23652b5a13dd312c1ca819ff021b357','addForeignKeyConstraint baseTableName=competition, constraintName=fk_competition__parent_id, referencedTableName=competition; addForeignKeyConstraint baseTableName=competition, constraintName=fk_competition__country_id, referencedTableName=country...','',NULL,'4.6.1',NULL,NULL,'3256580477'),('20230828205829-2','jhipster','config/liquibase/changelog/20230828205829_added_entity_constraints_Matchday.xml','2023-08-28 23:03:03',90,'EXECUTED','8:faae529389738afd0c69af169bc4f444','addForeignKeyConstraint baseTableName=matchday, constraintName=fk_matchday__competition_id, referencedTableName=competition','',NULL,'4.6.1',NULL,NULL,'3256580477'),('20230828205830-2','jhipster','config/liquibase/changelog/20230828205830_added_entity_constraints_Deduction.xml','2023-08-28 23:03:03',91,'EXECUTED','8:0d97b2346fc6e63accea4c8ebc95c24f','addForeignKeyConstraint baseTableName=deduction, constraintName=fk_deduction__team_id, referencedTableName=team; addForeignKeyConstraint baseTableName=deduction, constraintName=fk_deduction__competition_id, referencedTableName=competition; addFore...','',NULL,'4.6.1',NULL,NULL,'3256580477'),('20230828205831-2','jhipster','config/liquibase/changelog/20230828205831_added_entity_constraints_Suspension.xml','2023-08-28 23:03:03',92,'EXECUTED','8:43795a93ea6bbe5400bf4c20284bc9d8','addForeignKeyConstraint baseTableName=suspension, constraintName=fk_suspension__person_id, referencedTableName=person; addForeignKeyConstraint baseTableName=suspension, constraintName=fk_suspension__competition_id, referencedTableName=competition;...','',NULL,'4.6.1',NULL,NULL,'3256580477'),('20230828205832-2','jhipster','config/liquibase/changelog/20230828205832_added_entity_constraints_Injury.xml','2023-08-28 23:03:03',93,'EXECUTED','8:405e511c4170ac7c01502c333ad20fab','addForeignKeyConstraint baseTableName=injury, constraintName=fk_injury__player_id, referencedTableName=player; addForeignKeyConstraint baseTableName=injury, constraintName=fk_injury__match_id, referencedTableName=jhi_match','',NULL,'4.6.1',NULL,NULL,'3256580477'),('20230828205834-2','jhipster','config/liquibase/changelog/20230828205834_added_entity_constraints_SystemConfiguration.xml','2023-08-28 23:03:03',94,'EXECUTED','8:8638ceec1e09fd2dfe21b8ccf241dc44','addForeignKeyConstraint baseTableName=system_configuration, constraintName=fk_system_configuration__current_season_id, referencedTableName=season','',NULL,'4.6.1',NULL,NULL,'3256580477'),('20230828205835-2','jhipster','config/liquibase/changelog/20230828205835_added_entity_constraints_ActionKey.xml','2023-08-28 23:03:03',95,'EXECUTED','8:3023b770e7e5089905bd3912602895df','addForeignKeyConstraint baseTableName=action_key, constraintName=fk_action_key__graphic_element_id, referencedTableName=graphic_element','',NULL,'4.6.1',NULL,NULL,'3256580477'),('20230828205836-2','jhipster','config/liquibase/changelog/20230828205836_added_entity_constraints_Position.xml','2023-08-28 23:03:03',96,'EXECUTED','8:759b32eac284cf483063407ed85b3dcd','addForeignKeyConstraint baseTableName=rel_position__parent, constraintName=fk_rel_position__parent__position_id, referencedTableName=position; addForeignKeyConstraint baseTableName=rel_position__parent, constraintName=fk_rel_position__parent__pare...','',NULL,'4.6.1',NULL,NULL,'3256580477');
/*!40000 ALTER TABLE `databasechangelog` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `databasechangeloglock`
--

DROP TABLE IF EXISTS `databasechangeloglock`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `databasechangeloglock` (
  `ID` int(11) NOT NULL,
  `LOCKED` bit(1) NOT NULL,
  `LOCKGRANTED` datetime DEFAULT NULL,
  `LOCKEDBY` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `databasechangeloglock`
--

LOCK TABLES `databasechangeloglock` WRITE;
/*!40000 ALTER TABLE `databasechangeloglock` DISABLE KEYS */;
INSERT INTO `databasechangeloglock` VALUES (1,'\0',NULL,NULL);
/*!40000 ALTER TABLE `databasechangeloglock` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `deduction`
--

DROP TABLE IF EXISTS `deduction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `deduction` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `points` int(11) DEFAULT NULL,
  `moment` datetime,
  `reason` varchar(255) DEFAULT NULL,
  `team_id` bigint(20) NOT NULL,
  `competition_id` bigint(20) NOT NULL,
  `match_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_deduction__team_id` (`team_id`),
  KEY `fk_deduction__competition_id` (`competition_id`),
  KEY `fk_deduction__match_id` (`match_id`),
  CONSTRAINT `fk_deduction__competition_id` FOREIGN KEY (`competition_id`) REFERENCES `competition` (`id`),
  CONSTRAINT `fk_deduction__match_id` FOREIGN KEY (`match_id`) REFERENCES `jhi_match` (`id`),
  CONSTRAINT `fk_deduction__team_id` FOREIGN KEY (`team_id`) REFERENCES `team` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `deduction`
--

LOCK TABLES `deduction` WRITE;
/*!40000 ALTER TABLE `deduction` DISABLE KEYS */;
/*!40000 ALTER TABLE `deduction` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `formation`
--

DROP TABLE IF EXISTS `formation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `formation` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `graphics_name` varchar(255) NOT NULL,
  `detailed_name` varchar(255) DEFAULT NULL,
  `distribution` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `formation`
--

LOCK TABLES `formation` WRITE;
/*!40000 ALTER TABLE `formation` DISABLE KEYS */;
INSERT INTO `formation` VALUES (1,'4-2-3-1','4-2-3-1',''),(2,'4-3-2-1','4-3-2-1',''),(3,'4-1-4-1','4-5-1 (defensivo)',''),(4,'4-4-1-1','4-5-1 (ofensivo)',''),(5,'4-4-2','4-4-2 (línea)',''),(6,'4-1-2-1-2','4-4-2 (rombo)',''),(7,'4-3-1-2','4-4-2 (cerrado)',''),(8,'4-1-3-2','4-1-3-2',''),(9,'4-2-2-2','4-2-2-2',''),(10,'4-3-3','4-3-3 (contención)',''),(11,'4-3-3','4-3-3 (línea)',''),(12,'4-3-3','4-3-3',''),(13,'4-3-3','4-3-3 (ofensivo)',''),(14,'4-1-2-3','4-1-2-3',''),(15,'4-2-4','4-2-4',''),(16,'3-3-3-1','3-3-3-1',''),(17,'3-6-1','3-6-1',''),(18,'3-2-3-2','3-2-3-2',''),(19,'3-1-4-2','3-1-4-2',''),(20,'3-4-1-2','3-4-1-2',''),(21,'3-5-2','3-5-2',''),(22,'3-4-3','3-4-3 (línea)',''),(23,'3-1-2-1-3','3-4-3 (rombo)',''),(24,'3-3-1-3','3-3-1-3',''),(25,'5-4-1','5-4-1',''),(26,'5-3-2','5-3-2 (línea)',''),(27,'5-3-2','5-3-2 (ofensivo)',''),(28,'5-2-3','5-2-3',''),(29,'2-3-5','2-3-5 (Pirámide)',''),(30,'2-3-2-3','2-3-2-3 (Metodo)',''),(31,'3-2-3-2','3-2-3-2 (WW/MM)',''),(32,'3-2-2-3','3-2-2-3 (WM)',''),(33,'3-3-4','3-3-4 (línea)',''),(34,'3-3-4','3-3-4',''),(35,'4-1-5','4-1-5','');
/*!40000 ALTER TABLE `formation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `graphic_element`
--

DROP TABLE IF EXISTS `graphic_element`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `graphic_element` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `code` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `graphic_element`
--

LOCK TABLES `graphic_element` WRITE;
/*!40000 ALTER TABLE `graphic_element` DISABLE KEYS */;
/*!40000 ALTER TABLE `graphic_element` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `injury`
--

DROP TABLE IF EXISTS `injury`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `injury` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `moment` datetime,
  `est_healing_time` varchar(255) DEFAULT NULL,
  `est_comeback_date` datetime,
  `reason` varchar(255) DEFAULT NULL,
  `player_id` bigint(20) NOT NULL,
  `match_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_injury__player_id` (`player_id`),
  KEY `fk_injury__match_id` (`match_id`),
  CONSTRAINT `fk_injury__match_id` FOREIGN KEY (`match_id`) REFERENCES `jhi_match` (`id`),
  CONSTRAINT `fk_injury__player_id` FOREIGN KEY (`player_id`) REFERENCES `player` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `injury`
--

LOCK TABLES `injury` WRITE;
/*!40000 ALTER TABLE `injury` DISABLE KEYS */;
/*!40000 ALTER TABLE `injury` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jhi_authority`
--

DROP TABLE IF EXISTS `jhi_authority`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `jhi_authority` (
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `jhi_authority`
--

LOCK TABLES `jhi_authority` WRITE;
/*!40000 ALTER TABLE `jhi_authority` DISABLE KEYS */;
INSERT INTO `jhi_authority` VALUES ('ROLE_ADMIN'),('ROLE_USER');
/*!40000 ALTER TABLE `jhi_authority` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jhi_match`
--

DROP TABLE IF EXISTS `jhi_match`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `jhi_match` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `moment` datetime,
  `attendance` int(11) DEFAULT NULL,
  `hashtag` varchar(255) DEFAULT NULL,
  `details` varchar(255) DEFAULT NULL,
  `misc_data` varchar(255) DEFAULT NULL,
  `motm_id` bigint(20) DEFAULT NULL,
  `home_lineup_id` bigint(20) DEFAULT NULL,
  `away_lineup_id` bigint(20) DEFAULT NULL,
  `home_team_id` bigint(20) NOT NULL,
  `away_team_id` bigint(20) NOT NULL,
  `venue_id` bigint(20) DEFAULT NULL,
  `match_delegate_id` bigint(20) DEFAULT NULL,
  `home_shirt_id` bigint(20) DEFAULT NULL,
  `away_shirt_id` bigint(20) DEFAULT NULL,
  `matchday_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ux_jhi_match__motm_id` (`motm_id`),
  UNIQUE KEY `ux_jhi_match__home_lineup_id` (`home_lineup_id`),
  UNIQUE KEY `ux_jhi_match__away_lineup_id` (`away_lineup_id`),
  KEY `fk_jhi_match__home_team_id` (`home_team_id`),
  KEY `fk_jhi_match__away_team_id` (`away_team_id`),
  KEY `fk_jhi_match__venue_id` (`venue_id`),
  KEY `fk_jhi_match__match_delegate_id` (`match_delegate_id`),
  KEY `fk_jhi_match__home_shirt_id` (`home_shirt_id`),
  KEY `fk_jhi_match__away_shirt_id` (`away_shirt_id`),
  KEY `fk_jhi_match__matchday_id` (`matchday_id`),
  CONSTRAINT `fk_jhi_match__away_lineup_id` FOREIGN KEY (`away_lineup_id`) REFERENCES `lineup` (`id`),
  CONSTRAINT `fk_jhi_match__away_shirt_id` FOREIGN KEY (`away_shirt_id`) REFERENCES `shirt` (`id`),
  CONSTRAINT `fk_jhi_match__away_team_id` FOREIGN KEY (`away_team_id`) REFERENCES `team` (`id`),
  CONSTRAINT `fk_jhi_match__home_lineup_id` FOREIGN KEY (`home_lineup_id`) REFERENCES `lineup` (`id`),
  CONSTRAINT `fk_jhi_match__home_shirt_id` FOREIGN KEY (`home_shirt_id`) REFERENCES `shirt` (`id`),
  CONSTRAINT `fk_jhi_match__home_team_id` FOREIGN KEY (`home_team_id`) REFERENCES `team` (`id`),
  CONSTRAINT `fk_jhi_match__matchday_id` FOREIGN KEY (`matchday_id`) REFERENCES `matchday` (`id`),
  CONSTRAINT `fk_jhi_match__match_delegate_id` FOREIGN KEY (`match_delegate_id`) REFERENCES `team_staff_member` (`id`),
  CONSTRAINT `fk_jhi_match__motm_id` FOREIGN KEY (`motm_id`) REFERENCES `match_player` (`id`),
  CONSTRAINT `fk_jhi_match__venue_id` FOREIGN KEY (`venue_id`) REFERENCES `venue` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `jhi_match`
--

LOCK TABLES `jhi_match` WRITE;
/*!40000 ALTER TABLE `jhi_match` DISABLE KEYS */;
INSERT INTO `jhi_match` VALUES (1,'2022-09-25 18:00:00',NULL,'#MoguerEgabrense','','',NULL,1,2,1,13,1,6,1,5,1);
/*!40000 ALTER TABLE `jhi_match` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jhi_persistent_token`
--

DROP TABLE IF EXISTS `jhi_persistent_token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `jhi_persistent_token` (
  `series` varchar(20) NOT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `token_value` varchar(20) NOT NULL,
  `token_date` date DEFAULT NULL,
  `ip_address` varchar(39) DEFAULT NULL,
  `user_agent` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`series`),
  KEY `fk_user_persistent_token` (`user_id`),
  CONSTRAINT `fk_user_persistent_token` FOREIGN KEY (`user_id`) REFERENCES `jhi_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `jhi_persistent_token`
--

LOCK TABLES `jhi_persistent_token` WRITE;
/*!40000 ALTER TABLE `jhi_persistent_token` DISABLE KEYS */;
/*!40000 ALTER TABLE `jhi_persistent_token` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jhi_user`
--

DROP TABLE IF EXISTS `jhi_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `jhi_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `login` varchar(50) NOT NULL,
  `password_hash` varchar(60) NOT NULL,
  `first_name` varchar(50) DEFAULT NULL,
  `last_name` varchar(50) DEFAULT NULL,
  `email` varchar(191) DEFAULT NULL,
  `image_url` varchar(256) DEFAULT NULL,
  `activated` bit(1) NOT NULL,
  `lang_key` varchar(10) DEFAULT NULL,
  `activation_key` varchar(20) DEFAULT NULL,
  `reset_key` varchar(20) DEFAULT NULL,
  `created_by` varchar(50) NOT NULL,
  `created_date` timestamp NULL,
  `reset_date` timestamp NULL DEFAULT NULL,
  `last_modified_by` varchar(50) DEFAULT NULL,
  `last_modified_date` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ux_user_login` (`login`),
  UNIQUE KEY `ux_user_email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `jhi_user`
--

LOCK TABLES `jhi_user` WRITE;
/*!40000 ALTER TABLE `jhi_user` DISABLE KEYS */;
INSERT INTO `jhi_user` VALUES (1,'admin','$2a$10$gSAhZrxMllrbgj/kkK9UceBPpChGWJA7SYIb1Mqo.n5aNLq1/oRrC','Administrator','Administrator','admin@localhost','','','es',NULL,NULL,'system',NULL,NULL,'system',NULL),(2,'user','$2a$10$VEjxo0jq2YG9Rbk2HmX9S.k1uZBGYUHdUcid3g/vfiEl7lwWgOH/K','User','User','user@localhost','','','es',NULL,NULL,'system',NULL,NULL,'system',NULL);
/*!40000 ALTER TABLE `jhi_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jhi_user_authority`
--

DROP TABLE IF EXISTS `jhi_user_authority`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `jhi_user_authority` (
  `user_id` bigint(20) NOT NULL,
  `authority_name` varchar(50) NOT NULL,
  PRIMARY KEY (`user_id`,`authority_name`),
  KEY `fk_authority_name` (`authority_name`),
  CONSTRAINT `fk_authority_name` FOREIGN KEY (`authority_name`) REFERENCES `jhi_authority` (`name`),
  CONSTRAINT `fk_user_id` FOREIGN KEY (`user_id`) REFERENCES `jhi_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `jhi_user_authority`
--

LOCK TABLES `jhi_user_authority` WRITE;
/*!40000 ALTER TABLE `jhi_user_authority` DISABLE KEYS */;
INSERT INTO `jhi_user_authority` VALUES (1,'ROLE_ADMIN'),(1,'ROLE_USER'),(2,'ROLE_USER');
/*!40000 ALTER TABLE `jhi_user_authority` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lineup`
--

DROP TABLE IF EXISTS `lineup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `lineup` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `details` varchar(255) DEFAULT NULL,
  `misc_data` varchar(255) DEFAULT NULL,
  `captain_id` bigint(20) DEFAULT NULL,
  `dt_id` bigint(20) DEFAULT NULL,
  `dt2_id` bigint(20) DEFAULT NULL,
  `team_delegate_id` bigint(20) DEFAULT NULL,
  `formation_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ux_lineup__captain_id` (`captain_id`),
  KEY `fk_lineup__dt_id` (`dt_id`),
  KEY `fk_lineup__dt2_id` (`dt2_id`),
  KEY `fk_lineup__team_delegate_id` (`team_delegate_id`),
  KEY `fk_lineup__formation_id` (`formation_id`),
  CONSTRAINT `fk_lineup__captain_id` FOREIGN KEY (`captain_id`) REFERENCES `match_player` (`id`),
  CONSTRAINT `fk_lineup__dt2_id` FOREIGN KEY (`dt2_id`) REFERENCES `team_staff_member` (`id`),
  CONSTRAINT `fk_lineup__dt_id` FOREIGN KEY (`dt_id`) REFERENCES `team_staff_member` (`id`),
  CONSTRAINT `fk_lineup__formation_id` FOREIGN KEY (`formation_id`) REFERENCES `formation` (`id`),
  CONSTRAINT `fk_lineup__team_delegate_id` FOREIGN KEY (`team_delegate_id`) REFERENCES `team_staff_member` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lineup`
--

LOCK TABLES `lineup` WRITE;
/*!40000 ALTER TABLE `lineup` DISABLE KEYS */;
INSERT INTO `lineup` VALUES (1,'','',5,1,NULL,6,NULL),(2,'','',32,8,9,10,NULL);
/*!40000 ALTER TABLE `lineup` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `location`
--

DROP TABLE IF EXISTS `location`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `location` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `graphics_name` varchar(255) DEFAULT NULL,
  `population` int(11) DEFAULT NULL,
  `census_year` int(11) DEFAULT NULL,
  `denonym` varchar(255) DEFAULT NULL,
  `country_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_location__country_id` (`country_id`),
  CONSTRAINT `fk_location__country_id` FOREIGN KEY (`country_id`) REFERENCES `country` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=190 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `location`
--

LOCK TABLES `location` WRITE;
/*!40000 ALTER TABLE `location` DISABLE KEYS */;
INSERT INTO `location` VALUES (1,'Andalucía','',8494155,2022,'Andaluz',1),(2,'Aragón','',1325342,2022,'Aragonés',1),(3,'Principado de Asturias','Asturias',1004499,2022,'Asturiano',1),(4,'Islas Baleares','Baleares',1176254,2022,'Balear, Baleárico',1),(5,'Canarias','',2176412,2022,'Canario, Isleño',1),(6,'Cantabria','',585222,2022,'Cántabro, Montañés, Cantábrico',1),(7,'Castilla y León','',2370064,2022,'Castellanomanchego',1),(8,'Castilla-La Mancha','',2052193,2022,'Castellanoleonés, Castellano y leonés',1),(9,'Cataluña','',7783302,2022,'Catalán',1),(10,'Comunidad Valenciana','',5090839,2022,'Valenciano',1),(11,'Extremadura','',1054245,2022,'Extremeño',1),(12,'Galicia','',2689152,2022,'Gallego',1),(13,'Comunidad de Madrid','Madrid',6744456,2022,'Madrileño',1),(14,'Región de Murcia','Murcia',1531439,2022,'Murciano',1),(15,'Comunidad Foral de Navarra','Navarra',663612,2022,'Navarro',1),(16,'País Vasco','',2207201,2022,'Vasco',1),(17,'La Rioja','',319485,2022,'Riojano',1),(18,'Ceuta','',82566,2022,'Ceutí, Caballa',1),(19,'Melilla','',85159,2022,'Melillense',1),(20,'Andalucía$Almería','',731792,2021,'Almeriense, Urcitano',1),(21,'Andalucía$Cádiz','',1260204,2022,'Gaditano',1),(22,'Andalucía$Córdoba','',781451,2021,'Cordobés',1),(23,'Andalucía$Granada','',921338,2021,'Granadino',1),(24,'Andalucía$Huelva','',528763,2022,'Onubense',1),(25,'Andalucía$Jaén','',627190,2021,'Jienense, Jiennense, Gienense, Giennense, Jaenero, Jaenés, Aurgitano',1),(26,'Andalucía$Málaga','',1717504,2022,'Malagueño, Malacitano',1),(27,'Andalucía$Sevilla','',1963000,2022,'Sevillano, Hispalense',1),(28,'Aragón$Huesca','',225456,2022,'Oscense',1),(29,'Aragón$Teruel','',134545,2021,'Turolense',1),(30,'Aragón$Zaragoza','',967452,2021,'Zaragozano',1),(31,'Canarias$Las Palmas','',1129395,2022,'Grancanarios, Canariones (Las Palmas)\r\nMajorero (Fuerteventura)\r\nLanzaroteño, Conejero (Lanzarote)\r\nGraciosero (La Graciosa)',1),(32,'Canarias$Santa Cruz de Tenerife','',1048306,2022,'Tinerfeño (Tenerife)\r\nLa Palma (Palmero)\r\nHerreño (El Hierro)\r\nGomero (La Gomera)',1),(33,'Castilla y León$Ávila','',158421,2021,'Abulense, Avilés',1),(34,'Castilla y León$Burgos','',358171,2017,'Burgalés',1),(35,'Castilla y León$León','',448179,2022,'Leonés',1),(36,'Castilla y León$Palencia','',158008,2022,'Palentino',1),(37,'Castilla y León$Salamanca','',327338,2021,'Salmantino, Charro',1),(38,'Castilla y León$Segovia','',153803,2022,'Segoviano',1),(39,'Castilla y León$Soria','',88747,2021,'Soriano',1),(40,'Castilla y León$Valladolid','',519361,2021,'Vallisoletano',1),(41,'Castilla y León$Zamora','',167215,2022,'Zamorano',1),(42,'Castilla-La Mancha$Albacete','',386464,2021,'Albaceteño, Albacetense',1),(43,'Castilla-La Mancha$Ciudad Real','',492591,2021,'Ciudadrealeño',1),(44,'Castilla-La Mancha$Cuenca','',195516,2021,'Conquense',1),(45,'Castilla-La Mancha$Guadalajara','',265588,2021,'Guadalajareño',1),(46,'Castilla-La Mancha$Toledo','',686841,2017,'Toledano',1),(47,'Cataluña$Barcelona','',5714730,2021,'Barcelonés',1),(48,'Cataluña$Gerona','',786596,2021,'Gerundense, Gerundés',1),(49,'Cataluña$Lérida','',439727,2021,'Leridano, Ilerdense',1),(50,'Cataluña$Tarragona','',822309,2021,'Tarraconense',1),(51,'Comunidad Valenciana$Alicante','',1901594,2021,'Alicantino',1),(52,'Comunidad Valenciana$Castellón','',587064,2021,'Castellonense',1),(53,'Comunidad Valenciana$Valencia','',2589312,2021,'Valenciano',1),(54,'Extremadura$Badajoz','',669943,2021,'Pacense, Badajocense, Bellotero',1),(55,'Extremadura$Cáceres','',389558,2021,'Cacereño',1),(56,'Galicia$La Coruña','',1120134,2021,'Coruñés',1),(57,'Galicia$Lugo','',326013,2021,'Lucense, Lugués',1),(58,'Galicia$Orense','',304280,2022,'Orensano',1),(59,'Galicia$Pontevedra','',944275,2021,'Pontevedrés',1),(60,'País Vasco$Álava','',334412,2022,'Alavés, Babazorro',1),(61,'País Vasco$Guipúzcoa','',726033,2021,'Guipuzcoano, Gipuzkoano',1),(62,'País Vasco$Vizcaya','',1148302,2017,'Vizcaíno',1),(63,'Andalucía$Huelva$Huelva','',141854,2022,'Onubense, Choquero',1),(64,'Andalucía$Huelva$Alájar','',802,2022,'Alajareño, Alajeño',1),(65,'Andalucía$Huelva$Aljaraque','',22078,2022,'Aljaraqueño',1),(66,'Andalucía$Huelva$El Almendro','',848,2022,'Almendrero',1),(67,'Andalucía$Huelva$Almonaster la Real','',1785,2022,'Almonasterense, Almonazterense',1),(68,'Andalucía$Huelva$Almonte','',25448,2022,'Almonteño',1),(69,'Andalucía$Huelva$Alosno','',3940,2022,'Alosnero',1),(70,'Andalucía$Huelva$Aracena','',8240,2022,'Aracenense, Arundense, Cebollero',1),(71,'Andalucía$Huelva$Aroche','',3024,2022,'Arocheño, Aruccitano, Aruceño',1),(72,'Andalucía$Huelva$Arroyomolinos de León','',951,2022,'Arroyenco',1),(73,'Andalucía$Huelva$Ayamonte','',21725,2022,'Ayamontino',1),(74,'Andalucía$Huelva$Beas','',4465,2022,'Beasino',1),(75,'Andalucía$Huelva$Berrocal','',302,2022,'Berrocaleño',1),(76,'Andalucía$Huelva$Bollullos Par del Condado','',14293,2022,'Bollullero',1),(77,'Andalucía$Huelva$Bonares','',6093,2022,'Bonariego, Bonariense',1),(78,'Andalucía$Huelva$Cabezas Rubias','',705,2022,'Rubiato',1),(79,'Andalucía$Huelva$Cala','',1155,2022,'Caleño, Caliche',1),(80,'Andalucía$Huelva$Calañas','',2734,2022,'Calañés',1),(81,'Andalucía$Huelva$El Campillo','',2018,2022,'Campillero',1),(82,'Andalucía$Huelva$Campofrío','',742,2022,'Campofrieño',1),(83,'Andalucía$Huelva$Cañaveral de León','',393,2022,'Cañetero',1),(84,'Andalucía$Huelva$Cartaya','',20717,2022,'Cartayero',1),(85,'Andalucía$Huelva$Castaño del Robledo','',227,2022,'Castañero',1),(86,'Andalucía$Huelva$El Cerro de Andévalo','',2286,2022,'Cerreño',1),(87,'Andalucía$Huelva$Chucena','',2229,2022,'Chucenero',1),(88,'Andalucía$Huelva$Corteconcepción','',561,2022,'Cortesano',1),(89,'Andalucía$Huelva$Cortegana','',4636,2022,'Corteganés, Corteganeso',1),(90,'Andalucía$Huelva$Cortelazor','',302,2022,'Cortelazoreño',1),(91,'Andalucía$Huelva$Cumbres de En medio','',59,2022,'Cumbreño',1),(92,'Andalucía$Huelva$Cumbres de San Bartolomé','',377,2022,'Cumbreño',1),(93,'Andalucía$Huelva$Cumbres Mayores','',1737,2022,'Cumbreño',1),(94,'Andalucía$Huelva$Encinasola','',1275,2022,'Marocho',1),(95,'Andalucía$Huelva$Escacena del Campo','',2288,2022,'Escacenero, Escacenino',1),(96,'Andalucía$Huelva$Fuenteheridos','',778,2022,'Fuenteheridense, Papero',1),(97,'Andalucía$Huelva$Galaroza','',1373,2022,'Cachonero',1),(98,'Andalucía$Huelva$Gibraleón','',12930,2022,'Olontense, Barrigaverde, Panzurrano',1),(99,'Andalucía$Huelva$La Granada de Riotinto','',254,2022,'Alfiyanco',1),(100,'Andalucía$Huelva$El Granado','',507,2022,'Granaíno',1),(101,'Andalucía$Huelva$Higuera de la Sierra','',1320,2022,'Higuereño',1),(102,'Andalucía$Huelva$Hinojales','',335,2022,'Panzón, Hinojalense',1),(103,'Andalucía$Huelva$Hinojos','',3951,2022,'Hinojero',1),(104,'Andalucía$Huelva$Isla Cristina','',21253,2022,'Isleño',1),(105,'Andalucía$Huelva$Jabugo','',2243,2022,'Jabugueño',1),(106,'Andalucía$Huelva$Lepe','',28617,2022,'Lepero',1),(107,'Andalucía$Huelva$Linares de la Sierra','',276,2022,'Linarense, Chicharrero',1),(108,'Andalucía$Huelva$Lucena del Puerto','',3213,2022,'Lucenero',1),(109,'Andalucía$Huelva$Manzanilla','',2119,2022,'Manzanillero, Mantúo',1),(110,'Andalucía$Huelva$Los Marines','',410,2022,'Marinense',1),(111,'Andalucía$Huelva$Minas de Riotinto','',3738,2022,'Riotinteño',1),(112,'Andalucía$Huelva$Moguer','',22643,2022,'Moguereño',1),(113,'Andalucía$Huelva$La Nava','',247,2022,'Navino',1),(114,'Andalucía$Huelva$Nerva','',5100,2022,'Nervense',1),(115,'Andalucía$Huelva$Niebla','',4196,2022,'Iliplense',1),(116,'Andalucía$Huelva$La Palma del Condado','',10770,2022,'Palmerino',1),(117,'Andalucía$Huelva$Palos de la Frontera','',12483,2022,'Palermo, Palense',1),(118,'Andalucía$Huelva$Paterna del Campo','',3478,2022,'Paternino',1),(119,'Andalucía$Huelva$Paymogo','',1124,2022,'Paymoguero',1),(120,'Andalucía$Huelva$Puebla de Guzmán','',3092,2022,'Puebleño',1),(121,'Andalucía$Huelva$Puerto Moral','',281,2022,'Pertomoraleño, Panzurraco',1),(122,'Andalucía$Huelva$Punta Umbría','',16167,2022,'Puntaumbrieño',1),(123,'Andalucía$Huelva$Rociana del Condado','',7866,2022,'Rocianero',1),(124,'Andalucía$Huelva$Rosal de la Frontera','',1698,2022,'Rosaleño',1),(125,'Andalucía$Huelva$San Bartolomé de la Torre','',3906,2022,'Bartolino',1),(126,'Andalucía$Huelva$San Juan del Puerto','',9532,2022,'Sanjuanero',1),(127,'Andalucía$Huelva$San Silvestre de Guzmán','',659,2022,'Sansilvestrero, Murrón',1),(128,'Andalucía$Huelva$Sanlúcar de Guadiana','',402,2022,'Sanluqueño',1),(129,'Andalucía$Huelva$Santa Ana la Real','',473,2022,'Santanero',1),(130,'Andalucía$Huelva$Santa Bárbara de Casa','',1075,2022,'Santabarbero',1),(131,'Andalucía$Huelva$Santa Olalla del Cala','',2028,2022,'Santaolallero, Maleno',1),(132,'Andalucía$Huelva$Trigueros','',7926,2022,'Triguereño',1),(133,'Andalucía$Huelva$Valdelarco','',239,2022,'Colmenero, Valdelarquino',1),(134,'Andalucía$Huelva$Valverde del Camino','',12721,2022,'Valverdeño',1),(135,'Andalucía$Huelva$Villablanca','',2945,2022,'Villablanquero, Murrón',1),(136,'Andalucía$Huelva$Villalba del Alcor','',3316,2022,'Villalbero',1),(137,'Andalucía$Huelva$Villanueva de las Cruces','',377,2022,'Cruceño',1),(138,'Andalucía$Huelva$Villanueva de los Castillejos','',2923,2022,'Castillejero',1),(139,'Andalucía$Huelva$Villarrasa','',2190,2022,'Villarrasero',1),(140,'Andalucía$Huelva$Zalamea la Real','',3026,2022,'Zalameño',1),(141,'Andalucía$Huelva$La Zarza-Perrunal','',1230,2022,'Zarceño (La Zarza)\r\nPerrunalero (Perrunal)',1),(142,'Andalucía$Huelva$Zufre','',774,2022,'Zufreño, Moclino',1),(143,'Andalucía$Huelva$Almonte$Matalascañas|Torre de la Higuera','',2450,2018,'Matalascañese',1),(144,'Andalucía$Huelva$Alosno$Tharsis','',1727,2019,'Tharsileño',1),(145,'Andalucía$Huelva$Jabugo$El Repilado','',NULL,NULL,'',1),(146,'Andalucía$Huelva$La Zarza-Perrunal$La Zarza','',NULL,NULL,'Zarceño',1),(147,'Andalucía$Huelva$La Zarza-Perrunal$Perrunal','',NULL,NULL,'Perrunalero',1),(148,'Andalucía$Huelva$Moguer&Palos de la Frontera$Mazagón','',4152,NULL,'Mazagonense',1),(149,'Andalucía$Huelva$Puebla de Guzmán$Las Herrerías','',NULL,NULL,'',1),(150,'Andalucía$Cádiz$Cádiz','',113066,2022,'Gaditano',1),(151,'Andalucía$Cádiz$Arcos de la Frontera','',30953,2022,'Arcense',1),(152,'Andalucía$Cádiz$Chiclana de la Frontera','',87493,2022,'Chiclanero',1),(153,'Andalucía$Cádiz$Los Barrios','',24069,2022,'Barreño, Barrioqueño',1),(154,'Andalucía$Cádiz$Sanlúcar de Barrameda','',69723,2022,'Sanluqueño',1),(155,'Andalucía$Cádiz$Arcos de la Frontera$Jédula','',2490,NULL,'Jedulense',1),(156,'Andalucía$Cádiz$Los Barrios$Los Cortijillos','',2636,2009,'',1),(157,'Andalucía$Córdoba$Córdoba','',319515,2022,'Cordobés, Cordubense, Cortubí, Patricense',1),(158,'Andalucía$Córdoba$Cabra','',20097,2022,'Egabrense',1),(159,'Andalucía$Córdoba$Montilla','',22490,2022,'Montillano',1),(160,'Andalucía$Córdoba$Palma del Río','',20810,2022,'Palmeño',1),(161,'Andalucía$Sevilla$Sevilla','',681998,2022,'Sevillano, Hispalense',1),(162,'Andalucía$Sevilla$Las Cabezas de San Juan','',16386,2022,'Cabecense, Cabeceño',1),(163,'Andalucía$Sevilla$Castilleja de la Cuesta','',17230,2022,'Alixeño, Castillejano',1),(164,'Andalucía$Sevilla$Isla Mayor','',5767,2022,'Isleño',1),(165,'Andalucía$Sevilla$Lebrija','',27665,2022,'Lebrijano, Nebricense, Nebrijano',1),(166,'Andalucía$Sevilla$Tomares','',25341,2022,'Tomareño',1),(167,'Andalucía$Sevilla$El Viso del Alcor','',19161,2022,'Visueño',1),(168,'Andalucía$Almería$Almería','',199237,2022,'Almeriense, Urcitano',1),(169,'Andalucía$Almería$Berja','',12807,2022,'Virgitano',1),(170,'Andalucía$Almería$Cantoria','',3533,2022,'Cantoriano',1),(171,'Andalucía$Almería$El Ejido','',87500,2022,'Ejidense',1),(172,'Andalucía$Granada$Granada','',228682,2022,'Granadino, Granadí, Iliberitano',1),(173,'Andalucía$Granada$Albolote','',19199,2022,'Alboloteño',1),(174,'Andalucía$Granada$Almuñécar','',26748,2022,'Sexitano, Almuñequero',1),(175,'Andalucía$Granada$Atarfe','',19452,2022,'Atarfeño',1),(176,'Andalucía$Granada$Albolote$Parque del Cubillas','',645,2012,'',1),(177,'Andalucía$Jaén$Jaén','',111669,2022,'Jienense, Jiennense, Gienense, Giennense, Jeanero, Jaenés, Aurgitano',1),(178,'Andalucía$Jaén$Martos','',24329,2022,'Marteño, Tuccitano',1),(179,'Andalucía$Jaén$Vilches','',4317,2022,'Vilcheño',1),(180,'Andalucía$Jaén$Villacarrillo','',10484,2022,'Villacarrillense, Campiñés',1),(181,'Andalucía$Málaga$Málaga','',579076,2022,'Malagueño, Malacitano',1),(182,'Andalucía$Málaga$Alhaurín de la Torre','',42531,2022,'Alhaurino',1),(183,'Andalucía$Málaga$Alhaurín el Grande','',26436,2022,'Alhaurino',1),(184,'Andalucía$Málaga$Casabermeja','',3813,2022,'Casabermejeño, Bermejo',1),(185,'Andalucía$Málaga$Marbella','',150725,2022,'Marbellí',1),(186,'Andalucía$Málaga$Mijas','',89502,2022,'Mijeño, Chichilargo',1),(187,'Andalucía$Málaga$Rincón de la Victoria','',50569,2022,'Rinconero',1),(188,'Andalucía$Málaga$Marbella$San Pedro Alcántara','',36384,2019,'Sampedreño',1),(189,'Andalucía$Málaga$Mijas$Las Lagunas','',43961,2012,'Lagunero',1);
/*!40000 ALTER TABLE `location` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `match_action`
--

DROP TABLE IF EXISTS `match_action`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `match_action` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `timestamp` varchar(255) DEFAULT NULL,
  `details` varchar(255) DEFAULT NULL,
  `misc_data` varchar(255) DEFAULT NULL,
  `match_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_match_action__match_id` (`match_id`),
  CONSTRAINT `fk_match_action__match_id` FOREIGN KEY (`match_id`) REFERENCES `jhi_match` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `match_action`
--

LOCK TABLES `match_action` WRITE;
/*!40000 ALTER TABLE `match_action` DISABLE KEYS */;
/*!40000 ALTER TABLE `match_action` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `match_player`
--

DROP TABLE IF EXISTS `match_player`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `match_player` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `shirt_number` int(11) DEFAULT NULL,
  `is_youth` int(11) DEFAULT NULL,
  `is_warned` int(11) DEFAULT NULL,
  `misc_data` varchar(255) DEFAULT NULL,
  `team_player_id` bigint(20) NOT NULL,
  `position_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_match_player__team_player_id` (`team_player_id`),
  KEY `fk_match_player__position_id` (`position_id`),
  CONSTRAINT `fk_match_player__position_id` FOREIGN KEY (`position_id`) REFERENCES `position` (`id`),
  CONSTRAINT `fk_match_player__team_player_id` FOREIGN KEY (`team_player_id`) REFERENCES `team_player` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `match_player`
--

LOCK TABLES `match_player` WRITE;
/*!40000 ALTER TABLE `match_player` DISABLE KEYS */;
INSERT INTO `match_player` VALUES (1,1,NULL,0,'',2,1),(2,4,NULL,0,'',3,6),(3,5,NULL,0,'',11,15),(4,6,NULL,0,'',13,16),(5,7,NULL,0,'',19,22),(6,8,NULL,0,'',16,20),(7,9,NULL,0,'',21,25),(8,10,NULL,0,'',17,20),(9,11,NULL,0,'',16,16),(10,13,NULL,0,'',1,1),(11,14,NULL,0,'',12,15),(12,15,NULL,0,'',6,19),(13,17,NULL,0,'',9,11),(14,18,NULL,0,'',5,7),(15,20,NULL,0,'',4,6),(16,21,NULL,0,'',20,25),(17,22,NULL,0,'',18,24),(18,23,NULL,0,'',22,17),(19,1,NULL,0,'',26,1),(20,7,NULL,0,'',27,4),(21,8,NULL,0,'',28,2),(22,11,NULL,0,'',29,3),(23,14,NULL,0,'',30,3),(24,15,NULL,0,'',31,2),(25,17,NULL,0,'',32,2),(26,20,NULL,0,'',33,3),(27,25,NULL,0,'',34,3),(28,27,NULL,0,'',35,3),(29,32,NULL,0,'',36,4),(30,13,NULL,0,'',37,1),(31,3,NULL,0,'',38,4),(32,4,NULL,0,'',39,2),(33,5,NULL,0,'',40,2),(34,9,NULL,0,'',41,4),(35,18,NULL,0,'',42,2),(36,22,NULL,0,'',43,3);
/*!40000 ALTER TABLE `match_player` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `matchday`
--

DROP TABLE IF EXISTS `matchday`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `matchday` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `graphics_name` varchar(255) DEFAULT NULL,
  `moment` datetime,
  `details` varchar(255) DEFAULT NULL,
  `misc_data` varchar(255) DEFAULT NULL,
  `competition_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_matchday__competition_id` (`competition_id`),
  CONSTRAINT `fk_matchday__competition_id` FOREIGN KEY (`competition_id`) REFERENCES `competition` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `matchday`
--

LOCK TABLES `matchday` WRITE;
/*!40000 ALTER TABLE `matchday` DISABLE KEYS */;
INSERT INTO `matchday` VALUES (1,'Div. Honor 22/23: J1','Jornada 1',NULL,'','',1),(2,'Div. Honor 22/23: J2','Jornada 2',NULL,'','',1),(3,'Div. Honor 22/23: J3','Jornada 3',NULL,'','',1),(4,'Div. Honor 22/23: J4','Jornada 4',NULL,'','',1),(5,'Div. Honor 22/23: J5','Jornada 5',NULL,'','',1),(6,'Div. Honor 22/23: J6','Jornada 6',NULL,'','',1),(7,'Div. Honor 22/23: J7','Jornada 7',NULL,'','',1),(8,'Div. Honor 22/23: J8','Jornada 8',NULL,'','',1),(9,'Div. Honor 22/23: J9','Jornada 9',NULL,'','',1),(10,'Div. Honor 22/23: J10','Jornada 10',NULL,'','',1),(11,'Div. Honor 22/23: J11','Jornada 11',NULL,'','',1),(12,'Div. Honor 22/23: J12','Jornada 12',NULL,'','',1),(13,'Div. Honor 22/23: J13','Jornada 13',NULL,'','',1),(14,'Div. Honor 22/23: J14','Jornada 14',NULL,'','',1),(15,'Div. Honor 22/23: J15','Jornada 15',NULL,'','',1),(16,'Div. Honor 22/23: J16','Jornada 16',NULL,'','',1),(17,'Div. Honor 22/23: J17','Jornada 17',NULL,'','',1),(18,'Div. Honor 22/23: J18','Jornada 18',NULL,'','',1),(19,'Div. Honor 22/23: J19','Jornada 19',NULL,'','',1),(20,'Div. Honor 22/23: J20','Jornada 20',NULL,'','',1),(21,'Div. Honor 22/23: J21','Jornada 21',NULL,'','',1),(22,'Div. Honor 22/23: J22','Jornada 22',NULL,'','',1),(23,'Div. Honor 22/23: J23','Jornada 23',NULL,'','',1),(24,'Div. Honor 22/23: J24','Jornada 24',NULL,'','',1),(25,'Div. Honor 22/23: J25','Jornada 25',NULL,'','',1),(26,'Div. Honor 22/23: J26','Jornada 26',NULL,'','',1),(27,'Div. Honor 22/23: J27','Jornada 27',NULL,'','',1),(28,'Div. Honor 22/23: J28','Jornada 28',NULL,'','',1),(29,'Div. Honor 22/23: J29','Jornada 29',NULL,'','',1),(30,'Div. Honor 22/23: J30','Jornada 30',NULL,'','',1);
/*!40000 ALTER TABLE `matchday` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `person`
--

DROP TABLE IF EXISTS `person`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `person` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `middle_name` varchar(255) DEFAULT NULL,
  `surname_1` varchar(255) DEFAULT NULL,
  `surname_2` varchar(255) DEFAULT NULL,
  `nicknames` varchar(255) DEFAULT NULL,
  `graphics_name` varchar(255) NOT NULL,
  `long_graphics_name` varchar(255) DEFAULT NULL,
  `callnames` varchar(255) DEFAULT NULL,
  `birth_date` varchar(255) DEFAULT NULL,
  `death_date` varchar(255) DEFAULT NULL,
  `medium_shot_photo` longblob,
  `medium_shot_photo_content_type` varchar(255) DEFAULT NULL,
  `full_shot_photo` longblob,
  `full_shot_photo_content_type` varchar(255) DEFAULT NULL,
  `social_media` varchar(255) DEFAULT NULL,
  `details` varchar(255) DEFAULT NULL,
  `misc_data` varchar(255) DEFAULT NULL,
  `nationality_id` bigint(20) DEFAULT NULL,
  `birthplace_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_person__nationality_id` (`nationality_id`),
  KEY `fk_person__birthplace_id` (`birthplace_id`),
  CONSTRAINT `fk_person__birthplace_id` FOREIGN KEY (`birthplace_id`) REFERENCES `location` (`id`),
  CONSTRAINT `fk_person__nationality_id` FOREIGN KEY (`nationality_id`) REFERENCES `country` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=78 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `person`
--

LOCK TABLES `person` WRITE;
/*!40000 ALTER TABLE `person` DISABLE KEYS */;
INSERT INTO `person` VALUES (1,'Jesús','','Gascó','','','Jesús Gascó','Jesús Gascó','Jesús Gascó','','','../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png','','','',1,112),(2,'José Antonio','','Domínguez','Gómez','','José Antonio Domínguez','José Antonio Domínguez','José Antonio','','','../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png','','','',1,112),(3,'José Antonio','','Domínguez','Gómez','Piti','José Antonio Domínguez Jr.','José Antonio Domínguez Jr.','José Antonio','','','../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png','','','',1,112),(4,'Arturo','','Cordero','López','','Arturo Cordero','Arturo Cordero','Arturo Cordero','1994$10$20','','../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png','','','',1,112),(5,'José María','','Lobo','Méndez','','Lobo','José María Lobo','Lobo','','','../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png','','','',1,132),(6,'Alberto','','Fernández','Vílchez','','Alberto Fernández','Alberto Fernández','Alberto Fernández','','','../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png','','','',1,NULL),(7,'Antonio José','','Maya','Padilla','','Antonio Maya','Antonio Maya','Maya','','','../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png','','','',1,63),(8,'Francisco','','López','Vega','Franillo','Franillo','Francisco López \"Franillo\"','Franillo','','','../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png','','','',1,112),(9,'Juan José','','Coronel','Domínguez','Juanjo','Juanjo Coronel','Juanjo Coronel','Juanjo Coronel','1988$6$10','','../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png','','','',1,77),(10,'Jaime','','Terán','González','','Jaime Terán','Jaime Terán','Terán','','','../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png','','','',1,NULL),(11,'Ezequiel','','Castilla','González','','Ezequiel','Ezequiel Castilla','Ezequiel','','','../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png','','','',1,112),(12,'Juan Carlos','','Cerpa','González','','Cerpa','Juan Carlos Cerpa','Cerpa','2001$11$7','','../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png','','','',1,117),(13,'Manuel','','Rodríguez','Rojas','Nene','Nene','Manuel Rodríguez \"Nene\"','Nene','1993$11$17','','../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png','','','',1,63),(14,'Javier','','Sánchez','Serrano','Javi','Javi Sánchez','Javi Sánchez','Javi Sánchez','','','../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png','','','',1,63),(15,'Mohamed Mady','','Bangoura','','','Bangoura','Mohamed Mady Bangoura','Bangoura','','','../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png','','','',1,NULL),(16,'Akram','','Bekar','','','Akram','Akram Bekar','Akram','','','../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png','','','',1,NULL),(17,'Alaghie','','Ndong','','Alai','Alai','Alaghie Ndong \"Alai\"','Alai','','','../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png','','','',1,NULL),(18,'José','','Cárdenas','López','Pepe, Pepito','Pepe Cárdenas','Pepe Cárdenas','Pepe Cárdenas','','','../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png','','','',1,63),(19,'Carlos','','Palacios','Hernández','','Carlos Palacios','Carlos Palacios','Carlos Palacios','2001$3$21','','../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png','','','',1,63),(20,'José Ángel','','Raposo','Mora','Xavi','Xavi','José Ángel Raposo \"Xavi\"','Xavi','','','../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png','','','',1,112),(21,'Eduardo','','Soriano','Ruiz','Edu','Edu Soriano','Edu Soriano','Edu Soriano','','','../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png','','','',1,112),(22,'Braulio','','Garrido','Muñoz','Wayo','Wayo','Braulio Garrido \"Wayo\"','Wayo','','','../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png','','','',1,112),(23,'Jesús','','Garrido','Flores','Biri','Biri','Jesús Garrido \"Biri\"','Biri','1997$1$14','','../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png','','','',1,63),(24,'Manuel Sebastián','','Domínguez','Expósito','Mele','Mele','Manuel Sebastián Domínguez \"Mele\"','Mele','1994$2$15','','../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png','','','',1,77),(25,'Alejandro','','Márquez','Conde','Ale','Ale Márquez','Ale Márquez','Ale Márquez','','','../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png','','','',1,112),(26,'Jesús','','Prieto','Prieto','','Prieto','Jesús Prieto','Prieto','','','../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png','','','',1,112),(27,'Pedro','','Ruiz','Vega','','Pedro Ruiz','Pedro Ruiz','Pedro Ruiz','','','../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png','','','',1,112),(28,'Alan','','Mora','López','','Alan','Alan Mora','Alan','','','../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png','','','',1,NULL),(29,'Francisco','','García','Pérez','','Fran García','Fran García','Fran García','','','../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png','','','',1,NULL),(30,'Julio','','Cordero','Pérez','','Julio Cordero','Julio Cordero','Julio Cordero','','','../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png','','','',1,112),(31,'Julio','','Ponce','Santos','','Julio Ponce','Julio Ponce','Julio Ponce','','','../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png','','','',1,NULL),(32,'Andrés','','Franco','Postigo','','Andrés','Andrés Franco','Andrés','','','../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png','','','',1,112),(33,'Adolfo','','Muñoz','Gómez','','Adolfo','Adolfo Muñoz','Adolfo','','','../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png','','','',1,NULL),(34,'José Manuel','','Rueda','Pérez','','Rueda','José Manuel Rueda','Rueda','','','../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png','','','',1,NULL),(35,'Fernando','','Azcárate','Martín','','Fernando Azcárate','Fernando Azcárate','Fernando Azcárate','1987$12$1','','../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png','','','',1,112),(36,'Juan Manuel','','Guerrero','Palma','Juan Palma','Juan Palma','Juan Manuel Guerrero','Juan Palma','','','../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png','','','',1,84),(37,'Eloy','','Bando','Gómez','','Eloy Bando','Eloy Bando','Eloy Bando','1991','','../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png','','','',1,74),(38,'Isabel María','','Garrido','Cruzado','','Isabel Garrido','Isabel Garrido','Isabel Garrido','','','../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png','','','',1,NULL),(39,'Demetrio','','Pérez','González','Deme','Deme','Demetrio Pérez','Demetrio Pérez','','','../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png','','','',1,126),(40,'Pablo','','Ruiz','Rodríguez','','Pablo Ruiz','Pablo Ruiz','Pablo Ruiz','','','../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png','','','',1,NULL),(41,'Israel','','Domínguez','Pichardo','Isra','Isra','Israel Domínguez','Israel Domínguez','','','../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png','','','',1,112),(42,'Álvaro','','García','Calvo','','García Calvo','Álvaro García Calvo','García Calvo','','','../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png','','','',1,NULL),(43,'Álvaro','','Fernández','Lozano','','Fernández Lozano','Álvaro Fernández Lozano','Fernández Lozano','','','../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png','','','',1,NULL),(44,'Adrián','','Olmo','Martín','','Olmo Martín','Adrián Olmo Martín','Olmo Martín','','','../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png','','','',1,NULL),(45,'Enrique','','Heredia','Díaz','','Heredia Díaz','Enrique Heredia Díaz','Heredia Díaz','','','../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png','','','',1,NULL),(46,'Martín','','Nieto','Pinteño','','Nieto Pinteño','Martín Nieto Pinteño','Nieto Pinteño','','','../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png','','','',1,NULL),(47,'Ignacio de Juan','','Meynet','Cabrera','','Meynet Cabrera','Ignacio de Juan Meynet Cabrera','Meynet Cabrera','','','../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png','','','',1,NULL),(48,'Víctor','','Bazán','Muñoz','','Bazán Muñoz','Víctor Bazán Muñoz','Bazán Muñoz','','','../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png','','','',1,NULL),(49,'Jaime','','González','Barrera','','González Barrera','Jaime González Barrera','González Barrera','','','../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png','','','',1,NULL),(50,'Alberto','','Triano','Blanco','','Triano Blanco','Alberto Triano Blanco','Triano Blanco','','','../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png','','','',1,NULL),(51,'José Ignacio','','Cámara','Molina','','Cámara Molina','José Ignacio Cámara Molina','Cámara Molina','','','../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png','','','',1,NULL),(52,'María Andrea','','López','Jiménez','','López Jiménez','María Andrea López Jiménez','López Jiménez','','','../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png','','','',1,NULL),(53,'Luis Miguel','','Vargas','Cabello','','Vargas Cabello','Luis Miguel Vargas Cabello','Vargas Cabello','','','../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png','','','',1,NULL),(54,'Alejandro','','Romero','Llamas','','Romero Llamas','Alejandro Romero Llamas','Romero Llamas','','','../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png','','','',1,NULL),(55,'Miguel Ángel','','Pereira','Gálvez','','Pereira Gálvez','Miguel Ángel Pereira Gálvez','Pereira Gálvez','','','../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png','','','',1,NULL),(56,'Francisco Javier','','Rodríguez','Díaz','','Rodríguez Díaz','Francisco Javier Rodríguez Díaz','Rodríguez Díaz','','','../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png','','','',1,NULL),(57,'Iván','','Berral','Suárez','','Iván','Iván Berral','Iván','','','../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png','','','',1,NULL),(58,'Antonio Jesús','','Aguilera','Aguilera','Chiqui','Chiqui','Antonio Jesús Aguilera \"Chiqui\"','Chiqui','','','../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png','','','',1,NULL),(59,'Ignacio','','Amo','Sánchez','Nacho','Nacho','Nacho Amo','Nacho','','','../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png','','','',1,157),(60,'Ariel Essau','','Escobar','Ponce','','Ariel','Ariel Essau','Ariel','','','../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png','','','',1,NULL),(61,'Rafael','','Cuenca','Cejudo','Rafa','Rafa Cuenca','Rafa Cuenca','Rafa Cuenca','','','../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png','','','',1,NULL),(62,'Sie Seydou','','Kambire','','','Sie Seydou','Sie Seydou Kambire','Sie Seydou','','','../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png','','','',1,NULL),(63,'Alejandro','','Carmona','Burón','','Carmona','Alejandro Carmona','Carmona','','','../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png','','','',1,NULL),(64,'Rafael Tomás','','Roldán','Aguilera','Rafa, Nomo','Nomo','Rafael Tomás Roldán \"Nomo\"','Nomo','','','../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png','','','',1,NULL),(65,'Raúl','','Mesa','Jiménez','','Raúl Mesa','Raúl Mesa','Raúl Mesa','','','../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png','','','',1,NULL),(66,'Juan Carlos','','Osuna','Córdoba','','Osuna','Juan Carlos Osuna','Osuna','','','../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png','','','',1,NULL),(67,'Miguel','','Cumplido','Mesa','Cazuelas, Cazu','Cazuelas','Miguel Cumplido \"Cazuelas\"','Cazuelas','','','../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png','','','',1,NULL),(68,'Rafael','','Roldán','Rascón','','Kiki','Rafael Roldán \"Kiki\"','Kiki','','','../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png','','','',1,NULL),(69,'Alejandro','','Vílchez','Luna','','Vílchez','Alejandro Vílchez','Vílchez','','','../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png','','','',1,NULL),(70,'Francisco Javier','','Pavón','Sánchez','Javi','Javi Pavón','Javi Pavón','Javi Pavón','','','../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png','','','',1,NULL),(71,'Juan Manuel','','Sánchez','Pérez','Juanma','Juanma','Juanma Sánchez','Juanma','','','../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png','','','',1,NULL),(72,'Antonio José','','Calvo','Jiménez','Anto','Anto','Antonio José Calvo \"Anto\"','Anto','','','../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png','','','',1,NULL),(73,'Francisco José','','Cañete','Ramírez','','Cañete','Francisco José Cañete','Cañete','','','../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png','','','',1,NULL),(74,'Alejandro','','Castro','Écija','Álex','Álex Castro','Álex Castro','Álex Castro','','','../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png','','','',1,NULL),(75,'Francisco Javier','','Sedano','Reyes','Fran','Fran Sedano','Francisco Javier Sedano','Fran Sedano','','','../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png','','','',1,NULL),(76,'Miguel','','Ocaña','Flores','','Miguel Ocaña','Miguel Ocaña','Miguel Ocaña','','','../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png','','','',1,NULL),(77,'José Antonio','','Sánchez','Muñoz','','José Antonio Sánchez','José Antonio Sánchez','José Antonio Sánchez','','','../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png','','','',1,NULL);
/*!40000 ALTER TABLE `person` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `player`
--

DROP TABLE IF EXISTS `player`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `player` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `graphics_name` varchar(255) NOT NULL,
  `long_graphics_name` varchar(255) DEFAULT NULL,
  `shirt_name` varchar(255) DEFAULT NULL,
  `height` int(11) DEFAULT NULL,
  `weight` int(11) DEFAULT NULL,
  `stronger_foot` varchar(255) DEFAULT NULL,
  `preferred_side` varchar(255) DEFAULT NULL,
  `contract_until` varchar(255) DEFAULT NULL,
  `retirement_date` varchar(255) DEFAULT NULL,
  `misc_data` varchar(255) DEFAULT NULL,
  `person_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ux_player__person_id` (`person_id`),
  CONSTRAINT `fk_player__person_id` FOREIGN KEY (`person_id`) REFERENCES `person` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=50 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `player`
--

LOCK TABLES `player` WRITE;
/*!40000 ALTER TABLE `player` DISABLE KEYS */;
INSERT INTO `player` VALUES (1,'Arturo Cordero','Arturo Cordero','',NULL,NULL,NULL,NULL,'','','',4),(2,'Lobo','José María Lobo','',NULL,NULL,NULL,NULL,'','','',5),(3,'Alberto Fernández','Alberto Fernández','',NULL,NULL,NULL,NULL,'','','',6),(4,'Maya','Antonio Maya','',NULL,NULL,NULL,NULL,'','','',7),(5,'Franillo','Francisco López \"Franillo\"','',NULL,NULL,NULL,NULL,'','','',8),(6,'Juanjo Coronel','Juanjo Coronel','',NULL,NULL,NULL,NULL,'','','',9),(7,'Terán','Jaime Terán','',NULL,NULL,NULL,NULL,'','','',10),(8,'Ezequiel','Ezequiel Castilla','',NULL,NULL,NULL,NULL,'','','',11),(9,'Cerpa','Juan Carlos Cerpa','',NULL,NULL,NULL,NULL,'','','',12),(10,'Nene','Manuel Rodríguez \"Nene\"','',NULL,NULL,NULL,NULL,'','','',13),(11,'Javi Sánchez','Javi Sánchez','',NULL,NULL,NULL,NULL,'','','',14),(12,'Bangoura','Mohamed Mady Bangoura','',NULL,NULL,NULL,NULL,'','','',15),(13,'Akram','Akram Bekar','',NULL,NULL,NULL,NULL,'','','',16),(14,'Alai','Alaghie Ndong \"Alai\"','',NULL,NULL,NULL,NULL,'','','',17),(15,'Pepe Cárdenas','Pepe Cárdenas','',NULL,NULL,NULL,NULL,'','','',18),(16,'Carlos Palacios','Carlos Palacios','',NULL,NULL,NULL,NULL,'','','',19),(17,'Xavi','José Ángel Raposo \"Xavi\"','',NULL,NULL,NULL,NULL,'','','',20),(18,'Edu Soriano','Edu Soriano','',NULL,NULL,NULL,NULL,'','','',21),(19,'Wayo','Braulio Garrido \"Wayo\"','',NULL,NULL,NULL,NULL,'','','',22),(20,'Biri','Jesús Garrido \"Biri\"','',NULL,NULL,NULL,NULL,'','','',23),(21,'Mele','Manuel Sebastián Domínguez \"Mele\"','',NULL,NULL,NULL,NULL,'','','',24),(22,'Ale Márquez','Ale Márquez','',NULL,NULL,NULL,NULL,'','','',25),(23,'Prieto','Jesús Prieto','',NULL,NULL,NULL,NULL,'','','',26),(24,'Pedro Ruiz','Pedro Ruiz','',NULL,NULL,NULL,NULL,'','','',27),(25,'Alan','Alan Mora','',NULL,NULL,NULL,NULL,'','','',28),(26,'Fran García','Fran García','',NULL,NULL,NULL,NULL,'','','',29),(27,'Julio Cordero','Julio Cordero','',NULL,NULL,NULL,NULL,'','','',30),(28,'Julio Ponce','Julio Ponce','',NULL,NULL,NULL,NULL,'','','',31),(29,'Andrés','Andrés Franco','',NULL,NULL,NULL,NULL,'','','',32),(30,'Adolfo','Adolfo Muñoz','',NULL,NULL,NULL,NULL,'','','',33),(31,'Rueda','José Manuel Rueda','',NULL,NULL,NULL,NULL,'','','',34),(32,'Iván','Iván Berral','',NULL,NULL,NULL,NULL,'','','',57),(33,'Chiqui','Antonio Jesús Aguilera \"Chiqui\"','',NULL,NULL,NULL,NULL,'','','',58),(34,'Nacho','Nacho Amo','',NULL,NULL,NULL,NULL,'','','',59),(35,'Ariel','Ariel Essau','',NULL,NULL,NULL,NULL,'','','',60),(36,'Rafa Cuenca','Rafa Cuenca','',NULL,NULL,NULL,NULL,'','','',61),(37,'Sie Seydou','Sie Seydou Kambire','',NULL,NULL,NULL,NULL,'','','',62),(38,'Carmona','Alejandro Carmona','',NULL,NULL,NULL,NULL,'','','',63),(39,'Nomo','Rafael Tomás Roldán \"Nomo\"','',NULL,NULL,NULL,NULL,'','','',64),(40,'Raúl Mesa','Raúl Mesa','',NULL,NULL,NULL,NULL,'','','',65),(41,'Osuna','Juan Carlos Osuna','',NULL,NULL,NULL,NULL,'','','',66),(42,'Cazuelas','Miguel Cumplido \"Cazuelas\"','',NULL,NULL,NULL,NULL,'','','',67),(43,'Kiki','Rafael Roldán \"Kiki\"','',NULL,NULL,NULL,NULL,'','','',68),(44,'Vílchez','Alejandro Vílchez','',NULL,NULL,NULL,NULL,'','','',69),(45,'Javi Pavón','Javi Pavón','',NULL,NULL,NULL,NULL,'','','',70),(46,'Juanma','Juanma Sánchez','',NULL,NULL,NULL,NULL,'','','',71),(47,'Anto','Antonio José Calvo \"Anto\"','',NULL,NULL,NULL,NULL,'','','',72),(48,'Cañete','Francisco José Cañete','',NULL,NULL,NULL,NULL,'','','',73),(49,'Álex Castro','Álex Castro','',NULL,NULL,NULL,NULL,'','','',74);
/*!40000 ALTER TABLE `player` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `position`
--

DROP TABLE IF EXISTS `position`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `position` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `abb` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `position`
--

LOCK TABLES `position` WRITE;
/*!40000 ALTER TABLE `position` DISABLE KEYS */;
INSERT INTO `position` VALUES (1,'Portero','PT'),(2,'Defensa','DEF'),(3,'Centrocampista','CC'),(4,'Delantero','DEL'),(5,'Líbero','LIB'),(6,'Central','CT'),(7,'Central Derecho','CTD'),(8,'Central Izquierdo','CTI'),(9,'Lateral','LAT'),(10,'Lateral Derecho','LD'),(11,'Lateral Izquierdo','LI'),(12,'Carrilero','CRR'),(13,'Carrilero Derecho','CRD'),(14,'Carrilero Izquierdo','CRI'),(15,'Mediocentro Defensivo','MCD'),(16,'Mediocentro','MC'),(17,'Interior','INT'),(18,'Interior Derecho','ID'),(19,'Interior Izquierdo','II'),(20,'Mediapunta','MP'),(21,'Extremo','EXT'),(22,'Extremo Derecho','ED'),(23,'Extremo Izquierdo','EI'),(24,'Segunda Punta','SP'),(25,'Delantero Centro','DC');
/*!40000 ALTER TABLE `position` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `referee`
--

DROP TABLE IF EXISTS `referee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `referee` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `graphics_name` varchar(255) NOT NULL,
  `long_graphics_name` varchar(255) DEFAULT NULL,
  `retirement_date` varchar(255) DEFAULT NULL,
  `misc_data` varchar(255) DEFAULT NULL,
  `person_id` bigint(20) NOT NULL,
  `association_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ux_referee__person_id` (`person_id`),
  KEY `fk_referee__association_id` (`association_id`),
  CONSTRAINT `fk_referee__association_id` FOREIGN KEY (`association_id`) REFERENCES `association` (`id`),
  CONSTRAINT `fk_referee__person_id` FOREIGN KEY (`person_id`) REFERENCES `person` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `referee`
--

LOCK TABLES `referee` WRITE;
/*!40000 ALTER TABLE `referee` DISABLE KEYS */;
INSERT INTO `referee` VALUES (1,'García Calvo','Álvaro García Calvo','','',42,2),(2,'Fernández Lozano','Álvaro Fernández Lozano','','',43,2),(3,'Olmo Martín','Adrián Olmo Martín','','',44,2),(4,'Heredia Díaz','Enrique Heredia Díaz','','',45,3),(5,'Nieto Pinteño','Martín Nieto Pinteño','','',46,3),(6,'Meynet Cabrera','Ignacio de Juan Meynet Cabrera','','',47,3),(7,'Bazán Muñoz','Víctor Bazán Muñoz','','',48,3),(8,'González Barrera','Jaime González Barrera','','',49,3),(9,'Triano Blanco','Alberto Triano Blanco','','',50,3),(10,'Cámara Molina','José Ignacio Cámara Molina','','',51,2),(11,'López Jiménez','María Andrea López Jiménez','','',52,2),(12,'Vargas Cabello','Luis Miguel Vargas Cabello','','',53,2),(13,'Romero Llamas','Alejandro Romero Llamas','','',54,3),(14,'Pereira Gálvez','Miguel Ángel Pereira Gálvez','','',55,3),(15,'Rodríguez Díaz','Francisco Javier Rodríguez Díaz','','',56,3);
/*!40000 ALTER TABLE `referee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rel_broadcast__broadcast_personnel_member`
--

DROP TABLE IF EXISTS `rel_broadcast__broadcast_personnel_member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rel_broadcast__broadcast_personnel_member` (
  `broadcast_personnel_member_id` bigint(20) NOT NULL,
  `broadcast_id` bigint(20) NOT NULL,
  PRIMARY KEY (`broadcast_id`,`broadcast_personnel_member_id`),
  KEY `fk_rel_broadcast__broadcast_per__broadcast_personnel_membe_98_id` (`broadcast_personnel_member_id`),
  CONSTRAINT `fk_rel_broadcast__broadcast_personnel_member__broadcast_id` FOREIGN KEY (`broadcast_id`) REFERENCES `broadcast` (`id`),
  CONSTRAINT `fk_rel_broadcast__broadcast_per__broadcast_personnel_membe_98_id` FOREIGN KEY (`broadcast_personnel_member_id`) REFERENCES `broadcast_personnel_member` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rel_broadcast__broadcast_personnel_member`
--

LOCK TABLES `rel_broadcast__broadcast_personnel_member` WRITE;
/*!40000 ALTER TABLE `rel_broadcast__broadcast_personnel_member` DISABLE KEYS */;
INSERT INTO `rel_broadcast__broadcast_personnel_member` VALUES (1,1),(2,1),(3,1);
/*!40000 ALTER TABLE `rel_broadcast__broadcast_personnel_member` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rel_competition__referee`
--

DROP TABLE IF EXISTS `rel_competition__referee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rel_competition__referee` (
  `referee_id` bigint(20) NOT NULL,
  `competition_id` bigint(20) NOT NULL,
  PRIMARY KEY (`competition_id`,`referee_id`),
  KEY `fk_rel_competition__referee__referee_id` (`referee_id`),
  CONSTRAINT `fk_rel_competition__referee__competition_id` FOREIGN KEY (`competition_id`) REFERENCES `competition` (`id`),
  CONSTRAINT `fk_rel_competition__referee__referee_id` FOREIGN KEY (`referee_id`) REFERENCES `referee` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rel_competition__referee`
--

LOCK TABLES `rel_competition__referee` WRITE;
/*!40000 ALTER TABLE `rel_competition__referee` DISABLE KEYS */;
INSERT INTO `rel_competition__referee` VALUES (1,1),(2,1),(3,1),(4,1),(5,1),(6,1),(7,1),(8,1),(9,1),(10,1),(11,1),(12,1),(13,1),(14,1),(15,1);
/*!40000 ALTER TABLE `rel_competition__referee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rel_competition__team`
--

DROP TABLE IF EXISTS `rel_competition__team`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rel_competition__team` (
  `team_id` bigint(20) NOT NULL,
  `competition_id` bigint(20) NOT NULL,
  PRIMARY KEY (`competition_id`,`team_id`),
  KEY `fk_rel_competition__team__team_id` (`team_id`),
  CONSTRAINT `fk_rel_competition__team__competition_id` FOREIGN KEY (`competition_id`) REFERENCES `competition` (`id`),
  CONSTRAINT `fk_rel_competition__team__team_id` FOREIGN KEY (`team_id`) REFERENCES `team` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rel_competition__team`
--

LOCK TABLES `rel_competition__team` WRITE;
/*!40000 ALTER TABLE `rel_competition__team` DISABLE KEYS */;
INSERT INTO `rel_competition__team` VALUES (1,1),(5,1),(6,1),(7,1),(8,1),(9,1),(10,1),(11,1),(12,1),(13,1),(14,1),(15,1),(16,1),(17,1),(18,1),(19,1);
/*!40000 ALTER TABLE `rel_competition__team` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rel_jhi_match__referee`
--

DROP TABLE IF EXISTS `rel_jhi_match__referee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rel_jhi_match__referee` (
  `referee_id` bigint(20) NOT NULL,
  `jhi_match_id` bigint(20) NOT NULL,
  PRIMARY KEY (`jhi_match_id`,`referee_id`),
  KEY `fk_rel_jhi_match__referee__referee_id` (`referee_id`),
  CONSTRAINT `fk_rel_jhi_match__referee__jhi_match_id` FOREIGN KEY (`jhi_match_id`) REFERENCES `jhi_match` (`id`),
  CONSTRAINT `fk_rel_jhi_match__referee__referee_id` FOREIGN KEY (`referee_id`) REFERENCES `referee` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rel_jhi_match__referee`
--

LOCK TABLES `rel_jhi_match__referee` WRITE;
/*!40000 ALTER TABLE `rel_jhi_match__referee` DISABLE KEYS */;
INSERT INTO `rel_jhi_match__referee` VALUES (1,1),(2,1),(3,1);
/*!40000 ALTER TABLE `rel_jhi_match__referee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rel_lineup__match_player`
--

DROP TABLE IF EXISTS `rel_lineup__match_player`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rel_lineup__match_player` (
  `match_player_id` bigint(20) NOT NULL,
  `lineup_id` bigint(20) NOT NULL,
  PRIMARY KEY (`lineup_id`,`match_player_id`),
  KEY `fk_rel_lineup__match_player__match_player_id` (`match_player_id`),
  CONSTRAINT `fk_rel_lineup__match_player__lineup_id` FOREIGN KEY (`lineup_id`) REFERENCES `lineup` (`id`),
  CONSTRAINT `fk_rel_lineup__match_player__match_player_id` FOREIGN KEY (`match_player_id`) REFERENCES `match_player` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rel_lineup__match_player`
--

LOCK TABLES `rel_lineup__match_player` WRITE;
/*!40000 ALTER TABLE `rel_lineup__match_player` DISABLE KEYS */;
INSERT INTO `rel_lineup__match_player` VALUES (1,1),(2,1),(3,1),(4,1),(5,1),(6,1),(7,1),(8,1),(9,1),(10,1),(11,1),(12,1),(13,1),(14,1),(15,1),(16,1),(17,1),(18,1),(19,2),(20,2),(21,2),(22,2),(23,2),(24,2),(25,2),(26,2),(27,2),(28,2),(29,2),(30,2),(31,2),(32,2),(33,2),(34,2),(35,2),(36,2);
/*!40000 ALTER TABLE `rel_lineup__match_player` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rel_match_action__match_player`
--

DROP TABLE IF EXISTS `rel_match_action__match_player`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rel_match_action__match_player` (
  `match_player_id` bigint(20) NOT NULL,
  `match_action_id` bigint(20) NOT NULL,
  PRIMARY KEY (`match_action_id`,`match_player_id`),
  KEY `fk_rel_match_action__match_player__match_player_id` (`match_player_id`),
  CONSTRAINT `fk_rel_match_action__match_player__match_action_id` FOREIGN KEY (`match_action_id`) REFERENCES `match_action` (`id`),
  CONSTRAINT `fk_rel_match_action__match_player__match_player_id` FOREIGN KEY (`match_player_id`) REFERENCES `match_player` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rel_match_action__match_player`
--

LOCK TABLES `rel_match_action__match_player` WRITE;
/*!40000 ALTER TABLE `rel_match_action__match_player` DISABLE KEYS */;
/*!40000 ALTER TABLE `rel_match_action__match_player` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rel_player__position`
--

DROP TABLE IF EXISTS `rel_player__position`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rel_player__position` (
  `position_id` bigint(20) NOT NULL,
  `player_id` bigint(20) NOT NULL,
  PRIMARY KEY (`player_id`,`position_id`),
  KEY `fk_rel_player__position__position_id` (`position_id`),
  CONSTRAINT `fk_rel_player__position__player_id` FOREIGN KEY (`player_id`) REFERENCES `player` (`id`),
  CONSTRAINT `fk_rel_player__position__position_id` FOREIGN KEY (`position_id`) REFERENCES `position` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rel_player__position`
--

LOCK TABLES `rel_player__position` WRITE;
/*!40000 ALTER TABLE `rel_player__position` DISABLE KEYS */;
INSERT INTO `rel_player__position` VALUES (1,1),(1,2),(1,27),(1,29),(1,30),(1,32),(1,43),(2,26),(2,34),(2,37),(2,38),(2,45),(2,46),(2,48),(3,28),(3,35),(3,36),(3,39),(3,40),(3,41),(3,44),(3,49),(4,25),(4,33),(4,42),(4,47),(6,3),(6,4),(6,5),(6,6),(6,7),(6,8),(8,5),(8,6),(8,7),(8,8),(9,6),(9,9),(9,10),(13,11),(13,12),(14,12),(14,13),(14,17),(14,22),(15,17),(16,14),(16,15),(16,20),(16,22),(17,10),(17,22),(18,12),(18,14),(18,16),(18,17),(18,18),(18,19),(18,22),(20,14),(20,15),(20,19),(20,20),(20,23),(21,23),(21,31),(22,15),(22,18),(22,19),(22,20),(22,23),(22,24),(22,31),(23,19),(23,20),(23,21),(23,24),(23,31),(24,19),(25,19);
/*!40000 ALTER TABLE `rel_player__position` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rel_position__parent`
--

DROP TABLE IF EXISTS `rel_position__parent`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rel_position__parent` (
  `parent_id` bigint(20) NOT NULL,
  `position_id` bigint(20) NOT NULL,
  PRIMARY KEY (`position_id`,`parent_id`),
  KEY `fk_rel_position__parent__parent_id` (`parent_id`),
  CONSTRAINT `fk_rel_position__parent__parent_id` FOREIGN KEY (`parent_id`) REFERENCES `position` (`id`),
  CONSTRAINT `fk_rel_position__parent__position_id` FOREIGN KEY (`position_id`) REFERENCES `position` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rel_position__parent`
--

LOCK TABLES `rel_position__parent` WRITE;
/*!40000 ALTER TABLE `rel_position__parent` DISABLE KEYS */;
INSERT INTO `rel_position__parent` VALUES (2,5),(2,6),(2,7),(2,8),(2,9),(3,12),(3,15),(3,16),(3,17),(3,20),(4,21),(4,24),(4,25),(9,10),(9,11),(12,13),(12,14),(17,18),(17,19),(21,22),(21,23);
/*!40000 ALTER TABLE `rel_position__parent` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rel_team__venue`
--

DROP TABLE IF EXISTS `rel_team__venue`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rel_team__venue` (
  `venue_id` bigint(20) NOT NULL,
  `team_id` bigint(20) NOT NULL,
  PRIMARY KEY (`team_id`,`venue_id`),
  KEY `fk_rel_team__venue__venue_id` (`venue_id`),
  CONSTRAINT `fk_rel_team__venue__team_id` FOREIGN KEY (`team_id`) REFERENCES `team` (`id`),
  CONSTRAINT `fk_rel_team__venue__venue_id` FOREIGN KEY (`venue_id`) REFERENCES `venue` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rel_team__venue`
--

LOCK TABLES `rel_team__venue` WRITE;
/*!40000 ALTER TABLE `rel_team__venue` DISABLE KEYS */;
INSERT INTO `rel_team__venue` VALUES (1,1),(2,7),(3,8),(4,9),(5,10),(6,11),(7,12),(8,13),(9,14),(10,15),(11,16),(12,17),(13,18),(14,19),(15,20),(16,21),(17,8),(18,17),(19,9);
/*!40000 ALTER TABLE `rel_team__venue` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `season`
--

DROP TABLE IF EXISTS `season`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `season` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `graphics_name` varchar(255) DEFAULT NULL,
  `start_date` date DEFAULT NULL,
  `end_date` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `season`
--

LOCK TABLES `season` WRITE;
/*!40000 ALTER TABLE `season` DISABLE KEYS */;
INSERT INTO `season` VALUES (1,'Temporada 2020/21','','2020-07-01','2021-06-30'),(2,'Temporada 2021/22','','2021-07-01','2022-06-30'),(3,'Temporada 2022/23','','2022-07-01','2023-06-30');
/*!40000 ALTER TABLE `season` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `shirt`
--

DROP TABLE IF EXISTS `shirt`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `shirt` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `colour_1` varchar(255) DEFAULT NULL,
  `colour_2` varchar(255) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `model` longblob,
  `model_content_type` varchar(255) DEFAULT NULL,
  `photo` longblob,
  `photo_content_type` varchar(255) DEFAULT NULL,
  `team_id` bigint(20) DEFAULT NULL,
  `season_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_shirt__team_id` (`team_id`),
  KEY `fk_shirt__season_id` (`season_id`),
  CONSTRAINT `fk_shirt__season_id` FOREIGN KEY (`season_id`) REFERENCES `season` (`id`),
  CONSTRAINT `fk_shirt__team_id` FOREIGN KEY (`team_id`) REFERENCES `team` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shirt`
--

LOCK TABLES `shirt` WRITE;
/*!40000 ALTER TABLE `shirt` DISABLE KEYS */;
INSERT INTO `shirt` VALUES (1,'0d41ba','ede640',2,'../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png',1,2),(2,'9e140f','',1,'../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png',1,2),(3,'ede640','',1,'../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png',1,1),(4,'ffffff','6c00ab',2,'../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png',1,1),(5,'cc0000','ffffff',3,'../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png',13,3),(6,'ffffff','',1,'../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png',9,3),(7,'cc0000','',1,'../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png',15,3),(8,'ffee00','ccbe00',2,'../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png',18,3),(9,'ffee00','000000',2,'../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png',20,3),(10,'000000','cc0000',2,'../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png',10,3),(11,'ffffff','000000',2,'../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png',11,3),(12,'cc0000','ffffff',3,'../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png',7,3),(13,'cc0000','0000cc',3,'../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png',38,2),(14,'cc0000','0000cc',2,'../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png',39,2),(15,'00000','ede640',2,'../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png',39,2),(16,'ffffff','000000',2,'../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png',41,2),(17,'ffffff','000000',2,'../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png',45,2),(18,'ffffff','0a6bcc',2,'../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png',42,2),(19,'002999','',1,'../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png',44,2),(20,'009957','',1,'../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png',44,2),(21,'cc0000','0',2,'../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png',49,2),(22,'cc0000','ffffff',3,'../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png',51,2),(23,'002999','ffffff',2,'../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png',51,2),(24,'009957','ffffff',2,'../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png',55,2),(25,'2988cc','',1,'../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png',46,2),(26,'0d41ba','ede640',2,'../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png',54,2),(27,'ffffff','',1,'../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png',54,2),(28,'ffffff','cc0000',2,'../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png',50,2),(29,'ffffff','',2,'../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png',50,2),(30,'cc0000','0000cc',3,'../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png',56,2),(31,'cc0000','',1,'../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png',56,2),(32,'cc0000','',1,'../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png',47,1);
/*!40000 ALTER TABLE `shirt` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sponsor`
--

DROP TABLE IF EXISTS `sponsor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sponsor` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `graphics_name` varchar(255) DEFAULT NULL,
  `logo` longblob,
  `logo_content_type` varchar(255) DEFAULT NULL,
  `monoc_logo` longblob,
  `monoc_logo_content_type` varchar(255) DEFAULT NULL,
  `details` varchar(255) DEFAULT NULL,
  `misc_data` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sponsor`
--

LOCK TABLES `sponsor` WRITE;
/*!40000 ALTER TABLE `sponsor` DISABLE KEYS */;
INSERT INTO `sponsor` VALUES (1,'Moguer Televisión, SL','Moguer Televisión','../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png','','');
/*!40000 ALTER TABLE `sponsor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `staff_member`
--

DROP TABLE IF EXISTS `staff_member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `staff_member` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `graphics_name` varchar(255) NOT NULL,
  `long_graphics_name` varchar(255) DEFAULT NULL,
  `default_role` varchar(255) DEFAULT NULL,
  `contract_until` varchar(255) DEFAULT NULL,
  `retirement_date` varchar(255) DEFAULT NULL,
  `misc_data` varchar(255) DEFAULT NULL,
  `person_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ux_staff_member__person_id` (`person_id`),
  CONSTRAINT `fk_staff_member__person_id` FOREIGN KEY (`person_id`) REFERENCES `person` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `staff_member`
--

LOCK TABLES `staff_member` WRITE;
/*!40000 ALTER TABLE `staff_member` DISABLE KEYS */;
INSERT INTO `staff_member` VALUES (1,'Fernando Azcárate','Fernando Azcárate','DT','','','',35),(2,'Juan Palma','Juan Manuel Guerrero','DT','','','',36),(3,'Eloy Bando','Eloy Bando','DT2','','','',37),(4,'Isabel Garrido','Isabel Garrido','PRESIDENT','','','',38),(5,'Deme','Demetrio Pérez','BOARD_MEMBER','','','',39),(6,'Pablo Ruiz','Pablo Ruiz','TEAM_DELEGATE','','','',40),(7,'Isra','Israel Domínguez','MATCH_DELEGATE','','','',41),(8,'Fran Sedano','Francisco Javier Sedano','DT','','','',75),(9,'Miguel Ocaña','Miguel Ocaña','DT2','','','',76),(10,'José Antonio Sánchez','José Antonio Sánchez','TEAM_DELEGATE','','','',77);
/*!40000 ALTER TABLE `staff_member` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `suspension`
--

DROP TABLE IF EXISTS `suspension`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `suspension` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `matches` int(11) DEFAULT NULL,
  `moment` datetime,
  `reason` varchar(255) DEFAULT NULL,
  `person_id` bigint(20) NOT NULL,
  `competition_id` bigint(20) NOT NULL,
  `match_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_suspension__person_id` (`person_id`),
  KEY `fk_suspension__competition_id` (`competition_id`),
  KEY `fk_suspension__match_id` (`match_id`),
  CONSTRAINT `fk_suspension__competition_id` FOREIGN KEY (`competition_id`) REFERENCES `competition` (`id`),
  CONSTRAINT `fk_suspension__match_id` FOREIGN KEY (`match_id`) REFERENCES `jhi_match` (`id`),
  CONSTRAINT `fk_suspension__person_id` FOREIGN KEY (`person_id`) REFERENCES `person` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `suspension`
--

LOCK TABLES `suspension` WRITE;
/*!40000 ALTER TABLE `suspension` DISABLE KEYS */;
/*!40000 ALTER TABLE `suspension` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `system_configuration`
--

DROP TABLE IF EXISTS `system_configuration`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `system_configuration` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `misc_data` varchar(255) DEFAULT NULL,
  `current_season_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ux_system_configuration__current_season_id` (`current_season_id`),
  CONSTRAINT `fk_system_configuration__current_season_id` FOREIGN KEY (`current_season_id`) REFERENCES `season` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `system_configuration`
--

LOCK TABLES `system_configuration` WRITE;
/*!40000 ALTER TABLE `system_configuration` DISABLE KEYS */;
INSERT INTO `system_configuration` VALUES (1,'',3);
/*!40000 ALTER TABLE `system_configuration` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `team`
--

DROP TABLE IF EXISTS `team`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `team` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `abb` varchar(255) NOT NULL,
  `graphics_name` varchar(255) DEFAULT NULL,
  `short_name` varchar(255) DEFAULT NULL,
  `nicknames` varchar(255) DEFAULT NULL,
  `establishment_date` varchar(255) DEFAULT NULL,
  `badge` longblob,
  `badge_content_type` varchar(255) DEFAULT NULL,
  `monoc_badge` longblob,
  `monoc_badge_content_type` varchar(255) DEFAULT NULL,
  `details` varchar(255) DEFAULT NULL,
  `misc_data` varchar(255) DEFAULT NULL,
  `parent_id` bigint(20) DEFAULT NULL,
  `preferred_formation_id` bigint(20) DEFAULT NULL,
  `location_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_team__parent_id` (`parent_id`),
  KEY `fk_team__preferred_formation_id` (`preferred_formation_id`),
  KEY `fk_team__location_id` (`location_id`),
  CONSTRAINT `fk_team__location_id` FOREIGN KEY (`location_id`) REFERENCES `location` (`id`),
  CONSTRAINT `fk_team__parent_id` FOREIGN KEY (`parent_id`) REFERENCES `team` (`id`),
  CONSTRAINT `fk_team__preferred_formation_id` FOREIGN KEY (`preferred_formation_id`) REFERENCES `formation` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=57 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `team`
--

LOCK TABLES `team` WRITE;
/*!40000 ALTER TABLE `team` DISABLE KEYS */;
INSERT INTO `team` VALUES (1,'Club Deportivo Moguer','MOG','CD Moguer','Moguer','','1922','../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png','','',NULL,27,112),(2,'Real Club Recreativo de Huelva, SAD','REC','Recreativo de Huelva','Recre','','1989$12$23','../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png','','',NULL,NULL,63),(3,'Club Deportivo San Roque de Lepe, SAD','SRO','CD San Roque de Lepe','San Roque','','1956$10$31','../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png','','',NULL,NULL,106),(4,'Club Polideportivo El Ejido 1969','PEJ','CP El Ejido 1969','Poli Ejido 1969','','2012','../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png','','',NULL,NULL,171),(5,'Ayamonte Club de Fútbol','AYA','Ayamonte CF','Ayamonte','','1924$5$24','../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png','','',NULL,NULL,73),(6,'Bollullos Club de Fútbol','BOL','Bollullos CF','Bollullos','','1933','../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png','','',NULL,NULL,76),(7,'Algaida Unión Deportiva','ALG','Algaida UD','Algaida','','1973','../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png','','',NULL,NULL,154),(8,'Arcos Club de Fútbol','ARC','Arcos CF','Arcos','','1956','../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png','','',NULL,NULL,151),(9,'Castilleja Club de Fútbol','CAS','Castilleja CF','Castilleja','','1929','../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png','','',NULL,NULL,163),(10,'Club Deportivo Cabecense','CAB','CD Cabecense','Cabecense','','1942','../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png','','',NULL,NULL,162),(11,'Club Atlético Central','ATC','Atlético Central','Central','','2018$8$1','../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png','','',NULL,NULL,161),(12,'Chiclana Club de Fútbol','CHI','Chiclana CF','Chiclana','','1948','../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png','','',NULL,NULL,152),(13,'Club Deportivo Egabrense','EGA','CD Egabrense','Egabrense','','1921','../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png','','',NULL,NULL,158),(14,'La Palma Club de Fútbol','LPA','La Palma CF','La Palma','','1915','../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png','','',NULL,NULL,116),(15,'Montilla Club de Fútbol','MON','Montilla CF','Montilla','','1973$8$1','../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png','','',NULL,NULL,159),(16,'Unión Balompédica Lebrijana','LEB','UB Lebrijana','Lebrijana','','1928','../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png','','',NULL,NULL,165),(17,'Unión Deportiva Los Barrios','LBA','UD Los Barrios','Los Barrios','','1993','../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png','','',NULL,NULL,153),(18,'Atlético Palma del Río','ATP','Atlético Palma del Río','Palma del Río','','1977','../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png','','',NULL,NULL,160),(19,'Unión Deportiva Tomares','TOM','UD Tomares','Tomares','','1976','../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png','','',NULL,NULL,166),(20,'Villafranco Club de Fútbol','VIL','Viilafranco CF','Villafranco','','1968','../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png','','',NULL,NULL,164),(21,'Unión Polideportiva Viso','VIS','UP Viso','Viso','','1975','../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png','','',NULL,NULL,167),(22,'Alhaurin de la Torre Club de Fútbol','AHT','Alhaurín de la Torre CF','Alhaurín de la Torre','','1969$12$4','../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png','','',NULL,NULL,182),(23,'Club Deportivo Alhaurino','AHG','CD Alhaurino','Alhaurino','','1908$9$17','../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png','','',NULL,NULL,183),(24,'Club Deportivo Almuñécar City','ALM','CD Almuñécar City','Almuñécar','','2018$6$1','../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png','','',NULL,NULL,174),(25,'AOVE Villacarrillo Club de Fútbol','AVC','AOVE Villacarrillo CF','AOVE Villacarrillo','','2006','../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png','','',NULL,NULL,180),(26,'Atarfe Industrial Club de Fútbol','ATA','Atarfe Industrial CF','Atarfe','','1930$11$30','../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png','','',NULL,NULL,175),(27,'Berja Club de Fútbol','BER','Berja CF','Berja','','2013','../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png','','',3,NULL,169),(28,'Club Deportivo Cantoria 2017 Fútbol Club','CAN','CD Cantoria 2017 FC','Cantoria 2017','','2017','../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png','','',NULL,NULL,170),(29,'Club Deportivo Casabermeja','CAS','CD Casabermeja','Casabermeja','','1978','../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png','','',NULL,NULL,184),(30,'Fútbol Club Cubillas de Albolote','CUB','FC Cubillas de Albolote','Cubillas','','2012','../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png','','',NULL,NULL,176),(31,'Martos Club Deportivo','MAR','Martos CD','Martos','','1970','../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png','','',NULL,NULL,178),(32,'Club Polideportivo Mijas Las Lagunas','MIJ','CP Mijas Las Lagunas','Mijas - Las Lagunas','','1991','../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png','','',NULL,NULL,189),(33,'Unión Deportiva Pavía','PAV','UD Pavía','Pavía','','1955$7$25','../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png','','',NULL,NULL,168),(34,'Club Polideportivo Almería','ALM','CP Almería','Poli Almería','','1983$7$9','../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png','','',NULL,NULL,168),(35,'Club Deportivo Rincón de la Victoria','RIN','CD Rincón','Rincón','','1964$7$7','../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png','','',NULL,NULL,187),(36,'Unión Deportiva San Pedro','SPE','UD San Pedro','San Pedro','','1974$2$7','../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png','','',NULL,NULL,188),(37,'Club Deportivo Vilches','VIL','CD Vilches','Vilches','','1970','../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png','','',NULL,NULL,179),(38,'Agrupación Deportiva Almonte Balompié','ALM','AD Almonte Balompié','Almonte','','1985','../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png','','',NULL,NULL,68),(39,'Aroche Club de Fútbol','ARO','Aroche CF','Aroche','','1927','../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png','','',NULL,NULL,71),(40,'Real Club Recreativo de Huelva \"B\"','ATO','Atlético Onubense','Atlético Onubense','','1960','../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png','','',1,NULL,63),(41,'Beas Club de Fútbol','BEA','Beas CF','Beas','','2009','../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png','','',NULL,NULL,74),(42,'Club Deportivo Bonares','BON','CD Bonares','Bonares','','2012','../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png','','',NULL,NULL,77),(43,'Club Atlético Calañas','CAL','Atlético Calañas','Calañas','','1997','../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png','','',NULL,NULL,80),(44,'Club Deportivo Canela','CAN','CD Canela','Canela','','1974','../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png','','',NULL,NULL,73),(45,'Club Atlético Cruceño','CRU','Atlético Cruceño','Cruceño','','1987','../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png','','',NULL,NULL,137),(46,'Isla Cristina Fútbol Club','ISL','Isla Cristina FC','Isla Cristina','','1999','../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png','','',NULL,NULL,104),(47,'Club Deportivo Pinzón','PIN','CD Pinzón','Pinzón','','1966','../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png','','',NULL,NULL,117),(48,'Club Deportivo Punta Umbría','PUN','CD Punta Umbría','Punta Umbría','','1968','../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png','','',NULL,NULL,122),(49,'Club Deportivo Repilado','REP','CD Repilado','Repilado','','1932','../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png','','',NULL,NULL,145),(50,'Riotinto Balompié','RIO','Riotinto Balompié','Riotinto','','1914','../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png','','',NULL,NULL,111),(51,'Club Deportivo Rociana','ROC','CD Rociana','Rociana','','1928','../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png','','',NULL,NULL,123),(52,'Club Deportivo San Juan','SJU','CD San Juan','San Juan','','','../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png','','',NULL,NULL,126),(53,'Club Deportivo San Roque de Lepe \"B\"','SRO','CD San Roque de Lepe B','San Roque B','','','../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png','','',2,NULL,106),(54,'Patronato Municipal de Deportes Aljaraque','ALJ','PMD Aljaraque','Aljaraque','','','../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png','','',NULL,NULL,65),(55,'Olímpica Valverdeña Club de Fútbol','OLV','Olímpica Valverdeña CF','Olímpica Valverdeña','','1927','../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png','','',NULL,NULL,134),(56,'Zalamea Club de Fútbol','ZAL','Zalamea CF','Zalamea','','1914','../fake-data/blob/hipster.png','image/png','../fake-data/blob/hipster.png','image/png','','',NULL,NULL,140);
/*!40000 ALTER TABLE `team` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `team_player`
--

DROP TABLE IF EXISTS `team_player`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `team_player` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `preferred_shirt_number` int(11) DEFAULT NULL,
  `is_youth` int(11) DEFAULT NULL,
  `since_date` date DEFAULT NULL,
  `until_date` date DEFAULT NULL,
  `misc_data` varchar(255) DEFAULT NULL,
  `team_id` bigint(20) DEFAULT NULL,
  `player_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_team_player__team_id` (`team_id`),
  KEY `fk_team_player__player_id` (`player_id`),
  CONSTRAINT `fk_team_player__player_id` FOREIGN KEY (`player_id`) REFERENCES `player` (`id`),
  CONSTRAINT `fk_team_player__team_id` FOREIGN KEY (`team_id`) REFERENCES `team` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `team_player`
--

LOCK TABLES `team_player` WRITE;
/*!40000 ALTER TABLE `team_player` DISABLE KEYS */;
INSERT INTO `team_player` VALUES (1,13,0,NULL,NULL,'',1,1),(2,1,0,NULL,NULL,'',1,2),(3,4,0,NULL,NULL,'',1,3),(4,20,0,NULL,NULL,'',1,4),(5,18,0,NULL,NULL,'',1,5),(6,15,0,NULL,NULL,'',1,6),(7,25,0,NULL,NULL,'',1,7),(8,2,0,NULL,NULL,'',1,8),(9,17,0,NULL,NULL,'',1,9),(10,17,0,NULL,NULL,'',1,10),(11,5,0,NULL,NULL,'',1,11),(12,14,0,NULL,NULL,'',1,12),(13,6,0,NULL,NULL,'',1,13),(14,10,0,NULL,NULL,'',1,14),(15,11,0,NULL,NULL,'',1,15),(16,8,0,NULL,NULL,'',1,16),(17,10,0,NULL,NULL,'',1,17),(18,19,0,NULL,NULL,'',1,18),(19,7,0,NULL,NULL,'',1,19),(20,21,0,NULL,NULL,'',1,20),(21,9,0,NULL,NULL,'',1,21),(22,23,1,NULL,NULL,'',1,22),(23,11,0,NULL,NULL,'',1,23),(24,1,0,NULL,NULL,'',1,30),(25,9,0,NULL,NULL,'',1,31),(26,1,0,NULL,NULL,'',13,32),(27,7,0,NULL,NULL,'',13,33),(28,8,0,NULL,NULL,'',13,34),(29,11,0,NULL,NULL,'',13,35),(30,14,0,NULL,NULL,'',13,36),(31,15,0,NULL,NULL,'',13,37),(32,17,0,NULL,NULL,'',13,38),(33,20,0,NULL,NULL,'',13,39),(34,25,0,NULL,NULL,'',13,40),(35,27,0,NULL,NULL,'',13,41),(36,32,0,NULL,NULL,'',13,42),(37,13,0,NULL,NULL,'',13,43),(38,3,0,NULL,NULL,'',13,44),(39,4,0,NULL,NULL,'',13,45),(40,5,0,NULL,NULL,'',13,46),(41,9,0,NULL,NULL,'',13,47),(42,18,0,NULL,NULL,'',13,48),(43,22,0,NULL,NULL,'',13,49);
/*!40000 ALTER TABLE `team_player` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `team_staff_member`
--

DROP TABLE IF EXISTS `team_staff_member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `team_staff_member` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role` varchar(255) DEFAULT NULL,
  `since_date` date DEFAULT NULL,
  `until_date` date DEFAULT NULL,
  `misc_data` varchar(255) DEFAULT NULL,
  `team_id` bigint(20) DEFAULT NULL,
  `staff_member_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_team_staff_member__team_id` (`team_id`),
  KEY `fk_team_staff_member__staff_member_id` (`staff_member_id`),
  CONSTRAINT `fk_team_staff_member__staff_member_id` FOREIGN KEY (`staff_member_id`) REFERENCES `staff_member` (`id`),
  CONSTRAINT `fk_team_staff_member__team_id` FOREIGN KEY (`team_id`) REFERENCES `team` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `team_staff_member`
--

LOCK TABLES `team_staff_member` WRITE;
/*!40000 ALTER TABLE `team_staff_member` DISABLE KEYS */;
INSERT INTO `team_staff_member` VALUES (1,'DT',NULL,NULL,'',1,1),(2,'DT',NULL,NULL,'',1,2),(3,'DT2',NULL,NULL,'',1,3),(4,'PRESIDENT',NULL,NULL,'',1,4),(5,'BOARD_MEMBER',NULL,NULL,'',1,5),(6,'TEAM_DELEGATE',NULL,NULL,'',1,6),(7,'MATCH_DELEGATE',NULL,NULL,'',1,7),(8,'DT',NULL,NULL,'',8,8),(9,'DT2',NULL,NULL,'',8,9),(10,'TEAM_DELEGATE',NULL,NULL,'',8,10);
/*!40000 ALTER TABLE `team_staff_member` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `template_formation`
--

DROP TABLE IF EXISTS `template_formation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `template_formation` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `formation_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ux_template_formation__formation_id` (`formation_id`),
  CONSTRAINT `fk_template_formation__formation_id` FOREIGN KEY (`formation_id`) REFERENCES `formation` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `template_formation`
--

LOCK TABLES `template_formation` WRITE;
/*!40000 ALTER TABLE `template_formation` DISABLE KEYS */;
INSERT INTO `template_formation` VALUES (1,'4-2-3-1',1),(2,'4-3-2-1',2),(3,'4-5-1 (defensivo)',3),(4,'4-5-1 (ofensivo)',4),(5,'4-4-2 (línea)',5),(6,'4-4-2 (rombo)',6),(7,'4-4-2 (cerrado)',7),(8,'4-1-3-2',8),(9,'4-2-2-2',9),(10,'4-3-3 (contención)',10),(11,'4-3-3 (línea)',11),(12,'4-3-3',12),(13,'4-3-3 (ofensivo)',13),(14,'4-1-2-3',14),(15,'4-2-4',15),(16,'3-3-3-1',16),(17,'3-6-1',17),(18,'3-2-3-2',18),(19,'3-1-4-2',19),(20,'3-4-1-2',20),(21,'3-5-2',21),(22,'3-4-3 (línea)',22),(23,'3-4-3 (rombo)',23),(24,'3-3-1-3',24),(25,'5-4-1',25),(26,'5-3-2 (línea)',26),(27,'5-3-2 (ofensivo)',27),(28,'5-2-3',28),(29,'2-3-5 (Pirámide)',29),(30,'2-3-2-3 (Metodo)',30),(31,'3-2-3-2 (WW/MM)',31),(32,'3-2-2-3 (WM)',32),(33,'3-3-4 (línea)',33),(34,'3-3-4',34),(35,'4-1-5',35);
/*!40000 ALTER TABLE `template_formation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `venue`
--

DROP TABLE IF EXISTS `venue`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `venue` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `graphics_name` varchar(255) DEFAULT NULL,
  `long_graphics_name` varchar(255) DEFAULT NULL,
  `capacity` int(11) DEFAULT NULL,
  `opening_date` varchar(255) DEFAULT NULL,
  `field_size` varchar(255) DEFAULT NULL,
  `is_artificial_grass` int(11) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `geographic_location` varchar(255) DEFAULT NULL,
  `details` varchar(255) DEFAULT NULL,
  `misc_data` varchar(255) DEFAULT NULL,
  `location_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_venue__location_id` (`location_id`),
  CONSTRAINT `fk_venue__location_id` FOREIGN KEY (`location_id`) REFERENCES `location` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `venue`
--

LOCK TABLES `venue` WRITE;
/*!40000 ALTER TABLE `venue` DISABLE KEYS */;
INSERT INTO `venue` VALUES (1,'Campo de césped artificial del Centro Municipal de Deportes de Moguer','Centro Municipal de Deportes','Centro Municipal de Deportes de Moguer',NULL,'','',1,'','','','',112),(2,'Doro Stadium','Doro Stadium','Doro Stadium',NULL,'','',1,'','','','',154),(3,'Estadio Municipal Antonio Gallardo','Antonio Gallardo','Estadio Municipal Antonio Gallardo',NULL,'','',0,'','','','',151),(4,'Estadio Municipal Antonio Almendro','Antonio Almendro','Estadio Municipal Antonio Almendro',NULL,'','',1,'','','','',163),(5,'Campo de césped artificial del Centro Municipal Deportivo Carlos Marchena','Centro Municipal Deportivo Carlos Marchena','Centro Municipal Deportivo Carlos Marchena',NULL,'','',1,'','','','',162),(6,'Campo de césped artificial del Centro Deportivo Vega de Triana','Centro Deportivo Vega de Triana','Centro Deportivo Vega de Triana',NULL,'','',1,'','','','',161),(7,'Campo Municipal de Fútbol','Campo Municipal de Fútbol','Campo Municipal de Fútbol de Chiclana',NULL,'','',0,'','','','',152),(8,'Ciudad Deportiva María Dolores Jiménez Guardeño','María Dolores Jiménez Guardeño','Ciudad Deportiva María Dolores Jiménez Guardeño',NULL,'','',1,'','','','',158),(9,'Campo de césped artificial del Polideportivo Municipal - Centro Deportivo H2','Polideportivo Municipal Nuevo','Polideportivo Municipal - Centro Deportivo H2',NULL,'','',1,'','','','',116),(10,'Estadio Municipal de Montilla','Estadio Municipal de Montilla','Estadio Municipal de Montilla',NULL,'','',1,'','','','',159),(11,'Campo de césped artificial del Polideportivo Municipal de Lebrija','Polideportivo Municipal de Lebrija','Polideportivo Municipal de Lebrija',NULL,'','',1,'','','','',165),(12,'Estadio Municipal Carlos Piña','Carlos Piña','Estadio Municipal Carlos Piña',NULL,'','',0,'','','','',153),(13,'Campo de fútbol \"El Pandero\"','El Pandero','Campo de fútbol El Pandero',NULL,'','',1,'','','','',160),(14,'Estadio Municipal San Sebastián','San Sebastián','Estadio Municipal San Sebastián',NULL,'','',1,'','','','',166),(15,'Estadio Rafael Beca','Rafael Beca','Estadio Rafael Beca',NULL,'','',1,'','','','',164),(16,'Campo de Fútbol Municipal San Sebastián','San Sebastián','Campo de Fútbol Municipal San Sebastián',NULL,'','',1,'','','','',167),(17,'Estadio Municipal de Jédula','Estadio Municipal de Jédula','Estadio Municipal de Jédula',NULL,'','',1,'','','','',155),(18,'Campo de Fútbol Antonio Gavira','Antonio Gavira','Campo de Fútbol Antonio Gavira',NULL,'','',1,'','','','',156),(19,'Campo de Fútbol Municipal Joaquín Rodríguez Morilla','Joaquín Rodríguez Morilla','Campo de Fútbol Municipal Joaquín Rodríguez Morilla',NULL,'','',1,'','','','',163);
/*!40000 ALTER TABLE `venue` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-11-22  4:33:35
