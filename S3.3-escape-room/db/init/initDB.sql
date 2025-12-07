-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema escape_room
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema escape_room
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `escape_room` DEFAULT CHARACTER SET utf8mb3 ;
USE `escape_room` ;

-- -----------------------------------------------------
-- Table `escape_room`.`certificate`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `escape_room`.`certificate` (
  `id_certificate` INT NOT NULL AUTO_INCREMENT,
  `type` ENUM('') NOT NULL,
  `emision_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `reward` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`id_certificate`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `escape_room`.`clue`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `escape_room`.`clue` (
  `id_clue` INT NOT NULL AUTO_INCREMENT,
  `type` ENUM('') NOT NULL,
  `clue_description` VARCHAR(200) NOT NULL,
  `price` DECIMAL(10,0) NOT NULL,
  PRIMARY KEY (`id_clue`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `escape_room`.`decoration_object`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `escape_room`.`decoration_object` (
  `id_decoration_object` INT NOT NULL AUTO_INCREMENT,
  `material` ENUM('') NOT NULL,
  `stock` INT NOT NULL,
  `price` DECIMAL(10,0) NOT NULL,
  PRIMARY KEY (`id_decoration_object`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `escape_room`.`player`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `escape_room`.`player` (
  `id_player` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `subscribed` TINYINT NOT NULL,
  `certificate_id_certificate` INT NOT NULL,
  PRIMARY KEY (`id_player`),
  INDEX `fk_player_certificate_idx` (`certificate_id_certificate` ASC) VISIBLE,
  CONSTRAINT `fk_player_certificate`
    FOREIGN KEY (`certificate_id_certificate`)
    REFERENCES `escape_room`.`certificate` (`id_certificate`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `escape_room`.`themes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `escape_room`.`themes` (
  `id_themes` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id_themes`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `escape_room`.`room`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `escape_room`.`room` (
  `id_room` INT NOT NULL AUTO_INCREMENT,
  `difficulty` ENUM('') NOT NULL,
  `clue_id_clue` INT NOT NULL,
  `decoration_object_id_decoration_object` INT NOT NULL,
  `themes_id_themes` INT NOT NULL,
  `price` DECIMAL(10,0) NOT NULL,
  PRIMARY KEY (`id_room`),
  INDEX `fk_room_clue1_idx` (`clue_id_clue` ASC) VISIBLE,
  INDEX `fk_room_decoration_object1_idx` (`decoration_object_id_decoration_object` ASC) VISIBLE,
  INDEX `fk_room_themes1_idx` (`themes_id_themes` ASC) VISIBLE,
  CONSTRAINT `fk_room_clue1`
    FOREIGN KEY (`clue_id_clue`)
    REFERENCES `escape_room`.`clue` (`id_clue`),
  CONSTRAINT `fk_room_decoration_object1`
    FOREIGN KEY (`decoration_object_id_decoration_object`)
    REFERENCES `escape_room`.`decoration_object` (`id_decoration_object`),
  CONSTRAINT `fk_room_themes1`
    FOREIGN KEY (`themes_id_themes`)
    REFERENCES `escape_room`.`themes` (`id_themes`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `escape_room`.`ticket`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `escape_room`.`ticket` (
  `id_ticket` INT NOT NULL AUTO_INCREMENT,
  `purchase_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `total` DECIMAL(10,0) NOT NULL,
  `player_id_player` INT NOT NULL,
  `room_id_room` INT NOT NULL,
  PRIMARY KEY (`id_ticket`),
  INDEX `fk_ticket_player1_idx` (`player_id_player` ASC) VISIBLE,
  INDEX `fk_ticket_room1_idx` (`room_id_room` ASC) VISIBLE,
  CONSTRAINT `fk_ticket_player1`
    FOREIGN KEY (`player_id_player`)
    REFERENCES `escape_room`.`player` (`id_player`),
  CONSTRAINT `fk_ticket_room1`
    FOREIGN KEY (`room_id_room`)
    REFERENCES `escape_room`.`room` (`id_room`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
