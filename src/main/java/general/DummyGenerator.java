package general;

import shelter.models.ParentInfo;
import shelter.models.UserInfo;

import java.util.*;

public class DummyGenerator {
    public static ArrayList<String> generatePhoneNum(int dummySize){

        HashSet<String> phoneNumSet = new HashSet<String>();

        while(phoneNumSet.size() < dummySize){
            phoneNumSet.add(randomNumber(8));
        }

        return new ArrayList<String>(phoneNumSet);
    }

    public static String randomName(int size) {
        Random r = new Random();
        StringBuilder sb = new StringBuilder(size);

        for(int i = 0; i < size; i++) {
            char tmp = (char) ('a' + r.nextInt('z' - 'a'));
            sb.append(tmp);
        }
        return sb.toString();
    }

    public static String randomNumber(int size){
        Random rand = new Random();
        String numStr = "010";

        for(int i=0;i<size;i++){
            String ran = Integer.toString(rand.nextInt(10));

            numStr += ran;
        }
        return numStr;
    }

    public static ArrayList<UserInfo> generateUserInfoFrom(ArrayList<String> pnum){
        Random rand = new Random();
        ArrayList<UserInfo> userInfo = new ArrayList<UserInfo>();
        for(int i = 0; i < pnum.size(); i++){
            int gender = rand.nextInt(2);
            UserInfo newUserInfo = new UserInfo();
            newUserInfo.setuNum(pnum.get(i));
            newUserInfo.setuName(randomName(10));
            newUserInfo.setGender(gender == 0 ? "M" : "F");
            newUserInfo.setAge(new Random().nextInt(50));
            userInfo.add(newUserInfo);
        }
        return userInfo;
    }

    public static ArrayList<ParentInfo> generateParentInfoFrom(ArrayList<String> pnum){
        Random r = new Random();

        double lonRangeMin = 128;
        double lonRangeMax = 129;
        double latRangeMin = 35;
        double latRangeMax = 36;

        double randomLat = 0.0;
        double randomLon = 0.0;

        ArrayList<ParentInfo> parentInfo = new ArrayList<ParentInfo>();

        for (String s : pnum) {

            ParentInfo newParentInfo = new ParentInfo();

            newParentInfo.setpNum(s);

            newParentInfo.setpName(randomName(10));

            randomLon = lonRangeMin + (lonRangeMax - lonRangeMin) * r.nextDouble();
            newParentInfo.setLon(randomLon);

            randomLat = latRangeMin + (latRangeMax - latRangeMin) * r.nextDouble();
            newParentInfo.setLat(randomLat);

            parentInfo.add(newParentInfo);
        }

        return parentInfo;
    }
}
