package com.github.urrestarazualejandro.agentsskillsfinder.parser

import com.github.urrestarazualejandro.agentsskillsfinder.model.ParseResult
import com.github.urrestarazualejandro.agentsskillsfinder.model.Skill
import org.yaml.snakeyaml.Yaml
import java.io.File
import java.io.FileReader

class SkillParser {

    private val yaml = Yaml()

    fun parse(file: File, projectBasePath: String): ParseResult {
        return try {
            val content = file.readText()
            val frontMatter = extractFrontMatter(content)

            if (frontMatter == null) {
                return ParseResult.Error(file.absolutePath, "No front matter found")
            }

            val data = yaml.load<Map<String, Any>>(frontMatter)

            val name = data["name"] as? String
            val description = data["description"] as? String

            if (name.isNullOrBlank()) {
                return ParseResult.Error(file.absolutePath, "Missing required field: name")
            }

            if (description.isNullOrBlank()) {
                return ParseResult.Error(file.absolutePath, "Missing required field: description")
            }

            val skillDir = file.parentFile
            val relativePath = computeRelativePath(skillDir, projectBasePath)

            val skill = Skill(
                name = name,
                description = description,
                relativePath = relativePath,
                source = data["source"] as? String,
                risk = data["risk"] as? String,
                filePath = file.absolutePath
            )

            ParseResult.Success(skill)
        } catch (e: Exception) {
            ParseResult.Error(file.absolutePath, "Parse error: ${e.message}")
        }
    }

    private fun extractFrontMatter(content: String): String? {
        val trimmed = content.trimStart()
        if (!trimmed.startsWith("---")) {
            return null
        }

        val firstDelimiter = trimmed.indexOf("---", 3)
        if (firstDelimiter == -1) {
            return null
        }

        return trimmed.substring(3, firstDelimiter).trim()
    }

    private fun computeRelativePath(skillDir: File, projectBasePath: String): String {
        val skillDirPath = skillDir.absolutePath
        val basePath = File(projectBasePath).absolutePath

        return if (skillDirPath.startsWith(basePath)) {
            skillDirPath.removePrefix(basePath).removePrefix("/").removePrefix("\\")
        } else {
            skillDir.name
        }
    }
}
