CREATE DATABASE IF NOT EXISTS paymybuddy;
use paymybuddy;

CREATE TABLE IF NOT EXISTS `user` (
    `user_id` BIGINT NOT NULL,
    `email` VARCHAR(255) NOT NULL,
    `first_name` VARCHAR(255) NOT NULL,
    `last_name` VARCHAR(255) NOT NULL,
    `password` VARCHAR(255) NOT NULL,
    `wallet_balance` DECIMAL(6 , 2 ) DEFAULT NULL,
    PRIMARY KEY (`user_id`),
    UNIQUE KEY `user_email_unique` (`email`)
);

CREATE TABLE IF NOT EXISTS `roles` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(20) DEFAULT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `user_roles` (
    `user_id` BIGINT NOT NULL,
    `role_id` INT NOT NULL,
    PRIMARY KEY (`user_id` , `role_id`),
    KEY `FKh8ciramu9cc9q3qcqiv4ue8a6` (`role_id`),
    CONSTRAINT `FK55itppkw3i07do3h7qoclqd4k` FOREIGN KEY (`user_id`)
        REFERENCES `user` (`user_id`),
    CONSTRAINT `FKh8ciramu9cc9q3qcqiv4ue8a6` FOREIGN KEY (`role_id`)
        REFERENCES `roles` (`id`)
);

CREATE TABLE IF NOT EXISTS `user_sequence` (
    `next_val` BIGINT DEFAULT NULL
);

CREATE TABLE IF NOT EXISTS `bank_account` (
    `bank_account_id` BIGINT NOT NULL,
    `bank_name` VARCHAR(255) DEFAULT NULL,
    `iban` INT NOT NULL,
    `user_id` BIGINT DEFAULT NULL,
    PRIMARY KEY (`bank_account_id`),
    KEY `FK92iik4jwhk7q385jubl2bc2mm` (`user_id`),
    CONSTRAINT `FK92iik4jwhk7q385jubl2bc2mm` FOREIGN KEY (`user_id`)
        REFERENCES `user` (`user_id`)
);

CREATE TABLE IF NOT EXISTS `bank_account_sequence` (
    `next_val` BIGINT DEFAULT NULL
);

CREATE TABLE IF NOT EXISTS `bank_transaction` (
    `bank_transaction_id` BIGINT NOT NULL,
    `amount` DOUBLE NOT NULL,
    `commission` DOUBLE DEFAULT NULL,
    `date` DATE DEFAULT NULL,
    `bank_account_id` BIGINT DEFAULT NULL,
    `user_id` BIGINT DEFAULT NULL,
    PRIMARY KEY (`bank_transaction_id`),
    KEY `FKnpwg5dyb1nfbuauelluot89xh` (`bank_account_id`),
    KEY `FKrhhhx30svpp1yc1bgm9bcv5vm` (`user_id`),
    CONSTRAINT `FKnpwg5dyb1nfbuauelluot89xh` FOREIGN KEY (`bank_account_id`)
        REFERENCES `bank_account` (`bank_account_id`),
    CONSTRAINT `FKrhhhx30svpp1yc1bgm9bcv5vm` FOREIGN KEY (`user_id`)
        REFERENCES `user` (`user_id`)
);

CREATE TABLE IF NOT EXISTS `bank_transaction_sequence` (
    `next_val` BIGINT DEFAULT NULL
);

CREATE TABLE IF NOT EXISTS `contact` (
    `user_id` BIGINT NOT NULL,
    `friend_user_id` BIGINT NOT NULL,
    KEY `FKn82i63mg2clkicnk8tc47i0d8` (`friend_user_id`),
    KEY `FKe07k4jcfdophemi6j1lt84b61` (`user_id`),
    CONSTRAINT `FKe07k4jcfdophemi6j1lt84b61` FOREIGN KEY (`user_id`)
        REFERENCES `user` (`user_id`),
    CONSTRAINT `FKn82i63mg2clkicnk8tc47i0d8` FOREIGN KEY (`friend_user_id`)
        REFERENCES `user` (`user_id`)
);

CREATE TABLE IF NOT EXISTS `transaction` (
    `transaction_id` BIGINT NOT NULL,
    `amount` DOUBLE NOT NULL,
    `commission` DOUBLE NOT NULL,
    `date` DATE DEFAULT NULL,
    `description` VARCHAR(255) NOT NULL,
    `creditor_id` BIGINT DEFAULT NULL,
    `debtor_id` BIGINT DEFAULT NULL,
    PRIMARY KEY (`transaction_id`),
    KEY `FK4dvla4ar6iubq2la4aa0ftgge` (`creditor_id`),
    KEY `FK7xdw4xwe494cbpv0i3rb3tb3x` (`debtor_id`),
    CONSTRAINT `FK4dvla4ar6iubq2la4aa0ftgge` FOREIGN KEY (`creditor_id`)
        REFERENCES `user` (`user_id`),
    CONSTRAINT `FK7xdw4xwe494cbpv0i3rb3tb3x` FOREIGN KEY (`debtor_id`)
        REFERENCES `user` (`user_id`)
);

CREATE TABLE IF NOT EXISTS `transaction_sequence` (
    `next_val` BIGINT DEFAULT NULL
);

