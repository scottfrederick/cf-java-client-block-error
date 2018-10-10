cf-java-client-block-error
============

## Building

This project requires Java 8 to compile. 

~~~
$ ./gradlew clean assemble
~~~

## Running the application locally

Edit the [application.yml](src/main/resources/application.yml) file to specify a CF target, username, password, etc.

~~~
$ ./gradlew bootRun
~~~

## Test the application

~~~
$ curl http://localhost:8080/spaces
{
    "error": "Internal Server Error",
    "message": "block()/blockFirst()/blockLast() are blocking, which is not supported in thread reactor-http-nio-2",
    "path": "/spaces",
    "status": 500,
}
~~~
