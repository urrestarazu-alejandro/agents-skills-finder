package com.github.urrestarazualejandro.agentsskillsfinder.parser

import com.github.urrestarazualejandro.agentsskillsfinder.model.ParseResult
import org.junit.Before
import org.junit.Test
import java.io.File

class SkillParserTest {

    private lateinit var parser: SkillParser
    private lateinit var tempDir: File

    @Before
    fun setUp() {
        parser = SkillParser()
        tempDir = File.createTempFile("skill_parser_test", "_dir").apply { delete(); mkdirs() }
    }

    @Test
    fun testParseSimpleYaml() {
        val content = """
            |---
            |name: test-skill
            |description: A simple test skill
            |---
            |# Content
        """.trimMargin()

        val file = File(tempDir, "test.md").apply { writeText(content) }
        val result = parser.parse(file, tempDir.absolutePath)

        assert(result is ParseResult.Success) { "Should parse successfully" }
        val skill = (result as ParseResult.Success).skill
        assert(skill.name == "test-skill") { "Name should match" }
        assert(skill.description == "A simple test skill") { "Description should match" }
    }

    @Test
    fun testParseYamlWithFoldedScalar() {
        val content = """
            |---
            |name: java-pro
            |description: "Master Java 21+ with modern features like virtual threads, pattern"
            |  matching, and Spring Boot 3.x. Expert in the latest Java ecosystem including
            |  GraalVM, Project Loom, and cloud-native patterns. Use PROACTIVELY for Java
            |  development, microservices architecture, or performance optimization.
            |risk: unknown
            |source: community
            |---
        """.trimMargin()

        val file = File(tempDir, "java-pro.md").apply { writeText(content) }
        val result = parser.parse(file, tempDir.absolutePath)

        assert(result is ParseResult.Success) { "Should parse successfully. Error: ${(result as? ParseResult.Error)?.reason}" }
    }

    @Test
    fun testParseYamlWithLiteralScalar() {
        val content = """
            |---
            |name: schema-markup
            |description: ">"
            |  Design, validate, and optimize schema.org structured data for eligibility,
            |  correctness, and measurable SEO impact.
            |allowed-tools: Read, Glob, Grep
            |risk: unknown
            |source: community
            |---
        """.trimMargin()

        val file = File(tempDir, "schema.md").apply { writeText(content) }
        val result = parser.parse(file, tempDir.absolutePath)

        assert(result is ParseResult.Success) { "Should parse successfully: ${(result as? ParseResult.Error)?.reason}" }
    }

    @Test
    fun testParseYamlWithNestedMetadata() {
        val content = """
            |---
            |name: azure-eventgrid-py
            |description: Azure Event Grid SDK for Python
            |metadata:
            |  model: opus
            |risk: unknown
            |source: community
            |---
        """.trimMargin()

        val file = File(tempDir, "azure.md").apply { writeText(content) }
        val result = parser.parse(file, tempDir.absolutePath)

        assert(result is ParseResult.Success) { "Should parse successfully: ${(result as? ParseResult.Error)?.reason}" }
        if (result is ParseResult.Success) {
            assert(result.skill.name == "azure-eventgrid-py")
            assert(result.skill.source == "community")
            assert(result.skill.risk == "unknown")
        }
    }

    @Test
    fun testParseYamlWithSpecialCharsInDescription() {
        val content = """
            |---
            |name: security-auditor
            |description: cybersecurity, and compliance frameworks
            |risk: unknown
            |source: community
            |---
        """.trimMargin()

        val file = File(tempDir, "security.md").apply { writeText(content) }
        val result = parser.parse(file, tempDir.absolutePath)

        assert(result is ParseResult.Success) { "Should parse successfully: ${(result as? ParseResult.Error)?.reason}" }
    }

    @Test
    fun testParseYamlWithQuotes() {
        val content = """
            |---
            |name: market-sizing-analysis
            |description: "determine SAM", "estimate SOM", and market analysis
            |risk: unknown
            |source: community
            |---
        """.trimMargin()

        val file = File(tempDir, "market.md").apply { writeText(content) }
        val result = parser.parse(file, tempDir.absolutePath)

        assert(result is ParseResult.Success) { "Should parse successfully: ${(result as? ParseResult.Error)?.reason}" }
    }

    @Test
    fun testParseMissingName() {
        val content = """
            |---
            |description: Some description
            |---
        """.trimMargin()

        val file = File(tempDir, "test.md").apply { writeText(content) }
        val result = parser.parse(file, tempDir.absolutePath)

        assert(result is ParseResult.Error) { "Should fail with missing name" }
    }

    @Test
    fun testParseMissingDescription() {
        val content = """
            |---
            |name: test-skill
            |---
        """.trimMargin()

        val file = File(tempDir, "test.md").apply { writeText(content) }
        val result = parser.parse(file, tempDir.absolutePath)

        assert(result is ParseResult.Error) { "Should fail with missing description" }
    }

    @Test
    fun testParseNoFrontMatter() {
        val content = "# Just some content without front matter"

        val file = File(tempDir, "test.md").apply { writeText(content) }
        val result = parser.parse(file, tempDir.absolutePath)

        assert(result is ParseResult.Error) { "Should fail without front matter" }
    }

    @Test
    fun testParseWithAllFields() {
        val content = """
            |---
            |name: full-skill
            |description: A complete skill with all fields
            |source: community
            |risk: safe
            |---
        """.trimMargin()

        val file = File(tempDir, "full.md").apply { writeText(content) }
        val result = parser.parse(file, tempDir.absolutePath)

        assert(result is ParseResult.Success) { "Should parse successfully" }
        val skill = (result as ParseResult.Success).skill
        assert(skill.name == "full-skill")
        assert(skill.description == "A complete skill with all fields")
        assert(skill.source == "community")
        assert(skill.risk == "safe")
    }
}
