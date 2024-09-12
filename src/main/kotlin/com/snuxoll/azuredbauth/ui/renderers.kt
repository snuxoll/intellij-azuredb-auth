package com.snuxoll.azuredbauth.ui

import com.snuxoll.azuredbauth.database.AuthType
import java.awt.Component
import javax.swing.DefaultListCellRenderer
import javax.swing.JList
import javax.swing.ListCellRenderer

object AuthTypeComboRenderer : ListCellRenderer<AuthType> {
    private val defaultRenderer = DefaultListCellRenderer()
    override fun getListCellRendererComponent(
        list: JList<out AuthType>,
        value: AuthType,
        index: Int,
        isSelected: Boolean,
        cellHasFocus: Boolean
    ): Component {
        val displayName = value.displayName
        return defaultRenderer.getListCellRendererComponent(list, displayName, index, isSelected, cellHasFocus)
    }

}