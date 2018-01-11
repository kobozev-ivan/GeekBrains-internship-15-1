/**
 * Осуществляет чтение таблицы PAGES и выявление тех элементов,
 * у которых отсутствует значение времени последнего сканирования
 * @author Anton Lapin, Yury Tweritin
 * @date 29.12.2017
 */
package dbworker;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;


public class PagesTableReader {

    private Statement stmt;
    private Connection connection;
    private TreeMap<String, Integer> unchecked;
    private ResultSet rs;
    Date newScanDate;
    //форматирование даты для совместимости с SQLite    
//    SimpleDateFormat format1 = new SimpleDateFormat("YYYY-MM-DD HH:MM:SS.SSS");

    /**
     * Метод, отвечающий за подключение к БД
     * @throws SQLException, ClassNotFoundException
     */

    private void connect() throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        this.connection = DriverManager.getConnection("jdbc:sqlite:GEEKBRAINS_INTERNSHIP_15_1_DB.db");
        this.stmt = connection.createStatement();
    }

    /**
     * Метод, отвечающий за отключение от БД
     */

    private void disconnect(){
        try {
            this.connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод, осуществляющий запрос на поиск и получение ссылок веб-страниц 
     * всех сайтов, у которых отсутствует время последнего сканирования
     * Во время запроса добавляет время последнего сканирования страниц(для 
     * каждой страницы)
     * @return неотсканированные ссылки веб-страниц
     * @throws Exception
     * возвращает коллекцию ссылок на страницы всех сайтов, которые еще
     * не сканировали 
     */

    public TreeMap<String, Integer> getUncheckedPages() throws Exception {
        System.out.println("Чтение из PAGES непроверенных url");
        long t = System.currentTimeMillis();
        this.unchecked = new TreeMap<>();
        connect();
        this.connection.setAutoCommit(false);
        this.rs = this.stmt.executeQuery("SELECT URL, ID FROM PAGES" +
                " WHERE LAST_SCAN IS NULL LIMIT 50;");
        
        while(this.rs.next()){
            this.unchecked.put(this.rs.getString(1), this.rs.getInt(2));//кладем в коллекцию
        }
        this.connection.setAutoCommit(true);
        System.out.println((System.currentTimeMillis() - t) / 1000 + "s" + (System.currentTimeMillis() - t) % 1000 + "ms");
        /*
        *обновление даты работает очень медленно
        */
        setLastScanDateForEachItem();//добавление времени последнего сканирования
//        this.connection.setAutoCommit(true);
        disconnect();
        return this.unchecked;
    }

    /**
     * Метод, осуществляющий простановку у каждого выявленного элемента дату
     * последнего сканирования
     * @throws SQLException
     */

    private void setLastScanDateForEachItem() throws SQLException {
        this.newScanDate = new Date();
        this.newScanDate.getTime();
//        System.out.println(this.newScanDate);
//        System.out.println(format1.format(this.newScanDate));
        this.connection.setAutoCommit(false);
        Set<Map.Entry<String, Integer>> pair = this.unchecked.entrySet();
        for (Map.Entry<String, Integer> item : pair) {
            this.stmt.executeUpdate("UPDATE PAGES SET LAST_SCAN = '" +
                    this.newScanDate + "' WHERE URL = '" + item.getKey() + "';");
        }
        this.connection.setAutoCommit(true);
    }
}
