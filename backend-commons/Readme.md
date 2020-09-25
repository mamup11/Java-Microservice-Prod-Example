## Setup

To add this common code as a dependency just add the local jar to your local repository with

```
mvn clean install -U
```

And then add the dependency to your pom as you would normally add a dependency and make sure to add the 
property `application.name` to your application.properties so the logs are able to get the name of the project 
that is adding logs.

Also if you want to automatically add the logging interceptor for your rest request you need to add the line

```
@ComponentScan(basePackages = "com.companion.api")
```

To your configuration or main file. If you don't want to add componentScan for this package you just need to 
add the interceptor yourself creating a configuration file like `LoggingInterceptorConfig` to add the interceptor to
the registry.