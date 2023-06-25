package com.project.noteapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {
    RecyclerView recyclerView;
    FloatingActionButton addButton;
    SearchView searchView;
    NotesAdapter adapter;
    List<Notes> notes = new ArrayList<>();
    Notes selectedNote= new Notes();
    RoomDB database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=findViewById(R.id.recyclerView);
        addButton=findViewById(R.id.addBtn);
        searchView=findViewById(R.id.search_bar);
        database=RoomDB.getInstance(this);
        notes=database.notesDao().getAll();

        adapter= new NotesAdapter(MainActivity.this, notes, clickListener);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MainActivity.this, NoteTakerActivity.class);
//                startActivity(intent);
                startActivityForResult(intent,101);
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query){
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                List<Notes> searchList=new ArrayList<>();
                for(Notes note: notes){
                    if(note.getTitle().toLowerCase().contains(newText.toLowerCase()) || note.getText().toLowerCase().contains(newText.toLowerCase()))
                        searchList.add(note);
                }
                adapter.search(searchList);

                return true;
            }
        });

    }

    private  final NotesClickListener clickListener = new NotesClickListener() {
        @Override
        public void onClick(Notes notes) {
            Intent intent= new Intent(MainActivity.this, NoteTakerActivity.class);
            intent.putExtra("old_note", notes);
            startActivityForResult(intent,102);
        }

        @Override
        public void onLongClick(Notes notes, CardView notesView) {
            selectedNote=notes;
            PopupMenu popupMenu= new PopupMenu(MainActivity.this, notesView);
            popupMenu.setOnMenuItemClickListener(MainActivity.this);
            popupMenu.inflate(R.menu.popup_menu);
            popupMenu.show();
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101) {
            if (resultCode == Activity.RESULT_OK) {
                Notes newNotes = (Notes) data.getSerializableExtra("note"); //use to fetch an object from intent not String
                database.notesDao().insert(newNotes);
                notes.clear();
                notes.addAll(database.notesDao().getAll());
                adapter.notifyDataSetChanged();
            }
        } else if (requestCode==102) {
            if (resultCode == Activity.RESULT_OK) {
                Notes newNotes = (Notes) data.getSerializableExtra("note"); //use to fetch an object from intent not String
                database.notesDao().update(newNotes.getId(),newNotes.getTitle(),newNotes.getText());
                notes.clear();
                notes.addAll(database.notesDao().getAll());
                adapter.notifyDataSetChanged();
            }

        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.pinned:
                if(selectedNote.isPinned()){
                    database.notesDao().pin(selectedNote.getId(), false);
                    Toast.makeText(this, "Unpinned!!", Toast.LENGTH_SHORT).show();
                }
                else{
                    database.notesDao().pin(selectedNote.getId(),true);
                    Toast.makeText(this, "Pinned!!", Toast.LENGTH_SHORT).show();
                }
                notes.clear();
                notes.addAll(database.notesDao().getAll());
                adapter.notifyDataSetChanged();
                return true;
            case R.id.delete:
                database.notesDao().delete(selectedNote);
                notes.remove(selectedNote);
                adapter.notifyDataSetChanged();
                Toast.makeText(this, "Note Deleted!", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return false;
        }
    }
}