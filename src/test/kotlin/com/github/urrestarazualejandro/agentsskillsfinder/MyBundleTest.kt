package com.github.urrestarazualejandro.agentsskillsfinder

import org.junit.Test

class MyBundleTest {

    @Test
    fun testMessageBundle() {
        val message = MyBundle.message("name")
        assertNotNull(message)
    }

    @Test
    fun testMessagePointer() {
        val messagePointer = MyBundle.messagePointer("name")
        assertNotNull(messagePointer)
    }

    @Test
    fun testMessageWithParams() {
        val message = MyBundle.message("name", "param1", "param2")
        assertNotNull(message)
    }

    private fun assertNotNull(value: Any?) {
        assert(value != null) { "Value should not be null" }
    }
}
