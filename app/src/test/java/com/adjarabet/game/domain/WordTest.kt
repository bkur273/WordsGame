package com.adjarabet.game.domain

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class WordTest {

    @ParameterizedTest
    @MethodSource("validSource")
    fun should_beValidWords(candidate: Word) {
        candidate.get()
    }

    @ParameterizedTest
    @MethodSource("invalidSource")
    fun should_beInvalidWords(candidate: Word) {
        Assertions.assertThrows(Word.InvalidWordFormatException::class.java) {
            candidate.get()
        }
    }

    @Suppress("unused")
    companion object {
        @JvmStatic
        fun validSource(): Stream<Word> = Stream.of(
            Word("tree sa qa", 2),
            Word("mmm", 0),
            Word("a\\/n", 0)
        )

        @JvmStatic
        fun invalidSource(): Stream<Word> = Stream.of(
            Word("tree sa qa", 1),
            Word("", 0),
            Word("     ", 0),
            Word("tree sa qa ", 2),
        )
    }

}