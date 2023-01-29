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

import java.io.IOException

sealed class PropError(val uiText: UIText? = null, val exception: Throwable? = null) {
    class IOError(exception: Throwable? = null, uiText: UIText? = null) : PropError(uiText, exception)
    class NotFoundError(uiText: UIText? = null) : PropError(uiText)
    class UnexpectedError(exception: Throwable? = null, uiText: UIText? = null) : PropError(uiText, exception)
}

fun Throwable.toPropError(): PropError {
    return when (this) {
        is IOException -> PropError.IOError(exception = this)
        else -> PropError.UnexpectedError(exception = this)
    }
}
