package parser;

import downloader.Downloader;

import java.io.*;
import java.net.URL;
import java.sql.SQLException;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.zip.GZIPInputStream;

public class ParseRobotsDotTxt extends Thread{
    //получает список robots.txt
    //инициирует поиск robots.txt данных сайтов
    //просматривает robots.txt в поисках ссылок на новые страницы

    private TreeMap<String, Integer> pagesList = new TreeMap<>();
    private TreeMap<String, Integer> unchRobotsList = new TreeMap<>();

    public ParseRobotsDotTxt(TreeMap<String, Integer> unchRobotsList) {
        this.unchRobotsList = unchRobotsList;
    }

    public void run(){
        System.out.println("parseRobotsDotTxt begin");

        Set<Map.Entry<String, Integer>> set = unchRobotsList.entrySet();
        for (Map.Entry<String, Integer> item: set) {
            Integer id = item.getValue();
            String url = item.getKey();
            pagesList.putAll(searchNewPageReferences(url, id));
        }
        System.out.println("parseRobotsDotTxt end");
    }

    private TreeMap<String, Integer> searchNewPageReferences(String url, int id) {
        TreeMap<String, Integer> newPages = new TreeMap<>();
        String pageContent = new Downloader().download(url);
        String[] splitContent = pageContent.split(" ");

        for(int i = 0; i < splitContent.length; i++){
            if(splitContent[i].equals("Sitemap:") || splitContent[i].equals("sitemap:")){
                newPages.put(splitContent[i + 1], id);
            }
        }

        return newPages;
    }

    public TreeMap<String, Integer> getPagesList() {
        return pagesList;
    }
}
