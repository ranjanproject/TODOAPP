package com.pratik.todo.ui

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

fun AppCompatActivity.showFragment(id: Int, fragment: Fragment) {
    supportFragmentManager.transact {
        replace(id, fragment)
    }
}

private inline fun FragmentManager.transact(action: FragmentTransaction.() -> Unit) {
    beginTransaction().apply {
        action()
    }.commit()
}

//    fun AppCompatActivity.setupActionBar(title: String, subtitle: String, action:
//    AppCompatActivity.() -> Unit) {
//        val toolbar = findViewById<View>(R.id.toolbar_actionbar) as Toolbar
//        Common.setToolbarBackAndTitles(toolbar, title, subtitle).setOnClickListener {
//            action()
//        }
//        setSupportActionBar(toolbar)
//    }

fun AppCompatActivity.addFragment(frameId: Int, fragment: Fragment) {
    supportFragmentManager.beginTransaction().replace(frameId, fragment).commitAllowingStateLoss()
}