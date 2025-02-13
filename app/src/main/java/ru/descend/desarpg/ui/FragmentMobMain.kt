package ru.descend.desarpg.ui

import ru.descend.desarpg.databinding.FragmentMobMainBinding
import ru.descend.desarpg.model.EnumPropsType

class FragmentMobMain : BaseFragment<FragmentMobMainBinding>(FragmentMobMainBinding::inflate) {

    private fun initBaseStats() {
        binding.textMobName.setText(viewModel.getMob().name)
        binding.textMobHealth.setProperty(viewModel.getCurrentStats().getStat(EnumPropsType.HEALTH))
        binding.textMobAttack.setProperty(viewModel.getCurrentStats().getStat(EnumPropsType.PHYSIC_ATTACK))
        binding.textMobStrength.setProperty(viewModel.getCurrentStats().getStat(EnumPropsType.STRENGTH))
    }

    override fun setUpViews() {
        binding.buttonHealthTo50.setOnClickListener {
            val stat = viewModel.getStockStats().getStockStat(EnumPropsType.HEALTH)
            stat.setValue(50)
            viewModel.saveToDB()
        }
        binding.buttonHealthAddPercent.setOnClickListener {
            val stat = viewModel.getStockStats().getStockStat(EnumPropsType.HEALTH)
            stat.addPercent(5)
            viewModel.saveToDB()
        }
        binding.buttonHealthRemovePercent.setOnClickListener {
            val stat = viewModel.getStockStats().getStockStat(EnumPropsType.HEALTH)
            stat.removePercent(5)
            viewModel.saveToDB()
        }
        binding.buttonHealthAdd.setOnClickListener {
            val stat = viewModel.getStockStats().getStockStat(EnumPropsType.HEALTH)
            stat.addValue(2)
            viewModel.saveToDB()
        }
        binding.buttonStrengthAdd.setOnClickListener {
            val stat = viewModel.getStockStats().getStockStat(EnumPropsType.STRENGTH)
            stat.addValue(1)
            viewModel.saveToDB()
        }
        binding.buttonStrengthAddPercent.setOnClickListener {
            val stat = viewModel.getStockStats().getStockStat(EnumPropsType.STRENGTH)
            stat.addPercent(5)
            viewModel.saveToDB()
        }
        binding.buttonAddHealthForStrength.setOnClickListener {
            val stat = viewModel.getStockStats().getStockStat(EnumPropsType.HEALTH_FOR_STRENGTH)
            stat.addValue(1)
            viewModel.saveToDB()
        }
        initObservers()
    }

    private fun initObservers() {
        viewModel.getMobBattleStatsLiveData().observe(this) {
            initBaseStats()
        }
    }
}