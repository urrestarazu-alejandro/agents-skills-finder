package com.github.urrestarazualejandro.agentsskillsfinder.toolWindow

import com.github.urrestarazualejandro.agentsskillsfinder.model.Skill
import javax.swing.table.AbstractTableModel

class SkillsTableModel : AbstractTableModel() {

    private var skills: List<Skill> = emptyList()

    companion object {
        const val COLUMN_NAME = 0
        const val COLUMN_DESCRIPTION = 1
        const val COLUMN_PATH = 2
        const val COLUMN_COUNT = 3
    }

    fun setSkills(newSkills: List<Skill>) {
        skills = newSkills
        fireTableDataChanged()
    }

    fun getSkillAt(rowIndex: Int): Skill? {
        return if (rowIndex in skills.indices) skills[rowIndex] else null
    }

    override fun getRowCount(): Int = skills.size

    override fun getColumnCount(): Int = COLUMN_COUNT

    override fun getValueAt(rowIndex: Int, columnIndex: Int): Any {
        val skill = skills[rowIndex]
        return when (columnIndex) {
            COLUMN_NAME -> skill.name
            COLUMN_DESCRIPTION -> skill.description
            COLUMN_PATH -> skill.relativePath
            else -> ""
        }
    }

    override fun getColumnName(column: Int): String {
        return when (column) {
            COLUMN_NAME -> "Name"
            COLUMN_DESCRIPTION -> "Description"
            COLUMN_PATH -> "Path"
            else -> ""
        }
    }
}
