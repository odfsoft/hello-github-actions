---
layout: post
title:  "Spring boot guess game with Github Actions"
author: Folger
categories: [ Spring boot, tutorial ]
image: assets/images/spring-boot.png
description: "Tutorial about how to setup github action to run automated test using github actions and push a docker image to a public docker hub."
featured: true
hidden: true
---

Tutorial about how to setup github action to run automated test using github actions and push a docker image to a public docker hub.

## Welcome to "Spring-boog-guess-game" with GitHub Actions

the main goal of this repo is to create a guessing game app using spring-boot-webflux, which will store the game information guava cache,
we will run integration test cases automatically when a new PR is open and once merge to master the docker image should be push to docker hub.

# Prerequisities
- Java 8 / maven etc
- Docker
        
# Tech Stack:
- Spring-boot webflux
- Kotlin
- [Klint](https://ktlint.github.io/)
- Github Actions

# Steps
### Create a Spring boot sample application
using [spring boot initilizr](https://start.spring.io/):  
for this app I used version 2.2 RC1 with kotlin and maven.
- lets add some features to the empty spring boot repo, the api should:
```kotlin
some sample code here
```
- Create a game, which generate a random number and store's it in session.
Request:
```
curl -X POST http://localhost:8080/api/games
```
response:
```
{
    "id": "7e37b1ec-e40c-425a-9757-abf8a57ffb91",
    "guess": 461
}
```
- Get a game with the id returned in the previous request.
Request:
```
curl -X GET http://localhost:8080/api/game/7e37b1ec-e40c-425a-9757-abf8a57ffb91
```
Response:
```
{
    "id": "7e37b1ec-e40c-425a-9757-abf8a57ffb91",
    "guess": 461
}
```
- receive guesses, the api will response with a 200 and message text either saying too low, too high, congratulations you guessed.
Request:
```
curl -X POST  http://localhost:8080/api/game/7e37b1ec-e40c-425a-9757-abf8a57ffb91/guess   -H 'Content-Type: application/json'  -d '{"guessNumber": 10}'
```
Response:
```
{
    "message": "your guess is too low"
}
```
TODO: create a step by step guide to the previous setup.
## Dockerize the application using Jib
the project will use [Jib](https://github.com/GoogleContainerTools/jib) for dockerization, hence no Dockerfile, 
Use `./mvnw compile jib:dockerBuild` to build to a local Docker daemon, this command should create a docker image with the name `guess-game`.
lets run the docker container locally: ` docker container run --name guess-game -p 8080:8080 -d guess-game`
if you try the calls on the previous step they should still work: 
Request:
```
curl -X POST http://localhost:8080/api/games
```
response:
```
{
    "id": "7e37b1ec-e40c-425a-9757-abf8a57ffb91",
    "guess": 461
}
```
### Automate the test phase using github actions
 lets make github actions run the test cases as part of the pipeline
the idea is to run all the test cases when we push to a branch, and to build and deploy when we merge into master
 - [pull request job](https://github.com/odfsoft/spring-boot-guess-game/blob/master/.github/workflows/pullrequest.yml)
 
### Automate the Docker push to a docker hub using github actions
 - [merge job](https://github.com/odfsoft/spring-boot-guess-game/blob/master/.github/workflows/merge.yml)
