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

package com.propin.properties.data.local.model

import android.icu.math.BigDecimal
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.propin.properties.domain.model.PaymentFrequency

@Entity(tableName = "properties")
data class PropertyDto(
    @PrimaryKey(autoGenerate = true) val ID: Int,
    val description: String,
    @Embedded val address: AddressDto,
    val defaultRate: BigDecimal?,
    val defaultPaymentFrequency: PaymentFrequency?
)
