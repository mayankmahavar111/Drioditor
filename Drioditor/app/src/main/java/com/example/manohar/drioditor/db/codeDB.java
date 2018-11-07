package com.example.manohar.drioditor.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.manohar.drioditor.model.codes;

@Database(entities = codes.class, version = 1)
public abstract class codeDB extends RoomDatabase {
    public abstract codeDao code_dao();
    private static codeDB instance;
    public static final String DATABASE_NAME="codesDB";

    public static codeDB getInstance(Context context) {
        if(instance==null)
            instance=Room.databaseBuilder(context,codeDB.class, DATABASE_NAME).allowMainThreadQueries().build();
        return instance;
    }
}
