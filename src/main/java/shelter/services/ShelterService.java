package shelter.services;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import shelter.models.Shelter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ShelterService {

    /*
    설명 : 공공데이터 포털로부터 지진/해일 대피소 정보를 동기적으로 가져옵니다.
    인자 : -
    리턴 : Shelter 정보들을 담고 있는 List;
     */
    public List<Shelter> getShelterInfo() throws Exception {
        List<Shelter> shelters = null;
        int dataNum = -1;

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://apis.data.go.kr/1741000/TsunamiShelter2/getTsunamiShelterList?ServiceKey=IFtOSVKdqT5O3DxQKx4%2BRPiAYs8n4OREn4pbQ7xtKz%2B8rqPWP8U7ziituRu2JzZfmM9DtxJN9ZcGet3Pw8e9Aw%3D%3D&type=json&pageNo=1&numOfRows=1000&flag=Y\n")
                .build();
        // bulk loading;

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            String body = Objects.requireNonNull(response.body()).string();
            //공공 데이터 포털로 부터 데이터를 가져오면

            if(response.body() != null) {
                JSONObject responseObject = new JSONObject(body);// 최상위 object;
                System.out.println("----- shelter info json parsing... -----");
                JSONArray jsonArray = responseObject.getJSONArray("TsunamiShelter");

                JSONObject head = jsonArray.getJSONObject(0);
                int total_cnt = head.getJSONArray("head").getJSONObject(0).getInt("totalCount");
                // Array의 첫번째 object -> head
                System.out.printf("----- now we have %d shelter info..! -----\n", total_cnt);

                if(total_cnt > 0) {
                    JSONObject data = jsonArray.getJSONObject(1);
                    // Array의 두번째 object -> row

                    JSONArray rows = data.getJSONArray("row");
                    // 실제 지진 대피소 정보를 가지고 있다.
                    shelters = new ArrayList<Shelter>();

                    for(int i = 0 ; i < total_cnt; i++){
                        Shelter shelter = parseShelterInfo(rows.getJSONObject(i));
                        shelters.add(shelter);
                    }
                }
                System.out.println("----- shelter info json successfully parsed! -----");
            }
        }

        return shelters;
    }

    private static Shelter parseShelterInfo(JSONObject json){
        Shelter shelter = new Shelter();
        shelter.setId(json.getInt("id"));
        shelter.setSidoName(json.getString("sido_name"));
        shelter.setSigunguName(json.getString("sigungu_name"));
        shelter.setRemarks(json.getString("remarks"));
        shelter.setShelNm(json.getString("shel_nm"));
        shelter.setAddress(json.getString("address"));
        shelter.setLon(json.getDouble("lon"));
        shelter.setLat(json.getDouble("lat"));
        shelter.setShelAv(json.getInt("shel_av"));
        shelter.setLenth(json.getInt("lenth"));
        shelter.setShelDivType(json.getString("shel_div_type"));
        shelter.setHeight(json.getInt("height"));
        return shelter;
    }
}
