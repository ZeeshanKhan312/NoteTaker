package com.project.noteapp;

import  static androidx.room.OnConflictStrategy.REPLACE;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@SuppressWarnings("ALL")
@Dao
public interface NotesDAO {

    @Insert(onConflict = REPLACE)
    void insert(Notes notes);
    @Query("SELECT * FROM notes ORDER BY id DESC")
    List<Notes> getAll();
    @Query("UPDATE notes SET title =:title, text =:text WHERE id =:id")
    void update(int id, String title, String text);
    @Delete
    void delete(Notes note);

    @Query("UPDATE notes SET pinned =:pin WHERE id =:id")
    void pin(int id, boolean pin);

}
