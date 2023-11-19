package com.propin.android.ui.composables

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.propin.android.R
import com.propin.android.ui.theme.PropInTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PropAppBar(
    modifier: Modifier = Modifier,
    @StringRes titleRes: Int,
    backPressEnabled: Boolean = true,
    onBackPress: () -> Unit = {}
) {
    TopAppBar(
        modifier = modifier,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
            navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
            actionIconContentColor = MaterialTheme.colorScheme.onPrimary
        ),
        title = { Text(stringResource(id = titleRes)) },
        navigationIcon = {
            if (backPressEnabled) {
                IconButton(onClick = onBackPress) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(id = R.string.content_description_navigate_back)
                    )
                }
            }
        })
}

@Preview
@Composable
fun PropInAppBarPreview() {
    PropInTheme {
        PropAppBar(titleRes = R.string.app_name)
    }
}