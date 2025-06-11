CREATE TABLE Customer (
    id INT PRIMARY KEY,
    street VARCHAR(255),
    number VARCHAR(50),
    floor VARCHAR(50),
    latitude DOUBLE,
    longitude DOUBLE,
    CONSTRAINT FK_Customer_User FOREIGN KEY (id) REFERENCES `User`(id)
);
