package shelter.services;

import general.General;
import org.jetbrains.annotations.NotNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;
import shelter.interfaces.ShelterRetrofitInterface;
import shelter.models.ShelterResponse;

import java.io.IOException;

public class ShelterService {
    public void getShelterInfo(
            String type,
            int page_no,
            int numOfRow,
            String flag
    ){
        final ShelterRetrofitInterface shelterRetrofitInterface = General.getShelterRetrofit().create(ShelterRetrofitInterface.class);
        shelterRetrofitInterface.getShelterInfo(General.getmShelterKey(), type, page_no, numOfRow, flag).enqueue(
                new Callback<ShelterResponse>() {
                    @Override
                    public void onResponse(Call<ShelterResponse> call, Response<ShelterResponse> response) {
                        General.flag = false;
                    }

                    @Override
                    public void onFailure(Call<ShelterResponse> call, Throwable t) {

                    }
                }
        );
    }
}
