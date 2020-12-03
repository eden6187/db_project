package main;

import com.google.gson.Gson;
import general.General;
import okhttp3.*;
import shelter.services.ShelterService;

import java.io.IOException;

public class Main {
    public static void main(String[] args){
        try{
            new Main().run();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void run() throws Exception {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://apis.data.go.kr/1741000/TsunamiShelter2/getTsunamiShelterList?ServiceKey=IFtOSVKdqT5O3DxQKx4%2BRPiAYs8n4OREn4pbQ7xtKz%2B8rqPWP8U7ziituRu2JzZfmM9DtxJN9ZcGet3Pw8e9Aw%3D%3D&type=json&pageNo=1&numOfRows=1000&flag=Y\n")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            System.out.println(response.body().string());
        }
    }
}
