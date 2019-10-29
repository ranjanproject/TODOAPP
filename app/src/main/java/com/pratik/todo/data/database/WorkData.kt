package com.pratik.todo.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "works")
data class WorkData(
    @PrimaryKey(autoGenerate = true) val id: Int ,
    @ColumnInfo(name = "city") var cityName: String? ,
    @ColumnInfo(name = "temp") var temp: String?
)