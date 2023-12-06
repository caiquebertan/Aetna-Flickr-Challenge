package com.caique.aetnatestflickr.util

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue


fun TextFieldValue.copyAndMoveSelection(text: String) =
    this.copy(text = text, selection = TextRange(text.length))