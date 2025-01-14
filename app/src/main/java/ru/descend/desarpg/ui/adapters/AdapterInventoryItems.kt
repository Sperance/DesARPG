package ru.descend.desarpg.ui.adapters

import ru.descend.desarpg.databinding.AdapterItemInventoryMobBinding
import ru.descend.desarpg.logic.BaseItem

class AdapterInventoryItems : DesAdapter<BaseItem, AdapterItemInventoryMobBinding>(AdapterItemInventoryMobBinding::class) {
    override fun onBindItem(
        item: BaseItem,
        binding: AdapterItemInventoryMobBinding
    ) {
        binding.textItemName.setText(item.name)
        binding.textItemName.setTextPostfix(item.rarity.rarityName)
        binding.textItemName.setPostfixTextColor(item.rarity.rarityColor)
        binding.textItemDescription.setText(item.description)
        binding.textCount.setMaybeText(item.count)
    }

    override fun isAreContentsTheSame(itemNew: BaseItem, itemOld: BaseItem): Boolean? {
        return false
    }
}