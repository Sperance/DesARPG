package ru.descend.desarpg.ui

import androidx.navigation.fragment.findNavController
import ru.descend.desarpg.databinding.FragmentWorkObjectBinding
import ru.descend.desarpg.model.EnumWorkStatsType

class FragmentWorkObject : BaseFragment<FragmentWorkObjectBinding>(FragmentWorkObjectBinding::inflate) {
    override fun setUpViews() {
        binding.buttonMining.setOnClickListener {
            val direction = FragmentWorkObjectDirections.actionFragmentWorkObjectToFragmentWorkMining(EnumWorkStatsType.MINING)
            findNavController().navigate(direction)
        }
        binding.buttonFishing.setOnClickListener {
            val direction = FragmentWorkObjectDirections.actionFragmentWorkObjectToFragmentWorkMining(EnumWorkStatsType.FISHING)
            findNavController().navigate(direction)
        }
    }
}