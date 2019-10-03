## Welcome to "Spring-boog-guess-game" with GitHub Actions

the main goal of this repo is to create a guessing game app using spring-boot-webflux, which will store the game information in a postgres database.
the application will run in an AWS EKS cluster, the deployment pipeline will use github actions for CI/CD.

# Prerequisities
- Java 8 / maven etc
- Docker
- Kubectl
- helm
        
# Tech Stack:
- Spring-boot webflux
- Kotlin
- [Klint](https://ktlint.github.io/)
- [AWS EKS](https://aws.amazon.com/eks/)
- Github Actions
- [Test containers](testcontainers.org)
- PostgreSQL
- [flyway](https://flywaydb.org/)
- [R2DBC](https://r2dbc.io/)

# Steps
1) create a sample application using [spring boot initilizr](https://start.spring.io/): 
for this app I used version 2.2 RC1 with kotlin and maven.
2) lets add some features to the empty spring boot repo, the api should:
- Create a game, which generate a random number and store's it in session.
Request:
`curl -X POST http://localhost:8080/api/games`
response:
```
{
    "id": "7e37b1ec-e40c-425a-9757-abf8a57ffb91",
    "guess": 461
}
```
- Get a game with the id returned in the previous request.
Request:
`curl -X GET http://localhost:8080/api/game/7e37b1ec-e40c-425a-9757-abf8a57ffb91`
Response:
```
{
    "id": "7e37b1ec-e40c-425a-9757-abf8a57ffb91",
    "guess": 461
}
```
- receive guesses, the api will response with a 200 and message text either saying too low, too high, congratulations you guessed.
Request:
`curl -X POST  http://localhost:8080/api/game/7e37b1ec-e40c-425a-9757-abf8a57ffb91/guess   -H 'Content-Type: application/json'  -d '{"guessNumber": 10}'`
Response:
```
{
    "message": "your guess is too low"
}
```
TODO: create a step by step guide to the previous setup.
2) Dockerize the application using Jib
the project will use [Jib](https://github.com/GoogleContainerTools/jib) for dockerization, hence no Dockerfile, 
Use `./mvnw compile jib:dockerBuild` to build to a local Docker daemon, this command should create a docker image with the name `guess-game`.
lets run the docker container locally: ` docker container run --name guess-game -p 8080:8080 -d guess-game`
if you try the calls on the previous step they should still work: 
Request:
`curl -X POST http://localhost:8080/api/games`
response:
```
{
    "id": "7e37b1ec-e40c-425a-9757-abf8a57ffb91",
    "guess": 461
}
```
7) lets make github actions run the test cases as part of the pipeline
the idea is to run all the test cases when we push to a branch, and to build and deploy when we merge into master
 - [pull request job](.github/workflows/pullrequest.yml)
 - [merge job](.github/workflows/merge.yml)
8) lets create a EKS cluster to run our docker container in a kubernetes managed cluster
9) lets automate the deploy on merge to master in github actions.
10) lets run some perfromance testing on it
11) lets add it to our github actions pipeline.
