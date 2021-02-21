package com.adjarabet.game.domain

import java.lang.Exception

class SentenceValidationException(
    val result: GameOverResult,
    cause: Throwable? = null
) : Exception("Game is over. Player[${result.playerType}] lost", cause)
