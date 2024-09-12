package com.github.snuxoll.intellij.azuredbauth.ui.extensions

import com.intellij.uiDesigner.core.GridConstraints
import java.awt.Dimension
import javax.swing.JComponent

class GridConstraintsBuilder(
    var row: Int = 0,
    var column: Int = 0,
    var rowSpan: Int = 1,
    var colSpan: Int = 1,
    var anchor: Int = GridConstraints.ANCHOR_CENTER,
    var fill: Int = GridConstraints.FILL_NONE,
    var hSizePolicy: Int = 3,
    var vSizePolicy: Int = 3,
    var minimumSize: Dimension = Dimension(-1, -1),
    val preferredSize: Dimension = Dimension(-1, -1),
    val maximumSize: Dimension = Dimension(-1, -1)
) {
    fun build(): GridConstraints =
        GridConstraints(
            row,
            column,
            rowSpan,
            colSpan,
            anchor,
            fill,
            hSizePolicy,
            vSizePolicy,
            minimumSize,
            preferredSize,
            maximumSize
        )
}

fun GridConstraintsBuilder.sizeFor(component: JComponent) {
    minimumSize.width = component.minimumSize.width
    minimumSize.height = component.minimumSize.height
    preferredSize.width = component.preferredSize.width
    preferredSize.height = component.preferredSize.height
    maximumSize.width = component.maximumSize.width
    maximumSize.height = component.maximumSize.height
    hSizePolicy = GridConstraints.SIZEPOLICY_FIXED
    vSizePolicy = GridConstraints.SIZEPOLICY_FIXED
}

fun GridConstraintsBuilder.anchorLeft() {
    anchor = GridConstraints.ANCHOR_WEST
}

fun gridConstraints(rowNum: Int, columnNum: Int, init: GridConstraintsBuilder.() -> Unit): GridConstraints {
    val builder = GridConstraintsBuilder(rowNum, columnNum)
    builder.init()
    return builder.build()
}

fun gridConstraints(rowNum: Int, columnNum: Int): GridConstraints =
    GridConstraints().apply {
        row = rowNum
        column = columnNum
    }

fun GridConstraints.row(rowNum: Int): GridConstraints =
    this.apply { row = rowNum }

fun GridConstraints.col(columnNum: Int): GridConstraints =
    this.apply { column = columnNum }

fun GridConstraints.fillHorizontal(): GridConstraints =
    this.apply { fill = GridConstraints.FILL_HORIZONTAL }