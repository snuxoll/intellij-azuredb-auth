package com.snuxoll.azuredbauth

import com.intellij.DynamicBundle
import org.jetbrains.annotations.NonNls
import org.jetbrains.annotations.PropertyKey

@NonNls
private const val BUNDLE = "messages.messages"

object Messages : DynamicBundle(BUNDLE) {

    @JvmStatic
    fun message(@PropertyKey(resourceBundle = BUNDLE) key: String, vararg params: Any) =
        getMessage(key, *params)

    @Suppress("unused")
    @JvmStatic
    fun messagePointer(@PropertyKey(resourceBundle = BUNDLE) key: String, vararg params: Any) =
        getLazyMessage(key, *params)

    val usernameLabel get() = message("usernameLabel")
    val displayName get() = message("displayName")

    val defaultAuthModeLabel get() = message("defaultAuthModeLabel")

    val authModeLabel get() = message("authModeLabel")
}
