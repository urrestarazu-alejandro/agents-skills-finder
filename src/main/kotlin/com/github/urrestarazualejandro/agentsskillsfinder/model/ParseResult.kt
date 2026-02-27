package com.github.urrestarazualejandro.agentsskillsfinder.model

sealed class ParseResult {
    data class Success(val skill: Skill) : ParseResult()
    data class Error(val filePath: String, val reason: String) : ParseResult()
}
