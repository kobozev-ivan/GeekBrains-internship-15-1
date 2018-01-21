/**
 * получает из SitesTableReader лист названий сайтов, которые еще не сканировались
 * формирует и добавляет  в таблицу PAGES ссылки на robots.txt,передает их в 
 * PAGES как непроверенные
 * или добавляет ссылки на HTML страницы(не robots.txt), передает 
 * их в PAGES как непроверенные
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
     * Метод, который записывает ссылки на robots.txt непроверенных веб-сайтов 
     * в таблицу PAGES
     */

    public void insertIntoPagesTableRobotsTxtFile() {
        try{
            startSitesTableReader();//получили из БД коллекцию сайтов, без robots.txt
            connect();
            this.connection.setAutoCommit(false);//отключаем автоматическое 
            //управление транзакциями при работе с БД(для ускорения серии запросов)
            sitesListHandling(true);//формируем список ссылок на robots.txt и 
            //передаем его в БД (в PAGES), привязывая по времени
            this.connection.setAutoCommit(true);
        } catch (Exception e){
            e.printStackTrace();
        }finally {
            disconnect();
            this.sites.clear();
        }
    }

    /**
     * Метод, запускающий SitesTableReader, 
     * возвращает список названий сайтов, у которых нет robots.txt (которые не
     * сканировались на наличие robots.txt)
     */

    private void startSitesTableReader(){
        this.sitesTableReader = new SitesTableReader();
        try {
            this.sitesTableReader.start();//запрос из БД списка сайтов, 
            //которых еще не сканировали на наличие robots.txt (т.е. новые сайты) 
            this.sitesTableReader.join();
            //возвращение колллекции сайтов не сканированных на наличие robots.txt
            this.sites = this.sitesTableReader.getNoReferenceSiteNamesList();//получение
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод, осуществляющий обработку элементов списка названий сайта, не имеющих robots.txt
     * В процессе обхода формирует ссылки на robots.txt и кладет их 
     * в БД, привязывая к временной отметке.
     */

    private void sitesListHandling(boolean isRobots) {
        Set<Map.Entry<String, Integer>> names = this.sites.entrySet();/*
        /*чтобы пробежаться по всем парам ключ-сайт используя метод 
         *entrySet(возвращает множество пар) преобразуем в коллекцию Set 
         *(инициализируем её в переменной names) и пробегаемся по коллекции
         */
        for (Map.Entry<String, Integer> siteName : names) {//коллекция: ключ-url страницы,значение-site_id
            int siteId = siteName.getValue();
            this.date = new Date();//инициализируем  переменную для хранения даты 
            //обрабатываемого сайта без robots.txt
            this.date.getTime();//присваиваем текущую дату
            if(isRobots) {
                //формируем ссылку на файл robots.txt
                this.url = "http://" + siteName.getKey() + "/robots.txt";
            } else {
                this.url = siteName.getKey();// если формируем ссылку не на robots.txt
            }
            insertQueryExecutor(this.url, siteId, this.date); //запрос на добавление данных в БД
        }
    }

    /**
     * Метод, осуществляющий запись в PAGES новых ссылок на веб-страницы(не robots.txt), полученных из Collector
     * @param newPagesList
     */

    public void insertIntoPagesTablePagesListFromCollector(TreeMap<String, Integer> newPagesList) {
        this.sites = newPagesList;//коллекция: ключ-url страницы,значение-site_id
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
     * Метод, осуществляющий непосредственно запрос на запись в БД
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
