package com.adjarabet.game.user

import android.app.AlertDialog
import android.content.*
import android.os.Bundle
import android.os.IBinder
import android.view.KeyEvent
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.adjarabet.game.R
import com.adjarabet.game.bot.BotService
import com.adjarabet.game.domain.GameOverResult
import com.adjarabet.game.domain.PlayedWord
import com.adjarabet.game.domain.PlayerType
import com.adjarabet.game.domain.Word


class UserActivity : AppCompatActivity() {

    private lateinit var botService: BotService
    private var isBound = false

    private lateinit var viewModel: UserViewModel

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            botService = (service as BotService.BotBinder).service
            isBound = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            isBound = false
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[UserViewModel::class.java]

        val list = findViewById<RecyclerView>(R.id.list)
        val editText = findViewById<EditText>(R.id.editText)

        val wordsAdapter = WordsAdapter()
        list.adapter = wordsAdapter
        list.layoutManager = LinearLayoutManager(this)

        editText.setOnKeyListener { _, code: Int, event: KeyEvent ->
            if (event.action == KeyEvent.ACTION_DOWN && code == KeyEvent.KEYCODE_ENTER) {
                viewModel.add(
                    PlayedWord(
                        Word(editText.text.toString(), viewModel.currentWord().wordSize()),
                        PlayerType.User
                    )
                )
                editText.setText("")
                true
            } else
                false
        }

        viewModel.words.observe(this, wordsAdapter::submitList)
        viewModel.finishGame.observe(this) {
            editText.setText("")
            showFinishDialog(it)
        }
        viewModel.requestWordFromBot.observe(this) {
            if (isBound)
                viewModel.add(botService.newWord(viewModel.currentWord()))
        }
    }

    private fun showFinishDialog(result: GameOverResult) {
        AlertDialog.Builder(this)
            .setTitle(R.string.game_over_title)
            .setMessage(
                getString(
                    R.string.game_over_message,
                    result.playerType.toString(),
                    result.wrongWord,
                    result.correctSuggestion
                )
            )
            .setPositiveButton(android.R.string.ok) { dialogInterface, _: Int ->
                dialogInterface.cancel()
            }.show()
    }

    override fun onStart() {
        super.onStart()
        val botIntent = Intent(this, BotService::class.java)
        bindService(botIntent, connection, Context.BIND_AUTO_CREATE)
    }

    override fun onStop() {
        super.onStop()
        unbindService(connection)
    }

}