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

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.database.SQLException
import android.net.Uri
import com.example.android.todolist.data.TaskContract.TaskEntry

class TaskContentProvider : ContentProvider() {
    private var mTaskDbHelper: TaskDbHelper? = null

    companion object {
        const val TASKS = 100
        const val TASK_WITH_ID = 101

        private val sUriMatcher = buildUriMatcher()

        @JvmStatic
        fun buildUriMatcher(): UriMatcher {
            val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)
            uriMatcher.addURI(TaskContract.AUTHORITY, TaskContract.PATH_TASKS, TASKS)
            uriMatcher.addURI(TaskContract.AUTHORITY, TaskContract.PATH_TASKS + "/#", TASK_WITH_ID)
            return uriMatcher
        }
    }

    override fun onCreate(): Boolean {
        mTaskDbHelper = TaskDbHelper(context)
        return true
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri {
        val db = mTaskDbHelper!!.writableDatabase

        val returnUri: Uri = when (sUriMatcher.match(uri)) {
            TASKS -> {
                val id = db.insert(TaskEntry.TABLE_NAME, null, values)
                if (id > 0) {
                    ContentUris.withAppendedId(TaskEntry.CONTENT_URI, id)
                } else {
                    throw SQLException("Failed to insert row into $uri")
                }
            }
            else -> throw UnsupportedOperationException("Unknown uri: $uri")
        }
        context.contentResolver.notifyChange(uri, null)

        return returnUri
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor {

        val db = mTaskDbHelper!!.readableDatabase

        val retCursor: Cursor = when (sUriMatcher.match(uri)) {
            TASKS -> db.query(TaskEntry.TABLE_NAME, projection, selection, selectionArgs,
                null, null, sortOrder)
            else -> throw UnsupportedOperationException("Unknown uri: $uri")
        }

        retCursor.setNotificationUri(context.contentResolver, uri)

        return retCursor
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {

        val db = mTaskDbHelper!!.writableDatabase
        val tasksDeleted: Int = when (sUriMatcher.match(uri)) {
            TASK_WITH_ID -> {
                val id = uri.pathSegments[1]
                db.delete(TaskEntry.TABLE_NAME, "_id=?", arrayOf(id))
            }
            else -> throw UnsupportedOperationException("Unknown uri: $uri")
        }
        if (tasksDeleted != 0) {
            context.contentResolver.notifyChange(uri, null)
        }

        return tasksDeleted
    }

    override fun update(
        uri: Uri, values: ContentValues, selection: String,
        selectionArgs: Array<String>
    ): Int {
        throw UnsupportedOperationException("Not yet implemented")
    }

    override fun getType(uri: Uri): String {
        throw UnsupportedOperationException("Not yet implemented")
    }

}