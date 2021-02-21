package com.adjarabet.game.bot

import com.adjarabet.game.domain.PlayedWord
import com.adjarabet.game.domain.PlayerType
import com.adjarabet.game.domain.Word
import java.util.*

class ExcellentBot(private val random: Random) : Bot {

    override fun generate(word: Word): PlayedWord {
        return if (isUnlucky())
            PlayedWord(word = word.composed(randomInvalidString(randomLen())), PlayerType.Bot)
        else
            PlayedWord(word = word.composed(randomValidString(randomLen())), PlayerType.Bot)
    }

    private fun isUnlucky(): Boolean {
        val random = random.nextInt(101)
        return random > 97
    }

    private fun randomLen() = random.nextInt(10)

    private fun randomInvalidString(len: Int): String {
        return "${randomValidString(len)} ${randomValidString(len)}"
    }

    private fun randomValidString(len: Int): String {
        val sb = StringBuilder(len)
        for (i in 0 until len)
            sb.append(WORDS_SEQUENCE[random.nextInt(WORDS_SEQUENCE.length)])
        return sb.toString()
    }

    companion object {
        const val WORDS_SEQUENCE = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"
    }
}