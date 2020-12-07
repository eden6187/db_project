package shelter.models;

public class Typhoon {
    private Integer tID;
    private String tName;
    private Double lat;
    private Double lon;
    private String tTime;

    public Integer gettID() {
        return tID;
    }

    public void settID(Integer tID) {
        this.tID = tID;
    }

    public String gettName() {
        return tName;
    }

    public void settName(String tName) {
        this.tName = tName;
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

    public String gettTime() {
        return tTime;
    }

    public void settTime(String tTime) {
        this.tTime = tTime;
    }
}
