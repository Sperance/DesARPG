package ru.descend.desarpg.ui

import ru.descend.desarpg.databinding.FragmentTestButtonsBinding
import ru.descend.desarpg.launch

class FragmentTestButtons : BaseFragment<FragmentTestButtonsBinding>(FragmentTestButtonsBinding::inflate) {
    override fun setUpViews() {
        binding.buttonShowMobs.setOnClickListener {
            viewModel.launch {
                val mobs = viewModel.getAllMobs()
                println("MOBS: $mobs")
            }
        }
    }
}