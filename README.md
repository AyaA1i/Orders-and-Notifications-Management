# Orders and Notifications Management API
## Overview 
This project focuses on creating "Orders and Notifications Management" module for e-commerce system. The system provides essential features, including product listing, customer account management, order placement, shipping, and notification handling.

## Features
1. Store Products Listing
2. Customer Account Management
3. Order Placement
4. Order Cancellation
5. Order Details
6. Notifications Handling

## API Endpoints
1. Sign Up
2. Log in
3. View Catalog
4. Make simple order
5. Make compound order
6. Show Customer Notifications
7. Cancel Order


## UML System Class Diagram
[UML Class Diagram](https://drive.google.com/file/d/1lRUlbZHQLUFWefaV4nSO5YPS8Tra5vuX/view?usp=sharing)

## Endpoints:
| Exposed API | Description | Input |
| ----------- | ----------- | ----- |
| POST ```/createAccount``` |Creates a new account for that customer adds it to customer repo, and returns confirmation message| Account object |
| GET ```/product/list``` | Returns all the products that are currently in stock | NONE |
| GET ```/product/{categoryName}``` |  A count of the remaining parts from each category should be available.| NONE |
| DELETE ```/product/remove/{serialNumber}``` | remove product from the repo | NONE |
| POST ```/product/add``` |create product and add it to the repo| product object |
| POST ```/order/placeSimpleOrder``` | Create a new simple order add it to the orders repo complete payment and send a notification to the customer | Simple Order object |
| POST ```/order/placeCompoundOrder{email}``` | Create a new compound order add it to the orders repo complete payment for each customer and send a notification to each customer | Compound Order object |
| GET ```/order/listOrders``` | List all details of the order whether it's simple or compound | NONE |
| DELETE ```/order/cancelOrder/{id}``` | Check if the order can be canceled if it is it changes its status to canceled and return the money to the customers and return the products to the products repo | NONE |
| DELETE ```/order/cancelOrderShipping/{id}``` | Check if the order can be canceled if it is it changes its status to canceled it's shipping and returns the money to the customers | NONE |
| GET ```/mostUsedTemplate``` | Returns a string with the templates' statistics, sorted by their frequencies, representing the number of times each template has been sent. | NONE |
| GET ```/mostNotified``` | Returns a string that has the statistics for the most notified email addresses and phone numbers | NONE |
| GET ```/notifications`` | Returns a the current notifications in the queue | NONE |



## Contributers
Orders and Notifications API project is developed with contributions from the following team members working with Agile SDLC method:
- [Aya Ali](https://github.com/AyaA1i)
- [Maya Ayman](https://github.com/MayaZayn)
- [Basmala Gad](https://github.com/BasmalahGad)
- [Sara Tamer](https://github.com/SaraTamer)

