package ru.descend.desarpg.koin

import io.objectbox.BoxStore
import io.objectbox.kotlin.flow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.viewModel
import org.koin.core.scope.get
import org.koin.dsl.module
import ru.descend.desarpg.AppController
import ru.descend.desarpg.MainActivityVM
import ru.descend.desarpg.model.MyObjectBox
import ru.descend.desarpg.repository.BoxMobRepository
import ru.descend.desarpg.repository.CurrentMobRepository
import ru.descend.desarpg.repository.MobBattleStatsRepository
import ru.descend.desarpg.repository.MobSkillTreeStatsRepository
import ru.descend.desarpg.repository.MobSystemStatsRepository
import ru.descend.desarpg.ui.custom.SkillTreeView
import java.io.File

val objectBoxModule = module {
    single {
        BoxStore.deleteAllFiles(File(androidApplication().filesDir, TEST_DIRECTORY))
        MyObjectBox.builder().directory(File(androidApplication().filesDir, TEST_DIRECTORY)).androidContext(androidApplication()).build()
    }
    single { BoxMobRepository(get()) }
//    single { CurrentMobRepository(get()) }
//    single { MobBattleStatsRepository(get()) }
//    single { MobSystemStatsRepository(get()) }
//    single { MobSkillTreeStatsRepository(get()) }
}

val viewModelModule = module {
    viewModel { MainActivityVM(get()) }
}

private val TEST_DIRECTORY = "objectbox-example/test-db"