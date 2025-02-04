package ru.descend.desarpg

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import io.objectbox.BoxStore

class AppController : Application() {

    lateinit var curBoxStore: BoxStore

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

        curBoxStore = MyObjectBox.builder().androidContext(this).build()
        curBoxStore.removeAllObjects()
    }
}