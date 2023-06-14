-- Users
insert into users (id, username, password, email, is_verified, created_at, updated_at) values (1, 'user123', '1234', 'user1@mail.com', false, '2023-04-15T16:25:00.000', '2023-04-15T16:25:00.000');


-- UserBalance
insert into user_balance (id, balance_type, amount, created_at, updated_at, user_id) values  (1, 'CASH', 10000, '2023-04-15T16:25:00.000', '2023-04-15T16:25:00.000', 1);