## Welcome to "Spring-boog-guess-game" with GitHub Actions

the main goal of this repo is to showcase a spring-boot-webflux application to run a guessing game, which will store information in a postgres database.
the application will run in an AWS EKS cluster, the deployment pipeline will use github actions for CI/CD.

Tech Stack:
- Spring-boot webflux
- Kotlin
- Klint
- AWS EKS
- Github Actions
- Test containers
- PostgreSQL
- flyway
- R2DBC
TODO: add urls to the projects.

1) create a sample application using spring boot initilizr
for the example I picked version 2.2 RC1 with kotlin and maven.
2) lets add some features to the empty spring boot repo, the api should:
- Create a game, which generate a random number and store's it in session.
Request:
`curl -X POST http://localhost:8080/api/game `
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
docker_push/build files. Use `./mvnw compile jib:dockerBuild` to build to a local Docker daemon.

3) Start the application locally
5) restart the app locally and lets test it
⋊> ~/Developer on Course-content ⨯ curl -X POST http://localhost:8080/api/game                                                                                                                16:12:45
{"id":"e2e8460f-d51c-4ffb-be8e-e139c22c62d9","guess":499}⏎

curl -X POST  http://localhost:8080/api/game/98040149-769d-44c6-9e98-430b9ef18f4a/guess   -H 'Content-Type: application/json'  -d '{"guessNumber": 10}'


6) lets create some unit test cases
7) lets make github actions run the test cases as part of the pipeline
8) lets create a EKS cluster to run our docker container in a kubernetes managed cluster
9) lets automate the deploy on merge to master in github actions.
10) lets run some perfromance testing on it
11) lets add it to our github actions pipeline.
