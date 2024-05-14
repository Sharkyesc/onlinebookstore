CREATE TABLE books (
    id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255),
    price DOUBLE,
    author VARCHAR(100),
    press VARCHAR(100),
    pub_time DATE,
    stocks INT,
    description TEXT
);

CREATE TABLE Users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(50) NOT NULL,
    email VARCHAR(100),
    address VARCHAR(255),
    phone VARCHAR(20)
);

CREATE TABLE Orders (
    order_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    FOREIGN KEY (user_id) REFERENCES Users(user_id),
    order_time DATETIME,
    book_id INT,
    FOREIGN KEY (book_id) REFERENCES Books(book_id),
    quantity INT,
    total_price DECIMAL(10, 2),
    shipping_address VARCHAR(255)
);


CREATE TABLE `orders` (
  `orderID` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `userID` INT UNSIGNED NOT NULL,
  `orderTime` DATETIME NOT NULL,
  `totalPrice` DECIMAL(10, 2) NOT NULL,
  PRIMARY KEY (`orderID`),
  FOREIGN KEY (`userID`) REFERENCES `users` (`userID`)
);


CREATE TABLE `orderitems` (
  `itemID` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `orderID` INT UNSIGNED NOT NULL,
  `bookID` INT UNSIGNED NOT NULL,
  `quantity` INT UNSIGNED NOT NULL,
  `price` DECIMAL(10, 2) NOT NULL,
  PRIMARY KEY (`itemID`),
  FOREIGN KEY (`orderID`) REFERENCES `orders` (`orderID`),
  FOREIGN KEY (`bookID`) REFERENCES `books` (`bookID`)
);
