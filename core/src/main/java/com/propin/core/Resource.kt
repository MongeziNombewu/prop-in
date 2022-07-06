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

sealed class Resource<T>(val data: T? = null, val error: Exception? = null, val uiText: UIText? = null) {
    class Success<T>(data: T?) : Resource<T>(data = data)
    class Error<T>(exception: Exception? = null, uiText: UIText? = null) : Resource<T>(uiText = uiText, error = exception)
    class Loading<T>(data: T?) : Resource<T>(data)
}
