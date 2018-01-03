/**
 * Получает список robots.txt, инициирует поиск robots.txt данных сайтов,
 * просматривает robots.txt в поисках ссылок на новые страницы
 * @author Anton Lapin, Yury Tweritin
 * @date 29.12.2017
 */
package parser;

import downloader.Downloader;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class ParseRobotsDotTxt extends Thread {

    private TreeMap<String, Integer> pagesList = new TreeMap<>();
    private TreeMap<String, Integer> unchRobotsList;

    /**
     * Конструктор класса
     * @param unchRobotsList
     */

    public ParseRobotsDotTxt(TreeMap<String, Integer> unchRobotsList) {
        this.unchRobotsList = unchRobotsList;
    }

    /**
     * Основной алгоритм работы нити
     */

    public void run(){
        Set<Map.Entry<String, Integer>> set = this.unchRobotsList.entrySet();
        for (Map.Entry<String, Integer> item: set) {
            Integer id = item.getValue();
            String[] url = item.getKey().split(" ");
            this.pagesList.putAll(searchNewPageReferences(url[1], id));
        }
    }

    /**
     * Метод, осуществляющий поиск новых ссылок веб-страниц
     * @param url
     * @param id
     * @return список новых ссылок веб-страниц
     */

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

    /**
     * Метод, осуществляющий получение списка ссылок веб-страниц
     * @return список ссылок веб-страниц
     */

    public TreeMap<String, Integer> getPagesList() {
        return this.pagesList;
    }
}
