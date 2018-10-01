CREATE DATABASE IF NOT EXISTS webshop;

USE webshop;

DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS item_prc;
DROP TABLE IF EXISTS item_qty;
DROP TABLE IF EXISTS item;

CREATE TABLE user(
  uid INT AUTO_INCREMENT PRIMARY KEY,
  uname VARCHAR(30) UNIQUE NOT NULL,
  hashedpw CHAR(64) NOT NULL
);

CREATE TABLE item(
  iid INT AUTO_INCREMENT PRIMARY KEY,
  iname VARCHAR(30) UNIQUE NOT NULL
);

CREATE TABLE item_prc(
  iid INT NOT NULL PRIMARY KEY,
  prc DECIMAL NOT NULL,
  FOREIGN KEY(iid) REFERENCES item(iid)
);

CREATE TABLE item_qty(
  iid INT NOT NULL PRIMARY KEY,
  qty INT NOT NULL,
  FOREIGN KEY(iid) REFERENCES item(iid)
);