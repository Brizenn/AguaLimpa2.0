package br.com.nathan.agualimpa.activity;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import br.com.nathan.agualimpa.R;
import br.com.nathan.agualimpa.model.News;

public class NoticiasActivity extends AppCompatActivity {

    private RecyclerView recyclerViewNews;
    private NewsAdapter newsAdapter;
    public static List<News> newsList = new ArrayList<>();

    Button lancar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noticias);

        // Inicialize o RecyclerView
        recyclerViewNews = findViewById(R.id.recyclerViewNews);
        recyclerViewNews.setLayoutManager(new LinearLayoutManager(this));


        newsAdapter = new NewsAdapter(newsList);


        recyclerViewNews.setAdapter(newsAdapter);

        // Adicione notícias de exemplo
        newsList.add(new News("ÁGUA POTAVEL EM SANTA CATARINA", "Água potavel em Santa Cataria está disponivel para o público", "Geral", R.drawable.urgente));
        newsList.add(new News("galão D'água", "deposito oferece água para desabrigados", "Região de Porto Alegre", R.drawable.aguaa));
        newsList.add(new News("ÁGUA testada e aprovada", "PESQUISADOR testou as águas e constou que contem virus de leptospirose", "Novidades", R.drawable.aguaaa));


        newsAdapter.notifyDataSetChanged();





    }
}
