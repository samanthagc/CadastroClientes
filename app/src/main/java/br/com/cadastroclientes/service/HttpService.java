package br.com.cadastroclientes.service;

import android.os.AsyncTask;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import br.com.cadastroclientes.model.CEP;
import br.com.cadastroclientes.util.AppUtil;

/**
 * Created by Samantha on 26/12/2018.
 */

public class HttpService extends AsyncTask<Void, Void, CEP> {

    private final String cep;

    public HttpService(String cep) {
        this.cep = cep;
    }

    @Override
    protected CEP doInBackground(Void... voids) {

        StringBuilder resposta = new StringBuilder();

        try {
            URL url = new URL(AppUtil.BASE_URL + cep);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-type", "application/json");
            connection.setDoOutput(true);
            connection.setConnectTimeout(5000);
            connection.connect();

            //url.openStream() é o json
            Scanner scanner = new Scanner(url.openStream());
            while (scanner.hasNext()) {
                resposta.append(scanner.next());
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //recebe o json e mapeia-o para transformá-lo em java
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            return objectMapper.readValue(resposta.toString(), CEP.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
