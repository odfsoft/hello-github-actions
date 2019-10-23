package com.odfsoft.guessgame.repository

import com.odfsoft.guessgame.domain.Game
import com.odfsoft.guessgame.util.logger
import io.r2dbc.spi.Row
import io.r2dbc.spi.RowMetadata
import org.springframework.data.r2dbc.core.DatabaseClient
import org.springframework.data.r2dbc.core.asType
import org.springframework.data.r2dbc.core.into
import org.springframework.stereotype.Component
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
        client.execute("SELECT id, guess FROM game WHERE id = \$1")
            .bind(0, gameId).asType<Game>()
            .fetch()
            .first()
            .switchIfEmpty(Mono.error(RuntimeException("Game not found!")))

    fun findAll(): Flux<Game> {
        log.info("starting something")
        val switchIfEmpty = client
                .execute("select id, guess FROM game")
                .map { row, rowMetadata -> ConvertToGame(row, rowMetadata) }
                .all()
                .onErrorContinue { ex, value ->
                    log.error("Unexpected error while fetching games ${ex.cause} - $value", ex.stackTrace)
                }
        return switchIfEmpty
    }

    private fun ConvertToGame(row: Row, rowMetadata: RowMetadata): Game {
        log.info("row $row, $rowMetadata")
        return Game(UUID.fromString(row.get("id", String::class.java)),
                row.get("guess", Integer::class.java).toInt())
    }

}
