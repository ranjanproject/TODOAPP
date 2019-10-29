package com.pratik.todo.data.database


import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.Room



@Database(entities = [WorkData::class], version = 1)
abstract class WorkDatabase: RoomDatabase() {
    companion object {
        private var instance: WorkDatabase? = null
        fun getDatabase(context: Context): WorkDatabase? {
            if (instance == null) {
                instance =
                    Room.databaseBuilder(context , WorkDatabase::class.java , "Work.db").build()
            }
            return instance
        }
    }
    abstract fun workDAO() : WorkDAO
}