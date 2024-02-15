-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: localhost    Database: hundirlaflota
-- ------------------------------------------------------
-- Server version	8.0.35

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
-- Table structure for table `barcos`
--

DROP TABLE IF EXISTS `barcos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `barcos` (
  `IdJugador` int NOT NULL,
  `IdTablero` int NOT NULL,
  `PosicionBarco1` varchar(2) NOT NULL,
  `isBarco1Golpeado` tinyint DEFAULT NULL,
  `PosicionBarco2` varchar(2) NOT NULL,
  `isBarco2Golpeado` tinyint DEFAULT NULL,
  `PosicionBarco3` varchar(2) NOT NULL,
  `isBarco3Golpeado` tinyint DEFAULT NULL,
  `PosicionBarco4` varchar(2) NOT NULL,
  `isBarco4Golpeado` tinyint DEFAULT NULL,
  PRIMARY KEY (`IdJugador`,`IdTablero`),
  KEY `IdTablero_idx` (`IdTablero`),
  CONSTRAINT `IdJugadorBarcos` FOREIGN KEY (`IdJugador`) REFERENCES `usuarios` (`IDUsuario`),
  CONSTRAINT `IdTableroBarcos` FOREIGN KEY (`IdTablero`) REFERENCES `tablero` (`IdTablero`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `barcos`
--

LOCK TABLES `barcos` WRITE;
/*!40000 ALTER TABLE `barcos` DISABLE KEYS */;
INSERT INTO `barcos` VALUES (1,14,'A1',1,'A2',1,'A3',1,'A4',1),(1,15,'C1',0,'B5',0,'B3',0,'A1',0),(1,16,'A1',1,'A2',0,'A3',0,'A4',0),(1,17,'A1',1,'A2',1,'A3',0,'A4',0),(2,14,'A1',0,'A2',0,'A3',0,'A4',1),(2,15,'A1',0,'A2',0,'A3',0,'A4',0),(3,17,'E5',0,'D2',0,'B4',0,'E1',1),(5,16,'A1',1,'A2',1,'A3',1,'A4',0);
/*!40000 ALTER TABLE `barcos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `movimientos`
--

DROP TABLE IF EXISTS `movimientos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `movimientos` (
  `IdDisparo` int NOT NULL AUTO_INCREMENT,
  `IdJugador` int NOT NULL,
  `IdTablero` int NOT NULL,
  `PosicionDisparo` varchar(2) NOT NULL,
  PRIMARY KEY (`IdDisparo`),
  KEY `IdTableroDisparo_idx` (`IdTablero`),
  KEY `IdJugadorDisparando_idx` (`IdJugador`),
  CONSTRAINT `IdJugadorDisparando` FOREIGN KEY (`IdJugador`) REFERENCES `usuarios` (`IDUsuario`),
  CONSTRAINT `IdTableroDisparo` FOREIGN KEY (`IdTablero`) REFERENCES `tablero` (`IdTablero`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `movimientos`
--

LOCK TABLES `movimientos` WRITE;
/*!40000 ALTER TABLE `movimientos` DISABLE KEYS */;
INSERT INTO `movimientos` VALUES (11,2,14,'A1'),(12,1,14,'E4'),(13,2,14,'D4'),(14,1,14,'A1'),(15,1,14,'A2'),(16,1,14,'A3'),(17,1,14,'A4'),(18,2,14,'A2'),(19,1,16,'A1'),(20,5,16,'B3'),(21,1,16,'A2'),(22,5,16,'A1'),(23,1,16,'A3'),(24,1,17,'D5'),(25,3,17,'A1'),(26,1,17,'A4'),(27,3,17,'C4'),(28,1,17,'E1'),(29,3,17,'E1'),(30,1,17,'B2'),(31,3,17,'A2'),(32,1,17,'E3');
/*!40000 ALTER TABLE `movimientos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `partida`
--

DROP TABLE IF EXISTS `partida`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `partida` (
  `IdPartida` int NOT NULL AUTO_INCREMENT,
  `IdTablero` int NOT NULL,
  `idJugador1` int NOT NULL,
  `idJugador2` int NOT NULL,
  `isTerminada` tinyint DEFAULT '0',
  `MovimientosTotal` int DEFAULT '0',
  `NombreJugadorGanador` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`IdPartida`,`IdTablero`),
  KEY `IdTablero_idx` (`IdTablero`),
  KEY `IdUsuario1_idx` (`idJugador1`),
  KEY `IdUsuario2_idx` (`idJugador2`),
  CONSTRAINT `IdTablero` FOREIGN KEY (`IdTablero`) REFERENCES `tablero` (`IdTablero`),
  CONSTRAINT `IdUsuario1` FOREIGN KEY (`idJugador1`) REFERENCES `usuarios` (`IDUsuario`),
  CONSTRAINT `IdUsuario2` FOREIGN KEY (`idJugador2`) REFERENCES `usuarios` (`IDUsuario`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `partida`
--

LOCK TABLES `partida` WRITE;
/*!40000 ALTER TABLE `partida` DISABLE KEYS */;
INSERT INTO `partida` VALUES (14,14,2,1,1,5,'root'),(15,15,2,1,1,0,'root'),(16,16,1,5,1,5,'Pedro'),(17,17,1,3,1,9,'root');
/*!40000 ALTER TABLE `partida` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tablero`
--

DROP TABLE IF EXISTS `tablero`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tablero` (
  `IdTablero` int NOT NULL AUTO_INCREMENT,
  `TurnoJugador1` tinyint DEFAULT '1',
  `TurnoJugador2` tinyint DEFAULT '0',
  `NumBarcosRestantesJugador1` int DEFAULT '4',
  `NumBarcosRestantesJugador2` int DEFAULT '4',
  PRIMARY KEY (`IdTablero`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tablero`
--

LOCK TABLES `tablero` WRITE;
/*!40000 ALTER TABLE `tablero` DISABLE KEYS */;
INSERT INTO `tablero` VALUES (14,0,1,3,2),(15,0,0,4,4),(16,0,0,3,1),(17,0,1,2,3);
/*!40000 ALTER TABLE `tablero` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuarios` (
  `IDUsuario` int NOT NULL AUTO_INCREMENT,
  `NombreUsuario` varchar(45) NOT NULL,
  `Contrasenia` varchar(100) NOT NULL,
  `PartidasJugadas` int NOT NULL DEFAULT '0',
  `Puntuacion` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`IDUsuario`,`NombreUsuario`),
  UNIQUE KEY `IDUsuario_UNIQUE` (`IDUsuario`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios`
--

LOCK TABLES `usuarios` WRITE;
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
INSERT INTO `usuarios` VALUES (1,'root','3506402',999,1008),(2,'AdrianP','-1422087353',34,93),(3,'Javier','-1167076265',10,58),(4,'Prueba','-976956847',0,0),(5,'Pedro','106542988',5,10);
/*!40000 ALTER TABLE `usuarios` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-02-15 20:23:32
