CREATE TABLE OrderProducts (
    productId INT NOT NULL,
    orderId INT NOT NULL,
    PRIMARY KEY (productId, orderId),
    FOREIGN KEY (productId) REFERENCES Product(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    FOREIGN KEY (orderId) REFERENCES Orders(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);
