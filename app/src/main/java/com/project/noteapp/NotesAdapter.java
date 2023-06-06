package com.project.noteapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.viewHolder> {

    public class viewHolder extends RecyclerView.ViewHolder{
    TextView title, text, date;
    ImageView pinned;
    CardView notesContainer;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            notesContainer=itemView.findViewById(R.id.cardView);
            title=itemView.findViewById(R.id.title);
            text=itemView.findViewById(R.id.text);
            date=itemView.findViewById(R.id.date);
            pinned=itemView.findViewById(R.id.pinned);
        }
    }

    Context context;
    List<Notes>  notesList= new ArrayList<>();
    NotesClickListener listener;
    public NotesAdapter(Context context, List<Notes> notesList,NotesClickListener listener){
        this.context=context;
        this.notesList=notesList;
        this.listener=listener;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.notes_list,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        holder.title.setText(notesList.get(position).getTitle());
        holder.text.setText(notesList.get(position).getText());

        holder.date.setText(notesList.get(position).getDate());
        holder.date.setSelected(true);  //for scrolling effect

        if(notesList.get(position).isPinned())
            holder.pinned.setImageResource(R.drawable.push_pin_24);
        else
            holder.pinned.setImageResource(0);

        int colorCode= getRandomColor();
        holder.notesContainer.setCardBackgroundColor(colorCode);  //doubt ?? :: 55

        holder.notesContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        holder.notesContainer.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }

//  to get a random color
    private int getRandomColor(){
        List<Integer> colorCode= new ArrayList<>();
        colorCode.add(R.color.color1);
        colorCode.add(R.color.color2);
        colorCode.add(R.color.color3);
        colorCode.add(R.color.color4);
        colorCode.add(R.color.color5);

        Random random = new Random();
        int randomColor= random.nextInt(colorCode.size());
        return  randomColor;
    }

}
