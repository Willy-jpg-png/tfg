CREATE TABLE DeliveryPerson (
    id INT PRIMARY KEY,
    photo VARCHAR(500),
    CONSTRAINT FK_DeliveryPerson_User FOREIGN KEY (id) REFERENCES `User`(id)
);
