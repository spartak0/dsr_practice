package com.example.dsr_practice.ui.edit_trigger_screen

import androidx.annotation.StringRes

sealed class VerifyResult(@StringRes val messageId: Int? = null) {
    class Success : VerifyResult()
    class Error(@StringRes messageId: Int) : VerifyResult(messageId = messageId)
}