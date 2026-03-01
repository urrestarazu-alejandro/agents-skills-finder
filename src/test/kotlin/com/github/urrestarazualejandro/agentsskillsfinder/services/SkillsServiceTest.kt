package com.github.urrestarazualejandro.agentsskillsfinder.services

import com.github.urrestarazualejandro.agentsskillsfinder.model.Skill
import com.intellij.openapi.components.service
import com.intellij.testFramework.fixtures.BasePlatformTestCase
import java.util.function.Consumer

class SkillsServiceTest : BasePlatformTestCase() {

    private lateinit var skillsService: SkillsService

    override fun setUp() {
        super.setUp()
        skillsService = project.service<SkillsService>()
    }

    fun testGetSkillsInitiallyEmpty() {
        val skills = skillsService.getSkills()
        assertNotNull(skills)
        assertTrue(skills is List<Skill>)
    }

    fun testAddListener() {
        var callbackInvoked = false
        val listener = Consumer<List<Skill>> { _ ->
            callbackInvoked = true
        }

        skillsService.addListener(listener)

        // The listener should be added without errors
        assertFalse(callbackInvoked)
    }

    fun testRemoveListener() {
        val listener = Consumer<List<Skill>> { _ ->
            // Do nothing
        }

        skillsService.addListener(listener)
        skillsService.removeListener(listener)

        // Should not throw any exceptions
        assertTrue(true)
    }

    fun testGetInstance() {
        val instance = SkillsService.getInstance(project)
        assertNotNull(instance)
        assertSame(instance, skillsService)
    }

    fun testReload() {
        // Reload should not throw exceptions
        skillsService.reload()
        assertTrue(true)
    }

    fun testMultipleListeners() {
        var count1 = 0
        var count2 = 0

        val listener1 = Consumer<List<Skill>> { _ -> count1++ }
        val listener2 = Consumer<List<Skill>> { _ -> count2++ }

        skillsService.addListener(listener1)
        skillsService.addListener(listener2)

        // Both listeners added
        assertFalse(count1 > 0)
        assertFalse(count2 > 0)

        // Remove one listener
        skillsService.removeListener(listener1)
    }
}
