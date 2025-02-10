package ru.descend.desarpg.ui

import ru.descend.desarpg.databinding.FragmentSkillTreeBinding

class FragmentSkillTree : BaseFragment<FragmentSkillTreeBinding>(FragmentSkillTreeBinding::inflate) {
    override fun setUpViews() {
        val listNodes = viewModel.getSkillTreeNodes()
        binding.skillTreeView.loadNodes(listNodes.arrayStats)
    }
}