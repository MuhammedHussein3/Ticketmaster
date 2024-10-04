# Ticketmaster System

Ticketmaster is a microservices-based system designed to handle ticket booking, event management, and user notifications with QR code functionality. It provides an efficient way to book tickets for events, manage venues, and send booking confirmations to users via email.
### Low-Level Design Diagram
![Ticket Booking Architecture](https://github.com/MuhammedHussein3/Ticketmaster/blob/master/diagrams/Ticket%20Booking%20Architecture.png)
---

## Table of Contents

1. [Introduction](#introduction)
2. [Functional Requirements](#functional-requirements)
3. [Non-Functional Requirements](#non-functional-requirements)
4. [Activity Diagram](#Activity-Diagram)
5. [Sequence Diagram](#Sequence-Diagram)
6. [Core Entities](#core-entities)
7. [API Overview](#api-overview)
8. [Deep Dives](#Deep-Dives)
9. [Technologies Used](#technologies-used)
10. [Installation](#installation)

---

## Introduction

The **Ticketmaster System** is a robust microservice architecture built to handle the end-to-end ticket booking process. It allows users to:

- Browse events and book tickets.
- Receive tickets in QR code form for seamless event check-in.
- Get booking confirmation and event details via email notifications.

The system follows best practices in clean code, SOLID principles, and utilizes Spring Boot, Kotlin, Java, Docker, and other advanced technologies to ensure scalability and performance.

---
## Functional Requirements

1. Users should be able to view events.

   - Users will have access to all events available on the platform, including details like event name, date, time, venue, ticket availability, and pricing.

2. Users should be able to search for events.

   - Users can search for events by multiple criteria such as event name, venue, date, category, or location.

3. Users should be able to book tickets to events.

   - Users can select seats and proceed to book tickets for an event. This includes confirming payment and receiving ticket details.

4. sers should receive booking confirmation emails with QR codes.

   - Upon successful ticket booking, users will receive a confirmation email, including details about the booking and a QR code for ticket validation at the event.

6. Users should be able to cancel or modify their bookings.

    - Users can cancel or update their bookings if the event's cancellation or modification policy allows it. The system will handle refunds and notifications accordingly.

7. The system should notify users about event updates or cancellations.

   - In case of event cancellations, postponements, or changes, users should receive notifications via email or SMS.

8. Users should be able to make payments securely.
 
   - Users should be able to complete payments using various methods like PayPal, with secure payment gateways integrated.

---

## Non-Functional Requirements

1. **Scalability**

    - The system should handle high volumes of users, especially during peak times (e.g., for popular events or sales promotions). Horizontal scaling via microservices architecture ensures the system can meet demand.
  
4. **Availability**

     - The system should prioritize availability. For example, users must always be able to access and browse events even if some backend services (like notifications) face temporary issues. Ticket sales should also prioritize availability to ensure users do not face interruptions during booking

5. **Consistency**

    - Payment and ticket reservations should be consistent. Once a user books a ticket, the system should ensure that the booking is reflected in the event’s ticket availability in real time. CAP Theorem suggests that booking and payment services should prioritize consistency while browsing services prioritize availability.

6. **Performance**

    - The system should load pages and handle requests with low latency, ensuring fast response times for user interactions, including searching for events or processing payments.

7. **Fault Tolerance**

    - The system should recover gracefully from failures using Resilience4j for circuit breaking and retries. If an external service fails (like a payment provider or messaging service), the system should have fallback mechanisms.

8. **Rate Limiting and Throttling**

   - Redis rate limiting should ensure that the system can handle bursts of requests without overwhelming the services, especially during high-traffic periods like the release of tickets for popular events.

9. **Read vs Write Ratio**

   - The system is read-heavy since users will frequently browse and search for events, but there will also be occasional write-heavy operations like bookings, cancellations, and updates. Optimizations like caching can help reduce the load for read-heavy operations, while proper indexing in databases can handle write operations effectively.

10. **Caching**

   - Frequently accessed data, like event details and search results, should be cached using Redis to enhance performance. Cached data should have appropriate expiration policies to ensure that users always see accurate information about ticket availability.
     
11. **Monitoring and Observability**

   - Use Zipkin for distributed tracing, Prometheus for monitoring service metrics, and Grafana for visualizing real-time performance statistics.

---

## Activity Diagram 

This diagram shows the flow of processes from one to another activity.
![Activity Diagram](https://github.com/MuhammedHussein3/Ticketmaster/blob/master/diagrams/Activity%20Diagram.png)

---

## Sequence Diagram

### Detailed Points for the Sequence Diagram

1. **Search Events:**
   - **Step 1**: User initiates a search for events through the API Gateway.
   - **Step 2**: The API Gateway routes the request to the **Search Service**.
   - **Step 3**: The **Search Service** first checks the cache (e.g., Redis) for any pre-existing results.
   - **Step 4**: If the results are not found in the cache (cache miss), the **Search Service** queries the primary database (e.g., PostgreSQL).
   - **Step 5**: Results from the database are returned to the **Search Service**.
   - **Step 6**: The results are then cached and returned to the user via the API Gateway.

2. **View Event Details:**
   - **Step 1**: User selects an event to view its details via the API Gateway.
   - **Step 2**: The request is routed to the **Event Service**.
   - **Step 3**: The **Event Service** queries the database for detailed event information.
   - **Step 4**: The details are then returned to the user via the API Gateway.

3. **Book Tickets:**
   - **Step 1**: User books tickets for an event through the API Gateway.
   - **Step 2**: The API Gateway forwards the request to the **Ticket Service**.
   - **Step 3**: The **Ticket Service** creates the necessary ticket entries in the database.
   - **Step 4**: A booking confirmation is sent back to the user via the API Gateway.

4. **Reserve Tickets:**
   - **Step 1**: User requests to reserve tickets through the API Gateway.
   - **Step 2**: The request is forwarded to the **Booking Service**.
   - **Step 3**: The **Booking Service** communicates with the **Ticket Service** to check for available tickets by querying the database.
   - **Step 4**: If tickets are available, the **Booking Service** creates a reservation.
   - **Step 5**: The **Notification Service** is called to send a reservation confirmation email.
   - **Step 6**: The confirmation email is sent via the **Email Service**, which generates and sends a QR code with the ticket details to the user.

5. **Send Notification:**
   - **Step 1**: After a booking or reservation is confirmed, the **Notification Service** triggers an email with ticket details.
   - **Step 2**: The **Notification Service** uses the **Email Service** to generate and send the email.
   - **Step 3**: The email, including a QR code of the booked ticket, is sent to the user.

### Sequence Diagram Visualization

![Sequence Diagram](https://github.com/MuhammedHussein3/Ticketmaster/blob/master/diagrams/Sequence%20Diagram.png)

---

## Core Entities

# Ticket Booking ERD Diagram
![ERD](https://github.com/MuhammedHussein3/Ticketmaster/blob/master/diagrams/Ticket%20Booking%20ERD.png)

The **Ticketmaster System** contains several core entities:

1. **User**: Represents a registered user in the system.
   - `userId`
   - `firstName`, `lastName`
   - `email`
   - `password`

2. **Event**: Represents an event that users can book tickets for.
   - `eventId`
   - `eventName`
   - `eventDate`, `venueName`, `venueLocation`
   - `category`

3. **Booking**: Stores information related to a user booking an event.
   - `bookingId`
   - `userId`
   - `eventId`
   - `bookingTime`

4. **Ticket**: Represents the tickets booked by the user for a specific event.
   - `ticketId`
   - `seatId`
   - `reservedId`
   - `userId`

---

## API Overview

### Search APIs
- **GET /api/v1/search**: Retrieve a list of events based on search criteria such as keywords, dates, and categories.
- **GET /api/v1/search/getAll**: Fetch all events available in the system, optionally supporting pagination.

### Event Management APIs
- **POST /api/v1/events**: Create a new event by providing details such as title, description, date, time, and venue.
- **PUT /api/v1/events/{event-id}**: Update the details of an existing event identified by `event-id`.
- **DELETE /api/v1/events/{event-id}**: Remove an event from the system using its `event-id`.
- **GET /api/v1/events/{event-id}**: Retrieve detailed information about a specific event using its `event-id`.
- **POST /api/v1/venues**: Add a new venue with details such as name, address, and capacity.
- **GET /api/v1/venues/{venue-id}**: Get details of a specific venue using its `venue-id`.
- **GET /api/v1/venues**: List all venues available in the system.
- **PUT /api/v1/venues/{venue-id}**: Update information for an existing venue specified by `venue-id`.
- **POST /api/v1/categories**: Create a new category for events (e.g., music, sports).
- **GET /api/v1/categories/{category-id}**: Fetch details of a specific category using its `category-id`.
- **DELETE /api/v1/categories/{category-id}**: Remove a category from the system using its `category-id`.
- **PUT /api/v1/categories/{category-id}**: Update an existing category identified by `category-id`.

### Ticket Management APIs
- **POST /api/v1/tickets/event/{event-id}/create**: Create tickets for a specific event identified by `event-id`.
- **PUT /api/v1/tickets/update-seats**: Update the seating arrangement for an event.
- **GET /api/v1/tickets/available-seats**: Retrieve a list of available seats for a specific event.
- **GET /api/v1/tickets/event/{event-id}**: Get a list of tickets for a specific event identified by `event-id`.
- **GET /api/v1/tickets/user/{user-id}**: Fetch all tickets booked by a specific user identified by `user-id`.

### Booking APIs
- **POST /api/v1/bookings/reserve**: Book tickets for an event by providing event and user details.
- **PUT /api/v1/bookings/confirm/{reservation-id}**: Confirm booking details for a user using a specific reservation identified by `reservation-id`.

### Notification APIs
- **POST /notifications/send**: Send an email with ticket details and QR codes to the user after booking is confirmed. 

Full API documentation is available in the integrated **Swagger** documentation.

---

## Deep Dives

### 1. Introducing Elasticsearch for Search Performance

- **Elasticsearch**: A distributed search and analytics engine that provides powerful full-text search capabilities. It is ideal for scenarios requiring quick searches due to its ability to hold an inverted index of terms that map to actual keys (e.g., TicketId) in the primary database.

- **Inverted Indexing**: Store an inverted index of search terms (event names, locations, etc.) mapped to the corresponding TicketIds in Elasticsearch. This allows for efficient searching, as queries can be resolved quickly without hitting the primary database.

- **Data Storage**: Depending on the weight of the search results, you may decide to keep relevant event properties in Elasticsearch, allowing for ultra-fast search results. However, it’s crucial to note that Elasticsearch should not serve as the primary database.

- **Update Mechanism**: To ensure Elasticsearch remains in sync with your primary database, you have two primary options:

  1. **My Implementation**
     - After an update to the primary database, the corresponding event should also be updated in Elasticsearch.
         
     - **Pros**: Simple to implement; straightforward logic for handling updates.
     
     - **Cons**: Increases code complexity, especially around failure handling and ensuring consistency between the two databases.
     
  2. **Change Data Capture (CDC)**
     - Use tools like Kafka Connect to capture changes from the primary database and stream them to Elasticsearch.
         
     - **Pros**: Cleaner architecture; eliminates the need for direct updates in your application logic; provides reliable and real-time updates to Elasticsearch.
     
     - **Cons**: Higher costs associated with setting up and maintaining a Kafka cluster, especially if write operations are infrequent.

- **Custom Redis Cache**: Implement a Redis cache to store frequently accessed data. This can improve read performance by reducing the load on the primary database. Redis can be configured to automatically invalidate cache entries when updates occur in the primary database.

### 2. Ticket Service Architecture

- **Ticket Management**: The Ticket Service is responsible for managing all operations related to tickets, including creation, updates, availability checks, and retrievals. This service interacts closely with the Event Management and Booking APIs to ensure tickets are correctly associated with events.

- **Endpoints**:
  - **POST /api/v1/tickets/event/{event-id}/create**: Create tickets for a specified event. This endpoint should ensure that the number of tickets created does not exceed the event capacity.
  - **PUT /api/v1/tickets/update-seats**: Update the seat arrangement for an event, ensuring that available seats are accurately reflected.
  - **GET /api/v1/tickets/available-seats**: Retrieve a list of available seats for a specific event. This can utilize caching strategies to speed up responses.
  - **GET /api/v1/tickets/event/{event-id}**: Retrieve ticket details for a specific event, including pricing and availability.
  - **GET /api/v1/tickets/user/{user-id}**: Retrieve all tickets associated with a user.

### 3. Caching Strategy

- **Caching Layer**: Implement a caching layer using Redis or a cloud-based caching solution to enhance the performance of read-heavy operations, especially for ticket availability checks.

- **Cache Invalidation**: When tickets are updated or booked, the cache should be invalidated to ensure users receive the most up-to-date information.

- **CDN for API Requests**: Use a Content Delivery Network (CDN) to cache API responses for static or less frequently changing data, reducing the load on the backend and improving response times for users.

### 4. Booking System Reliability

- **Consistency**: Implement distributed transactions or eventual consistency models to ensure that ticket bookings are reliable and free of double bookings. This can be achieved through optimistic locking or using message queues to handle the booking process asynchronously.

- **Fault Tolerance**: Design the booking system to be fault-tolerant, allowing for retries and graceful degradation in case of service failures. Implement fallback mechanisms in the event of downtime or errors in the ticket booking process.

### 5. Notification System

- **Email Notifications**: Utilize a robust email service to send booking confirmations, ticket details, and QR codes to users. Implement templating systems (e.g., Thymeleaf) to create professional email formats.

- **Real-time Notifications**: Consider integrating a real-time notification service (e.g., WebSockets) to provide instant updates to users about their bookings and events, enhancing user engagement.

### 6. Scalability Considerations

- **Horizontal Scaling**: Design the Ticket Service to support horizontal scaling, allowing for the addition of more servers or instances as user demand increases.

- **Load Balancing**: Use load balancers to distribute incoming requests evenly across multiple instances, ensuring optimal performance during peak usage.

### 7. Security Measures

- **Authentication and Authorization**: Implement JWT-based authentication to secure API endpoints and protect user data. Ensure that sensitive operations (like ticket booking) are only accessible to authenticated users.

- **Rate Limiting**: Implement rate limiting to prevent abuse of APIs, especially during high traffic events, protecting the system from denial-of-service attacks.

## Approach: Implementing a Distributed Lock with Redis for Ticket Booking
To avoid race conditions in a high-concurrency environment, a **distributed lock** with **TTL (Time To Live)** using **Redis** can be implemented during the ticket booking process. This ensures that tickets are reserved and released in a controlled manner.

### How it Works:
1. **Lock Acquisition**:
    - When a user selects a ticket, a lock is acquired in **Redis** using the ticket ID as the unique identifier, with a predefined TTL.
    - This TTL acts as an automatic expiration time, ensuring the lock is released if the user doesn't complete the transaction in time.

2. **Completing the Purchase**:
    - If the user successfully completes the purchase, the **Ticket Service** updates the ticket status in the **PostgreSQL** database to "Booked".
    - The lock in **Redis** is manually released by the application after the TTL.

3. **Lock Expiry**:
    - If the user does not complete the purchase within the TTL, **Redis** automatically releases the lock.
    - The ticket becomes available again for booking by other users without additional intervention.

4. **Simplifying the Ticket Table**:
    - The **Ticket Table** now only has two states: **Available** and **Booked**.
    - Locking of reserved tickets is handled entirely by **Redis**.
    - Redis stores the ticket ID as the key and the user ID as the value, ensuring that when a user confirms the booking, they are the one who reserved the ticket.

### Challenges:
1. **Handling Failures**:
    - If the Redis lock system goes down, user experience could be temporarily degraded.
    - However, **Optimistic Concurrency Control (OCC)** or similar mechanisms in the **PostgreSQL** database ensure that there is no double booking of tickets.
    - The downside is that users may receive an error after entering payment details if someone else books the ticket faster.
    - Despite the potential for temporary issues, this approach is more reliable than having tickets locked indefinitely in case of system failures.

## Conclusion
These deep dives outline the strategies and technologies employed in the Ticket Booking System, specifically focusing on the Ticket Service, to ensure high performance, reliability, and user satisfaction. Each component plays a crucial role in achieving the overall goals of the system, facilitating a seamless ticket booking experience.

---

## Technologies Used

### Backend
- **Java 21**: The core language for backend services, providing high performance and robust libraries.
- **Kotlin**: Used alongside Java for building concise and expressive code, especially in microservices.
- **Spring Boot 3**: The framework that powers microservices, providing built-in support for RESTful APIs, dependency injection, and various essential features.

### Security
- **Spring Security 6**: Manages authentication and authorization, ensuring secure access to APIs and services.
- **JWT (JSON Web Tokens)**: Token-based authentication mechanism for stateless communication, particularly useful in distributed systems.

### Messaging
- **Kafka**: Distributed streaming platform used for building real-time data pipelines and applications. Ideal for high-throughput, low-latency messaging.

### Databases
- **PostgreSQL**: Relational database used to store structured data such as event information, ticket bookings, and users.
- **MongoDB**: NoSQL database that handles semi-structured or unstructured data like user preferences and logs.
  
### Caching
- **Redis**: In-memory data structure store used for caching frequently accessed data to improve performance.

### Search & Indexing
- **Elasticsearch**: Provides a distributed search and analytics engine for high-speed querying of events and tickets. Stores an inverted index for faster searching.

### API Documentation
- **Swagger**: Automatically generated API documentation that helps developers understand and test the API endpoints.

### Email Service
- **JavaMailSender**: Facilitates sending emails for user confirmations, notifications, and alerts.
- **Thymeleaf**: Template engine for generating dynamic emails with HTML templates, making it easy to send visually appealing notifications.

### CI/CD
- **Docker**: Containerization platform used to package microservices and deploy them consistently across environments.
- **Heroku**: Cloud platform used to host the microservices, allowing for scalable and easily managed deployments.

### Monitoring & Tracing
- **Zipkin**: Distributed tracing system that helps monitor and troubleshoot latency issues by tracking the flow of requests across microservices.

### Real-time Data Integration
- **Debezium**: Used for **Change Data Capture (CDC)** to monitor and capture database changes (in PostgreSQL) and sync them with other services in real time.

### Deployment & Orchestration
- **Docker Compose**: Used to define and manage multiple services (like Kafka, PostgreSQL, Redis, Elasticsearch) that make up the TicketMaster system.
- **Kubernetes (Optional)**: Can be used for orchestrating containerized applications for more advanced deployments with auto-scaling.

### Event Streaming & Data Sync
- **Kafka Connect**: Kafka-based tool for integrating data sources, including capturing changes from PostgreSQL and syncing them to Elasticsearch.

### Additional Tools
- **Zipkin**: Monitoring distributed traces for latency issues and tracking the flow of requests across microservices.
- **Thymeleaf**: Template engine used for creating dynamic email templates for user notifications.


---

## Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/MuhammedHussein3/Ticketmaster.git
