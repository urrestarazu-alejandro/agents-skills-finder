package com.github.urrestarazualejandro.agentsskillsfinder.scanner

import com.github.urrestarazualejandro.agentsskillsfinder.model.ParseResult
import com.github.urrestarazualejandro.agentsskillsfinder.model.Skill
import com.github.urrestarazualejandro.agentsskillsfinder.parser.SkillParser
import com.intellij.openapi.diagnostic.logger
import com.intellij.openapi.progress.ProgressIndicator
import com.intellij.openapi.progress.Task
import com.intellij.openapi.project.Project
import java.io.File

class SkillsFileScanner(private val project: Project) {

    private val parser = SkillParser()
    private val log = logger<SkillsFileScanner>()

    fun scan(callback: (List<Skill>) -> Unit) {
        val projectBasePath = project.basePath ?: return
        val skillsDir = File(projectBasePath, ".agents/skills")

        if (!skillsDir.exists() || !skillsDir.isDirectory) {
            callback(emptyList())
            return
        }

        object : Task.Backgroundable(project, "Loading Skills", true) {
            override fun run(indicator: ProgressIndicator) {
                val skills = mutableListOf<Skill>()
                val skillDirs = skillsDir.listFiles()?.filter { it.isDirectory } ?: emptyList()

                indicator.text = "Scanning skills..."
                indicator.isIndeterminate = false

                skillDirs.forEachIndexed { index, dir ->
                    if (indicator.isCanceled) {
                        return
                    }

                    indicator.fraction = (index + 1).toDouble() / skillDirs.size
                    indicator.text2 = "Processing: ${dir.name}"

                    val skillFile = File(dir, "SKILL.md")
                    if (skillFile.exists() && skillFile.isFile) {
                        when (val result = parser.parse(skillFile, projectBasePath)) {
                            is ParseResult.Success -> skills.add(result.skill)
                            is ParseResult.Error -> log.warn("Failed to parse ${result.filePath}: ${result.reason}")
                        }
                    }
                }

                callback(skills)
            }
        }.queue()
    }
}
