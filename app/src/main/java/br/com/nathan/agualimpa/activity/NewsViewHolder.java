package br.com.nathan.agualimpa.activity;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import br.com.nathan.agualimpa.R;
import br.com.nathan.agualimpa.model.News;

public  class NewsViewHolder extends RecyclerView.ViewHolder {

    private TextView textViewTitle;
    private TextView textViewDescription;
    private TextView textViewCategory;

    public NewsViewHolder(@NonNull View itemView) {
        super(itemView);
        textViewTitle = itemView.findViewById(R.id.textViewTitle);
        textViewDescription = itemView.findViewById(R.id.textViewDescription);
        textViewCategory = itemView.findViewById(R.id.textViewCategory);
    }

    public void bind(News news) {
        textViewTitle.setText(news.getTitle());
        textViewDescription.setText(news.getDescription());
        textViewCategory.setText(news.getCategory());
    }
}

