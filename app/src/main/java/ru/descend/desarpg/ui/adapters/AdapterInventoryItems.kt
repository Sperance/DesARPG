package ru.descend.desarpg.ui.adapters

import ru.descend.desarpg.databinding.AdapterItemInventoryMobBinding
import ru.descend.desarpg.room.datas.items.RoomItems

class AdapterInventoryItems : DesAdapter<RoomItems, AdapterItemInventoryMobBinding>(AdapterItemInventoryMobBinding::class) {
    override fun onBindItem(
        item: RoomItems,
        binding: AdapterItemInventoryMobBinding
    ) {
        binding.textItemName.setText(item.name)
        binding.textItemName.setTextPostfix(item.rarity.rarityName)
        binding.textItemName.setPostfixTextColor(item.rarity.rarityColor)
        binding.textItemDescription.setText(item.description)
        binding.textCount.setMaybeText(item.count)
    }

    override fun isAreContentsTheSame(itemNew: RoomItems, itemOld: RoomItems): Boolean {
        return false
    }
}