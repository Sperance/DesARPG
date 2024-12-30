package ru.descend.desarpg.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import ru.descend.desarpg.R
import ru.descend.desarpg.databinding.FragmentMainBinding

class FragmentMain : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonToButtons.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentMain_to_fragmentTestButtons)
        }
        binding.buttonToMainStats.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentMain_to_fragmentMobMain)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}