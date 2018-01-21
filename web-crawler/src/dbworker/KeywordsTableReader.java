/**
 * Предназначен для чтения таблицы Keywords
 * @author Anton Lapin, Yury Tweritin
 * @date 31.12.2017
 */
package dbworker;

import java.sql.*;
import java.util.TreeMap;

public class KeywordsTableReader extends Thread {

    private Statement stmt;
    private Connection connection;
    private TreeMap<String, Integer> keywordsList;
    private ResultSet rs;

    /**
     * Основной алгоритм работы нити
     */

    public void run() {
        try {
            connect();
            initKeywordsList();
            disconnect();
        } catch (Exception e){
            e.printStackTrace();
            System.out.println("Ошибка загрузки из Keywords");
        }


    }

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
     * Метод, осуществляющий запрос из БД ключевых слов по которым ведется
     * парсинг html страниц
     * @throws Exception
     */

    public void initKeywordsList() throws Exception {
        this.keywordsList = new TreeMap<>();
        this.rs = this.stmt.executeQuery("SELECT NAME, PERSON_ID FROM KEYWORDS;");
        while(this.rs.next()){
            this.keywordsList.put(this.rs.getString(1), this.rs.getInt(2));//treemap: ключ=name keyword, значение=person_id
        }
    }
    /*
    *Метод возвращающий коллекцию ключевых слов
    */
    public TreeMap<String, Integer> getKeywordsList(){
        return this.keywordsList;
    }
}
