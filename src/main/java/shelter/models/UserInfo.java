package shelter.models;

public class UserInfo {
    private String uNum;
    private String uName;
    private String pName;
    private String pNum;

    public String getuNum() {
        return uNum;
    }

    public void setuNum(String uNum) {
        this.uNum = uNum;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getpNum() {
        return pNum;
    }

    public void setpNum(String pNum) {
        this.pNum = pNum;
    }
}
