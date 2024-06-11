package com.example.recyclerviewb;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

public class MahasiswaViewHolder extends RecyclerView.ViewHolder {
    public ImageView _jkImageView;
    public TextView _jkTextView, _jpTextView, _namaTextView, _nimTextView, _noTextView;


    public MahasiswaViewHolder(View itemView) {
        super(itemView);

        _jkImageView = itemView.findViewById(R.id.jkImageView);
        _jkTextView = itemView.findViewById(R.id.jkTextView);
        _jpTextView = itemView.findViewById(R.id.jpTextView);
        _namaTextView = itemView.findViewById(R.id.namaTextView);
        _nimTextView = itemView.findViewById(R.id.nimTextView);
        _noTextView = itemView.findViewById(R.id.noTextView);
    }
}
