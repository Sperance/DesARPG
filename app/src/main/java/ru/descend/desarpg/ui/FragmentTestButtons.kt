package ru.descend.desarpg.ui

import ru.descend.desarpg.databinding.FragmentTestButtonsBinding
import ru.descend.desarpg.log
import ru.descend.desarpg.model.EnumPropsType

class FragmentTestButtons : BaseFragment<FragmentTestButtonsBinding>(FragmentTestButtonsBinding::inflate) {

    override fun setUpViews() {
        log("FragmentTestButtons VM: ${viewModel.hashCode()}")
        binding.buttonShowAllStats.setOnClickListener {
            viewModel.getAllBoxes().forEach {
                log("${it.entityClass.simpleName}: \n")
                it.all.forEach { item ->
                    log("item: $item")
                }
            }
        }
        binding.buttonClearMobs.setOnClickListener {

        }
    }
}