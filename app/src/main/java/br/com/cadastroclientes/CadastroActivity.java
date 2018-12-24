package br.com.cadastroclientes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import br.com.cadastroclientes.dao.ClienteDAO;
import br.com.cadastroclientes.helper.CadastroHelper;
import br.com.cadastroclientes.model.Cliente;

/**
 * Created by Samantha on 24/12/2018.
 */

public class CadastroActivity extends AppCompatActivity {

    private Button btnCadastrar;
    private CadastroHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        helper = new CadastroHelper(this);
        btnCadastrar = helper.getBtnCadastrar();

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cliente cliente = helper.pegaCliente();

                if(cliente.getNome().equals("") || cliente.getSobrenome().equals("")
                        || cliente.getCpf().equals("") || cliente.getCep().equals("")){
                    Toast.makeText(getApplicationContext(),"Preencha todos os campos!", Toast.LENGTH_LONG).show();
                } else if (!cliente.getNome().matches("(?=^.{2,60}$)^[A-ZÀÁÂĖÈÉÊÌÍÒÓÔÕÙÚÛÇ][a-zàáâãèéêìíóôõùúç]+(?:[ ](?:das?|dos?|de|e|[A-Z][a-z]+))*$")){
                    Toast.makeText(getApplicationContext(),"Insira um nome válido!", Toast.LENGTH_LONG).show();
                } else if (!cliente.getSobrenome().matches("(?=^.{2,60}$)^[A-ZÀÁÂĖÈÉÊÌÍÒÓÔÕÙÚÛÇ][a-zàáâãèéêìíóôõùúç]+(?:[ ](?:das?|dos?|de|e|[A-Z][a-z]+))*$")){
                    Toast.makeText(getApplicationContext(),"Insira um sobrenome válido!", Toast.LENGTH_LONG).show();
                } else if (!cliente.getCpf().matches("[0-9]{11}")){
                    Toast.makeText(getApplicationContext(),"Insira um CPF válido, utilize apenas números!", Toast.LENGTH_LONG).show();
                } else if (!cliente.getCep().matches("\\d\\d\\d\\d\\d\\d\\d\\d")){
                    Toast.makeText(getApplicationContext(),"Insira um CEP válido, utilize apenas números!", Toast.LENGTH_LONG).show();
                } else{
                    ClienteDAO dao = new ClienteDAO(CadastroActivity.this);
                    dao.insere(cliente);
                    Toast.makeText(getApplicationContext(), "Cliente cadastrado com sucesso!", Toast.LENGTH_LONG).show();
                     finish();
                }

//                ClienteDAO dao = new ClienteDAO(CadastroActivity.this);
//                dao.insere(cliente);
//                Toast.makeText(getApplicationContext(), "Cliente cadastrado!", Toast.LENGTH_LONG).show();;
//                finish();

            }
        });
    }
}
