/*
* Copyright (C) 2016 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package com.example.android.todolist.data

import android.net.Uri
import android.provider.BaseColumns

object TaskContract {

    const val AUTHORITY = "com.example.android.todolist"
    val BASE_CONTENT_URI: Uri = Uri.parse("content://$AUTHORITY")
    const val PATH_TASKS = "tasks"

    object TaskEntry : BaseColumns {
        @JvmField
        val CONTENT_URI: Uri = BASE_CONTENT_URI.buildUpon().appendPath(PATH_TASKS).build()
        const val TABLE_NAME = "tasks"

        // Since TaskEntry implements the interface "BaseColumns", it has an automatically produced
        // "_ID" column in addition to the two below

        const val _ID = "_id"
        const val COLUMN_DESCRIPTION = "description"
        const val COLUMN_PRIORITY = "priority"
        /*
        The above table structure looks something like the sample table below.
        With the name of the table and columns on top, and potential contents in rows

        Note: Because this implements BaseColumns, the _id column is generated automatically

        tasks
         - - - - - - - - - - - - - - - - - - - - - -
        | _id  |    description     |    priority   |
         - - - - - - - - - - - - - - - - - - - - - -
        |  1   |  Complete lesson   |       1       |
         - - - - - - - - - - - - - - - - - - - - - -
        |  2   |    Go shopping     |       3       |
         - - - - - - - - - - - - - - - - - - - - - -
        .
        .
        .
         - - - - - - - - - - - - - - - - - - - - - -
        | 43   |   Learn guitar     |       2       |
         - - - - - - - - - - - - - - - - - - - - - -

         */
    }
}