CREATE TABLE ProductAddings (
    productId INT NOT NULL,
    addingId INT NOT NULL,
    PRIMARY KEY (productId, addingId),
    FOREIGN KEY (productId) REFERENCES Product(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    FOREIGN KEY (addingId) REFERENCES Adding(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);
