CREATE DATABASE  IF NOT EXISTS `guatecinema` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `guatecinema`;
-- MySQL dump 10.13  Distrib 8.0.38, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: guatecinema
-- ------------------------------------------------------
-- Server version	8.0.39

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
-- Table structure for table `asiento`
--

DROP TABLE IF EXISTS `asiento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `asiento` (
  `id` int NOT NULL AUTO_INCREMENT,
  `sala_id` int NOT NULL,
  `numero` int NOT NULL,
  `tipo` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_sala_asiento` (`sala_id`),
  CONSTRAINT `asiento_ibfk_1` FOREIGN KEY (`sala_id`) REFERENCES `sala` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_sala_asiento` FOREIGN KEY (`sala_id`) REFERENCES `sala` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `asiento`
--

LOCK TABLES `asiento` WRITE;
/*!40000 ALTER TABLE `asiento` DISABLE KEYS */;
INSERT INTO `asiento` VALUES (1,1,1,'NORMAL'),(2,1,2,'NORMAL'),(3,1,3,'NORMAL'),(4,2,1,'VIP'),(5,2,2,'VIP'),(6,2,3,'VIP');
/*!40000 ALTER TABLE `asiento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `asiento_reserva`
--

DROP TABLE IF EXISTS `asiento_reserva`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `asiento_reserva` (
  `asiento_id` int NOT NULL,
  `usuario_id` int NOT NULL,
  PRIMARY KEY (`asiento_id`,`usuario_id`),
  KEY `usuario_id` (`usuario_id`),
  CONSTRAINT `asiento_reserva_ibfk_1` FOREIGN KEY (`asiento_id`) REFERENCES `asiento` (`id`),
  CONSTRAINT `asiento_reserva_ibfk_2` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `asiento_reserva`
--

LOCK TABLES `asiento_reserva` WRITE;
/*!40000 ALTER TABLE `asiento_reserva` DISABLE KEYS */;
/*!40000 ALTER TABLE `asiento_reserva` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `boleto`
--

DROP TABLE IF EXISTS `boleto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `boleto` (
  `id` int NOT NULL AUTO_INCREMENT,
  `funcion_id` int NOT NULL,
  `cliente_id` int NOT NULL,
  `asiento_id` int NOT NULL,
  `complemento_id` int DEFAULT NULL,
  `precio` double NOT NULL,
  `reserva_id` int DEFAULT NULL,
  `usuario_id` int DEFAULT NULL,
  `pelicula_id` int DEFAULT NULL,
  `promocion_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `complemento_id` (`complemento_id`),
  KEY `idx_boleto_funcion` (`funcion_id`),
  KEY `idx_boleto_cliente` (`cliente_id`),
  KEY `fk_asiento_boleto` (`asiento_id`),
  KEY `fk_reserva_boleto` (`reserva_id`),
  KEY `fk_boleto_pelicula` (`pelicula_id`),
  KEY `fk_boleto_usuario` (`usuario_id`),
  KEY `fk_boleto_promocion` (`promocion_id`),
  CONSTRAINT `boleto_ibfk_1` FOREIGN KEY (`funcion_id`) REFERENCES `funcion` (`id`) ON DELETE CASCADE,
  CONSTRAINT `boleto_ibfk_2` FOREIGN KEY (`cliente_id`) REFERENCES `usuario` (`id`) ON DELETE CASCADE,
  CONSTRAINT `boleto_ibfk_3` FOREIGN KEY (`asiento_id`) REFERENCES `asiento` (`id`) ON DELETE CASCADE,
  CONSTRAINT `boleto_ibfk_4` FOREIGN KEY (`complemento_id`) REFERENCES `complemento` (`id`),
  CONSTRAINT `fk_asiento_boleto` FOREIGN KEY (`asiento_id`) REFERENCES `asiento` (`id`),
  CONSTRAINT `fk_boleto_pelicula` FOREIGN KEY (`pelicula_id`) REFERENCES `pelicula` (`id`),
  CONSTRAINT `fk_boleto_promocion` FOREIGN KEY (`promocion_id`) REFERENCES `promocion` (`id`),
  CONSTRAINT `fk_boleto_usuario` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`),
  CONSTRAINT `fk_funcion_boleto` FOREIGN KEY (`funcion_id`) REFERENCES `funcion` (`id`),
  CONSTRAINT `fk_reserva_boleto` FOREIGN KEY (`reserva_id`) REFERENCES `reserva` (`id`),
  CONSTRAINT `fk_usuario_boleto` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `boleto`
--

LOCK TABLES `boleto` WRITE;
/*!40000 ALTER TABLE `boleto` DISABLE KEYS */;
INSERT INTO `boleto` VALUES (7,13,1,1,NULL,10,NULL,1,1,NULL),(8,11,2,2,NULL,12,NULL,2,2,NULL);
/*!40000 ALTER TABLE `boleto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cine`
--

DROP TABLE IF EXISTS `cine`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cine` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) NOT NULL,
  `direccion` varchar(255) NOT NULL,
  `ubicacion` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cine`
--

LOCK TABLES `cine` WRITE;
/*!40000 ALTER TABLE `cine` DISABLE KEYS */;
INSERT INTO `cine` VALUES (1,'GuateCinema Morales','Calle Real, Zona 1','Morales, Izabal'),(2,'GuateCinema Capital','Calzada Roosevelt, Zona 7','Ciudad de Guatemala, Guatemala');
/*!40000 ALTER TABLE `cine` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cine_pelicula`
--

DROP TABLE IF EXISTS `cine_pelicula`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cine_pelicula` (
  `cine_id` int NOT NULL,
  `pelicula_id` int NOT NULL,
  PRIMARY KEY (`cine_id`,`pelicula_id`),
  KEY `pelicula_id` (`pelicula_id`),
  CONSTRAINT `cine_pelicula_ibfk_1` FOREIGN KEY (`cine_id`) REFERENCES `cine` (`id`),
  CONSTRAINT `cine_pelicula_ibfk_2` FOREIGN KEY (`pelicula_id`) REFERENCES `pelicula` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cine_pelicula`
--

LOCK TABLES `cine_pelicula` WRITE;
/*!40000 ALTER TABLE `cine_pelicula` DISABLE KEYS */;
INSERT INTO `cine_pelicula` VALUES (1,1),(2,1),(1,2),(2,3);
/*!40000 ALTER TABLE `cine_pelicula` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cliente`
--

DROP TABLE IF EXISTS `cliente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cliente` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) DEFAULT NULL,
  `direccion` varchar(255) DEFAULT NULL,
  `nit` varchar(20) DEFAULT NULL,
  `correo_electronico` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `correo_electronico` (`correo_electronico`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cliente`
--

LOCK TABLES `cliente` WRITE;
/*!40000 ALTER TABLE `cliente` DISABLE KEYS */;
/*!40000 ALTER TABLE `cliente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `complemento`
--

DROP TABLE IF EXISTS `complemento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `complemento` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) NOT NULL,
  `precio` double DEFAULT NULL,
  `pelicula_id` int DEFAULT NULL,
  `promocion_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_complemento_pelicula` (`pelicula_id`),
  KEY `fk_promocion` (`promocion_id`),
  CONSTRAINT `fk_complemento_pelicula` FOREIGN KEY (`pelicula_id`) REFERENCES `pelicula` (`id`),
  CONSTRAINT `fk_promocion` FOREIGN KEY (`promocion_id`) REFERENCES `promocion` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `complemento`
--

LOCK TABLES `complemento` WRITE;
/*!40000 ALTER TABLE `complemento` DISABLE KEYS */;
INSERT INTO `complemento` VALUES (1,'Palomitas',10,NULL,NULL),(2,'Refresco',5,NULL,NULL),(3,'Nachos',7,NULL,NULL);
/*!40000 ALTER TABLE `complemento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `detalle_factura`
--

DROP TABLE IF EXISTS `detalle_factura`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `detalle_factura` (
  `id` int NOT NULL AUTO_INCREMENT,
  `factura_id` int DEFAULT NULL,
  `boleto_id` int DEFAULT NULL,
  `cantidad` int DEFAULT NULL,
  `precio_unitario` decimal(10,2) DEFAULT NULL,
  `subtotal` decimal(10,2) DEFAULT NULL,
  `complemento_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `factura_id` (`factura_id`),
  KEY `boleto_id` (`boleto_id`),
  KEY `FK333arkrnlutymby9if7rg548q` (`complemento_id`),
  CONSTRAINT `detalle_factura_ibfk_1` FOREIGN KEY (`factura_id`) REFERENCES `factura` (`id`),
  CONSTRAINT `detalle_factura_ibfk_2` FOREIGN KEY (`boleto_id`) REFERENCES `boleto` (`id`),
  CONSTRAINT `FK333arkrnlutymby9if7rg548q` FOREIGN KEY (`complemento_id`) REFERENCES `complemento` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `detalle_factura`
--

LOCK TABLES `detalle_factura` WRITE;
/*!40000 ALTER TABLE `detalle_factura` DISABLE KEYS */;
/*!40000 ALTER TABLE `detalle_factura` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `factura`
--

DROP TABLE IF EXISTS `factura`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `factura` (
  `id` int NOT NULL AUTO_INCREMENT,
  `fecha_emision` datetime DEFAULT NULL,
  `total` decimal(10,2) DEFAULT NULL,
  `cliente_id` int DEFAULT NULL,
  `usuario_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `cliente_id` (`cliente_id`),
  KEY `usuario_id` (`usuario_id`),
  CONSTRAINT `factura_ibfk_1` FOREIGN KEY (`cliente_id`) REFERENCES `cliente` (`id`),
  CONSTRAINT `factura_ibfk_2` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `factura`
--

LOCK TABLES `factura` WRITE;
/*!40000 ALTER TABLE `factura` DISABLE KEYS */;
/*!40000 ALTER TABLE `factura` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `funcion`
--

DROP TABLE IF EXISTS `funcion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `funcion` (
  `id` int NOT NULL AUTO_INCREMENT,
  `pelicula_id` int NOT NULL,
  `sala_id` int NOT NULL,
  `horario` datetime NOT NULL,
  `cine_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_funcion_pelicula` (`pelicula_id`),
  KEY `idx_funcion_sala` (`sala_id`),
  KEY `fk_funcion_cine` (`cine_id`),
  CONSTRAINT `fk_funcion_cine` FOREIGN KEY (`cine_id`) REFERENCES `cine` (`id`),
  CONSTRAINT `fk_pelicula_funcion` FOREIGN KEY (`pelicula_id`) REFERENCES `pelicula` (`id`),
  CONSTRAINT `fk_sala_funcion` FOREIGN KEY (`sala_id`) REFERENCES `sala` (`id`),
  CONSTRAINT `funcion_ibfk_1` FOREIGN KEY (`pelicula_id`) REFERENCES `pelicula` (`id`) ON DELETE CASCADE,
  CONSTRAINT `funcion_ibfk_2` FOREIGN KEY (`sala_id`) REFERENCES `sala` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `funcion`
--

LOCK TABLES `funcion` WRITE;
/*!40000 ALTER TABLE `funcion` DISABLE KEYS */;
INSERT INTO `funcion` VALUES (11,1,1,'2024-10-19 20:00:00',1),(12,2,1,'2024-10-19 21:00:00',1),(13,1,2,'2024-10-19 20:00:00',1),(14,2,2,'2024-10-19 21:00:00',1);
/*!40000 ALTER TABLE `funcion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pelicula`
--

DROP TABLE IF EXISTS `pelicula`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pelicula` (
  `id` int NOT NULL AUTO_INCREMENT,
  `titulo` varchar(100) NOT NULL,
  `genero` varchar(50) DEFAULT NULL,
  `clasificacion` varchar(10) DEFAULT NULL,
  `sinopsis` varchar(600) DEFAULT NULL,
  `trailer` varchar(255) DEFAULT NULL,
  `reparto` varchar(255) DEFAULT NULL,
  `hora` varchar(255) DEFAULT NULL,
  `sala` varchar(255) DEFAULT NULL,
  `posterurl` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pelicula`
--

LOCK TABLES `pelicula` WRITE;
/*!40000 ALTER TABLE `pelicula` DISABLE KEYS */;
INSERT INTO `pelicula` VALUES (1,'The Dark Knight','Acción','PG-13',' En esta secuela de \"Batman Begins\", el caballero de la noche, Batman, enfrenta una nueva amenaza en Gotham: el Joker, un criminal impredecible y caótico que siembra el miedo y el caos. Con la ayuda del fiscal de distrito Harvey Dent y el comisionado Jim Gordon, Batman trata de detener al Joker mientras lucha con las implicaciones morales de su guerra contra el crimen.','https://www.youtube.com/watch?v=EXeTwQwrjcw','Christian Bale, Heath Ledger','20:00','Sala 1','https://upload.wikimedia.org/wikipedia/en/1/1c/The_Dark_Knight_%282008_film%29.jpg'),(2,'Interstellar','Ciencia ficción','PG-13','Un grupo de astronautas es enviado a través de un agujero de gusano en busca de un nuevo hogar para la humanidad, ya que la Tierra está en decadencia. Liderado por el ex piloto de la NASA Cooper, la tripulación explora planetas en diferentes galaxias, enfrentando desafíos científicos y emocionales, mientras Cooper lucha por regresar a su familia.','https://www.youtube.com/watch?v=zSWdZVtZTE','Matthew McConaughey, Anne Hathaway','21:00','Sala 2','https://upload.wikimedia.org/wikipedia/en/b/bc/Interstellar_film_poster.jpg'),(3,'Inception','Ciencia ficción','PG-13','Dom Cobb es un ladrón especializado en infiltrarse en los sueños para extraer secretos valiosos. Cuando se le ofrece la oportunidad de borrar su historial criminal, acepta un trabajo arriesgado: implantar una idea en la mente de una persona, lo que se conoce como \"origen\". Para lograrlo, deberá enfrentarse a su propio subconsciente y a peligrosos enemigos dentro del mundo de los sueños.','https://www.youtube.com/watch?v=BZOMKZ0AwC8','Leonardo DiCaprio, Joseph Gordon-Levitt','22:00','Sala 2','https://upload.wikimedia.org/wikipedia/en/2/2e/Inception_%282010%29_theatrical_poster.jpg'),(4,'Avengers: Endgame','Acción','PG-13','Después de los devastadores eventos de \"Avengers: Infinity War\", los Vengadores supervivientes se unen para revertir las acciones de Thanos, quien eliminó a la mitad de toda la vida en el universo. En una misión que los llevará a través del tiempo y el espacio, los héroes luchan por restaurar el orden y traer de vuelta a sus compañeros caídos.','https://www.youtube.com/watch?v=9hpWz0ZMFAo','Robert Downey Jr., Chris Evans','19:30','Sala 1','https://upload.wikimedia.org/wikipedia/en/0/0d/Avengers_Endgame_poster.jpg');
/*!40000 ALTER TABLE `pelicula` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pelicula_rol`
--

DROP TABLE IF EXISTS `pelicula_rol`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pelicula_rol` (
  `pelicula_id` int NOT NULL,
  `rol_id` int NOT NULL,
  PRIMARY KEY (`pelicula_id`,`rol_id`),
  KEY `rol_id` (`rol_id`),
  CONSTRAINT `pelicula_rol_ibfk_1` FOREIGN KEY (`pelicula_id`) REFERENCES `pelicula` (`id`),
  CONSTRAINT `pelicula_rol_ibfk_2` FOREIGN KEY (`rol_id`) REFERENCES `rol` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pelicula_rol`
--

LOCK TABLES `pelicula_rol` WRITE;
/*!40000 ALTER TABLE `pelicula_rol` DISABLE KEYS */;
/*!40000 ALTER TABLE `pelicula_rol` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `promocion`
--

DROP TABLE IF EXISTS `promocion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `promocion` (
  `id` int NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(255) DEFAULT NULL,
  `descuento` double DEFAULT NULL,
  `fecha_inicio` date DEFAULT NULL,
  `fecha_fin` date DEFAULT NULL,
  `pelicula_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKd73kphqm171jf4af216k25gvt` (`pelicula_id`),
  CONSTRAINT `FKd73kphqm171jf4af216k25gvt` FOREIGN KEY (`pelicula_id`) REFERENCES `pelicula` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `promocion`
--

LOCK TABLES `promocion` WRITE;
/*!40000 ALTER TABLE `promocion` DISABLE KEYS */;
INSERT INTO `promocion` VALUES (1,'Descuento de 20% en entradas los lunes',20,'2024-10-01','2024-12-31',NULL),(2,'2x1 en entradas para el estreno de Interstellar',50,'2024-10-18','2024-10-25',NULL);
/*!40000 ALTER TABLE `promocion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reserva`
--

DROP TABLE IF EXISTS `reserva`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reserva` (
  `id` int NOT NULL AUTO_INCREMENT,
  `funcion_id` int NOT NULL,
  `cliente_id` int NOT NULL,
  `asiento_id` int NOT NULL,
  `estado` varchar(255) NOT NULL,
  `fecha_reserva` datetime DEFAULT NULL,
  `boleto_id` int DEFAULT NULL,
  `tipo_reserva` varchar(20) DEFAULT NULL,
  `es_reserva_app` tinyint(1) NOT NULL DEFAULT '0',
  `usuario_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `asiento_id` (`asiento_id`),
  KEY `idx_reserva_funcion` (`funcion_id`),
  KEY `idx_reserva_cliente` (`cliente_id`),
  KEY `reserva_ibfk_4` (`boleto_id`),
  KEY `fk_usuario_reserva` (`usuario_id`),
  CONSTRAINT `FK7cg2jiyn5cf6f6elccvb6963k` FOREIGN KEY (`cliente_id`) REFERENCES `cliente` (`id`),
  CONSTRAINT `fk_funcion_reserva` FOREIGN KEY (`funcion_id`) REFERENCES `funcion` (`id`),
  CONSTRAINT `fk_usuario` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`),
  CONSTRAINT `fk_usuario_reserva` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`),
  CONSTRAINT `reserva_ibfk_1` FOREIGN KEY (`funcion_id`) REFERENCES `funcion` (`id`) ON DELETE CASCADE,
  CONSTRAINT `reserva_ibfk_2` FOREIGN KEY (`cliente_id`) REFERENCES `usuario` (`id`) ON DELETE CASCADE,
  CONSTRAINT `reserva_ibfk_3` FOREIGN KEY (`asiento_id`) REFERENCES `asiento` (`id`) ON DELETE CASCADE,
  CONSTRAINT `reserva_ibfk_4` FOREIGN KEY (`boleto_id`) REFERENCES `boleto` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reserva`
--

LOCK TABLES `reserva` WRITE;
/*!40000 ALTER TABLE `reserva` DISABLE KEYS */;
/*!40000 ALTER TABLE `reserva` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rol`
--

DROP TABLE IF EXISTS `rol`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rol` (
  `id` int NOT NULL,
  `nombre` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `nombre` (`nombre`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rol`
--

LOCK TABLES `rol` WRITE;
/*!40000 ALTER TABLE `rol` DISABLE KEYS */;
INSERT INTO `rol` VALUES (2,'ADMIN'),(1,'USER'),(3,'VENDEDOR');
/*!40000 ALTER TABLE `rol` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sala`
--

DROP TABLE IF EXISTS `sala`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sala` (
  `id` int NOT NULL AUTO_INCREMENT,
  `cine_id` int NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `capacidad` int NOT NULL,
  `tipo` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `cine_id` (`cine_id`),
  CONSTRAINT `sala_ibfk_1` FOREIGN KEY (`cine_id`) REFERENCES `cine` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sala`
--

LOCK TABLES `sala` WRITE;
/*!40000 ALTER TABLE `sala` DISABLE KEYS */;
INSERT INTO `sala` VALUES (1,1,'Sala 1',30,'NORMAL'),(2,1,'Sala 2',20,'VIP'),(3,1,'Sala 3',30,'NORMAL'),(4,2,'Sala 1',30,'NORMAL'),(5,2,'Sala 2',20,'VIP');
/*!40000 ALTER TABLE `sala` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) NOT NULL,
  `correo_electronico` varchar(100) NOT NULL,
  `contraseña` varchar(255) NOT NULL,
  `rol_id` int DEFAULT NULL,
  `cine_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `correo_electronico` (`correo_electronico`),
  KEY `usuario_ibfk_1` (`rol_id`),
  KEY `fk_usuario_cine` (`cine_id`),
  CONSTRAINT `fk_usuario_cine` FOREIGN KEY (`cine_id`) REFERENCES `cine` (`id`),
  CONSTRAINT `usuario_ibfk_1` FOREIGN KEY (`rol_id`) REFERENCES `rol` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (1,'Juan Pérez','juan.perez@example.com','$2a$10$aHEcjdUMrF7cGVIzLbpMh.QP5oXoxtwTgvH2fB2LNZVDdU8tjc3Nq',1,NULL),(2,'Fer','Fer@gmail.com','$2a$10$fCEeH.mrD/SlfMPZB6pnreLema41eoerpGOJiEFFMOfKba5CFeB0K',2,NULL);
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'guatecinema'
--

--
-- Dumping routines for database 'guatecinema'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-10-18 21:15:48
