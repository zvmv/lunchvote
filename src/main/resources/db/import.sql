INSERT INTO users (name, email, enabled, password)
VALUES ('User', 'user@yandex.ru', true, 'password'),
       ('Admin', 'admin@gmail.com', true, 'admin');

INSERT INTO user_roles (user_id, roles)
VALUES (1, 'USER'),
       (2, 'ADMIN');
