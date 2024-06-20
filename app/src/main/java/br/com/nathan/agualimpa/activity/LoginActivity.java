package br.com.nathan.agualimpa.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import br.com.nathan.agualimpa.R;
import br.com.nathan.agualimpa.Util.ConfiguraBd;

public class LoginActivity extends AppCompatActivity {

    private Button Acessar;
    private Button Cadastrar;
    private Spinner spinner;
    private EditText email, senha;
    private FirebaseAuth autentificacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        autentificacao = ConfiguraBd.Firebaseautentificacao();

        Cadastrar = findViewById(R.id.buttonCadastre_se);
        Acessar = findViewById(R.id.buttonAcessar);
        email = findViewById(R.id.editTextEmailLogin);
        senha = findViewById(R.id.editTextSenhaLogin);
        spinner = findViewById(R.id.spinnerTipo);

        // Configurar o Spinner com opções
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.tipos_usuarios, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        Acessar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String emailText = email.getText().toString();
                    String senhaText = senha.getText().toString();
                    String selectedTipo = spinner.getSelectedItem().toString();

                    if (TextUtils.isEmpty(emailText) || TextUtils.isEmpty(senhaText)) {
                        Toast.makeText(LoginActivity.this, "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show();
                    } else if ("Selecione um tipo".equals(selectedTipo)) {
                        Toast.makeText(LoginActivity.this, "Por favor, selecione um tipo.", Toast.LENGTH_SHORT).show();
                    } else {
                        autentificacao.signInWithEmailAndPassword(emailText, senhaText)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            Intent intent = new Intent(LoginActivity.this, NoticiasActivity.class);
                                            startActivity(intent);
                                            finish(); // Fechar a atividade de login
                                        } else {
                                            Toast.makeText(LoginActivity.this, "Erro ao fazer login. Verifique suas credenciais.", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }
                } catch (Exception e) {
                    Toast.makeText(LoginActivity.this, "Ocorreu um erro: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        Cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, CadastroActivity.class));
            }
        });
    }
}
