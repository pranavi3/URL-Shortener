**URL Shortener – Design Document**



1.  **Problem Statement**



The aim of this project is to build an URL shortener service like [bit.ly](http://bit.ly) that takes a long URL and returns a short URL. When short URL is accessed, it should redirect to the long URL.



**2\. Functional Requirements** 

  

*   On submitting a long URL, the service should return a **short and unique alias**.
*   If the same long URL is submitted multiple times, it should return the **same short URL**.
*   When a short URL is accessed, it should **redirect to the original long URL**.
*   The service should track and expose the number of times a short URL has been accessed. 

  

**3\. Non Functional Requirements**

*   **Low latency**: The service should redirect to the original long URL with minimal delay.
*   **High Availability:** The service should remain operational with minimal or no downtime, even during failures
*   **Reliability:** The data/mapping should not be lost under any circumstances.
*   **Scalability:** The service should handle millions of reads per day and scale horizontally to meet the demand.



**4\. API Design**

The API has 3 endpoints serving different purposes. 

*   **POST /api/url/generate**

*   **Purpose:** Generate a short URL for a given long URL
*   **Request:**

{

“longURL” : “https://www.example.com/some/long/url”

}

*   **Response:**  (201 CREATED)

{

“shortCode” : “abcA123”

}



*   **GET  /api/url/{shortCode}**

*   **Purpose:** Redirect to the original long URL
*   **Response:**  (302 FOUND)

{

“longURL” : “https://www.example.com/some/long/url”

}

*   **GET /api/url/{shortCode}/analytics**

*   **Purpose:** Get the number of times the short URL has been accessed
*   **Response:** (200 OK)

{

“clicks” : 127

}



**Reasoning behind the API Design:**

*   **Why HTTP code 302?**

*   302 does **not cache** the redirect, which increases load but ensures analytics are captured.
*   301 could reduce server load, but breaks analytics if browser caches redirect.



**5\. Database Schema**

*   url\_mappings Table

| Column Name | Data Type | Description |
| --- | --- | --- |
| id | INTEGER | Primary key, auto-increment |
| short_code | VARCHAR | Unique short identifier (e.g., "abc123") |
| long_url | TEXT | Original long URL |
| user_id | VARCHAR | (Optional) User ID for multi-user support |
| creation_date | DATETIME | (Optional) Timestamp of creation |
| clicks | INTEGER | Number of times the short URL was accessed |
| expires_at | DATETIME | (Optional) Expiration time after which URL dies |



Note: Index will be added on short\_code for faster lookup



**Reasoning behind Database Schema:**

*   Relational database is chosen for data storage for following reasons:

*   **Structured data:** Simple schema and flat mapping between short\_code and long\_url
*   **Strong consistency:** Every time the redirection happens to correct long URL.
*   **Faster lookup:** Index on short\_code will make retrieval efficient.
*   **Schema expansion:** Easy to add additional fields in the future.

*   Since there is no need for flexible schema or nested documents, NoSql is not needed.



**6\. Deep Dive**

*   **URL Generation Strategy**

*   **Base62** encoding:

*   Generate a short URL by encoding the auto-incremented DB id into Base62 (a-z A-Z 0-9)
*   This will ensure uniqueness(no collisions).
*   Fast and predictable
*   The length of shortCode would be between 6-8 characters 
*   Total codes that can be generated = 62^6 - 62^8 = billions of unique URLs.
*   All these URLs will be human readable

*   **Reasoning behind URL Generation Strategy:**

*   Why not Hashing/ Random String?

*   Hashing can cause collisions, needs retry logic
*   Random String needs to be checked in DB on generation to ensure uniqueness.

*   In future enhancements, when custom codes are allowed, DB table should be queried for uniqueness.

*   **Caching strategy**

*   Cache will be used to store the mapping between longURL and shortURL for faster redirection.

*   Key: shortCode
*   Value: longURL
*   **Strict consistency enforced** , on a cache miss or URL update, the DB is queried and cache is updated immediately.
*   No stale data allowed

*   Cache will also be used to update the click count of shortURL and later periodically bulk update to DB.

*   Key: shortCode
*   Value: clickCount
*   Redis INCR used to track click counts in real-time.
*   Eventual consistency is acceptable

*   **Technology: Redis**

*   Fast key-value retrieval
*   TTL support for expiration



**7\. Future Scope:**

*   **Custom Short codes**

*   Allow users to specify a desired short code instead of using auto-generated ones.

*   **Advanced analytics**

*   Access count over timeframe
*   User level analytics
*   Geo location level analytics

*   **Authentication and Authorisation**

*   Allow only specific users to create and manage URLs

*   **Deletion and URL expiration**

*   Add Time to live(TTL) and deletion API

*   **Rate Limiting**

*   Protect the service using rate limiting.



**8\. Tech Stack:**

*   Java, Spring Boot,  MySQL, Redis, Docker, Postman



**9\. Testing strategies:**

*   Unit tests - Mockito, Unit
*   Integration test
*   Manual test - Postman