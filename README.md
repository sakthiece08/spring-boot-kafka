### Spring Boot Kafka

#### Technologies and Tools
* Java 21
* Maven
* sdkman
* Kafka

#### SDKMAN
Using .**_sdkmanrc_** file we can enable project specific java and maven versions

```
java=21.0.7-tem
maven=3.9.6
```

#### Maven Wrapper
Install maven wrapper to run maven commands on ease.

```
mvn wrapper:wrapper
./mvnw clean build
```

### Kafka Producer
Create Spring Boot Kakfa Producer project using [Spring Initializr](https://start.spring.io/) 

![img.png](src/main/resources/images/Producer.png)

