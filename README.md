
## ✨ ~ System Monitoring Data Transaction ~ ✨

✨ 👋 Hi Everyone 👋 ✨

Here I will share my spring boot project which functions as a back end in the system later. Previously, I shared a Laravel front end project that can be used to monitor transaction data in bank accounts. Now I will share the back end part that is similar to this.

## About this project :

This project was created using the Spring Boot framework using IDEA intelligence editor software. This project uses authentication and authorization for the login and registration process. The API can be accessed according to the authorization that has been given.

Here are some APIs that can be accessed :

## API Authentication : [example -- localhost:8080/api/auth/login ]

#### ✨Register Customer --- ( /api/auth/register/customer )

> @Request Body - Method POST

    {
        "email" : "user@gmail.com",
        "password" : "user1234",
        "name" : "user",
        "address" : "user street",
        "mobilePhone" : "0000000"
    }

> @Response Body

    {
        "statusCode": 201,
        "message": "Successfully register new customer",
        "data": {
            "email": "user@gmail.com",
            "role": "ROLE_CUSTOMER"
        }
    }

---

#### ✨Register Admin --- ( /api/auth/register/admin )

> @Request Body - Method POST

    {
        "email" : "admin@gmail.com",
        "password" : "admin123",
        "name" : "admin",
        "mobilePhone" : "00000000"
    }

> @Response Body

    {
        "statusCode": 201,
        "message": "Successfully register new customer",
        "data": {
            "email": "admin@gmail.com",
            "role": "ROLE_ADMIN"
        }
    }

---

#### ✨Login --- ( /api/auth/login )

> @Request Body - Method POST

    {
        "email" : "user@gmail.com",
        "password" : "user1234"
    }

> @Response Body

    {
        "statusCode": 200,
        "message": "Success Login",
        "data": {
            "email": "user@gmail.com",
            "role": "ROLE_CUSTOMER",
            "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJTeXN0ZW0gTW9uaXRvcmluZyB0cmFuc2FjdGlvbiIsInN1YiI6IjZlZDVkNjA4LTU1MWMtNDNhZC05YjYyLTQ3ZGY2ODM3MjI0ZiIsImV4cCI6MTcxMTU0NjQ4MSwiaWF0IjoxNzExNTQyODgxLCJyb2xlIjoiUk9MRV9DVVNUT01FUiJ9.ZNBLWHoqDbFBXnOxDrc-fPHTwfPx8KX7as_y8QxYFcE"
        }
    }

## Role Customer API : 

#### ✨Add Bank --- ( /bank )

> @Request Body - Method POST

    {
        "service" : "bank_name",
        "noRekening" : "12345678",
        "customer_id" : "6e03cc20-9703-498c-bac7-7f4b9cef190f"
    }

---

#### ✨Update Bank --- ( /bank )

> @Request Body - Method PUT

    {
        "idBank" : "4e69e0a4-62cd-478f-84ac-d1e1337e89a0",
        "service" : "bank_name",
        "noRekening" : "1234567"
    }

---

#### ✨Find Bank By Id --- ( /bank/{id} )
> Method GET

---

#### ✨Add Transaction --- ( /transaction )
> @Request Body - Method POST

    {
        "idBank" : "444aefd4-958f-436d-90d3-2a745776e9ca",
        "transDate" : "2011-03-27T10:16:30",
        "type" : "CREDIT",                          //-> you can change this to DEBIT OR CREDIT
        "nominal" : 10000,
        "saldo" : 8600000,
        "description" : "Free Money"
    }

---

#### ✨Update Transaction --- ( /transaction )
> @Request Body - Method PUT

    {
        "idTransaction" : "79f85722-8d0e-4e0d-8942-fe3cbc033e36",
        "transDate" : "2020-03-27T10:16:30",
        "type" : "DEBIT",
        "nominal" : 90000,
        "saldo" : 1600000,
        "description" : "Free to play"
    }

---


## Role Admin API :

#### ✨Get All Bank --- ( /all/bank )

> Method GET

---

## Noted : 
In this project, I use native queries to manage existing transaction data. You can see the original query I used in the “repository” folder in the BankRepository.java and TransactionRepository.java files. 
because in this project you log in using a token, don't forget to enter your token when you want to access the API.

Also you can see the tests I have done with the POSTMAN application at the following link. ✨ <br>
https://www.postman.com/supply-technologist-42770162/workspace/test-api-monitoring-system/collection/32333114-fbed6ebd-89d4-45a4-ab35-81a42c472509?action=share&creator=32333114

You can change and study the code that I have created, hopefully this project can help you. 😄🙏✨ <br>
See you next time.. ✌✌



<br> 
#API
#MySql
#QueryNative
#SpringIoC
#DataManipulation
#Authentication
#Authorization
