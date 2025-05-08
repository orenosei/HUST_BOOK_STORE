USE `bookstore`;

CREATE TABLE `admin` (
                         `username` varchar(255) NOT NULL UNIQUE,
                         `password` varchar(255) NOT NULL,
                         `question` varchar(225) NOT NULL,
                         `answer` varchar(225) NOT NULL,
                         `admin_id` INT AUTO_INCREMENT,
                         `name` varchar(255) DEFAULT NULL,
                         `phonenumber` varchar(255) DEFAULT NULL,
                         `email` varchar(255) DEFAULT NULL,
                         PRIMARY KEY (`admin_id`)
);

CREATE TABLE `user` (
                        `username` varchar(255) NOT NULL UNIQUE,
                        `password` varchar(255) NOT NULL,
                        `question` varchar(225) NOT NULL,
                        `answer` varchar(225) NOT NULL,
                        `user_id` INT AUTO_INCREMENT,
                        `name` varchar(255) DEFAULT NULL,
                        `phonenumber` varchar(255) DEFAULT NULL,
                        `email` varchar(255) DEFAULT NULL,
                        `address` varchar(255) DEFAULT NULL,
                        PRIMARY KEY (`user_id`)
);

CREATE TABLE `product` (
                           `product_id` varchar(255) NOT NULL,
                           `type` varchar(20) NOT NULL,
                           `name` varchar(255) NOT NULL,
                           `image` varchar(500) DEFAULT NULL,
                           `distributor` varchar(255) NOT NULL,
                           `description` text,
                           `added_date` date DEFAULT NULL,
                           `stock` int NOT NULL DEFAULT '0',
                           `import_price` float NOT NULL,
                           `sell_price` float NOT NULL,
                           `age_restrict` int DEFAULT '0',
                           `isbn` varchar(255) DEFAULT NULL,
                           `author` varchar(255) DEFAULT NULL,
                           `genre` varchar(255) DEFAULT NULL,
                           `pub_date` date DEFAULT NULL,
                           PRIMARY KEY (`product_id`)
);

CREATE TABLE `voucher` (
                        `code` varchar(255) NOT NULL UNIQUE ,
                        `discount` float NOT NULL,
                        `duration` DATE NOT NULL,
                        `remaining` int NOT NULL,
                        `voucher_id` INT AUTO_INCREMENT,
                        PRIMARY KEY (`voucher_id`)
);

CREATE TABLE `cart` (
                        `cart_id` INT AUTO_INCREMENT PRIMARY KEY, -- Khóa chính cho bảng cart
                        `user_id` INT NOT NULL,                   -- Khóa ngoại tham chiếu đến bảng user
                        `total_cost` FLOAT NOT NULL,              -- Tổng chi phí giỏ hàng
                        `final_price` FLOAT NOT NULL,             -- Giá cuối cùng sau khi giảm giá
                        FOREIGN KEY (`user_id`) REFERENCES `user`(`user_id`) -- Khóa ngoại
                            ON DELETE CASCADE                     -- Xóa giỏ hàng khi xóa user
                            ON UPDATE CASCADE                     -- Cập nhật user_id nếu thay đổi
);

CREATE TABLE `cart_item` (
                             `cart_item_id` INT AUTO_INCREMENT PRIMARY KEY, -- ID duy nhất cho từng mục trong giỏ hàng
                             `cart_id` INT NOT NULL,                        -- Tham chiếu đến giỏ hàng
                             `product_id` VARCHAR(255) NOT NULL,            -- Tham chiếu đến sản phẩm
                             `quantity` INT NOT NULL,                       -- Số lượng sản phẩm trong giỏ
                             FOREIGN KEY (`cart_id`) REFERENCES `cart`(`cart_id`)
                                 ON DELETE CASCADE ON UPDATE CASCADE,
                             FOREIGN KEY (`product_id`) REFERENCES `product`(`product_id`)
                                 ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE `bill` (
                        `bill_id` INT AUTO_INCREMENT PRIMARY KEY, -- ID duy nhất cho hóa đơn
                        `user_id` INT NOT NULL,                   -- Tham chiếu đến người dùng
                        `total_price` FLOAT NOT NULL,             -- Tổng giá trị hóa đơn
                        `purchase_date` DATE NOT NULL,            -- Ngày mua hàng
                        FOREIGN KEY (`user_id`) REFERENCES `user`(`user_id`)
                            ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE `bill_item` (
                             `bill_item_id` INT AUTO_INCREMENT PRIMARY KEY, -- ID duy nhất cho từng mục hóa đơn
                             `bill_id` INT NOT NULL,                        -- Tham chiếu đến hóa đơn
                             `product_id` VARCHAR(255) NOT NULL,            -- Tham chiếu đến sản phẩm
                             `quantity` INT NOT NULL,                       -- Số lượng sản phẩm đã mua
                             `price_at_purchase` FLOAT NOT NULL,            -- Giá sản phẩm tại thời điểm mua
                             FOREIGN KEY (`bill_id`) REFERENCES `bill`(`bill_id`)
                                 ON DELETE CASCADE ON UPDATE CASCADE,
                             FOREIGN KEY (`product_id`) REFERENCES `product`(`product_id`)
                                 ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE `bill_voucher` (
                                `bill_voucher_id` INT AUTO_INCREMENT PRIMARY KEY, -- ID duy nhất cho mục voucher
                                `bill_id` INT NOT NULL,                           -- Tham chiếu đến hóa đơn
                                `voucher_id` INT NOT NULL,                        -- Tham chiếu đến voucher
                                FOREIGN KEY (`bill_id`) REFERENCES `bill`(`bill_id`)
                                    ON DELETE CASCADE ON UPDATE CASCADE,
                                FOREIGN KEY (`voucher_id`) REFERENCES `voucher`(`voucher_id`)
                                    ON DELETE CASCADE ON UPDATE CASCADE
);