create database ingaccount;
use  ingaccount;

-- create tables
create table User (id int PRIMARY KEY NOT NULL AUTO_INCREMENT, name VARCHAR(255), details VARCHAR(255));
create table Account (id int PRIMARY KEY NOT NULL AUTO_INCREMENT, iban VARCHAR(255), name VARCHAR(255),
    created_at datetime, user_id int, FOREIGN KEY(user_id) REFERENCES User(id));

-- populate tables
-- users
INSERT INTO User (`id`,`name`,`details`) VALUES
(1, 'John Doe', 'Savings account'),
(2, 'Donald Duck', 'Savings account'),
(3, 'Minnie Mouse', 'Savings account'),
(4, 'Mickey Mouse', 'Savings account');

-- account
INSERT INTO Account (`id`, `iban`, `name`, `created_at`, `user_id`) VALUES
(1, 'SE35 5000 0000 0549 1000 0003', 'Sweden IBAN', NOW(), 1),
(2, 'CH93 0076 2011 6238 5295 7', 'Switzerland IBAN', NOW(), 2),
(3, 'DE89 3704 0044 0532 0130 00', 'Germany IBAN', NOW(), 3);
