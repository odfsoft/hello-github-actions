package com.odfsoft.guessgame.domain

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class GameTest {

    @Test
    fun `should give too low when guess is too small`() {
        val game = Game(guess = 500)
        assertEquals(TOO_HIGH, game.tryGuess(501))
    }

    @Test
    fun `should give too high when guess is too big`() {
        val game = Game(guess = 500)
        assertEquals(TOO_LOW, game.tryGuess(499))
    }

    @Test
    fun `should give congrats when guess is correct`() {
        val game = Game(guess = 500)
        assertEquals(RIGHT_GUESS, game.tryGuess(500))
    }
}
