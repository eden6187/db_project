package db;

import shelter.models.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DBController {
    private static final String url = "jdbc:postgresql://localhost:5432/project";
    private static final String user = "gim-yeonghyeon";
    private static final String password = "!!dnwn556";

    private Connection connection = null;
    private static DBController singleTon;

    private DBController() { }

    public static DBController getDBController(){
        if(singleTon == null)
            singleTon = new DBController();

        return singleTon;
    }

    private void makeConnection() throws SQLException{
        if(this.connection == null) {
            this.connection = DriverManager.getConnection(url, user, password);
            System.out.println("success to make connection with DB!");
        }
    }

    public void shelterInfoBulkLoading(List<Shelter> shelterList){
        try {
            this.makeConnection();
            this.dropShelterTable();
            this.createShelterTable();
        }catch (SQLException ex){
            SQLExceptionHandler.printSQLException(ex);
        }

        Statement stmt = null;
        try {
            stmt = this.connection.createStatement();
            this.connection.setAutoCommit(false);
        }catch (SQLException ex){
            SQLExceptionHandler.printSQLException(ex);
        }

        try {
            for (Shelter shelter : shelterList) {
                String sql = "INSERT INTO shelter VALUES( "
                        + shelter.getId() + ", "
                        + "'" + shelter.getSidoName() + "', "
                        + "'" + shelter.getSigunguName() + "', "
                        + "'" + shelter.getRemarks() + "', "
                        + "'" + shelter.getShelNm() + "', "
                        + "'" + shelter.getAddress() + "', "
                        + shelter.getLon() + ", "
                        + shelter.getLat() + ", "
                        + shelter.getLenth() + ", "
                        + shelter.getShelAv() + ", "
                        + "'" + shelter.getShelDivType() + "', "
                        + shelter.getHeight()
                        + " );";
                System.out.println(sql);
                stmt.addBatch(sql);
            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }
        try {
            stmt.executeBatch();
        }catch (SQLException ex) {
            ex.printStackTrace();
        }finally {
            try {
                connection.commit();
                connection.setAutoCommit(true);
            }catch (Exception exception){
                exception.printStackTrace();
            }
        }
    }

    public void createShelterTable(){
        try {
            this.makeConnection();
            String sql  = "CREATE TABLE shelter( "
                    +" id INTEGER PRIMARY KEY,"
                    +" sido_name VARCHAR(255),"
                    +" sigungu_name VARCHAR(255),"
                    +" remarks VARCHAR(255),"
                    +" shel_nm VARCHAR(255),"
                    +" address VARCHAR(255),"
                    +" lon NUMERIC(9,6),"
                    +" lat NUMERIC(8,6),"
                    +" shel_av INTEGER,"
                    +" lenth INTEGER,"
                    +" shel_div_type VARCHAR(256),"
                    +" height INTEGER"
                    +" );";

            Statement stmt = connection.createStatement();
            stmt.execute(sql);
        }catch(SQLException ex){
            SQLExceptionHandler.printSQLException(ex);
        }
    }

    public void createTable(){
        createShelterTable();
        createUserInfoTable();
        createParentInfoTable();
        createCareTable();
        createTyphoonTable();
        createEarthQuakeTable();
    }

    public void dropTable(){
        dropUserInfoTable();
        dropParentInfoTable();
        dropCareTable();
        dropTyphoonTable();
        dropEarthquakeTable();
    }

    public void createUserInfoTable(){
        try{
            this.makeConnection();
            Statement stmt = this.connection.createStatement();
            String sql = "Create Table UserInfo(userPhoneNum varchar(11), userName varchar(20)," +
                    " age int, gender char, primary key(userPhoneNum))";
            stmt.executeUpdate(sql);
        } catch (SQLException ex){
            SQLExceptionHandler.printSQLException(ex);
        }
    }

    public void createParentInfoTable(){
        try{
            this.makeConnection();
            Statement stmt = this.connection.createStatement();
            String sql = "Create Table ParentInfo(parentPhoneNum varchar(11), parentName varchar(20), " +
                    "latitude NUMERIC(8,6), longtitude NUMERIC(9,6), primary key(parentPhoneNum))";
            stmt.executeUpdate(sql);
        } catch (SQLException ex){
            SQLExceptionHandler.printSQLException(ex);
        }
    }

    public void createCareTable(){
        try{
            this.makeConnection();
            Statement stmt = this.connection.createStatement();
            String sql = "Create Table Care(userPhoneNum varchar(11), parentPhoneNum varchar(11), " +
                    "primary key(userPhoneNum,parentPhoneNum), " +
                    "foreign key(userPhoneNum) references UserInfo(userPhoneNum)," +
                    "foreign key(parentPhoneNum) references ParentInfo(parentPhoneNum));";
            stmt.executeUpdate(sql);
        } catch (SQLException ex){
            SQLExceptionHandler.printSQLException(ex);
        }
    }

    public void createTyphoonTable(){
        try{
            this.makeConnection();
            Statement stmt = this.connection.createStatement();
            String sql = "Create Table Typhoon(tId int, tName varchar(10)," +
                    "latitude NUMERIC(8,6), longtitude NUMERIC(9,6), tTime varchar(20)" +
                    ",primary key(tID));";
            stmt.executeUpdate(sql);
        } catch (SQLException ex){
            SQLExceptionHandler.printSQLException(ex);
        }
    }

    public void createEarthQuakeTable(){
        try{
            this.makeConnection();
            Statement stmt = this.connection.createStatement();
            String sql = "Create Table Earthquake(eqId int," +
                    "latitude NUMERIC(8,6), longtitude NUMERIC(9,6), eqTime varchar(20), eqScale float" +
                    ",primary key(eqID));";
            stmt.executeUpdate(sql);
        } catch (SQLException ex){
            SQLExceptionHandler.printSQLException(ex);
        }
    }

    public void dropShelterTable(){
        try{
            this.makeConnection();
            Statement stmt = this.connection.createStatement();
            String del1="drop table Shelter;";
            stmt.executeUpdate(del1);
        }catch(SQLException ex){
            SQLExceptionHandler.printSQLException(ex);
        }
    }

    public void dropUserInfoTable(){
        try{
            this.makeConnection();
            Statement stmt = this.connection.createStatement();
            String del1="drop table UserInfo cascade;";
            stmt.executeUpdate(del1);
        }catch(SQLException ex){
            SQLExceptionHandler.printSQLException(ex);
        }
    }

    public void dropParentInfoTable(){
        try{
            this.makeConnection();
            Statement stmt = this.connection.createStatement();
            String del2="drop table ParentInfo cascade;";
            stmt.executeUpdate(del2);
        }catch(SQLException ex){
            SQLExceptionHandler.printSQLException(ex);
        }
    }

    public void dropCareTable(){
        try{
            this.makeConnection();
            Statement stmt = this.connection.createStatement();
            String del3="drop table Care cascade;";
            stmt.executeUpdate(del3);
        }catch(SQLException ex){
            SQLExceptionHandler.printSQLException(ex);
        }
    }

    public void dropTyphoonTable(){
        try{
            this.makeConnection();
            Statement stmt = this.connection.createStatement();
            String del1="drop table Typhoon cascade;";
            stmt.executeUpdate(del1);
        }catch(SQLException ex){
            SQLExceptionHandler.printSQLException(ex);
        }
    }

    public void dropEarthquakeTable(){
        try{
            this.makeConnection();
            Statement stmt = this.connection.createStatement();
            String del2="drop table Earthquake cascade;";
            stmt.executeUpdate(del2);
        }catch(SQLException ex){
            SQLExceptionHandler.printSQLException(ex);
        }
    }

    public void insertParentInfo(ParentInfo info){
        String sqlP = "Insert Into ParentInfo(parentPhoneNum,parentName,latitude,longtitude)" +
                " Values(?,?,?,?);";
        try{
            this.makeConnection();
            PreparedStatement pre_stmt = this.connection.prepareStatement(sqlP);
            pre_stmt.setString(1,info.getpNum());
            pre_stmt.setString(2, info.getpName());
            pre_stmt.setDouble(3, info.getLat()); // 형변환
            pre_stmt.setDouble(4, info.getLon());
            pre_stmt.execute();

        }catch (SQLException ex){
            SQLExceptionHandler.printSQLException(ex);
        }

    }

    public void insertUserInfo(UserInfo userInfo) {
        String sqlR = "Insert Into UserInfo(userPhoneNum,userName,age,gender)" +
                " Values(?,?,?,?);";
        try {
            this.makeConnection();
            PreparedStatement pre_stmt = this.connection.prepareStatement(sqlR);
            pre_stmt = this.connection.prepareStatement(sqlR);
            pre_stmt.setString(1, userInfo.getuNum());
            pre_stmt.setString(2, userInfo.getuName());
            pre_stmt.setInt(3, userInfo.getAge());
            pre_stmt.setString(4, userInfo.getGender());
            pre_stmt.execute();
        } catch (SQLException ex) {
            SQLExceptionHandler.printSQLException(ex);
        }
    }

    public void insertTyphoon(Typhoon typhoon){
        String sqlT = "Insert Into Typhoon(tId, tName,lat,lon,tTime) "+
                " Values(?,?,?,?,?);";
        try {
            this.makeConnection();
            PreparedStatement pre_stmt = this.connection.prepareStatement(sqlT);
            pre_stmt.setInt(1, typhoon.gettID());
            pre_stmt.setString(2, typhoon.gettName());
            pre_stmt.setDouble(3, typhoon.getLat());
            pre_stmt.setDouble(4, typhoon.getLon());
            pre_stmt.setString(5, typhoon.gettTime());
            pre_stmt.execute();
        } catch (SQLException ex) {
            SQLExceptionHandler.printSQLException(ex);
        }
    }

    public void insertEarthquake(Earthquake earthquake){
        String sqlT = "Insert Into Earthquake(eqId,lat,lon,eqTime,eqScale) "+
                " Values(?,?,?,?,?);";
        try {
            this.makeConnection();
            PreparedStatement pre_stmt = this.connection.prepareStatement(sqlT);
            pre_stmt.setInt(1, earthquake.getEqId());
            pre_stmt.setDouble(2, earthquake.getLat());
            pre_stmt.setDouble(3, earthquake.getLon());
            pre_stmt.setString(4, earthquake.getEqTime());
            pre_stmt.setFloat(5,earthquake.getEqScale());
            pre_stmt.execute();
        } catch (SQLException ex) {
            SQLExceptionHandler.printSQLException(ex);
        }
    }
}
