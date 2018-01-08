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
//        DBCreator dbc=new DBCreator();
//        dbc.run();
//        SitesTableReader s=new SitesTableReader();
//        s.start();
//        s.getNoReferenceSiteNamesList();
//        long t = System.currentTimeMillis();
        new Collector().start();
//        PagesTableReader p=new PagesTableReader();
//        unchecked=p.getUncheckedPages();
//        Set<Map.Entry<String, Integer>> pair = unchecked.entrySet();
//        for (Map.Entry<String, Integer> item : pair) {
//            System.out.println(item.getKey());
//        }
       
        
//        System.out.println((System.currentTimeMillis() - t) / 1000 + "s");
//        new ParseHTML().start();

    }
}
