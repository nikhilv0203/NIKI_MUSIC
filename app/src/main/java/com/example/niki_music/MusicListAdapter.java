package com.example.niki_music;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;

public class MusicListAdapter extends RecyclerView.Adapter<MusicListAdapter.ViewHolder>{

    ArrayList<AudioModel> songsList;
    Context context;

    public void onItemMove(int fromPosition, int toPosition) {
        Collections.swap(songsList, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
    }

    public MusicListAdapter(ArrayList<AudioModel> songsList, Context context) {
        this.songsList = songsList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_item,parent,false);
        return new MusicListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder( MusicListAdapter.ViewHolder holder, int position) {
        AudioModel songData = songsList.get(holder.getAdapterPosition());
        holder.titleTextView.setText(songData.getTitle());

        if(MyMediaPlayer.currentIndex==holder.getAdapterPosition()){
            holder.titleTextView.setTextColor(Color.parseColor("#FF0000"));
        }else{
            holder.titleTextView.setTextColor(Color.parseColor("#000000"));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //On item click, navigate to another activity
                int clickedPosition = holder.getAdapterPosition();

                MyMediaPlayer.getInstance().reset();
                MyMediaPlayer.currentIndex = clickedPosition;

                Intent intent = new Intent(context,MusicPlayerActivity.class);
                intent.putExtra("LIST",songsList);
                intent.putExtra("POSITION", clickedPosition);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return songsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView titleTextView;
        ImageView iconImageView;
        public ViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.music_title_text);
            iconImageView = itemView.findViewById(R.id.icon_view);
        }
    }
}
