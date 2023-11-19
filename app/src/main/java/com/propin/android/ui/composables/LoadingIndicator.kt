package com.propin.android.ui.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.propin.android.ui.theme.PropInTheme

@Composable
fun LoadingIndicator(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize()) {
        LinearProgressIndicator()
    }
}

@Preview
@Composable
fun LoadingPreview() {
    PropInTheme {
        LoadingIndicator()
    }
}