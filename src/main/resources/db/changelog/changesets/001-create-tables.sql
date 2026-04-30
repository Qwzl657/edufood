--liquibase formatted sql

--changeset edufood:1
CREATE TABLE users (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       email VARCHAR(255) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       name VARCHAR(255) NOT NULL,
                       role VARCHAR(50) NOT NULL
);

--changeset edufood:2
CREATE TABLE cafes (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       name VARCHAR(255) NOT NULL,
                       description VARCHAR(1000),
                       image_url VARCHAR(500)
);

--changeset edufood:3
CREATE TABLE dishes (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        name VARCHAR(255) NOT NULL,
                        description VARCHAR(1000),
                        price DECIMAL(10,2) NOT NULL,
                        cafe_id BIGINT NOT NULL,
                        FOREIGN KEY (cafe_id) REFERENCES cafes(id)
);

--changeset edufood:4
CREATE TABLE orders (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        user_id BIGINT NOT NULL,
                        created_at TIMESTAMP NOT NULL,
                        total_price DECIMAL(10,2) NOT NULL,
                        FOREIGN KEY (user_id) REFERENCES users(id)
);

--changeset edufood:5
CREATE TABLE order_items (
                             id BIGINT AUTO_INCREMENT PRIMARY KEY,
                             order_id BIGINT NOT NULL,
                             dish_id BIGINT NOT NULL,
                             quantity INT NOT NULL,
                             price DECIMAL(10,2) NOT NULL,
                             FOREIGN KEY (order_id) REFERENCES orders(id),
                             FOREIGN KEY (dish_id) REFERENCES dishes(id)
);