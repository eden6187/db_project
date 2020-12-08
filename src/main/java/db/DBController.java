package db;

import general.DummyGenerator;
import shelter.models.*;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

    public ArrayList<ParentInfo> getParentInfoNearEarthquake(){
        ArrayList<ParentInfo> parentInfos = new ArrayList<ParentInfo>();

        ResultSet resultSet = null;

        String sql = "select * \n" +
                "from parentinfo as P\n" +
                "where exists(\n" +
                "       select 1\n" +
                "       from earthquake\n" +
                "       where ST_DistanceSphere(\n" +
                "               ST_SetSRID(ST_MakePoint(P.latitude, P.longtitude), 4326),\n" +
                "               ST_SetSRID(ST_MakePoint(earthquake.latitude, earthquake.longtitude), 4326) \n" +
                "                   ) < 5000\n" +
                "               and \n" +
                "                   to_timestamp(?, 'YYYY-MM-DD HH24:MI:SS.MS')\n" +
                "               < to_timestamp(earthquake.eqtime, 'YYYY-MM-DD HH24:MI:SS.MS')\n" +
                "       );\n";


        try {

            PreparedStatement pstmt = this.connection.prepareStatement(sql);
            String today = null;
            Date date = new Date();
            System.out.println(date);
            SimpleDateFormat sdformat = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");

            Calendar cal = Calendar.getInstance();

            cal.setTime(date);
            cal.add(Calendar.MINUTE, -10);
            today = sdformat.format(cal.getTime());
            pstmt.setString(1,today);
            resultSet = pstmt.executeQuery();

        }catch(SQLException ex){
            SQLExceptionHandler.printSQLException(ex);
        }

        try {
            int i = 0;
            System.out.println("\n--- 지진 반경 5km 내부에 거주 중인 인원의 정보 ---\n");
            while (resultSet.next()){
                ParentInfo parentInfo = new ParentInfo();
                parentInfo.setpNum(resultSet.getString("parentphonenum"));
                parentInfo.setpName(resultSet.getString("parentname"));
                parentInfo.setLat(resultSet.getDouble("latitude"));
                parentInfo.setLon(resultSet.getDouble("longtitude"));
                System.out.println(parentInfo);
                parentInfos.add(parentInfo);
            }

        }catch (SQLException ex){
            SQLExceptionHandler.printSQLException(ex);
        }

        return parentInfos;
    }

    public ArrayList<Shelter> getShelterNearParent(ParentInfo parentInfo){
        ArrayList<Shelter> shelters = new ArrayList<Shelter>();

        System.out.println("---"+ parentInfo.getpName() +" 인근 대피소 목록" + "---");

        String sql = "select *\n" +
                "                      from shelter as S\n" +
                "                      where ST_DistanceSphere( \n" +
                "                               ST_SetSRID(ST_MakePoint(S.lat, S.lon), 4326),\n" +
                "                               ST_SetSRID(ST_MakePoint(?, ?), 4326)\n" +
                "                                   ) < 30000;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDouble(1,parentInfo.getLat());
            preparedStatement.setDouble(2,parentInfo.getLon());
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()){
                Shelter s = new Shelter();
                s.setSidoName(rs.getString("sido_name"));
                s.setSigunguName(rs.getString("sigungu_name"));
                s.setShelNm(rs.getString("shel_nm"));
                s.setAddress(rs.getString("address"));
                s.setLat(rs.getDouble("lat"));
                s.setLon(rs.getDouble("lon"));
                System.out.println(s);
                shelters.add(s);
            }

        }catch(SQLException ex){
            SQLExceptionHandler.printSQLException(ex);
        }

        return shelters;
    }

    public void getUserInfoFrom(ParentInfo parentInfo){
        String sql = "select *\n" +
                "                      from care\n" +
                "                      where parentphonenum = ?;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,parentInfo.getpNum());
            ResultSet rs = preparedStatement.executeQuery();
            System.out.println("--- "+ parentInfo.getpName() +"의 보호자 번호 ---");
            while(rs.next()){
                System.out.println(rs.getString("userphonenum"));
            }
            System.out.println();
        }catch (SQLException ex){
            SQLExceptionHandler.printSQLException(ex);
        }
    }

    public void insertDummyEarthQuake(){
        String today = null;
        Date date = new Date();
        System.out.println(date);
        SimpleDateFormat sdformat = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MINUTE, -9);
        today = sdformat.format(cal.getTime());

        Earthquake eq = DummyGenerator.generateEarthquakes(today);

        String sql = "Insert Into earthquake"+
                " Values(?,?,?,?,?);";
        try {

            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1,eq.getEqId());
            pstmt.setDouble(2,eq.getLat());
            pstmt.setDouble(3,eq.getLon());
            pstmt.setString(4,eq.getEqTime());
            pstmt.setDouble(5,eq.getEqScale());
            pstmt.execute();

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
}
