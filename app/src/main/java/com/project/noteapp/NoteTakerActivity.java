package com.project.noteapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NoteTakerActivity extends AppCompatActivity {

    EditText title, content;
    LinearLayout saveBtn;
    Notes note;
    boolean isUpdating=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_taker);
        title= findViewById(R.id.title);
        content=findViewById(R.id.content);
        saveBtn=findViewById(R.id.saveBtn);
        note =new Notes();

        try{
            note=(Notes)getIntent().getSerializableExtra("old_note");
            title.setText(note.getTitle());
            content.setText(note.getText());
            isUpdating=true;
        }catch (Exception e){
            e.printStackTrace();
        }

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isUpdating){
                    note=new Notes();
                }
                if(!title.getText().toString().isEmpty() && !content.getText().toString().isEmpty()) {
                    note.setTitle(title.getText().toString());
                    note.setText(content.getText().toString());
                    Date date = new Date();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm a");
                    note.setDate(dateFormat.format(date));

                    Intent intent = new Intent();
                    intent.putExtra("note", note);
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }
                else{
                    Toast.makeText(NoteTakerActivity.this, "Enter the title and contents of the note!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}