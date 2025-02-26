package ru.descend.desarpg.ui

import ru.descend.desarpg.databinding.FragmentMobMainBinding
import ru.descend.desarpg.model.EnumPropsType
import ru.descend.desarpg.model.EnumSystemStatsType

class FragmentMobMain : BaseFragment<FragmentMobMainBinding>(FragmentMobMainBinding::inflate) {

    private fun initBaseStats() {
        binding.textMobName.setText(viewModel.getMob().name)
        binding.textMobHealth.setProperty(viewModel.getCurrentStats().getStat(EnumPropsType.HEALTH))
        binding.textMobAttack.setProperty(viewModel.getCurrentStats().getStat(EnumPropsType.PHYSIC_ATTACK))
        binding.textMobStrength.setProperty(viewModel.getCurrentStats().getStat(EnumPropsType.STRENGTH))
    }

    override fun setUpViews() {
        binding.buttonHealthTo50.setOnClickListener {
            viewModel.getStockStats().getStockStat(EnumPropsType.HEALTH).setValue(50).saveToBox()
            initBaseStats()
        }
        binding.buttonHealthAddPercent.setOnClickListener {
            viewModel.getStockStats().getStockStat(EnumPropsType.HEALTH).addPercent(5).saveToBox()
            initBaseStats()
        }
        binding.buttonHealthRemovePercent.setOnClickListener {
            viewModel.getStockStats().getStockStat(EnumPropsType.HEALTH).removePercent(5).saveToBox()
            initBaseStats()
        }
        binding.buttonHealthAdd.setOnClickListener {
            viewModel.getStockStats().getStockStat(EnumPropsType.HEALTH).addValue(2).saveToBox()
            initBaseStats()
        }
        binding.buttonStrengthAdd.setOnClickListener {
            viewModel.getStockStats().getStockStat(EnumPropsType.STRENGTH).addValue(1).saveToBox()
            initBaseStats()
        }
        binding.buttonStrengthAddPercent.setOnClickListener {
            viewModel.getStockStats().getStockStat(EnumPropsType.STRENGTH).addPercent(5).saveToBox()
            initBaseStats()
        }
        binding.buttonAddHealthForStrength.setOnClickListener {
            viewModel.getStockStats().getStockStat(EnumPropsType.HEALTH_FOR_STRENGTH).addValue(1).saveToBox()
            initBaseStats()
        }
    }
}