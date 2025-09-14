# order-management-service-SpringBoot-


🍕 Food Orders Service
📖 Description

This project is a Spring Boot microservice for managing food orders. It provides REST APIs to place, track, and update food orders, while also integrating with AWS SQS (Simple Queue Service) for asynchronous order processing.

The main features include:

Creating and retrieving customer food orders

Paginated order listing with filters (page, size, sort)

Updating order status (e.g., PLACED, PROCESSED, DELIVERED)

Sending order events to an AWS SQS queue for further processing (e.g., payment service, delivery service)

Using MySQL for data persistence with JPA/Hibernate

Following clean layered architecture with Controllers, Services, DTOs, and Configs

This project was built as a backend service for a food delivery system and demonstrates real-world enterprise practices:

Environment variables for secrets (AWS credentials)

Database migrations with Flyway (optional)

Modular and testable Spring Boot structure

⚙️ Tech Stack

Java 17

Spring Boot (Web, Data JPA, Validation)

MySQL

AWS SQS

Flyway (DB migrations – optional)

Maven

IntelliJ IDEA

🚀 Setup
Prerequisites

Java 17+

Maven

MySQL

AWS account with SQS queue

Local Setup

Create MySQL DB

CREATE DATABASE foodorders;


Configure Database & AWS in application.properties

spring.datasource.url=jdbc:mysql://127.0.0.1:3306/foodorders
spring.datasource.username=root
spring.datasource.password=password
sqs.queue.url=https://sqs.ap-south-1.amazonaws.com/1234567890/food-orders-queue
aws.region=ap-south-1
aws.accessKeyId=${AWS_ACCESS_KEY_ID}
aws.secretAccessKey=${AWS_SECRET_ACCESS_KEY}


Set AWS creds as env vars (do not hardcode!)

export AWS_ACCESS_KEY_ID=your_key
export AWS_SECRET_ACCESS_KEY=your_secret


Build & Run

mvn clean install
mvn spring-boot:run

📡 API Endpoints
## API Endpoints
- POST `/api/v1/orders` (body: CreateOrderRequest)
- GET `/api/v1/orders?page=0&size=10&sort=orderTime,desc`
- GET `/api/v1/orders/{id}`
- GET `/api/v1/orders/{id}/status`
- PATCH `/api/v1/orders/{id}/status?status=PROCESSED`
- 

### Sample POST payload
```json
## POST
{
  "customerName": "Amit",
  "items": [
    {
      "itemName": "Pizza",
      "quantity": 1,
      "unitPrice": 250.00
    },
    {
      "itemName": "Pasta",
      "quantity": 2,
      "unitPrice": 150.00
    }
  ],
  "totalAmount": 550.00,
  "orderTime": "2025-09-12T12:00:00+05:30"
}

