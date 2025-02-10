package vn.edu.usth.connect.Network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;
import vn.edu.usth.connect.Models.Dto.CoordinatesDTO;

public interface LocationService {

    @GET("/api/v1/location/get-coordinates-from-db")
    Call<CoordinatesDTO> getCoordinates(
            @Header("Authorization") String authHeader,
            @Query("location") String location
    );
}
