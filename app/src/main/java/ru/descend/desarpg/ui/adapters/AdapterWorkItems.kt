package ru.descend.desarpg.ui.adapters

import androidx.core.content.ContextCompat
import ru.descend.desarpg.databinding.AdapterItemWorkListBinding
import ru.descend.desarpg.model.WorkLeveledItem

class AdapterWorkItems : DesAdapter<WorkLeveledItem, AdapterItemWorkListBinding>(AdapterItemWorkListBinding::class) {

    override fun onBindItem(item: WorkLeveledItem, binding: AdapterItemWorkListBinding, position: Int) {
        binding.textName.setText(item.itemName)
        binding.textDescription.setTextPrefix("Скорость добычи: ")
        binding.textDescription.setText(item.expForTick)
        binding.textDescription2.setTextPrefix("Опыта добычи: ")
        binding.textDescription2.setText(item.expForMined)

        binding.cardView.strokeColor = if (item.isSelected) {
            ContextCompat.getColor(binding.root.context, android.R.color.holo_green_dark)
        } else {
            ContextCompat.getColor(binding.root.context, android.R.color.background_dark)
        }
    }
}