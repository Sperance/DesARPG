package ru.descend.desarpg.ui

import ru.descend.desarpg.databinding.FragmentMobMainBinding

class FragmentMobMain : BaseFragment<FragmentMobMainBinding>(FragmentMobMainBinding::inflate) {

    private fun initBaseStats() {
        binding.textMobName.setText(viewModel.currentMob.name)
        binding.textMobHealth.setProperty(viewModel.currentMob.battleStats.health)
        binding.textMobAttack.setProperty(viewModel.currentMob.battleStats.attackPhysic)
        binding.textMobStrength.setProperty(viewModel.currentMob.battleStats.strength)
    }

    override fun setUpViews() {
        initBaseStats()
        binding.buttonHealthTo50.setOnClickListener {
            viewModel.currentMob.battleStats.health.set(50)
            viewModel.currentMob.battleStats.health.setPercent(10)
            viewModel.updateMob()
            initBaseStats()
        }
        binding.buttonHealthAdd.setOnClickListener {
            viewModel.currentMob.battleStats.health.add(2)
            viewModel.updateMob()
            initBaseStats()
        }
        binding.buttonStrengthAdd.setOnClickListener {
            viewModel.currentMob.battleStats.strength.add(1)
            viewModel.updateMob()
            initBaseStats()
        }
        binding.buttonStrengthAddPercent.setOnClickListener {
            viewModel.currentMob.battleStats.strength.addPercent(5)
            viewModel.updateMob()
            initBaseStats()
        }
    }
}