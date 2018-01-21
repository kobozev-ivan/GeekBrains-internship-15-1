/**
 * Создает базу данных SQLite3 для тестирования элементов веб-краулера
 * @author Anton Lapin
 * @date 29.12.2017
 */
package dbworker;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBCreator {

    private Connection connection;
    private Statement stmt;
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    /**
     * Точка входа
     * @param args
     */

    public static void main(String[] args) {
        new DBCreator().run();
    }

    /**
     * Основной алгоритм работы
     */

    public void run(){
        try{
            connect();
            dropTables();
            createTables();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            disconnect();
        }
    }

    /**
     * Метод, отвечающий за подключение к БД
     * @throws Exception
     */

    private void connect() throws Exception{
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:GEEKBRAINS_INTERNSHIP_15_1_DB.db");
        stmt = connection.createStatement();
    }

    /**
     * Метод, отвечающий за отключение от БД
     */

    private void disconnect(){
        try {
            connection.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * Метод - SQL-запрос на создание БД
     * @throws SQLException
     */

    private void createTables() throws SQLException{
        stmt.executeUpdate("CREATE TABLE PERSONS(\n" +
                "   ID INT PRIMARY KEY UNIQUE,\n" +
                "   NAME NVARCHAR(2048) NOT NULL\n" +
                ");\n\n" +

                "CREATE TABLE SITES(\n" +
                "   ID INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "   NAME NVARCHAR(256) NOT NULL\n" +
                "   );\n\n" +

                "CREATE TABLE KEYWORDS(\n" +
                "   ID INT PRIMARY KEY,\n" +
                "   NAME NVARCHAR(2048) NOT NULL,\n" +
                "   PERSON_ID INT,\n" +
                "   CONSTRAINT fk_persons FOREIGN KEY (PERSON_ID) REFERENCES PERSONS(ID)\n" +
                ");\n\n" +

                "CREATE TABLE PAGES(\n" +
                "   ID INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "   URL NVARCHAR(2048) NOT NULL,\n" +
                "   SITE_ID INT,\n" +
                "   FOUND datetime,\n" +
                "   LAST_SCAN datetime,\n" +
                "   CONSTRAINT fk_sites FOREIGN KEY (SITE_ID) REFERENCES SITES(ID)\n" +
                ");\n\n" +

                "CREATE TABLE PERSON_PAGE_RANK(\n" +
                "   PERSON_ID INT,\n" +
                "   PAGE_ID INT,\n" +
                "   RANK SMALLINT NOT NULL,\n" +
                "   CONSTRAINT fk_pages_rank FOREIGN KEY (PAGE_ID) REFERENCES PAGES(ID),\n" +
                "   CONSTRAINT fk_persons_rank FOREIGN KEY (PERSON_ID) REFERENCES PERSONS(ID)\n" +
                ");\n"+
                //для тестирования заполняю PERSONS и KEYWORDS
                "INSERT INTO PERSONS (ID,NAME) VALUES ('1','Путин');\n"+
                "INSERT INTO KEYWORDS (ID,NAME,PERSON_ID) VALUES ('1','Путин','1');\n"+
                "INSERT INTO KEYWORDS (ID,NAME,PERSON_ID) VALUES ('2','Путину','1');\n"+
                "INSERT INTO KEYWORDS (ID,NAME,PERSON_ID) VALUES ('3','Путина','1');\n"+
                //для тестирования заполняю сразу несколько сайтов               
                "INSERT INTO SITES (NAME) VALUES ('tass.ru');\n"+
                "INSERT INTO SITES (NAME) VALUES ('udmapk.ru');\n"+
                "INSERT INTO SITES (NAME) VALUES ('aif.ru');\n");
    }

    /**
     * Метод по очистке БД от старых таблиц
     * @throws SQLException
     */

    private void dropTables() throws SQLException {
        stmt.executeUpdate("DROP TABLE IF EXISTS PERSONS;\n" +
                "DROP TABLE IF EXISTS SITES;\n" +
                "DROP TABLE IF EXISTS KEYWORDS;\n" +
                "DROP TABLE IF EXISTS PAGES;\n" +
                "DROP TABLE IF EXISTS PERSON_PAGE_RANK;\n");
    }

}
