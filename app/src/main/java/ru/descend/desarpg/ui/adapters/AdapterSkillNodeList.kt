package ru.descend.desarpg.ui.adapters

import ru.descend.desarpg.databinding.AdapterItemNodeSkillBinding
import ru.descend.desarpg.model.DoubleProp
import ru.descend.desarpg.model.EnumPropsType
import ru.descend.desarpg.model.StockStatsProp

class AdapterSkillNodeList : DesAdapter<DoubleProp, AdapterItemNodeSkillBinding>(AdapterItemNodeSkillBinding::class) {
    override fun onBindItem(item: DoubleProp, binding: AdapterItemNodeSkillBinding) {
        val statName = EnumPropsType.getFromName(item.type)?.statName + " "
        val valueProp = if (item.valueProp > 0) "+${item.valueProp}" else "${item.valueProp}"
        val valuePerc = if (item.percentProp > 0) "+${item.percentProp}" else "${item.percentProp}"
        val resultStr = statName + if (item.valueProp != 0.0) valueProp else "" + if (item.percentProp != 0.0) valuePerc else ""
        binding.statLine.setText(resultStr)
    }
}