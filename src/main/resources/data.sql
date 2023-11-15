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

INSERT INTO models (id, name, type, make_id)
VALUES (1, 'S 1000', 'MOTORCYCLE', 1),
       (2, 'A6', 'AUTOMOBILE', 3),
       (3, '550', 'AUTOMOBILE', 1),
       (4, 'Q5', 'AUTOMOBILE', 3),
       (5, 'YZFR1', 'MOTORCYCLE', 2);

INSERT INTO vehicles (id, uuid, engine, transmission, year, model_id, vin, odometer_km, notes)
VALUES (1, '45c4011d-5bc7-4047-a3c4-3eaf126102d9', 'PETROL', 'AUTOMATIC', 2012, 3, 'WBAGN83493D479736', 180000, '...'),
       (2, 'c5f130a2-a7c3-4e01-abae-e3cf860d42a9', 'PETROL', 'MANUAL', 2022, 1, 'WBADM6334XB728241', 10000, 'Crashed'),
       (3, 'cf5667f3-de59-4c9a-821a-8a2dd0e2a897', 'PETROL', 'AUTOMATIC', 2018, 4, 'WA1WFDFPXDA095665', 215000, 'Run and Drive'),
       (4, '6df5b107-a342-48b9-a899-252e5a1f1e25', 'PETROL', 'AUTOMATIC', 2013, 2, 'WAUKFBFM4BA119967', 130000, 'Run and Drive'),
       (5, 'c5c9f8b5-f9a1-4b4e-82d1-4ba3290d94da', 'PETROL', 'MANUAL', 2023, 5, 'JY41YW003HC005055', 35000, 'Crashed');

INSERT INTO pictures (id, vehicle_id, url)
VALUES (1, 1, 'https://cs.copart.com/v1/AUTH_svc.pdoc00001/lpp/0623/ab4232eed57e493eb2ed4464ec770428_ful.jpg'),
       (2, 1, 'https://cs.copart.com/v1/AUTH_svc.pdoc00001/lpp/0623/9d148f4b544741c4a0762363c0587180_ful.jpg'),
       (3, 1, 'https://cs.copart.com/v1/AUTH_svc.pdoc00001/lpp/0623/94e0d9aeaf66423da4cf235e10a1250f_ful.jpg'),
       (4, 2, 'https://cs.copart.com/v1/AUTH_svc.pdoc00001/lpp/1023/7b9e94a9ced6418cb43a17ba91398ac1_ful.jpg'),
       (5, 2, 'https://cs.copart.com/v1/AUTH_svc.pdoc00001/lpp/1023/5ae5f8c707304b70910bfcb08f4762ea_ful.jpg'),
       (6, 2, 'https://cs.copart.com/v1/AUTH_svc.pdoc00001/lpp/1023/941a0bfd639b419b864e3e870e57950e_ful.jpg'),
       (7, 3, 'https://cs.copart.com/v1/AUTH_svc.pdoc00001/lpp/1023/6325ed4a6d7b45008e5091c44b7fde4f_ful.jpg'),
       (8, 3, 'https://cs.copart.com/v1/AUTH_svc.pdoc00001/lpp/1023/3c267500b202438ba6c83121f3512a2f_ful.jpg'),
       (9, 4, 'https://cs.copart.com/v1/AUTH_svc.pdoc00001/lpp/1023/7cf889963a3a465faaf32aec10aa5c16_ful.jpg'),
       (10, 4, 'https://cs.copart.com/v1/AUTH_svc.pdoc00001/lpp/1023/26ff6a8162734e618bfc3555878fd5ca_ful.jpg'),
       (11, 4, 'https://cs.copart.com/v1/AUTH_svc.pdoc00001/lpp/1023/8d2d0da10c684bfeae624ced1610a972_ful.jpg'),
       (12, 5, 'https://cs.copart.com/v1/AUTH_svc.pdoc00001/lpp/0823/afecee0b7efb46789a4b32d28f5af719_ful.jpg'),
       (13, 5, 'https://cs.copart.com/v1/AUTH_svc.pdoc00001/lpp/0823/b426bd755ae649a7bc75aae30b0b99b6_ful.jpg');

UPDATE vehicles SET primary_image_id = 2 WHERE id=1;
UPDATE vehicles SET primary_image_id = 4 WHERE id = 2;
UPDATE vehicles SET primary_image_id = 7 WHERE id = 3;
UPDATE vehicles SET primary_image_id = 9 WHERE id = 4;
UPDATE vehicles SET primary_image_id = 12 WHERE id = 5;

