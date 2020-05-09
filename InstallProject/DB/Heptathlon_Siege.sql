-- phpMyAdmin SQL Dump
-- version 4.6.6deb5
-- https://www.phpmyadmin.net/
--
-- Client :  localhost:3306
-- Généré le :  Sam 09 Mai 2020 à 14:05
-- Version du serveur :  5.7.30-0ubuntu0.18.04.1
-- Version de PHP :  7.2.24-0ubuntu0.18.04.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données :  `Heptathlon_Siege`
--

-- --------------------------------------------------------

--
-- Structure de la table `Article`
--

CREATE TABLE `Article` (
  `Reference` varchar(11) NOT NULL,
  `Price` double NOT NULL,
  `Description` varchar(300) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Contenu de la table `Article`
--

INSERT INTO `Article` (`Reference`, `Price`, `Description`) VALUES
('F00001', 10, 'F00001 description'),
('F00002', 20, 'F00002 description'),
('F00003', 30, 'F00003 description'),
('F00004', 40, 'F00004 description'),
('F00005', 50, 'F00005 description'),
('L00001', 20, 'L00001 description'),
('L00002', 30, 'L00002 description'),
('L00003', 40, 'L00003 description'),
('L00004', 50, 'L00004 description'),
('L00005', 60, 'L00005 description'),
('R00001', 20, 'R00001 description'),
('R00002', 30, 'R00002 description'),
('R00003', 40, 'R00003 description'),
('R00004', 50, 'R00004 description'),
('R00005', 60, 'R00005 description');

-- --------------------------------------------------------

--
-- Structure de la table `Bill`
--

CREATE TABLE `Bill` (
  `IDBill` varchar(100) NOT NULL,
  `Shop` varchar(100) NOT NULL,
  `Date` varchar(100) NOT NULL,
  `Total` float NOT NULL,
  `Payment` varchar(100) NOT NULL,
  `Paid` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `Bill_Details`
--

CREATE TABLE `Bill_Details` (
  `IDBill` varchar(100) NOT NULL,
  `Reference` varchar(100) NOT NULL,
  `Quantity` int(11) NOT NULL,
  `Price` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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
('Longboard', 'L00001'),
('Longboard', 'L00002'),
('Longboard', 'L00003'),
('Longboard', 'L00004'),
('Longboard', 'L00005'),
('Rugby', 'R00001'),
('Rugby', 'R00002'),
('Rugby', 'R00003'),
('Rugby', 'R00004'),
('Rugby', 'R00005');

-- --------------------------------------------------------

--
-- Structure de la table `Shop`
--

CREATE TABLE `Shop` (
  `Name` varchar(100) NOT NULL,
  `Reference` varchar(11) NOT NULL,
  `Stock` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Contenu de la table `Shop`
--

INSERT INTO `Shop` (`Name`, `Reference`, `Stock`) VALUES
('shop1', 'F00001', 100),
('shop1', 'F00002', 120),
('shop1', 'F00003', 140),
('shop1', 'F00004', 160),
('shop1', 'F00005', 180),
('shop1', 'R00001', 100),
('shop1', 'R00002', 120),
('shop1', 'R00003', 140),
('shop1', 'R00004', 160),
('shop1', 'R00005', 180);

--
-- Index pour les tables exportées
--

--
-- Index pour la table `Article`
--
ALTER TABLE `Article`
  ADD PRIMARY KEY (`Reference`),
  ADD KEY `Reference` (`Reference`);

--
-- Index pour la table `Bill`
--
ALTER TABLE `Bill`
  ADD PRIMARY KEY (`IDBill`),
  ADD KEY `Shop` (`Shop`);

--
-- Index pour la table `Bill_Details`
--
ALTER TABLE `Bill_Details`
  ADD KEY `IDBill` (`IDBill`),
  ADD KEY `Reference` (`Reference`);

--
-- Index pour la table `Family`
--
ALTER TABLE `Family`
  ADD PRIMARY KEY (`Reference`);

--
-- Index pour la table `Shop`
--
ALTER TABLE `Shop`
  ADD PRIMARY KEY (`Reference`),
  ADD KEY `Reference` (`Reference`),
  ADD KEY `Reference_2` (`Reference`),
  ADD KEY `Name` (`Name`);

--
-- Contraintes pour les tables exportées
--

--
-- Contraintes pour la table `Article`
--
ALTER TABLE `Article`
  ADD CONSTRAINT `Article_ibfk_1` FOREIGN KEY (`Reference`) REFERENCES `Family` (`Reference`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `Bill`
--
ALTER TABLE `Bill`
  ADD CONSTRAINT `Bill_ibfk_1` FOREIGN KEY (`Shop`) REFERENCES `Shop` (`Name`);

--
-- Contraintes pour la table `Bill_Details`
--
ALTER TABLE `Bill_Details`
  ADD CONSTRAINT `Bill_Details_ibfk_1` FOREIGN KEY (`IDBill`) REFERENCES `Bill` (`IDBill`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `Bill_Details_ibfk_2` FOREIGN KEY (`Reference`) REFERENCES `Article` (`Reference`);

--
-- Contraintes pour la table `Shop`
--
ALTER TABLE `Shop`
  ADD CONSTRAINT `Shop_ibfk_2` FOREIGN KEY (`Reference`) REFERENCES `Article` (`Reference`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
