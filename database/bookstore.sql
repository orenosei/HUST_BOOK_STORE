CREATE DATABASE bookstore;
USE bookstore;
CREATE TABLE admin (
                       id INT AUTO_INCREMENT,
                       fullname VARCHAR(255) NOT NULL,
                       username VARCHAR(255) NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       question VARCHAR(225) NOT NULL,
                       answer VARCHAR(225) NOT NULL,
                       privacy_code VARCHAR(255) DEFAULT 'abc123',
                       date DATE,
                       PRIMARY KEY (id)
);

CREATE TABLE book (
                      isbn VARCHAR(225) NOT NULL,
                      title VARCHAR(225) NOT NULL,
                      author VARCHAR(225) NOT NULL,
                      genre VARCHAR(225) NOT NULL,
                      image VARCHAR(500),
                      distributor VARCHAR(225) NOT NULL,
                      description VARCHAR(225) NOT NULL,
                      pub_date DATE,
                      sell_date DATE,
                      quantity INT NOT NULL,
                      age_restrict INT NOT NULL,
                      remain INT NOT NULL,
                      import_price FLOAT NOT NULL,
                      sell_price FLOAT NOT NULL,
                      sell_quantity INT NOT NULL,
                      PRIMARY KEY(isbn)
);

CREATE TABLE vpp (
                     id VARCHAR(225) NOT NULL,
                     name VARCHAR(225) NOT NULL,
                     image VARCHAR(500),
                     distributor VARCHAR(225) NOT NULL,
                     description VARCHAR(225) NOT NULL,
                     pub_date DATE,
                     sell_date DATE,
                     quantity INT NOT NULL,
                     age_restrict INT NOT NULL,
                     remain INT NOT NULL,
                     import_price FLOAT NOT NULL,
                     sell_price FLOAT NOT NULL,
                     sell_quantity INT NOT NULL,
                     PRIMARY KEY(id)
);

CREATE TABLE toy (
                     id VARCHAR(225) NOT NULL,
                     name VARCHAR(225) NOT NULL,
                     image VARCHAR(500),
                     distributor VARCHAR(225) NOT NULL,
                     description VARCHAR(225) NOT NULL,
                     pub_date DATE,
                     sell_date DATE,
                     quantity INT NOT NULL,
                     age_restrict INT NOT NULL,
                     remain INT NOT NULL,
                     import_price FLOAT NOT NULL,
                     sell_price FLOAT NOT NULL,
                     sell_quantity INT NOT NULL,
                     PRIMARY KEY(id)
);

CREATE TABLE user (
                      id VARCHAR(255) NOT NULL,
                      name VARCHAR(255) NOT NULL,
                      phonenumber VARCHAR(255) NOT NULL,
                      email VARCHAR(255) NOT NULL,
                      image VARCHAR(500),
                      createdDate Date,
                      password VARCHAR(255),
                      question VARCHAR(255),
                      answer VARCHAR(255),
                      total FLOAT NOT NULL,
                      buy_date DATE,
                      cashier VARCHAR(255) NOT NULL,
                      payment_method VARCHAR(255) NOT NULL,
                      fav_genre  VARCHAR(255) NOT NULL,
                      fav_author  VARCHAR(255) NOT NULL,
                      PRIMARY KEY (id)
);

-- CREATE TABLE issue (
-- 	msv VARCHAR(255) NOT NULL,
--     name VARCHAR(255) NOT NULL,
--     isbn VARCHAR(255) NOT NULL,
--     title VARCHAR(255) NOt null,
--     issueDate DATE,
--     dueDate DATE,
--     returnDate DATE,
-- 	id INT AUTO_INCREMENT PRIMARY KEY,
-- 	CONSTRAINT fk_msv FOREIGN KEY (msv) REFERENCES user(msv) ON DELETE CASCADE
-- );

select * from admin;