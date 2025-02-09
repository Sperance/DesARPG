package ru.descend.desarpg.koin

import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.viewModel
import org.koin.core.scope.get
import org.koin.dsl.module
import ru.descend.desarpg.AppController
import ru.descend.desarpg.MainActivityVM
import ru.descend.desarpg.model.MyObjectBox
import ru.descend.desarpg.repository.BoxMobRepository

val objectBoxModule = module {
    single {
        val box = MyObjectBox.builder().androidContext(androidApplication()).build()
        box.removeAllObjects()
        box
    }
    single { BoxMobRepository(get()) }
}

val viewModelModule = module {
    viewModel { MainActivityVM(get()) }
}