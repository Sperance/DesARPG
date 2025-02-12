package ru.descend.desarpg.ui

import ru.descend.desarpg.databinding.FragmentTestButtonsBinding
import ru.descend.desarpg.log
import ru.descend.desarpg.model.EnumPropsType

class FragmentTestButtons : BaseFragment<FragmentTestButtonsBinding>(FragmentTestButtonsBinding::inflate) {

    override fun setUpViews() {
        binding.buttonShowAllStats.setOnClickListener {
            viewModel.getAllBoxes().forEach {
                it.all.forEach { item -> log("item: $item") }
            }
        }
        binding.buttonClearMobs.setOnClickListener {

        }
    }
}