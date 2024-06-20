package br.com.nathan.agualimpa.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import br.com.nathan.agualimpa.R;
import br.com.nathan.agualimpa.Util.ConfiguraBd;
import br.com.nathan.agualimpa.model.Usuario;

public class CadastroActivity extends Activity {

    Usuario usuario;
    FirebaseAuth autentificacao;
    EditText campoNome, campoEmail, campoCPF, campoSenha;
    Button botaoCadastrar;
    Spinner spinnerTipo;

    private DatabaseReference referenciaDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        inicializar();

        referenciaDatabase = FirebaseDatabase.getInstance().getReference(); // Inicializa a referência do Firebase Database
        botaoCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validarCampos(v); // Chama a função para validar os campos e cadastrar o usuário
            }
        });
    }

    private void inicializar() {
        campoNome = findViewById(R.id.editTextNome);
        campoEmail = findViewById(R.id.editTextEmail);
        campoCPF = findViewById(R.id.editTextCPF);
        campoSenha = findViewById(R.id.editTextSenha);
        spinnerTipo = findViewById(R.id.spinnerTipo); // Inicialização do Spinner
        botaoCadastrar = findViewById(R.id.buttonCadastrar);
    }

    private void validarCampos(View v) {
        String nome = campoNome.getText().toString();
        String email = campoEmail.getText().toString();
        String cpf = campoCPF.getText().toString();
        String senha = campoSenha.getText().toString();
        String tipo = spinnerTipo.getSelectedItem().toString();

        if (!nome.isEmpty() && !email.isEmpty() && !cpf.isEmpty() && !senha.isEmpty()) {
            // Todos os campos obrigatórios estão preenchidos, então cria o objeto Usuario
            usuario = new Usuario();
            usuario.setNome(nome);
            usuario.setEmail(email);
            usuario.setCPF(cpf);
            usuario.setSenha(senha);
            usuario.setTipo(tipo); // Definindo o tipo de usuário

            // Cadastro do usuário
            cadastrarUsuario();
        } else {
            Toast.makeText(this, "Preencha todos os campos obrigatórios", Toast.LENGTH_SHORT).show();
        }
    }

    private void cadastrarUsuario() {
        autentificacao = ConfiguraBd.Firebaseautentificacao();

        autentificacao.createUserWithEmailAndPassword(
                usuario.getEmail(), usuario.getSenha()
        ).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sucesso ao cadastrar o usuário
                    FirebaseUser user = autentificacao.getCurrentUser();
                    user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                // Email de verificação enviado com sucesso
                                salvarDadosUsuario(user.getUid()); // Salva os dados do usuário no Realtime Database
                            } else {
                                Toast.makeText(CadastroActivity.this, "Erro ao enviar email de verificação.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    // Falha ao cadastrar o usuário
                    String excecao = "";
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthWeakPasswordException e) {
                        excecao = "Digite uma senha mais forte";
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        excecao = "Digite um email válido";
                    } catch (FirebaseAuthUserCollisionException e) {
                        excecao = "Esta conta já existe";
                    } catch (Exception e) {
                        excecao = "Erro ao cadastrar usuário: " + e.getMessage();
                        e.printStackTrace();
                    }
                    Toast.makeText(CadastroActivity.this, excecao, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void salvarDadosUsuario(String uid) {
        DatabaseReference usuariosRef = referenciaDatabase.child("usuarios").child(uid);
        usuariosRef.setValue(usuario).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    // Sucesso ao salvar os dados do usuário no Realtime Database
                    Toast.makeText(CadastroActivity.this, "Cadastro realizado com sucesso. Verifique seu email para ativar sua conta.", Toast.LENGTH_LONG).show();
                    autentificacao.signOut();
                    finish(); // Fecha a Activity de cadastro e volta para a tela anterior
                } else {
                    // Falha ao salvar os dados do usuário no Realtime Database
                    Toast.makeText(CadastroActivity.this, "Erro ao salvar dados do usuário.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
