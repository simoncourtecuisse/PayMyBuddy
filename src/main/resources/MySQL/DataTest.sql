USE paymybuddy;

INSERT INTO user (user_id, first_name, last_name, email, password, wallet_balance)
VALUES (1, 'Simon', 'Courtecuisse', 'simon.courtecuisse@example.com', '123456', 34),
       (2, 'Simon', 'Soulie', 'simon.soulie@example.com', 'fdsdsdqgf', 67),
       (3, 'Marion', 'Aillerie', 'marion.aillerie@example.com', 'pooiuyezesd', 91),
       (4, 'Igor', 'Courtecuisse', 'igor.courtecuisse@example.com', 'azsqdqfbfdf', 95),
       (5, 'Romain', 'Luscan', 'romain.luscan@example.com', 'sdf65+6sdf', 200);
       
INSERT INTO contact(user_id, friend_user_id)
VALUES (1, 2),
       (2, 1),
       (1, 3),
       (3, 1),
       (2, 3),
       (3, 2),
       (4, 1),
       (1, 4);

INSERT INTO bank_account (bank_account_id, user_id, iban, bank_name)
VALUES (1, 1, '232625', 'BNP Paribas'),
       (2, 2, '685137', 'Société Générale'),
       (3, 3, '809572', 'Société Générale'),
       (4, 4, '158338', 'ING Direct');

INSERT INTO transaction_label (transaction_label_id, description)
VALUES (1, 'restaurant bill'),
       (2, 'knicks game'),
       (3, 'bar bill'),
       (4, 'rent bike'),
       (5, 'birthday dinner'),
       (6, 'cinema ticket');

INSERT INTO transaction (transaction_id, transaction_label_id, creditor_id, debtor_id, amount, commission, date)
VALUES (1, 2, 1, 2, 300.00, 0.5, '2019-01-01'),
       (2, 1, 3, 1, 15.00, 0.1, '2019-01-02'),
       (3, 5, 2, 3, 18.25, 0.1, '2019-02-25'),
       (4, 6, 3, 4, 17.00, 0.2, '2019-03-19'),
       (5, 3, 1, 4, 12.50, 0.3, '2019-04-18'),
       (6, 4, 3, 2, 25.00, 0.5, '2019-05-08');
       
INSERT INTO bank_transaction (bank_transaction_id, amount, commission, date, bank_account_id, user_id)
VALUES (1, 10, 0.5, '2019-01-01', 1, 1),
       (2, 100, 0.5, '2020-01-01', 1, 1),
       (3, 200, 0.5, '2021-01-01', 1, 1);

UPDATE user_sequence
SET next_val = '6';

UPDATE bank_account_sequence
SET next_val = '5';

UPDATE transaction_label_sequence
SET next_val = '7';

UPDATE transaction_sequence
SET next_val = '7';

UPDATE bank_transaction_sequence
SET next_val = '4';