package com.adjarabet.game.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.adjarabet.game.domain.*
import com.adjarabet.game.util.SingleLiveEvent

class UserViewModel : ViewModel() {

    private val sentence = Sentence(mutableListOf())

    private val _words = MutableLiveData<List<String>>()
    val words: LiveData<List<String>> get() = _words

    private val _requestWordFromBot = SingleLiveEvent<Unit>()
    val requestWordFromBot: LiveData<Unit> get() = _requestWordFromBot

    private val _finishGame = SingleLiveEvent<GameOverResult>()
    val finishGame: LiveData<GameOverResult> get() = _finishGame

    fun add(playedWord: PlayedWord) {
        try {
            sentence.add(playedWord)
            _words.value = sentence.asStrings(playedWord.playerType)
            if (playedWord.playerType == PlayerType.User)
                _requestWordFromBot.value = Unit
        } catch (ex: SentenceValidationException) {
            sentence.clear()
            _words.value = emptyList()
            _finishGame.value = ex.result
        }
    }

    fun currentWord() = sentence.asWord()

}