@file:Suppress("unused")

package com.adjarabet.game.bot

import com.adjarabet.game.domain.PlayedWord
import com.adjarabet.game.domain.PlayerType
import com.adjarabet.game.domain.Word

class LooserBot : Bot {
    override fun generate(word: Word): PlayedWord {
        return PlayedWord(Word("ab ac dac ", 0), PlayerType.Bot)
    }
}