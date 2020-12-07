package shelter.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Shelter {
    @Expose
    private Integer id;
    @SerializedName("sido_name")
    @Expose
    private String sidoName;
    @SerializedName("sigungu_name")
    @Expose
    private String sigunguName;
    @SerializedName("remarks")
    @Expose
    private String remarks;
    @SerializedName("shel_nm")
    @Expose
    private String shelNm;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("lon")
    @Expose
    private Double lon;
    @SerializedName("lat")
    @Expose
    private Double lat;
    @SerializedName("shel_av")
    @Expose
    private Integer shelAv;
    @SerializedName("lenth")
    @Expose
    private Integer lenth;
    @SerializedName("shel_div_type")
    @Expose
    private String shelDivType;
    @SerializedName("seismic")
    @Expose
    private String seismic;
    @SerializedName("height")
    @Expose
    private Integer height;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSidoName() {
        return sidoName;
    }

    public void setSidoName(String sidoName) {
        this.sidoName = sidoName;
    }

    public String getSigunguName() {
        return sigunguName;
    }

    public void setSigunguName(String sigunguName) {
        this.sigunguName = sigunguName;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getShelNm() {
        return shelNm;
    }

    public void setShelNm(String shelNm) {
        this.shelNm = shelNm;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Integer getShelAv() {
        return shelAv;
    }

    public void setShelAv(Integer shelAv) {
        this.shelAv = shelAv;
    }

    public Integer getLenth() {
        return lenth;
    }

    public void setLenth(Integer lenth) {
        this.lenth = lenth;
    }

    public String getShelDivType() {
        return shelDivType;
    }

    public void setShelDivType(String shelDivType) {
        this.shelDivType = shelDivType;
    }

    public String getSeismic() {
        return seismic;
    }

    public void setSeismic(String seismic) {
        this.seismic = seismic;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    @Override
    public String toString() {
        String string = "";
        string += "시설 이름 :"  + this.shelNm +"\n";
        string += "시도 : " + this.sidoName +"\n";
        string += "시군구 : " + this.sigunguName +"\n";
        string += "위도 : " + this.lat +"\n";
        string += "경도 : " + this.lon +"\n";
        return string;
    }
}
