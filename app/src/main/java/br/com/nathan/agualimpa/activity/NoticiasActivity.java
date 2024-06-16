package br.com.nathan.agualimpa.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import br.com.nathan.agualimpa.R;
import br.com.nathan.agualimpa.activity.NewsAdapter;
import br.com.nathan.agualimpa.model.News;

public class NoticiasActivity extends AppCompatActivity {

    private RecyclerView recyclerViewNews;
    private NewsAdapter newsAdapter;
    public static List<News> newsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noticias);

        // Inicialize o RecyclerView
        recyclerViewNews = findViewById(R.id.recyclerViewNews);
        recyclerViewNews.setLayoutManager(new LinearLayoutManager(this));

        // Inicialize o NewsAdapter com a lista de notícias
        newsAdapter = new NewsAdapter(newsList);

        // Defina o adapter para o RecyclerView
        recyclerViewNews.setAdapter(newsAdapter);

        // Adicione notícias de exemplo (opcional, dependendo da sua lógica de aplicativo)
        newsList.add(new News("Título da Notícia 1", "Descrição da Notícia 1", "Geral"));
        newsList.add(new News("Título da Notícia 2", "Descrição da Notícia 2", "Região"));
        newsList.add(new News("Título da Notícia 3", "Descrição da Notícia 3", "Novidades"));

        // Notifique o adapter que os dados foram alterados
        newsAdapter.notifyDataSetChanged();
    }
}
