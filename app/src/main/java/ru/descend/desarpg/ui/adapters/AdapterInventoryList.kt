package ru.descend.desarpg.ui.adapters

import ru.descend.desarpg.databinding.AdapterItemInventoryLineBinding
import ru.descend.desarpg.model.BaseItem

class AdapterInventoryList : DesAdapter<BaseItem, AdapterItemInventoryLineBinding>(AdapterItemInventoryLineBinding::class) {
    override fun onBindItem(item: BaseItem, binding: AdapterItemInventoryLineBinding) {
        binding.itemName.setText(item.name)
        binding.itemName.setTextPostfix(if (item.count > 1) item.count.toString() else "")
        binding.itemDescription.setText(item.description)
    }
}