-- phpMyAdmin SQL Dump
-- version 5.0.4deb2~bpo10+1
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Erstellungszeit: 17. Mai 2022 um 13:29
-- Server-Version: 10.3.34-MariaDB-0+deb10u1
-- PHP-Version: 8.0.18

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Datenbank: `GastroApp`
--

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `Accounts`
--

CREATE TABLE `Accounts` (
  `ID` int(11) NOT NULL,
  `Name` varchar(50) NOT NULL,
  `Password` varchar(255) NOT NULL,
  `DepatmentID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Daten für Tabelle `Accounts`
--

INSERT INTO `Accounts` (`ID`, `Name`, `Password`, `DepatmentID`) VALUES
(1, 'Schindler', 'sebastian', 1),
(2, 'Rakutt', 'paul', 1),
(3, 'Akbulut', 'talha', 1),
(4, 'Administrator', 'admin', 1),
(8, 'Eberhardt', 'danny', 1),
(9, 'Nico', 'schuster', 1),
(10, '1', '1', 1);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `Department`
--

CREATE TABLE `Department` (
  `ID` int(11) NOT NULL,
  `Name` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Daten für Tabelle `Department`
--

INSERT INTO `Department` (`ID`, `Name`) VALUES
(1, 'Kellner');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `Orders`
--

CREATE TABLE `Orders` (
  `ID` int(11) NOT NULL,
  `AccountID` int(11) NOT NULL,
  `TabelID` int(11) NOT NULL,
  `ProductID` int(11) NOT NULL,
  `Quantity` int(11) DEFAULT NULL,
  `Paid` int(11) NOT NULL,
  `Processing` tinyint(1) NOT NULL,
  `Timestamp` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Daten für Tabelle `Orders`
--

INSERT INTO `Orders` (`ID`, `AccountID`, `TabelID`, `ProductID`, `Quantity`, `Paid`, `Processing`, `Timestamp`) VALUES
(409, 10, 1, 44, 1, 0, 0, '2022-05-17 07:40:00'),
(410, 10, 1, 56, 1, 0, 0, '2022-05-17 07:39:45'),
(411, 10, 2, 68, 1, 0, 0, '2022-05-17 07:40:00'),
(412, 10, 2, 24, 1, 0, 0, '2022-05-17 07:40:01'),
(413, 10, 2, 12, 1, 0, 0, '2022-05-17 07:40:01');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `Products`
--

CREATE TABLE `Products` (
  `ID` int(11) NOT NULL,
  `TypeID` int(11) NOT NULL,
  `Name` varchar(50) NOT NULL,
  `Price` int(11) NOT NULL,
  `Description` varchar(255) DEFAULT NULL,
  `Available` tinyint(1) DEFAULT NULL,
  `Weight` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Daten für Tabelle `Products`
--

INSERT INTO `Products` (`ID`, `TypeID`, `Name`, `Price`, `Description`, `Available`, `Weight`) VALUES
(1, 1, 'Miesmuscheln mit Knoblauch Dip', 7, 'Muscheln mit einem besonderem Knoblauch Dip.', 1, 1),
(2, 2, 'Schwarze Pasta mit Flusskrebsen', 10, 'Eine der beliebtesten Pasta-Zubereitungen in Italien, feine schwarze Pasta mit Flusskrebsen aus der Region.', 1, 2),
(3, 3, 'Teramisu mit Amaretto', 5, 'Einer unseren besten desserts im unseren Hause.', 1, 3),
(4, 4, 'Virgin Mojito (alkoholfrei)', 4, 'Alkoholfreies Cocktail rezept mit Minze.', 1, 4),
(5, 1, 'Amerikanisches Fingerfood', 5, 'Geräucherte Würstchen mit einer Home-Style Soße.', 1, 1),
(6, 2, 'Hof Bürger', 10, 'Beef-Bürger mit Kraut aus der Umgebung.', 1, 2),
(7, 3, 'Schokopudding', 5, 'Standard Dessert mit schokolade.', 1, 3),
(8, 4, 'Cola', 4, 'Sponsored by Coca Cola.', 1, 4),
(9, 1, 'Salat Hofer Style', 5, 'Salat mit Hofer dressing.', 1, 1),
(10, 2, 'Chinesisches Wok', 8, 'Chineisches traditionelles Wok.', 1, 2),
(11, 3, 'Creme La Fresh', 5, 'Feine Schokocreme mit Streuseln.', 1, 3),
(12, 4, 'Thunder Limo', 4, 'Limo aus der Region (selber hergestellt.', 1, 4),
(13, 1, 'Tomato Garli', 5, 'Tomaten Suppe mit Knoblauch.', 1, 1),
(14, 2, 'Wiener-Schnitzel', 8, 'Fein paniertes Hänchen Schnitzel nach Wiener Art.', 1, 2),
(15, 3, 'Gelbe Rackete', 5, 'Vanille Pudding.', 1, 3),
(16, 4, 'Sprite', 4, 'Sponsored by Sprite.', 1, 4),
(17, 1, 'Döner Teller aka PomDöner', 5, 'Döner Fleisch mit Pommes und Kräuter Soße.', 1, 1),
(18, 2, 'Crunchy Chicken Bürger', 7, 'Chicken Bürger besser als bei McDonalds.', 1, 2),
(19, 3, 'Süße Törtchen', 5, 'Basti\'s Sahne Kuchen Spezialität.', 1, 3),
(20, 4, 'RedBull', 4, 'Normales RedBull.', 1, 4),
(21, 1, 'Studenten Kuchen', 4, 'Blätterteig Auflauf mit Käse.', 1, 1),
(22, 2, 'Beef Bacon Bürgi', 8, 'Bürger mit Beef und Bacon dazu Süß Kartoffeln.', 1, 2),
(23, 3, 'Sahne kuss', 3, 'Sahne Kiss.', 1, 3),
(24, 4, 'Sex on the Beach', 7, 'Sex on the Beach einer unseren besten Cocktails.', 1, 4),
(25, 1, 'Erdbeeren Dip', 4, 'Flammkuchen mit Erdbeeren', 1, 1),
(26, 2, 'Lasagne', 8, 'Lasagne mit Becanel Soße.', 1, 2),
(27, 3, 'Erdbeer Milchshake', 3, 'Erdbeer Milchshake mit schokoraspeln.', 1, 3),
(28, 4, 'Blueberry Fago', 7, 'Blaubeeren Cocktail (alkohol frei).', 1, 4),
(29, 1, 'Cheese Strips', 5, 'Käse mini Auflauf mit Makkaroni.', 1, 1),
(30, 2, 'Spaghetti Bolognese', 7, 'Spaghetti mit einer Fleisch Soße.', 1, 2),
(31, 3, 'Fantastisches Jogurt', 4, 'Joghurt mit Samrties und Caramell Soße.', 1, 3),
(32, 4, 'Red Bull Red', 7, 'Rotes Redbull.', 1, 4),
(33, 1, 'Kartoffel Zuchini Suppe', 6, 'Cremige Kartoffel Zuchini Suppe.', 1, 1),
(34, 2, 'Spaghetti BroJoSauce', 7, 'Spaghetti mit Brokoli Sahne Sauce.', 1, 2),
(35, 3, 'Biscuit Rolle', 9, 'Teigrolle mit Erdbeersauce.', 1, 3),
(36, 4, 'Red Bull White', 4, 'Weißes Redbull.', 1, 4),
(37, 1, 'Rote Flunder', 6, 'Lachs Tartar auf Rotebete Carpaccio.', 1, 1),
(38, 2, 'Cheese Mäse', 8, 'Käse Auflauf.', 1, 2),
(39, 3, 'Sahne Kuchen', 8, 'Sahne Kuchen wie ein Bienenstich.', 1, 3),
(40, 4, 'Red Bull Granatapfel', 4, 'Christmas Edition Redbull.', 1, 4),
(41, 1, 'Spargel-Mozerella Röllchen', 7, 'Spagel Zuchini Röllchen in feiner Mozerella Soße.', 1, 1),
(42, 2, 'Rinderfilet Bayerische Art', 7, 'Rinderfilet mit Speckböhnchen und Spätzle.', 1, 2),
(43, 3, 'Waffel Cake', 7, 'Schoko Waffel mit Mandeln.', 1, 3),
(44, 4, 'Red Bull Yellow', 4, 'Yellow Edition Redbull.', 1, 4),
(45, 1, 'Vini Mini', 7, 'Vinis Würstchen spezialität.', 1, 1),
(46, 2, 'Pizza Ocean', 6, 'Pizza mit Thunfisch oder Meeresfrüchte.', 1, 2),
(47, 3, 'Caramell Shock', 7, 'Caramell überzogene Milchshake Cross Cake.', 1, 3),
(48, 4, 'Fanta', 4, 'Sponsored by Fanta.', 1, 4),
(49, 1, 'Paprika Texanische Art', 7, 'Kalte Vorspeise aus Paprika und Frischkäse, Knoblauch und Fleisch.', 1, 1),
(50, 2, 'Roladen', 6, 'Roladen mit Senf.', 1, 2),
(51, 3, 'Eisberg Salat', 5, 'Eisberg Salat mit Sauce.', 1, 3),
(52, 4, 'Fritz Cola 0,5', 3, 'Sponsored by Fritz Cola.', 1, 4),
(53, 1, 'Gurken Caprese', 7, 'Gurken Scheiben mit Pinienkernen und Mozerella Scheiben.', 1, 1),
(54, 2, 'Pepparoni Pizza', 6, 'Pizza mit Bolognese Soße und Pepparoni.', 1, 2),
(55, 3, 'Extreme Sugar Rush', 7, 'Waffel Stapel mit Erdbeer Sahne Torte.', 1, 3),
(56, 4, 'Die Limo', 4, 'Sponsored by Die Limo.', 1, 4),
(57, 1, 'Chicken Wrap', 5, 'Kalte Hänchen Teig Rolle.', 1, 1),
(58, 2, 'Pfarrerschnitzel', 9, 'Leckeres zartes Hänchen Fleisch mit zarter Soße.', 1, 2),
(59, 3, 'Pfannkuchen', 7, 'Pfannkuchen.', 1, 3),
(60, 4, 'Die Zitronen Spülung', 3, 'Limo Marke eigen.', 1, 4),
(61, 1, 'Vegetarisch Wrap', 5, 'Soja Bohnen Teig Rolle.', 1, 1),
(62, 2, 'Super Steak', 6, 'Steak mit Butter und Rahmsoße.', 1, 2),
(63, 3, 'Mini Kuchen', 4, 'Kleiner Kuchen für zwischen durch.', 1, 3),
(64, 4, 'Mineralwasser (sprudelig)', 1, 'Aus Natürlichen Quellen.', 1, 4),
(65, 1, 'ZiegenKäse Brot', 5, 'Fladenbrot mit Ziegenkäse.', 1, 1),
(66, 2, 'Uwu Baum', 6, 'Brokoli Auflauf mit Sahne.', 1, 2),
(67, 3, 'Bienenstich', 4, 'Klassischer Bienenstich.', 1, 3),
(68, 4, 'Mineralwasser (still)', 1, 'Aus Natürlichen Quellen.', 1, 4),
(69, 1, 'Hänchen Suppe', 5, 'Hänchen Suppe mit Brot scheiben.', 1, 1),
(70, 2, 'Döner Teller', 7, 'Döner mit Salat und zwiebeln auf dem Tisch.', 1, 2),
(71, 3, 'Benjamin Blümchen Torte', 6, 'Klassischer Benjamin Kuchen.', 1, 3),
(72, 4, 'Grüner Tee', 0, 'Apfel Tee.', 1, 4),
(73, 5, 'Pommes', 5, 'Kartoffel Fritten mit Ketchup und Mayo nach wahl', 1, 1),
(74, 5, 'Süßkartoffel Fritten', 6, 'Süßkartoffel Fritten mit Ketchup und Mayo nach wahl.', 1, 1),
(75, 5, 'Fagiolini alla genovese', 7, 'Pikantes Bohnengemüse mit Knoblauch und Sardellen.', 1, 1),
(76, 5, 'Bruschetta auf kleine Baguettestückchen', 6, 'Eine klassische italienische Vorspeise.', 1, 1),
(77, 5, 'Cheese Fritts', 5, 'Pommes Fritten mit Käse', 1, 1),
(78, 5, 'Fish n Chips', 5, 'Pommes Fritten mit Fischstäbchen', 1, 1),
(79, 5, 'Tsatsiki', 6, 'Haus eigene Zsatsiki nach Griechischer Art', 1, 1),
(80, 5, 'Chips n Dip', 4, 'Pepperoni Chips in Frischkäse mit Dill', 1, 1),
(81, 5, 'Chicken Nuggets n Flips', 7, 'Chicken Nuggets in Erdnusssoße', 1, 1),
(82, 5, 'Nutella Brötchen', 4, 'kleine Nutella Brötchen', 1, 1),
(83, 5, 'Fischsticks in Ramsoße', 6, 'Fischstäbchen in Ramsoße', 1, 1),
(84, 5, 'Warme Schnitten', 4, 'Warme teig schnidden', 1, 1),
(85, 5, 'Paprika Chips n Cheese', 4, 'Paprika Chips mit Cheese Dip', 1, 1);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `Reservation`
--

CREATE TABLE `Reservation` (
  `ID` int(11) NOT NULL,
  `TableID` int(11) NOT NULL,
  `Name` varchar(255) DEFAULT NULL,
  `Email` varchar(60) DEFAULT NULL,
  `Phone` varchar(30) DEFAULT NULL,
  `Date` date DEFAULT NULL,
  `Time` time DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Daten für Tabelle `Reservation`
--

INSERT INTO `Reservation` (`ID`, `TableID`, `Name`, `Email`, `Phone`, `Date`, `Time`) VALUES
(1, 1, 'DarkModz-Official', 'sebastian@schindlertai.de', '0000', '2022-04-29', '11:00:00'),
(2, 1, 'Bananenmann', 'banane@mann.de', '0000', '2022-05-16', '09:00:00'),
(3, 2, 'Paul Rakutt', 'paul@rakutt.eu', '00000', '2022-05-17', '21:00:00');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `StockOrder`
--

CREATE TABLE `StockOrder` (
  `ID` int(11) NOT NULL,
  `ProductID` int(11) NOT NULL,
  `Orderd` tinyint(1) DEFAULT NULL,
  `Arrived` tinyint(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `Tables`
--

CREATE TABLE `Tables` (
  `ID` int(11) NOT NULL,
  `Nummer` int(11) NOT NULL,
  `Ort` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Daten für Tabelle `Tables`
--

INSERT INTO `Tables` (`ID`, `Nummer`, `Ort`) VALUES
(1, 1, ''),
(2, 2, ''),
(3, 3, ''),
(4, 4, ''),
(5, 5, ''),
(6, 6, ''),
(7, 7, ''),
(8, 8, ''),
(9, 9, ''),
(10, 10, ''),
(11, 11, ''),
(12, 12, ''),
(13, 13, ''),
(14, 14, ''),
(15, 15, ''),
(16, 16, ''),
(17, 17, ''),
(18, 18, ''),
(19, 19, ''),
(20, 20, '');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `Type`
--

CREATE TABLE `Type` (
  `ID` int(11) NOT NULL,
  `Name` varchar(50) NOT NULL,
  `Weight` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Daten für Tabelle `Type`
--

INSERT INTO `Type` (`ID`, `Name`, `Weight`) VALUES
(1, 'Vorspeise', NULL),
(2, 'Hauptspeise', NULL),
(3, 'Nachspeise', NULL),
(4, 'Trinken', NULL),
(5, 'Beilagen', NULL);

--
-- Indizes der exportierten Tabellen
--

--
-- Indizes für die Tabelle `Accounts`
--
ALTER TABLE `Accounts`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `DepatmentID` (`DepatmentID`);

--
-- Indizes für die Tabelle `Department`
--
ALTER TABLE `Department`
  ADD PRIMARY KEY (`ID`);

--
-- Indizes für die Tabelle `Orders`
--
ALTER TABLE `Orders`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `AccountID` (`AccountID`),
  ADD KEY `TabelID` (`TabelID`),
  ADD KEY `ProductID` (`ProductID`);

--
-- Indizes für die Tabelle `Products`
--
ALTER TABLE `Products`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `TypeID` (`TypeID`);

--
-- Indizes für die Tabelle `Reservation`
--
ALTER TABLE `Reservation`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `TableID` (`TableID`);

--
-- Indizes für die Tabelle `StockOrder`
--
ALTER TABLE `StockOrder`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `ProductID` (`ProductID`);

--
-- Indizes für die Tabelle `Tables`
--
ALTER TABLE `Tables`
  ADD PRIMARY KEY (`ID`);

--
-- Indizes für die Tabelle `Type`
--
ALTER TABLE `Type`
  ADD PRIMARY KEY (`ID`);

--
-- AUTO_INCREMENT für exportierte Tabellen
--

--
-- AUTO_INCREMENT für Tabelle `Accounts`
--
ALTER TABLE `Accounts`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT für Tabelle `Department`
--
ALTER TABLE `Department`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT für Tabelle `Orders`
--
ALTER TABLE `Orders`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=414;

--
-- AUTO_INCREMENT für Tabelle `Products`
--
ALTER TABLE `Products`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=86;

--
-- AUTO_INCREMENT für Tabelle `Reservation`
--
ALTER TABLE `Reservation`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT für Tabelle `StockOrder`
--
ALTER TABLE `StockOrder`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT für Tabelle `Tables`
--
ALTER TABLE `Tables`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT für Tabelle `Type`
--
ALTER TABLE `Type`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- Constraints der exportierten Tabellen
--

--
-- Constraints der Tabelle `Accounts`
--
ALTER TABLE `Accounts`
  ADD CONSTRAINT `Accounts_ibfk_1` FOREIGN KEY (`DepatmentID`) REFERENCES `Department` (`ID`);

--
-- Constraints der Tabelle `Orders`
--
ALTER TABLE `Orders`
  ADD CONSTRAINT `Orders_ibfk_1` FOREIGN KEY (`AccountID`) REFERENCES `Accounts` (`ID`),
  ADD CONSTRAINT `Orders_ibfk_2` FOREIGN KEY (`TabelID`) REFERENCES `Tables` (`ID`),
  ADD CONSTRAINT `Orders_ibfk_3` FOREIGN KEY (`ProductID`) REFERENCES `Products` (`ID`);

--
-- Constraints der Tabelle `Products`
--
ALTER TABLE `Products`
  ADD CONSTRAINT `Products_ibfk_1` FOREIGN KEY (`TypeID`) REFERENCES `Type` (`ID`);

--
-- Constraints der Tabelle `Reservation`
--
ALTER TABLE `Reservation`
  ADD CONSTRAINT `Reservation_ibfk_1` FOREIGN KEY (`TableID`) REFERENCES `Tables` (`ID`);

--
-- Constraints der Tabelle `StockOrder`
--
ALTER TABLE `StockOrder`
  ADD CONSTRAINT `StockOrder_ibfk_1` FOREIGN KEY (`ProductID`) REFERENCES `Products` (`ID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
