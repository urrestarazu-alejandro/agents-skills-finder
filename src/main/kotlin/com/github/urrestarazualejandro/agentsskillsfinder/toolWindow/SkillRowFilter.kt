package com.github.urrestarazualejandro.agentsskillsfinder.toolWindow

import javax.swing.RowFilter

class SkillRowFilter : RowFilter<SkillsTableModel, Int>() {

    private var searchText: String = ""

    fun setSearchText(text: String) {
        searchText = text.lowercase()
    }

    override fun include(entry: Entry<out SkillsTableModel, out Int>): Boolean {
        if (searchText.isBlank()) return true

        val model = entry.model
        val rowIndex = entry.identifier

        val skill = model.getSkillAt(rowIndex) ?: return false

        return skill.description.lowercase().contains(searchText) ||
                skill.name.lowercase().contains(searchText)
    }
}
