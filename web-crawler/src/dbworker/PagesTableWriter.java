/**
 * получает из SitesTableReader лист названий сайтов добавляет в таблицу PAGES ссылку на robots.txt,
 * принимает от парсера ссылки на HTML страницы, передает их в PAGES как непроверенные
 * @author Anton Lapin, Yury Tweritin
 * @date 29.12.2017
 */
package dbworker;

import java.sql.*;
import java.util.*;
import java.util.Date;

public class PagesTableWriter {

    private Statement stmt;
    private Connection connection;
    private TreeMap<String, Integer> sites = new TreeMap<>();
    private SitesTableReader sitesTableReader;
    private String url;
    private Date date;

    /**
     * Метод, отвечающий за подключение к БД
     * @throws SQLException, ClassNotFoundException
     */

    private void connect() throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        this.connection = DriverManager.getConnection("jdbc:sqlite:GEEKBRAINS_INTERNSHIP_15_1_DB.db");
        this.stmt = this.connection.createStatement();
    }

    /**
     * Метод, отвечающий за отключение от БД
     */

    private void disconnect() {
        try {
            this.connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод, который записывает ссылку на robots.txt веб-сайта в таблицу PAGES
     */

    public void insertIntoPagesTableRobotsTxtFile() {
        try{
            startSitesTableReader();
            connect();
            this.connection.setAutoCommit(false);
            sitesListHandling(true);
            this.connection.setAutoCommit(true);
        } catch (Exception e){
            e.printStackTrace();
        }finally {
            disconnect();
            this.sites.clear();
        }
    }

    /**
     * Метод, запускающий SitesTableReader, получает список названий сайтов, у которых нет robots.txt
     */

    private void startSitesTableReader(){
        this.sitesTableReader = new SitesTableReader();
        try {
            this.sitesTableReader.start();
            this.sitesTableReader.join();
            this.sites = this.sitesTableReader.getNoReferenceSiteNamesList();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод, осуществляющий обработку элементов списка названий сайта, не имеющих robots.txt
     */

    private void sitesListHandling(boolean isRobots) {
        Set<Map.Entry<String, Integer>> names = this.sites.entrySet();
        for (Map.Entry<String, Integer> siteName : names) {
            int id = siteName.getValue();
            this.date = new Date();
            this.date.getTime();
            if(isRobots) {
                this.url = "http://" + siteName.getKey() + "/robots.txt";
            } else {
                this.url = siteName.getKey();
            }
            insertQueryExecutor(this.url, id, date);
        }
    }

    /**
     * Метод, осуществляющий запись в PAGES новых ссылок на веб-страницы, полученных из Collector
     * @param newPagesList
     */

    public void insertIntoPagesTablePagesListFromCollector(TreeMap<String, Integer> newPagesList) {
        this.sites = newPagesList;
        try{
            connect();
            this.connection.setAutoCommit(false);
            sitesListHandling(false);
            this.connection.setAutoCommit(true);
        } catch (Exception e){
            e.printStackTrace();
        }finally {
            disconnect();
            sites.clear();
        }
    }

    /**
     * Метод, осуществляющий непосредственно запрос в БД
     * @param url
     * @param siteId
     * @param found
     */

    private void insertQueryExecutor(String url, int siteId, Date found) {
        try {
            this.stmt.executeUpdate("INSERT INTO PAGES (URL, SITE_ID, FOUND)" +
                    " VALUES ('" + url + "','" + siteId + "','" + found + "')");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
