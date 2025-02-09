package ru.descend.desarpg.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.viewbinding.ViewBinding
import ru.descend.desarpg.MainActivityVM

typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

abstract class BaseFragment<VBinding : ViewBinding>(private val inflate: Inflate<VBinding>) : Fragment() {

    val viewModel: MainActivityVM by activityViewModels()
    private var _binding: VBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = inflate.invoke(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViews()
    }

    abstract fun setUpViews()

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}