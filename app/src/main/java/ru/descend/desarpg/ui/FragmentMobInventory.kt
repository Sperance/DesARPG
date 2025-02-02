package ru.descend.desarpg.ui

import androidx.recyclerview.widget.LinearLayoutManager
import ru.descend.desarpg.databinding.FragmentMobInventoryBinding
import ru.descend.desarpg.log
import ru.descend.desarpg.logic.EnumItemRarity
import ru.descend.desarpg.logic.EnumItemType
import ru.descend.desarpg.logic.StockSimpleStatsBool
import ru.descend.desarpg.logic.StockSimpleStatsValue
import ru.descend.desarpg.room.datas.items.RoomItems
import ru.descend.desarpg.ui.adapters.AdapterInventoryItems

class FragmentMobInventory : BaseFragment<FragmentMobInventoryBinding>(FragmentMobInventoryBinding::inflate) {

    private val adapterInventory = AdapterInventoryItems()

    override fun setUpViews() {
        binding.buttonAddSimpleItem.setOnClickListener {
            val newSimpleItem = RoomItems(name = "Сфера запаса", rarity = EnumItemRarity.RARITY, type = EnumItemType.ITEM)
//            viewModel.repoItems.insert(newSimpleItem)
        }
        binding.buttonAddEquipItem.setOnClickListener {
            val newEquipItem = RoomItems(name = "Меч Жнеца", rarity = EnumItemRarity.MAGIC, type = EnumItemType.EQUIPMENT)
            newEquipItem.description = "Меч который может Всё"
            newEquipItem.paramsBool.add(StockSimpleStatsBool.IsCanSell(false))
            newEquipItem.paramsValue.add(StockSimpleStatsValue.MaxPrefix(2))
//            viewModel.repoItems.insert(newEquipItem)
        }
        binding.buttonClearItems.setOnClickListener {
//            viewModel.repoItems.deleteAll()
        }
        initRecyclers()
        initObservers()
    }

    private fun initObservers() {
//        viewModel.repoItems.allItems.observe(this) { data ->
//            log("[OBSERVE ITEMS]: $data")
//            viewModel.currentMob.inventoryItems.clearInventory()
//            viewModel.currentMob.inventoryItems.setInventory(data)
//            adapterInventory.onNewData(data)
//        }
    }

    private fun initRecyclers() {
        binding.recyclerInventory.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerInventory.adapter = adapterInventory
    }
}