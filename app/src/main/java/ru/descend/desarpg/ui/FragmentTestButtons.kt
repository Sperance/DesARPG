package ru.descend.desarpg.ui

import ru.descend.desarpg.databinding.FragmentTestButtonsBinding
import ru.descend.desarpg.logic.EnumPropsType

class FragmentTestButtons : BaseFragment<FragmentTestButtonsBinding>(FragmentTestButtonsBinding::inflate) {

    override fun setUpViews() {
        binding.buttonShowMobs.setOnClickListener {
            viewModel.currentMob.battleStats.target.arrayStats.forEach {
                println("OBJ: $it")
            }
        }
        binding.buttonClearMobs.setOnClickListener {
            val stat = viewModel.currentMob.battleStats.target.getStat(EnumPropsType.HEALTH)
            stat!!.addValue(150)
            viewModel.currentMob.battleStats.target.save(viewModel.currentBox)
            println("H: $stat")
        }
    }
}