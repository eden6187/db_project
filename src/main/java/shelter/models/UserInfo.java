package shelter.models;

public class UserInfo {
    private String uNum;
    private String uName;
    private Integer age;
    private String gender;

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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
