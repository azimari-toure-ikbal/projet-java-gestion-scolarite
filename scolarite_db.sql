-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Feb 24, 2023 at 11:56 PM
-- Server version: 5.7.36
-- PHP Version: 7.4.26

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `scolarite_db`
--

-- --------------------------------------------------------

--
-- Table structure for table `apprenants`
--

DROP TABLE IF EXISTS `apprenants`;
CREATE TABLE IF NOT EXISTS `apprenants` (
  `idApprenant` int(11) NOT NULL AUTO_INCREMENT,
  `prenom` varchar(64) NOT NULL,
  `nom` varchar(64) NOT NULL,
  `dtNaiss` date NOT NULL,
  `nationalite` varchar(40) NOT NULL,
  `echeancier` int(40) NOT NULL DEFAULT '0',
  `sexe` varchar(2) NOT NULL,
  `matricule` int(11) NOT NULL,
  PRIMARY KEY (`idApprenant`),
  UNIQUE KEY `matricule` (`matricule`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `apprenants`
--

INSERT INTO `apprenants` (`idApprenant`, `prenom`, `nom`, `dtNaiss`, `nationalite`, `echeancier`, `sexe`, `matricule`) VALUES
(7, 'Ikbal', 'AZIMARI TOURE', '2002-12-27', 'Togolaise', 2, 'M', 101);

-- --------------------------------------------------------

--
-- Table structure for table `archives`
--

DROP TABLE IF EXISTS `archives`;
CREATE TABLE IF NOT EXISTS `archives` (
  `idAnnee` int(11) NOT NULL,
  `fichier` varchar(69) NOT NULL,
  `annee` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `candidat`
--

DROP TABLE IF EXISTS `candidat`;
CREATE TABLE IF NOT EXISTS `candidat` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` int(11) NOT NULL,
  `prenom` varchar(50) NOT NULL,
  `nom` varchar(50) NOT NULL,
  `niveau_etude` varchar(50) NOT NULL,
  `examen_prepare` varchar(50) NOT NULL,
  `ecole_origine` varchar(50) NOT NULL,
  `adresse` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `classeapprenant`
--

DROP TABLE IF EXISTS `classeapprenant`;
CREATE TABLE IF NOT EXISTS `classeapprenant` (
  `idClasseAppenant` int(11) NOT NULL AUTO_INCREMENT,
  `idClasse` int(11) NOT NULL,
  `idApprenant` int(11) NOT NULL,
  PRIMARY KEY (`idClasseAppenant`),
  KEY `classeapprenant_ibfk_1` (`idApprenant`),
  KEY `classeapprenant_ibfk_2` (`idClasse`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `classeapprenant`
--

INSERT INTO `classeapprenant` (`idClasseAppenant`, `idClasse`, `idApprenant`) VALUES
(25, 15, 40),
(26, 15, 1),
(27, 14, 2),
(28, 14, 3),
(29, 14, 4),
(30, 14, 5),
(31, 14, 6),
(32, 15, 7);

-- --------------------------------------------------------

--
-- Table structure for table `classes`
--

DROP TABLE IF EXISTS `classes`;
CREATE TABLE IF NOT EXISTS `classes` (
  `idClasse` int(11) NOT NULL AUTO_INCREMENT,
  `intitule` varchar(40) NOT NULL,
  `reference` int(11) NOT NULL,
  `annee` varchar(12) NOT NULL,
  `formation` varchar(36) NOT NULL,
  `views` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`idClasse`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `classes`
--

INSERT INTO `classes` (`idClasse`, `intitule`, `reference`, `annee`, `formation`, `views`) VALUES
(1, 'Crèche', 1, '2022-2023', 'Maternelle', '2023-02-15 19:45:07'),
(2, 'Très Petite Section', 2, '2022-2023', 'Maternelle', '2023-02-19 20:30:54'),
(3, 'Petite Section', 3, '2022/2023', 'Maternelle', '2023-02-15 19:45:07'),
(4, 'Moyenne Section', 3, '2022-2023', 'Maternelle', '2023-02-18 21:02:48'),
(5, 'Grande Section', 3, '2022-2023', 'Maternelle', '2023-02-15 19:45:07'),
(6, 'CI', 4, '2022/2023', 'Elementaire', '2023-02-15 19:45:07'),
(7, 'CP', 4, '2022/2023', 'Elementaire', '2023-02-15 19:45:07'),
(8, 'CE1', 5, '2022/2023', 'Elementaire', '2023-02-15 19:45:07'),
(9, 'CE2', 5, '2022/2023', 'Elementaire', '2023-02-15 19:45:07'),
(10, 'CM1', 6, '2022/2023', 'Elementaire', '2023-02-15 19:45:07'),
(11, 'CM2', 6, '2022/2023', 'Elementaire', '2023-02-15 19:45:07'),
(12, '6eme', 7, '2022/2023', 'College', '2023-02-18 21:02:50'),
(13, '5eme', 7, '2022/2023', 'College', '2023-02-15 19:45:07'),
(14, '4eme', 8, '2022/2023', 'College', '2023-02-23 19:57:17'),
(15, '3eme', 8, '2022/2023', 'College', '2023-02-24 20:48:25'),
(16, '1ere année', 9, '2022/2023', 'Froid/Climatisation', '2023-02-15 19:45:07'),
(17, '2e année', 9, '2022/2023', 'Froid/Climatisation', '2023-02-15 19:45:07'),
(18, '3e année', 9, '2022/2023', 'Froid/Climatisation', '2023-02-19 20:29:16');

-- --------------------------------------------------------

--
-- Table structure for table `echeancier`
--

DROP TABLE IF EXISTS `echeancier`;
CREATE TABLE IF NOT EXISTS `echeancier` (
  `idEcheancier` int(11) NOT NULL AUTO_INCREMENT,
  `intitule` varchar(50) NOT NULL,
  `date` date NOT NULL,
  `idClasse` int(11) NOT NULL,
  PRIMARY KEY (`idEcheancier`),
  KEY `ref_classe_rk` (`idClasse`)
) ENGINE=InnoDB AUTO_INCREMENT=172 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `echeancier`
--

INSERT INTO `echeancier` (`idEcheancier`, `intitule`, `date`, `idClasse`) VALUES
(1, 'octobre', '2022-10-01', 1),
(2, 'novembre', '2022-11-01', 1),
(3, 'decembre', '2022-12-01', 1),
(4, 'janvier', '2023-01-01', 1),
(5, 'fevrier', '2023-02-01', 1),
(6, 'mars', '2023-02-01', 1),
(7, 'avril', '2023-02-01', 1),
(8, 'mai', '2023-02-01', 1),
(9, 'juin', '2023-02-01', 1),
(10, 'octobre', '2022-10-01', 2),
(11, 'novembre', '2022-11-01', 2),
(12, 'decembre', '2022-12-01', 2),
(13, 'janvier', '2023-01-01', 2),
(14, 'fevrier', '2023-02-01', 2),
(15, 'mars', '2023-02-01', 2),
(16, 'avril', '2023-02-01', 2),
(17, 'mai', '2023-02-01', 2),
(18, 'juin', '2023-02-01', 2),
(19, 'octobre', '2022-10-01', 3),
(20, 'novembre', '2022-11-01', 3),
(21, 'decembre', '2022-12-01', 3),
(22, 'janvier', '2023-01-01', 3),
(23, 'fevrier', '2023-02-01', 3),
(24, 'mars', '2023-02-01', 3),
(25, 'avril', '2023-02-01', 3),
(26, 'mai', '2023-02-01', 3),
(27, 'juin', '2023-02-01', 3),
(28, 'octobre', '2022-10-01', 4),
(29, 'novembre', '2022-11-01', 4),
(30, 'decembre', '2022-12-01', 4),
(31, 'janvier', '2023-01-01', 4),
(32, 'fevrier', '2023-02-01', 4),
(33, 'mars', '2023-02-01', 4),
(34, 'avril', '2023-02-01', 4),
(35, 'mai', '2023-02-01', 4),
(36, 'juin', '2023-02-01', 4),
(37, 'octobre', '2022-10-01', 5),
(38, 'novembre', '2022-11-01', 5),
(39, 'decembre', '2022-12-01', 5),
(40, 'janvier', '2023-01-01', 5),
(41, 'fevrier', '2023-02-01', 5),
(42, 'mars', '2023-02-01', 5),
(43, 'avril', '2023-02-01', 5),
(44, 'mai', '2023-02-01', 5),
(45, 'juin', '2023-02-01', 5),
(46, 'octobre', '2022-10-01', 5),
(47, 'novembre', '2022-11-01', 5),
(48, 'decembre', '2022-12-01', 5),
(49, 'janvier', '2023-01-01', 5),
(50, 'fevrier', '2023-02-01', 5),
(51, 'mars', '2023-02-01', 5),
(52, 'avril', '2023-02-01', 5),
(53, 'mai', '2023-02-01', 5),
(54, 'juin', '2023-02-01', 5),
(55, 'octobre', '2022-10-01', 6),
(56, 'novembre', '2022-11-01', 6),
(57, 'decembre', '2022-12-01', 6),
(58, 'janvier', '2023-01-01', 6),
(59, 'fevrier', '2023-02-01', 6),
(60, 'mars', '2023-02-01', 6),
(61, 'avril', '2023-02-01', 6),
(62, 'mai', '2023-02-01', 6),
(63, 'juin', '2023-02-01', 6),
(64, 'octobre', '2022-10-01', 7),
(65, 'novembre', '2022-11-01', 7),
(66, 'decembre', '2022-12-01', 7),
(67, 'janvier', '2023-01-01', 7),
(68, 'fevrier', '2023-02-01', 7),
(69, 'mars', '2023-02-01', 7),
(70, 'avril', '2023-02-01', 7),
(71, 'mai', '2023-02-01', 7),
(72, 'juin', '2023-02-01', 7),
(73, 'octobre', '2022-10-01', 8),
(74, 'novembre', '2022-11-01', 8),
(75, 'decembre', '2022-12-01', 8),
(76, 'janvier', '2023-01-01', 8),
(77, 'fevrier', '2023-02-01', 8),
(78, 'mars', '2023-02-01', 8),
(79, 'avril', '2023-02-01', 8),
(80, 'mai', '2023-02-01', 8),
(81, 'juin', '2023-02-01', 8),
(82, 'octobre', '2022-10-01', 9),
(83, 'novembre', '2022-11-01', 9),
(84, 'decembre', '2022-12-01', 9),
(85, 'janvier', '2023-01-01', 9),
(86, 'fevrier', '2023-02-01', 9),
(87, 'mars', '2023-02-01', 9),
(88, 'avril', '2023-02-01', 9),
(89, 'mai', '2023-02-01', 9),
(90, 'juin', '2023-02-01', 9),
(91, 'octobre', '2022-10-01', 10),
(92, 'novembre', '2022-11-01', 10),
(93, 'decembre', '2022-12-01', 10),
(94, 'janvier', '2023-01-01', 10),
(95, 'fevrier', '2023-02-01', 10),
(96, 'mars', '2023-02-01', 10),
(97, 'avril', '2023-02-01', 10),
(98, 'mai', '2023-02-01', 10),
(99, 'juin', '2023-02-01', 10),
(100, 'octobre', '2022-10-01', 11),
(101, 'novembre', '2022-11-01', 11),
(102, 'decembre', '2022-12-01', 11),
(103, 'janvier', '2023-01-01', 11),
(104, 'fevrier', '2023-02-01', 11),
(105, 'mars', '2023-02-01', 11),
(106, 'avril', '2023-02-01', 11),
(107, 'mai', '2023-02-01', 11),
(108, 'juin', '2023-02-01', 11),
(109, 'octobre', '2022-10-01', 12),
(110, 'novembre', '2022-11-01', 12),
(111, 'decembre', '2022-12-01', 12),
(112, 'janvier', '2023-01-01', 12),
(113, 'fevrier', '2023-02-01', 12),
(114, 'mars', '2023-02-01', 12),
(115, 'avril', '2023-02-01', 12),
(116, 'mai', '2023-02-01', 12),
(117, 'juin', '2023-02-01', 12),
(118, 'octobre', '2022-10-01', 13),
(119, 'novembre', '2022-11-01', 13),
(120, 'decembre', '2022-12-01', 13),
(121, 'janvier', '2023-01-01', 13),
(122, 'fevrier', '2023-02-01', 13),
(123, 'mars', '2023-02-01', 13),
(124, 'avril', '2023-02-01', 13),
(125, 'mai', '2023-02-01', 13),
(126, 'juin', '2023-02-01', 13),
(127, 'octobre', '2022-10-01', 14),
(128, 'novembre', '2022-11-01', 14),
(129, 'decembre', '2022-12-01', 14),
(130, 'janvier', '2023-01-01', 14),
(131, 'fevrier', '2023-02-01', 14),
(132, 'mars', '2023-02-01', 14),
(133, 'avril', '2023-02-01', 14),
(134, 'mai', '2023-02-01', 14),
(135, 'juin', '2023-02-01', 14),
(136, 'octobre', '2022-10-01', 15),
(137, 'novembre', '2022-11-01', 15),
(138, 'decembre', '2022-12-01', 15),
(139, 'janvier', '2023-01-01', 15),
(140, 'fevrier', '2023-02-01', 15),
(141, 'mars', '2023-02-01', 15),
(142, 'avril', '2023-02-01', 15),
(143, 'mai', '2023-02-01', 15),
(144, 'juin', '2023-02-01', 15),
(145, 'octobre', '2022-10-01', 16),
(146, 'novembre', '2022-11-01', 16),
(147, 'decembre', '2022-12-01', 16),
(148, 'janvier', '2023-01-01', 16),
(149, 'fevrier', '2023-02-01', 16),
(150, 'mars', '2023-02-01', 16),
(151, 'avril', '2023-02-01', 16),
(152, 'mai', '2023-02-01', 16),
(153, 'juin', '2023-02-01', 16),
(154, 'octobre', '2022-10-01', 17),
(155, 'novembre', '2022-11-01', 17),
(156, 'decembre', '2022-12-01', 17),
(157, 'janvier', '2023-01-01', 17),
(158, 'fevrier', '2023-02-01', 17),
(159, 'mars', '2023-02-01', 17),
(160, 'avril', '2023-02-01', 17),
(161, 'mai', '2023-02-01', 17),
(162, 'juin', '2023-02-01', 17),
(163, 'octobre', '2022-10-01', 18),
(164, 'novembre', '2022-11-01', 18),
(165, 'decembre', '2022-12-01', 18),
(166, 'janvier', '2023-01-01', 18),
(167, 'fevrier', '2023-02-01', 18),
(168, 'mars', '2023-02-01', 18),
(169, 'avril', '2023-02-01', 18),
(170, 'mai', '2023-02-01', 18),
(171, 'juin', '2023-02-01', 18);

-- --------------------------------------------------------

--
-- Table structure for table `modules`
--

DROP TABLE IF EXISTS `modules`;
CREATE TABLE IF NOT EXISTS `modules` (
  `idModule` int(11) NOT NULL AUTO_INCREMENT,
  `intitule` varchar(40) NOT NULL,
  `idClasse` int(11) NOT NULL,
  `semestre` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`idModule`),
  KEY `idClasse` (`idClasse`)
) ENGINE=InnoDB AUTO_INCREMENT=170 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `modules`
--

INSERT INTO `modules` (`idModule`, `intitule`, `idClasse`, `semestre`) VALUES
(11, 'Maths', 15, 1),
(12, 'Français', 15, 1),
(13, 'Histo/Geo', 15, 1),
(14, 'Physique-Chimie', 15, 1),
(15, 'Science de la vie et de la terre', 15, 1),
(16, 'Education physique', 15, 1),
(17, 'Anglais', 15, 1),
(18, 'Maths', 12, 1),
(19, 'Histo/Geo', 12, 1),
(20, 'Anglais', 12, 1),
(21, 'Science de la vie et de la terre', 12, 1),
(22, 'Education physique', 12, 1),
(23, 'Physique-Chimie', 12, 1),
(24, 'Français', 12, 1),
(25, 'Maths', 12, 2),
(26, 'Histo/Geo', 12, 2),
(27, 'Anglais', 12, 2),
(28, 'Science de la vie et de la terre', 12, 2),
(29, 'Education physique', 12, 2),
(30, 'Physique-Chimie', 12, 2),
(31, 'Français', 12, 2),
(32, 'Maths', 15, 2),
(33, 'Histo/Geo', 15, 2),
(34, 'Anglais', 15, 2),
(35, 'Science de la vie et de la terre', 15, 2),
(36, 'Education physique', 15, 2),
(37, 'Physique-Chimie', 15, 2),
(38, 'Français', 15, 2),
(39, 'Maths', 13, 2),
(40, 'Histo/Geo', 13, 2),
(41, 'Anglais', 13, 2),
(42, 'Science de la vie et de la terre', 13, 2),
(43, 'Education physique', 13, 2),
(44, 'Physique-Chimie', 13, 2),
(45, 'Français', 13, 2),
(46, 'Maths', 13, 1),
(47, 'Histo/Geo', 13, 1),
(48, 'Anglais', 13, 1),
(49, 'Science de la vie et de la terre', 13, 1),
(50, 'Education physique', 13, 1),
(51, 'Physique-Chimie', 13, 1),
(52, 'Français', 13, 1),
(53, 'Maths', 14, 2),
(54, 'Histo/Geo', 14, 2),
(55, 'Anglais', 14, 2),
(56, 'Science de la vie et de la terre', 14, 2),
(57, 'Education physique', 14, 2),
(58, 'Physique-Chimie', 14, 2),
(59, 'Français', 14, 2),
(60, 'Maths', 14, 1),
(61, 'Histo/Geo', 14, 1),
(62, 'Anglais', 14, 1),
(63, 'Science de la vie et de la terre', 14, 1),
(64, 'Education physique', 14, 1),
(65, 'Physique-Chimie', 14, 1),
(66, 'Français', 14, 1),
(81, 'Maths', 7, 1),
(82, 'Français', 7, 1),
(83, 'Sciences', 7, 1),
(84, 'Histo/Geo', 7, 1),
(85, 'Savoir vivre', 7, 1),
(86, 'Education artistique', 7, 1),
(87, 'Anglais', 7, 1),
(88, 'Maths', 7, 2),
(89, 'Français', 7, 2),
(90, 'Sciences', 7, 2),
(91, 'Histo/Geo', 7, 2),
(92, 'Savoir vivre', 7, 2),
(93, 'Education artistique', 7, 2),
(94, 'Anglais', 7, 2),
(95, 'Maths', 8, 1),
(96, 'Français', 8, 1),
(97, 'Sciences', 8, 1),
(98, 'Histo/Geo', 8, 1),
(99, 'Savoir vivre', 8, 1),
(100, 'Education artistique', 8, 1),
(101, 'Anglais', 8, 1),
(102, 'Maths', 8, 2),
(103, 'Français', 8, 2),
(104, 'Sciences', 8, 2),
(105, 'Histo/Geo', 8, 2),
(106, 'Savoir vivre', 8, 2),
(107, 'Education artistique', 8, 2),
(108, 'Anglais', 8, 2),
(109, 'Maths', 9, 1),
(110, 'Français', 9, 1),
(111, 'Sciences', 9, 1),
(112, 'Histo/Geo', 9, 1),
(113, 'Savoir vivre', 9, 1),
(114, 'Education artistique', 9, 1),
(115, 'Anglais', 9, 1),
(116, 'Maths', 9, 2),
(117, 'Français', 9, 2),
(118, 'Sciences', 9, 2),
(119, 'Histo/Geo', 9, 2),
(120, 'Savoir vivre', 9, 2),
(121, 'Education artistique', 9, 2),
(122, 'Anglais', 9, 2),
(123, 'Maths', 10, 1),
(124, 'Français', 10, 1),
(125, 'Sciences', 10, 1),
(126, 'Histo/Geo', 10, 1),
(127, 'Savoir vivre', 10, 1),
(128, 'Education artistique', 10, 1),
(129, 'Anglais', 10, 1),
(130, 'Maths', 10, 2),
(131, 'Français', 10, 2),
(132, 'Sciences', 10, 2),
(133, 'Histo/Geo', 10, 2),
(134, 'Savoir vivre', 10, 2),
(135, 'Education artistique', 10, 2),
(136, 'Anglais', 10, 2),
(137, 'Maths', 11, 1),
(138, 'Français', 11, 1),
(139, 'Sciences', 11, 1),
(140, 'Histo/Geo', 11, 1),
(141, 'Savoir vivre', 11, 1),
(142, 'Education artistique', 11, 1),
(143, 'Anglais', 11, 1),
(144, 'Maths', 11, 2),
(145, 'Français', 11, 2),
(146, 'Sciences', 11, 2),
(147, 'Histo/Geo', 11, 2),
(148, 'Savoir vivre', 11, 2),
(149, 'Education artistique', 11, 2),
(150, 'Anglais', 11, 2),
(159, 'Philosophie', 14, 1),
(160, 'Espagnol', 14, 2),
(162, 'Musique', 14, 2),
(163, 'Psychologie', 15, 2),
(164, 'Musique', 15, 1),
(165, 'Dessin', 15, 1),
(166, 'Burautique', 14, 1),
(167, 'Informatique', 15, 2),
(168, 'TP Couple', 15, 2),
(169, 'TP Couple', 15, 2);

-- --------------------------------------------------------

--
-- Table structure for table `notes`
--

DROP TABLE IF EXISTS `notes`;
CREATE TABLE IF NOT EXISTS `notes` (
  `idNote` int(11) NOT NULL AUTO_INCREMENT,
  `valeur` float NOT NULL,
  `idApprenant` int(11) NOT NULL,
  `idModule` int(11) NOT NULL,
  PRIMARY KEY (`idNote`),
  KEY `idModule` (`idModule`),
  KEY `idApprenant` (`idApprenant`)
) ENGINE=InnoDB AUTO_INCREMENT=147 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `notes`
--

INSERT INTO `notes` (`idNote`, `valeur`, `idApprenant`, `idModule`) VALUES
(113, 0, 7, 11),
(114, 0, 7, 12),
(115, 0, 7, 13),
(116, 0, 7, 14),
(117, 0, 7, 15),
(118, 0, 7, 16),
(119, 0, 7, 17),
(120, 0, 7, 32),
(121, 0, 7, 33),
(122, 0, 7, 34),
(123, 0, 7, 35),
(124, 0, 7, 36),
(125, 0, 7, 37),
(126, 0, 7, 38),
(141, 0, 7, 163),
(142, 0, 7, 162),
(143, 0, 7, 165),
(144, 0, 7, 167),
(145, 0, 7, 168),
(146, 0, 7, 168);

-- --------------------------------------------------------

--
-- Table structure for table `notifications`
--

DROP TABLE IF EXISTS `notifications`;
CREATE TABLE IF NOT EXISTS `notifications` (
  `idNotification` int(11) NOT NULL AUTO_INCREMENT,
  `utilisateur` varchar(120) NOT NULL,
  `admin` varchar(120) NOT NULL,
  `date` datetime NOT NULL,
  `message` text NOT NULL,
  `seen` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`idNotification`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `notifications`
--

INSERT INTO `notifications` (`idNotification`, `utilisateur`, `admin`, `date`, `message`, `seen`) VALUES
(2, 'Natasha Nice', 'Angela White', '2023-02-21 16:30:07', 'This is actually a test', 0),
(3, 'Sophie Dee', 'Marshall', '2023-02-24 20:20:05', 'L\'action ADD sur l\'apprenant Jamie Bowen a été annulée par l\'administrateur Marshall le 2023-02-24T20:20:05.075698.', 0);

-- --------------------------------------------------------

--
-- Table structure for table `paiements`
--

DROP TABLE IF EXISTS `paiements`;
CREATE TABLE IF NOT EXISTS `paiements` (
  `idPaiement` int(11) NOT NULL AUTO_INCREMENT,
  `numeroRecu` varchar(40) NOT NULL,
  `montant` int(11) NOT NULL,
  `rubrique` varchar(255) NOT NULL,
  `date` datetime NOT NULL,
  `idApprenant` int(11) NOT NULL,
  `caissier` varchar(40) NOT NULL,
  `classe` varchar(40) NOT NULL,
  `observation` text NOT NULL,
  `apprenant` varchar(110) NOT NULL,
  PRIMARY KEY (`idPaiement`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `paiements`
--

INSERT INTO `paiements` (`idPaiement`, `numeroRecu`, `montant`, `rubrique`, `date`, `idApprenant`, `caissier`, `classe`, `observation`, `apprenant`) VALUES
(1, 'RCU1677182', 88000, 'inscription', '2023-02-23 00:00:00', 7, 'Marcus Sins', '3eme', 'paiement d\'inscription', 'Ikbal AZIMARI TOURE'),
(2, 'RCU1677182', 28000, 'scolarite november', '2023-02-23 00:00:00', 7, 'Marcus Sins', '3eme', '', 'Ikbal AZIMARI TOURE'),
(3, 'RCU1677182', 30000, 'tenue', '2023-02-23 00:00:00', 7, 'Marcus Sins', '3eme', '', 'Ikbal AZIMARI TOURE'),
(4, 'RCU1677182', 28000, 'scolarite december', '2023-02-23 00:00:00', 7, 'Marcus Sins', '3eme', '', 'Ikbal AZIMARI TOURE'),
(5, 'RCU1677182', 28000, 'scolarite january', '2023-02-23 00:00:00', 7, 'Marcus Sins', '3eme', '', 'Ikbal AZIMARI TOURE'),
(6, 'RCU1677279', 1000, 'scolarite february', '2023-02-24 00:00:00', 7, 'Violet Myers', '3eme', 'Paiement de scolarite', 'Ikbal AZIMARI TOURE'),
(7, 'RCU726', 1000, 'scolarite february', '2023-02-24 00:00:00', 7, 'Violet Myers', '3eme', 'Paiement de scolarite', 'Ikbal AZIMARI TOURE'),
(8, 'RCU47', 1000, 'scolarite november', '2023-02-24 00:00:00', 7, 'Violet Myers', '3eme', 'Paiement de scolarite', 'Ikbal AZIMARI TOURE');

-- --------------------------------------------------------

--
-- Table structure for table `rubriques`
--

DROP TABLE IF EXISTS `rubriques`;
CREATE TABLE IF NOT EXISTS `rubriques` (
  `idRubrique` int(11) NOT NULL AUTO_INCREMENT,
  `droitInscription` int(11) NOT NULL,
  `scolarite` int(11) NOT NULL,
  `album` int(11) NOT NULL,
  `tenue` int(11) NOT NULL,
  `fraisGeneraux` int(11) NOT NULL,
  `cotisationAPE` int(11) NOT NULL,
  `inscription` int(11) NOT NULL,
  `reference` int(11) NOT NULL,
  PRIMARY KEY (`idRubrique`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `rubriques`
--

INSERT INTO `rubriques` (`idRubrique`, `droitInscription`, `scolarite`, `album`, `tenue`, `fraisGeneraux`, `cotisationAPE`, `inscription`, `reference`) VALUES
(1, 75000, 50000, 2000, 0, 0, 3000, 130000, 1),
(2, 17000, 23000, 2000, 10000, 10000, 3000, 65000, 2),
(3, 17000, 16000, 2000, 10000, 10000, 3000, 58000, 3),
(4, 17000, 15000, 0, 30000, 10000, 3000, 75000, 4),
(5, 17000, 16000, 0, 30000, 10000, 3000, 75000, 5),
(6, 17000, 21500, 0, 30000, 10000, 3000, 76000, 6),
(7, 17000, 23000, 0, 30000, 10000, 3000, 83000, 7),
(8, 17000, 28000, 0, 30000, 10000, 3000, 88000, 8),
(9, 17000, 15000, 0, 0, 10000, 3000, 52000, 9);

-- --------------------------------------------------------

--
-- Table structure for table `utilisateurs`
--

DROP TABLE IF EXISTS `utilisateurs`;
CREATE TABLE IF NOT EXISTS `utilisateurs` (
  `idUtilisateur` int(11) NOT NULL AUTO_INCREMENT,
  `password` varchar(256) NOT NULL,
  `nom` varchar(40) NOT NULL,
  `prenom` varchar(40) NOT NULL,
  `email` varchar(64) NOT NULL,
  `numero` varchar(20) NOT NULL,
  `type` varchar(40) NOT NULL,
  PRIMARY KEY (`idUtilisateur`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `utilisateurs`
--

INSERT INTO `utilisateurs` (`idUtilisateur`, `password`, `nom`, `prenom`, `email`, `numero`, `type`) VALUES
(1, '512b41e4c50d5bf81260c0348f2b95c6356b0337dd20363627597fc55ce5cfa9:37105d758e128be84847a52aaaecdb1b', 'Syla', 'Fatou', 'fatou.syla@mail.cum', '76 696 69 69', 'secretaire'),
(2, '5d268fe52f312d201fd1b9cd25e4682322b9e4dfa6bab5b4f26e8585589a8770:4546ec6fa470364096832fcbf20fc7be', 'Sins', 'Marcus', 'marcus.sins@mail.com', '76 789 24 23', 'caissier'),
(3, '0daa9c161a86a038406703c08c6521b1dba5b232d9cff24b393c4e883b594f0d:2af138cf75363f68398a1fbacf1e3812', 'Youssef', 'Wissam', 'wissam.youssef@mail.com', '76 138 52 88', 'administrateur'),
(4, 'admin', 'admin', 'admin', 'admin@mail.com', '77 111 11 11', 'administrateur'),
(5, 'secretaire', 'Test', 'Secretaire', 'test@mail.com', '77 222 22 22', 'secretaire'),
(6, 'caissier', 'Test', 'Caissier', 'test@mail.com', '77 333 33 33', 'caissier'),
(7, '21e9b99b9683d2751c3bcd9aeec0a9a117f1cbe2a9b040878f27afe89368c3c1:0a03936c9b9bbf2823b1124c009f0f3c', 'GREY', 'Owen', 'owen@gmail.com', '+771234567', 'caissier');

--
-- Constraints for dumped tables
--

--
-- Constraints for table `classeapprenant`
--
ALTER TABLE `classeapprenant`
  ADD CONSTRAINT `classeapprenant_ibfk_2` FOREIGN KEY (`idClasse`) REFERENCES `classes` (`idClasse`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `echeancier`
--
ALTER TABLE `echeancier`
  ADD CONSTRAINT `ref_classe_rk` FOREIGN KEY (`idClasse`) REFERENCES `classes` (`idClasse`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `modules`
--
ALTER TABLE `modules`
  ADD CONSTRAINT `modules_ibfk_1` FOREIGN KEY (`idClasse`) REFERENCES `classes` (`idClasse`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `notes`
--
ALTER TABLE `notes`
  ADD CONSTRAINT `notes_ibfk_1` FOREIGN KEY (`idModule`) REFERENCES `modules` (`idModule`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `notes_ibfk_2` FOREIGN KEY (`idApprenant`) REFERENCES `apprenants` (`idApprenant`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
