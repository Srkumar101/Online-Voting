# Online Voting System

Online Voting System is a Spring Boot-based application that allows users to cast their votes online for various elections and surveys. The application is designed to be easy to use, secure, and customizable to fit the needs of various organizations.

## Key Features

- RESTful API for voting and surveying
- Role-based access control for administrators and users
- API level permission for restricted access to certain functions
- Detailed logs for auditing and error tracking

The RESTful API for voting and surveying is designed to be intuitive and easy to use, allowing users to quickly cast their votes or respond to surveys. The application also supports role-based access control, allowing administrators to define different roles with different permissions for users. This ensures that only authorized personnel can manage and access sensitive data.

API level permission provides an additional layer of security by allowing administrators to define which APIs a user can access based on their role. This ensures that only authorized users can access specific APIs and functions, enhancing the overall security of the application.

Detailed logs are also maintained for auditing and error tracking, allowing administrators to track user activity and troubleshoot any issues that may arise.

## Getting Started

- Clone the repository to your local machine using <b>git clone https://github.com/nawabrahbar/library-management.git</b>
- Navigate to the project directory using <b>cd library-management</b>
- Build the project using <b>mvn clean install</b>
- Start the server using <b>mvn spring-boot:run</b>
- Open a web browser and navigate to [http://localhost:8080/api/v1](http://localhost:8080/api/v1) to view the application

## Usage

The Online voting system project provides the following endpoints:

### Authentication
- GET /invalidate-token: Expire token of a candidates in the election system.
- POST /login: Validate a candidate to the election system.

### User
- GET /user: Retrieves a list of all users in the system.
- GET /user/{id}: Retrieves a specific user from the system by ID.
- POST /user: Adds a new user to the system.
- PUT /user/{id}: Updates an existing user in the system by ID.
- PUT /change-password : Update user password.
- DELETE /user/{id}: Deletes a specific user from the system by ID.

### User
- GET /user: Retrieves a list of all users in the system.
- GET /user/{id}: Retrieves a specific user from the system by ID.
- POST /user: Adds a new user to the system.
- PUT /user/{id}: Updates an existing user in the system by ID.
- PUT /change-password : Update user password.
- DELETE /user/{id}: Deletes a specific user from the system by ID.
- DELETE /user: Deletes all user from the election system.

### Votes
- GET /vote: Retrieves a list of all votes made by users in the library system.
- GET /vote/{id}: Retrieves a specific vote from the library system by ID.
- POST /vote: Adds a new vote to the library system.

### Roles
- GET /role: Retrieves a list of all roles defined in the library system.
- GET /role/{id}: Retrieves a specific role from the library system by ID.
- POST /role: Adds a new role to the library system.
- PUT /role/{id}: Updates an existing role in the library system by ID.
- DELETE /role/{id}: Deletes a specific role from the library system by ID.
- DELETE /role: Deletes all role from the election system.

### Privileges
- GET /privilege: Retrieves a list of all privileges defined in the library system.
- GET /privilege/{id}: Retrieves a specific privilege from the library system by ID.
- POST /privilege: Adds a new privilege to the library system.
- PUT /privilege/{id}: Updates an existing privilege in the library system by ID.
- DELETE /privilege/{id}: Deletes a specific privilege from the library system by ID.
- DELETE /privilege: Deletes all privilege from the election system.

### Candidates
- GET /candidate: Retrieves a list of all candidates in the election system.
- GET /candidate/{id}: Retrieves a specific candidate from the election system by ID.
- GET /candidate/approved: Retrieve a list of all approved candidate from the election system.
- GET /candidate/pending: Retrieve a list of allpending candidate from the election system.
- GET /candidate/denied: Retrieve a list of all pending candidate from the election system.
- POST /registration: Adds a new candidate to the election system.
- PUT /candidate/{id}: Updates an existing candidate in the election system by ID.
- PUT /candidate/approved/{id}: Updates an existing pending candidate to approved in the election system by ID.
- PUT /candidate/denied/{id}: Updates an existing pending candidate to denied in the election system by ID.
- DELETE /candidate/{id}: Deletes a specific candidate from the election system by ID.
- DELETE /candidate: Deletes all candidate from the election system.

### Blacklist Token
- DELETE /black-listed-token: Deletes all black listed token from the election system.

## Contributing

We welcome contributions to the Library Management project! To contribute:

- <b>Fork</b> the repository to your own GitHub account.
- Clone the repository to your local machine using <b>git clone https://github.com/nawabrahbar/library-management.git</b>
- Create a new branch for your changes using <b>git checkout -b feature/your-feature-name</b>
- Make your changes and commit them using <b>git commit -m "Your commit message"</b>
- Push your changes to your forked repository

## Acknowledgements

- Spring Boot
- Apache Tomcat
- Java 8 or higher
- MongoDB
