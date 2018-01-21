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
     * Так как обработка данных ссылок в последующем очень ресурсоемка, поэтому
     * за один раз отбираются первые 50 ссылок.
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
        this.rs = this.stmt.executeQuery("SELECT ID, URL, SITE_ID FROM PAGES" +
                " WHERE LAST_SCAN IS NULL LIMIT 50;");
        
        while(this.rs.next()){
            System.out.println(this.rs.getInt(1)+" "+this.rs.getString(2)+" "+this.rs.getInt(3));
            this.unchecked.put(this.rs.getInt(1)+" "+this.rs.getString(2), this.rs.getInt(3));//считываем в коллекцию
                            //treemap: ключ=id+url, значение=site_id
        }
        this.connection.setAutoCommit(true);
        System.out.println((System.currentTimeMillis() - t) / 1000 + "s" + (System.currentTimeMillis() - t) % 1000 + "ms");
        /*
        *обновление даты работает очень медленно
        */
        setLastScanDateForEachItem();//добавление времени последнего сканирования
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
        this.connection.setAutoCommit(false);
        Set<Map.Entry<String, Integer>> pair = this.unchecked.entrySet();  //treemap: ключ=id+url, значение=site_id      
        for (Map.Entry<String, Integer> item : pair) {
            String[] split=item.getKey().split(" ");
            String urlPage=split[1];
            this.stmt.executeUpdate("UPDATE PAGES SET LAST_SCAN = '" +
                    this.newScanDate + "' WHERE URL = '" +urlPage+ "';");
        }
        this.connection.setAutoCommit(true);
    }
}
