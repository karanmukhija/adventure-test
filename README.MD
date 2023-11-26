## User Service

This service will create/update/delete/find user from the database

- The below APIs will be accessed by user having ADMIN token. Invoke the Authentication Service http://localhost:8081/authentication/generate/adminToken to get the admin token and pass the value as Authorization Header in the following APIs
  -  Create User - http://localhost:8080/api/users
  -  Delete User - http://localhost:8080/api/users/1

   
- The below APIs will be accessed by user having ADMIN or USER token. Invoke the Authentication Service http://localhost:8081/authentication/generate/userToken or http://localhost:8081/authentication/generate/adminToken to get the user or admin token and pass the value as Authorization Header in the following APIs 
  - Get User - http://localhost:8080/api/users/1
  - Update User - http://localhost:8080/api/users/1

Run the Main Application **UserServiceApplication.java** and verify if you can access the above endpoints. Make sure the Authentication Service is running on port 8081

You can access the H2 Console via http://localhost:8080/h2-console and query user_details db. The password is listed in application.properties 