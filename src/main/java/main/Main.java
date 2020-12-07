package main;

import com.google.gson.Gson;
import db.DBController;
import db.SQLExceptionHandler;
import general.General;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;
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
//        UserInfo userInfo = scanUserInfo();
//        dbc.insertUserInfo(userInfo);
//        ParentInfo parentInfo = scanParentInfo();
//        dbc.insertParentInfo(parentInfo);
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
}
