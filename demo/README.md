## This is Online store of pc`s 
## Demo v0.1 Categories&Products
### To run the application:  
#### Ensure to have installed these components:  
+ Java: jdk-25.*  
+ Build: Gradle 9.7.1  
+ Container: Docker 29.2.0  
+ DB: MySQL 8.4.11 LTS

MySQL Server needs to be configured at 3306 port

#### Run commands:
```ps
docker-compose up -d
```
```ps
./gradlew bootRun
```

#### Then go to POSTMAN:
###### on current version available Products and Categories API
###### there some examples of API endpoints
+ Get    // get all categories   
```http
http://localhost:8080/api/categories
```
+ Post and body // create new category
```http
http://localhost:8080/api/categories
```
```json 
{"name": "GPU"}
```
