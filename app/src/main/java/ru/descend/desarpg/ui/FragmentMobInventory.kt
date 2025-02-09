package ru.descend.desarpg.ui

import androidx.recyclerview.widget.LinearLayoutManager
import ru.descend.desarpg.databinding.FragmentMobInventoryBinding

class FragmentMobInventory : BaseFragment<FragmentMobInventoryBinding>(FragmentMobInventoryBinding::inflate) {

    override fun setUpViews() {
        binding.buttonAddSimpleItem.setOnClickListener {

        }
        binding.buttonAddEquipItem.setOnClickListener {

        }
        binding.buttonClearItems.setOnClickListener {

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
    }
}