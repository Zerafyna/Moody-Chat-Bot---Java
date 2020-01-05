-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema moodybot
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `moodybot` ;

-- -----------------------------------------------------
-- Schema moodybot
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `moodybot` DEFAULT CHARACTER SET utf8 ;
USE `moodybot` ;

-- -----------------------------------------------------
-- Table `moodybot`.`analyzerlog`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `moodybot`.`analyzerlog` ;

CREATE TABLE IF NOT EXISTS `moodybot`.`analyzerlog` (
  `Id` INT(11) NOT NULL AUTO_INCREMENT,
  `Date` DATETIME NOT NULL,
  `UserId` VARCHAR(150) NULL DEFAULT NULL,
  `Message` VARCHAR(1000) NOT NULL,
  `Mood` VARCHAR(45) NOT NULL,
  `Value` DOUBLE NOT NULL,
  PRIMARY KEY (`Id`))
ENGINE = InnoDB
AUTO_INCREMENT = 108
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `moodybot`.`botsettingslog`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `moodybot`.`botsettingslog` ;

CREATE TABLE IF NOT EXISTS `moodybot`.`botsettingslog` (
  `Id` INT(11) NOT NULL AUTO_INCREMENT,
  `Date` DATETIME NOT NULL,
  `TrackStatistics` TINYINT(4) NOT NULL,
  `WatchingActivity` VARCHAR(150) NULL DEFAULT NULL,
  `AnnonimusLog` TINYINT(4) NOT NULL,
  PRIMARY KEY (`Id`))
ENGINE = InnoDB
AUTO_INCREMENT = 78
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `moodybot`.`hellmode`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `moodybot`.`hellmode` ;

CREATE TABLE IF NOT EXISTS `moodybot`.`hellmode` (
  `Id` INT(11) NOT NULL AUTO_INCREMENT,
  `Date` DATETIME NOT NULL,
  `State` VARCHAR(3) NOT NULL,
  PRIMARY KEY (`Id`))
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
