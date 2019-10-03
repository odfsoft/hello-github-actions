package com.odfsoft.guessgame.domain

import java.util.UUID
import kotlin.random.Random

const val TOO_LOW = "your guess is too low"
const val TOO_HIGH = "your guess is too high"
const val RIGHT_GUESS = "Congratulations!! You have guessed"

data class Game(
    val id: UUID = UUID.randomUUID(),
    val guess: Int = Random.nextInt(500)
) {
    fun tryGuess(guessNumber: Int) =
        when {
            guessNumber > guess -> TOO_HIGH
            guessNumber < guess -> TOO_LOW
            else -> RIGHT_GUESS
        }
}

data class Guess(
    val guessNumber: Int
)
