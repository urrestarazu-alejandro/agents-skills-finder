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

            // Normalize frontmatter to handle common YAML formatting issues
            val normalizedFrontMatter = normalizeFrontMatter(frontMatter)

            val data = yaml.load<Map<String, Any>>(normalizedFrontMatter)

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

    private fun normalizeFrontMatter(frontMatter: String): String {
        val lines = frontMatter.lines().toMutableList()
        val result = mutableListOf<String>()
        var i = 0

        while (i < lines.size) {
            val line = lines[i]

            // Check if this is a description field with quoted string followed by unquoted continuation
            if (line.trim().startsWith("description:") && line.contains("\"")) {
                val descriptionMatch = Regex("""^(\s*description:\s*)"([^"]*)"(.*)$""").find(line)

                if (descriptionMatch != null) {
                    val indent = descriptionMatch.groupValues[1]
                    val firstPart = descriptionMatch.groupValues[2]
                    val remainder = descriptionMatch.groupValues[3].trim()

                    // Look ahead for continuation lines (indented lines without key:)
                    val continuationLines = mutableListOf<String>()
                    var j = i + 1

                    while (j < lines.size) {
                        val nextLine = lines[j]
                        // Check if it's an indented continuation (starts with spaces, no key:)
                        if (nextLine.matches(Regex("""^\s+[^\s\-:].*""")) && !nextLine.trim().startsWith("-")) {
                            continuationLines.add(nextLine.trim())
                            j++
                        } else {
                            break
                        }
                    }

                    // If we found continuation lines, convert to folded scalar format
                    if (continuationLines.isNotEmpty()) {
                        result.add("${indent}>")
                        result.add("  $firstPart${if (remainder.isNotEmpty()) " $remainder" else ""}")
                        continuationLines.forEach { cont ->
                            result.add("  $cont")
                        }
                        i = j
                        continue
                    }
                }
            }

            result.add(line)
            i++
        }

        return result.joinToString("\n")
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
