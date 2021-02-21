package com.adjarabet.game.bot

import com.adjarabet.game.domain.Word
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.util.*

class ExcellentBotTest {

    private val fakeRandom = object : Random() {
        val realRandom = Random()
        override fun nextInt(bound: Int): Int {
            return if (bound == 101) 100 else realRandom.nextInt(bound)
        }
    }

    private val excellentBot = ExcellentBot(fakeRandom)

    @Test
    fun test_excellentBotFailureState() {
        Assertions.assertThrows(Word.InvalidWordFormatException::class.java) {
            val generatedWord = excellentBot.generate(Word("abc", 0))
            generatedWord.word.get()
        }
    }

}
