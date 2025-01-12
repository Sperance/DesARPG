package ru.descend.desarpg.ui

import androidx.recyclerview.widget.LinearLayoutManager
import ru.descend.desarpg.databinding.FragmentMobInventoryBinding
import ru.descend.desarpg.logic.EnumItemRarity
import ru.descend.desarpg.logic.EquippingItem
import ru.descend.desarpg.logic.SimpleItem
import ru.descend.desarpg.ui.adapters.AdapterInventoryItems

class FragmentMobInventory : BaseFragment<FragmentMobInventoryBinding>(FragmentMobInventoryBinding::inflate) {

    private val adapterInventory = AdapterInventoryItems()

    override fun setUpViews() {
        binding.buttonAddSimpleItem.setOnClickListener {
            val newSimpleItem = SimpleItem("Сфера запаса", EnumItemRarity.RARITY)
            viewModel.currentMob.inventoryItems.addToInventory(newSimpleItem)
            adapterInventory.addItem(newSimpleItem)
            viewModel.updateMob()
        }
        binding.buttonAddEquipItem.setOnClickListener {
            val newEquipItem = EquippingItem("Меч Жнеца", EnumItemRarity.MAGIC)
            newEquipItem.description = "Меч который может Всё"
            viewModel.currentMob.inventoryItems.addToInventory(newEquipItem)
            adapterInventory.addItem(newEquipItem)
            viewModel.updateMob()
            viewModel.updateMob()
        }
        binding.buttonClearItems.setOnClickListener {
            viewModel.clearAllItems()
            adapterInventory.clear()
            viewModel.updateMob()
        }
        initRecyclers()
    }

    private fun initRecyclers() {
        binding.recyclerInventory.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerInventory.adapter = adapterInventory
    }
}