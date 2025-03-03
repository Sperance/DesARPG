package ru.descend.desarpg.ui

import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import ru.descend.desarpg.databinding.FragmentWorkMiningBinding
import ru.descend.desarpg.logic.randomizers.randInt
import ru.descend.desarpg.model.EnumWorkStatsType
import ru.descend.desarpg.model.WorkLeveledItem
import ru.descend.desarpg.model.WorkStatsProp
import ru.descend.desarpg.to0Text
import ru.descend.desarpg.ui.adapters.AdapterWorkItems

class FragmentWorkMining : BaseFragment<FragmentWorkMiningBinding>(FragmentWorkMiningBinding::inflate) {
    private val args: FragmentWorkMiningArgs by navArgs()
    private lateinit var stat: WorkStatsProp
    private val adapter = AdapterWorkItems()
    private var lastItemSelected = -1
    private var currentItemSelected = 101

    override fun setUpViews() {
        stat = viewModel.getWorkStats().getStockStat(args.stat)
        binding.recyclerWorkTypes.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerWorkTypes.adapter = adapter
        adapter.onClicked = { it: WorkLeveledItem, pos: Int ->
            if (lastItemSelected != currentItemSelected) {
                if (currentItemSelected != -1) lastItemSelected = currentItemSelected
                currentItemSelected = pos

                stat.selectedObject = it.codeIndex
                stat.saveToBox()

                // Обновляем данные модели
                adapter.getItems().forEachIndexed { index, workItem ->
                    workItem.isSelected = index == currentItemSelected
                }

                // Уведомляем адаптер о необходимости обновления
                if (lastItemSelected != -1) adapter.notifyItemChanged(lastItemSelected)
                if (currentItemSelected != -1) adapter.notifyItemChanged(currentItemSelected)

                binding.workCurrentObject.getProgressBar().progress = 0

                reloadCard()

                Snackbar.make(requireView(), "Пример текта: ${it.itemName}", Snackbar.LENGTH_SHORT).show()
            }
        }

        reloadViews()

        binding.workCurrentObject.setOnClickListener {
            val addedProgress = randInt(EnumWorkStatsType.getSelectedObject(stat)!!.expForTick)
            binding.workCurrentObject.getProgressBar().progress += addedProgress
            if (binding.workCurrentObject.getProgressBar().progress >= stat.needNextLevelExperience.toInt()) {
                binding.workCurrentObject.getProgressBar().progress = 0

                val newItem = stat.getMiningItem()
                viewModel.getInventory().addItem(newItem)

                stat.minedTick(newItem)
                stat.saveToBox()

                reloadViews()
            }
            binding.workCurrentObject.getTextProgress().setTextPrefix(binding.workCurrentObject.getProgressBar().progress.toString())
            binding.workCurrentObject.getTextProgress().setTextPostfix(stat.needNextLevelExperience.to0Text())
        }
    }

    private fun reloadViews() {
        binding.textLevelMining.setText(stat.globalLevelWork)
        binding.textLevelMiningAllCount.setText(stat.countGainedItems.to0Text())
        binding.textWorkName.setText(stat.getTypeEnum()?.statName)
        binding.textExperienceCurrent.setText(stat.currentExperience.to0Text())
        binding.textExperienceNeed.setText(stat.needNextLevelExperience.to0Text())
        adapter.onNewData(stat.getActiveLevels())
        reloadCard()
    }

    private fun reloadCard() {
        val selectedStat = EnumWorkStatsType.getSelectedObject(stat)
        binding.workCurrentObject.setItemName(selectedStat?.itemName)
        binding.workCurrentObject.setExpText(selectedStat?.expForMined.toString())
        binding.workCurrentObject.getProgressBar().max = stat.needNextLevelExperience.toInt()
        binding.workCurrentObject.getTextProgress().setTextPrefix(binding.workCurrentObject.getProgressBar().progress.toString())
        binding.workCurrentObject.getTextProgress().setTextPostfix(stat.needNextLevelExperience.to0Text())
    }

    override fun onResume() {
        super.onResume()
        adapter.getItems().forEach {
            it.isSelected = false
        }
        EnumWorkStatsType.getSelectedObject(stat)?.let { saved ->
            saved.isSelected = true
            lastItemSelected = adapter.getItemIndex(saved)
            adapter.notifyItemChanged(lastItemSelected)
        }
    }
}