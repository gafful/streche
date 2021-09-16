package com.gafful.streche.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gafful.streche.Greeting
import android.widget.TextView
import com.gafful.streche.DatabaseDriverFactory
import com.gafful.streche.ScoreRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

fun greet(): String {
    return Greeting().greeting()
}

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tv: TextView = findViewById(R.id.text_view)
        tv.text = greet()

        val repository = ScoreRepository(DatabaseDriverFactory(this))

        runBlocking {
            withContext(Dispatchers.IO) {
                repository.add(true)
                val launches = repository.getLaunches(true)
                launches.forEach {
                    println(it)
                    println(it.missionName)
                }
            }
        }
    }
}
