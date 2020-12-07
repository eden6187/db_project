package general;

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
}
