package com.github.urrestarazualejandro.agentsskillsfinder.toolWindow

import com.github.urrestarazualejandro.agentsskillsfinder.model.Skill
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

class SkillsTableModelTest {

    private lateinit var tableModel: SkillsTableModel

    private val testSkills = listOf(
        Skill(
            name = "test-skill-1",
            description = "Test skill 1 description",
            relativePath = "/skills/test-skill-1",
            source = "personal",
            risk = "safe",
            filePath = "/test/skills/test-skill-1"
        ),
        Skill(
            name = "test-skill-2",
            description = "Test skill 2 description",
            relativePath = "/skills/test-skill-2",
            source = "community",
            risk = "unknown",
            filePath = "/test/skills/test-skill-2"
        )
    )

    @Before
    fun setUp() {
        tableModel = SkillsTableModel()
    }

    @Test
    fun testInitialState() {
        assertEquals(0, tableModel.rowCount)
        assertEquals(3, tableModel.columnCount)
    }

    @Test
    fun testSetSkills() {
        tableModel.setSkills(testSkills)
        assertEquals(2, tableModel.rowCount)
    }

    @Test
    fun testGetSkillAt() {
        tableModel.setSkills(testSkills)

        val skill = tableModel.getSkillAt(0)
        assertEquals("test-skill-1", skill?.name)

        val skill2 = tableModel.getSkillAt(1)
        assertEquals("test-skill-2", skill2?.name)
    }

    @Test
    fun testGetSkillAtInvalidIndex() {
        tableModel.setSkills(testSkills)

        assertNull(tableModel.getSkillAt(-1))
        assertNull(tableModel.getSkillAt(10))
    }

    @Test
    fun testGetColumnCount() {
        assertEquals(SkillsTableModel.COLUMN_COUNT, tableModel.columnCount)
        assertEquals(3, tableModel.columnCount)
    }

    @Test
    fun testGetColumnName() {
        assertEquals("Name", tableModel.getColumnName(SkillsTableModel.COLUMN_NAME))
        assertEquals("Description", tableModel.getColumnName(SkillsTableModel.COLUMN_DESCRIPTION))
        assertEquals("Path", tableModel.getColumnName(SkillsTableModel.COLUMN_PATH))
        assertEquals("", tableModel.getColumnName(999))
    }

    @Test
    fun testGetValueAt() {
        tableModel.setSkills(testSkills)

        assertEquals("test-skill-1", tableModel.getValueAt(0, SkillsTableModel.COLUMN_NAME))
        assertEquals("Test skill 1 description", tableModel.getValueAt(0, SkillsTableModel.COLUMN_DESCRIPTION))
        assertEquals("/skills/test-skill-1", tableModel.getValueAt(0, SkillsTableModel.COLUMN_PATH))

        assertEquals("test-skill-2", tableModel.getValueAt(1, SkillsTableModel.COLUMN_NAME))
        assertEquals("Test skill 2 description", tableModel.getValueAt(1, SkillsTableModel.COLUMN_DESCRIPTION))
        assertEquals("/skills/test-skill-2", tableModel.getValueAt(1, SkillsTableModel.COLUMN_PATH))
    }

    @Test
    fun testGetValueAtInvalidColumn() {
        tableModel.setSkills(testSkills)

        assertEquals("", tableModel.getValueAt(0, 999))
    }

    @Test
    fun testEmptyTable() {
        tableModel.setSkills(emptyList())
        assertEquals(0, tableModel.rowCount)
    }

    @Test
    fun testUpdateSkills() {
        tableModel.setSkills(testSkills)
        assertEquals(2, tableModel.rowCount)

        val newSkills = listOf(
            Skill(
                name = "new-skill",
                description = "New skill description",
                relativePath = "/skills/new-skill",
                source = "personal",
                risk = "safe",
                filePath = "/test/skills/new-skill"
            )
        )

        tableModel.setSkills(newSkills)
        assertEquals(1, tableModel.rowCount)
        assertEquals("new-skill", tableModel.getSkillAt(0)?.name)
    }
}
