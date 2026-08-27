package com.example.training4.data.repository

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.training4.data.habit.Habit
import com.example.training4.data.habit.HabitCategory
import kotlinx.coroutines.flow.Flow

@Dao
interface HabitDao {

    @Upsert
    suspend fun upsertHabit(habit: Habit)

    @Delete
    suspend fun deleteHabit(habit: Habit)

    @Query("SELECT * FROM habit ORDER BY category ASC")
    fun getHabitByCategory(): Flow<List<Habit>>

    @Query("SELECT * FROM habit ORDER BY title ASC")
    fun getHabitByTitle(): Flow<List<Habit>>

    @Query("SELECT * FROM habit ORDER BY frequency ASC")
    fun getHabitByFrequency(): Flow<List<Habit>>

    @Query("SELECT * FROM habit WHERE id = :id")
    suspend fun getHabitById(id: Int): Habit?

}