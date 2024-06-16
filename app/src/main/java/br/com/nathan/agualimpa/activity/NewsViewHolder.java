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
        textViewTitle = itemView.findViewById(R.id.newsTitle);
        textViewDescription = itemView.findViewById(R.id.newsDescription);
        textViewCategory = itemView.findViewById(R.id.newsCategory);
    }

    public void bind(News news) {
        textViewTitle.setText(news.getTitle());
        textViewDescription.setText(news.getDescription());
        textViewCategory.setText(news.getCategory());
    }
}

