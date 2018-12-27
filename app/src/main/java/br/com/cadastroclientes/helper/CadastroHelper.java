package br.com.cadastroclientes.helper;

import android.widget.Button;
import android.widget.EditText;

import br.com.cadastroclientes.CadastroActivity;
import br.com.cadastroclientes.R;
import br.com.cadastroclientes.model.Cliente;

/**
 * Created by Samantha on 24/12/2018.
 */

public class CadastroHelper {

    private EditText nome, sobrenome, cpf, cep;
    private Cliente cliente;
    private Button btnCadastrar, btnConsultaCep;

    public CadastroHelper(CadastroActivity activity){
        cliente = new Cliente();
        nome = activity.findViewById(R.id.edNomeCadastro);
        sobrenome = activity.findViewById(R.id.edSobrenomeCadastro);
        cpf = activity.findViewById(R.id.edCPFCadastro);
        cep = activity.findViewById(R.id.edCEPCadastro);
        btnCadastrar = activity.findViewById(R.id.btnCadastro);
        btnConsultaCep = activity.findViewById(R.id.btnConsultar);
    }

    public Button getBtnCadastrar(){
        return btnCadastrar;
    }

    public Button getBtnConsultaCep() {
        return btnConsultaCep;
    }

    public EditText getCep() {
        return cep;
    }

    /**
     * Busca um objeto cliente.
     *
     * @return cliente.
     */
    public Cliente pegaCliente() {
        cliente.setNome(nome.getText().toString());
        cliente.setSobrenome(sobrenome.getText().toString());
        cliente.setCpf(cpf.getText().toString());
        cliente.setCep(cep.getText().toString());

        return cliente;
    }


}
