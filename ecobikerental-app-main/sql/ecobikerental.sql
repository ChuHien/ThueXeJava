create database EcoBikeRental;
use EcoBikeRental;

CREATE TABLE BikeType (
  BikeTypeID int,
  Name nvarchar(50),
  PRIMARY KEY (BikeTypeID)
);

CREATE TABLE PriceList (
  PriceID int,
  Name nvarchar(50),
  Price float,
  PRIMARY KEY (PriceID)
);

CREATE TABLE ParkingLot (
  ParkingLotID int,
  Name nvarchar(50),
  Address text,
  Area float,
  PRIMARY KEY (ParkingLotID)
);

CREATE TABLE Bike (
  BikeID int,
  ParkingLotID int,
  BikeTypeID int,
  PriceID int,
  Name nvarchar(30),
  BatteryStatus nvarchar(50),
  Status bit,
  PRIMARY KEY (BikeID),
  FOREIGN KEY (ParkingLotID) REFERENCES ParkingLot (ParkingLotID),
  FOREIGN KEY (BikeTypeID) REFERENCES BikeType (BikeTypeID),
  FOREIGN KEY (PriceID) REFERENCES PriceList (PriceID)
);

use EcobikeRental;
CREATE TABLE TransactionInfo (
  TransactionID int auto_increment ,
  BikeID int,
  CardID nvarchar(20),
  Date text,
  UnlockDate text,
  Deposit float,
  RentingTime float,
  Status bit,
  PaymentMethod int,
  PRIMARY KEY (TransactionID),
  FOREIGN KEY (BikeID) REFERENCES Bike (BikeID)
);

-- BIKE TYPE
INSERT INTO BikeType
VALUES 
('1',N'Xe đạp đơn thường'),
('2',N'Xe đạp đơn điện'),
('3',N'Xe đạp đôi thường');

-- Price List
INSERT INTO PriceList
VALUES 
('1', N'Giá thuê 30 phút đầu', '10000'),
('2', N'Giá thuê mỗi 15 phút', '3000'),
('3', N'Giá thuê theo ngày', '200000'),
('4', N'Giá thuê mỗi giờ', '10000'),
('5', N'Giá thuê muộn mỗi 15 phút', '2000');

-- PARKING LOT
INSERT INTO ParkingLot
VALUES 
('1',N'Bãi xe A1', N'A1', '10.0'),
('2',N'Bãi xe A2', N'A2', '10.5');

-- BIKE
INSERT INTO Bike
VALUES 
('1','1','1','1', N'Xe đạp đơn 1', N'6 giờ',b'0'),
('2','1','1','2', N'Xe đạp đơn 2', N'Không',b'0'),
('3','2','2','3', N'Xe đạp điện 1', N'11 giờ',b'0'),
('4','2','2','4', N'Xe đạp điện 2', N'Không',b'0');



-- check
SELECT * FROM Bike;
SELECT * FROM ParkingLot;
SELECT * FROM BikeType;
SELECT * FROM PriceList;
SELECT * FROM TransactionInfo;