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
$ curl -X GET http://localhost:8080/orgs

system,test

$ curl -X PUT http://localhost:8080/orgs
{
    "error": "Internal Server Error",
    "message": "block()/blockFirst()/blockLast() are blocking, which is not supported in thread reactor-http-nio-2",
    "path": "/orgs",
    "status": 500,
}

$ curl -X GET http://localhost:8080/spaces

development,staging,production

$ curl -X PUT http://localhost:8080/spaces
{
    "error": "Internal Server Error",
    "message": "block()/blockFirst()/blockLast() are blocking, which is not supported in thread reactor-http-nio-2",
    "path": "/spaces",
    "status": 500,
}
~~~

When the `Internal Server Error` happens, the top of the stacktrace is 

~~~
java.lang.IllegalStateException: block()/blockFirst()/blockLast() are blocking, which is not supported in thread reactor-http-nio-5
	at reactor.core.publisher.BlockingSingleSubscriber.blockingGet(BlockingSingleSubscriber.java:111) ~[reactor-core-3.2.0.RELEASE.jar:3.2.0.RELEASE]
	at reactor.core.publisher.Mono.block(Mono.java:1498) ~[reactor-core-3.2.0.RELEASE.jar:3.2.0.RELEASE]
	at org.cloudfoundry.reactor.uaa.UaaSigningKeyResolver.refreshKeys(UaaSigningKeyResolver.java:107) ~[cloudfoundry-client-reactor-3.13.0.RELEASE.jar:na]
	at org.cloudfoundry.reactor.uaa.UaaSigningKeyResolver.getKey(UaaSigningKeyResolver.java:89) ~[cloudfoundry-client-reactor-3.13.0.RELEASE.jar:na]
	at org.cloudfoundry.reactor.uaa.UaaSigningKeyResolver.resolveSigningKey(UaaSigningKeyResolver.java:59) ~[cloudfoundry-client-reactor-3.13.0.RELEASE.jar:na]
	at io.jsonwebtoken.impl.DefaultJwtParser.parse(DefaultJwtParser.java:316) ~[jjwt-0.9.1.jar:0.9.1]
	at io.jsonwebtoken.impl.DefaultJwtParser.parse(DefaultJwtParser.java:481) ~[jjwt-0.9.1.jar:0.9.1]
	at io.jsonwebtoken.impl.DefaultJwtParser.parseClaimsJws(DefaultJwtParser.java:541) ~[jjwt-0.9.1.jar:0.9.1]
	at org.cloudfoundry.reactor.uaa.UsernameProvider.getUsername(UsernameProvider.java:71) ~[cloudfoundry-client-reactor-3.13.0.RELEASE.jar:na]
~~~
