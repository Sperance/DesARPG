package ru.descend.desarpg.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ru.descend.desarpg.R
import ru.descend.desarpg.model.SkillNodeEntity
import ru.descend.desarpg.ui.custom.SkillTreeView

class NodeBottomSheetFragment : BottomSheetDialogFragment() {
    private lateinit var node: SkillNodeEntity
    private var activationListener: OnNodeActivationListener? = null

    interface OnNodeActivationListener {
        fun onActivateNodeRequested(node: SkillNodeEntity)
        fun onDeactivateNodeRequested(node: SkillNodeEntity)
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
    ): View? {
        return inflater.inflate(R.layout.bottom_sheet_node, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<TextView>(R.id.node_name).text = node.name

        val activateButton = view.findViewById<Button>(R.id.activate_button)
        val deactivateButton = view.findViewById<Button>(R.id.deactivate_button)

        if (node.isActivated) {
            activateButton.visibility = View.GONE
            deactivateButton.visibility = View.VISIBLE
        } else {
            activateButton.visibility = View.VISIBLE
            deactivateButton.visibility = View.GONE
        }

        activateButton.setOnClickListener {
            activationListener?.onActivateNodeRequested(node)
            dismiss()
        }

        deactivateButton.setOnClickListener {
            activationListener?.onDeactivateNodeRequested(node)
            dismiss()
        }
    }

    companion object {
        const val TAG = "NodeBottomSheet"
    }
}