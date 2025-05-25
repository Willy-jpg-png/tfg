CREATE TABLE Customer (
  id INT PRIMARY KEY,
  address VARCHAR(255),
  CONSTRAINT FK_Customer_User FOREIGN KEY (id) REFERENCES `User`(id)
);
