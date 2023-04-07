-- MySQL dump 10.13  Distrib 8.0.31, for Win64 (x86_64)
--
-- Host: j8a509.p.ssafy.io    Database: ssafy_cow_db
-- ------------------------------------------------------
-- Server version	8.0.32

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
-- Table structure for table `word_cloud`
--

DROP TABLE IF EXISTS `word_cloud`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `word_cloud` (
  `name` text,
  `value` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `word_cloud`
--

LOCK TABLES `word_cloud` WRITE;
/*!40000 ALTER TABLE `word_cloud` DISABLE KEYS */;
INSERT INTO `word_cloud` VALUES ('삼성전자',56),('반도체',43),('감산',41),('지원',26),('출시',24),('영업',24),('추경',23),('적자',22),('시장',17),('실적',16),('경상수지',15),('전망',15),('투자',15),('연속',14),('삼성',14),('메모리',13),('진행',13),('방문',13),('기업',12),('개최',12),('롯데',12),('태양광',11),('공장',11),('정부',10),('동결',10),('기준금리',10),('기대',10),('올해',10),('부통령',9),('세계',9),('최악',9),('예상',9),('전년',9),('가능성',9),('미국',9),('반등',9),('신제품',9),('개발',9),('에너지솔루션',9),('글로벌',8),('회복',8),('개선',8),('추진',8),('코스',8),('해리스',8),('이벤트',8),('최고',8),('종합',8),('주가',8),('배터리',8),('자회사',7),('아파트',7),('한진',7),('도쿄',7),('선언',7),('한국',7),('상용',7),('신설',7),('신사',7),('엔솔',7),('수출',7),('확보',7),('현장',6),('현대차',6),('조현민',6),('우군',6),('팝업스토어',6),('클린',6),('전환',6),('공급',6),('최대',6),('효성첨단소재',6),('후원',6),('오픈',6),('조성',6),('참가',6),('한화큐셀',6),('진출',6),('광주비엔날레',6),('확대',6),('전자',6),('페스티벌',6),('기아',6),('명줄',6),('선정',6),('결정',6),('체결',6),('환율',6),('신사업',6),('임직원',6),('한화솔루션',5),('직류배전',5),('혜택',5),('탈출',5),('사장',5),('마스터',5),('시중은행',5),('은행',5),('닝쇼크',5),('빌딩',5);
/*!40000 ALTER TABLE `word_cloud` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-04-07 14:04:39
