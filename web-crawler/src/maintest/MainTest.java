package maintest;

import dbworker.DBCreator;//для теста
import dbworker.PagesTableReader;
import dbworker.SitesTableReader;//для теста
import java.sql.SQLException;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import parser.Collector;
import parser.ParseHTML;



public class MainTest {
    static TreeMap<String, Integer> unchecked;//для теста
    public static void main(String[] args) throws SQLException, Exception {
        DBCreator dbc=new DBCreator();
        dbc.run();
        new Collector().start();
    }
}
