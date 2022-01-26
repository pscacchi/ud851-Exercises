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

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.android.todolist.data.TaskContract.TaskEntry

class TaskDbHelper(context: Context?) : SQLiteOpenHelper(context, DATABASE_NAME, null, VERSION) {
    companion object {
        private const val DATABASE_NAME = "tasksDb.db"
        private const val VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase) {

        val CREATE_TABLE = "CREATE TABLE " + TaskEntry.TABLE_NAME + " (" +
                TaskEntry._ID + " INTEGER PRIMARY KEY, " +
                TaskEntry.COLUMN_DESCRIPTION + " TEXT NOT NULL, " +
                TaskEntry.COLUMN_PRIORITY + " INTEGER NOT NULL);"
        db.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + TaskEntry.TABLE_NAME)
        onCreate(db)
    }

}