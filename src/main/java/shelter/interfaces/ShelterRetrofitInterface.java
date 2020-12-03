package shelter.interfaces;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import shelter.models.ShelterResponse;

public interface ShelterRetrofitInterface {
    @GET("/TsunamiShelter2")
    Call<ShelterResponse> getShelterInfo(
            @Query("ServiceKey") String key,
            @Query("type") String type,
            @Query("pageNo") int page_no,
            @Query("numOfRows") int numOfRow,
            @Query("flag") String flag
    );
}
