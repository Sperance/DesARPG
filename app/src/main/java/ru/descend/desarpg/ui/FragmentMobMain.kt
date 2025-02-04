package ru.descend.desarpg.ui

import ru.descend.desarpg.databinding.FragmentMobMainBinding
import ru.descend.desarpg.logic.EnumPropsType

class FragmentMobMain : BaseFragment<FragmentMobMainBinding>(FragmentMobMainBinding::inflate) {

    private fun initBaseStats() {
        binding.textMobName.setText(viewModel.currentMob.name)
        binding.textMobHealth.setProperty(viewModel.currentMob.battleStats.target.getCalcHealth())
//        binding.textMobAttack.setProperty(viewModel.currentMob.battleStats.target.getStat(EnumPropsType.PHYSIC_ATTACK))
        binding.textMobStrength.setProperty(viewModel.currentMob.battleStats.target.getCalcStrength())
    }

    override fun setUpViews() {
        initBaseStats()
        binding.buttonHealthTo50.setOnClickListener {
            val stat = viewModel.currentMob.battleStats.target.getStat(EnumPropsType.HEALTH)
            stat!!.setValue(50)
            viewModel.currentMob.battleStats.target.save(viewModel.currentBox)
            initBaseStats()
        }
        binding.buttonHealthAdd.setOnClickListener {
            val stat = viewModel.currentMob.battleStats.target.getStat(EnumPropsType.HEALTH)
            stat!!.addValue(2)
            viewModel.currentMob.battleStats.target.save(viewModel.currentBox)
            initBaseStats()
        }
        binding.buttonStrengthAdd.setOnClickListener {
            val stat = viewModel.currentMob.battleStats.target.getStat(EnumPropsType.STRENGTH)
            stat!!.addValue(1)
            viewModel.currentMob.battleStats.target.save(viewModel.currentBox)
            initBaseStats()
        }
        binding.buttonStrengthAddPercent.setOnClickListener {
            val stat = viewModel.currentMob.battleStats.target.getStat(EnumPropsType.STRENGTH)
            stat!!.addPercent(5)
            viewModel.currentMob.battleStats.target.save(viewModel.currentBox)
            initBaseStats()
        }
    }
}