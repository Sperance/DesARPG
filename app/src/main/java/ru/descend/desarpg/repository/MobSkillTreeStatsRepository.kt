package ru.descend.desarpg.repository

import io.objectbox.BoxStore
import ru.descend.desarpg.model.MobBattleStats
import ru.descend.desarpg.model.MobSkillTreeStats

class MobSkillTreeStatsRepository(boxStore: BoxStore) : BaseObjectBoxRepository<MobSkillTreeStats>(boxStore.boxFor(MobSkillTreeStats::class.java))