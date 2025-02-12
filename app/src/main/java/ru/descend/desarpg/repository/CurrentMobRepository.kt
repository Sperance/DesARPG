package ru.descend.desarpg.repository

import io.objectbox.BoxStore
import ru.descend.desarpg.model.RoomMobs

class CurrentMobRepository(boxStore: BoxStore) : BaseObjectBoxRepository<RoomMobs>(boxStore.boxFor(RoomMobs::class.java))