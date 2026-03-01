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

        // Split search text into tokens
        val tokens = searchText.split("\\s+".toRegex()).filter { it.isNotBlank() }

        // Combine name and description for searching
        val combinedText = "${skill.name.lowercase()} ${skill.description.lowercase()}"

        // All tokens must be present in the combined text
        return tokens.all { token -> combinedText.contains(token) }
    }
}
