package shelter.models;

public class Earthquake {
    private Integer eqId;
    private Double lat;
    private Double lon;
    private String eqTime;
    private Float eqScale;

    public Integer getEqId() {
        return eqId;
    }

    public void setEqId(Integer eqId) {
        this.eqId = eqId;
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

    public String getEqTime() {
        return eqTime;
    }

    public void setEqTime(String eqTime) {
        this.eqTime = eqTime;
    }

    public Float getEqScale() {
        return eqScale;
    }

    public void setEqScale(Float eqScale) {
        this.eqScale = eqScale;
    }
}
