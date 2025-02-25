package ru.descend.desarpg.ui

import ru.descend.desarpg.databinding.FragmentWorkMiningBinding
import ru.descend.desarpg.logic.randomizers.randInt
import ru.descend.desarpg.logic.randomizers.randLong
import ru.descend.desarpg.model.BaseItem
import ru.descend.desarpg.model.EnumItemCategory
import ru.descend.desarpg.model.EnumItemRarity
import ru.descend.desarpg.model.EnumSystemStatsType
import ru.descend.desarpg.to0Text
import ru.descend.desarpg.ui.custom.ViewWorkObject

class FragmentWorkMining : BaseFragment<FragmentWorkMiningBinding>(FragmentWorkMiningBinding::inflate) {
    override fun setUpViews() {
        reloadViews()
        binding.workCurrentObject.setLevelText("1")
        binding.workCurrentObject.setOnClickListener {
            binding.workCurrentObject.addProgressBarProgress(randInt(3, 10))
            if (binding.workCurrentObject.getProgressBarProgress() >= 100) {
                binding.workCurrentObject.setProgressBarProgress(0)

                val item = BaseItem(
                    name = "Камень",
                    description = "Простая добыча",
                    count = randLong(1, 2),
                    category = EnumItemCategory.RESOURCE.name,
                    rarity = EnumItemRarity.COMMON.name
                )
                viewModel.getInventory().addItem(item)

                viewModel.getCurrentSystemStats().getStockStat(EnumSystemStatsType.COUNT_MINING_ALL_ITEMS).add(item.count).saveToBox()
                reloadViews()
            }
        }
    }

    private fun reloadViews() {
        binding.textLevelMiningAllCount.setText(viewModel.getCurrentSystemStats().getStockStat(EnumSystemStatsType.COUNT_MINING_ALL_ITEMS).valueProp.to0Text())
    }
}