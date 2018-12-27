package br.com.cadastroclientes.service;

import br.com.cadastroclientes.model.CEP;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Samantha on 26/12/2018.
 */

public interface CEPService {

    @GET("{cep}")
    Call<CEP> buscaCep(@Path("cep") String cep);
}
