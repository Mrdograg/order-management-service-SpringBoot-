# 🍕 Order Management Service (Spring Boot)

A **Spring Boot microservice** for managing food orders.  
It provides REST APIs to **place, track, and update orders** with **MySQL persistence** and **AWS SQS integration** for asynchronous processing.  

---

## ✨ Features  
- ✅ Place and retrieve customer food orders  
- ✅ Paginated order listing with sorting  
- ✅ Update order status (`PLACED`, `PROCESSED`, `DELIVERED`)  
- ✅ Publish order events to **AWS SQS** for downstream services (payment, delivery, etc.)  
- ✅ Built with **clean layered architecture**: Controller → Service → Repository → Entity → DTO  

---

## 🛠 Tech Stack  

| Layer / Purpose   | Technology |
|-------------------|------------|
| **Language**      | Java 17 |
| **Framework**     | Spring Boot (Web, Data JPA, Validation) |
| **Database**      | MySQL |
| **Cloud Service** | AWS SQS (Simple Queue Service) |
| **Build Tool**    | Maven |
| **IDE**           | IntelliJ IDEA |

---

## ⚙️ Setup  

### Prerequisites  
- Java 17+  
- Maven  
- MySQL  
- AWS account with an SQS queue  

### Local Setup  

1. **Create MySQL Database**  
   ```sql
   CREATE DATABASE foodorders;

2. **Configure application.properties**  
- spring.datasource.url=jdbc:mysql://127.0.0.1:3306/foodorders
- spring.datasource.username="username"
- spring.datasource.password="password"
- sqs.queue.url=https://sqs.ap-south-1.amazonaws.com/1234567890/food-orders-queue
- aws.region=ap-south-1
- aws.accessKeyId=${AWS_ACCESS_KEY_ID}
- aws.secretAccessKey=${AWS_SECRET_ACCESS_KEY}

3. **Set AWS Credentials (Environment Variables)** 
export AWS_ACCESS_KEY_ID=your_key
export AWS_SECRET_ACCESS_KEY=your_secret

4. **Build and Run** 
mvn clean install
mvn spring-boot:run


## 🛠 API ENDPOINTS
- POST `/api/v1/orders` (body: CreateOrderRequest)
- GET `/api/v1/orders?page=0&size=10&sort=orderTime,desc`
- GET `/api/v1/orders/{id}`
- GET `/api/v1/orders/{id}/status`
- PATCH `/api/v1/orders/{id}/status?status=PROCESSED`

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





