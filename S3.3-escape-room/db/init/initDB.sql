-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema escape_room
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema escape_room
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `escape_room` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `escape_room` ;

-- -----------------------------------------------------
-- Table `escape_room`.`certificate`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `escape_room`.`certificate` (
  `id_certificate` INT NOT NULL AUTO_INCREMENT,
  `type` ENUM('') NOT NULL,
  `reward` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`id_certificate`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `escape_room`.`theme`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `escape_room`.`theme` (
  `id_theme` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id_theme`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `escape_room`.`room`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `escape_room`.`room` (
  `id_room` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `difficulty` ENUM('EASY', 'MEDIUM', 'HARD', 'EXTREME') NOT NULL DEFAULT 'MEDIUM',
  `price` DECIMAL(10,2) NOT NULL,
  `theme_id` INT NOT NULL,
  PRIMARY KEY (`id_room`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE,
  INDEX `fk_room_theme_idx` (`theme_id` ASC) VISIBLE,
  CONSTRAINT `fk_room_theme`
    FOREIGN KEY (`theme_id`)
    REFERENCES `escape_room`.`theme` (`id_theme`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `escape_room`.`clue`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `escape_room`.`clue` (
  `id_clue` INT NOT NULL AUTO_INCREMENT,
  `type` ENUM('') NOT NULL,
  `clue_description` VARCHAR(200) NOT NULL,
  `price` DECIMAL(10,2) NOT NULL,
  `theme_id` INT NULL,
  `room_id` INT NOT NULL,
  PRIMARY KEY (`id_clue`),
  INDEX `fk_clue_themes_idx` (`theme_id` ASC) VISIBLE,
  INDEX `fk_clue_room_idx` (`room_id` ASC) VISIBLE,
  CONSTRAINT `fk_clue_themes`
    FOREIGN KEY (`theme_id`)
    REFERENCES `escape_room`.`theme` (`id_theme`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT,
  CONSTRAINT `fk_clue_room`
    FOREIGN KEY (`room_id`)
    REFERENCES `escape_room`.`room` (`id_room`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `escape_room`.`decoration_object`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `escape_room`.`decoration_object` (
  `id_decoration_object` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `material` ENUM('WOOD', 'METAL', 'PLASTIC') NOT NULL,
  `stock` INT NOT NULL,
  `price` DECIMAL(10,2) NOT NULL,
  `room_id` INT NOT NULL,
  PRIMARY KEY (`id_decoration_object`),
  INDEX `fk_decoration_object_room_idx` (`room_id` ASC) VISIBLE,
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE,
  CONSTRAINT `fk_decoration_object_room`
    FOREIGN KEY (`room_id`)
    REFERENCES `escape_room`.`room` (`id_room`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
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
  PRIMARY KEY (`id_player`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `escape_room`.`ticket`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `escape_room`.`ticket` (
  `id_ticket` INT NOT NULL AUTO_INCREMENT,
  `purchase_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `total` DECIMAL(10,2) NOT NULL,
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
    REFERENCES `escape_room`.`room` (`id_room`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `escape_room`.`player_certificate`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `escape_room`.`player_certificate` (
  `player_id` INT NOT NULL,
  `certificate_id` INT NOT NULL,
  `room_id` INT NOT NULL,
  `issued_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`player_id`, `certificate_id`, `room_id`),
  INDEX `fk_player_certificate_certificate_idx` (`certificate_id` ASC) VISIBLE,
  INDEX `fk_player_certificate_room_idx` (`room_id` ASC) VISIBLE,
  CONSTRAINT `fk_player_certificate_player`
    FOREIGN KEY (`player_id`)
    REFERENCES `escape_room`.`player` (`id_player`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT,
  CONSTRAINT `fk_player_certificate_certificate`
    FOREIGN KEY (`certificate_id`)
    REFERENCES `escape_room`.`certificate` (`id_certificate`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT,
  CONSTRAINT `fk_player_certificate_room`
    FOREIGN KEY (`room_id`)
    REFERENCES `escape_room`.`room` (`id_room`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;