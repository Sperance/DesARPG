package ru.descend.desarpg.ui.adapters

import ru.descend.desarpg.databinding.AdapterItemNodeSkillBinding
import ru.descend.desarpg.model.EnumPropsType
import ru.descend.desarpg.model.StockStatsProp
import ru.descend.desarpg.to0Text

class AdapterSkillNodeList : DesAdapter<StockStatsProp, AdapterItemNodeSkillBinding>(AdapterItemNodeSkillBinding::class) {
    override fun onBindItem(item: StockStatsProp, binding: AdapterItemNodeSkillBinding, position: Int) {
        val statName = EnumPropsType.getFromName(item.type)?.statName + " "
        val valueProp = if (item.valueProp > 0) "+${item.valueProp.to0Text()}" else item.valueProp.to0Text()
        val valuePerc = if (item.percentProp > 0) "+${item.percentProp.to0Text()}%" else "${item.percentProp.to0Text()}%"
        val resultStr = statName + (if (item.valueProp != 0.0) valueProp else "") + (if (item.percentProp != 0.0) " $valuePerc" else "")
        binding.statLine.setText(resultStr)
    }
}