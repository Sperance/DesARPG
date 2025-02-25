package ru.descend.desarpg.ui

import androidx.navigation.fragment.findNavController
import ru.descend.desarpg.R
import ru.descend.desarpg.databinding.FragmentWorkObjectBinding

class FragmentWorkObject : BaseFragment<FragmentWorkObjectBinding>(FragmentWorkObjectBinding::inflate) {
    override fun setUpViews() {
        binding.buttonMining.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentWorkObject_to_fragmentWorkMining)
        }
    }
}