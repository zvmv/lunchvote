INSERT INTO users (name, email, enabled, password)
VALUES ('User', 'user@yandex.ru', true, 'password'),
       ('Admin', 'admin@gmail.com', true, 'admin');

INSERT INTO user_roles (user_id, roles)
VALUES (1, 'USER'),
       (2, 'ADMIN');

INSERT INTO restaurant (name, address)
VALUES ('Мак дональдс', 'пл.Ленина'),
       ('Волна', 'ст.Кстово'),
       ('Мир вкуса', 'рядом с т.ц. Восторг'),
       ('Автосуши', 'рядом с т.ц. Восторг');