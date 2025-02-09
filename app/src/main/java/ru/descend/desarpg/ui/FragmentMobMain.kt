package ru.descend.desarpg.ui

import ru.descend.desarpg.databinding.FragmentMobMainBinding
import ru.descend.desarpg.model.EnumPropsType

class FragmentMobMain : BaseFragment<FragmentMobMainBinding>(FragmentMobMainBinding::inflate) {

    private fun initBaseStats() {
        binding.textMobName.setText(viewModel.currentMob.name)
        binding.textMobHealth.setProperty(viewModel.currentStats.getStat(EnumPropsType.HEALTH))
        binding.textMobAttack.setProperty(viewModel.currentStats.getStat(EnumPropsType.PHYSIC_ATTACK))
        binding.textMobStrength.setProperty(viewModel.currentStats.getStat(EnumPropsType.STRENGTH))
    }

    override fun setUpViews() {
        initBaseStats()
        binding.buttonHealthTo50.setOnClickListener {
            val stat = viewModel.currentStats.getStockStat(EnumPropsType.HEALTH)
            stat.setValue(50)
            viewModel.saveToDB()
            initBaseStats()
        }
        binding.buttonHealthAddPercent.setOnClickListener {
            val stat = viewModel.currentStats.getStockStat(EnumPropsType.HEALTH)
            stat.addPercent(5)
            viewModel.saveToDB()
            initBaseStats()
        }
        binding.buttonHealthRemovePercent.setOnClickListener {
            val stat = viewModel.currentStats.getStockStat(EnumPropsType.HEALTH)
            stat.removePercent(5)
            viewModel.saveToDB()
            initBaseStats()
        }
        binding.buttonHealthAdd.setOnClickListener {
            val stat = viewModel.currentStats.getStockStat(EnumPropsType.HEALTH)
            stat.addValue(2)
            viewModel.saveToDB()
            initBaseStats()
        }
        binding.buttonStrengthAdd.setOnClickListener {
            val stat = viewModel.currentStats.getStockStat(EnumPropsType.STRENGTH)
            stat.addValue(1)
            viewModel.saveToDB()
            initBaseStats()
        }
        binding.buttonStrengthAddPercent.setOnClickListener {
            val stat = viewModel.currentStats.getStockStat(EnumPropsType.STRENGTH)
            stat.addPercent(5)
            viewModel.saveToDB()
            initBaseStats()
        }
        binding.buttonAddHealthForStrength.setOnClickListener {
            val stat = viewModel.currentStats.getStockStat(EnumPropsType.HEALTH_FOR_STRENGTH)
            stat.addValue(1)
            viewModel.saveToDB()
            initBaseStats()
        }
    }
}