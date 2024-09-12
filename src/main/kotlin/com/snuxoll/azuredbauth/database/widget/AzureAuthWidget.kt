package com.snuxoll.azuredbauth.database.widget

import com.snuxoll.azuredbauth.Messages
import com.snuxoll.azuredbauth.database.AZURE_AUTH_TYPE_KEY
import com.snuxoll.azuredbauth.database.AuthType
import com.snuxoll.azuredbauth.database.AzureAuthProvider
import com.snuxoll.azuredbauth.settings.AzureAuthSettings
import com.snuxoll.azuredbauth.ui.AuthTypeComboRenderer
import com.snuxoll.azuredbauth.ui.extensions.anchorLeft
import com.snuxoll.azuredbauth.ui.extensions.fillHorizontal
import com.snuxoll.azuredbauth.ui.extensions.gridConstraints
import com.snuxoll.azuredbauth.ui.extensions.sizeFor
import com.intellij.database.access.DatabaseCredentials
import com.intellij.database.dataSource.DatabaseAuthProvider
import com.intellij.database.dataSource.DatabaseConnectionConfig
import com.intellij.database.dataSource.DatabaseConnectionInterceptor
import com.intellij.database.dataSource.url.template.MutableParametersHolder
import com.intellij.database.dataSource.url.template.ParametersHolder
import com.intellij.openapi.observable.properties.AtomicProperty
import com.intellij.openapi.observable.util.bind
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.ComboBox
import com.intellij.ui.CardLayoutPanel
import com.intellij.ui.components.JBLabel
import com.intellij.uiDesigner.core.GridConstraints
import com.intellij.uiDesigner.core.GridLayoutManager
import javax.swing.JComponent
import javax.swing.JPanel

@Suppress("UnstableApiUsage")
class AzureAuthWidget(
    private val project: Project?,
    @Suppress("UNUSED_PARAMETER") credentials: DatabaseCredentials,
    config: DatabaseConnectionConfig
) :
    DatabaseAuthProvider.AuthWidget {

    private val model = Model(
        config.getAdditionalProperty(AZURE_AUTH_TYPE_KEY)?.let { AuthType.valueOf(it) }
            ?: projectSettings.authType,
        config.dataSource.username
    )

    private val projectSettings get() = AzureAuthSettings.getInstance(project!!)

    private val panel: JPanel = JPanel(GridLayoutManager(2, 2))

    private val credentialPanels = mapOf(
        AuthType.DEFAULT to DefaultCredentialPanel(model.usernameProperty),
        AuthType.AZURE_CLI to AzureCLICredentialPanel(model.usernameProperty),
        AuthType.INTERACTIVE to InteractiveCredentialPanel(model.usernameProperty),
        AuthType.MANAGED_IDENTITY to ManagedIdentityCredentialPanel(),
        AuthType.SERVICE_PRINCIPAL to ServicePrincipalCredentialPanel()
    )

    private val settingsPanel = object : CardLayoutPanel<AuthType, CredentialPanel, CredentialPanel>() {
        override fun prepare(authType: AuthType): CredentialPanel =
            credentialPanels[authType] ?: throw IllegalArgumentException("Unknown auth type: $authType")

        override fun create(panel: CredentialPanel): CredentialPanel =
            panel
    }

    private val authTypeLabel = JBLabel(Messages.authModeLabel)
    private val authTypeComboBox = ComboBox(AuthType.entries.filter { it.enabled }.toTypedArray()).apply {
        renderer = AuthTypeComboRenderer
    }.bind(model.authTypeProperty)

    init {
        settingsPanel.select(model.authType, true)
        model.authTypeProperty.afterChange { new ->
            settingsPanel.select(new, true)
        }
        panel.add(authTypeLabel, gridConstraints(0, 0) {
            sizeFor(authTypeLabel)
            anchorLeft()
        })
        panel.add(authTypeComboBox, gridConstraints(0, 1).fillHorizontal())
        panel.add(settingsPanel, gridConstraints(1, 0) {
            colSpan = 2
            fill = GridConstraints.FILL_BOTH
        })
    }


    override fun forceSave() {

    }

    override fun save(config: DatabaseConnectionConfig, copyCredentials: Boolean) {
        config.dataSource.username = model.username
        config.setAdditionalProperty(AZURE_AUTH_TYPE_KEY, model.authType.name)
        val authProvider = DatabaseConnectionInterceptor.EP_NAME.findExtension(AzureAuthProvider::class.java)
        authProvider?.clearCredentials(config)
    }

    override fun getComponent(): JComponent = panel

    override fun getPreferredFocusedComponent(): JComponent = authTypeComboBox

    override fun hidePassword() {

    }

    override fun isPasswordChanged(): Boolean {
        return false
    }

    override fun onChanged(r: Runnable) {

    }

    override fun reloadCredentials() {

    }

    override fun updateFromUrl(holder: ParametersHolder) {

    }

    override fun updateUrl(model: MutableParametersHolder) {
    }

    private class Model(defaultAuthType: AuthType, username: String) {
        var authTypeProperty = AtomicProperty(defaultAuthType)
        var authType by authTypeProperty

        var usernameProperty = AtomicProperty(username)
        var username by usernameProperty
    }
}