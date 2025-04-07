CREATE DATABASE bookstore;
USE bookstore;

#
# CREATE TABLE book (
#     type VARCHAR(20) NOT NULL DEFAULT 'Book',
#     id VARCHAR(255) NOT NULL,
#     isbn VARCHAR(225) NOT NULL,
#     title VARCHAR(225) NOT NULL,
#     author VARCHAR(225) NOT NULL,
#     genre VARCHAR(225) NOT NULL,
#     image VARCHAR(500),
#     distributor VARCHAR(225) NOT NULL,
#     description VARCHAR(225) NOT NULL,
#     pub_date DATE,
#     sell_date DATE,
#     quantity INT NOT NULL,
#     age_restrict INT NOT NULL,
#     remain INT NOT NULL,
#     import_price FLOAT NOT NULL,
#     sell_price FLOAT NOT NULL,
#     sell_quantity INT NOT NULL,
#     PRIMARY KEY(id)
# );
#
# CREATE TABLE stationery (
#     id VARCHAR(225) NOT NULL,
#     type VARCHAR(20) NOT NULL DEFAULT 'Stationery',
#     name VARCHAR(225) NOT NULL,
#     image VARCHAR(500),
#     distributor VARCHAR(225) NOT NULL,
#     description VARCHAR(225) NOT NULL,
#     pub_date DATE,
#     sell_date DATE,
#     quantity INT NOT NULL,
#     age_restrict INT NOT NULL,
#     remain INT NOT NULL,
#     import_price FLOAT NOT NULL,
#     sell_price FLOAT NOT NULL,
#     sell_quantity INT NOT NULL,
#     PRIMARY KEY(id)
# );
#
# CREATE TABLE toy (
#     type VARCHAR(20) NOT NULL DEFAULT 'Toy',
#     id VARCHAR(225) NOT NULL,
#     name VARCHAR(225) NOT NULL,
#     image VARCHAR(500),
#     distributor VARCHAR(225) NOT NULL,
#     description VARCHAR(225) NOT NULL,
#     pub_date DATE,
#     sell_date DATE,
#     quantity INT NOT NULL,
#     age_restrict INT NOT NULL,
#     remain INT NOT NULL,
#     import_price FLOAT NOT NULL,
#     sell_price FLOAT NOT NULL,
#     sell_quantity INT NOT NULL,
#     PRIMARY KEY(id)
# );

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

CREATE TABLE user (
                      id VARCHAR(255) NOT NULL,
                      name VARCHAR(255) NOT NULL,
                      phonenumber VARCHAR(255) NOT NULL,
                      address VARCHAR(255) NOT NULL,
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

# vì trong csdl của mình không có bảng product, nên khi truy vấn tất cả, cần phải gộp 3 bảng book, toy, stationery lại
# SELECT
#     type, id, isbn, title, author, genre, image, distributor, description,
#     pub_date, sell_date, quantity, age_restrict, remain, import_price, sell_price, sell_quantity
# FROM book
# UNION
# SELECT
#     type, id, NULL, name, NULL, NULL, image, distributor, description,
#     pub_date, sell_date, quantity, age_restrict, remain, import_price, sell_price, sell_quantity
# FROM stationery
# UNION
# SELECT
#     type, id, NULL, name, NULL, NULL, image, distributor, description,
#     pub_date, sell_date, quantity, age_restrict, remain, import_price, sell_price, sell_quantity
# FROM toy;
#
# ALTER TABLE book DROP PRIMARY KEY;
# ALTER TABLE book ADD PRIMARY KEY (id);



DROP TABLE IF EXISTS book;
DROP TABLE IF EXISTS stationery;
DROP TABLE IF EXISTS toy;

CREATE TABLE product (
                         id VARCHAR(255) NOT NULL,
                         type VARCHAR(20) NOT NULL,         -- 'Book', 'Stationery', 'Toy', ...
                         name VARCHAR(255) NOT NULL,        -- tên hiển thị chung (dùng cho tất cả loại)
                         image VARCHAR(500),
                         distributor VARCHAR(255) NOT NULL,
                         description TEXT,
                         pub_date DATE,
                         sell_date DATE,                -- ??
                         added_date DATE,
                         quantity INT NOT NULL DEFAULT 0,
                         remain INT NOT NULL DEFAULT 0, -- ??
                         import_price FLOAT NOT NULL,
                         sell_price FLOAT NOT NULL,
                         sell_quantity INT NOT NULL DEFAULT 0,
                         age_restrict INT DEFAULT 0,
    -- Thông tin riêng cho sách (có thể NULL nếu không phải Book)
                         isbn VARCHAR(255),
                         author VARCHAR(255),
                         genre VARCHAR(255),
                         PRIMARY KEY (id)
);



INSERT INTO product (
    id, type, name, image, distributor, description, pub_date, sell_date, added_date,
    quantity, remain, import_price, sell_price, sell_quantity, age_restrict,
    isbn, author, genre
) VALUES
      (
          'B001', 'Book', 'Dế Mèn Phiêu Lưu Ký', 'images/de-men.jpg', 'NXB Kim Đồng',
          'Câu chuyện phiêu lưu hấp dẫn của chú dế mèn.', '2000-06-01', '2024-04-01', '2024-03-31',
          50, 50, 20000, 35000, 0, 6,
          '9786042107632', 'Tô Hoài', 'Thiếu nhi'
      ),
      (
          'B002', 'Book', 'Nhà Giả Kim', 'images/nha-gia-kim.jpg', 'NXB Văn Học',
          'Hành trình tìm kiếm kho báu và chính mình.', '2010-01-01', '2024-04-02', '2024-04-01',
          30, 30, 50000, 79000, 0, 12,
          '9786045678901', 'Paulo Coelho', 'Triết lý'
      );
INSERT INTO product (
    id, type, name, image, distributor, description, pub_date, sell_date, added_date,
    quantity, remain, import_price, sell_price, sell_quantity, age_restrict
) VALUES
      (
          'S001', 'Stationery', 'Bút mực Thiên Long TL-08', 'images/but.jpg', 'Thiên Long',
          'Bút viết trơn mượt, mực đều màu.', '2023-12-01', '2024-03-01', '2024-02-28',
          100, 100, 3000, 5000, 0, 6
      ),
      (
          'S002', 'Stationery', 'Tập Campus A4', 'images/tap-campus.jpg', 'Campus',
          'Tập giấy trắng mịn, phù hợp cho học sinh sinh viên.', '2024-01-15', '2024-04-01', '2024-03-15',
          200, 200, 8000, 12000, 0, 5
      );
INSERT INTO product (
    id, type, name, image, distributor, description, pub_date, sell_date, added_date,
    quantity, remain, import_price, sell_price, sell_quantity, age_restrict
) VALUES
      (
          'T001', 'Toy', 'Rubik 3x3 Moyu', 'images/rubik.jpg', 'Moyu Toys',
          'Khối rubik cao cấp cho người chơi chuyên nghiệp.', '2023-11-01', '2024-03-20', '2024-03-19',
          60, 60, 20000, 45000, 0, 8
      ),
      (
          'T002', 'Toy', 'Gấu bông Totoro', 'images/totoro.jpg', 'Studio Ghibli',
          'Gấu bông Totoro đáng yêu, chất liệu mềm mại.', '2023-10-10', '2024-04-03', '2024-04-01',
          40, 40, 60000, 99000, 0, 3
      );