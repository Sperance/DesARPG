package ru.descend.desarpg.ui

import ru.descend.desarpg.databinding.FragmentTestButtonsBinding

class FragmentTestButtons : BaseFragment<FragmentTestButtonsBinding>(FragmentTestButtonsBinding::inflate) {
    override fun setUpViews() {
        binding.buttonShowMobs.setOnClickListener {

        }
        binding.buttonClearMobs.setOnClickListener {

        }
    }
}