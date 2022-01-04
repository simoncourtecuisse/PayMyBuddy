CREATE DATABASE IF NOT EXISTS paymybuddytest;

CREATE TABLE IF NOT EXISTS paymybuddytest.user (
id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
firstname VARCHAR(255) NOT NULL,
lastname VARCHAR(255) NOT NULL,
email VARCHAR(255) NOT NULL UNIQUE,
password VARCHAR(255) NOT NULL,
balance DECIMAL(6,2) NOT NULL
);

CREATE TABLE IF NOT EXISTS paymybuddytest.bank_account (
id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
user_id BIGINT NOT NULL,
iban INTEGER NOT NULL
);

CREATE TABLE IF NOT EXISTS paymybuddytest.transaction (
id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
transaction_label_id BIGINT NOT NULL,
creditor_id BIGINT NOT NULL,
debtor_id BIGINT NOT NULL,
amount DECIMAL(6,2) NOT NULL,
commission DECIMAL(6,2) NOT NULL,
date DATE NOT NULL
);

CREATE TABLE IF NOT EXISTS paymybuddytest.transaction_label (
id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
description VARCHAR(1000) NOT NULL
);

CREATE TABLE IF NOT EXISTS paymybuddytest.contact (
user_id BIGINT NOT NULL,
friend_user_id BIGINT NOT NULL,
PRIMARY KEY (user_id, friend_user_id)
);

ALTER TABLE paymybuddytest.contact
ADD CONSTRAINT user_contact_fk FOREIGN KEY (user_id) REFERENCES user (id)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE paymybuddytest.contact
ADD CONSTRAINT user_contact_fk1 FOREIGN KEY (friend_user_id) REFERENCES user (id)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE paymybuddytest.bank_account
ADD CONSTRAINT user_bank_account_fk FOREIGN KEY (user_id) REFERENCES user (id)
ON DELETE CASCADE
ON UPDATE NO ACTION;

ALTER TABLE paymybuddytest.transaction
ADD CONSTRAINT user_transaction_fk FOREIGN KEY (creditor_id) REFERENCES user (id)
ON DELETE CASCADE
ON UPDATE NO ACTION;

ALTER TABLE paymybuddytest.transaction
ADD CONSTRAINT user_transaction_fk1 FOREIGN KEY (debtor_id) REFERENCES user (id)
ON DELETE CASCADE
ON UPDATE NO ACTION;

ALTER TABLE paymybuddytest.transaction
ADD CONSTRAINT transaction_label_fk FOREIGN KEY (transaction_label_id) REFERENCES transaction_label (id)
ON DELETE CASCADE
ON UPDATE NO ACTION;