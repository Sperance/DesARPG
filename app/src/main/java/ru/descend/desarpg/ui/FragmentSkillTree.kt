package ru.descend.desarpg.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.descend.desarpg.databinding.FragmentSkillTreeBinding
import ru.descend.desarpg.ui.custom.SkillTreeView

class FragmentSkillTree : BaseFragment<FragmentSkillTreeBinding>(FragmentSkillTreeBinding::inflate) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return SkillTreeView(requireContext(), viewModel = viewModel)
    }

    override fun setUpViews() {

    }
}