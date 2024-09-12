package com.github.snuxoll.intellij.azuredbauth.database

import com.azure.identity.DefaultAzureCredential
import com.azure.identity.DefaultAzureCredentialBuilder
import com.intellij.openapi.components.Service
import com.intellij.openapi.diagnostic.logger
import com.intellij.openapi.project.Project

@Service(Service.Level.PROJECT)
class AzureAuthService(private val project: Project) {
    init {
        LOG.warn("Azure Auth service initialization")
    }

    fun defaultAuthToken() {
        DefaultAzureCredentialBuilder().build()
    }

    companion object {
        private val LOG = logger<AzureAuthService>()
    }
}