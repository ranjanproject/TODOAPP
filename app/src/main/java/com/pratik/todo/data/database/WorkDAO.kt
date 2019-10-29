package com.pratik.todo.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.Flowable

@Dao
interface WorkDAO {

    @Query("SELECT * FROM works")
    fun getAll(): Flowable<List<WorkData>>

    @Insert
    fun insertWork(workData: WorkData)
}