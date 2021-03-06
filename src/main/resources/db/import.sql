INSERT INTO users (name, email, enabled, password, admin)
VALUES ('Mike', 'mike@yandex.ru', true, 'pass', false),
       ('Kelly', 'kelly@yandex.ru', true, 'pass', false),
       ('Bob', 'bob@yandex.ru', true, 'pass', false),
       ('Jack', 'jack@yandex.ru', true, 'pass', false),
       ('Admin', 'admin@yandex.ru', true, 'pass', true);

INSERT INTO menu (restaurant, menudate, dishes, price)
VALUES ('Mc.Donalds', CURRENT_DATE, 'Бигтейсти, картошка фри, кола', 300),
       ('Волга', CURRENT_DATE, 'Салат, солянка, чай/кофе', 400),
       ('Мир вкуса', CURRENT_DATE, 'Пицца "Пепперони"', 450),
       ('Автосуши', CURRENT_DATE, 'Филадельфия роллы', 400),
       ('Столовка', CURRENT_DATE, 'Первое, второе, компот и ватрушка', 150);

INSERT INTO vote (votedate, menu_id, user_id)
VALUES (CURRENT_DATE, 1, 1),
       (CURRENT_DATE, 1, 2),
       (CURRENT_DATE, 2, 3),
       (CURRENT_DATE, 1, 4),
       (CURRENT_DATE, 2, 5),
       ((CURRENT_DATE - 1), 3, 1),
       ((CURRENT_DATE - 1), 3, 2),
       ((CURRENT_DATE - 1), 2, 3),
       ((CURRENT_DATE - 1), 2, 4);
