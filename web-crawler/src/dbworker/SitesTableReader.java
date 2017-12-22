package dbworker;

import java.sql.*;
import java.util.TreeMap;

public class SitesTableReader extends Thread {
    //делает запрос в БД таблицу Sites
    //получает сведения об элементах таблицы,
    //у которых нет соответствий в таблице Pages

    private Statement stmt;
    private Connection connection;
    private TreeMap<Integer, String> siteNameslist = new TreeMap<>();


    public void run(){
        System.out.println("sitesTableWorker begin");
        try{
            connect();
            querySitesName();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            disconnect();
        }
        System.out.println("sitesTableWorker end");
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
        ResultSet rs = stmt.executeQuery("SELECT * FROM SITES\n" +
                "   LEFT JOIN PAGES ON PAGES.SITE_ID = SITES.ID\n" +
                "   WHERE PAGES.URL IS NULL;");

        while(rs.next()){
            System.out.println(rs.getInt(1) + " " + rs.getString(2));
            Integer id = rs.getInt(1);
            String siteName = rs.getString(2);
            siteNameslist.put(id, siteName);
        }
    }

    public TreeMap<Integer, String> getNoReferenceSiteNamesList() throws SQLException {
        return siteNameslist;
    }
}
