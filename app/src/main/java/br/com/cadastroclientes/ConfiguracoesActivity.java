package br.com.cadastroclientes;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import br.com.cadastroclientes.config.RetrofitConfig;
import br.com.cadastroclientes.dao.ClienteDAO;
import br.com.cadastroclientes.helper.ConfiguracoesHelper;
import br.com.cadastroclientes.model.CEP;
import br.com.cadastroclientes.model.Cliente;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Samantha on 24/12/2018.
 */

public class ConfiguracoesActivity extends AppCompatActivity {

    private EditText nome, sobrenome, cpf, cep;
    private ConfiguracoesHelper helper;
    private Button btnEditar, btnMaps;
    private TextView dataNascimentoEdicao;
    private DatePickerDialog.OnDateSetListener listenerNascimentoEdicao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracoes);

        String extra = getIntent().getExtras().get("clienteId").toString();
        final Long clienteId = Long.parseLong(extra);

        ClienteDAO clienteDAO = new ClienteDAO(ConfiguracoesActivity.this);
        final Cliente cliente = clienteDAO.buscaPorId(clienteId);

        helper = new ConfiguracoesHelper(this);

        nome = helper.getNomeEdicao();
        sobrenome = helper.getSobrenomeEdicao();
        cpf = helper.getCpfEdicao();
        cep = helper.getCepEdicao();
        btnEditar = helper.getBtnEditar();
        btnMaps = helper.getBtnMaps();

        dataNascimentoEdicao = findViewById(R.id.tvNascimentoEdicao);

        //preenchendo campos com os dados antigos
        nome.setText(cliente.getNome().toString());
        sobrenome.setText(cliente.getSobrenome().toString());
        cpf.setText(cliente.getCpf().toString());
        cep.setText(cliente.getCep().toString());
        dataNascimentoEdicao.setText(cliente.getDataNascimento().toString());

        dataNascimentoEdicao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                //definindo como será exposto o DatePicker ao usuário
                DatePickerDialog dialog = new DatePickerDialog(
                        ConfiguracoesActivity.this,
                        android.R.style.Theme_Holo_Dialog_MinWidth,
                        listenerNascimentoEdicao,
                        year, month, day
                );

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        listenerNascimentoEdicao = new DatePickerDialog.OnDateSetListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                month = month +1;

                //formato da data a qual foi escolhida
                String data = dayOfMonth + "/" +  month + "/" + year;

                dataNascimentoEdicao.setText(data);

            }
        };

        btnMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cliente clienteEditado = helper.pegaClienteEdicao();

                final Intent intent = new Intent(ConfiguracoesActivity.this, MapsActivity.class);
                intent.putExtra("cepMaps", clienteEditado.getCep());
                startActivity(intent);
            }
        });


        //TODO Alterar dados
        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cliente clienteEditado = helper.pegaClienteEdicao();

                clienteEditado.setDataNascimento(dataNascimentoEdicao.getText().toString());

                if(clienteEditado.getNome().equals("") || clienteEditado.getSobrenome().equals("")
                        || clienteEditado.getCpf().equals("") || clienteEditado.getCep().equals("")){
                    Toast.makeText(getApplicationContext(),"Preencha todos os campos!", Toast.LENGTH_LONG).show();
                } else if (!clienteEditado.getNome().matches("(?=^.{2,60}$)^[A-ZÀÁÂĖÈÉÊÌÍÒÓÔÕÙÚÛÇ][a-zàáâãèéêìíóôõùúç]+(?:[ ](?:das?|dos?|de|e|[A-Z][a-z]+))*$")){
                    Toast.makeText(getApplicationContext(),"Insira um nome válido!", Toast.LENGTH_LONG).show();
                } else if (!clienteEditado.getSobrenome().matches("(?=^.{2,60}$)^[A-ZÀÁÂĖÈÉÊÌÍÒÓÔÕÙÚÛÇ][a-zàáâãèéêìíóôõùúç]+(?:[ ](?:das?|dos?|de|e|[A-Z][a-z]+))*$")){
                    Toast.makeText(getApplicationContext(),"Insira um sobrenome válido!", Toast.LENGTH_LONG).show();
                } else if (!clienteEditado.getCpf().matches("[0-9]{11}")){
                    Toast.makeText(getApplicationContext(),"Insira um CPF válido, utilize apenas números!", Toast.LENGTH_LONG).show();
                } else if (!clienteEditado.getCep().matches("\\d\\d\\d\\d\\d\\d\\d\\d")){
                    Toast.makeText(getApplicationContext(),"Insira um CEP válido, utilize apenas números!", Toast.LENGTH_LONG).show();
                } else if(dataNascimentoEdicao.getText().equals(cliente.getDataNascimento())
                        && clienteEditado.getNome().equals(cliente.getNome())
                        && clienteEditado.getSobrenome().equals(cliente.getSobrenome())
                        && clienteEditado.getCpf().equals(cliente.getCpf())
                        && clienteEditado.getCep().equals(cliente.getCep())){
                    Toast.makeText(getApplicationContext(),"Não há alterações a serem feitas.", Toast.LENGTH_LONG).show();
                } else {
                    ClienteDAO dao = new ClienteDAO(ConfiguracoesActivity.this);
                    dao.atualiza(clienteEditado, clienteId);
                    Toast.makeText(getApplicationContext(), "Cliente atualizado com sucesso!", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });


    }
}
