package parser;

import dbworker.PagesTableReader;
import dbworker.PagesTableWriter;

import java.sql.SQLException;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class Collector extends Thread {
    private TreeMap<String, Integer> unchecked = new TreeMap<>();
    private TreeMap<String, Integer> unchRobotsList = new TreeMap<>();
    private TreeMap<String, Integer> unchSiteMapsList = new TreeMap<>();
    private TreeMap<String, Integer> unchHTMLPagesList = new TreeMap<>();
    private TreeMap<String, Integer> newPagesList = new TreeMap<>();

    public void run() {
        PagesTableWriter ptw = new PagesTableWriter();
        ptw.insertIntoPagesTableRobotsTxtFile();
        PagesTableReader ptr = new PagesTableReader();

        try {
            unchecked = ptr.queryUncheckedPages();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        sortUncheckedPages();

        if(!(unchRobotsList.isEmpty())) {
            ParseRobotsDotTxt rbts = new ParseRobotsDotTxt(unchRobotsList);
            rbts.start();
            try {
                rbts.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            newPagesList.putAll(rbts.getPagesList());
        }

        if(!(unchSiteMapsList.isEmpty())) {
            ParseSiteMaps smps = new ParseSiteMaps(unchSiteMapsList);
            smps.start();
            try {
                smps.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            newPagesList.putAll(smps.getPagesList());
        }

        ptw.insertIntoPagesTablePagesListFromCollector(newPagesList);

    }

    private void sortUncheckedPages(){
        Set<Map.Entry<String, Integer>> pair = unchecked.entrySet();
        for (Map.Entry<String, Integer> item : pair) {
            if (item.getKey().contains("http")) {
                if (item.getKey().contains("robots.txt")) {
                    unchRobotsList.put(item.getKey(), item.getValue());
                } else if (item.getKey().contains("sitemap")) {
                    unchSiteMapsList.put(item.getKey(), item.getValue());
                } else {
                    unchHTMLPagesList.put(item.getKey(), item.getValue());
                }
            }
        }
    }


}
