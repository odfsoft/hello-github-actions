package com.odfsoft.guessgame.service

import com.odfsoft.guessgame.domain.Game
import com.odfsoft.guessgame.repository.GameRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.UUID

@Service
class GameService(var repository: GameRepository) {

    fun createGame(): Mono<Game> {
        val game = Game()
        return repository.save(game).then(Mono.just(game))
    }

    fun getGame(gameId: UUID): Mono<Game> = repository.findByGameId(gameId)

    fun getAllGames(): Flux<Game> = repository.findAll()

}
