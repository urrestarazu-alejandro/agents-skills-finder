package com.github.urrestarazualejandro.agentsskillsfinder.services

import com.github.urrestarazualejandro.agentsskillsfinder.model.Skill
import com.github.urrestarazualejandro.agentsskillsfinder.scanner.SkillsFileScanner
import com.intellij.openapi.components.Service
import com.intellij.openapi.project.Project
import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.CopyOnWriteArraySet
import java.util.function.Consumer

@Service(Service.Level.PROJECT)
class SkillsService(private val project: Project) {

    private val skillsCache = CopyOnWriteArrayList<Skill>()
    private val listeners = CopyOnWriteArraySet<Consumer<List<Skill>>>()
    private val scanner = SkillsFileScanner(project)
    private var isLoading = false

    fun loadSkillsAsync() {
        if (isLoading) return
        isLoading = true

        scanner.scan { skills ->
            skillsCache.clear()
            skillsCache.addAll(skills)
            isLoading = false
            notifyListeners()
        }
    }

    fun getSkills(): List<Skill> = skillsCache.toList()

    fun reload() {
        isLoading = false
        loadSkillsAsync()
    }

    fun addListener(listener: Consumer<List<Skill>>) {
        listeners.add(listener)
    }

    fun removeListener(listener: Consumer<List<Skill>>) {
        listeners.remove(listener)
    }

    private fun notifyListeners() {
        val currentSkills = skillsCache.toList()
        listeners.forEach { it.accept(currentSkills) }
    }

    companion object {
        fun getInstance(project: Project): SkillsService {
            return project.getService(SkillsService::class.java)
        }
    }
}
