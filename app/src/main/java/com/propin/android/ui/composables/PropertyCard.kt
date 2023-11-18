/*
 * Copyright (C) 2022. Mongezi Nombewu
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.propin.android.ui.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.propin.android.ui.theme.PropInTheme
import com.propin.properties.domain.model.Address
import com.propin.properties.domain.model.PaymentFrequency
import com.propin.properties.domain.model.Property

@Composable
fun PropertyCard(property: Property) {
    Column {
        Text(text = property.description)
        property.address.lineOne.let {
            Text(text = it, maxLines = 3)
        }
    }
}

@Preview
@Composable
fun PreviewPropertyCard() {
    val property = Property(
        id = 0,
        description = "21 Jump Street",
        address = Address(
            "The Block",
            "21 Jump Street",
            "Down Under",
            "Auckland",
            "Australia",
            "1990"
        ),
        defaultRate = 100,
        defaultPaymentFrequency = PaymentFrequency.MONTHLY
    )

    PropInTheme {
        Surface {
            PropertyCard(property = property)
        }
    }
}