package br.com.nathan.agualimpa.activity;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

import br.com.nathan.agualimpa.R;
import br.com.nathan.agualimpa.Util.ConfiguraBd;
import br.com.nathan.agualimpa.model.Usuario;

public class LoginActivity extends AppCompatActivity {

    private  Button Acessar;
    private Button Cadastrar;

    private FirebaseAuth auth;
    private EditText email,senha;

    private FirebaseAuth autentificacao;

    private Usuario usuario;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();

        Cadastrar = findViewById(R.id.buttonCadastre_se);
        Acessar = findViewById(R.id.buttonAcessar);
        email = findViewById(R.id.editTextEmailLogin);
        senha = findViewById(R.id.editTextSenhaLogin);


        Acessar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String emailText = email.getText().toString();
                String senhaText = senha.getText().toString();

                if (TextUtils.isEmpty(emailText) || TextUtils.isEmpty(senhaText)) {
                    Toast.makeText(LoginActivity.this, "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show();
                } else {
                    autentificacao = ConfiguraBd.Firebaseautentificacao();

                    autentificacao.signInWithEmailAndPassword(
                            emailText, senhaText


                    ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                startActivity(new Intent(LoginActivity.this, NoticiasActivity.class));
                            } else {
                                // Se o login falhar, exiba uma mensagem de erro.
                                Toast.makeText(LoginActivity.this, "Erro ao fazer login. Verifique suas credenciais.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
                Cadastrar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Quando o botão de cadastro for clicado, inicie a atividade de cadastro.
                        startActivity(new Intent(LoginActivity.this, CadastroActivity.class));
                    }
                });
            }
        });
    }



    }