-- MySQL dump 10.13  Distrib 8.0.22, for Win64 (x86_64)
--
-- Host: localhost    Database: lms
-- ------------------------------------------------------
-- Server version	8.0.22

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
-- Table structure for table `authors`
--

DROP TABLE IF EXISTS `authors`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `authors` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=130 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `authors`
--

LOCK TABLES `authors` WRITE;
/*!40000 ALTER TABLE `authors` DISABLE KEYS */;
INSERT INTO `authors` VALUES (1,'Ackroyd, Peter'),(2,'Aczel, Amir'),(3,'Archer, Jeffery'),(4,'Bach, Richard'),(5,'Baz-Zohar, Michael'),(6,'BBC'),(7,'Bell, E T'),(8,'Bodanis, David'),(9,'Bradsky, Gary'),(10,'Braithwaite'),(11,'Brown, Dan'),(12,'Camus, Albert'),(13,'Capra, Fritjof'),(14,'Conway, Drew'),(15,'Corbett, Jim'),(16,'Cormen, Thomas'),(17,'Crichton, Michael'),(18,'Dalrymple, William'),(19,'Dawkins, Richard'),(20,'Deb, Siddhartha'),(21,'Deshpande, P L S'),(22,'Deshpande, P L'),(23,'Deshpande, Sunita'),(24,'Devlin, Keith'),(25,'Dickens, Charles'),(26,'Dickinson, Peter'),(27,'Dostoevsky, Fyodor'),(28,'Downey, Allen'),(29,'Doyle, Arthur Conan'),(30,'Drucker, Peter'),(31,'Dubner, Stephen'),(32,'Duda, Hart'),(33,'Durant, Will'),(34,'Durrell, Gerald'),(35,'Dylan, Bob'),(36,'Eddins, Steve'),(37,'Eraly, Abraham'),(38,'Feynman, Richard'),(39,'Fisk, Robert'),(40,'Follett, Ken'),(41,'Foreman, John'),(42,'Forsyth, David'),(43,'Forsyth, Frederick'),(44,'Franco, Sergio'),(45,'Friedman, Thomas'),(46,'Gardner, Earle Stanley'),(47,'Garg, Sanjay'),(48,'Ghosh, Amitav'),(49,'Gleick, James'),(50,'Gordon, Richard'),(51,'Goswami, Jaideva'),(52,'Greene, W. H.'),(53,'Grisham, John'),(54,'Gupta, Madan'),(55,'Hansberry, Lorraine'),(56,'Harris, Sam'),(57,'Hawking, Stephen'),(58,'Haykin, Simon'),(59,'Heisenberg, Werner'),(60,'Heller, Joseph'),(61,'Hemingway, Ernest'),(62,'Hitler, Adolf'),(63,'Hugo, Victor'),(64,'Huntington, Samuel'),(65,'Huxley, Aldous'),(66,'Iacoca, Lee'),(67,'Janert, Phillip'),(68,'Kafka, Frank'),(69,'Kale, V P'),(70,'Kanetkar, Yashwant'),(71,'Kautiyla'),(72,'Konnikova, Maria'),(73,'Lapierre, Dominique'),(74,'Larsson, Steig'),(75,'Machiavelli'),(76,'Maugham, William S'),(77,'McKinney, Wes'),(78,'Menon, V P'),(79,'Mlodinow, Leonard'),(80,'Mohan, Ned'),(81,'Naipaul, V S'),(82,'Naipaul, V. S.'),(83,'Nariman'),(84,'Nayar, Kuldip'),(85,'Nehru, Jawaharlal'),(86,'Nisbet, Robert'),(87,'Oram, Andy'),(88,'Orwell, George'),(89,'Palkhivala'),(90,'Pausch, Randy'),(91,'Pirsig, Robert'),(92,'Poe, Edgar Allen'),(93,'Pratt, John'),(94,'Rand, Ayn'),(95,'Ranjan, Sudhanshu'),(96,'Rashid, Muhammad'),(97,'Raymond, Eric'),(98,'Rowling, J K'),(99,'Russell, Bertrand'),(100,'Rutherford, Alex'),(101,'Sagan, Carl'),(102,'Said, Edward'),(103,'Sassoon, Jean'),(104,'Sebastian Gutierrez'),(105,'Sen, Amartya'),(106,'Shih, Frank'),(107,'Silver, Nate'),(108,'Singh, Simon'),(109,'Smith, Adam'),(110,'Sorabjee'),(111,'Steinbeck, John'),(112,'Stonier, Alfred'),(113,'Stroud, Jonathan'),(114,'Sussman, Gerald'),(115,'Tanenbaum, Andrew'),(116,'Tao, Terence'),(117,'Taub, Schilling'),(118,'Tharoor, Shashi'),(119,'Thomas, Joy'),(120,'Vapnik, Vladimir'),(121,'Various'),(122,'Verne, Jules'),(123,'Villani, Cedric'),(124,'Vonnegut, Kurt'),(125,'Wells, H G'),(126,'Wells, H. G.'),(127,'West, Morris'),(128,'Woodward, Bob'),(129,'Zaidi, Hussain');
/*!40000 ALTER TABLE `authors` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `book_author`
--

DROP TABLE IF EXISTS `book_author`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `book_author` (
  `book_id` int unsigned NOT NULL,
  `author_id` int unsigned NOT NULL,
  PRIMARY KEY (`book_id`,`author_id`),
  KEY `author_id` (`author_id`),
  CONSTRAINT `book_author_ibfk_1` FOREIGN KEY (`book_id`) REFERENCES `books` (`book_id`),
  CONSTRAINT `book_author_ibfk_2` FOREIGN KEY (`author_id`) REFERENCES `authors` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `book_author`
--

LOCK TABLES `book_author` WRITE;
/*!40000 ALTER TABLE `book_author` DISABLE KEYS */;
INSERT INTO `book_author` VALUES (198,1),(178,2),(191,2),(14,3),(118,3),(126,3),(174,3),(9,4),(209,4),(79,5),(54,7),(137,9),(32,10),(195,10),(112,11),(142,11),(160,11),(168,12),(175,13),(190,13),(205,13),(12,15),(76,15),(42,16),(120,16),(143,18),(148,18),(176,18),(35,19),(59,20),(113,20),(202,20),(102,21),(149,21),(201,22),(139,24),(181,24),(188,24),(104,25),(158,25),(97,26),(208,26),(78,27),(203,27),(182,28),(70,29),(163,29),(71,30),(106,30),(177,30),(30,31),(1,32),(210,33),(56,34),(187,34),(36,35),(116,35),(123,35),(18,36),(184,36),(38,37),(85,38),(164,39),(4,41),(147,41),(199,42),(46,43),(99,43),(37,45),(94,45),(98,45),(47,47),(88,47),(48,48),(57,48),(91,48),(114,48),(58,49),(74,49),(8,50),(80,50),(138,50),(44,51),(110,51),(53,52),(144,54),(49,55),(95,55),(170,56),(117,57),(180,57),(166,58),(28,59),(31,59),(29,60),(204,60),(6,61),(20,61),(107,61),(19,62),(133,62),(11,63),(75,63),(121,63),(7,64),(27,64),(89,64),(5,65),(67,65),(194,65),(26,66),(125,66),(127,66),(141,66),(169,66),(16,69),(65,69),(82,69),(165,69),(136,70),(167,70),(23,72),(162,74),(157,75),(183,75),(109,76),(155,76),(159,76),(64,77),(122,77),(153,80),(197,80),(206,80),(145,81),(172,81),(60,82),(105,82),(93,83),(100,83),(129,83),(77,84),(134,84),(40,85),(52,85),(83,87),(92,87),(131,87),(161,88),(185,88),(51,89),(196,89),(200,89),(17,91),(132,91),(192,91),(154,92),(39,94),(63,94),(22,96),(24,96),(119,96),(152,96),(128,97),(108,98),(90,99),(124,99),(186,100),(43,103),(50,103),(55,103),(207,103),(101,104),(3,105),(69,105),(87,105),(130,105),(173,105),(140,106),(151,106),(21,107),(66,107),(96,107),(115,107),(68,108),(179,111),(62,112),(111,112),(34,113),(86,113),(41,114),(2,115),(73,115),(156,115),(189,115),(25,117),(45,118),(61,118),(193,118),(135,119),(10,120),(72,120),(81,120),(103,120),(171,122),(33,124),(15,125),(146,126),(150,126),(84,127),(13,129);
/*!40000 ALTER TABLE `book_author` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `book_genres`
--

DROP TABLE IF EXISTS `book_genres`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `book_genres` (
  `book_id` int unsigned NOT NULL,
  `genre_id` int unsigned NOT NULL,
  PRIMARY KEY (`book_id`,`genre_id`),
  KEY `genre_id` (`genre_id`),
  CONSTRAINT `book_genres_ibfk_1` FOREIGN KEY (`book_id`) REFERENCES `books` (`book_id`),
  CONSTRAINT `book_genres_ibfk_2` FOREIGN KEY (`genre_id`) REFERENCES `genres` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `book_genres`
--

LOCK TABLES `book_genres` WRITE;
/*!40000 ALTER TABLE `book_genres` DISABLE KEYS */;
INSERT INTO `book_genres` VALUES (5,1),(19,1),(27,1),(29,1),(37,1),(51,1),(54,1),(78,1),(102,1),(105,1),(108,1),(109,1),(126,1),(169,1),(178,1),(188,1),(192,1),(201,1),(2,2),(8,2),(38,2),(46,2),(47,2),(52,2),(67,2),(76,2),(81,2),(84,2),(89,2),(103,2),(104,2),(114,2),(120,2),(122,2),(143,2),(153,2),(163,2),(173,2),(181,2),(198,2),(1,3),(20,3),(32,3),(65,3),(69,3),(79,3),(80,3),(83,3),(98,3),(133,3),(146,3),(171,3),(180,3),(184,3),(189,3),(208,3),(21,4),(22,4),(30,4),(59,4),(62,4),(73,4),(74,4),(82,4),(95,4),(111,4),(118,4),(121,4),(125,4),(136,4),(145,4),(150,4),(151,4),(157,4),(162,4),(170,4),(187,4),(200,4),(203,4),(4,5),(25,5),(45,5),(56,5),(71,5),(88,5),(91,5),(106,5),(112,5),(113,5),(130,5),(140,5),(159,5),(166,5),(172,5),(175,5),(179,5),(191,5),(197,5),(199,5),(210,5),(12,6),(16,6),(33,6),(42,6),(50,6),(60,6),(75,6),(77,6),(87,6),(97,6),(99,6),(195,6),(207,6),(209,6),(14,7),(36,7),(43,7),(49,7),(61,7),(64,7),(66,7),(68,7),(90,7),(107,7),(110,7),(115,7),(117,7),(129,7),(138,7),(160,7),(164,7),(183,7),(206,7),(7,8),(13,8),(15,8),(23,8),(28,8),(40,8),(70,8),(85,8),(92,8),(94,8),(100,8),(101,8),(119,8),(128,8),(135,8),(139,8),(177,8),(185,8),(190,8),(193,8),(194,8),(9,9),(10,9),(24,9),(34,9),(41,9),(53,9),(131,9),(134,9),(147,9),(149,9),(155,9),(168,9),(3,10),(6,10),(55,10),(58,10),(86,10),(93,10),(132,10),(142,10),(148,10),(158,10),(165,10),(167,10),(182,10),(196,10),(202,10),(205,10),(11,11),(17,11),(18,11),(39,11),(44,11),(48,11),(57,11),(72,11),(123,11),(124,11),(137,11),(141,11),(144,11),(152,11),(154,11),(161,11),(204,11),(26,12),(31,12),(35,12),(63,12),(96,12),(116,12),(127,12),(156,12),(174,12),(176,12),(186,12);
/*!40000 ALTER TABLE `book_genres` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `books`
--

DROP TABLE IF EXISTS `books`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `books` (
  `book_id` int unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  `isbn` char(13) NOT NULL,
  `page_no` int NOT NULL,
  `published_date` date NOT NULL,
  `publisher_id` int unsigned NOT NULL,
  PRIMARY KEY (`book_id`),
  KEY `publisher_ibfk_1_idx` (`publisher_id`),
  CONSTRAINT `publisher_ibfk_1` FOREIGN KEY (`publisher_id`) REFERENCES `publisher` (`publisher_id`)
) ENGINE=InnoDB AUTO_INCREMENT=211 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `books`
--

LOCK TABLES `books` WRITE;
/*!40000 ALTER TABLE `books` DISABLE KEYS */;
INSERT INTO `books` VALUES (1,'20000 Leagues Under the Sea','441602116',190,'1979-07-17',33),(2,'Age of Discontuinity','873576019',80,'1972-03-05',14),(3,'Age of the Warrior','508786015',201,'2008-05-26',3),(4,'Age of Wrath','258014759',61,'1982-05-15',18),(5,'Aghal Paghal','254318621',145,'1974-08-01',18),(6,'Ahe Manohar Tari','131938638',130,'1989-01-23',9),(7,'All the President\'s Men','378455695',149,'1990-02-18',1),(8,'Amulet of Samarkand','744143624',238,'1998-07-24',22),(9,'Analysis, Vol I','374205491',264,'2000-10-02',3),(10,'Angels & Demons','255356904',100,'1987-07-05',18),(11,'Animal Farm','178201625',251,'1997-06-05',15),(12,'Apulki','280072312',107,'1979-03-07',20),(13,'Argumentative Indian','778884794',300,'1989-06-30',23),(14,'Arthashastra','825440416',116,'1990-02-24',10),(15,'Artist and the Mathematician','393713771',89,'1994-01-12',29),(16,'Asami Asami','121487547',258,'2001-05-03',8),(17,'Ashenden of The British Agent','943161591',244,'1987-02-20',19),(18,'Attorney','777353478',249,'2007-08-31',18),(19,'Ayn Rand Answers','637895741',166,'1989-10-12',3),(20,'Batatyachi Chal','872038022',260,'1993-02-18',32),(21,'Batman Earth One','176516493',169,'2001-04-22',28),(22,'Batman Handbook','845163393',75,'1999-11-01',29),(23,'Batman: The Long Halloween','201864987',174,'1977-08-02',12),(24,'Beautiful and the Damned','441243367',243,'1973-12-08',11),(25,'Beyond Degrees','478175626',70,'1995-05-16',1),(26,'Beyond the Three Seas','746665277',69,'2009-08-30',18),(27,'Birth of a Theorem','111063374',67,'2005-03-28',18),(28,'Bookless in Baghdad','741570143',197,'1978-01-08',6),(29,'Brethren','532593423',275,'1977-08-25',12),(30,'Broca\'s Brain','444759110',164,'1978-04-24',28),(31,'Burning Bright','665509729',91,'1983-01-15',6),(32,'Case of the Lame Canary','338099565',88,'1996-01-09',5),(33,'Catch 22','477715603',300,'2002-05-04',30),(34,'Cathedral and the Bazaar','780810199',173,'2000-05-31',7),(35,'Char Shabda','856766145',292,'1978-05-25',11),(36,'Christmas Carol','388110393',277,'1970-08-03',6),(37,'City of Djinns','418636659',247,'1973-03-23',21),(38,'City of Joy','442669634',75,'1981-10-01',25),(39,'Clash of Civilizations and Remaking of the World Order','843438492',297,'1996-05-22',28),(40,'Code Book','854246529',182,'2001-12-07',5),(41,'Complete Mastermind','160917168',165,'1994-06-17',34),(42,'Complete Sherlock Holmes, The - Vol I','668270418',208,'1991-02-14',21),(43,'Complete Sherlock Holmes, The - Vol II','547035936',51,'1979-08-22',32),(44,'Computer Vision, A Modern Approach','719568233',196,'1987-04-11',23),(45,'Courtroom Genius','379323405',129,'2008-10-03',20),(46,'Crime and Punishment','754335198',252,'2009-11-08',6),(47,'Crisis on Infinite Earths','232725847',241,'1976-01-08',17),(48,'Data Analysis with Open Source Tools','497229299',118,'1977-03-18',20),(49,'Data Mining Handbook','742611845',179,'1981-05-10',34),(50,'Data Scientists at Work','807580306',59,'1977-05-25',11),(51,'Data Smart','917457072',263,'1971-07-09',5),(52,'Data Structures Using C & C++','492660858',92,'1996-11-25',19),(53,'Death of Superman','392893549',66,'1995-06-09',16),(54,'Deceiver','265984111',293,'2007-11-17',3),(55,'Design with OpAmps','975734198',222,'1979-10-01',10),(56,'Devil\'s Advocate','128361311',269,'2004-02-14',13),(57,'Discovery of India','308370170',99,'2002-03-10',12),(58,'Doctor in the Nude','970341884',190,'1986-11-21',25),(59,'Doctor on the Brain','923263350',77,'1977-08-15',22),(60,'Dongri to Dubai','104367993',75,'2010-04-18',9),(61,'Down and Out in Paris & London','365512937',144,'1984-06-13',31),(62,'Drunkard\'s Walk','514101951',52,'1980-10-18',25),(63,'Dylan on Dylan','437129891',62,'2004-04-21',19),(64,'Econometric Analysis','493259846',132,'1986-11-30',3),(65,'Electric Universe','818653815',232,'1971-08-25',7),(66,'Elements of Information Theory','855967797',229,'1985-05-03',28),(67,'Empire of the Mughal - Brothers at War','606557269',289,'1990-07-04',14),(68,'Empire of the Mughal - Raiders from the North','134408275',226,'2003-08-27',28),(69,'Empire of the Mughal - Ruler of the World','979438219',111,'1980-09-11',27),(70,'Empire of the Mughal - The Serpent\'s Tooth','809404575',229,'1986-10-21',10),(71,'Empire of the Mughal - The Tainted Throne','776341643',166,'1970-07-29',13),(72,'Eyeless in Gaza','256596740',234,'2002-10-31',15),(73,'False Impressions','348817994',230,'2008-12-13',9),(74,'Farewell to Arms','148024717',203,'2003-07-01',34),(75,'Final Crisis','798366169',98,'1994-08-09',30),(76,'Flashpoint','972702406',148,'1998-01-21',29),(77,'Freakonomics','411372103',249,'1977-02-13',30),(78,'Free Will','374118893',209,'2001-07-11',27),(79,'Freedom at Midnight','831560205',229,'1974-02-07',21),(80,'From Beirut to Jerusalem','250791701',243,'1998-08-21',26),(81,'Fundamentals of Wavelets','558354987',66,'1977-03-02',12),(82,'Girl who kicked the Hornet\'s Nest','558263821',88,'1987-03-06',14),(83,'Girl who played with Fire','442319437',270,'2003-10-09',2),(84,'Girl with the Dragon Tattoo','348689126',286,'2007-05-12',2),(85,'God Created the Integers','896044695',269,'1984-05-09',14),(86,'Grapes of Wrath','463482699',150,'1971-08-04',30),(87,'Great Indian Novel','591710659',160,'2000-07-06',31),(88,'Great War for Civilization','911784514',282,'1977-10-01',16),(89,'Gun Gayin Awadi','843382035',124,'2001-12-12',13),(90,'Hafasavnuk','491791198',145,'1978-12-30',32),(91,'Half A Life','625392555',180,'1988-12-18',23),(92,'Hidden Connections','718900168',179,'1970-08-31',7),(93,'History of England, Foundation','489948019',142,'1981-04-30',8),(94,'History of the DC Universe','347346151',113,'1992-07-14',18),(95,'History of Western Philosophy','279516718',235,'1983-07-13',3),(96,'How to Think Like Sherlock Holmes','370406944',150,'1993-01-06',11),(97,'Hunchback of Notre Dame','802475807',111,'1987-03-04',17),(98,'Idea of Justice','111809437',164,'2002-07-15',2),(99,'Identity & Violence','859425083',58,'1979-01-21',5),(100,'Idiot','219902911',86,'2003-08-29',24),(101,'Image Processing & Mathematical Morphology','322136466',107,'2003-10-29',15),(102,'Image Processing with MATLAB','785394230',280,'1980-08-12',5),(103,'In a Free State','917242743',258,'1976-11-18',27),(104,'India from Midnight to Milennium','254356613',289,'1981-02-09',23),(105,'India\'s Legal System','722914149',295,'1972-06-19',9),(106,'Information','133713444',127,'1984-12-31',5),(107,'Integration of the Indian States','706584245',116,'1972-04-20',27),(108,'Introduction to Algorithms','928575050',146,'2008-03-19',31),(109,'Jim Corbett Omnibus','895737104',292,'2005-12-30',10),(110,'Journal of a Novel','723910169',107,'1985-02-28',19),(111,'Journal of Economics, vol 106 No 3','859987010',121,'2010-12-02',15),(112,'Judge','645784736',170,'2005-05-13',3),(113,'Jurassic Park','406460684',156,'1978-07-22',12),(114,'Justice League: The Villain\'s Journey','329245529',63,'1986-10-05',15),(115,'Justice League: Throne of Atlantis','474372628',189,'2000-03-16',11),(116,'Justice, Judiciary and Democracy','730656389',234,'1971-01-11',33),(117,'Karl Marx Biography','634442790',135,'1970-10-02',20),(118,'Killing Joke','784354626',262,'1982-08-23',12),(119,'Last Lecture','257090390',242,'2001-08-31',22),(120,'Last Mughal','801041415',85,'1971-10-27',3),(121,'Learning OpenCV','429501291',246,'1970-04-15',25),(122,'Let Us C','641553397',57,'1999-12-13',3),(123,'Life in Letters','594530355',75,'1991-11-08',26),(124,'Machine Learning for Hackers','948088856',280,'1992-04-02',4),(125,'Making Software','731364308',291,'1976-07-20',11),(126,'Manasa','632833292',167,'1970-01-09',33),(127,'Maqta-e-Ghalib','576733109',135,'1973-05-22',12),(128,'Maugham\'s Collected Short Stories, Vol 3','355923597',110,'1993-12-27',13),(129,'Mein Kampf','962205864',60,'2006-10-06',21),(130,'Men of Mathematics','219668903',81,'1983-02-19',9),(131,'Moon and Sixpence','733417340',187,'2008-02-20',26),(132,'Moon is Down','173637068',202,'2008-05-19',7),(133,'More Tears to Cry','667763245',57,'2001-04-15',32),(134,'Mossad','933853769',275,'1998-01-18',28),(135,'Murphy\'s Law','669501347',246,'1981-01-25',32),(136,'Nature of Statistical Learning Theory','349204570',97,'2000-06-02',22),(137,'Neural Networks','712979880',273,'1997-10-29',10),(138,'New Machiavelli','309583464',179,'1988-01-07',13),(139,'New Markets & Other Essays','669048864',251,'1988-02-01',29),(140,'Numbers Behind Numb3rs','671401570',231,'1993-08-05',4),(141,'O Jerusalem!','384353252',228,'1971-12-11',3),(142,'On Education','805076900',69,'2006-10-26',31),(143,'Once There Was a War','133621641',288,'1985-07-17',18),(144,'One','887176346',299,'1994-10-09',29),(145,'Orientalism','131413962',100,'1976-05-07',6),(146,'Outsider','798522813',137,'1974-07-25',26),(147,'Oxford book of Modern Science Writing','354707986',284,'1984-10-09',12),(148,'Pattern Classification','993741995',77,'1974-11-28',31),(149,'Phantom of Manhattan','686915230',104,'1988-11-05',21),(150,'Philosophy: Who Needs It','209216354',247,'1998-02-05',8),(151,'Physics & Philosophy','353177270',135,'1979-01-13',1),(152,'Pillars of the Earth','647988936',131,'1970-06-28',4),(153,'Pointers in C','850603595',216,'1976-01-16',12),(154,'Political Philosophers','159196353',279,'1995-08-23',23),(155,'Power Electronics - Mohan','734395957',295,'2008-06-20',27),(156,'Power Electronics - Rashid','774181365',239,'1990-10-13',30),(157,'Prince','213026280',92,'1985-12-13',13),(158,'Principles of Communication Systems','877044868',97,'1993-12-15',30),(159,'Prisoner of Birth','387099057',156,'1973-02-18',5),(160,'Python for Data Analysis','587316824',91,'2008-03-16',30),(161,'Radiowaril Bhashane & Shrutika','334142571',214,'2006-11-06',9),(162,'Raisin in the Sun','231014825',173,'1998-01-20',20),(163,'Rationality & Freedom','133935463',238,'1971-05-04',10),(164,'Return of the Primitive','412592793',217,'2008-10-12',29),(165,'Ropemaker','595273302',273,'2004-04-16',10),(166,'Rosy is My Relative','706273860',200,'2007-03-22',23),(167,'Russian Journal','197874599',267,'2001-07-15',27),(168,'Scoop!','213087169',286,'1987-02-24',3),(169,'Sea of Poppies','865528835',221,'2009-01-01',32),(170,'Selected Short Stories','176109604',124,'1986-10-03',22),(171,'Short History of the World','685155789',88,'1970-04-25',27),(172,'Signal and the Noise','186860097',114,'1987-08-13',27),(173,'Simpsons & Their Mathematical Secrets','407748402',246,'1986-05-16',27),(174,'Slaughterhouse Five','236808317',207,'2003-06-03',33),(175,'Social Choice & Welfare, Vol 39 No. 1','818363902',242,'2008-04-07',34),(176,'Soft Computing & Intelligent Systems','172735195',50,'1976-01-14',12),(177,'Statistical Decision Theory','947753367',198,'2004-11-13',8),(178,'Statistical Learning Theory','312262047',287,'1987-07-19',19),(179,'Story of Philosophy','999860404',82,'2004-03-18',13),(180,'Structure & Interpretation of Computer Programs','232286980',178,'1971-08-21',34),(181,'Structure and Randomness','990987109',93,'1980-04-03',32),(182,'Superfreakonomics','150131423',192,'1983-01-16',34),(183,'Superman Earth One - 1','305784710',236,'1985-07-16',32),(184,'Superman Earth One - 2','130161917',295,'2005-05-24',30),(185,'Surely You\'re Joking Mr Feynman','832549277',239,'1996-10-26',21),(186,'Tales of Beedle the Bard','179989416',270,'1997-07-21',27),(187,'Tales of Mystery and Imagination','113121943',257,'1971-11-19',10),(188,'Talking Straight','650082727',190,'1980-05-31',34),(189,'Tao of Physics','805027026',141,'1996-05-17',30),(190,'Textbook of Economic Theory','682720106',213,'1981-01-08',30),(191,'Theory of Everything','259271271',275,'1991-08-22',29),(192,'Think Complexity','843415325',175,'1987-07-10',22),(193,'To Sir With Love','611417002',192,'1976-09-03',1),(194,'Trembling of a Leaf','433952758',242,'2003-04-02',6),(195,'Trial','445994619',241,'1997-06-26',21),(196,'Uncommon Wisdom','625390704',298,'2007-05-23',27),(197,'Unpopular Essays','572186385',262,'1971-03-18',22),(198,'Urlasurla','634966622',211,'1971-01-29',28),(199,'Veil: Secret Wars of the CIA','694032487',91,'1986-05-05',21),(200,'Veteran','558596706',152,'1998-11-11',30),(201,'Vyakti ani Valli','443255705',56,'1992-07-24',17),(202,'We the Living','815974805',255,'2001-09-19',24),(203,'We the Nation','434637527',97,'1976-01-21',26),(204,'We the People','609299255',281,'1979-05-27',11),(205,'Wealth of Nations','624383878',102,'1975-11-10',32),(206,'Winter of Our Discontent','347449895',61,'1998-02-04',5),(207,'World\'s Great Thinkers','780194481',235,'1974-11-09',19),(208,'World\'s Greatest Short Stories','571715969',55,'1975-07-20',1),(209,'World\'s Greatest Trials','673326330',70,'1973-06-23',32),(210,'Zen & The Art of Motorcycle Maintenance','811399699',54,'1987-11-15',25);
/*!40000 ALTER TABLE `books` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `borrow_records`
--

DROP TABLE IF EXISTS `borrow_records`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `borrow_records` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `book_id` int unsigned DEFAULT NULL,
  `borrow_date` date NOT NULL,
  `return_date` date DEFAULT NULL,
  `borrower_id` char(6) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `book_id` (`book_id`),
  KEY `borrower_id` (`borrower_id`),
  CONSTRAINT `borrow_records_ibfk_1` FOREIGN KEY (`book_id`) REFERENCES `books` (`book_id`),
  CONSTRAINT `borrow_records_ibfk_2` FOREIGN KEY (`borrower_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `borrow_records`
--

LOCK TABLES `borrow_records` WRITE;
/*!40000 ALTER TABLE `borrow_records` DISABLE KEYS */;
INSERT INTO `borrow_records` VALUES (1,188,'2022-02-18','2022-03-08','U00038'),(2,132,'2022-02-18','2022-02-25','U00021'),(3,167,'2022-02-18',NULL,'U00199'),(4,6,'2022-02-21','2022-03-09','U00005'),(5,37,'2022-02-21','2022-03-01','U00014'),(6,20,'2022-02-21',NULL,'U00077'),(7,153,'2022-02-22','2022-02-28','U00084'),(8,1,'2022-02-23','2022-03-11','U00003'),(9,47,'2022-02-23','2022-03-11','U00003'),(10,29,'2022-02-23',NULL,'U00072'),(11,155,'2022-02-24','2022-02-28','U00176'),(12,201,'2022-02-24','2022-02-28','U00176'),(13,77,'2022-02-25','2022-03-11','U00055'),(14,48,'2022-02-25','2022-03-03','U00138'),(15,39,'2022-02-28','2022-03-11','U00111'),(16,22,'2022-03-01','2022-03-04','U00047'),(17,111,'2022-03-02','2022-03-03','U00091'),(18,16,'2022-03-03','2022-03-08','U00168'),(19,72,'2022-03-03','2022-03-08','U00168'),(20,51,'2022-03-03','2022-03-11','U00086'),(21,8,'2022-03-04',NULL,'U00149'),(22,92,'2022-03-04','2022-03-08','U00200'),(23,99,'2022-03-07','2022-03-09','U00155'),(24,17,'2022-03-07','2022-03-10','U00044'),(25,56,'2023-03-08',NULL,'U00067'),(26,172,'2022-03-08','2022-03-11','U00188'),(27,205,'2022-03-08','2022-03-09','U00069'),(28,193,'2022-03-09','2022-03-11','U00125'),(29,140,'2022-03-09','2022-03-10','U00119'),(30,153,'2022-03-10',NULL,'U00097'),(31,79,'2022-03-10',NULL,'U00009'),(32,43,'2022-03-11',NULL,'U00019'),(33,88,'2022-03-11',NULL,'U00051'),(34,3,'2022-03-11',NULL,'U00051');
/*!40000 ALTER TABLE `borrow_records` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `borrow_rule`
--

DROP TABLE IF EXISTS `borrow_rule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `borrow_rule` (
  `rule_id` int unsigned NOT NULL AUTO_INCREMENT,
  `rank_name` varchar(255) NOT NULL,
  `daily_fine` double(8,2) NOT NULL,
  `borrow_period` int NOT NULL,
  PRIMARY KEY (`rule_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `borrow_rule`
--

LOCK TABLES `borrow_rule` WRITE;
/*!40000 ALTER TABLE `borrow_rule` DISABLE KEYS */;
INSERT INTO `borrow_rule` VALUES (1,'default',1.50,14);
/*!40000 ALTER TABLE `borrow_rule` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `genres`
--

DROP TABLE IF EXISTS `genres`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `genres` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `genres`
--

LOCK TABLES `genres` WRITE;
/*!40000 ALTER TABLE `genres` DISABLE KEYS */;
INSERT INTO `genres` VALUES (1,'fiction'),(2,'economics'),(3,'history'),(4,'nonfiction'),(5,'mathematics'),(6,'philosophy'),(7,'science'),(8,'comic'),(9,'computer science'),(10,'data science'),(11,'signal processing'),(12,'psychology');
/*!40000 ALTER TABLE `genres` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `publisher`
--

DROP TABLE IF EXISTS `publisher`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `publisher` (
  `publisher_id` int unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`publisher_id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `publisher`
--

LOCK TABLES `publisher` WRITE;
/*!40000 ALTER TABLE `publisher` DISABLE KEYS */;
INSERT INTO `publisher` VALUES (1,'HBA'),(2,'Random House'),(3,'Penguin'),(4,'Mauj'),(5,'HBA'),(6,'Picador'),(7,'HighStakes'),(8,'Vintage'),(9,'HarperCollins'),(10,'Bodley Head'),(11,'vikas'),(12,'Simon & Schuster'),(13,'BBC'),(14,'Pearson'),(15,'Apress'),(16,'Wiley'),(17,'Prentice Hall'),(18,'Pan'),(19,'Rupa'),(20,'FreePress'),(21,'Routledge'),(22,'CRC'),(23,'Orient Blackswan'),(24,'MIT Press'),(25,'Hyperion'),(26,'O\' Reilly'),(27,'Springer'),(28,'Dell'),(29,'TMH'),(30,'Jaico'),(31,'Elsevier'),(32,'Pocket'),(33,'Transworld'),(34,'Fontana');
/*!40000 ALTER TABLE `publisher` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `user_id` char(6) NOT NULL,
  `user_name` varchar(255) NOT NULL,
  `rule_id` int unsigned NOT NULL,
  PRIMARY KEY (`user_id`),
  KEY `rule_id` (`rule_id`),
  CONSTRAINT `users_ibfk_1` FOREIGN KEY (`rule_id`) REFERENCES `borrow_rule` (`rule_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES ('U00001','Elliot Riley',1),('U00002','Selma Graham',1),('U00003','Ursula Galvan',1),('U00004','Nicholas Burton',1),('U00005','Lucia Compton',1),('U00006','Dominic Estes',1),('U00007','Jaime Huber',1),('U00008','Buddy Watson',1),('U00009','Beau Savage',1),('U00010','Dwayne Ramos',1),('U00011','Dante Robinson',1),('U00012','Hans Weaver',1),('U00013','Patrick Stafford',1),('U00014','Janell Kirk',1),('U00015','Ernesto Blevins',1),('U00016','Sebastian Luna',1),('U00017','Pearl Schmitt',1),('U00018','Malcolm Doyle',1),('U00019','Consuelo Holden',1),('U00020','Yesenia Espinoza',1),('U00021','Salvatore Rangel',1),('U00022','Salvador Heath',1),('U00023','Zane Wilkins',1),('U00024','Josephine Bowen',1),('U00025','Shane Todd',1),('U00026','Luella Carney',1),('U00027','Elba Cooper',1),('U00028','Dalton Pena',1),('U00029','Brandi Carlson',1),('U00030','Kenya Klein',1),('U00031','Rigoberto Mcdaniel',1),('U00032','Vito Gould',1),('U00033','Brittney Kirby',1),('U00034','Monroe Wolfe',1),('U00035','Muriel Middleton',1),('U00036','Phoebe Conley',1),('U00037','Kris Rogers',1),('U00038','Eileen Riggs',1),('U00039','Madeline Mckay',1),('U00040','Miriam Terry',1),('U00041','Roderick Adams',1),('U00042','Lynette Villa',1),('U00043','Sterling Potts',1),('U00044','Jarred Maxwell',1),('U00045','Riley Short',1),('U00046','Leta Norton',1),('U00047','Anderson Romero',1),('U00048','Lorena Trujillo',1),('U00049','Will Cantu',1),('U00050','Geoffrey Russell',1),('U00051','Susan Nelson',1),('U00052','Socorro Guzman',1),('U00053','Kent James',1),('U00054','Paris Yang',1),('U00055','Carissa Yates',1),('U00056','Erin Wade',1),('U00057','Derrick Hensley',1),('U00058','Lacey Bond',1),('U00059','Elias Pruitt',1),('U00060','Chrystal Kline',1),('U00061','Tommie Grimes',1),('U00062','Cornell Glenn',1),('U00063','Brenda Irwin',1),('U00064','Ada Clay',1),('U00065','Deena Walters',1),('U00066','Travis Lam',1),('U00067','Willy Peck',1),('U00068','Jarrod Bowman',1),('U00069','Miranda Wiley',1),('U00070','Abdul Salazar',1),('U00071','Garfield Doyle',1),('U00072','Julio Santana',1),('U00073','Ursula Harmon',1),('U00074','Ty Burnett',1),('U00075','Arturo Hodges',1),('U00076','Lou Arroyo',1),('U00077','Jannie Forbes',1),('U00078','Justine Reese',1),('U00079','Jacinto Giles',1),('U00080','Jacquelyn Ortiz',1),('U00081','Kimberly Mcintyre',1),('U00082','Alonso Watkins',1),('U00083','Juana Ferrell',1),('U00084','Jerri Haynes',1),('U00085','Wilbert Simpson',1),('U00086','Maximo Singleton',1),('U00087','Otto Macias',1),('U00088','Kathryn Knox',1),('U00089','Maureen Snow',1),('U00090','Lea Hinton',1),('U00091','Jame Prince',1),('U00092','Carrie Rivas',1),('U00093','Annabelle Clements',1),('U00094','Irma Carpenter',1),('U00095','Jamaal Jarvis',1),('U00096','Dolly Bowen',1),('U00097','Garth Cantrell',1),('U00098','Christopher Butler',1),('U00099','Eula Conner',1),('U00100','Robt Copeland',1),('U00101','Barbra Lutz',1),('U00102','Noelle Roberson',1),('U00103','Tammi Bradford',1),('U00104','Maryanne Pollard',1),('U00105','Elisha Hardin',1),('U00106','Wilson Long',1),('U00107','Gregorio Bender',1),('U00108','Johnathon Wise',1),('U00109','Vince Fischer',1),('U00110','Raymundo Cantu',1),('U00111','Jordon Pace',1),('U00112','Ramon Acevedo',1),('U00113','Ester May',1),('U00114','Daniel Jefferson',1),('U00115','Jermaine Oconnor',1),('U00116','Wilford Sanford',1),('U00117','Oliver Thomas',1),('U00118','Buster Hendricks',1),('U00119','Neal Solis',1),('U00120','Jeanie Richardson',1),('U00121','Everette Rangel',1),('U00122','Cristopher Hayden',1),('U00123','Lonnie Douglas',1),('U00124','Ora Flores',1),('U00125','Beulah Fritz',1),('U00126','Shirley Benitez',1),('U00127','Benjamin Rojas',1),('U00128','Shana Baker',1),('U00129','Curtis Monroe',1),('U00130','Ahmed Stone',1),('U00131','Alberta Foley',1),('U00132','Francis Huber',1),('U00133','Lynne Beasley',1),('U00134','Bobbi Ramos',1),('U00135','Angela Chang',1),('U00136','Deshawn Manning',1),('U00137','Fletcher Hanson',1),('U00138','Steve Mccann',1),('U00139','Casandra Moore',1),('U00140','Estela Ellis',1),('U00141','Marissa Curtis',1),('U00142','Carol Reese',1),('U00143','Jae Gill',1),('U00144','Henry Contreras',1),('U00145','Jake Petersen',1),('U00146','Ed Arias',1),('U00147','Porfirio Brown',1),('U00148','Marjorie Cooper',1),('U00149','Magdalena Chaney',1),('U00150','Roderick Hooper',1),('U00151','Roxie Cameron',1),('U00152','Benedict Bryan',1),('U00153','Victor Serrano',1),('U00154','Darryl Fox',1),('U00155','Lilian Moss',1),('U00156','Phil Ferrell',1),('U00157','Albert Gates',1),('U00158','Wm Lowe',1),('U00159','Amanda Anthony',1),('U00160','Cornelius Mitchell',1),('U00161','Latasha Hammond',1),('U00162','Leann Mcintosh',1),('U00163','Liliana Ibarra',1),('U00164','Morton Page',1),('U00165','Stacie Ellison',1),('U00166','Cyrus Bailey',1),('U00167','Maritza Glass',1),('U00168','Colette Gross',1),('U00169','Elvira Nicholson',1),('U00170','Imogene Bowers',1),('U00171','Blair Freeman',1),('U00172','Maude Cuevas',1),('U00173','Ross Odom',1),('U00174','Mari Cordova',1),('U00175','Isaias Arellano',1),('U00176','Fay Ball',1),('U00177','Sandra Berger',1),('U00178','Raymon Martin',1),('U00179','Abram Schmitt',1),('U00180','Jackie Rubio',1),('U00181','Rafael Rice',1),('U00182','Tanisha Odonnell',1),('U00183','Alan Hughes',1),('U00184','Federico Benjamin',1),('U00185','Sheryl Chambers',1),('U00186','Dixie English',1),('U00187','Will Thomas',1),('U00188','Rodrigo Noble',1),('U00189','Courtney Garrison',1),('U00190','Terence Kramer',1),('U00191','Julie Chavez',1),('U00192','Merlin Ford',1),('U00193','Larry Winters',1),('U00194','Wally Bentley',1),('U00195','Damon Stafford',1),('U00196','Rich Logan',1),('U00197','Delores Mcdonald',1),('U00198','Alyce Valenzuela',1),('U00199','Shanna Faulkner',1),('U00200','Salvatore Crane',1);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-03-15 15:45:37
