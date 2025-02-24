package ru.descend.desarpg.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ru.descend.desarpg.databinding.BottomSheetNodeBinding
import ru.descend.desarpg.model.DoubleProp
import ru.descend.desarpg.model.SkillNodeEntity
import ru.descend.desarpg.ui.adapters.AdapterSkillNodeList

class NodeBottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding: BottomSheetNodeBinding? = null
    private val binding get() = _binding!!
    private lateinit var node: SkillNodeEntity
    private var activationListener: OnNodeActivationListener? = null
    private val adapter = AdapterSkillNodeList()

    interface OnNodeActivationListener {
        fun onActivateNodeRequested(node: SkillNodeEntity)
        fun onDeactivateNodeRequested(node: SkillNodeEntity)
        fun onLevelUpNodeRequested(node: SkillNodeEntity)
    }

    fun setNode(node: SkillNodeEntity) {
        this.node = node
    }

    fun setActivationListener(listener: OnNodeActivationListener) {
        this.activationListener = listener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetNodeBinding.inflate(layoutInflater, container, false)
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.nodeName.setText(node.name)

        if (node.level > 1) binding.nodeLevel.setText("Увеличение показателей на ${(node.level - 1) * node.bonusForLevel}%")
        binding.nodeLevel.setTextPostfix("Level: ${node.level} / ${node.maxLevel}")

        binding.recyclerNodeStats.adapter = adapter
        binding.recyclerNodeStats.layoutManager = LinearLayoutManager(requireContext())

        adapter.onNewData(ArrayList<DoubleProp>().apply { addAll(node.arrayStats) })

        if (node.isActivated) {
            binding.activateButton.visibility = View.GONE
            binding.deactivateButton.visibility = View.VISIBLE
        } else {
            binding.activateButton.visibility = View.VISIBLE
            binding.deactivateButton.visibility = View.GONE
        }

        binding.nodeLevel.visibility = if (node.maxLevel > 1) View.VISIBLE else View.GONE
        binding.buttonAddLevel.visibility = if (node.maxLevel > 1) View.VISIBLE else View.GONE

        binding.activateButton.setOnClickListener {
            activationListener?.onActivateNodeRequested(node)
            dismiss()
        }

        binding.deactivateButton.setOnClickListener {
            activationListener?.onDeactivateNodeRequested(node)
            dismiss()
        }

        binding.buttonAddLevel.setOnClickListener {
            activationListener?.onLevelUpNodeRequested(node)
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}