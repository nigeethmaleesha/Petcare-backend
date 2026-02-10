-- MySQL dump 10.13  Distrib 8.0.44, for Win64 (x86_64)
--
-- Host: localhost    Database: petcare_admin
-- ------------------------------------------------------
-- Server version	5.7.44-log

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
-- Table structure for table `admin`
--

DROP TABLE IF EXISTS `admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `admin` (
  `admin_id` int(11) NOT NULL AUTO_INCREMENT,
  `full_name` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` varchar(255) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`admin_id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin`
--

LOCK TABLES `admin` WRITE;
/*!40000 ALTER TABLE `admin` DISABLE KEYS */;
INSERT INTO `admin` VALUES (1,'Admin User','admin@petcare.com','admin123','admin','2026-02-04 07:06:17');
/*!40000 ALTER TABLE `admin` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-02-10 21:11:46
-- MySQL dump 10.13  Distrib 8.0.44, for Win64 (x86_64)
--
-- Host: localhost    Database: adoptionms
-- ------------------------------------------------------
-- Server version	5.7.44-log

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
-- Table structure for table `adoption`
--

DROP TABLE IF EXISTS `adoption`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `adoption` (
  `adoption_id` varchar(255) NOT NULL,
  `pet_name` varchar(255) DEFAULT NULL,
  `breed` varchar(255) DEFAULT NULL,
  `species` varchar(255) DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  `size` varchar(255) DEFAULT NULL,
  `shelter_id` varchar(255) DEFAULT NULL,
  `vaccinated` tinyint(1) DEFAULT NULL,
  `kid_friendly` tinyint(1) DEFAULT NULL,
  `medical_notes` text,
  `special_needs` text,
  `image_path` varchar(255) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`adoption_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `adoption`
--

LOCK TABLES `adoption` WRITE;
/*!40000 ALTER TABLE `adoption` DISABLE KEYS */;
INSERT INTO `adoption` VALUES ('PET-001','Buddy','Rottweiler','Dog',1,'Small','REG-002',0,1,'Good.....................','ok.............................','/uploads/pet/1770612296894_7b921b09-66fc-45d0-8495-586f30943b6e.webp','2026-02-08 23:14:58'),('PET-002','Buddy','Rottweiler','Dog',1,'Small','REG-002',0,1,'Good.....................','ok.............................','/uploads/pet/1770612298057_7b921b09-66fc-45d0-8495-586f30943b6e.webp','2026-02-08 23:14:58'),('PET-003','Buddy','Rottweiler','Dog',0,'Small','SPAS/SL/2024/1785',1,0,'good','ok............','/uploads/pet/1770618310824_7b921b09-66fc-45d0-8495-586f30943b6e.webp','2026-02-09 00:55:13'),('PET-004','Killer','Rottweiler','Dog',1,'Small','SPAS/SL/2024/1785',1,0,'good','ok','/uploads/pet/1770618960849_7b921b09-66fc-45d0-8495-586f30943b6e.webp','2026-02-09 01:06:01'),('PET-005','Buddy','Rottweiler','Dog',1,'Small','12345678',1,0,'dfghjk','dfghjm',NULL,'2026-02-09 14:10:31');
/*!40000 ALTER TABLE `adoption` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `adoption_requests`
--

DROP TABLE IF EXISTS `adoption_requests`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `adoption_requests` (
  `request_id` varchar(255) NOT NULL,
  `request_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `adoption_id` varchar(255) DEFAULT NULL,
  `pet_name` varchar(255) DEFAULT NULL,
  `shelter_id` varchar(255) DEFAULT NULL,
  `type_of_home` varchar(255) DEFAULT NULL,
  `fenced_yard` enum('Yes','No') DEFAULT 'No',
  `activity_level` varchar(255) DEFAULT NULL,
  `hours_alone_per_day` int(11) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `fullname` varchar(255) DEFAULT NULL,
  `contact_no` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`request_id`),
  KEY `fk_adoption_id` (`adoption_id`),
  CONSTRAINT `fk_adoption_id` FOREIGN KEY (`adoption_id`) REFERENCES `adoption` (`adoption_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `adoption_requests`
--

LOCK TABLES `adoption_requests` WRITE;
/*!40000 ALTER TABLE `adoption_requests` DISABLE KEYS */;
INSERT INTO `adoption_requests` VALUES ('REQ-001','2026-02-08 23:18:24','PET-001','Buddy','REG-00REG-002','House','No','Medium',4,'Pending','Nigeeth Maleesha','0764846394'),('REQ-002','2026-02-08 23:21:25','PET-001','Buddy','REG-00REG-002','Apartment','Yes','High',4,'Pending','Nigeeth','0764846394'),('REQ-003','2026-02-08 23:27:48','PET-001','Buddy','REG-00REG-002','House','Yes','High',5,'Pending','Dishan','0764846394'),('REQ-004','2026-02-08 23:42:12','PET-001','Buddy','REG-002','House','Yes','High',0,'Approved','Nigeeth','0764846394'),('REQ-005','2026-02-09 01:07:41','PET-004','Killer','SPAS/SL/2024/1785','House','Yes','High',5,'Pending','Nigeeth','0764846394'),('REQ-006','2026-02-09 14:11:53','PET-005','Buddy','12345678','House','Yes','High',4,'Approved','Nigeeth','0764846394');
/*!40000 ALTER TABLE `adoption_requests` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-02-10 21:11:46
-- MySQL dump 10.13  Distrib 8.0.44, for Win64 (x86_64)
--
-- Host: localhost    Database: petcare_emergency
-- ------------------------------------------------------
-- Server version	5.7.44-log

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
-- Table structure for table `chat_history`
--

DROP TABLE IF EXISTS `chat_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `chat_history` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `session_id` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `user_message` text COLLATE utf8mb4_unicode_ci,
  `ai_response` text COLLATE utf8mb4_unicode_ci,
  `timestamp` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `pet_symptom` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `is_emergency` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `idx_session_id` (`session_id`),
  KEY `idx_timestamp` (`timestamp`),
  KEY `idx_emergency` (`is_emergency`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chat_history`
--

LOCK TABLES `chat_history` WRITE;
/*!40000 ALTER TABLE `chat_history` DISABLE KEYS */;
INSERT INTO `chat_history` VALUES (1,'session_1770272486413_p928xz8hs','My pet is vomiting','? **VOMITING/DIGESTIVE ISSUES ASSESSMENT**\n\nBased on your description: \"My pet is vomiting\"\n\n**IMMEDIATE CARE PROTOCOL:**\n1. **Food Fasting:** Withhold all food for 4-6 hours (water remains available)\n2. **Hydration:** Offer small amounts of water every 30 minutes\n3. **Bland Diet:** After fasting, offer: boiled chicken + white rice (3:1 ratio)\n4. **Monitor:** Count vomiting episodes, note color/content\n\n**CAUSE ANALYSIS:**\n• Dietary indiscretion (ate something unusual)\n• Viral/bacterial infection\n• Parasites\n• Organ disease (kidney/liver)\n• Obstruction (requires immediate vet)\n\n**RED FLAGS (Emergency Vet):**\n✅ Vomiting more than 3 times in 24 hours\n✅ Blood in vomit (red, coffee-ground appearance)\n✅ Projectile vomiting\n✅ Accompanied by diarrhea\n✅ Lethargy, weakness, or collapse\n✅ Suspected ingestion of toxins\n\n**HOME MONITORING CHECKLIST:**\n• Gum color: Should be pink (not pale/white)\n• Skin tent test: Check for dehydration\n• Energy level: Note any changes\n• Appetite: Willingness to eat/drink\n\n**URGENCY LEVEL: MEDIUM-HIGH** - Depends on frequency and accompanying symptoms.\n\n**Timeline for Veterinary Care:**\n• Within 2 hours: If blood present or severe lethargy\n• Within 12 hours: If vomiting continues despite fasting\n• Within 24 hours: If symptoms persist but pet is stable\n','2026-02-05 06:21:27','Vomiting',1),(2,'session_1770273158333_h4ul2tzwx','My pet is Vomiting','? **VOMITING/DIGESTIVE ISSUES ASSESSMENT**\n\nBased on your description: \"My pet is Vomiting\"\n\n**IMMEDIATE CARE PROTOCOL:**\n1. **Food Fasting:** Withhold all food for 4-6 hours (water remains available)\n2. **Hydration:** Offer small amounts of water every 30 minutes\n3. **Bland Diet:** After fasting, offer: boiled chicken + white rice (3:1 ratio)\n4. **Monitor:** Count vomiting episodes, note color/content\n\n**CAUSE ANALYSIS:**\n• Dietary indiscretion (ate something unusual)\n• Viral/bacterial infection\n• Parasites\n• Organ disease (kidney/liver)\n• Obstruction (requires immediate vet)\n\n**RED FLAGS (Emergency Vet):**\n✅ Vomiting more than 3 times in 24 hours\n✅ Blood in vomit (red, coffee-ground appearance)\n✅ Projectile vomiting\n✅ Accompanied by diarrhea\n✅ Lethargy, weakness, or collapse\n✅ Suspected ingestion of toxins\n\n**HOME MONITORING CHECKLIST:**\n• Gum color: Should be pink (not pale/white)\n• Skin tent test: Check for dehydration\n• Energy level: Note any changes\n• Appetite: Willingness to eat/drink\n\n**URGENCY LEVEL: MEDIUM-HIGH** - Depends on frequency and accompanying symptoms.\n\n**Timeline for Veterinary Care:**\n• Within 2 hours: If blood present or severe lethargy\n• Within 12 hours: If vomiting continues despite fasting\n• Within 24 hours: If symptoms persist but pet is stable\n','2026-02-05 06:32:38','Vomiting',1),(3,'session_1770273158333_h4ul2tzwx','My pet is beeding','? **GENERAL PET HEALTH ASSESSMENT**\n\nBased on your description: \"My pet is beeding\"\n\n**COMPREHENSIVE HEALTH CHECKLIST:**\n\n**VITAL SIGNS MONITORING:**\n• **Temperature:** 100.5°F - 102.5°F (38°C - 39.2°C)\n• **Heart Rate:** 70-160 (dogs), 140-220 (cats) beats/minute\n• **Respiration:** 15-30 (dogs), 20-30 (cats) breaths/minute\n• **Gum Color:** Pink (not pale/white/blue)\n• **Capillary Refill:** <2 seconds (press gum, color should return quickly)\n\n**HOME OBSERVATION PROTOCOL:**\n1. **Appetite/Thirst:** Normal, increased, decreased, absent\n2. **Energy Level:** Normal, lethargic, hyperactive\n3. **Behavior Changes:** Hiding, aggression, anxiety\n4. **Elimination:** Frequency, consistency, color, effort\n5. **Movement:** Limping, stiffness, difficulty rising\n\n**COMMON CONCERNS & RESPONSES:**\n\n**Not Eating:**\n• Temporary (24h): Monitor hydration\n• Extended (>48h): Veterinary consultation\n• Senior pets: Earlier intervention recommended\n\n**Lethargy:**\n• Mild (sleepy but responsive): 24h observation\n• Severe (unresponsive/difficult to rouse): Emergency\n\n**Behavior Changes:**\n• Document specific changes\n• Note triggers/environment\n• Video record if possible for vet\n\n**WHEN TO ESCALATE CARE:**\n✅ Symptoms worsen despite home care\n✅ Multiple symptoms present\n✅ Senior/young animals\n✅ Pre-existing medical conditions\n✅ No improvement in 24-48 hours\n\n**TELEHEALTH PREPARATION:**\n• Take clear photos/videos\n• Record timeline of symptoms\n• List medications/supplements\n• Note diet/treat changes\n• Prepare questions for vet\n\n**URGENCY LEVEL: LOW** - Based on general description.\n\n**Recommended Action Plan:**\n1. **Immediate:** Monitor vital signs 2-3 times daily\n2. **24-hour mark:** Reassess, document changes\n3. **48-hour mark:** If no improvement, schedule vet visit\n4. **Any worsening:** Contact vet immediately\n\n**Emergency Preparedness:**\n• Keep vet/emergency numbers accessible\n• Have pet carrier ready\n• Know route to emergency clinic\n• Keep medical records organized\n','2026-02-05 06:33:08','General Symptoms',1),(4,'session_1770273158333_h4ul2tzwx','My pet are not good','? **GENERAL PET HEALTH ASSESSMENT**\n\nBased on your description: \"My pet are not good\"\n\n**COMPREHENSIVE HEALTH CHECKLIST:**\n\n**VITAL SIGNS MONITORING:**\n• **Temperature:** 100.5°F - 102.5°F (38°C - 39.2°C)\n• **Heart Rate:** 70-160 (dogs), 140-220 (cats) beats/minute\n• **Respiration:** 15-30 (dogs), 20-30 (cats) breaths/minute\n• **Gum Color:** Pink (not pale/white/blue)\n• **Capillary Refill:** <2 seconds (press gum, color should return quickly)\n\n**HOME OBSERVATION PROTOCOL:**\n1. **Appetite/Thirst:** Normal, increased, decreased, absent\n2. **Energy Level:** Normal, lethargic, hyperactive\n3. **Behavior Changes:** Hiding, aggression, anxiety\n4. **Elimination:** Frequency, consistency, color, effort\n5. **Movement:** Limping, stiffness, difficulty rising\n\n**COMMON CONCERNS & RESPONSES:**\n\n**Not Eating:**\n• Temporary (24h): Monitor hydration\n• Extended (>48h): Veterinary consultation\n• Senior pets: Earlier intervention recommended\n\n**Lethargy:**\n• Mild (sleepy but responsive): 24h observation\n• Severe (unresponsive/difficult to rouse): Emergency\n\n**Behavior Changes:**\n• Document specific changes\n• Note triggers/environment\n• Video record if possible for vet\n\n**WHEN TO ESCALATE CARE:**\n✅ Symptoms worsen despite home care\n✅ Multiple symptoms present\n✅ Senior/young animals\n✅ Pre-existing medical conditions\n✅ No improvement in 24-48 hours\n\n**TELEHEALTH PREPARATION:**\n• Take clear photos/videos\n• Record timeline of symptoms\n• List medications/supplements\n• Note diet/treat changes\n• Prepare questions for vet\n\n**URGENCY LEVEL: LOW** - Based on general description.\n\n**Recommended Action Plan:**\n1. **Immediate:** Monitor vital signs 2-3 times daily\n2. **24-hour mark:** Reassess, document changes\n3. **48-hour mark:** If no improvement, schedule vet visit\n4. **Any worsening:** Contact vet immediately\n\n**Emergency Preparedness:**\n• Keep vet/emergency numbers accessible\n• Have pet carrier ready\n• Know route to emergency clinic\n• Keep medical records organized\n','2026-02-05 06:35:03','General Symptoms',1),(5,'session_1770273158333_h4ul2tzwx','My pet is laba','? **GENERAL PET HEALTH ASSESSMENT**\n\nBased on your description: \"My pet is laba\"\n\n**COMPREHENSIVE HEALTH CHECKLIST:**\n\n**VITAL SIGNS MONITORING:**\n• **Temperature:** 100.5°F - 102.5°F (38°C - 39.2°C)\n• **Heart Rate:** 70-160 (dogs), 140-220 (cats) beats/minute\n• **Respiration:** 15-30 (dogs), 20-30 (cats) breaths/minute\n• **Gum Color:** Pink (not pale/white/blue)\n• **Capillary Refill:** <2 seconds (press gum, color should return quickly)\n\n**HOME OBSERVATION PROTOCOL:**\n1. **Appetite/Thirst:** Normal, increased, decreased, absent\n2. **Energy Level:** Normal, lethargic, hyperactive\n3. **Behavior Changes:** Hiding, aggression, anxiety\n4. **Elimination:** Frequency, consistency, color, effort\n5. **Movement:** Limping, stiffness, difficulty rising\n\n**COMMON CONCERNS & RESPONSES:**\n\n**Not Eating:**\n• Temporary (24h): Monitor hydration\n• Extended (>48h): Veterinary consultation\n• Senior pets: Earlier intervention recommended\n\n**Lethargy:**\n• Mild (sleepy but responsive): 24h observation\n• Severe (unresponsive/difficult to rouse): Emergency\n\n**Behavior Changes:**\n• Document specific changes\n• Note triggers/environment\n• Video record if possible for vet\n\n**WHEN TO ESCALATE CARE:**\n✅ Symptoms worsen despite home care\n✅ Multiple symptoms present\n✅ Senior/young animals\n✅ Pre-existing medical conditions\n✅ No improvement in 24-48 hours\n\n**TELEHEALTH PREPARATION:**\n• Take clear photos/videos\n• Record timeline of symptoms\n• List medications/supplements\n• Note diet/treat changes\n• Prepare questions for vet\n\n**URGENCY LEVEL: LOW** - Based on general description.\n\n**Recommended Action Plan:**\n1. **Immediate:** Monitor vital signs 2-3 times daily\n2. **24-hour mark:** Reassess, document changes\n3. **48-hour mark:** If no improvement, schedule vet visit\n4. **Any worsening:** Contact vet immediately\n\n**Emergency Preparedness:**\n• Keep vet/emergency numbers accessible\n• Have pet carrier ready\n• Know route to emergency clinic\n• Keep medical records organized\n','2026-02-05 06:36:03','General Symptoms',1),(6,'session_1770273158333_h4ul2tzwx','my pet is beeding','? **GENERAL PET HEALTH ASSESSMENT**\n\nBased on your description: \"my pet is beeding\"\n\n**COMPREHENSIVE HEALTH CHECKLIST:**\n\n**VITAL SIGNS MONITORING:**\n• **Temperature:** 100.5°F - 102.5°F (38°C - 39.2°C)\n• **Heart Rate:** 70-160 (dogs), 140-220 (cats) beats/minute\n• **Respiration:** 15-30 (dogs), 20-30 (cats) breaths/minute\n• **Gum Color:** Pink (not pale/white/blue)\n• **Capillary Refill:** <2 seconds (press gum, color should return quickly)\n\n**HOME OBSERVATION PROTOCOL:**\n1. **Appetite/Thirst:** Normal, increased, decreased, absent\n2. **Energy Level:** Normal, lethargic, hyperactive\n3. **Behavior Changes:** Hiding, aggression, anxiety\n4. **Elimination:** Frequency, consistency, color, effort\n5. **Movement:** Limping, stiffness, difficulty rising\n\n**COMMON CONCERNS & RESPONSES:**\n\n**Not Eating:**\n• Temporary (24h): Monitor hydration\n• Extended (>48h): Veterinary consultation\n• Senior pets: Earlier intervention recommended\n\n**Lethargy:**\n• Mild (sleepy but responsive): 24h observation\n• Severe (unresponsive/difficult to rouse): Emergency\n\n**Behavior Changes:**\n• Document specific changes\n• Note triggers/environment\n• Video record if possible for vet\n\n**WHEN TO ESCALATE CARE:**\n✅ Symptoms worsen despite home care\n✅ Multiple symptoms present\n✅ Senior/young animals\n✅ Pre-existing medical conditions\n✅ No improvement in 24-48 hours\n\n**TELEHEALTH PREPARATION:**\n• Take clear photos/videos\n• Record timeline of symptoms\n• List medications/supplements\n• Note diet/treat changes\n• Prepare questions for vet\n\n**URGENCY LEVEL: LOW** - Based on general description.\n\n**Recommended Action Plan:**\n1. **Immediate:** Monitor vital signs 2-3 times daily\n2. **24-hour mark:** Reassess, document changes\n3. **48-hour mark:** If no improvement, schedule vet visit\n4. **Any worsening:** Contact vet immediately\n\n**Emergency Preparedness:**\n• Keep vet/emergency numbers accessible\n• Have pet carrier ready\n• Know route to emergency clinic\n• Keep medical records organized\n','2026-02-05 06:37:05','General Symptoms',1);
/*!40000 ALTER TABLE `chat_history` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-02-10 21:11:46
-- MySQL dump 10.13  Distrib 8.0.44, for Win64 (x86_64)
--
-- Host: localhost    Database: petcare
-- ------------------------------------------------------
-- Server version	5.7.44-log

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
-- Table structure for table `growth_record`
--

DROP TABLE IF EXISTS `growth_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `growth_record` (
  `growth_id` int(11) NOT NULL AUTO_INCREMENT,
  `pet_id` int(11) NOT NULL,
  `measurement_date` date NOT NULL,
  `weight` decimal(38,2) NOT NULL,
  `height` decimal(38,2) NOT NULL,
  `notes` varchar(255) DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`growth_id`),
  KEY `pet_id` (`pet_id`),
  CONSTRAINT `growth_record_ibfk_1` FOREIGN KEY (`pet_id`) REFERENCES `pet` (`pet_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `growth_record`
--

LOCK TABLES `growth_record` WRITE;
/*!40000 ALTER TABLE `growth_record` DISABLE KEYS */;
INSERT INTO `growth_record` VALUES (1,1,'2023-10-12',32.50,58.20,'Regular checkup','2026-02-03 06:22:01'),(2,3,'2026-02-03',35.30,3.50,'Good','2026-02-03 07:57:57'),(3,3,'2026-02-03',36.50,3.70,'Good','2026-02-03 07:58:30'),(4,3,'2025-12-03',34.30,3.30,'Good','2026-02-03 07:59:33'),(5,4,'2026-02-03',35.00,3.80,'Growth weight','2026-02-03 15:20:43'),(6,4,'2026-02-03',35.50,3.70,'Good','2026-02-03 17:20:21'),(7,4,'2026-02-03',37.00,30.50,'Good','2026-02-03 17:21:46'),(8,6,'2026-02-10',3.80,1.90,'Good','2026-02-10 14:58:07');
/*!40000 ALTER TABLE `growth_record` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `medical_record`
--

DROP TABLE IF EXISTS `medical_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `medical_record` (
  `medical_id` int(11) NOT NULL AUTO_INCREMENT,
  `pet_id` int(11) NOT NULL,
  `record_date` date NOT NULL,
  `condition_name` varchar(255) NOT NULL,
  `record_type` varchar(255) NOT NULL,
  `treatment` varchar(255) DEFAULT NULL,
  `notes` varchar(255) DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`medical_id`),
  KEY `pet_id` (`pet_id`),
  CONSTRAINT `medical_record_ibfk_1` FOREIGN KEY (`pet_id`) REFERENCES `pet` (`pet_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `medical_record`
--

LOCK TABLES `medical_record` WRITE;
/*!40000 ALTER TABLE `medical_record` DISABLE KEYS */;
INSERT INTO `medical_record` VALUES (1,1,'2023-10-12','Annual Checkup','Routine','General physical exam, weight check','Buddy is at a healthy weight. Vital signs normal.','2026-02-03 06:23:28'),(2,3,'2026-02-03','Annual Checkup','Routine','Ok,all good','Next Comming....','2026-02-03 08:00:39'),(3,6,'2026-02-10','Annual Checkup','Vaccine','Treatment is good','No problem','2026-02-10 14:59:58');
/*!40000 ALTER TABLE `medical_record` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pet`
--

DROP TABLE IF EXISTS `pet`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pet` (
  `pet_id` int(11) NOT NULL AUTO_INCREMENT,
  `pet_owner_id` int(11) NOT NULL,
  `pet_name` varchar(255) NOT NULL,
  `species` varchar(255) NOT NULL,
  `breed` varchar(255) DEFAULT NULL,
  `date_of_birth` date DEFAULT NULL,
  `weight` decimal(38,2) DEFAULT NULL,
  `height` decimal(38,2) DEFAULT NULL,
  `health_notes` text,
  `image_url` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`pet_id`),
  KEY `pet_owner_id` (`pet_owner_id`),
  CONSTRAINT `pet_ibfk_1` FOREIGN KEY (`pet_owner_id`) REFERENCES `pet_owner` (`pet_owner_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pet`
--

LOCK TABLES `pet` WRITE;
/*!40000 ALTER TABLE `pet` DISABLE KEYS */;
INSERT INTO `pet` VALUES (1,1,'Buddy','Dog','Golden Retriever','2022-01-15',32.50,58.20,'Healthy and active','https://images.unsplash.com/photo-1633722715463-d30f4f325e24','Active','2026-02-03 06:13:52','2026-02-03 06:18:52'),(3,2,'Buddy','dog','Corgi','2025-02-03',30.35,3.50,'Good Dog',NULL,'Active','2026-02-03 07:28:48','2026-02-03 07:28:48'),(4,3,'Killer','dog','Rottweiler','2024-01-01',37.00,30.50,'I love my dog','/petowner-app/uploads/7b921b09-66fc-45d0-8495-586f30943b6e.webp','Active','2026-02-03 15:19:39','2026-02-03 17:21:47'),(6,5,'Buddy','dog','Rottweiler','2026-02-10',3.50,2.50,'Good Health','/petowner-app/uploads/1a3f0a1e-685c-4c26-954e-ed60b2c7ba89.webp','Active','2026-02-10 14:56:09','2026-02-10 14:58:31');
/*!40000 ALTER TABLE `pet` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pet_owner`
--

DROP TABLE IF EXISTS `pet_owner`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pet_owner` (
  `pet_owner_id` int(11) NOT NULL AUTO_INCREMENT,
  `full_name` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` varchar(255) NOT NULL,
  `agree` tinyint(1) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`pet_owner_id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pet_owner`
--

LOCK TABLES `pet_owner` WRITE;
/*!40000 ALTER TABLE `pet_owner` DISABLE KEYS */;
INSERT INTO `pet_owner` VALUES (1,'Nigeeth Maleesha','nigeeth@gmail.com','ef797c8118f02dfb649607dd5d3f8c7623048c9c063d532cc95c5ed7a898a64f','pet-owner',1,'2026-02-02 14:13:48'),(2,'Shasani Nisansala','shasani@gmail.com','NMmaleesha','pet-owner',1,'2026-02-02 17:54:05'),(3,'Dishan Keminda','dishan@gmail.com','12345678','pet-owner',1,'2026-02-02 18:49:29'),(4,'Nigeeth Maleesha','kamkanamlage394@gmail.com','NMnigeeth1','pet-owner',1,'2026-02-07 09:50:31'),(5,'Nigeeth Maleesha','m@gmail.com','NMnigeeth3','pet-owner',1,'2026-02-10 14:53:45');
/*!40000 ALTER TABLE `pet_owner` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vaccination`
--

DROP TABLE IF EXISTS `vaccination`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `vaccination` (
  `vaccination_id` int(11) NOT NULL AUTO_INCREMENT,
  `pet_id` int(11) NOT NULL,
  `vaccine_name` varchar(255) NOT NULL,
  `vaccination_date` date NOT NULL,
  `clinic_name` varchar(255) DEFAULT NULL,
  `veterinarian_name` varchar(255) DEFAULT NULL,
  `notes` varchar(255) DEFAULT NULL,
  `next_due_date` date DEFAULT NULL,
  `reminder_set` tinyint(1) DEFAULT '1',
  `status` varchar(255) DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`vaccination_id`),
  KEY `pet_id` (`pet_id`),
  CONSTRAINT `vaccination_ibfk_1` FOREIGN KEY (`pet_id`) REFERENCES `pet` (`pet_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vaccination`
--

LOCK TABLES `vaccination` WRITE;
/*!40000 ALTER TABLE `vaccination` DISABLE KEYS */;
INSERT INTO `vaccination` VALUES (2,1,'Rabies','2023-10-14','City Vet Clinic','Dr. Sarah Wilson','Annual booster shot','2024-10-14',1,NULL,'2026-02-03 06:20:01'),(3,1,'DHPP','2023-10-14','City Vet Clinic','Dr. Sarah Wilson','Annual booster shot','2024-10-14',1,'Completed','2026-02-03 06:20:48'),(4,3,'Rabies','2026-02-03','Zone',NULL,'Complete','2029-02-03',1,'Completed','2026-02-03 07:56:13'),(5,3,'DHPP','2026-02-28','Boom',NULL,'Due','2027-02-28',1,'Due Soon','2026-02-03 07:57:02'),(7,4,'Rabies','2026-02-01','Zone',NULL,'Completed','2029-02-01',1,'Completed','2026-02-03 17:23:41'),(8,4,'DHPP','2026-02-07','Zone',NULL,NULL,'2027-02-07',1,'Due Soon','2026-02-03 17:24:30'),(9,6,'DHPP','2026-02-10','Xnoe',NULL,'Completed','2027-02-10',1,'Completed','2026-02-10 14:56:52'),(10,6,'Rabies','2026-02-28','zone',NULL,'due soon','2029-02-28',1,'Due Soon','2026-02-10 14:57:38');
/*!40000 ALTER TABLE `vaccination` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-02-10 21:11:47
-- MySQL dump 10.13  Distrib 8.0.44, for Win64 (x86_64)
--
-- Host: localhost    Database: petcaredb
-- ------------------------------------------------------
-- Server version	5.7.44-log

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
-- Table structure for table `donation_requests`
--

DROP TABLE IF EXISTS `donation_requests`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `donation_requests` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `shelter_id` int(11) NOT NULL,
  `shelter_name` varchar(100) NOT NULL,
  `title` varchar(150) NOT NULL,
  `description` text,
  `image_url` varchar(500) DEFAULT NULL,
  `target_amount` decimal(38,2) NOT NULL,
  `current_amount` decimal(38,2) NOT NULL,
  `status` enum('OPEN','COMPLETED','CANCELLED') NOT NULL DEFAULT 'OPEN',
  `start_date` date NOT NULL,
  `end_date` date DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `donation_requests`
--

LOCK TABLES `donation_requests` WRITE;
/*!40000 ALTER TABLE `donation_requests` DISABLE KEYS */;
INSERT INTO `donation_requests` VALUES (1,1,'Shelter #1','xznjcjs','xbmnszmx','http://localhost:8080/uploads/af0e8be7-f8ab-4eff-b67d-9bd13cac02c4.jpg',500000.00,0.00,'OPEN','2026-02-07','2026-02-08','2026-02-07 13:18:15','2026-02-07 13:18:15'),(2,1,'Shelter #1','sdxcv','dxcvb','http://localhost:8080/uploads/6a8fe875-65d3-49f0-ae02-482e59069337.PNG',800000.00,0.00,'OPEN','2026-02-07','2026-02-08','2026-02-07 13:20:53','2026-02-07 13:20:53'),(3,12345678,'Shelter 12345678','dfghjm','fghjm,','',500000.00,0.00,'OPEN','2026-02-09','2026-02-18','2026-02-09 14:06:24','2026-02-09 14:06:24'),(4,1,'Happy Paws Shelter','Help Feed Our Rescued Pets','We need funds to provide food for 50 rescued dogs and cats','https://example.com/image1.jpg',5000.00,1500.00,'OPEN','2026-01-01','2026-12-31','2026-02-09 20:27:10','2026-02-09 20:27:10'),(5,2,'Safe Haven Animal Rescue','Medical Fund for Injured Animals','Emergency medical care for animals hit by vehicles','https://example.com/image2.jpg',10000.00,7000.00,'OPEN','2026-01-15','2026-06-30','2026-02-09 20:27:10','2026-02-10 09:41:56'),(6,3,'Forever Home Sanctuary','Build New Shelter Facilities','Construction of new kennels and play areas','https://example.com/image3.jpg',25000.00,12000.00,'OPEN','2026-02-01','2026-08-31','2026-02-09 20:27:10','2026-02-09 20:27:10'),(7,1,'Happy Paws Shelter','Help Feed Our Rescued Pets','We need funds to provide food for 50 rescued dogs and cats','https://example.com/image1.jpg',5000.00,1500.00,'OPEN','2026-01-01','2026-12-31','2026-02-09 20:34:50','2026-02-09 20:34:50'),(8,2,'Safe Haven Animal Rescue','Medical Fund for Injured Animals','Emergency medical care for animals hit by vehicles','https://example.com/image2.jpg',10000.00,3500.00,'OPEN','2026-01-15','2026-06-30','2026-02-09 20:34:50','2026-02-09 20:34:50'),(9,3,'Forever Home Sanctuary','Build New Shelter Facilities','Construction of new kennels and play areas','https://example.com/image3.jpg',25000.00,12000.00,'OPEN','2026-02-01','2026-08-31','2026-02-09 20:34:50','2026-02-09 20:34:50'),(10,123456789,'Shelter 123456789','sfghdjkl','sghajk','',50000.00,25000.00,'OPEN','2026-02-09','2026-02-18','2026-02-09 15:24:59','2026-02-09 15:37:56'),(11,12345678,'Shelter 12345678','dfghjk','fdghnjmjnsshhs','http://localhost:8080/uploads/346ac92a-ac0d-4d3b-bb35-05fe4d08e781.jpg',7500000.00,0.00,'OPEN','2026-02-10','2026-02-18','2026-02-09 22:06:47','2026-02-09 22:06:47'),(12,12345678,'Shelter 12345678','dfghjk','fdghnjmjnsshhs','http://localhost:8080/uploads/22df0312-82de-4354-ae60-d5b45390a56b.jpg',7500000.00,24999.00,'OPEN','2026-02-10','2026-02-18','2026-02-09 22:06:47','2026-02-09 22:10:30'),(13,12345678,'Shelter 12345678','vcbxnzka','yeuwjanzh','/images/default-donation.jpg',50000.00,0.00,'OPEN','2026-02-10','2026-02-11','2026-02-10 00:15:27','2026-02-10 00:15:27');
/*!40000 ALTER TABLE `donation_requests` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `donations`
--

DROP TABLE IF EXISTS `donations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `donations` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `donation_request_id` int(11) NOT NULL,
  `donor_name` varchar(100) DEFAULT NULL,
  `donor_email` varchar(150) DEFAULT NULL,
  `amount` decimal(10,2) NOT NULL,
  `status` enum('PENDING','SUCCESS','FAILED') NOT NULL DEFAULT 'PENDING',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `shelter_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_donations_shelter_id` (`shelter_id`),
  KEY `idx_donations_status` (`status`),
  KEY `idx_donations_created_at` (`created_at`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `donations`
--

LOCK TABLES `donations` WRITE;
/*!40000 ALTER TABLE `donations` DISABLE KEYS */;
INSERT INTO `donations` VALUES (1,1,'Alice Johnson','alice.j@example.com',50.00,'SUCCESS','2026-01-15 05:00:00',1),(2,1,'Bob Smith','bob.smith@provider.net',125.50,'SUCCESS','2026-01-16 08:50:00',1),(3,2,'Charlie Brown','charlie@webmail.com',10.00,'FAILED','2026-01-18 03:45:00',2),(4,1,'Diana Prince','diana@themyscira.io',500.00,'PENDING','2026-01-20 05:30:00',1),(5,3,'Edward Norton','ed@cinema.com',75.00,'SUCCESS','2026-01-22 13:15:00',3),(6,2,'Fiona Gallagher','fiona.g@service.org',20.00,'SUCCESS','2026-01-25 02:40:00',2),(7,4,'George Miller','george@studio.com',1000.00,'PENDING','2026-02-01 07:30:00',3),(8,3,NULL,NULL,5.00,'SUCCESS','2026-02-02 10:00:00',3),(9,10,'Dishan Keminda','dishan@gmail.com',25000.00,'SUCCESS','2026-02-09 15:37:56',123456789),(10,12,'Dishan Keminda','dishan@gmail.com',24999.00,'SUCCESS','2026-02-09 22:10:29',12345678),(11,5,'Dishan Keminda','dishan@gmail.com',3500.00,'SUCCESS','2026-02-10 09:41:56',2);
/*!40000 ALTER TABLE `donations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `payments`
--

DROP TABLE IF EXISTS `payments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `payments` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `donation_id` int(11) NOT NULL,
  `provider` enum('STRIPE') NOT NULL,
  `provider_payment_id` varchar(255) NOT NULL,
  `provider_status` varchar(255) NOT NULL,
  `amount` decimal(10,2) NOT NULL,
  `currency` varchar(10) NOT NULL DEFAULT 'USD',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payments`
--

LOCK TABLES `payments` WRITE;
/*!40000 ALTER TABLE `payments` DISABLE KEYS */;
INSERT INTO `payments` VALUES (1,2,'STRIPE','pi_3SyR7WA3rFJxEmnv1RKwNCh0','requires_payment_method',25.00,'usd','2026-02-08 00:35:28'),(2,2,'STRIPE','pi_3SyRB2A3rFJxEmnv0VpV7Lwh','succeeded',25.00,'usd','2026-02-08 00:39:02'),(3,3,'STRIPE','pi_3Sz0YwA3rFJxEmnv1XcGgdM3','requires_payment_method',2500.00,'usd','2026-02-09 14:26:05'),(4,3,'STRIPE','pi_3Sz0YxA3rFJxEmnv0LvibWZy','succeeded',2500.00,'usd','2026-02-09 14:26:05'),(5,10,'STRIPE','pi_3Sz1cbA3rFJxEmnv1jCNi95d','requires_payment_method',25000.00,'usd','2026-02-09 15:33:55'),(6,10,'STRIPE','pi_3Sz1dJA3rFJxEmnv1yHkY9ZA','requires_payment_method',25000.00,'usd','2026-02-09 15:34:38'),(7,10,'STRIPE','pi_3Sz1fqA3rFJxEmnv0qen2hYY','requires_payment_method',25000.00,'usd','2026-02-09 15:37:15'),(8,10,'STRIPE','pi_3Sz1gJA3rFJxEmnv0Ztu3toQ','requires_payment_method',25000.00,'usd','2026-02-09 15:37:44'),(9,10,'STRIPE','pi_3Sz1gRA3rFJxEmnv1eErlayU','succeeded',25000.00,'usd','2026-02-09 15:37:52'),(10,12,'STRIPE','pi_3Sz7nrA3rFJxEmnv1d3eWfvd','requires_payment_method',24999.00,'usd','2026-02-09 22:09:57'),(11,12,'STRIPE','pi_3Sz7oJA3rFJxEmnv1wpWnbbc','succeeded',24999.00,'usd','2026-02-09 22:10:25'),(12,5,'STRIPE','pi_3SzIakA3rFJxEmnv1n5UJU26','requires_payment_method',3500.00,'usd','2026-02-10 09:41:07'),(13,5,'STRIPE','pi_3SzIbVA3rFJxEmnv0f9G2C3j','succeeded',3500.00,'usd','2026-02-10 09:41:54');
/*!40000 ALTER TABLE `payments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `shelter_statistics`
--

DROP TABLE IF EXISTS `shelter_statistics`;
/*!50001 DROP VIEW IF EXISTS `shelter_statistics`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `shelter_statistics` AS SELECT 
 1 AS `shelter_id`,
 1 AS `total_donations`,
 1 AS `total_received`,
 1 AS `pending_amount`,
 1 AS `last_donation`*/;
SET character_set_client = @saved_cs_client;

--
-- Final view structure for view `shelter_statistics`
--

/*!50001 DROP VIEW IF EXISTS `shelter_statistics`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `shelter_statistics` AS select `d`.`shelter_id` AS `shelter_id`,count(distinct `d`.`id`) AS `total_donations`,coalesce(sum((case when (`d`.`status` = 'SUCCESS') then `d`.`amount` else 0 end)),0) AS `total_received`,coalesce(sum((case when (`d`.`status` = 'PENDING') then `d`.`amount` else 0 end)),0) AS `pending_amount`,max(`d`.`created_at`) AS `last_donation` from `donations` `d` group by `d`.`shelter_id` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-02-10 21:11:47
