drop database IF EXISTS myshopdb ;

CREATE DATABASE  IF NOT EXISTS myshopdb;

USE myshopdb;

CREATE TABLE `user_table` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `Name` varchar(45) NOT NULL,
  `Password` varchar(45) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID_UNIQUE` (`ID`)
);

INSERT INTO `user_table` VALUES (1,'Admin','admin');


CREATE TABLE `staff_table` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `IdNum` bigint NOT NULL,
  `FirstName` varchar(45) NOT NULL,
  `LastName` varchar(45) NOT NULL,
  `Phone` bigint DEFAULT NULL,
  `Email` varchar(100) DEFAULT NULL,
  `Position` varchar(45) NOT NULL,
  `Salary` double NOT NULL,
  PRIMARY KEY (`ID`)
);


CREATE TABLE `staff_log_table` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `Date` datetime NOT NULL,
  `Type_of_operation` varchar(45) NOT NULL,
  `staffID` int NOT NULL,
  `oldIdNum` bigint NOT NULL,
  `oldFirstName` varchar(100) NOT NULL,
  `oldLastName` varchar(100) NOT NULL,
  `oldPhone` bigint NOT NULL,
  `oldEmail` varchar(200) NOT NULL,
  `oldPosition` varchar(45) NOT NULL,
  `oldSalary` double NOT NULL,
  `newIdNum` bigint NOT NULL,
  `newFirstName` varchar(100) NOT NULL,
  `newLastName` varchar(100) NOT NULL,
  `newPhone` bigint NOT NULL,
  `newEmail` varchar(100) NOT NULL,
  `newPosition` varchar(45) NOT NULL,
  `newSalary` double NOT NULL,
  PRIMARY KEY (`ID`)
);

CREATE TABLE `receipt_table` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `Date` datetime DEFAULT NULL,
  `TotalPrice` double DEFAULT NULL,
  `Paidamount` double DEFAULT NULL,
  `Changeamount` double DEFAULT NULL,
  `Type` varchar(50) DEFAULT NULL,
  `TotalTax` double DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `Type_idx` (`Type`)
);

CREATE TABLE `sells_table` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `Item_id` bigint NOT NULL,
  `Name` varchar(45) NOT NULL,
  `Quantity` int NOT NULL,
  `Price` double NOT NULL,
  `Tax` double NOT NULL,
  `Recipt_id` int NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `recipt_id_idx` (`Recipt_id`),
  CONSTRAINT `recipt_id` FOREIGN KEY (`Recipt_id`) REFERENCES `receipt_table` (`ID`)
);

CREATE TABLE `items_table` (
  `ID` bigint NOT NULL,
  `Name` varchar(45) NOT NULL,
  `Price` double NOT NULL,
  `TaxPercentage` int NOT NULL,
  `Quantity` int DEFAULT NULL,
  PRIMARY KEY (`ID`)
);

CREATE TABLE `item_log_table` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `Item_id` bigint NOT NULL,
  `Date` datetime NOT NULL,
  `Type_of_operation` varchar(45) NOT NULL,
  `oldName` varchar(45) NOT NULL,
  `oldPrice` double NOT NULL,
  `oldTaxPercentage` int NOT NULL,
  `oldQuantity` int NOT NULL,
  `newName` varchar(45) NOT NULL,
  `newPrice` double NOT NULL,
  `newTaxPercentage` int NOT NULL,
  `newQuantity` int NOT NULL,
  PRIMARY KEY (`ID`)
);



CREATE TRIGGER before_employee_update 
    AFTER INSERT ON staff_table
    FOR EACH ROW 
 insert into myshopdb.staff_log_table (Date,Type_of_operation,staffID,oldIdNum,oldFirstName,oldLastName,oldPhone,oldEmail,oldPosition,oldSalary,newIdNum,newFirstName,newLastName,newPhone,newEmail,newPosition,newSalary)
values (now(),'insert',new.ID,0,' ',' ',0,' ',' ',0,new.IdNum,new.FirstName,new.LastName,new.Phone,new.Email,new.Position,new.Salary);


CREATE TRIGGER staff_table_AFTER_UPDATE AFTER UPDATE ON staff_table FOR EACH ROW 
insert into myshopdb.staff_log_table (Date,Type_of_operation,staffID,oldIdNum,oldFirstName,oldLastName,oldPhone,oldEmail,oldPosition,oldSalary,newIdNum,newFirstName,newLastName,newPhone,newEmail,newPosition,newSalary)
values (now(),'update',old.ID,old.IdNum,old.FirstName,old.LastName,old.Phone,old.Email,old.Position,old.Salary,new.IdNum,new.FirstName,new.LastName,new.Phone,new.Email,new.Position,new.Salary);

CREATE TRIGGER staff_table_AFTER_DELETE before DELETE ON staff_table FOR EACH ROW
insert into myshopdb.staff_log_table (Date,Type_of_operation,staffID,oldIdNum,oldFirstName,oldLastName,oldPhone,oldEmail,oldPosition,oldSalary,newIdNum,newFirstName,newLastName,newPhone,newEmail,newPosition,newSalary)
values (now(),'delete',old.ID,old.IdNum,old.FirstName,old.LastName,old.Phone,old.Email,old.Position,old.Salary,0,' ',' ',0,' ',' ',0);


CREATE TRIGGER items_table_AFTER_INSERT AFTER INSERT ON items_table FOR EACH ROW
insert into myshopdb.item_log_table (Item_id,Date,Type_of_operation,oldName,oldPrice,oldTaxPercentage,oldQuantity,newName,newPrice,newTaxPercentage,newQuantity)
 values (new.ID,now(),'insert','null',0,0,0,new.Name,new.Price,new.TaxPercentage,new.Quantity);



CREATE TRIGGER items_table_BEFORE_UPDATE BEFORE UPDATE ON items_table FOR EACH ROW
insert into myshopdb.item_log_table (Item_id,Date,Type_of_operation,oldName,oldPrice,oldTaxPercentage,oldQuantity,newName,newPrice,newTaxPercentage,newQuantity)
 values (old.ID,now(),'update',old.Name,old.Price,old.TaxPercentage,old.Quantity,new.Name,new.Price,new.TaxPercentage,new.Quantity);

CREATE TRIGGER items_table_BEFORE_DELETE BEFORE DELETE ON items_table FOR EACH ROW 
insert into myshopdb.item_log_table (Item_id,Date,Type_of_operation,oldName,oldPrice,oldTaxPercentage,oldQuantity,newName,newPrice,newTaxPercentage,newQuantity)
 values (old.ID,now(),'delete',old.Name,old.Price,old.TaxPercentage,old.Quantity,'null',0,0,0);
