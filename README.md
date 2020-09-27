# Java Microservice Example

This repository aims to be a guideline to build microservices with Java trying to follow the best practices that I have 
learned through the years working on this area, with that in mind I cannot say this is the ultimate guide and any 
comments or suggestions will be greatly appreciated.

In this framework you will find ready to use features that are useful and in some cases mandatory for production 
applications. You will also find examples of how a microservice should be design, how should be tested, some examples 
of procedures that are needed working in a backend team like pull requests, the handling of the different environments 
such as local, stage and prod. Some guidance on CI, CD and some other things.

The idea with this project is to help people get more quickly to a production ready product and also to help people that 
are starting as backend developers or people who intent to start an startup or already have a company to understand how 
to get value of a good designed backend infrastructure, for some this may be a place to learn one or two things they can improve in their their current 
systems, for others this may be what they are used to see in a daily basis and some others won't agree with what I expose here. 
In order to improve the quality of this framework I am open to hear any comment or suggestions :)

# Table of Contents
1. [The project](#the-project)
    1. [Project Requirements](#project-requirements)
        1. [Store and Mobile App](#store-and-mobile-app)
            1. [Store and Mobile App Requirements](#store-and-mobile-app-requirements)
        2. [Company Portal](#company-portal)
            1. [Company Portal Requirements](#company-portal-requirements)
2. [Backend](#backend)
    1. [Setup](#setup)
    2. [Logging](#logging)
        1. [Logs analysis example](#logs-analysis-example)
        2. [Logging Framework](#logging-framework)
            1. [Markers and MDC](#markers-and-mdc)
            2. [Json Layout](#json-layout)
        3. [Network logging](#network-logging)
        4. [Support process](#support-process)
            1. [Correlation and Conversation IDs](#correlation-and-conversation-ids)
    3. [Interceptors](#interceptors)
    4. [Database](#database)
    5. [Rest endpoints](#rest-endpoints)
    6. [Calls to other Systems](#calls-to-other-systems)
    7. [Tests and coverage](#tests-and-coverage)
    8. [Pul Requests (PR), approvals and rejections](#pul-requests-pr-approvals-and-rejections)
    9. [CI - CD](#ci---cd)
    10. [Circuit breaker](#circuit-breaker)
    11. [Performance](#performance)
    12. [URL generation](#url-generation)

# The project

For this example we will be working as an example company called "Companion" with a domain companion.com. This company 
sells amazing T-Shirts, they have a web page under companion.com where they publish all the T-Shirts that are available 
for sale and allows customers to buy directly in the webpage. This company have 4 main projects:

- Store - Web app with React - under `companion.com`
- Company Portal - Web app with React - under `portal.companion.com`
- Companion App - Native app with Flutter - Available in `iOS` and `android`
- Backend Services - With Java - under `api.companion.com`

Note: This is an oversimplified example that aims to show a good microservice structure, apart from the Backend Services
the 3 projects previously mentioned does not exist and are described only to give context to this example, 
you will see diagrams and requirements for these projects to understand the requirements of the Backend Services, 
in this repo we will only see Java code and microservices projects only.

Note: All Companion's products are designed for use with AWS for this example

## Project Requirements

To get a more general idea this is the high level project architecture of the company here's is a high level diagram of 
the projects interactions:

![companion_projects](assets/companion_projects.png)

Next I will describe some features of each projects along with some requirements, the features are there to give a 
bigger context of what each project does and how big it is. The requirements are the ones that are implemented in this 
repository as part of the backend team to show some good practices and procedures.

### Store and Mobile App

The Store and the Mobile App are client faced projects and both allow users to browse products, buy products, create an account 
and look at previous purchases, save their favorites products, gift products to other people, publish and buy from 
products from a marketplace and have a virtual wallet that can be used to buy more products in the store or the marketplace. 
And many many more features that will not be listed.

#### Store and Mobile App Requirements

- Login with email and password, social media
- Register
- Get featured products
- Get product details by ID
- Add credit card
- Buy products selecting payment method and delivery address
- Get purchase history
- Get Store location to show on map

### Company Portal

The company portal is used for many things internally, since adding new products, updating product information, 
removing listed products, update available products in each storage, see reports and KPIs

#### Company Portal Requirements

- Login with email and password
- Register new people with roles (Admins and TI management)
- Search employees (All no clients)
- Add new products (Admins and Publishers)
- Get products (All no clients)
- Mark a product as featured (Admins and Publishers)
- Query sales of the current month (Admins and Finance)

# Backend

All the microservices exposes rest endpoints and accept json format only

![companion_projects](assets/microservices.png)

## Setup

## Logging

Logging is a vital part of evey production architecture since it is sometimes our only way to understand what is 
happening on a daily basis in our systems and allows us to monitor, alert and audit actions and changes as stated in the 
design principles for security on the AWS Well-Architected Framework https://wa.aws.amazon.com/wat.pillar.security.en.html.
It is also not secret that the bigger and complex the system the harder it 
is to debug it and give it support but that can be easily alleviated with a good logging system. Though not many people 
appreciate the potential of the logs, in my experience people tend to focus more on quickly developing new features, 
or getting to the market as soon as possible leaving logs for later as a tech debt which is lately forgotten, in some cases 
people later implements logs systems just to comply with the tech debt and start adding logs in the flow of some main 
features but not real value is being extracted from the logs and the implementation in these cases are more than 
all useless. In some cases the logs that were not implemented at the start are implemented as a tech debt later 
and it works amazingly and its good, but this last case ist not seen very often. I have seen many projects 
in different companies that either had a terrible logging design or not logging at all, there were only a couple of 
projects with an amazing logging system and coincidentally those companies were the biggest and more successful 
companies that I have had the privilege to work with. 

Logs provide useful information for the company, the not only help debug production issues and help track down problems, 
they also add value to a company by giving valuable data of its services, information that helps the company to learn 
of its products and designs and serves as a tool for continue development, to improve the current designs, to make them 
more efficient! or more secure, or be able to expand a business to new regions and geolocations. From the business 
point of view a company should be always changing and improving itself, growing, innovating, and for all that we 
need data to take decisions, with a good designed log system we will be able to extract useful information of our resources, 
like getting latency statistics of each service, understand how your microservices works, 
what endpoints get called the most? which is the slowest system and why? what clients call us the most? what countries? 
how much errors do we have in our systems? how does those categorize? where should we put efforts to fix or improve and 
where we shouldn't? all these questions can be answerable analyzing the information stored in the logs plus the 
ability to automatically shoot alarms or automate jobs based on some special jobs like errors or events. For this job 
there are many softwares in the market, software that automatically logs the calls made and track what a user does on a app, 
what buttons or endpoints the user calls and they give you a lot of useful information and for backend we can also 
use third party software to do this job but I rather build my own system that I share here, so it can be easily 
modified to include information relevant for the business.

### Logs analysis example

Lets say that a new company has backend with 2 microservices A and B, A has 10 rest 
endpoints and B has 20. Each morning the servers has an increase in users and its EC2 instances go from A having 2 to 5 
and B having 2 to 6. We want to reduce costs, we then log each call received and we log what endpoint is being called, 
from which microservice, the status code of the response, the time taken to respond in milliseconds the requesting IP.
 
With this information we are able to analyze the logs and realize that from all the calls made to A, 45% belong to a 
couple of endpoints, we also find out that 5 endpoints of B start increasing its response time from 100ms to 1500ms. 
With this information we can make some decisions, like we can create a new microservice that only has the 2 endpoints of A 
called C, now A has 8 endpoints, B 20 and C has 2; we can also investigate on the reason why the 5 endpoints of B get 
degraded under heavy load and we find out that those 5 endpoints are calling an external system that cannot handle the 
load but luckily that information does not change very often, more like one time each week so we decided to cache the 
response one time in a cache system, maybe in DB or a solution like redis and implement a notification endpoint which 
receives the new data when it changes and update the cache with the new information so all instances can get the latest 
information. With these changes is possible to imagine that each morning under load the instances will grow from 
A having 1 to 2, B having 2 to 4 and C having 1 to 2. This is an example of how a good use of the logs can help to 
improve a system.

### Logging Framework

For this example we are facing a heavily accessed system with thousands of calls per second and our main priority in this 
example is performance. With this in mind I chose to use Log4j 2 framework that provide us with the possibility of and 
amazing performance using the Asynchronous Loggers for Low-Latency Logging http://logging.apache.org/log4j/2.x/manual/async.html.
For this example code I configured all Loggers to be Asynchronous but if audit logs are a priority the file log4j2.xml 
and log4j2.component.properties can be modified to have a mix of sync and async loggers or just sync loggers if you need to.

#### Markers and MDC

Markers and the MDC represent a very important part of the logging system, in this repository you will find many uses of 
MDC but not examples of Markers. This is because Markers are mainly used to mark logs with "tags" that can be used to 
trigger automated jobs like i.e. you wish to send an email to security each time a user gets blocked because of many 
failed attempts, then you log a warn with a marker "USER_BLOCKED" and when the system detects this example marker it can
automatically send and email with the relevant information. MDC in the other hand can be considered as a thread context
that allows you to put information at the start of a thread that each consequently log can have access to. This is 
extreme useful to log contextual information on the rest calls such as Correlation-Id or the calling client so when a user
calls an endpoint all logs that the given endpoint creates can easily access and add that useful information. We will talk 
more about the Correlation-Id in the support process in this logging section.

#### Json Layout

You are probably used to see simple logs like:
`2020-09-22 02:52:38.831  INFO 5706 --- [nio-5000-exec-8] a.s.d.f.g.User: User example@asd.com registered successfully.`

This are common logs and the default kind of logs that you will see if you create a new Spring project. This logs are 
meant to be human readable and make it easier for us to debug our application but since what we want is to extract value 
from logs we are gonna use the Json Layout that as it names mention it logs json objects like:

```
{
  "timeMillis" : 1600744326837,
  "thread" : "main",
  "level" : "INFO",
  "loggerName" : "com.companion.api.commons.BaseApplication",
  "message" : "Started BaseApplication in 1.061 seconds (JVM running for 6.893)",
  "endOfBatch" : false,
  "loggerFqcn" : "org.apache.commons.logging.LogAdapter$Log4jLog",
  "threadId" : 1,
  "threadPriority" : 5,
  "timestamp" : "2020-09-21T22:12:06.837-0500",
  "application" : "companion-commons"
}
```

This logs gives flexibility to be ingested by a log visualization and analysis tool such as Kibana or Splunk that is where
we are going to query our logs and analyze the data from them. Since in this scenario we have multiple services and 
instances its not sustainable to search the logs in console or in the log files we need a tool to help us centralize and
make sense of all the logs that our systems will generate. In conclusion build your logs for a machine to process since 
machines can keep up with the amount of logs and present better data for the final user.

### Network logging

We can get logs from other products like a load balancer or a web server but I rather have the network logging be a 
responsibility of our application so every log follows an standard and have important information regarding the service 
and instance logging the information such as the correlation ID and the response time. That kind of information can also 
be obtained from the load balancer but the reason I choose to have the application do this job is to simplify the 
configuration efforts so I only have to import the already configured logging system and save time configuring 
load balancers to log what I want

### Support process

Logging plays an essential rol in the support process, they help to track down production issues to the root cause
and resolve bugs in an efficient manner. Also can help point out possible errors even before they happen like the 
sudden increase in an database latency can indicate that the resources of the DB instance may not be enough for 
the growing traffic.

#### Correlation and Conversation IDs

One of the biggest tool for a production support employee is an ID that can trace logs related to the same process to be
able to debug and find issues. For this we implemented a Correlation-ID and Conversation-ID, these two ids are added to 
all the logs in a rest request with the MDC so each time a services generates a log in the same rest request it will have
the same ID allowing us to query all the logs of one request by a unique id. 

But that's not all! with this implementation we can trace even calls to multiple systems in a complex environment. We 
assume that the client (webapp or mobile app) sends an unique correlation and conversation id when making a rest call as 
the headers, but if these headers are not present we generate one and return that id in the header of the response so 
the client knows what was the ID of its transaction and can be traced back. 
Also we sent that unique ID each time we call another service, so if a client calls a service A with an id "randomId" and 
that service A calls a service B and C with the same ID and then C calls a service D and E with the same ID and finally 
all returns to A and a response is given to the user we are now able to query ALL the logs from A, B, C, D and E by one ID
and that gives us the ability to check the logs on complex interactions between microservices and track down production 
issues easily even if you don't know where the root cause is. 

To accomplish this we also add the microservice ID to the log so we can identify which service is adding which log.

An example of the logs generated in one rest call is the following:

```
{
  "timeMillis" : 1600747926550,
  "thread" : "http-nio-8080-exec-2",
  "level" : "DEBUG",
  "loggerName" : "com.companion.api.commons.interceptors.LoggingInterceptor",
  "message" : "Starting Request POST /product, RequestBody: {\"username\":\"****\",\"password\":\"****\",\"id\":\"validId\",\"innerTest\":{\"username\":\"****\",\"password\":\"****\",\"name\":\"****\",\"lastName\":\"****\",\"message\":\"Say hello world!\"}}",
  "endOfBatch" : false,
  "loggerFqcn" : "org.apache.logging.slf4j.Log4jLogger",
  "threadId" : 38,
  "threadPriority" : 5,
  "timestamp" : "2020-09-21T23:12:06.550-0500",
  "X-Correlation-Id" : "3bbdab04-daf2-40f7-895b-7316d310c8c6",
  "X-Conversation-Id" : "f6764e9b-2e10-491f-b36c-68c740ebc31e",
  "clientAddress" : "0:0:0:0:0:0:0:1:52944",
  "application" : "@name@"
}
{
  "timeMillis" : 1600747926581,
  "thread" : "http-nio-8080-exec-2",
  "level" : "INFO",
  "loggerName" : "com.companion.api.commons.profile.controller.ProfileController",
  "message" : "Hello username",
  "endOfBatch" : false,
  "loggerFqcn" : "org.apache.logging.slf4j.Log4jLogger",
  "threadId" : 38,
  "threadPriority" : 5,
  "timestamp" : "2020-09-21T23:12:06.581-0500",
  "X-Correlation-Id" : "3bbdab04-daf2-40f7-895b-7316d310c8c6",
  "X-Conversation-Id" : "f6764e9b-2e10-491f-b36c-68c740ebc31e",
  "clientAddress" : "0:0:0:0:0:0:0:1:52944",
  "application" : "@name@"
}
{
  "timeMillis" : 1600747926591,
  "thread" : "http-nio-8080-exec-2",
  "level" : "DEBUG",
  "loggerName" : "com.companion.api.commons.interceptors.LoggingInterceptor",
  "message" : "Finished Request POST /product, RequestBody: {\"username\":\"****\",\"password\":\"****\",\"id\":\"validId\",\"innerTest\":{\"username\":\"****\",\"password\":\"****\",\"name\":\"****\",\"lastName\":\"****\",\"message\":\"Say hello world!\"}}; ResponseStatus: 200, ResponseTime: 40ms, ResponseBody: {\"username\":\"****\",\"password\":\"****\",\"id\":\"validId\",\"innerTest\":{\"username\":\"****\",\"password\":\"****\",\"name\":\"****\",\"lastName\":\"****\",\"message\":\"Say hello world!\"}}",
  "endOfBatch" : false,
  "loggerFqcn" : "org.apache.logging.slf4j.Log4jLogger",
  "threadId" : 38,
  "threadPriority" : 5,
  "timestamp" : "2020-09-21T23:12:06.591-0500",
  "X-Correlation-Id" : "3bbdab04-daf2-40f7-895b-7316d310c8c6",
  "X-Conversation-Id" : "f6764e9b-2e10-491f-b36c-68c740ebc31e",
  "clientAddress" : "0:0:0:0:0:0:0:1:52944",
  "responseTime(ms)" : "40",
  "application" : "@name@"
}
```
Note that the 3 logs have the same correlation and conversation ID as well as the body of the request and the response, 
the response code and the time in milliseconds that it took from the beginning of the call until the end when it responds.
This data is useful to analyze our systems and find latency issues or broken endpoints.

Note: The print of the request body and response body are only added on debug mode which can be used in development or test
environment, the normal mode for production should be info that does not logs the body of the request and response 
but logs the useful information of the rest call. Also you will find a masker class that mask the PII information based 
on the name of the json attributes which you can override by adding the `masker.attributesToMask` property that is a coma 
separated value, the default is:
`pass,password,user,username,name,lastName,email,mail,cardNumber,cvv,expDate`

## Interceptors 

## Database

DynamoDB, MariaDB

## Rest endpoints

DTO vs models, converters

## Calls to other Systems

Retrofit 2

## Tests and coverage

## Pul Requests (PR), approvals and rejections

## CI - CD

SonarQ, Snyk

## Queues

## Circuit breaker

## Performance

## URL generation