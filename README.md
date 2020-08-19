# Java Microservice Example

This repository aims to be a guideline to build microservices with Java trying to follow the best practices that I have 
learned through the years working on this area, with that in mind I cannot say this is the ultimate guide and any 
comments or suggestions will be greatly appreciated.

# The project

For this example we will be working as an example company called "Companion" with a domain companion.com. This company 
sells amazing T-Shirts, they have a web page under companion.com where they publish all the T-Shirts that are available 
for sale and this company has 3 teams, one that works in the store page under companion.com, another front end team that
works in a administrative web page where the staff can add new products (T-Shirts) with its metadata, see KPIs, 
query sales. And finally we work in the backend team that needs to build and sustain the backend infrastructure, rest 
endpoints, data storage and security.

Note: This is an oversimplified example that aims to show a good microservice structure, the 2 web pages previously 
mentioned does not exist and were mentioned only to give context to this example, you will se diagrams and requirements 
including this non existing pages but those are only there as context, here we will only see Java code and microservices.

To get a more general idea this is the high level products  architecture of the company:

![General architecture](assets/microservices.png)

# Backend

This example focus only on the backend side of the project 


## Loggin 

Dont forget to mention the choose in logger framework, markers and MDC http://logging.apache.org/log4j/2.x/manual/async.html
Remember to mention the need to the env variable to async logs

