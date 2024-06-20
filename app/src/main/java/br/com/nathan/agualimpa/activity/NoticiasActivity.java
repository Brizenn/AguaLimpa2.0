// NoticiasActivity.java
package br.com.nathan.agualimpa.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import br.com.nathan.agualimpa.R;

public class NoticiasActivity extends AppCompatActivity {

    private Button buttonLancarNoticias;
    private DatabaseReference referenciaDatabase;
    private FirebaseAuth mAuth;
    private static final String REQUIRED_USER_TYPE = "Pesquisador"; // Tipo de usuário autorizado

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noticias);

        buttonLancarNoticias = findViewById(R.id.buttonLancarNoticias);
        mAuth = FirebaseAuth.getInstance();
        referenciaDatabase = FirebaseDatabase.getInstance().getReference().child("usuarios");

        buttonLancarNoticias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Implemente aqui a lógica para lançar notícias
                startActivity(new Intent(NoticiasActivity.this, Ad_noticiasActivity.class));
            }
        });

        verificarTipoUsuario();
    }

    private void verificarTipoUsuario() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();
            DatabaseReference usuarioRef = referenciaDatabase.child(userId);
            usuarioRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String tipo = dataSnapshot.child("tipo").getValue(String.class);
                        if (tipo != null && tipo.equals(REQUIRED_USER_TYPE)) {
                            // Usuário é pesquisador, mostrar botão para lançar notícias
                            buttonLancarNoticias.setVisibility(View.VISIBLE);
                        } else {
                            // Usuário não é pesquisador, esconder botão para lançar notícias
                            buttonLancarNoticias.setVisibility(View.GONE);
                        }
                    } else {
                        // Não foi encontrado o tipo de usuário
                        Toast.makeText(NoticiasActivity.this, "Tipo de usuário não encontrado.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(NoticiasActivity.this, "Erro ao verificar o tipo de usuário.", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
