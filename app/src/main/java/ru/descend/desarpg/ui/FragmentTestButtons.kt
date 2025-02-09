package ru.descend.desarpg.ui

import ru.descend.desarpg.databinding.FragmentTestButtonsBinding
import ru.descend.desarpg.log
import ru.descend.desarpg.model.EnumPropsType
import ru.descend.desarpg.model.MobBattleStats
import ru.descend.desarpg.model.MobSystemStats
import ru.descend.desarpg.model.StockStatsProp
import ru.descend.desarpg.model.SystemStatsProp
import ru.descend.desarpg.model.RoomMobs

class FragmentTestButtons : BaseFragment<FragmentTestButtonsBinding>(FragmentTestButtonsBinding::inflate) {

    override fun setUpViews() {
        binding.buttonShowAllStats.setOnClickListener {
            val mobBox = viewModel.currentBox.boxFor(RoomMobs::class.java)
            log("mobBox: \n")
            mobBox.all.forEach {
                log("item: $it")
            }
            val battleStatsBox = viewModel.currentBox.boxFor(MobBattleStats::class.java)
            log("battleStatsBox: \n")
            battleStatsBox.all.forEach {
                log("item: $it")
            }
            val statsPropBox = viewModel.currentBox.boxFor(StockStatsProp::class.java)
            log("statsPropBox: \n")
            statsPropBox.all.forEach {
                log("item: $it")
            }
            val systemStatsBox = viewModel.currentBox.boxFor(MobSystemStats::class.java)
            log("systemStatsBox: \n")
            systemStatsBox.all.forEach {
                log("item: $it")
            }
            val systemPropBox = viewModel.currentBox.boxFor(SystemStatsProp::class.java)
            log("systemPropBox: \n")
            systemPropBox.all.forEach {
                log("item: $it")
            }
        }
        binding.buttonClearMobs.setOnClickListener {
            val stat = viewModel.currentStats.getStat(EnumPropsType.HEALTH)
            stat.addValue(150)
            viewModel.saveToDB()
            println("H: $stat")
        }
    }
}