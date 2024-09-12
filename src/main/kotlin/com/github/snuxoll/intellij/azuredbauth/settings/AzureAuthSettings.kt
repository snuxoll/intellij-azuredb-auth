package com.github.snuxoll.intellij.azuredbauth.settings

import com.github.snuxoll.intellij.azuredbauth.database.AuthType
import com.intellij.openapi.components.*
import com.intellij.openapi.project.Project

@Service(Service.Level.PROJECT)
@State(
    name = "com.github.snuxoll.intellij.azuredbauth.settings.AzureAuthSettings",
    storages = [Storage("AzureDBAuthPlugin.xml")]
)
class AzureAuthSettings : SimplePersistentStateComponent<AzureAuthSettings.State>(State()) {
    class State : BaseState() {
        @get:ReportValue
        var authType by enum<AuthType>(AuthType.DEFAULT)
    }

    var authType: AuthType
        get() = state.authType
        set(value) {
            state.authType = value
        }

    companion object {
        @JvmStatic
        fun getInstance(project: Project): AzureAuthSettings =
            project.getService(AzureAuthSettings::class.java)
    }
}