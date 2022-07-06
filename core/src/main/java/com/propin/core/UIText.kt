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

package com.propin.core

import android.content.Context
import androidx.annotation.StringRes

/**
 * UiText extracts string resources, and allows non-UI classes
 * to handle UI text without needing context
 */
sealed class UIText {
    data class DynamicString(val value: String) : UIText()
    class ResourceString(@StringRes val resId: Int, vararg val args: Any) : UIText()

    fun asString(context: Context): String {
        return when (this) {
            is DynamicString -> value
            is ResourceString -> context.getString(resId, args)
        }
    }
}
