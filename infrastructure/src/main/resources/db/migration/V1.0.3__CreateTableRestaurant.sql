CREATE TABLE Restaurant (
    id INT PRIMARY KEY,
    address VARCHAR(255),
    description VARCHAR(500),
    phone VARCHAR(50),
    website VARCHAR(255),
    CONSTRAINT FK_Restaurant_User FOREIGN KEY (id) REFERENCES `User`(id)
);
