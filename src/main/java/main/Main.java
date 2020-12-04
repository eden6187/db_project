package main;

import com.google.gson.Gson;
import db.DBController;
import db.SQLExceptionHandler;
import general.General;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;
import shelter.models.Shelter;
import shelter.services.ShelterService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args){
        ArrayList<Shelter> shelters = null;
        DBController dbc = null;
        try{
            shelters = (ArrayList<Shelter>) new ShelterService().getShelterInfo();
            dbc = DBController.getDBController();
        }catch (SQLException e){
            SQLExceptionHandler.printSQLException(e);
        }catch (Exception ex){
            ex.printStackTrace();
        }

        if(dbc != null)
            dbc.shelterInfoBulkLoading(shelters);

    }
}
