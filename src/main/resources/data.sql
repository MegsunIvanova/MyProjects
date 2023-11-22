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

INSERT
INTO models (id, name, type, make_id)
VALUES (1, 'S 1000', 'MOTORCYCLE', 1),
       (2, 'A6', 'AUTOMOBILE', 3),
       (3, '550', 'AUTOMOBILE', 1),
       (4, 'Q5', 'AUTOMOBILE', 3),
       (5, 'YZFR1', 'MOTORCYCLE', 2);

INSERT INTO vehicles (id, uuid, engine, transmission, year, model_id, vin, odometer_km, notes)
VALUES (1, '45c4011d-5bc7-4047-a3c4-3eaf126102d9', 'PETROL', 'AUTOMATIC', 2012, 3, 'WBAGN83493D479736', 180000, '...'),
       (2, 'c5f130a2-a7c3-4e01-abae-e3cf860d42a9', 'PETROL', 'MANUAL', 2022, 1, 'WBADM6334XB728241', 10000, 'Crashed'),
       (3, 'cf5667f3-de59-4c9a-821a-8a2dd0e2a897', 'PETROL', 'AUTOMATIC', 2018, 4, 'WA1WFDFPXDA095665', 215000,
        'Run and Drive'),
       (4, '6df5b107-a342-48b9-a899-252e5a1f1e25', 'PETROL', 'AUTOMATIC', 2013, 2, 'WAUKFBFM4BA119967', 130000,
        'Run and Drive'),
       (5, 'c5c9f8b5-f9a1-4b4e-82d1-4ba3290d94da', 'PETROL', 'MANUAL', 2023, 5, 'JY41YW003HC005055', 35000, 'Crashed'),
       (6, 'ee481fae-316e-4a09-a775-29cd48488afc', 'PETROL', 'AUTOMATIC', 2023, 2, 'WAUAF68E18A010612', 5000, ''),
       (7, 'c8c4384e-2c6f-414c-9ba3-d7c91be5ea5b', 'PETROL', 'MANUAL', 2023, 1, '', 4100, '');

INSERT INTO pictures (id, vehicle_id, url)
VALUES (1, 1, 'https://cs.copart.com/v1/AUTH_svc.pdoc00001/lpp/0623/ab4232eed57e493eb2ed4464ec770428_ful.jpg'),
       (2, 1, 'https://cs.copart.com/v1/AUTH_svc.pdoc00001/lpp/0623/9d148f4b544741c4a0762363c0587180_ful.jpg'),
       (3, 1, 'https://cs.copart.com/v1/AUTH_svc.pdoc00001/lpp/0623/94e0d9aeaf66423da4cf235e10a1250f_ful.jpg'),
       (4, 3, 'https://cs.copart.com/v1/AUTH_svc.pdoc00001/lpp/1023/6325ed4a6d7b45008e5091c44b7fde4f_ful.jpg'),
       (5, 3, 'https://cs.copart.com/v1/AUTH_svc.pdoc00001/lpp/1023/3c267500b202438ba6c83121f3512a2f_ful.jpg'),
       (6, 5, 'https://cs.copart.com/v1/AUTH_svc.pdoc00001/lpp/0823/afecee0b7efb46789a4b32d28f5af719_ful.jpg'),
       (7, 5, 'https://cs.copart.com/v1/AUTH_svc.pdoc00001/lpp/0823/b426bd755ae649a7bc75aae30b0b99b6_ful.jpg'),
       (8, 6, 'https://cs.copart.com/v1/AUTH_svc.pdoc00001/lpp/1023/375887e0d3784f3bbcc69b254a6c6a32_ful.jpg'),
       (9, 6, 'https://cs.copart.com/v1/AUTH_svc.pdoc00001/lpp/1023/aa451c322a3141708fe24aa0484d28a7_ful.jpg'),
       (10, 6, 'https://cs.copart.com/v1/AUTH_svc.pdoc00001/lpp/1023/f24a832ae98d46d49952d95d6061eb80_ful.jpg'),
       (11, 7, 'https://cs.copart.com/v1/AUTH_svc.pdoc00001/lpp/1023/0f50c7b072bb450aaf6423c25e14b156_ful.jpg'),
       (12, 7, 'https://cs.copart.com/v1/AUTH_svc.pdoc00001/lpp/1023/c1f35df97fe84a318f18e8c7339ef9b2_ful.jpg'),
       (13, 7, 'https://cs.copart.com/v1/AUTH_svc.pdoc00001/lpp/1023/1896fa2af717442088beff9af3bb13d4_ful.jpg');

UPDATE vehicles SET primary_image_id = 2 WHERE id = 1;
UPDATE vehicles SET primary_image_id = 5 WHERE id = 3;
UPDATE vehicles SET primary_image_id = 6 WHERE id = 5;
UPDATE vehicles SET primary_image_id = 8 WHERE id = 6;
UPDATE vehicles SET primary_image_id = 11 WHERE id = 7;

INSERT INTO currencies(id, rate_to_bgn)
VALUES ('BGN', 1.00000),
       ('USD', 1.78533),
       ('EUR', 1.95583);

INSERT INTO costs (id, amount, description, transaction_rate, completed, type, currency_id, vehicle_id)
VALUES (1, 10000.00, '', null, false, 'VEHICLE_AUCTION_PRICE', 'EUR', 5),
       (2, 35000.00, 'initial auction price', 1.78533, true, 'VEHICLE_AUCTION_PRICE', 'USD', 6),
       (3, 200.00, '', null, false, 'CONTAINER', 'USD', 6),
       (4, 2000.00, '', null, false, 'AUCTION_COMMISSION', 'USD', 6),
       (5, 4000.00, '', null, false, 'VAT_DUTY', 'EUR', 6),
       (6, 26000.00, '', 1.83422, true, 'VEHICLE_AUCTION_PRICE', 'EUR', 7),
       (7, 1350.00, '', null, false, 'CONTAINER', 'EUR', 7),
       (8, 400.00, '', 1.8394, false, 'AGENT_COMMISSION', 'USD', 7),
       (9, 2000.00, '', 1.8394, false, 'AUCTION_COMMISSION', 'USD', 7),
       (10, 600.00, 'transport in US', 1.84617, true, 'TRANSPORT_EXTERNAL', 'USD', 7),
       (11, 250.00, 'bank transfer', 1.84617, true, 'BANK_FEES', 'USD', 7),
       (12, 14000.00, '', null, false, 'VAT_DUTY', 'BGN', 7),
       (13, 300.00, 'paint job', null, false, 'REPAIRMENT_SERVICES', 'BGN', 7),
       (14, 400.00, 'spoiler', null, false, 'REPAIRMENT_PARTS', 'EUR', 7),
       (15, 100.00, '', null, false, 'TECHNICAL_INSPECTION', 'BGN', 7),
       (16, 150.00, '', null, false, 'TRANSPORT_INTERNAL', 'BGN', 7),
       (17, 80.00, 'stand', 1.00000, true, 'REPAIRMENT_PARTS', 'BGN', 7);