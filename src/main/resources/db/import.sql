INSERT INTO users (name, email, enabled, password, admin)
VALUES ('User', 'user@yandex.ru', true, 'password', false),
       ('Admin', 'admin@gmail.com', true, 'admin', true);

INSERT INTO restaurant (name, address)
VALUES ('Мак дональдс', 'пл.Ленина'),
       ('Волга', 'ст.Кстово'),
       ('Мир вкуса', 'рядом с т.ц. Восторг'),
       ('Автосуши', 'рядом с т.ц. Восторг');

INSERT INTO menu (restaurant, date, dishes, price)
VALUES ('Mc.Donalds', CURRENT_DATE, 'Бигтейсти, картошка фри, кола', 300),
       ('Волга', CURRENT_DATE, 'Салат, солянка, чай/кофе', 400),
       ('Мир вкуса', CURRENT_DATE, 'Пицца "Пепперони"', 450),
       ('Автосуши', CURRENT_DATE, 'Филадельфия роллы', 400);