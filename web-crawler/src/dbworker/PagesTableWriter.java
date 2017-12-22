package dbworker;

import java.sql.*;
import java.util.*;
import java.util.Date;

public class PagesTableWriter {
    //получает из SitesTableReader лист названий сайтов
    //добавляет в таблицу Pages ссылку на robots.txt
    //принимает от парсера ссылки на HTML страницы
    //передает их в Pages

    private Statement stmt;
    private Connection connection;
    private TreeMap<Integer, String> sites = new TreeMap<>();

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

    public void insertIntoPagesTableRobotsTxtFile(){
        try{
            connect();
            SitesTableReader s = new SitesTableReader();
            try {
                s.start();
                s.join();
                sites = s.getNoReferenceSiteNamesList();
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                connection.setAutoCommit(false);
            } catch (SQLException e){
                e.printStackTrace();
            }
            Set<Map.Entry<Integer, String>> names = sites.entrySet();
            for (Map.Entry<Integer, String> sitename : names) {
                System.out.println(sitename.getKey() + " " + sitename.getValue());
                String robots = "http://" + sitename.getValue() + "/robots.txt";
                int id = sitename.getKey();
                Date date = new Date();
                date.getTime();
                try {
                    stmt.executeUpdate("INSERT INTO PAGES (URL, SITE_ID, FOUND)" +
                            " VALUES ('" + robots + "','" + id + "','" + date + "')");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e){
                e.printStackTrace();
            }

        } catch (Exception e){
            e.printStackTrace();
        }finally {
            disconnect();
        }
    }

    public void insertIntoPagesTablePagesListFromCollector(TreeMap<String, Integer> newPagesList) {
        try{
            connect();
            try {
                connection.setAutoCommit(false);
            } catch (SQLException e){
                e.printStackTrace();
            }
            Set<Map.Entry<String, Integer>> pair = newPagesList.entrySet();
            for (Map.Entry<String, Integer> item : pair) {
                String url = item.getKey();
                int siteId = item.getValue();
                Date date = new Date();
                date.getTime();
                try {
                    stmt.executeUpdate("INSERT INTO PAGES (URL, SITE_ID, FOUND)" +
                            " VALUES ('" + url + "','" + siteId + "','" + date + "')");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e){
                e.printStackTrace();
            }

        } catch (Exception e){
            e.printStackTrace();
        }finally {
            disconnect();
        }
    }
}
