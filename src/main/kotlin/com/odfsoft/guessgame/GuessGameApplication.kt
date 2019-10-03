package com.odfsoft.guessgame

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class GuessGameApplication

fun main(args: Array<String>) {
    runApplication<GuessGameApplication>(*args)
}
