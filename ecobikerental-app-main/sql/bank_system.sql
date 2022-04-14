create database BankSystem;

use BankSystem;

CREATE TABLE CreditCard (
  CardID varchar(6),
  CustomerName text,
  Amount float,
  PRIMARY KEY (CardID)
);

-- Khoi tao moi nguoi 1000000 dong trong tai khoan

INSERT INTO CreditCard
VALUES 
('111111', N'Hiền', 1000000.0),
('222222', N'Anh', 1000000.0),
('333333', N'Ngân', 1000000.0);

-- check 
SELECT * FROM CreditCard;

-- delete 
-- DELETE  FROM CreditCard--