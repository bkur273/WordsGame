package com.adjarabet.game.domain

data class GameOverResult(
    val playerType: PlayerType,
    val wrongWord: String,
    val correctSuggestion: String,
)