package br.com.nathan.agualimpa.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.activity.EdgeToEdge;

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

public class CadastroActivity extends Activity {


    Usuario usuario;
    FirebaseAuth autentificacao;
    EditText campoNome,campoEmail,campoCPF,campoSenha;
    Button botaoCadastrar;

    Spinner spinnerTipo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
    inicializar();
    //validarCampos


        botaoCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validarCampos(v); // Chama a função para validar os campos e cadastrar o usuário
            }
        });
    }



    private void inicializar(){

        campoNome = findViewById(R.id.editTextNome);
        campoEmail = findViewById(R.id.editTextEmail);
        campoCPF = findViewById(R.id.editTextCPF);
        campoSenha = findViewById(R.id.editTextSenha);
        spinnerTipo = findViewById(R.id.spinnerTipo); // Inicialização do Spinner
        botaoCadastrar = findViewById(R.id.buttonCadastrar);

    }
    private void validarCampos(View v){
        String nome = campoNome.getText().toString();
        String email = campoEmail.getText().toString();
        String cpf = campoCPF.getText().toString();
        String senha = campoSenha.getText().toString();

        if (!nome.isEmpty()){
            if (!email.isEmpty()){
                if (!cpf.isEmpty()){
                    if (!senha.isEmpty()){

                        usuario = new Usuario();
                        usuario.setNome(nome);
                        usuario.setEmail(email);
                        usuario.setCPF(cpf);
                        usuario.setSenha(senha);

                        //cadastro
                        cadastrarUsuario();

                    }else {
                        Toast.makeText(this,"Preencha a senha", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(this,"Preencha o CPF", Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(this,"Preencha o email", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(this,"Preencha o nome", Toast.LENGTH_SHORT).show();

        }


    }

    private void cadastrarUsuario(){

        autentificacao = ConfiguraBd.Firebaseautentificacao();

        autentificacao.createUserWithEmailAndPassword(
                usuario.getEmail(),usuario.getSenha()
        ).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(CadastroActivity.this,"Sucesso ao cadastrar um Usuario", Toast.LENGTH_SHORT).show();
                }else {//mudei aqui
                    String exececao = "";

                    try {
                        throw task.getException();
                    }catch (FirebaseAuthWeakPasswordException e){
                        exececao ="digite uma senha mais forte";
                    }catch (FirebaseAuthInvalidCredentialsException e){
                        exececao ="Digite um email válido";
                    }catch (FirebaseAuthUserCollisionException e){
                        exececao ="Esta conta já existe";
                    }catch (Exception e){
                        exececao ="Erro ao cadastrar usuario"+ e.getMessage();
                        e.printStackTrace();
                    }
                    Toast.makeText(CadastroActivity.this,exececao, Toast.LENGTH_SHORT).show();
                }
            }

        });

    }
}