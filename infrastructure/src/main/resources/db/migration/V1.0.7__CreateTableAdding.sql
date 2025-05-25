CREATE TABLE Adding (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    restaurantId INT NOT NULL,
    description VARCHAR(500),
    price DOUBLE NOT NULL,
    image VARCHAR(500),
    FOREIGN KEY (restaurantId) REFERENCES Restaurant(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);
