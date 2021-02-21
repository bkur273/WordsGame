package com.adjarabet.game.domain

import java.lang.Exception

class Word(
    private val word: String,
    private val previousWordSize: Int
) {

    fun composed(newWord: String): Word {
        return Word("$word $newWord", word.split(" ").size)
    }

    fun wordSize() = word.split(" ").size

    fun get(): String {
        return if (isValid() || word.isBlank())
            throw InvalidWordFormatException(word)
        else
            word
    }

    fun getLast(): Word {
        return if (isValid() || word.isBlank())
            throw InvalidWordFormatException(word)
        else
            Word(word.split(" ").last(), previousWordSize)
    }

    private fun isValid(): Boolean {
        val currentWordsSize = word.split(" ").size
        return currentWordsSize > previousWordSize + 1
    }

    override fun toString(): String {
        return if (word.isBlank()) "[Empty or Blank]" else word
    }

    class InvalidWordFormatException(
        val word: String
    ) : Exception("Invalid Word Detected. Word[$word]")

}
