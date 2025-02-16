package ru.descend.desarpg.koin

import io.objectbox.BoxStore
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ru.descend.desarpg.MainActivityVM
import ru.descend.desarpg.MyObjectBox
import ru.descend.desarpg.repository.BoxMobRepository
import java.io.File

val objectBoxModule = module {
    single {
        BoxStore.deleteAllFiles(File(androidApplication().filesDir, TEST_DIRECTORY))
        MyObjectBox.builder().directory(File(androidApplication().filesDir, TEST_DIRECTORY)).androidContext(androidApplication()).build()
    }
    single { BoxMobRepository(get()) }
}

val viewModelModule = module {
    viewModel { MainActivityVM(get()) }
}

private val TEST_DIRECTORY = "objectbox-example/test-db"