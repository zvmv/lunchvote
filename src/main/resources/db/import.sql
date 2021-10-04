INSERT INTO users (name, email, enabled, password, admin)
VALUES ('User', 'user@yandex.ru', true, 'password', false),
       ('Admin', 'admin@gmail.com', true, 'admin', true);

INSERT INTO user_roles (user_id, roles)
VALUES (1, 'USER'),
       (2, 'ADMIN');

INSERT INTO restaurant (name, address)
VALUES ('Мак дональдс', 'пл.Ленина'),
       ('Волга', 'ст.Кстово'),
       ('Мир вкуса', 'рядом с т.ц. Восторг'),
       ('Автосуши', 'рядом с т.ц. Восторг');

INSERT INTO menu (restaurant_id, menudate, dishes, price)
VALUES (1, CURRENT_DATE, 'Бигтейсти, картошка фри, кола', 300),
       (2, CURRENT_DATE, 'Салат, солянка, чай/кофе', 400),
       (3, CURRENT_DATE, 'Пицца "Пепперони"', 450),
       (4, CURRENT_DATE, 'Филадельфия роллы', 400);