package main;

import com.google.gson.Gson;
import db.DBController;
import db.SQLExceptionHandler;
import general.DummyGenerator;
import general.General;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;
import shelter.models.Care;
import shelter.models.ParentInfo;
import shelter.models.Shelter;
import shelter.models.UserInfo;
import shelter.services.ShelterService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        ArrayList<Shelter> shelters = null;
        DBController dbc = null;

//        try{
//            shelters = (ArrayList<Shelter>) new ShelterService().getShelterInfo();
//            // shelter 정보 따오기
//            dbc = DBController.getDBController();
//        }catch (SQLException e){
//            SQLExceptionHandler.printSQLException(e);
//        }catch (Exception ex){
//            ex.printStackTrace();
//        }
//
//        if(dbc != null)
//            dbc.shelterInfoBulkLoading(shelters);

        dbc = DBController.getDBController();
        // db connection 만들기

        dbc.dropTable();
        dbc.createTable();

        ArrayList<String> pNum = DummyGenerator.generatePhoneNum(300);
        ArrayList<UserInfo> userInfos = DummyGenerator.generateUserInfoFrom(new ArrayList<String>(pNum.subList(0,100)));
        ArrayList<ParentInfo> parentInfos = DummyGenerator.generateParentInfoFrom((new ArrayList<String>(pNum.subList(100,300))));

        for(UserInfo info : userInfos){
            dbc.insertUserInfo(info);
        }

        for(ParentInfo info : parentInfos){
            dbc.insertParentInfo(info);
        }

        for(int i = 0 ; i < userInfos.size(); i++){
            for(int j = 2*i; j < 2*i + 2; j++){
            //    System.out.println( "" + i + j);
                Care care = new Care();
                care.setUserPnum(userInfos.get(i).getuNum());
                care.setParentPnum(parentInfos.get(j).getpNum());
                dbc.insertCare(care);
            }
        }

        commandMenu();

        dbc.insertDummyEarthQuake();
        ArrayList<ParentInfo> parent_in_danger = (ArrayList<ParentInfo>) dbc.getParentInfoNearEarthquake();

        for(ParentInfo info : parent_in_danger){
            dbc.getShelterNearParent(info);
            dbc.getUserInfoFrom(info);
        }
    }

    public static ParentInfo scanParentInfo(){

        Scanner scan = new Scanner(System.in);
        System.out.println("Insert Parent Information!!");
        System.out.println("Parent의 PhoneNumber를 입력해주세요");
        String pNm = scan.next();
        System.out.println("Parent의 이름을 입력해주세요");
        String pName = scan.next();
        System.out.println("Parent의 위도를 입력해주세요");
        Double lat = scan.nextDouble();
        System.out.println("parent의 경도을 입력해주세요");
        Double lon = scan.nextDouble();
        System.out.println("+내 phoneNumber를 입력해주세요");
        String my = scan.next();
        Care care = new Care();
        ParentInfo newParentInfo = new ParentInfo();
        care.setUserPnum(my);
        care.setParentPnum(pNm);

        newParentInfo.setLat(lat);
        newParentInfo.setLon(lon);
        newParentInfo.setpName(pName);
        newParentInfo.setpNum(pNm);

        DBController.getDBController().insertParentInfo(newParentInfo);

        DBController.getDBController().insertCare(care);

        return newParentInfo;
    }

    public static UserInfo scanUserInfo(){
        Scanner scan = new Scanner(System.in);

        System.out.println("Insert User Information!!");
        System.out.println("User의 PhoneNumber를 입력해주세요");
        String uPhoneNm = scan.next();
        System.out.println("user의 이름을 입력해주세요");
        String uName = scan.next();
        System.out.println("User의 Age를 입력해주세요");
        Integer uAge = Integer.valueOf(scan.next());
        System.out.println("User의 Gender를 입력해주세요");
        String uGender = scan.next();

        UserInfo newInfo = new UserInfo();
        newInfo.setuNum(uPhoneNm);
        newInfo.setuName(uName);
        newInfo.setAge(uAge);
        newInfo.setGender(uGender);

        return newInfo;
    }

    public static void commandMenu(){
        Scanner scan = new Scanner(System.in);

        while(true){
            System.out.println("##### 재난 경보 알림 시스템 ######");
            System.out.println("------------- 메뉴 ------------ ");
            System.out.println("1. 사용자(본인)의 정보를 입력합니다.");
            System.out.println("2. 부모님(지인)의 정보를 입력합니다.");
            System.out.println("3. 사용자(본인)의 정보를 삭제합니다.");
            System.out.println("4. 부모님(지인)의 정보를 삭제합니다.");
            System.out.println("5. 프로그램을 종료합니다.");
            System.out.println("-------------------------------");
            System.out.println("메뉴 : ");
            int getMenu = scan.nextInt();

            if(getMenu == 1){
                DBController.getDBController().insertUserInfo(scanUserInfo());
                System.out.println("계속 실행하겠습니까? press 1)\n프로그램을 종료하시겠습니까? press 5");
                int getmenu1 = scan.nextInt();
                if(getmenu1 == 5){
                    break;
                }else {
                    System.out.println("저장 완료!!");
                }
            }
            else if(getMenu == 2){
                scanParentInfo();
                System.out.println("이어서 실행하겠습니까? press 1)\n프로그램을 종료하시겠습니까? press 5");
                int getmenu2 = scan.nextInt();
                if(getmenu2 == 5){
                    break;
                }else {
                    System.out.println("저장 완료!!");
                }
            }
            else if(getMenu == 3){
                Scanner scanner = new Scanner(System.in);
                System.out.println("사용자(본인)의 정보를 삭제합니다.");
                System.out.println("삭제하고자 하는 전화번호를 입력하세요");
                String deleteUnum = scanner.next();
                DBController.getDBController().deleteUserInfo(deleteUnum);
            }
            else if(getMenu == 4){
                Scanner scanner = new Scanner(System.in);
                System.out.println("부모님의 정보를 삭제합니다.");
                System.out.println("삭제하고자 하는 전화번호를 입력하세요");
                String deletePnum = scanner.next();
                DBController.getDBController().deleteParentInfo(deletePnum);
            }
            else if(getMenu == 5){
                break;
            }
            else {
                System.out.println("\n 올바른 메뉴 번호를 입력하세요!!");
            }
        }
    }
}
