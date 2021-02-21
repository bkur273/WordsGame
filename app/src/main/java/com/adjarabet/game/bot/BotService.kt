package com.adjarabet.game.bot

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import com.adjarabet.game.domain.PlayedWord
import com.adjarabet.game.domain.Word
import java.util.*

class BotService : Service() {

    private val wordGenerator: Bot = ExcellentBot(Random())

    class BotBinder(val service: BotService) : Binder()

    private val botBinder = BotBinder(this)

    override fun onBind(intent: Intent?): IBinder {
        return botBinder
    }

    fun newWord(currentWord: Word): PlayedWord {
        return wordGenerator.generate(currentWord)
    }
}
