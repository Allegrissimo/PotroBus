package mx.job.potrobus.Interfaces;

import mx.job.potrobus.Entities.Usuario;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserAPI {

    @POST("login")
    Call<Usuario> login(@Body Usuario usuario);

    @POST("signup")
    Call<Usuario> signup(@Body Usuario usuario);
}
