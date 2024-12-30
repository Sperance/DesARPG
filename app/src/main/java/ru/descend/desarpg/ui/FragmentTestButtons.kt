package ru.descend.desarpg.ui

import kotlinx.coroutines.delay
import ru.descend.desarpg.databinding.FragmentTestButtonsBinding
import ru.descend.desarpg.launch

class FragmentTestButtons : BaseFragment<FragmentTestButtonsBinding>(FragmentTestButtonsBinding::inflate) {
    override fun setUpViews() {
        binding.buttonShowMobs.setOnClickListener {
            viewModel.launch {
                delay(2000)
                println("2")
                val mobs = viewModel.getAllMobs()
                println("MOBS: $mobs")
                delay(5000)
                println("5")
            }
        }
        binding.buttonClearMobs.setOnClickListener {
            viewModel.clearAllMobs()
        }
    }
}