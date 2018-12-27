package br.com.cadastroclientes.config;

import br.com.cadastroclientes.service.CEPService;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import static br.com.cadastroclientes.util.AppUtil.BASE_URL;

/**
 * Created by Samantha on 26/12/2018.
 */

public class RetrofitConfig {

    public final Retrofit retrofit;

    public RetrofitConfig() {
        //construindo objeto do tipo retrofit     /definindo a url base da API
        retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                //convertendo os valores de Json para String
                .addConverterFactory(JacksonConverterFactory.create()).build();
    }

    public CEPService getCEPService(){
        return retrofit.create(CEPService.class);
    }
}
