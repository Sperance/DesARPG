package ru.descend.desarpg.ui

import androidx.recyclerview.widget.LinearLayoutManager
import ru.descend.desarpg.applicationBox
import ru.descend.desarpg.databinding.FragmentMobInventoryBinding
import ru.descend.desarpg.log
import ru.descend.desarpg.model.BaseItem
import ru.descend.desarpg.model.EnumItemCategory
import ru.descend.desarpg.model.EnumItemRarity
import ru.descend.desarpg.ui.adapters.AdapterInventoryList

class FragmentMobInventory : BaseFragment<FragmentMobInventoryBinding>(FragmentMobInventoryBinding::inflate) {

    private val adapter = AdapterInventoryList()

    override fun setUpViews() {
        binding.buttonAddSimpleItem.setOnClickListener {
            val item = BaseItem(name = "Сфера хаоса", description = "Валюта", category = EnumItemCategory.RESOURCE.name, rarity = EnumItemRarity.RARE.name)
            viewModel.getInventory().addItem(item)
        }
        binding.buttonAddEquipItem.setOnClickListener {
            applicationBox.boxFor(BaseItem::class.java).all.forEach {
                log("item: $it")
            }
        }
        binding.buttonClearItems.setOnClickListener {
            viewModel.getInventory().clearInventory()
        }
        initRecyclers()
        initObservers()
    }

    private fun initObservers() {
        viewModel.getItemsLiveData().observe(this) {
            val list = ArrayList<BaseItem>()
            it.forEach { obj ->
                if (obj != null) list.add(obj)
            }
            adapter.onNewData(list)
        }
    }

    private fun initRecyclers() {
        binding.recyclerInventory.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerInventory.adapter = adapter
    }
}