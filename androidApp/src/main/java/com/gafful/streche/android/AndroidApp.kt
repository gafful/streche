package com.gafful.streche.android

import android.app.Application
import android.content.Context
import android.util.Log
import com.gafful.streche.initKoin
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

class AndroidApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin(
            module {
                single<Context> { this@AndroidApp }
                viewModel { MainActivityViewModel() }
                single {
                    { Log.i("Startup", "Hello from Android/Kotlin!") }
                }
            }
        )
    }
}
