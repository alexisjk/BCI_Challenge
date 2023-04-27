# Challenge BIC
Develop and Api for a Sign-up and Login users

## Steps to Setup Locally

**1. Clone the application**

```bash
git clone [https://github.com/alexisjk/BCI_Challenge.git)]
```
## How to run the application?
The most easy way to run the application it s using gradle :

```bash
gradle bootRun
```
The app will start running at <http://localhost:8080>

## Database Tables

Tables will be generated automatically thanks the following configuration inside `src/main/resources/application.properties`
```bash
spring.jpa.hibernate.ddl-auto = update
```
It s using a H2 memory database ,you can access the console in : 
http://localhost:8080/h2-console

## Unit tests
Spook framework had been used to perform Unit Test to the UserService Class

## How Application errors were handled?
`RestExceptionHandler` class it s in charge of managing most common errors.
This class could pottentially scale if we wish to manage more errors.
The following structure it s returned to the client in case of an Exception or if there is a validation error using the Api.
###### Example creating a User  with  missing or incorrect fields:

Status Header Response --> 400 .
I added and extra "errors" field to show the list of incorrect fields
```json
{
    "error": {
        "codigo": "BAD_REQUEST",
        "timestamp": "11-08-2021 10:35:54",
        "details": "Invalid_Request",
        "errores": [
            {
                "password -> La contraseña debe tener una longitud de 8 a 12 caracteres, una letra mayúscula y dos números"
            }
        ]
    }
}

```


### Testing the API Locally
There is a Postman collection in the root of the project  `/Postman`.

### Security
Added a filter to control de JWT sent in the header and get the Current user in the controller using @AuthenticationPrincipal

### Others
Used @CreatedDate and @LastModifiedDate to manage dates related to the user

## Exploring the  APIs

The app defines following requests.

### User API

| Method | Url | Description | Sample Valid Request Body |
| ------ | --- | ----------- | ------------------------- |
| POST   | /api/sing-up | Create a User| [JSON](#user-create) |
| GET    | /api/login | Get User profile |  [JSON](#user-login)|


## Sample JSON Request/Response 

##### <a id="user-create">Create User -> /api/sing-up</a>
```json
{
    "name": "User1",
    "email": "user1@example.comm",
    "password": "pass22aasG",
    "phones": [
        {
            "number": "987654321",
            "citycode": "1",
            "contrycode": "57"
        }
    ]
}
```
##### Response
```json
{
    "id": "1c1f7ddb-d279-4a58-b919-d31b1fdcf64f",
    "created": "2023-04-27T10:36:59.491",
    "lastLogin": "2023-04-27T10:36:59.491",
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyMUBleGFtcGxlLmNvbW0iLCJleHAiOjE2ODI2Mzg2MTksImlhdCI6MTY4MjYwMjYxOX0.izrPNSfzocFllvXQfAtSFfjl6W_sP8TD-tZb1b9qA6A",
    "name": "User1",
    "email": "user1@example.comm",
    "password": "$2a$10$KbS29MN3gJLcAxFdzLe2nOHOFGPDKekegPDf36lczzrutEmDlgWJu",
    "phones": [
        {
            "number": 987654321,
            "citycode": 1,
            "contrycode": "57"
        }
    ],
    "active": true
}
```
##### <a id="user-login">Login User -> /api/login</a>
Send the authorization token in the following way : 

![image](https://user-images.githubusercontent.com/1265533/234883090-17df8d83-efab-4d5c-8d00-18abef04613c.png)

##### Response
```json
{
    "id": "1c1f7ddb-d279-4a58-b919-d31b1fdcf64f",
    "created": "2023-04-27T10:36:59.491",
    "lastLogin": "2023-04-27T10:36:59.491",
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyMUBleGFtcGxlLmNvbW0iLCJleHAiOjE2ODI2Mzg4NzYsImlhdCI6MTY4MjYwMjg3Nn0.3QnBkJZ6OWG9hbMhrQ7Ajmx7coZWCtvZeMksvbF9xNQ",
    "name": "User1",
    "email": "user1@example.comm",
    "password": "$2a$10$KbS29MN3gJLcAxFdzLe2nOHOFGPDKekegPDf36lczzrutEmDlgWJu",
    "phones": [
        {
            "number": 987654321,
            "citycode": 1,
            "contrycode": "57"
        }
    ],
    "active": true
}
```
