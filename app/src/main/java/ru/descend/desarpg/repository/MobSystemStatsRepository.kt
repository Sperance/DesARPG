package ru.descend.desarpg.repository

import io.objectbox.BoxStore
import ru.descend.desarpg.model.MobSystemStats
import ru.descend.desarpg.model.RoomMobs

class MobSystemStatsRepository(boxStore: BoxStore) : BaseObjectBoxRepository<MobSystemStats>(boxStore.boxFor(MobSystemStats::class.java))