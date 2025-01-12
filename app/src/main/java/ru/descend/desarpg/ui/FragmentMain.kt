package ru.descend.desarpg.ui

import androidx.navigation.fragment.findNavController
import ru.descend.desarpg.R
import ru.descend.desarpg.databinding.FragmentMainBinding

class FragmentMain : BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {
    override fun setUpViews() {
        viewModel.clearAllMobs()
        viewModel.clearAllItems()
        binding.buttonToButtons.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentMain_to_fragmentTestButtons)
        }
        binding.buttonToMainStats.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentMain_to_fragmentMobMain)
        }
        binding.buttonToInventory.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentMain_to_fragmentMobInventory)
        }
    }
}