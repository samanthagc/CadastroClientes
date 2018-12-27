package br.com.cadastroclientes;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;

import java.util.List;

import br.com.cadastroclientes.adapter.ClienteAdapter;
import br.com.cadastroclientes.dao.ClienteDAO;
import br.com.cadastroclientes.model.Cliente;

/**
 * Created by Samantha on 24/12/2018.
 */

public class MainActivity extends AppCompatActivity {

    private RecyclerView listaClientes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listaClientes = findViewById(R.id.listaDeClientes);

        carregarLista();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CadastroActivity.class);
                startActivity(intent);
            }
        });
    }

    private void carregarLista() {
        ClienteDAO dao = new ClienteDAO(this);
        List<Cliente> clientes =  dao.buscarClientes();
        ClienteAdapter adapter = new ClienteAdapter(this, clientes);
        listaClientes.setAdapter(adapter);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 1);
        listaClientes.setLayoutManager(layoutManager);
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregarLista();
    }
}
