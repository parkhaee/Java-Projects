CREATE TABLE toys (
id INT NOT NULL Primary Key AUTO_INCREMENT, 
name VARCHAR(20), 
price DOUBLE, 
quantity INT);

INSERT INTO toys (name, price, quantity) 
VALUES 
('Woody', 36.99, 200),
('Buzz Lightyear', 52.99, 150),
('Teddy Bear', 15.00, 300),
('Lego Architecture', 39.99, 100),
('Jessie', 36.50, 100),
('Barbie', 50.00, 100),
('King Kong', 50.50, 50),
('Moomin', 9.99, 200),
('Darth Vader', 72.99, 20),
('Lightsaber', 65.50, 20);