/**
 * Делает запрос в БД таблицу Sites, получает сведения об элементах таблицы,
 * у которых нет соответствий в таблице PAGES
 * @author Anton Lapin, Yury Tweritin
 * @date 29.12.2017
 */
package dbworker;

import java.sql.*;
import java.util.TreeMap;

public class SitesTableReader extends Thread {

    private Statement stmt;
    private Connection connection;
    private TreeMap<String, Integer> siteNameslist = new TreeMap<>();

    /**
     * Собственно алгоритм работы нити
     */

    public void run(){
        System.out.println("sitesTableReader begin");
        try{
            connect();
            querySitesName();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            disconnect();
        }
        System.out.println("sitesTableReader end");
    }

    /**
     * Метод, отвечающий за подключение к БД
     * @throws SQLException, ClassNotFoundException
     */

    private void connect() throws SQLException, ClassNotFoundException{
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
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * Метод, осуществляющий запрос в БД на получение списка названий сайтов, 
     * не имеющих robots.txt
     * помещает найденные сайты в коллекцию siteNamelist
     * @throws SQLException
     */

    public void querySitesName() throws SQLException{
        ResultSet rs = this.stmt.executeQuery("SELECT * FROM SITES\n" +
                "   LEFT JOIN PAGES ON PAGES.SITE_ID = SITES.ID\n" +
                "   WHERE PAGES.URL IS NULL;");

        while(rs.next()){
            System.out.println(rs.getInt(1) + " " + rs.getString(2));
            Integer id = rs.getInt(1);
            String siteName = rs.getString(2);
            this.siteNameslist.put(siteName, id);
        }
    }

    /**
     * Метод, осуществляющий получение списка названий сайтов
     * @return список названий сайтов
     * @throws SQLException
     */

    public TreeMap<String, Integer> getNoReferenceSiteNamesList() throws SQLException {
        return this.siteNameslist;
    }
}
