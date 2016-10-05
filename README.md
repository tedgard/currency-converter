#Currency Converter
Convert any money value from one currency to another at the latest/historical rates using an API endpoint.

## Technology and requirement
This is spring boot web MVC application using JPA/Hibernate for data persistence.
Please make sure you have Java version 1.8 or later installed. Maven 3.3 is optional, for the application comes with a maven script. 

## Build and run
If you already have Maven 3.3+ installed, you can execute the following command : 
```
mvn install 
```

In case you don't have Maven, please use the script provided :

Windows 
```
mvnw.cmd install 
```

Linux, Mac OSX, etc.
```
./mvnw install 
```

Once everything has run to completion, the application ben executed :
```
java -jar target/currency-rate-converter-1.0-SNAPSHOT.jar 
```

You can access the app via your browser using the following url :
```
http://localhost:8080 
```


## Acceptance tests
The files have been written in the Gherkin language and can be found in the folder [feature](src/main/resources). 

Cucumber-JVM has been used to generate the steps from the features, allowing the implementation of the step definitions.

## Database 
Java Persistence Api (JPA) with Hibernate has been used for persistence layer. 

```
datasource.url=jdbc:h2:mem:currency-converter
datasource.username=admin
datasource.password=admin
```

Once the application is running, you can access the aforementioned database console through
```
/h2-console
```

## Api rates exchange endpoint
This applicaiton uses an api endpoint for retrieving all the currency rates.

API used : openexchangerates.org
Documentation : https://docs.openexchangerates.org/


## Continuous integration server and cloud hosting
The continuous integration server used here is [travis-ci.org] (https://travis-ci.org/tedgard/currency-converter) : https://travis-ci.org/tedgard/currency-converter

The cloud hosting used here is [heroku.com] (https://www.heroku.com/): http://currency-rate-converter.herokuapp.com

Those two are directly linked to GitHub app repository and deployed only when the build from the CI server passes. 

