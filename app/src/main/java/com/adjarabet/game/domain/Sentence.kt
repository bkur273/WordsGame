package com.adjarabet.game.domain

class Sentence(private val list: MutableList<PlayedWord>) : Collection<PlayedWord> by list {

    fun add(element: PlayedWord): Boolean {
        if (list.size == 100)
            throw MadnessException()

        val word = try {
            element.word.get()
        } catch (ex: Word.InvalidWordFormatException) {
            throw SentenceValidationException(
                result = GameOverResult(
                    playerType = element.playerType,
                    wrongWord = ex.word,
                    correctSuggestion = correctSuggestion(),
                ),
                cause = ex
            )
        }

        return if (joinToString() == dropLastOrEmpty(word) && isUnique(lastOrEmpty(word)))
            list.add(element.copy(word = element.word.getLast()))
        else
            throw SentenceValidationException(
                GameOverResult(
                    playerType = element.playerType,
                    wrongWord = word,
                    correctSuggestion = correctSuggestion()
                )
            )
    }

    fun clear() {
        list.clear()
    }

    fun asStrings(playerType: PlayerType): List<String> {
        val newStringsList = mutableListOf<String>()
        val currentStringsList = try {
            list.map { it.word.get() }
        } catch (ex: Word.InvalidWordFormatException) {
            throw SentenceValidationException(
                result = GameOverResult(
                    playerType = playerType,
                    wrongWord = ex.word,
                    correctSuggestion = correctSuggestion(),
                ),
                cause = ex
            )
        }
        repeat(list.size) { index ->
            newStringsList.add(currentStringsList.take(index + 1).joinToString(" ") { it })
        }
        return newStringsList
    }

    fun asWord(): Word {
        return Word(
            joinToString(),
            joinToString().split(" ").size
        )
    }

    private fun joinToString() = joinToString(separator = " ") { it.word.get() }
    private fun isUnique(word: String) = all { it.word.get() != word }
    private fun dropLastOrEmpty(word: String): String {
        val words = word.split(" ")
        return if (words.size > 1) {
            words.dropLast(1).joinToString(" ") { it }
        } else {
            ""
        }
    }
    private fun lastOrEmpty(word: String): String {
        val words = word.split(" ")
        return if (words.size > 1) {
            words.last()
        } else {
            ""
        }
    }

    private fun correctSuggestion(): String {
        val joinToString = joinToString()
        return if (joinToString.isNotEmpty())
            "$joinToString $CORRECT_SUGGESTION"
        else
            CORRECT_SUGGESTION
    }

    companion object {
        private const val CORRECT_SUGGESTION = "abc"
    }

}