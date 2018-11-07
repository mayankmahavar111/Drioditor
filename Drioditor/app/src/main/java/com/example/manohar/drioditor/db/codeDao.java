package com.example.manohar.drioditor.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.arch.persistence.room.OnConflictStrategy;
import android.provider.ContactsContract;

import com.example.manohar.drioditor.model.codes;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface codeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCode(codes code);

    @Delete
    void deleteCode(codes code);

    @Update
    void updateCode(codes code);

    @Query("SELECT * FROM codes")
    List<codes> getCodes();

    @Query("SELECT * FROM codes WHERE id = :codeId")
    codes getCodeById(int codeId);

    @Query("DELETE FROM codes WHERE id = :codeId")
    void deleteCodeById(int codeId);
}
