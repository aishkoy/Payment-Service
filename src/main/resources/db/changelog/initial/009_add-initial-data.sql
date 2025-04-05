--changeset Aisha:add_initial_data
-- Insert roles
INSERT INTO roles (role)
VALUES ('ADMIN'),
       ('USER');

-- Insert currencies
INSERT INTO currencies (currency)
VALUES ('USD'),
       ('EUR'),
       ('KGS');

-- Insert currency conversion rates
INSERT INTO currency_converter (from_currency_id, to_currency_id, rate)
VALUES
    ((SELECT id FROM currencies WHERE currency = 'USD'), (SELECT id FROM currencies WHERE currency = 'EUR'), 0.92),
    ((SELECT id FROM currencies WHERE currency = 'USD'), (SELECT id FROM currencies WHERE currency = 'KGS'), 89.41),
    ((SELECT id FROM currencies WHERE currency = 'EUR'), (SELECT id FROM currencies WHERE currency = 'USD'), 1.09),
    ((SELECT id FROM currencies WHERE currency = 'EUR'), (SELECT id FROM currencies WHERE currency = 'KGS'), 97.36),
    ((SELECT id FROM currencies WHERE currency = 'KGS'), (SELECT id FROM currencies WHERE currency = 'USD'), 0.0112),
    ((SELECT id FROM currencies WHERE currency = 'KGS'), (SELECT id FROM currencies WHERE currency = 'EUR'), 0.0103);

-- Insert transaction statuses
INSERT INTO transaction_statuses (status)
VALUES ('COMPLETED'),
       ('ROLLED_BACK'),
       ('PENDING'),
       ('DELETED');

-- Insert users
INSERT INTO users (name, phone_number, email, password, role_id, enabled)
VALUES ('John Doe', '996 (666) 66-66-66', 'john.doe@example.com', '$2a$12$CR.NIg7ZFIknaqY6.fwUou.pkQWejZqL7tk7uzJEIJSoR6OHlp/da',
        (SELECT id FROM roles WHERE role = 'USER'), true),
       ('John Doe', '996 (555) 55-55-55', 'jane.smith@example.com', '$2a$12$CR.NIg7ZFIknaqY6.fwUou.pkQWejZqL7tk7uzJEIJSoR6OHlp/da',
        (SELECT id FROM roles WHERE role = 'USER'), true),
       ('12345', '996 (999) 99-99-99', '12345', '$2a$12$qwlWOaWric/SiLrf9QAYuOsV1oD1zQ0BDW6JhOe3.okXuvfudKFra',
        (SELECT id FROM roles WHERE role = 'ADMIN'), true);
-- passwords: qwerty

-- Insert accounts
INSERT INTO accounts (user_id, currency_id, balance)
VALUES ((SELECT id FROM users WHERE email = 'john.doe@example.com'),
        (SELECT id FROM currencies WHERE currency = 'USD'), 1000.00),
       ((SELECT id FROM users WHERE email = 'jane.smith@example.com'),
        (SELECT id FROM currencies WHERE currency = 'EUR'), 1500.50);