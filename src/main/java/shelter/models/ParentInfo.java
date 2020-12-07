package shelter.models;

public class ParentInfo {
    private String pNum;
    private String pName;
    private Double lat;
    private Double lon;

    public String getpNum() {
        return pNum;
    }

    public void setpNum(String pNum) {
        this.pNum = pNum;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    @Override
    public String toString() {
        String string = "";
        string += "이름 : " + this.pName +"\n";
        string += "휴대폰 번호 : " + this.pNum +"\n";
        string += "위도 : " + this.lat +"\n";
        string += "경도 : " + this.lon +"\n";
        return string;
    }
}
