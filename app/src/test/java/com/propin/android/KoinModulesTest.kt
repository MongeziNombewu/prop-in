package com.propin.android

import com.propin.android.di.appModule
import com.propin.properties.di.propertiesModule
import org.junit.Assert.*
import org.junit.Test
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.test.verify.verify

@OptIn(KoinExperimentalAPI::class)
class KoinModulesTest {

    @Test
    fun `koin properties module verified`() {
        propertiesModule.verify(extraTypes = listOf(com.squareup.sqldelight.ColumnAdapter::class))
    }

    @Test
    fun `koin app module verified`() {
        appModule.verify(extraTypes = listOf(com.squareup.sqldelight.ColumnAdapter::class))
    }
}