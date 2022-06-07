USE paymybuddy;

INSERT INTO user (user_id, first_name, last_name, email, password, wallet_balance)
VALUES (0, 'admin', '', 'admin@paymybuddy.com', '$2a$10$GolCHmo.CxLanyXeHagmEOwS9H/zL1xa9ar09.MpEJbE019YWRvJa', 0),  -- password: admin123
       (1, 'Simon', 'Courtecuisse', 'simon.courtecuisse@example.com', '$2a$10$oOf.fKXV2R4jLjwr058nLe8XIBQ4VOF49wVN94QFOvoL9Bmxdk/76', 34), -- password: 123456
       (2, 'Simon', 'Soulie', 'simon.soulie@example.com', '$2a$10$ya6bHI8p5pgHR/eTuztA3eckV3/qVJU3MG7uY.lK5TUKJK9iBY8YK', 67), -- password: fdsdsdqgf
       (3, 'Marion', 'Aillerie', 'marion.aillerie@example.com', '$2a$10$XZsHEx8kTZViU4QyT9PccOhLsvCv/dRu6.YFeOd.SbtHMxOBpLF.W', 91), -- password: pooiuyezesd
       (4, 'Igor', 'Courtecuisse', 'igor.courtecuisse@example.com', '$2a$10$1wmzD89j2dIQofX5NTy0P.VmoLmZIpJhBbH6FriPcCrAc0uVNuKJm', 95), -- password: azsqdqfbfdf
       (5, 'Romain', 'Luscan', 'romain.luscan@example.com', '$2a$10$NgutpjGDp4WGAl6hgU0QNeboJJxF9JRfJEfuKYV4Dys9V6PyzdbtC', 200); -- password: sdf656sdf
       
INSERT INTO contact(user_id, friend_user_id)
VALUES (1, 2),
       (2, 1),
       (1, 3),
       (3, 1),
       (2, 3),
       (3, 2),
       (4, 1),
       (1, 4);

INSERT INTO roles(name)
VALUES ('ROLE_USER'),
       ('ROLE_ADMIN');

INSERT INTO bank_account (bank_account_id, user_id, iban, bank_name)
VALUES (1, 1, '232625', 'BNP Paribas'),
       (2, 2, '685137', 'Société Générale'),
       (3, 3, '809572', 'Société Générale'),
       (4, 4, '158338', 'ING Direct');

INSERT INTO user_roles (user_id, role_id)
VALUES (0, 2);

INSERT INTO transaction (transaction_id, description, creditor_id, debtor_id, amount, commission, date)
VALUES (1, 'knicks game', 1, 2, 300.00, 0.5, '2019-01-01'),
       (2, 'restaurant bill', 3, 1, 15.00, 0.1, '2019-01-02'),
       (3, 'birthday dinner', 2, 3, 18.25, 0.1, '2019-02-25'),
       (4, 'cinema ticket', 3, 4, 17.00, 0.2, '2019-03-19'),
       (5, 'bar bill', 1, 4, 12.50, 0.3, '2019-04-18'),
       (6, 'rent bike', 3, 2, 25.00, 0.5, '2019-05-08');
       
INSERT INTO bank_transaction (bank_transaction_id, amount, commission, date, bank_account_id, user_id)
VALUES (1, 10, 0.5, '2019-01-01', 1, 1),
       (2, 100, 0.5, '2020-01-01', 1, 1),
       (3, 200, 0.5, '2021-01-01', 1, 1);

UPDATE user_sequence
SET next_val = '6';

UPDATE bank_account_sequence
SET next_val = '5';

UPDATE transaction_sequence
SET next_val = '7';

UPDATE bank_transaction_sequence
SET next_val = '4';