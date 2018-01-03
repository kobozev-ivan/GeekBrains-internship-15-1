/**
 * Записывает результаты обхода html страниц в таблицу PERSON_PAGE_RANK
 * @author Anton Lapin, Yury Tweritin
 * @date 3.01.2018
 */
package dbworker;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class PersonPageRankTableWriter {
    private Statement stmt;
    private Connection connection;

    /**
     * Метод, отвечающий за подключение к БД
     * @throws SQLException, ClassNotFoundException
     */

    private void connect() throws Exception {
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
     * Метод очищает таблицу от элементов.
     * @throws SQLException
     */

    private void dropTable() throws SQLException {
        stmt.executeUpdate("DROP TABLE IF EXISTS PERSON_PAGE_RANK;");
        stmt.executeUpdate("CREATE TABLE PERSON_PAGE_RANK(\n" +
                "   PERSON_ID INT,\n" +
                "   PAGE_ID INT,\n" +
                "   RANK SMALLINT NOT NULL,\n" +
                "   CONSTRAINT fk_pages_rank FOREIGN KEY (PAGE_ID) REFERENCES PAGES(ID),\n" +
                "   CONSTRAINT fk_persons_rank FOREIGN KEY (PERSON_ID) REFERENCES PERSONS(ID)\n" +
                ");\n");
    }

    /**
     * Метод, осуществляющий запись списка личностей, ранжированных по html страницам.
     * @param pprList
     */

    public void insertIntoPPRTablePPRListFromCollector(TreeMap<String, Integer> pprList) {
        try {
            connect();
            dropTable();
            connection.setAutoCommit(false);
            //TODO:сделать перебор списков менее громоздким
            Set<Map.Entry<String, Integer>> ranks = pprList.entrySet();
            for (Map.Entry<String, Integer> item : ranks) {
                String[] splitKey = item.getKey().split(" ");
                int personId = Integer.valueOf(splitKey[0]);
                int pageId = Integer.valueOf(splitKey[1]);
                int rank = item.getValue();
                insertQueryExecutor(personId, pageId, rank);
            }
            connection.setAutoCommit(true);
            disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод, непосредственно выполняющий запрос в базу данных на добавление новых данных
     * @param personId
     * @param pageId
     * @param rank
     */

    private void insertQueryExecutor(int personId, int pageId, int rank) {
        try {
            this.stmt.executeUpdate("INSERT INTO PERSON_PAGE_RANK (PERSON_ID, PAGE_ID, RANK)" +
                    " VALUES ('" + personId + "','" + pageId + "','" + rank + "')");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
