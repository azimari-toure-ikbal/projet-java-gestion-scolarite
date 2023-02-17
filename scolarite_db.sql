-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Feb 17, 2023 at 06:04 PM
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
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `archives`
--

DROP TABLE IF EXISTS `archives`;
CREATE TABLE IF NOT EXISTS `archives` (
  `idAnnee` int(11) NOT NULL,
  `fichier` varchar(40) NOT NULL,
  `annee` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

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
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=latin1;

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
(2, 'Très Petite Section', 2, '2022-2023', 'Maternelle', '2023-02-15 19:45:07'),
(3, 'Petite Section', 3, '2022/2023', 'Maternelle', '2023-02-15 19:45:07'),
(4, 'Moyenne Section', 3, '2022-2023', 'Maternelle', '2023-02-16 10:08:01'),
(5, 'Grande Section', 3, '2022-2023', 'Maternelle', '2023-02-15 19:45:07'),
(6, 'CI', 4, '2022/2023', 'Elementaire', '2023-02-15 19:45:07'),
(7, 'CP', 4, '2022/2023', 'Elementaire', '2023-02-15 19:45:07'),
(8, 'CE1', 5, '2022/2023', 'Elementaire', '2023-02-15 19:45:07'),
(9, 'CE2', 5, '2022/2023', 'Elementaire', '2023-02-15 19:45:07'),
(10, 'CM1', 6, '2022/2023', 'Elementaire', '2023-02-15 19:45:07'),
(11, 'CM2', 6, '2022/2023', 'Elementaire', '2023-02-15 19:45:07'),
(12, '6eme', 7, '2022/2023', 'College', '2023-02-15 20:22:29'),
(13, '5eme', 7, '2022/2023', 'College', '2023-02-15 19:45:07'),
(14, '4eme', 8, '2022/2023', 'College', '2023-02-16 08:13:29'),
(15, '3eme', 8, '2022/2023', 'College', '2023-02-16 08:13:41'),
(16, '1ere année', 9, '2022/2023', 'Froid/Climatisation', '2023-02-15 19:45:07'),
(17, '2e année', 9, '2022/2023', 'Froid/Climatisation', '2023-02-15 19:45:07'),
(18, '3e année', 9, '2022/2023', 'Froid/Climatisation', '2023-02-16 08:15:27');

-- --------------------------------------------------------

--
-- Table structure for table `echeancier`
--

DROP TABLE IF EXISTS `echeancier`;
CREATE TABLE IF NOT EXISTS `echeancier` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `intitule` varchar(40) NOT NULL,
  `date` datetime NOT NULL,
  `reference` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

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
) ENGINE=InnoDB AUTO_INCREMENT=151 DEFAULT CHARSET=latin1;

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
(150, 'Anglais', 11, 2);

-- --------------------------------------------------------

--
-- Table structure for table `notes`
--

DROP TABLE IF EXISTS `notes`;
CREATE TABLE IF NOT EXISTS `notes` (
  `idNote` int(11) NOT NULL AUTO_INCREMENT,
  `valeur` int(11) NOT NULL,
  `idApprenant` int(11) NOT NULL,
  `idModule` int(11) NOT NULL,
  PRIMARY KEY (`idNote`),
  KEY `idModule` (`idModule`),
  KEY `idApprenant` (`idApprenant`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=latin1;

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
  `apprenant` varchar(120) NOT NULL,
  `caissier` varchar(40) NOT NULL,
  `classe` varchar(40) NOT NULL,
  `observation` text NOT NULL,
  PRIMARY KEY (`idPaiement`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `paiements`
--

INSERT INTO `paiements` (`idPaiement`, `numeroRecu`, `montant`, `rubrique`, `date`, `apprenant`, `caissier`, `classe`, `observation`) VALUES
(1, '696969', 690000, 'ZGBRaaaa', '2023-02-07 20:05:15', 'Jordi El Nino', 'Violet Myers', 'RedTube', 'That was hard but juicy');

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
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `utilisateurs`
--

INSERT INTO `utilisateurs` (`idUtilisateur`, `password`, `nom`, `prenom`, `email`, `numero`, `type`) VALUES
(1, 'fatou', 'Syla', 'Fatou', 'fatou.syla@mail.cum', '76 696 69 69', 'secretaire'),
(2, 'marcus', 'Sins', 'Marcus', 'marcus.sins@mail.com', '76 789 24 23', 'caissier'),
(3, 'wissam', 'Youssef', 'Wissam', 'wissam.youssef@mail.com', '76 138 52 88', 'administrateur'),
(4, 'admin', 'admin', 'admin', 'admin@mail.com', '77 111 11 11', 'administrateur'),
(5, 'secretaire', 'Test', 'Secretaire', 'test@mail.com', '77 222 22 22', 'secretaire'),
(6, 'caissier', 'Test', 'Caissier', 'test@mail.com', '77 333 33 33', 'caissier');

--
-- Constraints for dumped tables
--

--
-- Constraints for table `classeapprenant`
--
ALTER TABLE `classeapprenant`
  ADD CONSTRAINT `classeapprenant_ibfk_2` FOREIGN KEY (`idClasse`) REFERENCES `classes` (`idClasse`) ON DELETE NO ACTION ON UPDATE NO ACTION;

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
