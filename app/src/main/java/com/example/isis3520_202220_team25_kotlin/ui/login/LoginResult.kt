package com.example.isis3520_202220_team25_kotlin.ui.login

import com.example.isis3520_202220_team25_kotlin.ui.view.login.LoggedInUserView

/**
 * Authentication result : success (user details) or error message.
 */
data class LoginResult(
    val success: LoggedInUserView? = null,
    val error: Int? = null
)