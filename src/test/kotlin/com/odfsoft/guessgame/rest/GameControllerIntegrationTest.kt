package com.odfsoft.guessgame.rest

import com.odfsoft.guessgame.domain.Guess
import com.odfsoft.guessgame.domain.RIGHT_GUESS
import com.odfsoft.guessgame.domain.TOO_LOW
import com.odfsoft.guessgame.service.GameService
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.BodyInserters
import java.util.UUID

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Tag("integration-test")
class GameControllerIntegrationTest {

    @Autowired
    protected lateinit var webTestClient: WebTestClient

    @Autowired
    protected lateinit var gameService: GameService

    @Test
    fun shouldCreateGame() {
        baseRequest()
            .post()
            .uri("/api/game")
            .exchange()
            .expectStatus().isOk
            .expectBody()
                .jsonPath("$.id").isNotEmpty
                .jsonPath("$.guess").isNotEmpty
    }

    @Test
    fun shouldGetExistingGame() {
        val game = gameService.createGame().block()!!

        baseRequest()
            .get()
            .uri("/api/game/${game.id}")
            .exchange()
            .expectStatus().isOk
            .expectBody()
                .jsonPath("$.id").isEqualTo(game.id.toString())
                .jsonPath("$.guess").isEqualTo(game.guess)
    }

    @Test
    fun shouldFailIfGameDoesNotExists() {
        baseRequest()
            .get()
            .uri("/api/game/${UUID.randomUUID()}/")
            .exchange()
            .expectStatus()
            .isBadRequest
    }

    @Test
    fun `Guess should return lower when guessNumber is smaller`() {
        val game = gameService.createGame().block()!!

        baseRequest()
            .post()
            .uri("/api/game/${game.id}/guess")
            .body(BodyInserters.fromValue(Guess(game.guess - 1)))
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.message").isEqualTo(TOO_LOW)
    }

    @Test
    fun `Guess should return Contrats message when the guesssNumber is right`() {
        val game = gameService.createGame().block()!!

        baseRequest()
            .post()
            .uri("/api/game/${game.id}/guess")
            .body(BodyInserters.fromValue(Guess(game.guess)))
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.message").isEqualTo(RIGHT_GUESS)
    }

    protected fun baseRequest(): WebTestClient =
        webTestClient
            .mutate()
            .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON.toString())
            .build()
}
