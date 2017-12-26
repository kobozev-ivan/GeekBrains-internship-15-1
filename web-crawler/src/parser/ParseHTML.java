package parser;

import downloader.Downloader;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class ParseHTML extends Thread {

    private TreeMap<String, Integer> unchHTMLPagesList = new TreeMap<>();

    public ParseHTML(TreeMap<String, Integer> unchHTMLPagesList) {
        this.unchHTMLPagesList = unchHTMLPagesList;
    }

    public void run() {
        Set<Map.Entry<String, Integer>> pages = unchHTMLPagesList.entrySet();
        for (Map.Entry<String, Integer> page : pages) {
            System.out.println(new Downloader().download(page.getKey()));
        }
    }

}
