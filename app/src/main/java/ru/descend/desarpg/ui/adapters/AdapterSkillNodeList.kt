package ru.descend.desarpg.ui.adapters

import ru.descend.desarpg.databinding.AdapterItemNodeSkillBinding
import ru.descend.desarpg.model.StockStatsProp

class AdapterSkillNodeList : DesAdapter<StockStatsProp, AdapterItemNodeSkillBinding>(AdapterItemNodeSkillBinding::class) {
    override fun onBindItem(item: StockStatsProp, binding: AdapterItemNodeSkillBinding) {
        val statName = item.type.statName + " "
        val valueProp = if (item.valueP > 0) "+${item.valueP}" else "-${item.valueP}"
        val valuePerc = if (item.percentP > 0) "+${item.percentP}" else "-${item.percentP}"
        val resultStr = statName + if (item.valueP != 0.0) valueProp else "" + if (item.percentP != 0.0) valuePerc else ""
        binding.statLine.setText(resultStr)
    }
}