package ru.descend.desarpg

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import io.objectbox.BoxStore
import io.objectbox.android.ObjectBoxLiveData
import ru.descend.desarpg.logic.MyObjectBox

class AppController : Application() {

    lateinit var curBoxStore: BoxStore

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

        curBoxStore = MyObjectBox.builder().androidContext(this).build()
    }
}