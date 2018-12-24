package br.com.cadastroclientes.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import br.com.cadastroclientes.R;
import br.com.cadastroclientes.holder.ClienteViewHolder;
import br.com.cadastroclientes.model.Cliente;

/**
 * Created by Samantha on 24/12/2018.
 */

public class ClienteAdapter extends RecyclerView.Adapter {

    private final Context context;
    private final List<Cliente> clientes;

    public ClienteAdapter(Context context, List<Cliente> clientes) {
        this.context = context;
        this.clientes = clientes;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_clientes, parent, false);
        ClienteViewHolder holder = new ClienteViewHolder(view, this);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ClienteViewHolder viewHolder = (ClienteViewHolder) holder;
        Cliente cliente = clientes.get(position);
        viewHolder.preencher(cliente);

    }

    @Override
    public int getItemCount() {
        return clientes.size();
    }
}
