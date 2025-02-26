package ru.descend.desarpg.koin

import io.objectbox.BoxStore
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ru.descend.desarpg.AppController
import ru.descend.desarpg.MainActivityVM
import ru.descend.desarpg.applicationBox
import ru.descend.desarpg.model.MyObjectBox
import ru.descend.desarpg.repository.BoxMobRepository
import java.io.File

val objectBoxModule = module {
    single {
        val application = androidApplication() as AppController
        BoxStore.deleteAllFiles(File(application.filesDir, TEST_DIRECTORY))
        val box = MyObjectBox.builder().directory(File(application.filesDir, TEST_DIRECTORY)).androidContext(application).build()
        applicationBox = box
        box
    }
    single { BoxMobRepository(get()) }
}

val viewModelModule = module {
    viewModel { MainActivityVM(get()) }
}

private val TEST_DIRECTORY = "objectbox-example/test-db"