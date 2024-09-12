package com.github.snuxoll.intellij.azuredbauth.database.widget

import com.github.snuxoll.intellij.azuredbauth.Messages
import com.github.snuxoll.intellij.azuredbauth.ui.extensions.anchorLeft
import com.github.snuxoll.intellij.azuredbauth.ui.extensions.gridConstraints
import com.github.snuxoll.intellij.azuredbauth.ui.extensions.sizeFor
import com.intellij.openapi.observable.properties.AtomicProperty
import com.intellij.openapi.observable.util.bind
import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBTextField
import com.intellij.uiDesigner.core.GridConstraints
import com.intellij.uiDesigner.core.GridLayoutManager
import javax.swing.JPanel

sealed class CredentialPanel(
    layoutManager: GridLayoutManager = GridLayoutManager(2, 2)
) : JPanel(layoutManager) {
}

sealed class UsernameCredentialPanel(
    usernameProperty: AtomicProperty<String>
) : CredentialPanel() {
    protected val usernameLabel = JBLabel(Messages.usernameLabel)
    protected val usernameField = JBTextField().bind(usernameProperty)

    init {
        this.add(usernameLabel, gridConstraints(0, 0) {
            sizeFor(usernameLabel)
            anchorLeft()
        })
        this.add(usernameField, gridConstraints(0, 1) {
            fill = GridConstraints.FILL_HORIZONTAL
        })
    }
}

class DefaultCredentialPanel(
    usernameProperty: AtomicProperty<String>
) : UsernameCredentialPanel(usernameProperty) {

}

class AzureCLICredentialPanel(
    usernameProperty: AtomicProperty<String>
) : UsernameCredentialPanel(usernameProperty) {

}

class ManagedIdentityCredentialPanel(
) : CredentialPanel() {

}

class ServicePrincipalCredentialPanel(
) : CredentialPanel() {

}