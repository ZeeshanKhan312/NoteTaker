package com.project.noteapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    FloatingActionButton addButton;
    NotesAdapter adapter;
    List<Notes> notes = new ArrayList<>();
    RoomDB database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=findViewById(R.id.recyclerView);
        addButton=findViewById(R.id.addBtn);

        database=RoomDB.getInstance(this);
        notes=database.NotesDao().getAll();
        adapter= new NotesAdapter(MainActivity.this, notes, clickListener);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);

        Notes notes1= new Notes();
        notes1.setTitle("First Note");
        notes1.setText("Testing of Notes App.\nVersion 1.0\n.....");
        notes1.setDate("06th May two thousand twenty three");
        notes.add(notes1);
        adapter.notifyDataSetChanged();
    }

    private  final NotesClickListener clickListener = new NotesClickListener() {
        @Override
        public void onClick(Notes notes) {

        }

        @Override
        public void onLongClick(Notes notes, CardView notesView) {

        }
    };
}