package ru.descend.desarpg

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import io.objectbox.BoxStore
import ru.descend.desarpg.model.MyObjectBox
import java.io.File

class AppController : Application() {

    lateinit var curBoxStore: BoxStore

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

        curBoxStore = MyObjectBox.builder().baseDirectory(File(filesDir, "db")) .androidContext(this).build()
        curBoxStore.removeAllObjects()
        log("[DB: ${filesDir.absolutePath}]")
    }
}