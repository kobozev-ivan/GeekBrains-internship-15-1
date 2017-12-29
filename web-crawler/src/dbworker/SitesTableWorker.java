package dbworker;

import java.sql.*;
import java.util.ArrayList;

public class SitesTableWorker extends Thread {
    //делает запрос в БД таблицу Sites
    //получает сведения об элементах таблицы,
    //у которых нет соответствий в таблице Pages

    private Statement stmt;
    private Connection connection;
    private ArrayList<String> siteNameslist = new ArrayList<>();

    public void run(){
        try{
            connect();
            querySitesName();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            disconnect();
        }
    }

    private void connect() throws Exception{
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:GEEKBRAINS_INTERNSHIP_15_1_DB.db");
        stmt = connection.createStatement();
    }

    private void disconnect(){
        try {
            connection.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void querySitesName() throws SQLException{
        ResultSet rs = stmt.executeQuery("SELECT NAME FROM SITES\n" +
                "   LEFT JOIN PAGES ON PAGES.SITE_ID = SITES.ID\n" +
                "   WHERE PAGES.ID IS NULL;");

        while(rs.next()){
            System.out.println(rs.getString(1));
            String str = rs.getString(1);
            siteNameslist.add(str);
        }
    }

    public ArrayList<String> getNoReferenceSiteNamesList() throws SQLException {
        return siteNameslist;
    }
}
