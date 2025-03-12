package com.example.meetmewhere

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Events::class, Users::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase()
{
  abstract fun eventsDao(): EventsDAO
  abstract fun usersDao(): UsersDAO

  companion object{
      @Volatile
      private var INSTANCE: AppDatabase? = null

      fun getDatabase(context: Context): AppDatabase{
          //if the INSTANCE is not null--> return this
          //but if it is then create the database

          return INSTANCE?: synchronized(this){
              val instance= Room.databaseBuilder(
                  context.applicationContext,
                  //Converts class from kotlin to java
                  AppDatabase::class.java,
                  "meetmewhere_database"
              ) .fallbackToDestructiveMigration()
                  .build()
              INSTANCE = instance
              instance
          }

      }
  }
}