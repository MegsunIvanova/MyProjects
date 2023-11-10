INSERT INTO users (id, name, email, password)
VALUES (1, 'Admin Adminov', 'admin@mail.com',
        'f8e08577b9bf38e1e7e06c19a6fba34a5b2a158b879e2c30f022f57c68b516f94aaf4f0f8d593fe9cadb3ca8206fb392');

INSERT INTO users_roles (users_id, roles_id)
VALUES (1, 1),
       (1, 2);

INSERT INTO makes (id, name)
VALUES (1, 'BMW'),
       (2, 'YAMAHA'),
       (3, 'AUDI');

INSERT INTO models (id, model, type, make_id)
VALUES (1, 'S 1000 RR', 'MOTORCYCLE', 1),
       (2, 'A6', 'AUTOMOBILE', 3),
       (3, '550 I', 'AUTOMOBILE', 1),
       (4, 'Q5 PREMIUM', 'AUTOMOBILE', 3),
       (5, 'YZFR1', 'MOTORCYCLE', 2);