package com.odfsoft.guessgame.repository

import com.odfsoft.guessgame.domain.Game
import com.odfsoft.guessgame.util.logger
import org.springframework.data.r2dbc.core.DatabaseClient
import org.springframework.data.r2dbc.core.asType
import org.springframework.data.r2dbc.core.into
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.UUID

@Component
class GameRepository(private val client: DatabaseClient) {

    var log = logger()

    fun save(game: Game) =
        client
            .insert()
            .into<Game>().table("game")
            .using(game)
            .then()

    fun findByGameId(gameId: UUID): Mono<Game> =
        client.execute("SELECT id, guess FROM game WHERE game_id = \$1")
            .bind(0, gameId).asType<Game>()
            .fetch()
            .first()

    @Transactional
    fun findAll(): Flux<Game> {
        return client
                .select()
                .from(Game::class.java)
                .fetch()
                .all()
                .onErrorContinue {
                    ex, value -> log.error("Unexpected error while fetching games", ex.stackTrace)
                }
                .log()
    }

}
