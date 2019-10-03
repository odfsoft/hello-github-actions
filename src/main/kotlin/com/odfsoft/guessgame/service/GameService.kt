package com.odfsoft.guessgame.service

import com.google.common.cache.CacheBuilder
import com.odfsoft.guessgame.domain.Game
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.util.UUID
import java.util.concurrent.TimeUnit

@Service
class GameService {
    val gameCache = CacheBuilder.newBuilder()
        .initialCapacity(100)
        .maximumSize(300)
        .expireAfterAccess(5, TimeUnit.MINUTES)
        .build<UUID, Game>()

    fun createGame(): Mono<Game> {
        val game = Game()
        gameCache.put(game.id, game)
        return Mono.just(game)
    }

    fun getGame(gameId: UUID): Mono<Game> =
        Mono.just(gameCache.getIfPresent(gameId) ?: throw IllegalArgumentException("Game does not exists or expired."))
}
