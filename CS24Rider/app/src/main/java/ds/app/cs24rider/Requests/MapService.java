package ds.app.cs24rider.Requests;

import ds.app.cs24rider.Models.Maps.MapDirectionResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MapService {

    @GET("directions/json")
    Call<MapDirectionResponse> getDirections(@Query("destination") String destination, @Query("origin") String origin, @Query("mode") String mode, @Query("key") String key);

}
