package shelter.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ShelterResponse {
    @SerializedName("TsunamiShelter")
    List<ShelterResponseElement> shelterResponseElementList;

    public List<ShelterResponseElement> getShelterResponseElementList() {
        return shelterResponseElementList;
    }

    public void setShelterResponseElementList(List<ShelterResponseElement> shleterResponseElementList) {
        this.shelterResponseElementList = shleterResponseElementList;
    }
}
