package com.odfsoft.guessgame.rest

import com.odfsoft.guessgame.domain.Guess
import com.odfsoft.guessgame.service.GameService
import org.springframework.http.HttpHeaders
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import java.util.UUID
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import java.lang.IllegalArgumentException

@RestController
@RequestMapping("/api/game")
class GameController(val gameService: GameService) {

    @PostMapping
    fun createGame() = gameService.createGame()

    @GetMapping("/{id}")
    fun getGame(@PathVariable("id") gameId: UUID) = gameService.getGame(gameId)

    @PostMapping("/{id}/guess")
    fun guessNumber(
        @PathVariable("id") gameId: UUID,
        @RequestBody guess: Guess
    ) =
        gameService.getGame(gameId)
            .map { it.tryGuess(guess.guessNumber) }
            .map { GuessResponse(it) }

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalAccessException(exception: IllegalArgumentException): ResponseEntity<Mono<BadResponse>> {
        val headers = HttpHeaders()
        return ResponseEntity(Mono.just(BadResponse(exception.message ?: "Bad Request")), headers, HttpStatus.BAD_REQUEST)
    }

    data class BadResponse(val message: String)

    data class GuessResponse(val message: String)
}
