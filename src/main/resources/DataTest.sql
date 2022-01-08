USE paymybuddy;
INSERT INTO user (id, firstname, lastname, email, password, balance)
VALUES (1, 'Simon', 'Courtecuisse', 'simon.courtecuisse@example.com', 'sdsksnfosmfnsmdf', 1234),
       (2, 'Simon', 'Soulie', 'simon.soulie@example.com', 'fdsdsdqgf', 4567),
       (3, 'Marion', 'Aillerie', 'marion.aillerie@example.com', 'pooiuyezesd', 7891),
       (4, 'Igor', 'Courtecuisse', 'igor.courtecuisse@example.com', 'azsqdqfbfdf', 9595);

INSERT INTO contact(user_id, friend_user_id)
VALUES (1, 2),
       (1, 3),
       (2, 3),
       (4, 1);

INSERT INTO bank_account (id, user_id, iban)
VALUES (1, 1, '232625'),
       (2, 2, '685137'),
       (3, 3, '809572'),
       (4, 4, '158338');

INSERT INTO transaction_label (id, description)
VALUES (1, 'restaurant bill'),
       (2, 'knicks game'),
       (3, 'bar bill'),
       (4, 'rent bike'),
       (5, 'birthday dinner'),
       (6, 'cinema ticket');

INSERT INTO transaction (id, transaction_label_id, creditor_id, debtor_id, amount, commission, date)
VALUES (1, 2, 1, 2, 300.00, 0.5, '2019-01-01'),
       (2, 1, 3, 1, 15.00, 0.1, '2019-01-02'),
       (3, 5, 2, 3, 18.25, 0.1, '2019-02-25'),
       (4, 6, 3, 4, 17.00, 0.2, '2019-03-19'),
       (5, 3, 1, 4, 12.50, 0.3, '2019-04-18'),
       (6, 4, 3, 2, 25.00, 0.5, '2019-05-08');

