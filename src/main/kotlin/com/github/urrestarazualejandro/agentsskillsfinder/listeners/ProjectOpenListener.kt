package com.github.urrestarazualejandro.agentsskillsfinder.listeners

import com.github.urrestarazualejandro.agentsskillsfinder.services.SkillsService
import com.intellij.openapi.project.Project
import com.intellij.openapi.project.ProjectManagerListener

class ProjectOpenListener : ProjectManagerListener {

    @Deprecated("Deprecated in Java")
    override fun projectOpened(project: Project) {
        val skillsService = SkillsService.getInstance(project)
        skillsService.loadSkillsAsync()
    }
}
