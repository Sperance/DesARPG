package ru.descend.desarpg.ui

import androidx.navigation.fragment.navArgs
import ru.descend.desarpg.databinding.FragmentWorkMiningBinding
import ru.descend.desarpg.logic.randomizers.randInt
import ru.descend.desarpg.model.WorkStatsProp
import ru.descend.desarpg.to0Text

class FragmentWorkMining : BaseFragment<FragmentWorkMiningBinding>(FragmentWorkMiningBinding::inflate) {

    private val args: FragmentWorkMiningArgs by navArgs()
    private lateinit var stat: WorkStatsProp

    override fun setUpViews() {
        stat = viewModel.getWorkStats().getStockStat(args.stat)

        reloadViews()

        binding.workCurrentObject.setOnClickListener {
            binding.workCurrentObject.addProgressBarProgress(randInt(stat.getMiningRange()))
            if (binding.workCurrentObject.getProgressBarProgress() >= 100) {
                binding.workCurrentObject.setProgressBarProgress(0)

                val newItem = stat.getMiningItem()
                viewModel.getInventory().addItem(newItem)

                stat.minedTick(newItem)
                stat.saveToBox()

                reloadViews()
            }
        }
    }

    private fun reloadViews() {
        binding.textLevelMining.setText(stat.globalLevelWork)
        binding.textLevelMiningAllCount.setText(stat.countGainedItems.to0Text())
        binding.textWorkName.setText(stat.getTypeEnum()?.statName)
        binding.textExperienceCurrent.setText(stat.currentExperience.to0Text())
        binding.textExperienceNeed.setText(stat.needNextLevelExperience.to0Text())
    }
}