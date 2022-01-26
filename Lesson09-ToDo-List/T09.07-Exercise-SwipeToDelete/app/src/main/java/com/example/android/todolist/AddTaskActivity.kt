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
package com.example.android.todolist

import android.content.ContentValues
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Toast
import com.example.android.todolist.data.TaskContract

class AddTaskActivity : AppCompatActivity() {

    private var mPriority = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)

        (findViewById<View>(R.id.radButton1) as RadioButton).isChecked = true
        mPriority = 1
    }


    fun onClickAddTask(view: View?) {

        val input = (findViewById<View>(R.id.editTextTaskDescription) as EditText).text.toString()
        if (input.isEmpty()) {
            return
        }

        val contentValues = ContentValues()

        contentValues.put(TaskContract.TaskEntry.COLUMN_DESCRIPTION, input)
        contentValues.put(TaskContract.TaskEntry.COLUMN_PRIORITY, mPriority)

        val uri = contentResolver.insert(TaskContract.TaskEntry.CONTENT_URI, contentValues)


        if (uri != null) {
            Toast.makeText(baseContext, uri.toString(), Toast.LENGTH_LONG).show()
        }


        finish()
    }

    /**
     * onPrioritySelected is called whenever a priority button is clicked.
     * It changes the value of mPriority based on the selected button.
     */
    fun onPrioritySelected(view: View?) {
        when {
            (findViewById<View>(R.id.radButton1) as RadioButton).isChecked -> mPriority = 1
            (findViewById<View>(R.id.radButton2) as RadioButton).isChecked -> mPriority = 2
            (findViewById<View>(R.id.radButton3) as RadioButton).isChecked -> mPriority = 3
        }
    }
}