package br.com.cadastroclientes.holder;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import br.com.cadastroclientes.ConfiguracoesActivity;
import br.com.cadastroclientes.R;
import br.com.cadastroclientes.adapter.ClienteAdapter;
import br.com.cadastroclientes.model.Cliente;

/**
 * Created by Samantha on 24/12/2018.
 */

public class ClienteViewHolder extends RecyclerView.ViewHolder{

    private final ClienteAdapter adapter;
    private Long clienteId;
    private TextView nomeCliente, cpfCliente;

    public ClienteViewHolder(View itemView, ClienteAdapter adapter) {
        super(itemView);

        this.adapter = adapter;

        nomeCliente = itemView.findViewById(R.id.nomeView);
        cpfCliente = itemView.findViewById(R.id.cpfView);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Activity context = (Activity) view.getContext();
                final Intent intent = new Intent(context, ConfiguracoesActivity.class);
                intent.putExtra("clienteId", clienteId);
                context.startActivity(intent);
            }
        });
    }

    /**
     * Preenche dados na listagem.
     * @param cliente
     */
    public void preencher(Cliente cliente){
        clienteId = cliente.getId();

        //nome completo
        String nomeCompletoCl = cliente.getNome() + " " + cliente.getSobrenome();
        nomeCliente.setText(nomeCompletoCl);

        //cpf
        String cpfCl = cliente.getCpf();
        cpfCliente.setText(cpfCl);

    }
}
