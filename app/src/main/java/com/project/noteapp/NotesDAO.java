package com.project.noteapp;

import  static androidx.room.OnConflictStrategy.REPLACE;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface NotesDAO {

    @Insert(onConflict = REPLACE)
    public void insert(Notes note);
    @Query("SELECT * FROM notes ORDER BY id DESC")
    public List<Notes> getAll();
    @Query("UPDATE notes SET title =:title, text =:text WHERE id =:id")
    public void update(int id, String title, String text);
    @Delete
    void delete(Notes note);
}
