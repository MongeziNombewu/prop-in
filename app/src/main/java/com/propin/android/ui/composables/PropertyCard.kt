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

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.propin.android.ui.theme.PropInTheme
import com.propin.properties.domain.model.Address
import com.propin.properties.domain.model.Property

@Composable
fun PropertyCard(
    modifier: Modifier = Modifier,
    property: Property,
    onClick: (Long) -> Unit = {}
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .clickable { onClick(property.id) }
    ) {
        Text(modifier = Modifier.padding(top = 8.dp, start = 8.dp, end = 8.dp), text = property.description)
        property.address.lineOne.let {
            Text(
                modifier = Modifier.padding(top = 2.dp, start = 8.dp, end = 8.dp, bottom = 9.dp),
                text = it,
                maxLines = 3
            )
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
    )

    PropInTheme {
        Surface {
            PropertyCard(property = property)
        }
    }
}