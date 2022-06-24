<h1 align="center">🪴 Alura Forum API 🪴</h1>

<img src="https://64.media.tumblr.com/47b36822fce08a19e48c9f918403f4db/tumblr_omvjdm4AcD1w6mwsfo10_500.png" height="120" width="100%" object-fit="cover" />

This is a simple API built with Spring Boot to simulate how Alura Forum may works in backstage.


## Resources created

You can access these resources by running the project locally as well as by doing HTTP calls to the API on Cloud.

**private static final String _CLOUD_API_URL_** = https://alura-forum-gabriellucas.herokuapp.com/topicos


| resource | uri |
| --- | --- |
| register topic | /topicos |
| detail topic | /topicos/{id} |
| update topic | /topicos/{id} |
| list topics | /topicos |
| delete topic | /topicos/{id} |
| generate student token | /auth |
| generate moderator token | /auth |
| actuator | /actuator |

<br>
  
## Tecnhologies I learned

- Java
- Spring Boot
- Spring Data JPA
- Javax Validation
- Controller Advice
- Data Transfer Object - DTO
- REST Architecture Concepts
- Pagination
- Spring Cache
- Spring Security
- JWT Authentication
- Monitoring with Spring Actuator
- Documentation with OpenAPI and Swagger
- Spring Security with Roles
- Spring Profiles
- Testing
- Deploy
- Deploy on cloud with Docker
