package com.github.urrestarazualejandro.agentsskillsfinder.toolWindow

import com.github.urrestarazualejandro.agentsskillsfinder.MyBundle
import com.github.urrestarazualejandro.agentsskillsfinder.model.Skill
import com.github.urrestarazualejandro.agentsskillsfinder.services.SkillsService
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.diagnostic.logger
import com.intellij.openapi.project.Project
import com.intellij.ui.JBColor
import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBScrollPane
import com.intellij.ui.components.JBTextField
import com.intellij.ui.table.JBTable
import java.awt.BorderLayout
import java.awt.datatransfer.Clipboard
import java.awt.datatransfer.StringSelection
import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.util.Timer
import java.util.TimerTask
import javax.swing.JPanel
import javax.swing.JPopupMenu
import javax.swing.JMenuItem
import javax.swing.ListSelectionModel
import javax.swing.RowSorter
import javax.swing.SortOrder
import javax.swing.table.TableColumn

class SkillsToolWindowContent(
    private val project: Project,
    private val skillsService: SkillsService
) {

    private val log = logger<SkillsToolWindowContent>()
    private val tableModel = SkillsTableModel()
    private val rowFilter = SkillRowFilter()
    private var table: JBTable
    private var searchField: JBTextField
    private var statusLabel: JBLabel

    private var searchDebounceTimer: Timer? = null

    init {
        table = createTable()
        searchField = createSearchField()
        statusLabel = createStatusLabel()

        skillsService.addListener { skills ->
            ApplicationManager.getApplication().invokeLater {
                updateTable(skills)
            }
        }

        skillsService.loadSkillsAsync()
    }

    fun getContent(): JPanel {
        return JPanel(BorderLayout()).apply {
            add(searchField, BorderLayout.NORTH)
            add(JBScrollPane(table), BorderLayout.CENTER)
            add(statusLabel, BorderLayout.SOUTH)
        }
    }

    private fun createTable(): JBTable {
        val table = JBTable(tableModel).apply {
            setSelectionMode(ListSelectionModel.SINGLE_SELECTION)
            autoCreateRowSorter = true
            rowSorter = javax.swing.table.TableRowSorter<SkillsTableModel>().apply {
                setModel(tableModel)
                sortKeys = listOf(RowSorter.SortKey(0, SortOrder.ASCENDING))
            }

            val columnModel = columnModel
            listOf(0, 1, 2).forEach { col ->
                val column: TableColumn = columnModel.getColumn(col)
                when (col) {
                    0 -> column.preferredWidth = 150
                    1 -> column.preferredWidth = 300
                    2 -> column.preferredWidth = 200
                }
            }
        }

        table.rowSorter.addRowSorterListener {
            updateStatusLabel()
        }

        table.addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(e: MouseEvent) {
                if (e.clickCount == 2) {
                    copySelectedPath()
                }
            }

            override fun mousePressed(e: MouseEvent) {
                if (e.isPopupTrigger) {
                    showPopupMenu(e)
                }
            }

            override fun mouseReleased(e: MouseEvent) {
                if (e.isPopupTrigger) {
                    showPopupMenu(e)
                }
            }
        })

        return table
    }

    private fun createSearchField(): JBTextField {
        return JBTextField().apply {
            addKeyListener(object : KeyAdapter() {
                override fun keyReleased(e: KeyEvent) {
                    scheduleSearch()
                }
            })
        }
    }

    private fun createStatusLabel(): JBLabel {
        return JBLabel(MyBundle.message("skills.status.loading")).apply {
            foreground = JBColor.GRAY
        }
    }

    private fun scheduleSearch() {
        searchDebounceTimer?.cancel()
        searchDebounceTimer = Timer().apply {
            schedule(object : TimerTask() {
                override fun run() {
                    ApplicationManager.getApplication().invokeLater {
                        performSearch()
                    }
                }
            }, 300)
        }
    }

    private fun performSearch() {
        val text = searchField.text
        rowFilter.setSearchText(text)
        try {
            @Suppress("UNCHECKED_CAST")
            (table.rowSorter as? javax.swing.table.TableRowSorter<SkillsTableModel>)?.rowFilter = rowFilter
        } catch (e: Exception) {
            log.warn("Filter error: ${e.message}")
        }
        updateStatusLabel()
    }

    private fun updateTable(skills: List<Skill>) {
        tableModel.setSkills(skills)
        updateStatusLabel()
    }

    private fun updateStatusLabel() {
        val total = tableModel.rowCount
        val filtered = table.rowCount
        statusLabel.text = if (total == filtered) {
            MyBundle.message("skills.status.total", total)
        } else {
            MyBundle.message("skills.status.filtered", filtered, total)
        }
    }

    private fun showPopupMenu(e: MouseEvent) {
        val row = table.rowAtPoint(e.point)
        if (row >= 0) {
            table.setRowSelectionInterval(row, row)
            val popup = JPopupMenu()
            val copyPathItem = JMenuItem(MyBundle.message("skills.action.copyPath"))
            copyPathItem.addActionListener { copySelectedPath() }
            popup.add(copyPathItem)
            popup.show(table, e.x, e.y)
        }
    }

    private fun copySelectedPath() {
        val selectedRow = table.selectedRow
        if (selectedRow >= 0) {
            val modelRow = table.convertRowIndexToModel(selectedRow)
            val skill = tableModel.getSkillAt(modelRow)
            skill?.let {
                try {
                    val clipboard: Clipboard = java.awt.Toolkit.getDefaultToolkit().systemClipboard
                    clipboard.setContents(StringSelection(it.relativePath), null)
                    showNotification(MyBundle.message("skills.notification.copied", it.relativePath))
                } catch (ex: Exception) {
                    log.error("Clipboard error: ${ex.message}")
                }
            }
        }
    }

    private fun showNotification(message: String) {
        val notification = com.intellij.notification.Notification(
            "Agent Skills",
            MyBundle.message("skills.notification.title"),
            message,
            com.intellij.notification.NotificationType.INFORMATION
        )
        notification.notify(project)
    }
}
