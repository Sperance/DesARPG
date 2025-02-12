package ru.descend.desarpg

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import io.objectbox.BoxStore
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.descend.desarpg.koin.objectBoxModule
import ru.descend.desarpg.koin.viewModelModule

lateinit var applicationBox: BoxStore

class AppController : Application() {
    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        startKoin {
            androidContext(this@AppController)
            modules(objectBoxModule, viewModelModule)
        }
    }
}