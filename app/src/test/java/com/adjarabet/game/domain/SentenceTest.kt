package com.adjarabet.game.domain

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class SentenceTest {

    private val sentence = Sentence(mutableListOf())

    @Test
    fun should_addThreeWords_then_eqsToList() {
        sentence.add(PlayedWord(Word("ab", 0), PlayerType.User))
        sentence.add(PlayedWord(Word("ab ac", 1), PlayerType.Bot))
        sentence.add(PlayedWord(Word("ab ac ad", 2), PlayerType.User))

        val expected = listOf("ab", "ab ac", "ab ac ad")

        Assertions.assertEquals(expected, sentence.asStrings(PlayerType.User))
    }

    @Test
    fun should_addThreeWords_then_eqsToString() {
        sentence.add(PlayedWord(Word("ab", 0), PlayerType.User))
        sentence.add(PlayedWord(Word("ab ac", 1), PlayerType.Bot))
        sentence.add(PlayedWord(Word("ab ac ad", 2), PlayerType.User))

        val expected = "ab ac ad"

        Assertions.assertEquals(expected, sentence.asWord().get())
    }

    @Test
    fun should_throwSentenceException_when_duplicatingWordsInSentence() {
        Assertions.assertThrows(SentenceValidationException::class.java) {
            sentence.add(PlayedWord(Word("ab", 0), PlayerType.User))
            sentence.add(PlayedWord(Word("ab ab", 2), PlayerType.Bot))
        }
    }


}