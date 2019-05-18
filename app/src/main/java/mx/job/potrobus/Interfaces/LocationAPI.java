package mx.job.potrobus.Interfaces;

import mx.job.potrobus.Entities.Location;
import retrofit2.Call;
import retrofit2.http.GET;

public interface LocationAPI {
    @GET("location")
    Call<Location> getLastLocation();
}
