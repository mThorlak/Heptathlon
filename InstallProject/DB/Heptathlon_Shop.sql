-- phpMyAdmin SQL Dump
-- version 4.6.6deb5
-- https://www.phpmyadmin.net/
--
-- Client :  localhost:3306
-- Généré le :  Sam 09 Mai 2020 à 14:04
-- Version du serveur :  5.7.30-0ubuntu0.18.04.1
-- Version de PHP :  7.2.24-0ubuntu0.18.04.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données :  `Heptathlon_Shop`
--

-- --------------------------------------------------------

--
-- Structure de la table `Article`
--

CREATE TABLE `Article` (
  `Reference` varchar(11) NOT NULL,
  `Price` double NOT NULL,
  `Stock` int(11) NOT NULL,
  `Description` varchar(300) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Contenu de la table `Article`
--

INSERT INTO `Article` (`Reference`, `Price`, `Stock`, `Description`) VALUES
('F00001', 10, 100, 'F00001 description'),
('F00002', 20, 120, 'F00002 description'),
('F00003', 30, 140, 'F00003 description'),
('F00004', 40, 160, 'F00004 description'),
('F00005', 50, 180, 'F00005 description'),
('R00001', 10, 100, 'R00001 description'),
('R00002', 20, 120, 'R00002 description'),
('R00003', 30, 140, 'R00003 description'),
('R00004', 40, 160, 'R00004 description'),
('R00005', 50, 180, 'R00005 description');

-- --------------------------------------------------------

--
-- Structure de la table `Family`
--

CREATE TABLE `Family` (
  `Family` varchar(100) NOT NULL,
  `Reference` varchar(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Contenu de la table `Family`
--

INSERT INTO `Family` (`Family`, `Reference`) VALUES
('Football', 'F00001'),
('Football', 'F00002'),
('Football', 'F00003'),
('Football', 'F00004'),
('Football', 'F00005'),
('Rugby', 'R00001'),
('Rugby', 'R00002'),
('Rugby', 'R00003'),
('Rugby', 'R00004'),
('Rugby', 'R00005');

--
-- Index pour les tables exportées
--

--
-- Index pour la table `Article`
--
ALTER TABLE `Article`
  ADD PRIMARY KEY (`Reference`);

--
-- Index pour la table `Family`
--
ALTER TABLE `Family`
  ADD PRIMARY KEY (`Reference`);

--
-- Contraintes pour les tables exportées
--

--
-- Contraintes pour la table `Article`
--
ALTER TABLE `Article`
  ADD CONSTRAINT `Article_ibfk_1` FOREIGN KEY (`Reference`) REFERENCES `Family` (`Reference`) ON DELETE CASCADE ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
