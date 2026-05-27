-- MySQL dump 10.13  Distrib 8.0.46, for Win64 (x86_64)
--
-- Host: localhost    Database: campus_helper
-- ------------------------------------------------------
-- Server version	8.0.46

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '分类ID',
  `name` varchar(50) NOT NULL COMMENT '分类名称',
  `icon` varchar(50) DEFAULT NULL COMMENT '分类图标',
  `sort_order` int DEFAULT '0' COMMENT '排序',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='知识分类表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (1,'校历学期','calendar',1,'2026-05-24 15:10:44'),(2,'食堂餐饮','food',2,'2026-05-24 15:10:44'),(3,'图书馆','library',3,'2026-05-24 15:10:44'),(4,'交通出行','bus',4,'2026-05-24 15:10:44'),(5,'校园卡','card',5,'2026-05-24 15:10:44'),(6,'宿舍报修','repair',6,'2026-05-24 15:10:44'),(7,'社团活动','group',7,'2026-05-24 15:10:44'),(8,'办事流程','document',8,'2026-05-24 15:10:44'),(9,'常用电话','phone',9,'2026-05-24 15:10:44');
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `knowledge`
--

DROP TABLE IF EXISTS `knowledge`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `knowledge` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `category_id` int NOT NULL,
  `question` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `answer` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `keywords` text COLLATE utf8mb4_unicode_ci,
  `view_count` int DEFAULT '0',
  `source_type` tinyint DEFAULT '1',
  `status` tinyint DEFAULT '1',
  `contributor` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `admin_note` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=90 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `knowledge`
--

LOCK TABLES `knowledge` WRITE;
/*!40000 ALTER TABLE `knowledge` DISABLE KEYS */;
INSERT INTO `knowledge` VALUES (30,1,'2025-2026学年秋季学期什么时候开学？','2025-2026学年秋季学期开学时间为2025年9月1日（本科生、全日制研究生）。新生报到时间为2025年9月6日-7日（本科新生）。在校生注册时间为2025年8月29日-30日。学期共20周，至2026年1月18日结束。','[\"开学时间\",\"秋季学期\",\"注册\",\"新生报到\",\"2025\"]',156,1,1,NULL,NULL,'2026-05-24 15:39:28','2026-05-24 15:39:28'),(31,1,'2025-2026学年寒假放假时间？','2025-2026学年寒假时间为2026年1月19日至3月1日，共6周。春季学期于2026年3月2日（校历第1周星期一）正式开学，在校生注册时间为2026年2月28日-3月1日。','[\"寒假\",\"放假\",\"春季学期\",\"开学\",\"2026\"]',142,1,1,NULL,NULL,'2026-05-24 15:39:28','2026-05-24 15:39:28'),(32,1,'2025-2026学年春季学期教学安排？','春季学期自2026年3月2日起。第1-18周为教学周，第19-20周为考试周。全日制研究生公共课于校历第1周星期一（3月2日）正式开课。','[\"春季学期\",\"教学周\",\"考试周\",\"研究生\",\"公共课\"]',98,1,1,NULL,NULL,'2026-05-24 15:39:28','2026-05-24 15:39:28'),(33,1,'2025年暑假放假时间？','2025年暑假时间为2025年7月14日至8月31日，共7周。','[\"暑假\",\"2025\",\"放假时间\"]',76,1,1,NULL,NULL,'2026-05-24 15:39:28','2026-05-24 15:39:28'),(34,2,'五山校区食堂开放时间？','五山校区西区食堂营业时间为：周一至周日 早餐 07:00-09:00，午餐 11:00-13:30，晚餐 17:00-21:00。各校区食堂具体营业时间可能略有差异，建议关注\"华南理工大学后勤处\"公众号获取最新信息。','[\"五山\",\"食堂\",\"开放\",\"时间\",\"吃饭\",\"餐饮\"]',230,1,1,NULL,NULL,'2026-05-24 15:39:38','2026-05-24 15:39:38'),(35,2,'大学城校区有哪些食堂？','大学城校区设有多个食堂，包括：第一食堂（一饭）、第二食堂（二饭）等，分布在生活区和教学区。各食堂提供早、中、晚餐及夜宵服务，支持校园一卡通刷卡消费。','[\"大学城\",\"食堂\",\"一饭\",\"二饭\",\"吃饭\"]',189,1,1,NULL,NULL,'2026-05-24 15:39:38','2026-05-24 15:39:38');
/*!40000 ALTER TABLE `knowledge` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `knowledge_source`
--

DROP TABLE IF EXISTS `knowledge_source`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `knowledge_source` (
  `id` int NOT NULL AUTO_INCREMENT,
  `source_name` varchar(100) NOT NULL COMMENT '来源名称',
  `source_url` varchar(255) DEFAULT NULL COMMENT '来源链接',
  `import_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `record_count` int DEFAULT '0' COMMENT '导入记录数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='数据来源记录';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `knowledge_source`
--

LOCK TABLES `knowledge_source` WRITE;
/*!40000 ALTER TABLE `knowledge_source` DISABLE KEYS */;
/*!40000 ALTER TABLE `knowledge_source` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-05-27 13:31:08
