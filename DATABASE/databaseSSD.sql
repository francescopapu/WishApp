-- MySQL dump 10.13  Distrib 8.0.23, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: progettossd
-- ------------------------------------------------------
-- Server version	8.0.23

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
-- Table structure for table `gruppo`
--

DROP TABLE IF EXISTS `gruppo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `gruppo` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `Nome` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gruppo`
--

LOCK TABLES `gruppo` WRITE;
/*!40000 ALTER TABLE `gruppo` DISABLE KEYS */;
INSERT INTO `gruppo` VALUES (1,'Gruppo Bello'),(2,'Gruppo Brutto');
/*!40000 ALTER TABLE `gruppo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gruppo_listadesideri`
--

DROP TABLE IF EXISTS `gruppo_listadesideri`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `gruppo_listadesideri` (
  `GruppoID` int NOT NULL,
  `ListaDesideriID` int NOT NULL,
  PRIMARY KEY (`GruppoID`,`ListaDesideriID`),
  KEY `FKGruppo_Lis683055` (`ListaDesideriID`),
  CONSTRAINT `FKGruppo_Lis633240` FOREIGN KEY (`GruppoID`) REFERENCES `gruppo` (`ID`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `FKGruppo_Lis683055` FOREIGN KEY (`ListaDesideriID`) REFERENCES `listadesideri` (`ID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gruppo_listadesideri`
--

LOCK TABLES `gruppo_listadesideri` WRITE;
/*!40000 ALTER TABLE `gruppo_listadesideri` DISABLE KEYS */;
INSERT INTO `gruppo_listadesideri` VALUES (1,1),(2,2),(2,3),(1,4),(2,5);
/*!40000 ALTER TABLE `gruppo_listadesideri` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `listadesideri`
--

DROP TABLE IF EXISTS `listadesideri`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `listadesideri` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `UtenteID` int NOT NULL,
  `Nome` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FKListaDesid479326` (`UtenteID`),
  CONSTRAINT `FKListaDesid479326` FOREIGN KEY (`UtenteID`) REFERENCES `utente` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `listadesideri`
--

LOCK TABLES `listadesideri` WRITE;
/*!40000 ALTER TABLE `listadesideri` DISABLE KEYS */;
INSERT INTO `listadesideri` VALUES (1,1,'Lista Calcio'),(2,1,'Lista Uni'),(3,2,'Lista Bella'),(4,2,'Lista Ciccio'),(5,1,'Lista Farlocca'),(6,3,'Lista Buffa');
/*!40000 ALTER TABLE `listadesideri` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `after_delete_lista` AFTER DELETE ON `listadesideri` FOR EACH ROW BEGIN
	DELETE FROM prodotto AS p WHERE p.ListaDesideriID = OLD.ID;
    DELETE FROM gruppo_listadesideri AS gl WHERE gl.ListaDesideriID = OLD.ID;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `partecipazione`
--

DROP TABLE IF EXISTS `partecipazione`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `partecipazione` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `UtenteProprietario` bit(1) NOT NULL,
  `UtenteID` int NOT NULL,
  `GruppoID` int NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `FKPartecipaz714368` (`GruppoID`),
  KEY `FKPartecipaz792957` (`UtenteID`),
  CONSTRAINT `FKPartecipaz714368` FOREIGN KEY (`GruppoID`) REFERENCES `gruppo` (`ID`) ON DELETE CASCADE,
  CONSTRAINT `FKPartecipaz792957` FOREIGN KEY (`UtenteID`) REFERENCES `utente` (`ID`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `partecipazione`
--

LOCK TABLES `partecipazione` WRITE;
/*!40000 ALTER TABLE `partecipazione` DISABLE KEYS */;
INSERT INTO `partecipazione` VALUES (1,_binary '',1,1),(2,_binary '\0',2,1),(3,_binary '',2,2),(5,_binary '\0',1,2),(6,_binary '\0',3,2);
/*!40000 ALTER TABLE `partecipazione` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `after_delete_partecipazione` AFTER DELETE ON `partecipazione` FOR EACH ROW DELETE FROM gruppo_listadesideri AS gl WHERE gl.GruppoID = OLD.GruppoID
AND EXISTS(SELECT * FROM listadesideri AS lista WHERE gl.ListaDesideriID = lista.ID AND lista.UtenteID = OLD.UtenteID) */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `prodotto`
--

DROP TABLE IF EXISTS `prodotto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `prodotto` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `ListaDesideriID` int NOT NULL,
  `Nome` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FKProdotto471876` (`ListaDesideriID`),
  CONSTRAINT `FKProdotto471876` FOREIGN KEY (`ListaDesideriID`) REFERENCES `listadesideri` (`ID`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `prodotto`
--

LOCK TABLES `prodotto` WRITE;
/*!40000 ALTER TABLE `prodotto` DISABLE KEYS */;
INSERT INTO `prodotto` VALUES (1,1,'Insalata'),(2,1,'Pomodoro'),(3,1,'Patate'),(4,3,'Cipolla'),(6,1,'Mango'),(7,1,'Pera');
/*!40000 ALTER TABLE `prodotto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `utente`
--

DROP TABLE IF EXISTS `utente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `utente` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `sub` varchar(255) DEFAULT NULL,
  `Nome` varchar(255) DEFAULT NULL,
  `Cognome` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `utente`
--

LOCK TABLES `utente` WRITE;
/*!40000 ALTER TABLE `utente` DISABLE KEYS */;
INSERT INTO `utente` VALUES (1,'google-oauth2|110066287984616965246','Francesco','Pietrantonio'),(2,'google-oauth2|103610786215403310600','Francesco','Papulino'),(3,'google-oauth2|112381969180955049796','Carmine','Marra');
/*!40000 ALTER TABLE `utente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'progettossd'
--

--
-- Dumping routines for database 'progettossd'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-02-12 16:05:02
