package general;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.concurrent.TimeUnit;

public class General {
    public static boolean flag = true;
    private final static String mShelterBaseUrl = "http://apis.data.go.kr/1741000/TsunamiShelter2/";
    private final static String mShelterKey = "IFtOSVKdqT5O3DxQKx4%2BRPiAYs8n4OREn4pbQ7xtKz%2B8rqPWP8U7ziituRu2JzZfmM9DtxJN9ZcGet3Pw8e9Aw%3D%3D";
    private static Retrofit mShelterRetrofit;

    public static Retrofit getShelterRetrofit(){

        if(mShelterRetrofit == null){
            OkHttpClient client = new OkHttpClient.Builder()
                    .readTimeout(5000, TimeUnit.MILLISECONDS)
                    .connectTimeout(5000, TimeUnit.MILLISECONDS)
                    .build();

            mShelterRetrofit = new Retrofit.Builder()
                    .baseUrl(mShelterBaseUrl)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return mShelterRetrofit;
    }

    public static String getmShelterKey() {
        return mShelterKey;
    }
}
