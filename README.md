# Online Voting System

A secure online voting system built with Java 21, Spring Boot 3.5.7, and PostgreSQL.

## Features

- **User Roles**: Admin, Voter, Candidate
- **Voter Registration & Authentication**: OTP-based 2FA via email
- **Poll Creation & Management**: Admin can create and manage polls
- **Real-Time Vote Casting**: One vote per user per poll, no revote allowed
- **Live Result Updates**: Real-time vote results via WebSocket
- **Security Features**:
  - JWT-based authentication
  - Rate limiting to prevent brute-force attacks
  - SQL injection prevention (using JPA/Hibernate)
  - Duplicate vote prevention (database constraints + application logic)
  - Account lockout after failed login attempts
  - Password encryption using BCrypt

## Technology Stack

- **Java**: 21
- **Spring Boot**: 3.5.7
- **Database**: PostgreSQL
- **Security**: Spring Security with JWT
- **Real-time**: WebSocket (STOMP)
- **Email**: Spring Mail (for OTP delivery)

## Prerequisites

- Java 21 or higher
- Maven 3.6+
- PostgreSQL 12+
- SMTP server access (for email OTP)

## Database Setup

1. Create a PostgreSQL database:
```sql
CREATE DATABASE onlinevotingsystem;
```

2. Update `application.properties` with your PostgreSQL credentials:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/onlinevotingsystem
spring.datasource.username=postgres
spring.datasource.password=postgres
```

## Email Configuration

Update email settings in `application.properties`:
```properties
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=your-email@gmail.com
spring.mail.password=your-app-password
```

**Note**: For Gmail, you need to use an App Password, not your regular password.

## Running the Application

1. Clone the repository
2. Configure database and email settings in `application.properties`
3. Run the application:
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## API Endpoints

### Authentication
- `POST /api/auth/register` - Register a new user
- `POST /api/auth/verify-otp` - Verify OTP and get JWT token
- `POST /api/auth/login` - Login with username/email and password
- `POST /api/auth/resend-otp` - Resend OTP to email

### Polls (Public)
- `GET /api/polls/active` - Get all active polls

### Polls (Authenticated)
- `GET /api/polls` - Get all polls
- `GET /api/polls/{pollId}` - Get poll by ID
- `GET /api/polls/{pollId}/results` - Get poll results

### Votes (Voter Role Required)
- `POST /api/votes/cast` - Cast a vote
- `GET /api/votes/check/{pollId}` - Check if user has voted

### Admin (Admin Role Required)
- `POST /api/admin/polls` - Create a new poll
- `PUT /api/admin/polls/{pollId}/activate` - Activate a poll
- `PUT /api/admin/polls/{pollId}/deactivate` - Deactivate a poll
- `GET /api/admin/polls` - Get all polls created by admin
- `GET /api/admin/polls/{pollId}/results` - Get poll results
- `GET /api/admin/users` - Get all users

## WebSocket

Connect to WebSocket at `ws://localhost:8080/ws` to receive real-time poll updates.

Subscribe to poll updates:
```javascript
stompClient.subscribe('/topic/polls/{pollId}', function(message) {
    // Handle poll result updates
});
```

## Security Features

1. **JWT Authentication**: Stateless authentication using JWT tokens
2. **Rate Limiting**: Prevents brute-force attacks and excessive requests
3. **Account Lockout**: Accounts are locked after 5 failed login attempts for 30 minutes
4. **OTP Verification**: Email-based OTP for account activation
5. **Password Encryption**: BCrypt password hashing
6. **SQL Injection Prevention**: Using JPA parameterized queries
7. **Duplicate Vote Prevention**: Database unique constraint + application logic
8. **CORS Configuration**: Configured for cross-origin requests

## Database Schema

The application uses JPA/Hibernate with `ddl-auto=update`, so tables will be created automatically. Key entities:

- **User**: Stores user information with roles
- **Poll**: Stores poll information
- **Candidate**: Stores candidate information for each poll
- **Vote**: Stores votes with unique constraint on (poll_id, voter_id)
- **OtpToken**: Stores OTP tokens for email verification

## Example Usage

### 1. Register a Voter
```bash
POST /api/auth/register
{
  "username": "voter1",
  "email": "voter1@example.com",
  "password": "password123",
  "fullName": "John Doe",
  "role": "VOTER"
}
```

### 2. Verify OTP
```bash
POST /api/auth/verify-otp
{
  "email": "voter1@example.com",
  "otp": "123456"
}
```

### 3. Create a Poll (Admin)
```bash
POST /api/admin/polls
Authorization: Bearer {admin_jwt_token}
{
  "title": "Presidential Election 2024",
  "description": "Vote for your preferred candidate",
  "startTime": "2024-01-01T00:00:00",
  "endTime": "2024-01-31T23:59:59",
  "candidates": [
    {
      "name": "Candidate A",
      "bio": "Experienced leader",
      "party": "Party A"
    },
    {
      "name": "Candidate B",
      "bio": "Progressive thinker",
      "party": "Party B"
    }
  ]
}
```

### 4. Activate Poll (Admin)
```bash
PUT /api/admin/polls/{pollId}/activate
Authorization: Bearer {admin_jwt_token}
```

### 5. Cast a Vote
```bash
POST /api/votes/cast
Authorization: Bearer {voter_jwt_token}
{
  "pollId": 1,
  "candidateId": 1
}
```

## Development Notes

- The application uses Lombok for reducing boilerplate code
- Validation is performed using Jakarta Validation
- Real-time updates are sent via WebSocket after each vote
- OTP tokens expire after 10 minutes
- Failed login attempts are tracked and accounts are locked after 5 attempts

## License

This project is a demo application for educational purposes.
