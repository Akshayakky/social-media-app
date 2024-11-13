Social Media Application - 
This project is a REST API-based social media application allowing users to join groups, create posts with media attachments, like posts, and add comments. Designed with a modular architecture, the application follows best practices to ensure scalability and maintainability for future enhancements. The project is implemented using Spring Boot and Java.

Project Overview
The application provides a simplified social media platform where users can:

Join and create groups.
Create posts with multiple media types (images, videos) by providing S3 URLs.
Like and comment on posts.
View a paginated feed of posts from groups they belong to.
The API structure allows for easy scalability and the addition of new features.

Assumptions
Media Uploads: Media files are assumed to be pre-uploaded to Amazon S3 by the frontend. The backend API expects S3 URLs as part of the post creation request. However, if required, the backend can be modified to handle direct uploads.
Exception Handling: Centralized exception handling can be further enhanced using @ControllerAdvice for consistent error responses across all endpoints.
User Management: User authentication and authorization are outside the current scope, but this project can be extended to include token-based authentication (e.g., JWT).
Future Roadmap
Caching: Implement caching for frequently accessed endpoints, such as user feeds and group details, using tools like Redis to optimize performance.
Multi-threading: Enhance the processing of likes and comments to handle high concurrency scenarios effectively.
Real-time Updates: Integrate WebSockets for real-time notifications of likes, comments, or new posts.
Media Processing: Optionally include backend handling for media uploads to S3, along with image resizing or video processing if needed.
API Usage
Here are some example curl commands to interact with the API endpoints:

Create a Group


curl --location 'http://localhost:8080/api/social/v1/groups' \
--header 'Content-Type: application/json' \
--data '{
"groupName": "group"
}'
Create a Post


curl --location 'http://localhost:8080/api/social/v1/groups/16/posts' \
--header 'Content-Type: application/json' \
--data '{
"groupId": 1,
"caption": "My new post caption",
"userId": 31,
"media": [
{
"mediaType": "IMAGE",
"mediaUrl": "https://s3.amazonaws.com/mybucket/myphoto.jpg"
},
{
"mediaType": "VIDEO",
"mediaUrl": "https://s3.amazonaws.com/mybucket/myvideo.mp4"
}
]
}'
Add a New User


curl --location 'http://localhost:8080/api/social/v1/users' \
--header 'Content-Type: application/json' \
--data '{
"firstName": "name4",
"lastName": "lastname4",
"profileImageUrl": "imageUrl4",
"type": "PARENT_MENTOR",
"groupIds": [39]
}'
Like a Post


curl --location 'http://localhost:8080/api/social/v1/posts/10/like/31' \
--header 'Content-Type: application/json'
Comment on a Post


curl --location 'http://localhost:8080/api/social/v1/posts/10/comment' \
--header 'Content-Type: application/json' \
--data '{
"userId": 31,
"commentText": "Great post!"
}'
Get User Feed (Paginated)


curl --location 'http://localhost:8080/api/social/v1/users/31/feed?page=0&size=10'


Installation and Setup
Clone the Repository

git clone https://github.com/Akshayakky/social-media-app.git
Configure Database Set up your database configurations in application.properties.

Run the Application
./gradlew bootRun

Access API: The base URL for accessing the API will be http://localhost:8080/api/social/v1/.

Technical Stack
Spring Boot: Core framework for building the REST APIs.
JPA: For managing relational data and mapping it to Java objects.
Database: PostgreSQL is ideal for this social media application due to its ACID compliance for reliable transactions, scalability for high data volumes, and support for advanced data types (JSON, arrays) suited to complex social media data. Its indexing and full-text search features improve response times, making it efficient for querying user interactions and content.
Amazon S3: Assumed as the storage for user-uploaded media.
Transaction Management
This project uses Spring's @Transactional annotation to ensure atomicity and consistency for critical database operations. Methods that involve creating or updating entities are wrapped in transactions, ensuring that any errors result in a rollback, maintaining data integrity.