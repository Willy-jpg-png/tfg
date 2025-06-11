CREATE TABLE OrderProductAddings (
    orderProductId INT NOT NULL,
    addingId INT NOT NULL,
    PRIMARY KEY (orderProductId, addingId),
    FOREIGN KEY (orderProductId) REFERENCES OrderProducts(id)
     ON DELETE CASCADE
     ON UPDATE CASCADE,
    FOREIGN KEY (addingId) REFERENCES Adding(id)
     ON DELETE CASCADE
     ON UPDATE CASCADE
);
