CREATE DATABASE bookstore;
USE bookstore;

CREATE TABLE admin (

                       username VARCHAR(255) NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       question VARCHAR(225) NOT NULL,
                       answer VARCHAR(225) NOT NULL,
                       privacy_code VARCHAR(255) DEFAULT 'abc123'
);

CREATE TABLE user (
                      id VARCHAR(255) NOT NULL,
                      name VARCHAR(255) NOT NULL,
                      phone_number VARCHAR(255) NOT NULL,
                      address VARCHAR(255) NOT NULL,
                      email VARCHAR(255) NOT NULL,
                      image VARCHAR(500),
                      password VARCHAR(255),
                      question VARCHAR(255),
                      answer VARCHAR(255),
                      total FLOAT NOT NULL,
                      buy_date DATE,
                      fav_genre  VARCHAR(255) NOT NULL,
                      fav_author  VARCHAR(255) NOT NULL,
                      PRIMARY KEY (id)
);

CREATE TABLE product (
                         id VARCHAR(255) NOT NULL,
                         type VARCHAR(20) NOT NULL,         -- 'Book', 'Stationery', 'Toy', ...
                         name VARCHAR(255) NOT NULL,        -- tên hiển thị chung (dùng cho tất cả loại)
                         image VARCHAR(500),
                         distributor VARCHAR(255) NOT NULL,
                         description TEXT,
                         added_date DATE,
                         stock INT NOT NULL DEFAULT 0,
                         import_price FLOAT NOT NULL,
                         sell_price FLOAT NOT NULL,
                         sell_quantity INT NOT NULL DEFAULT 0,
                         age_restrict INT DEFAULT 0,
    -- Thông tin riêng cho sách (có thể NULL nếu không phải Book)
                         isbn VARCHAR(255),
                         author VARCHAR(255),
                         genre VARCHAR(255),
                         pub_date DATE,
                         PRIMARY KEY (id)
);

select * from product
