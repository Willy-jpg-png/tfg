CREATE TABLE Restaurant (
    id INT PRIMARY KEY,
    street VARCHAR(255),
    number VARCHAR(50),
    floor VARCHAR(50),
    latitude DOUBLE,
    longitude DOUBLE,
    description VARCHAR(500),
    phone VARCHAR(50),
    website VARCHAR(255),
    CONSTRAINT FK_Restaurant_User FOREIGN KEY (id) REFERENCES `User`(id)
);
