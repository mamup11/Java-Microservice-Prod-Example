# Auth Fast
The responsibility of this service is to handle 3 features:

- Registration
- Authentication
- Authorization

Since this is a mock service to illustrate a good general design for a microservice architecture the `Authentication` 
and `Authorization` sections will be just a really oversimplified implementation and should not be used in real systems

## Registration

When a user registers to the platform as a first step we should create a user, it being by social media sign in or 
registration by email/password. After successfully registering a user the next step should be to store the user 
information in the respective table/service. We should separate the login information from the user information and 
secure each one respectively.

As an example in this framework we have 2 important services responsible for the registration process that are 
`Auth-Fast` and `Commons-People`. The first stores the login information and handle the `Authentication` and 
`Authorization` flows, the second service handles the PII information of the users and provides an API to access user 
information when needed.

user -> Auth-Fast -> Commons-People

## Authentication

- user / pass 
- Login attempts
- Tokens and refresh tokens
- Inactive/banned tokens
- Encryption of tokens

## Authorization

- Token validation
- Decryption of tokens
- Token information