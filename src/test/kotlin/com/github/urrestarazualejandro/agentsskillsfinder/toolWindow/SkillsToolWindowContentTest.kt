package com.github.urrestarazualejandro.agentsskillsfinder.toolWindow

import com.github.urrestarazualejandro.agentsskillsfinder.services.SkillsService
import com.intellij.openapi.components.service
import com.intellij.testFramework.fixtures.BasePlatformTestCase

class SkillsToolWindowContentTest : BasePlatformTestCase() {

    fun testCreateToolWindowContent() {
        val skillsService = project.service<SkillsService>()
        val content = SkillsToolWindowContent(project, skillsService)

        // Test that content panel is created
        assertNotNull(content.getContent())
    }

    fun testToolWindowContentHasComponents() {
        val skillsService = project.service<SkillsService>()
        val content = SkillsToolWindowContent(project, skillsService)

        val panel = content.getContent()
        assertNotNull(panel)
        assertTrue(panel.componentCount > 0)
    }
}
