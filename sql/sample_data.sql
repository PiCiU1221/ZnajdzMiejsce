INSERT INTO roles (name)
VALUES
    ('ROLE_USER'),
    ('ROLE_ADMINISTRATOR'),
    ('ROLE_DEVELOPER');

INSERT INTO users (email, password, role_id)
VALUES
    ('user1@user.com', 'password', (SELECT role_id FROM roles WHERE name = 'ROLE_USER')),
    ('user2@user.com', 'password', (SELECT role_id FROM roles WHERE name = 'ROLE_USER')),
    ('user3@user.com', 'password', (SELECT role_id FROM roles WHERE name = 'ROLE_USER')),
    ('developer@developer.com', 'password', (SELECT role_id FROM roles WHERE name = 'ROLE_DEVELOPER')),
    ('administrator@administrator.com', 'password', (SELECT role_id FROM roles WHERE name = 'ROLE_ADMINISTRATOR'));

INSERT INTO parking_lots (latitude, longitude)
VALUES
    (53.438464, 14.542034),
    (53.448448, 14.506202),
    (53.425665, 14.554520);

INSERT INTO parking_spot_types (name)
VALUES
    ('Normal'),
    ('Disabled'),
    ('Family'),
    ('Motorcycle'),
    ('Electric');

INSERT INTO parking_spots (parking_lot_id, parking_spot_type_id, latitude, longitude)
VALUES
    (1, (SELECT parking_spot_type_id FROM parking_spot_types WHERE name = 'Normal'), 53.438420, 14.542020),
    (1, (SELECT parking_spot_type_id FROM parking_spot_types WHERE name = 'Disabled'), 53.438418, 14.542018),
    (1, (SELECT parking_spot_type_id FROM parking_spot_types WHERE name = 'Family'), 53.438416, 14.542016),
    (2, (SELECT parking_spot_type_id FROM parking_spot_types WHERE name = 'Motorcycle'), 53.448450, 14.506208),
    (2, (SELECT parking_spot_type_id FROM parking_spot_types WHERE name = 'Electric'), 53.448452, 14.506206),
    (2, (SELECT parking_spot_type_id FROM parking_spot_types WHERE name = 'Family'), 53.448454, 14.506204),
    (3, (SELECT parking_spot_type_id FROM parking_spot_types WHERE name = 'Normal'), 53.425660, 14.554526),
    (3, (SELECT parking_spot_type_id FROM parking_spot_types WHERE name = 'Normal'), 53.425662, 14.554524),
    (3, (SELECT parking_spot_type_id FROM parking_spot_types WHERE name = 'Family'), 53.425664, 14.554522);

INSERT INTO parking_spot_statuses (parking_spot_id, is_taken)
VALUES
    (1, TRUE),
    (2, FALSE),
    (3, FALSE),
    (4, TRUE),
    (5, FALSE),
    (6, FALSE),
    (7, TRUE),
    (8, FALSE),
    (9, FALSE);

INSERT INTO reservations (user_id, parking_spot_id, start_time, end_time)
VALUES
    ((SELECT user_id FROM users WHERE email = 'user1@user.com'), 1, '2024-05-22 10:00:00', '2024-05-22 12:00:00'),
    ((SELECT user_id FROM users WHERE email = 'user2@user.com'), 4, '2024-05-22 11:00:00', '2024-05-22 14:00:00'),
    ((SELECT user_id FROM users WHERE email = 'user3@user.com'), 7, '2024-05-22 20:00:00', '2024-05-22 21:00:00');