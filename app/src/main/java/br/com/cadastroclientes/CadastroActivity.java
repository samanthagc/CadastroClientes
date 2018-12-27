package br.com.cadastroclientes;

import android.app.DatePickerDialog;
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
import br.com.cadastroclientes.helper.CadastroHelper;
import br.com.cadastroclientes.model.CEP;
import br.com.cadastroclientes.model.Cliente;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Samantha on 24/12/2018.
 */

public class CadastroActivity extends AppCompatActivity {

    private Button btnCadastrar, btnConsultar;
    private CadastroHelper helper;
    private TextView dataNascimento, endereco;
    private EditText cep;
    private DatePickerDialog.OnDateSetListener listenerNascimento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        helper = new CadastroHelper(this);
        btnCadastrar = helper.getBtnCadastrar();
        btnConsultar = helper.getBtnConsultaCep();
        cep = helper.getCep();

        dataNascimento = findViewById(R.id.tvNascimentoCadastro);
        endereco = findViewById(R.id.tvEnderecoCadastro);

        btnConsultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Call<CEP> cepCall = new RetrofitConfig().getCEPService()
                        .buscaCep(cep.getText().toString());
                cepCall.enqueue(new Callback<CEP>() {
                    @Override
                    public void onResponse(Call<CEP> call, Response<CEP> response) {
                        if (response.isSuccessful()){
                            CEP cepConsultado = response.body();
                            endereco.setText(cepConsultado.toString());
                        } else {
                            Toast.makeText(getApplicationContext(), "Erro ao conectar", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<CEP> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "CEP NÃO LOCALIZADO", Toast.LENGTH_LONG).show();
                        Log.e("Throwable", " --------------------------- " + t);
                    }
                });
            }
        });

        dataNascimento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                //definindo como será exposto o DatePicker ao usuário
                DatePickerDialog dialog = new DatePickerDialog(
                        CadastroActivity.this,
                        android.R.style.Theme_Holo_Dialog_MinWidth,
                        listenerNascimento,
                        year, month, day
                );

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        listenerNascimento = new DatePickerDialog.OnDateSetListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                month = month +1;

                //formato da data a qual foi escolhida
                String data = dayOfMonth + "/" +  month + "/" + year;

                dataNascimento.setText(data);

            }
        };

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cliente cliente = helper.pegaCliente();

                cliente.setDataNascimento(dataNascimento.getText().toString());

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
                } else if(dataNascimento.getText().equals("Data de Nascimento")){
                    Toast.makeText(getApplicationContext(),"A data de nascimento é necessária.", Toast.LENGTH_LONG).show();
                } else {
                    ClienteDAO dao = new ClienteDAO(CadastroActivity.this);
                    dao.insere(cliente);
                    Toast.makeText(getApplicationContext(), "Cliente cadastrado com sucesso!", Toast.LENGTH_LONG).show();
                    finish();
                }

            }
        });
    }
}
