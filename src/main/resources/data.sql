INSERT INTO users (id, name, email, password)
VALUES (1, 'Admin Adminov', 'admin@mail.com',
        'f8e08577b9bf38e1e7e06c19a6fba34a5b2a158b879e2c30f022f57c68b516f94aaf4f0f8d593fe9cadb3ca8206fb392');

INSERT INTO users_roles (users_id, roles_id)
VALUES (1, 1),
       (1, 2);

INSERT INTO makers (id, name)
VALUES (1, 'BMW'),
       (2, 'YAMAHA'),
       (3, 'AUDI');