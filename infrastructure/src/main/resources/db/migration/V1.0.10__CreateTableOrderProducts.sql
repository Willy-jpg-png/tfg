CREATE TABLE OrderProducts (
    id INT AUTO_INCREMENT PRIMARY KEY,
    productId INT NOT NULL,
    orderId INT NOT NULL,
    quantity INT NOT NULL,
    FOREIGN KEY (productId) REFERENCES Product(id)
       ON DELETE CASCADE
       ON UPDATE CASCADE,
    FOREIGN KEY (orderId) REFERENCES Orders(id)
       ON DELETE CASCADE
       ON UPDATE CASCADE
);
