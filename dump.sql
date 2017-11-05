-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 ;
USE `mydb` ;

-- -----------------------------------------------------
-- Table `mydb`.`Odsjek`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Odsjek` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Korisnik`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Korisnik` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `ime` VARCHAR(45) NULL,
  `prezime` VARCHAR(45) NULL,
  `jmbg` VARCHAR(45) NULL,
  `username` VARCHAR(45) NULL,
  `password` VARCHAR(45) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Student`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Student` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `Korisnik_id` INT NOT NULL,
  `status` VARCHAR(45) NULL,
  `godinaStudija` VARCHAR(45) NULL,
  `Odsjek_id` INT NOT NULL,
  `index` VARCHAR(10) NULL,
  `datumPrvogUpisa` DATE NULL,
  `datumZadnjegUpisa` DATE NULL,
  PRIMARY KEY (`id`, `Korisnik_id`, `Odsjek_id`),
  INDEX `fk_Student_Odsjek1_idx` (`Odsjek_id` ASC),
  INDEX `fk_Student_Korisnik1_idx` (`Korisnik_id` ASC),
  CONSTRAINT `fk_Student_Odsjek1`
    FOREIGN KEY (`Odsjek_id`)
    REFERENCES `mydb`.`Odsjek` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Student_Korisnik1`
    FOREIGN KEY (`Korisnik_id`)
    REFERENCES `mydb`.`Korisnik` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`tipUposlenika`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`tipUposlenika` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `naziv` VARCHAR(45) NULL,
  `poSatu` TINYINT NULL DEFAULT 0,
  `cijenaPoSatu` DECIMAL(2) NULL DEFAULT 0,
  `ukupno` DECIMAL(2) NULL DEFAULT 0,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Uposlenik`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Uposlenik` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `Korisnik_id` INT NOT NULL,
  `tipUposlenika_id` INT NOT NULL,
  `aktivan` TINYINT NULL,
  `datumIsteka` DATE NULL,
  PRIMARY KEY (`id`, `Korisnik_id`, `tipUposlenika_id`),
  INDEX `fk_Uposlenik_Korisnik1_idx` (`Korisnik_id` ASC),
  INDEX `fk_Uposlenik_tipUposlenika1_idx` (`tipUposlenika_id` ASC),
  CONSTRAINT `fk_Uposlenik_Korisnik1`
    FOREIGN KEY (`Korisnik_id`)
    REFERENCES `mydb`.`Korisnik` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Uposlenik_tipUposlenika1`
    FOREIGN KEY (`tipUposlenika_id`)
    REFERENCES `mydb`.`tipUposlenika` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Predmet`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Predmet` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `naziv` VARCHAR(45) NULL,
  `sifra` VARCHAR(45) NULL,
  `Uposlenik_id` INT NOT NULL,
  PRIMARY KEY (`id`, `Uposlenik_id`),
  INDEX `fk_Predmet_Uposlenik_idx` (`Uposlenik_id` ASC),
  CONSTRAINT `fk_Predmet_Uposlenik`
    FOREIGN KEY (`Uposlenik_id`)
    REFERENCES `mydb`.`Uposlenik` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Kurs`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Kurs` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `Predmet_id` INT NOT NULL,
  `godina` INT NULL,
  `Odsjek_id` INT NOT NULL,
  `izborni` TINYINT NULL DEFAULT 0,
  `brPredavanja` INT NULL,
  `brTutorijala` INT NULL,
  `brLabova` INT NULL,
  `ects` DECIMAL(1) NULL,
  PRIMARY KEY (`id`, `Predmet_id`, `Odsjek_id`),
  INDEX `fk_Nastava_Predmet1_idx` (`Predmet_id` ASC),
  INDEX `fk_Kurs_Odsjek1_idx` (`Odsjek_id` ASC),
  CONSTRAINT `fk_Nastava_Predmet1`
    FOREIGN KEY (`Predmet_id`)
    REFERENCES `mydb`.`Predmet` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Kurs_Odsjek1`
    FOREIGN KEY (`Odsjek_id`)
    REFERENCES `mydb`.`Odsjek` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Nastava`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Nastava` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `akademskaGod` VARCHAR(45) NULL,
  `Kurs_id` INT NOT NULL,
  `Student_id` INT NOT NULL,
  `obnova` TINYINT NULL DEFAULT 0,
  `ovjera` VARCHAR(45) NULL,
  `ocjena` INT NULL DEFAULT 5,
  PRIMARY KEY (`id`, `Kurs_id`, `Student_id`),
  INDEX `fk_Studij_Nastava1_idx` (`Kurs_id` ASC),
  INDEX `fk_Kurs_Student1_idx` (`Student_id` ASC),
  CONSTRAINT `fk_Studij_Nastava1`
    FOREIGN KEY (`Kurs_id`)
    REFERENCES `mydb`.`Kurs` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Kurs_Student1`
    FOREIGN KEY (`Student_id`)
    REFERENCES `mydb`.`Student` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`prijavaIspit`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`prijavaIspit` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `datumPrijave` VARCHAR(45) NULL,
  `Nastava_id` INT NOT NULL,
  PRIMARY KEY (`id`, `Nastava_id`),
  INDEX `fk_prijavaIspit_Nastava1_idx` (`Nastava_id` ASC),
  CONSTRAINT `fk_prijavaIspit_Nastava1`
    FOREIGN KEY (`Nastava_id`)
    REFERENCES `mydb`.`Nastava` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Ispit`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Ispit` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `rezultat` VARCHAR(45) NULL,
  `polozen` TINYINT NULL DEFAULT 0,
  `prijavaIspit_id` INT NOT NULL,
  PRIMARY KEY (`id`, `prijavaIspit_id`),
  INDEX `fk_Ispit_prijavaIspit1_idx` (`prijavaIspit_id` ASC),
  CONSTRAINT `fk_Ispit_prijavaIspit1`
    FOREIGN KEY (`prijavaIspit_id`)
    REFERENCES `mydb`.`prijavaIspit` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Transakcije`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Transakcije` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `datum` DATE NULL,
  `iznos` DECIMAL NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`TransakcijeEksterne`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`TransakcijeEksterne` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `naziv` VARCHAR(45) NULL,
  `svrha` VARCHAR(45) NULL,
  `raèun` VARCHAR(45) NULL,
  `Transakcije_id` INT NOT NULL,
  PRIMARY KEY (`id`, `Transakcije_id`),
  INDEX `fk_TransakcijeVan_Transakcije1_idx` (`Transakcije_id` ASC),
  CONSTRAINT `fk_TransakcijeVan_Transakcije1`
    FOREIGN KEY (`Transakcije_id`)
    REFERENCES `mydb`.`Transakcije` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`TransakcijeInterne`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`TransakcijeInterne` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `naziv` VARCHAR(45) NULL,
  `svrha` VARCHAR(45) NULL,
  `Transakcije_id` INT NOT NULL,
  `Korisnik_id` INT NOT NULL,
  PRIMARY KEY (`id`, `Transakcije_id`, `Korisnik_id`),
  INDEX `fk_TransakcijeUnutra_Transakcije1_idx` (`Transakcije_id` ASC),
  INDEX `fk_TransakcijeUnutra_Korisnik1_idx` (`Korisnik_id` ASC),
  CONSTRAINT `fk_TransakcijeUnutra_Transakcije1`
    FOREIGN KEY (`Transakcije_id`)
    REFERENCES `mydb`.`Transakcije` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_TransakcijeUnutra_Korisnik1`
    FOREIGN KEY (`Korisnik_id`)
    REFERENCES `mydb`.`Korisnik` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`PravniPredmeti`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`PravniPredmeti` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `naziv` VARCHAR(45) NULL,
  `opis` VARCHAR(45) NULL,
  `predmet` VARCHAR(45) NULL,
  `datumPrijema` DATE NULL,
  `datumObrade` DATE NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`tipPotvrde`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`tipPotvrde` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `naziv` VARCHAR(45) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`zahtjevZaPotvrdu`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`zahtjevZaPotvrdu` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `datumZahtjeva` DATE NULL,
  `status` INT NULL,
  `tipPotvrde_id` INT NOT NULL,
  `Student_id` INT NOT NULL,
  PRIMARY KEY (`id`, `tipPotvrde_id`, `Student_id`),
  INDEX `fk_zahtjevZaPotvrdu_tipPotvrde1_idx` (`tipPotvrde_id` ASC),
  INDEX `fk_zahtjevZaPotvrdu_Student1_idx` (`Student_id` ASC),
  CONSTRAINT `fk_zahtjevZaPotvrdu_tipPotvrde1`
    FOREIGN KEY (`tipPotvrde_id`)
    REFERENCES `mydb`.`tipPotvrde` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_zahtjevZaPotvrdu_Student1`
    FOREIGN KEY (`Student_id`)
    REFERENCES `mydb`.`Student` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`javneNabavke`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`javneNabavke` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `subjekat` VARCHAR(45) NULL,
  `opis` VARCHAR(100) NULL,
  `kolicina` INT NULL,
  `cijena` DECIMAL NULL,
  `isporuèilac` VARCHAR(45) NULL,
  `datumObjave` DATE NULL,
  `datumObrade` DATE NULL,
  `status` VARCHAR(45) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Sala`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Sala` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `naziv` VARCHAR(45) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`ispitniRok`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`ispitniRok` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `naziv` VARCHAR(45) NULL,
  `vrsta` INT NULL,
  `rokZaPrijave` VARCHAR(45) NULL,
  `vrijemeOdrzavanja` TIME NULL,
  `Sala_id` INT NOT NULL,
  `Kurs_id` INT NOT NULL,
  PRIMARY KEY (`id`, `Sala_id`, `Kurs_id`),
  INDEX `fk_ispitniRok_Kurs1_idx` (`Kurs_id` ASC),
  INDEX `fk_ispitniRok_Sala1_idx` (`Sala_id` ASC),
  CONSTRAINT `fk_ispitniRok_Kurs1`
    FOREIGN KEY (`Kurs_id`)
    REFERENCES `mydb`.`Kurs` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_ispitniRok_Sala1`
    FOREIGN KEY (`Sala_id`)
    REFERENCES `mydb`.`Sala` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
