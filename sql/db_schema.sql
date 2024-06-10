CREATE TABLE roles
(
    role_id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    UNIQUE (name)
);

CREATE TABLE users
(
    user_id SERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    role_id INTEGER NOT NULL,
    FOREIGN KEY (role_id) REFERENCES roles(role_id),
    UNIQUE (email, role_id)
);

CREATE TABLE parking_lots
(
    parking_lot_id SERIAL PRIMARY KEY,
    latitude DECIMAL(8,6),
    longitude DECIMAL(9,6)
);

CREATE TABLE parking_spot_types
(
    parking_spot_type_id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE parking_spots
(
    parking_spot_id SERIAL PRIMARY KEY,
    parking_lot_id INTEGER NOT NULL,
    parking_spot_type_id INTEGER NOT NULL,
    latitude DECIMAL(8,6),
    longitude DECIMAL(9,6),
    FOREIGN KEY (parking_lot_id) REFERENCES parking_lots(parking_lot_id),
    FOREIGN KEY (parking_spot_type_id) REFERENCES parking_spot_types(parking_spot_type_id)
);

CREATE TABLE parking_spot_statuses
(
    parking_spot_status_id SERIAL PRIMARY KEY,
    parking_spot_id INTEGER NOT NULL,
    is_taken BOOLEAN NOT NULL,
    FOREIGN KEY (parking_spot_id) REFERENCES parking_spots(parking_spot_id)
);

CREATE TABLE reservations
(
    reservation_id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL,
    parking_spot_id INTEGER NOT NULL,
    start_time TIMESTAMP(0) NOT NULL,
    end_time TIMESTAMP(0) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (parking_spot_id) REFERENCES parking_spots(parking_spot_id)
);
