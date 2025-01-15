package ru.descend.desarpg.ui

import androidx.recyclerview.widget.LinearLayoutManager
import ru.descend.desarpg.databinding.FragmentMobInventoryBinding
import ru.descend.desarpg.log
import ru.descend.desarpg.logic.BaseItem
import ru.descend.desarpg.logic.EnumItemRarity
import ru.descend.desarpg.logic.EnumItemType
import ru.descend.desarpg.logic.StockSimpleStatsBool
import ru.descend.desarpg.room.datas.items.toRoomItem
import ru.descend.desarpg.ui.adapters.AdapterInventoryItems

class FragmentMobInventory : BaseFragment<FragmentMobInventoryBinding>(FragmentMobInventoryBinding::inflate) {

    private val adapterInventory = AdapterInventoryItems()

    override fun setUpViews() {
        binding.buttonAddSimpleItem.setOnClickListener {
            val newSimpleItem = BaseItem("Сфера запаса", EnumItemRarity.RARITY, EnumItemType.ITEM)
            viewModel.repoItems.insert(newSimpleItem.toRoomItem())
        }
        binding.buttonAddEquipItem.setOnClickListener {
            val newEquipItem = BaseItem("Меч Жнеца", EnumItemRarity.MAGIC, EnumItemType.EQUIPMENT)
            newEquipItem.description = "Меч который может Всё"
            newEquipItem.paramsBool.add(StockSimpleStatsBool.IsCanSell(false))
            viewModel.repoItems.insert(newEquipItem.toRoomItem())
        }
        binding.buttonClearItems.setOnClickListener {
            adapterInventory.clear()
            viewModel.updateMob()
        }
        initRecyclers()
        initObservers()
    }

    private fun initObservers() {
        viewModel.repoItems.allItems.observe(this) { data ->
            log("[OBSERVE ITEMS]: $data")
            viewModel.currentMob.inventoryItems.clearInventory()
            viewModel.currentMob.inventoryItems.setInventory(data)
            adapterInventory.onNewData(data)
        }
    }

    private fun initRecyclers() {
        binding.recyclerInventory.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerInventory.adapter = adapterInventory
    }
}