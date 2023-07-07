﻿# tdd-project

How to run the program
1. Clone the repository
2. Navigate to the project directory
3. Build and run the program
4. Navigate to http://localhost:8080/orders

How to test the program using POSTMAN:
Test the program by using POSTMAN. Run the http requests for GET/POST/PUT/DELETE to view create, view, update, and delete orders.

How to test and utilize H2 Database:
1. Run the program
2. Navigate to http://localhost:8080/h2-console
3. Log in using URL, username, and password found in application properites
4. Create orders table

  CREATE TABLE orders (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  customer_name VARCHAR(255) NOT NULL,
  order_date DATE,
  shipping_address VARCHAR(255),
  total DECIMAL(10, 2)
);

5. Run SQL statementes to insert, retrieve, update, and delete orders
6. View http://localhost:8080/orders to see the changes as well

