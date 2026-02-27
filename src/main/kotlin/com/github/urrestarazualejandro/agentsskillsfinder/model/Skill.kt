package com.github.urrestarazualejandro.agentsskillsfinder.model

data class Skill(
    val name: String,
    val description: String,
    val relativePath: String,
    val source: String? = null,
    val risk: String? = null,
    val filePath: String
)
