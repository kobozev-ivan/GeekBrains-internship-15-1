package dbworker;

import java.sql.*;
import java.util.*;
import java.util.Date;


public class PagesTableReader {

    //По запросу из парсера, выявляет строки таблицы
    //у которых выставлено время последнего сканирования в нуль

    private Statement stmt;
    private Connection connection;

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

    public TreeMap<String, Integer> queryUncheckedPages() throws SQLException {
        TreeMap<String, Integer> unchecked = new TreeMap<>();
        try{
            connect();
            ResultSet rs = stmt.executeQuery("SELECT URL, SITE_ID FROM PAGES" +
                    " WHERE LAST_SCAN IS NULL;");

            while(rs.next()){
                unchecked.put(rs.getString(1), rs.getInt(2));
            }
            Date newScanDate = new Date();
            newScanDate.getTime();
            connection.setAutoCommit(false);
            Set<Map.Entry<String, Integer>> pair = unchecked.entrySet();
            for (Map.Entry<String, Integer> item : pair) {
                stmt.executeUpdate("UPDATE PAGES SET LAST_SCAN = " + newScanDate + " WHERE URL = '" + item.getKey() + "';");
            }
            connection.setAutoCommit(true);
        } catch (Exception e){
            e.printStackTrace();
        }finally {
            disconnect();
        }
        return unchecked;
    }
}
