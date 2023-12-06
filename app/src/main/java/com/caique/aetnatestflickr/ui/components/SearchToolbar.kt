package com.caique.aetnatestflickr.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.caique.aetnatestflickr.R
import com.caique.aetnatestflickr.ui.design.AppIcons
import com.caique.aetnatestflickr.ui.design.AppTheme
import com.caique.aetnatestflickr.util.copyAndMoveSelection

@Composable
fun SearchToolbar(
    modifier: Modifier = Modifier,
    searchQuery: String = "",
    suggestions: List<String> = emptyList(),
    onSearchTriggered: (String) -> Unit,
) {
    Box(
        modifier = modifier
            .padding(
                horizontal = 16.dp, vertical = 8.dp
    )) {
        SearchTextField(
            onSearchTriggered = onSearchTriggered,
            searchQuery = searchQuery,
            suggestions = suggestions
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
private fun SearchTextField(
    searchQuery: String,
    onSearchTriggered: (String) -> Unit,
    suggestions: List<String>,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    var searchedQuery by remember {
        mutableStateOf(TextFieldValue(searchQuery))
    }

    var expanded by remember { mutableStateOf(false) }

    val onSearchExplicitlyTriggered = {
        keyboardController?.hide()
        onSearchTriggered(searchedQuery.text)
        expanded = false
    }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        }
    ) {
        OutlinedTextField(
            modifier = Modifier.menuAnchor()
                .fillMaxWidth()
                .onFocusChanged { focusState ->
                    expanded = focusState.isFocused
                }
                .testTag("searchTextField"),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
            ),
            placeholder = {
                Text(text = "Search")
            },
            leadingIcon = {
                Icon(
                    imageVector = AppIcons.Search,
                    contentDescription = stringResource(
                        id = R.string.search,
                    ),
                    tint = MaterialTheme.colorScheme.onSurface,
                )
            },
            trailingIcon = {
                if (searchedQuery.text.isNotEmpty()) {
                    IconButton(
                        onClick = {
                            searchedQuery = TextFieldValue("")
                            onSearchExplicitlyTriggered()
                        },
                    ) {
                        Icon(
                            imageVector = AppIcons.Close,
                            contentDescription = stringResource(
                                id = R.string.clear_search_text_content_desc,
                            ),
                            tint = MaterialTheme.colorScheme.onSurface,
                        )
                    }
                }
            },
            value = searchedQuery,
            onValueChange = {
                searchedQuery = it
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    onSearchExplicitlyTriggered()
                },
            ),
            maxLines = 1,
            singleLine = true,
        )
        val filteringOptions = suggestions.filter { it.trim().contains(searchedQuery.text.trim(), ignoreCase = true) }
        if (filteringOptions.isNotEmpty()) {
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = {
               },
            ) {
                filteringOptions.forEach { selectionOption ->
                    DropdownMenuItem(
                        text = { Text(selectionOption) },
                        onClick = {
                            searchedQuery = searchedQuery.copyAndMoveSelection(selectionOption)
                            onSearchExplicitlyTriggered()
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun SearchScreenPreview() {
    AppTheme {
        SearchToolbar(
            onSearchTriggered = {},
            suggestions = emptyList()
        )
    }
}
