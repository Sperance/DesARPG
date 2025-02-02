package ru.descend.desarpg.ui

import ru.descend.desarpg.databinding.FragmentTestButtonsBinding
import ru.descend.desarpg.log
import ru.descend.desarpg.logic.NewOBB
import ru.descend.desarpg.logic.NewObbAbs
import java.util.Random
import java.util.UUID

class FragmentTestButtons : BaseFragment<FragmentTestButtonsBinding>(FragmentTestButtonsBinding::inflate) {

    override fun setUpViews() {
        binding.buttonShowMobs.setOnClickListener {
            val box = viewModel.currentBox.boxFor(NewObbAbs::class.java)
            val newObj = NewObbAbs()
            newObj.name = UUID.randomUUID().toString()
            newObj.age = Random(System.currentTimeMillis()).nextInt()
            newObj.newValue = Random(System.currentTimeMillis()).nextInt()
            log("PUT: ${box.put(newObj)}")
        }
        binding.buttonClearMobs.setOnClickListener {
            val box = viewModel.currentBox.boxFor(NewObbAbs::class.java)
            log("OBJECTS: \n")
            box.all.forEach {
                log("BOX: $it")
            }
        }
    }
}