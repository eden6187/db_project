package main;

import db.DBController;
import general.DummyGenerator;
import shelter.models.Care;
import shelter.models.ParentInfo;
import shelter.models.Shelter;
import shelter.models.UserInfo;

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
                Care care = new Care();
                care.setUserPnum(userInfos.get(i).getuNum());
                care.setParentPnum(parentInfos.get(j).getpNum());
                dbc.insertCare(care);
            }
        }

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
        Double lon = scan.nextDouble();
        System.out.println("parent의 경도을 입력해주세요");

        Double lat = scan.nextDouble();
        ParentInfo newParentInfo = new ParentInfo();
        newParentInfo.setLat(lat);
        newParentInfo.setLon(lon);
        newParentInfo.setpName(pName);
        newParentInfo.setpNum(pNm);

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

    public static void scanUserPhoneNumToDelete(){
        System.out.println("삭제하고자 할 User의 핸드폰 번호를 입력하세요 : ");
        Scanner scanner = new Scanner(System.in);
        scanner.next();

    }
}
