package com.adjarabet.game.bot

import com.adjarabet.game.domain.PlayedWord
import com.adjarabet.game.domain.Word

interface Bot {
    fun generate(word: Word): PlayedWord
}
