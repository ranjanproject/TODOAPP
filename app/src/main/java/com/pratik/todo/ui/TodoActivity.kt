package com.pratik.todo.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.pratik.todo.R

class TodoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        addFragment(R.id.frameLayout, TodoFragment())
    }
}
