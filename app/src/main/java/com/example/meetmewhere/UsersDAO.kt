package com.example.meetmewhere

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UsersDAO {
    @Insert
    fun insertUser(users: Users)

    @Query("SELECT * FROM users")
    fun getAllUsers(): LiveData<List<Users>>

    @Query("SELECT * FROM users WHERE name = :name LIMIT 1")
    fun getUserByUsername(name: String): Users?
//    @Query("SELECT * FROM users WHERE ")
}