package ru.descend.desarpg.ui.adapters

import ru.descend.desarpg.databinding.AdapterItemNodeSkillBinding
import ru.descend.desarpg.model.DoubleProp
import ru.descend.desarpg.model.EnumPropsType
import ru.descend.desarpg.model.SkillNodeEntity

class AdapterSkillNodeList : DesAdapter<DoubleProp, AdapterItemNodeSkillBinding>(AdapterItemNodeSkillBinding::class) {

    lateinit var node: SkillNodeEntity

    override fun onBindItem(item: DoubleProp, binding: AdapterItemNodeSkillBinding) {
        val statName = EnumPropsType.getFromName(item.type)?.statName + " "
        val valueBonusFromPercent = if (node.getCurrentBonusForLevel() != 0) " ${node.getCurrentBonusForLevel()}%"  else ""
        val valueProp = if (item.valueProp > 0) "+${item.valueProp}" else "${item.valueProp}"
        val valuePerc = if (item.percentProp > 0) "+${item.percentProp}" else "${item.percentProp}"
        val resultStr = statName + if (item.valueProp != 0.0) valueProp else "" + if (item.percentProp != 0.0) valuePerc else ""
        binding.statLine.setText(resultStr)

        if (node.getCurrentBonusForLevel() != 0) binding.statLine.setTextBottom("Увеличение показателей на $valueBonusFromPercent")
    }
}