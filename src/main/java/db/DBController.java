package db;

import shelter.models.ParentInfo;
import shelter.models.Shelter;
import shelter.models.UserInfo;

import java.sql.*;
import java.util.List;

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
    }

    public void dropTable(){
        dropUserInfoTable();
        dropParentInfoTable();
    }

    public void createUserInfoTable(){
        try{
            this.makeConnection();
            Statement stmt = this.connection.createStatement();
            String sql = "Create Table UserInfo(userPhoneNum varchar(11), parentPhoneNum varchar(11)," +
                    " userName varchar(20), parentName varchar(20), primary key(userPhoneNum,parentPhoneNum))";
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
                    "latitude float, longtitude float, primary key(parentPhoneNum))";
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


    public void insertParentInfo(ParentInfo info){
        String sqlP = "Insert Into ParentInfo(parentPhoneNum,parentName,latitude,longtitude)" +
                " Values(?,?,?,?);";

        try{
            this.makeConnection();
            PreparedStatement pre_stmt = this.connection.prepareStatement(sqlP);
            pre_stmt.setString(1,info.getpNum());
            pre_stmt.setString(2, info.getpName());
            pre_stmt.setFloat(3, info.getLat()); // 형변환
            pre_stmt.setFloat(4, info.getLon());
            pre_stmt.execute();
        }catch (SQLException ex){
            SQLExceptionHandler.printSQLException(ex);
        }

    }

    public void insertUserInfo(UserInfo userInfo){
        String sqlR = "Insert Into UserInfo(userPhoneNum,parentPhoneNum,userName,parentName)" +
                " Values(?,?,?,?);";
        try{
            this.makeConnection();
            PreparedStatement pre_stmt = this.connection.prepareStatement(sqlR);
            pre_stmt = this.connection.prepareStatement(sqlR);
            pre_stmt.setString(1, userInfo.getuNum());
            pre_stmt.setString(2, userInfo.getpNum());
            pre_stmt.setString(3, userInfo.getuName());
            pre_stmt.setString(4, userInfo.getpName());
            pre_stmt.execute();

        }catch (SQLException ex){
            SQLExceptionHandler.printSQLException(ex);
        }

    }


}
