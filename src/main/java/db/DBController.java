package db;

import shelter.models.Shelter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
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

    public void createShelterTable() throws SQLException{
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

        System.out.println(sql);
    }

    public void dropShelterTable(){
        try {
            this.makeConnection();
        }catch (SQLException ex){
            SQLExceptionHandler.printSQLException(ex);
        }

        String sql  = "DROP TABLE shelter;";

        try {
            Statement stmt = connection.createStatement();
            stmt.execute(sql);
        }catch (SQLException ex){
            SQLExceptionHandler.printSQLException(ex);
        }

        System.out.println(sql);
    }
}
