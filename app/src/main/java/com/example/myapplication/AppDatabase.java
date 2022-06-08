package com.example.myapplication;


import android.content.Context;
//
//import androidx.room.Database;
//import androidx.room.Room;
//import androidx.room.RoomDatabase;

//@Database(entities = {Task.class}, version = 1)
public abstract class AppDatabase /*extends RoomDatabase */{
//    public abstract UserDao userDao();

    private static AppDatabase appDatabase;


    public AppDatabase (){}

    public static AppDatabase getInstance(Context context){

     if (appDatabase == null ) {
//         appDatabase = Room.databaseBuilder(context,
//                 AppDatabase.class,
//                 "AppDatabase").allowMainThreadQueries().build();
     }
        return appDatabase;
    }
}
