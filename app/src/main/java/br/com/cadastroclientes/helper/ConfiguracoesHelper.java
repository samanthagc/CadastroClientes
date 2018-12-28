package br.com.cadastroclientes.helper;

import android.widget.Button;
import android.widget.EditText;

import br.com.cadastroclientes.ConfiguracoesActivity;
import br.com.cadastroclientes.R;
import br.com.cadastroclientes.model.Cliente;

/**
 * Created by Samantha on 27/12/2018.
 */

public class ConfiguracoesHelper {

    private EditText nomeEdicao, sobrenomeEdicao, cpfEdicao, cepEdicao;
    private Cliente clienteEdicao;
    private Button btnEditar, btnMaps;

    public ConfiguracoesHelper(ConfiguracoesActivity activity){
        clienteEdicao = new Cliente();
        nomeEdicao = activity.findViewById(R.id.edNomeEdicao);
        sobrenomeEdicao = activity.findViewById(R.id.edSobrenomeEdicao);
        cpfEdicao = activity.findViewById(R.id.edCPFEdicao);
        cepEdicao = activity.findViewById(R.id.edCEPEdicao);
        btnEditar = activity.findViewById(R.id.btnEdicao);
        btnMaps = activity.findViewById(R.id.btnMaps);
    }


    public EditText getNomeEdicao() {
        return nomeEdicao;
    }

    public EditText getSobrenomeEdicao() {
        return sobrenomeEdicao;
    }

    public EditText getCpfEdicao() {
        return cpfEdicao;
    }

    public EditText getCepEdicao() {
        return cepEdicao;
    }

    public Button getBtnEditar() {
        return btnEditar;
    }

    public Button getBtnMaps() {
        return btnMaps;
    }

    /**
     * Busca um objeto cliente.
     *
     * @return cliente.
     */
    public Cliente pegaClienteEdicao() {
        clienteEdicao.setNome(nomeEdicao.getText().toString());
        clienteEdicao.setSobrenome(sobrenomeEdicao.getText().toString());
        clienteEdicao.setCpf(cpfEdicao.getText().toString());
        clienteEdicao.setCep(cepEdicao.getText().toString());

        return clienteEdicao;
    }
}
