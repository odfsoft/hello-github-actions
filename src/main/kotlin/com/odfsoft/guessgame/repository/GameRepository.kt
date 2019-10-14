package com.odfsoft.guessgame.repository

import com.odfsoft.guessgame.domain.Game
import org.springframework.data.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Component
import org.springframework.transaction.reactive.TransactionalOperator
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.UUID

@Component
class GameRepository(private val client: DatabaseClient, private val to: TransactionalOperator) {

    fun save(game: Game) =
        client
            .insert()
            .into(Game::class.java).table("game")
            .using(game)
            .then()

    fun findByGameId(gameId: UUID): Mono<Game> =
        client.execute("SELECT id, guess FROM game WHERE game_id = \$1")
            .bind(0, gameId)
            .`as`(Game::class.java)
            .fetch()
            .first()

    fun findAll(): Flux<Game> {
        return client
            .execute("select id, guess from game")
            .`as`(Game::class.java)
            .fetch()
            .all()
            .`as`(to::transactional)
            .onErrorContinue{ throwable, o -> System.out.println("value ignored $throwable $o") }
            .log()
    }


}
