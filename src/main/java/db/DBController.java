package db;

import shelter.models.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DBController {
    private static final String url = "jdbc:postgresql://localhost:5432/dbProject";
    private static final String user = "postgres";
    private static final String password = "oh54285428";

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
                    "foreign key(userPhoneNum) references UserInfo(userPhoneNum) on delete cascade," +
                    "foreign key(parentPhoneNum) references ParentInfo(parentPhoneNum) on delete cascade);";
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
                    "latitude NUMERIC(8,6), longtitude NUMERIC(9,6), eqTime varchar(30), eqScale float" +
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

    public void insertTyphoon(){
        String sqlT1 = "Insert Into Typhoon(tId, tName,latitude,longtitude,tTime) "+
                " Values('11','Mae-Mi','35.124836','128.325815','201128-17:30:55');";
        String sqlT2 = "Insert Into Typhoon(tId, tName,latitude,longtitude,tTime) "+
                " Values('22','Gaeguri','35.321238','128.189523','201206-19:30:45');";
        String sqlT3 = "Insert Into Typhoon(tId, tName,latitude,longtitude,tTime) "+
                " Values('33','Cham-sae','35.651379','128.804521','201208-14:35:28');";
        try {
            this.makeConnection();
            Statement stmt = this.connection.createStatement();
            stmt.executeUpdate(sqlT1);
            stmt.executeUpdate(sqlT2);
            stmt.executeUpdate(sqlT3);
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

    public void insertCare(Care care){
        String sql = "Insert Into Care(userphonenum, parentphonenum) "+
                " Values(?,?);";
        try {
            PreparedStatement pre_stmt = this.connection.prepareStatement(sql);
            pre_stmt.setString(1, care.getUserPnum());
            pre_stmt.setString(2, care.getParentPnum());
            pre_stmt.execute();
//            this.connection.commit();
        }catch (SQLException ex){
            SQLExceptionHandler.printSQLException(ex);
        }

    }
    public void deleteUserInfo(String userPhoneNum){
        String sql = "delete from userinfo where userphonenum = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, userPhoneNum);
            preparedStatement.execute();
        }catch (SQLException ex){
            SQLExceptionHandler.printSQLException(ex);
        }

    }

    public void deleteParentInfo(String parentPhoneNum){
        String sql = "delete from parentinfo where parentphonenum = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, parentPhoneNum);
            preparedStatement.execute();
        }catch (SQLException ex){
            SQLExceptionHandler.printSQLException(ex);
        }

    }

    public void dangerParent(){
        ResultSet rs;

        dropTyphoonTable();
        createTyphoonTable();
        insertTyphoon();

        String tem1 = "drop table tem";
        String tem2 = "create temporary table tem as\n" +
                "select *\n" +
                "from parentinfo as P\n" +
                "where exists(\n" +
                "select 1\n" +
                "from typhoon as T\n" +
                "where ST_DistanceSphere(\n" +
                " \t\tST_SetSRID(ST_MakePoint(P.latitude, P.longtitude), 4326),\n" +
                "\t\tST_SetSRID(ST_MakePoint(T.latitude, T.longtitude), 4326) \n" +
                "\t\t) < 5000\n" +
                ");";

        try {
            this.makeConnection();
            Statement stmt = this.connection.createStatement();
//            stmt.executeUpdate(tem1);
            System.out.printf("ppppp");
            stmt.execute(tem2);
            System.out.printf("ccccc");
        } catch (SQLException ex) {
            SQLExceptionHandler.printSQLException(ex);
        }
//
//        try {
//            this.makeConnection();
//            Statement stmt = this.connection.createStatement();
//            String tem3 = "update typhoon set latitude = '35.333333', longtitude = '128.644444'" +
//                    "where latitude = '35.124836' and longtitude = '128.325815';";
//            String tem4 = "drop table tem";
//            String tem5 = "create temporary table Tem as select * from parentinfo as P" +
//                    " where exists(select 1 from typhoon as T where ST_DistanceSphere(" +
//                    "ST_SetSRID(ST_MakePoint(P.latitude,P.longtitude),4326)," +
//                    "ST_SetSRID(ST_MakePoint(T.latitude,T.longtitude),4326)) < 5000);";
//            stmt.executeUpdate(tem3);
//            stmt.executeUpdate(tem4);
//            stmt.executeUpdate(tem5);
//        } catch (SQLException ex) {
//            SQLExceptionHandler.printSQLException(ex);
//        }
    }
}
