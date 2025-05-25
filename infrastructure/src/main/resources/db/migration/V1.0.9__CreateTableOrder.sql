CREATE TABLE Orders (
    id INT AUTO_INCREMENT PRIMARY KEY,
    restaurantId INT NOT NULL,
    customerId INT NOT NULL,
    deliveryPersonId INT,
    totalPrice DOUBLE NOT NULL,
    orderStatus VARCHAR(50)
);