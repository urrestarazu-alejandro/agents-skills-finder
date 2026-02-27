package com.github.urrestarazualejandro.agentsskillsfinder.toolWindow

import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.content.ContentFactory
import com.github.urrestarazualejandro.agentsskillsfinder.services.SkillsService
import java.io.File

class MyToolWindowFactory : ToolWindowFactory {

    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        val skillsService = project.service<SkillsService>()
        val skillsToolWindow = SkillsToolWindowContent(project, skillsService)
        val content = ContentFactory.getInstance().createContent(skillsToolWindow.getContent(), null, false)
        toolWindow.contentManager.addContent(content)
    }

    override fun shouldBeAvailable(project: Project): Boolean {
        val basePath = project.basePath ?: return false
        val skillsDir = File(basePath, ".agents/skills")
        return skillsDir.exists() && skillsDir.isDirectory
    }
}
