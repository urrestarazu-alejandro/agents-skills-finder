package com.github.urrestarazualejandro.agentsskillsfinder.toolWindow

import com.github.urrestarazualejandro.agentsskillsfinder.model.Skill
import com.github.urrestarazualejandro.agentsskillsfinder.toolWindow.SkillRowFilter
import com.github.urrestarazualejandro.agentsskillsfinder.toolWindow.SkillsTableModel
import org.junit.Before
import org.junit.Test
import javax.swing.RowFilter

class SkillRowFilterTest {

    private lateinit var tableModel: SkillsTableModel
    private lateinit var skillRowFilter: SkillRowFilter

    private val skills = listOf(
        Skill(
            name = "java-pro",
            description = "Advanced Java development skills for enterprise applications",
            relativePath = "/skills/java-pro",
            source = "personal",
            risk = "safe",
            filePath = "/test/skills/java-pro"
        ),
        Skill(
            name = "python-dev",
            description = "Python development skills",
            relativePath = "/skills/python-dev",
            source = "personal",
            risk = "safe",
            filePath = "/test/skills/python-dev"
        ),
        Skill(
            name = "javascript",
            description = "JavaScript programming language skills",
            relativePath = "/skills/javascript",
            source = "personal",
            risk = "safe",
            filePath = "/test/skills/javascript"
        ),
        Skill(
            name = "kotlin-advanced",
            description = "Advanced Kotlin features and best practices",
            relativePath = "/skills/kotlin-advanced",
            source = "personal",
            risk = "safe",
            filePath = "/test/skills/kotlin-advanced"
        ),
        Skill(
            name = "kotlin-coroutines-expert",
            description = "Expert patterns for Kotlin Coroutines and Flow, covering structured concurrency, error handling, and testing",
            relativePath = "/skills/kotlin-coroutines-expert",
            source = "community",
            risk = "safe",
            filePath = "/test/skills/kotlin-coroutines-expert"
        ),
        Skill(
            name = "react-patterns",
            description = "Modern React patterns and principles. Hooks, composition, performance, TypeScript best practices",
            relativePath = "/skills/react-patterns",
            source = "community",
            risk = "unknown",
            filePath = "/test/skills/react-patterns"
        ),
        Skill(
            name = "angular-migration",
            description = "Angular migration patterns and best practices for upgrading Angular applications",
            relativePath = "/skills/angular-migration",
            source = "community",
            risk = "safe",
            filePath = "/test/skills/angular-migration"
        ),
        Skill(
            name = "database-optimizer",
            description = "Database optimization techniques for PostgreSQL, performance tuning and query optimization",
            relativePath = "/skills/database-optimizer",
            source = "community",
            risk = "safe",
            filePath = "/test/skills/database-optimizer"
        ),
        Skill(
            name = "cloud-architect",
            description = "Cloud architecture design patterns and best practices for AWS, Azure, GCP",
            relativePath = "/skills/cloud-architect",
            source = "community",
            risk = "safe",
            filePath = "/test/skills/cloud-architect"
        ),
        Skill(
            name = "dotnet-architect",
            description = ".NET architecture patterns, Entity Framework, Dapper, and enterprise patterns",
            relativePath = "/skills/dotnet-architect",
            source = "community",
            risk = "safe",
            filePath = "/test/skills/dotnet-architect"
        )
    )

    @Before
    fun setUp() {
        tableModel = SkillsTableModel()
        skillRowFilter = SkillRowFilter()
        tableModel.setSkills(skills)
    }

    @Test
    fun testSearchJavaProExactName() {
        skillRowFilter.setSearchText("java-pro")
        
        val filteredModel = filterModel()
        
        assert(filteredModel.any { it.name == "java-pro" }) { "Should find java-pro with exact name search" }
    }

    @Test
    fun testSearchJavaPartialName() {
        skillRowFilter.setSearchText("java")
        
        val filteredModel = filterModel()
        
        assert(filteredModel.any { it.name == "java-pro" }) { "Should find java-pro when searching 'java'" }
        assert(filteredModel.any { it.name == "javascript" }) { "Should find javascript when searching 'java' since it starts with java" }
    }

    @Test
    fun testSearchPro() {
        skillRowFilter.setSearchText("pro")
        
        val filteredModel = filterModel()
        
        assert(filteredModel.any { it.name == "java-pro" }) { "Should find java-pro when searching 'pro'" }
    }

    @Test
    fun testSearchAdvanced() {
        skillRowFilter.setSearchText("advanced")
        
        val filteredModel = filterModel()
        
        assert(filteredModel.any { it.name == "java-pro" }) { "Should find java-pro when searching 'advanced' in description" }
        assert(filteredModel.any { it.name == "kotlin-advanced" }) { "Should find kotlin-advanced when searching 'advanced'" }
    }

    @Test
    fun testSearchEnterprise() {
        skillRowFilter.setSearchText("enterprise")
        
        val filteredModel = filterModel()
        
        assert(filteredModel.any { it.name == "java-pro" }) { "Should find java-pro when searching 'enterprise' in description" }
    }

    @Test
    fun testSearchMultipleTokensBothMatch() {
        skillRowFilter.setSearchText("java advanced")
        
        val filteredModel = filterModel()
        
        assert(filteredModel.any { it.name == "java-pro" }) { "Should find java-pro when searching 'java advanced'" }
        assert(!filteredModel.any { it.name == "kotlin-advanced" }) { "Should NOT find kotlin-advanced with strict search (java != kotlin)" }
    }

    @Test
    fun testSearchKotlinCoroutines() {
        skillRowFilter.setSearchText("coroutines")
        
        val filteredModel = filterModel()
        
        assert(filteredModel.any { it.name == "kotlin-coroutines-expert" }) { "Should find kotlin-coroutines-expert when searching 'coroutines'" }
    }

    @Test
    fun testSearchKotlinCoroutinesExpert() {
        skillRowFilter.setSearchText("kotlin-coroutines")
        
        val filteredModel = filterModel()
        
        assert(filteredModel.any { it.name == "kotlin-coroutines-expert" }) { "Should find kotlin-coroutines-expert" }
    }

    @Test
    fun testSearchReactPatterns() {
        skillRowFilter.setSearchText("react")
        
        val filteredModel = filterModel()
        
        assert(filteredModel.any { it.name == "react-patterns" }) { "Should find react-patterns when searching 'react'" }
    }

    @Test
    fun testSearchAngular() {
        skillRowFilter.setSearchText("angular")
        
        val filteredModel = filterModel()
        
        assert(filteredModel.any { it.name == "angular-migration" }) { "Should find angular-migration when searching 'angular'" }
    }

    @Test
    fun testSearchDatabase() {
        skillRowFilter.setSearchText("database")
        
        val filteredModel = filterModel()
        
        assert(filteredModel.any { it.name == "database-optimizer" }) { "Should find database-optimizer when searching 'database'" }
    }

    @Test
    fun testSearchPostgresql() {
        skillRowFilter.setSearchText("postgresql")
        
        val filteredModel = filterModel()
        
        assert(filteredModel.any { it.name == "database-optimizer" }) { "Should find database-optimizer when searching 'postgresql' in description" }
    }

    @Test
    fun testSearchCaseInsensitive() {
        skillRowFilter.setSearchText("KOTLIN")
        
        val filteredModel = filterModel()
        
        assert(filteredModel.any { it.name == "kotlin-advanced" }) { "Should find kotlin-advanced with case insensitive search" }
        assert(filteredModel.any { it.name == "kotlin-coroutines-expert" }) { "Should find kotlin-coroutines-expert with case insensitive search" }
    }

    @Test
    fun testEmptySearchReturnsAll() {
        skillRowFilter.setSearchText("")
        
        val filteredModel = filterModel()
        
        assert(filteredModel.size == skills.size) { "Empty search should return all skills" }
    }

    @Test
    fun testNoMatchReturnsEmpty() {
        skillRowFilter.setSearchText("ruby")
        
        val filteredModel = filterModel()
        
        assert(filteredModel.isEmpty()) { "Search with no match should return empty" }
    }

    @Test
    fun testSearchPerformance() {
        skillRowFilter.setSearchText("performance")
        
        val filteredModel = filterModel()
        
        assert(filteredModel.any { it.name == "react-patterns" }) { "Should find react-patterns when searching 'performance' in description" }
    }

    @Test
    fun testSearchDescriptionOnly() {
        skillRowFilter.setSearchText("typescript")
        
        val filteredModel = filterModel()
        
        assert(filteredModel.any { it.name == "react-patterns" }) { "Should find react-patterns when searching 'typescript' in description" }
    }

    @Test
    fun testSearchFlow() {
        skillRowFilter.setSearchText("flow")
        
        val filteredModel = filterModel()
        
        assert(filteredModel.any { it.name == "kotlin-coroutines-expert" }) { "Should find kotlin-coroutines-expert when searching 'flow' in description" }
    }

    @Test
    fun testSearchArchitectPrefix() {
        skillRowFilter.setSearchText("archit")
        
        val filteredModel = filterModel()
        
        assert(filteredModel.any { it.name == "cloud-architect" }) { "Should find cloud-architect when searching 'archit'" }
        assert(filteredModel.any { it.name == "dotnet-architect" }) { "Should find dotnet-architect when searching 'archit'" }
    }

    @Test
    fun testSearchDotnetPrefix() {
        skillRowFilter.setSearchText("dotnet")
        
        val filteredModel = filterModel()
        
        assert(filteredModel.any { it.name == "dotnet-architect" }) { "Should find dotnet-architect when searching 'dotnet'" }
    }

    private fun filterModel(): List<Skill> {
        val result = mutableListOf<Skill>()
        for (i in 0 until tableModel.rowCount) {
            if (skillRowFilter.include(createEntry(i))) {
                tableModel.getSkillAt(i)?.let { result.add(it) }
            }
        }
        return result
    }

    private fun createEntry(rowIndex: Int): RowFilter.Entry<SkillsTableModel, Int> {
        return object : RowFilter.Entry<SkillsTableModel, Int>() {
            override fun getModel(): SkillsTableModel = tableModel
            override fun getIdentifier(): Int = rowIndex
            override fun getValueCount(): Int = tableModel.columnCount
            override fun getValue(index: Int): Any = tableModel.getValueAt(rowIndex, index)
        }
    }
}
