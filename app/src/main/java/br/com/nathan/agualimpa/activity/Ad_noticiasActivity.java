package br.com.nathan.agualimpa.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import br.com.nathan.agualimpa.R;

public class Ad_noticiasActivity extends AppCompatActivity {

    private EditText editTextTitulo, editTextConteudo;
    private Button buttonPublicar;
    private DatabaseReference referenciaDatabase;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adicionar_noticias);

        editTextTitulo = findViewById(R.id.editTextTitulo);
        editTextConteudo = findViewById(R.id.editTextConteudo);
        buttonPublicar = findViewById(R.id.buttonPublicar);
        mAuth = FirebaseAuth.getInstance();
        referenciaDatabase = FirebaseDatabase.getInstance().getReference().child("noticias");

        buttonPublicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                publicarNoticia();
            }
        });
    }

    private void publicarNoticia() {
        String titulo = editTextTitulo.getText().toString();
        String conteudo = editTextConteudo.getText().toString();

        if (titulo.isEmpty() || conteudo.isEmpty()) {
            Toast.makeText(Ad_noticiasActivity.this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            return;
        }

        String userId = mAuth.getCurrentUser().getUid();
        String noticiaId = referenciaDatabase.push().getKey();

        Map<String, Object> noticiaMap = new HashMap<>();
        noticiaMap.put("titulo", titulo);
        noticiaMap.put("conteudo", conteudo);
        noticiaMap.put("userId", userId);

        referenciaDatabase.child(noticiaId).setValue(noticiaMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Ad_noticiasActivity.this, "Notícia publicada com sucesso", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(Ad_noticiasActivity.this, "Erro ao publicar notícia", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
