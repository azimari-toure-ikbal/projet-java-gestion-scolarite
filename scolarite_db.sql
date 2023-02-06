-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Feb 06, 2023 at 05:27 PM
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
  `email` varchar(64) NOT NULL,
  `dtNaiss` datetime NOT NULL,
  `nationalite` varchar(40) NOT NULL,
  `echeancier` varchar(40) NOT NULL,
  `sexe` int(11) NOT NULL,
  `matricule` int(11) NOT NULL,
  PRIMARY KEY (`idApprenant`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

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
  `idClasseAppenant` int(11) NOT NULL,
  `idClasse` int(11) NOT NULL,
  `idApprenant` int(11) NOT NULL,
  KEY `idApprenant` (`idApprenant`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

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
  PRIMARY KEY (`idClasse`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `classes`
--

INSERT INTO `classes` (`idClasse`, `intitule`, `reference`, `annee`, `formation`) VALUES
(1, 'creche', 1, '2023', 'Maternelle'),
(2, 'Très Petite Section', 2, '2023', 'Maternelle'),
(3, 'Moyenne Section', 3, '2023', 'Maternelle'),
(4, 'Grande Section', 3, '2023', 'Maternelle'),
(5, 'CI', 4, '2022/2023', 'Elementaire'),
(6, 'CP', 4, '2022/2023', 'Elementaire'),
(7, 'CE1', 5, '2022/2023', 'Elementaire'),
(8, 'CE2', 5, '2022/2023', 'Elementaire'),
(9, 'CM1', 6, '2022/2023', 'Elementaire'),
(10, 'CM2', 6, '2022/2023', 'Elementaire'),
(11, 'Petite Section', 3, '2022/2023', 'Maternelle'),
(12, '3eme', 8, '2022/2023', 'College'),
(13, '4eme', 8, '2022/2023', 'College'),
(14, '5eme', 7, '2022/2023', 'College'),
(15, '6eme', 7, '2022/2023', 'College'),
(16, '1ere année', 9, '2022/2023', 'Froid/Climatisation'),
(17, '2e année', 9, '2022/2023', 'Froid/Climatisation'),
(18, '3e année', 9, '2022/2023', 'Froid/Climatisation');

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
  PRIMARY KEY (`idModule`),
  KEY `idClasse` (`idClasse`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `notes`
--

DROP TABLE IF EXISTS `notes`;
CREATE TABLE IF NOT EXISTS `notes` (
  `idNote` int(11) NOT NULL,
  `valeur` int(11) NOT NULL,
  `idApprenant` int(11) NOT NULL,
  `idModule` int(11) NOT NULL,
  KEY `idModule` (`idModule`),
  KEY `idApprenant` (`idApprenant`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `paiements`
--

DROP TABLE IF EXISTS `paiements`;
CREATE TABLE IF NOT EXISTS `paiements` (
  `idPaiement` int(11) NOT NULL,
  `montant` int(11) NOT NULL,
  `rubrique` varchar(255) NOT NULL,
  `date` datetime NOT NULL,
  `apprenant` varchar(120) NOT NULL,
  `caissier` varchar(40) NOT NULL,
  `classe` varchar(40) NOT NULL,
  `observation` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `utilisateurs`
--

INSERT INTO `utilisateurs` (`idUtilisateur`, `password`, `nom`, `prenom`, `email`, `numero`, `type`) VALUES
(1, 'fatou', 'Syla', 'Fatou', 'fatou.syla@mail.cum', '76 696 69 69', 'secretaire'),
(2, 'marcus', 'Sins', 'Marcus', 'marcus.sins@mail.com', '76 789 24 23', 'comptable'),
(3, 'wissam', 'Youssef', 'Wissam', 'wissam.youssef@mail.com', '76 138 52 88', 'administrateur');

--
-- Constraints for dumped tables
--

--
-- Constraints for table `classeapprenant`
--
ALTER TABLE `classeapprenant`
  ADD CONSTRAINT `classeapprenant_ibfk_1` FOREIGN KEY (`idApprenant`) REFERENCES `apprenants` (`idApprenant`) ON DELETE NO ACTION ON UPDATE NO ACTION;

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
