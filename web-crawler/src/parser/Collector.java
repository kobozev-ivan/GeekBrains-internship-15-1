/**
 * Осуществляет узловые функции:
 * - запускает автоматический запрос в БД
 * - извлекает список непроверенных ссылок на веб-страницы
 * - сортирует список по типу ссылок: robots.txt, sitemap, HTML
 * - отправляет каждый отсортированный список в соответствующий объект для обхода
 * - получает из этих объектов новые списки ссылок, объединяет их в один
 * - передает получившийся список в объект, осуществляющий запись ссылок в таблицу PAGES
 * @author Anton Lapin, Yury Tweritin
 * @date 29.12.2017
 */
package parser;

import dbworker.PagesTableReader;
import dbworker.PagesTableWriter;
import dbworker.PersonPageRankTableWriter;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class Collector extends Thread {
    private TreeMap<String, Integer> unchecked;
    private TreeMap<String, Integer> unchRobotsList = new TreeMap<>();
    private TreeMap<String, Integer> unchSiteMapsList = new TreeMap<>();
    private TreeMap<String, Integer> unchHTMLPagesList = new TreeMap<>();
    private TreeMap<String, Integer> newPagesList = new TreeMap<>();
    private TreeMap<String, Integer> newPersonPageRankList = new TreeMap<>();
    private PagesTableWriter ptw;
    private PagesTableReader ptr;
    private ParseRobotsDotTxt rbts;
    private ParseSiteMaps smps;
    private ParseHTML phtml;
    private PersonPageRankTableWriter pprtw;

    /**
     * Основной алгоритм работы
     */

    public void run() {
        initUncheckedSitesList();
        sortUncheckedPages();
        if(!(this.unchRobotsList.isEmpty())) {
            initParseRobotsDotTxt();
        }
        if(!(this.unchSiteMapsList.isEmpty())) {
            initParseSiteMaps();
        }
        if(!(this.unchHTMLPagesList.isEmpty())) {
            initParseHTML();
        }
        this.ptw.insertIntoPagesTablePagesListFromCollector(this.newPagesList);
        if(!this.newPersonPageRankList.isEmpty()) {
            this.pprtw.insertIntoPPRTablePPRListFromCollector(this.newPersonPageRankList);
        }
    }

    /**
     * Метод, инициализирующий поиск ссылок непроверенных веб-страниц
     */

    private void initUncheckedSitesList() {
        this.ptw = new PagesTableWriter();
        this.ptw.insertIntoPagesTableRobotsTxtFile();
        this.ptr = new PagesTableReader();
        try {
            this.unchecked = this.ptr.getUncheckedPages();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод, осуществляющий сортировку общего списка ссылок непроверенных веб-страниц
     */

    private void sortUncheckedPages(){
        Set<Map.Entry<String, Integer>> pair = this.unchecked.entrySet();
        for (Map.Entry<String, Integer> item : pair) {
            if (item.getKey().contains("http")) {
                if (item.getKey().contains("robots.txt")) {
                    this.unchRobotsList.put(item.getKey(), item.getValue());
                } else if (item.getKey().contains("sitemap")) {
                    this.unchSiteMapsList.put(item.getKey(), item.getValue());
                } else if(item.getKey().contains(".html")){
                    this.unchHTMLPagesList.put(item.getKey(), item.getValue());
                }
            }
        }
    }

    /**
     * Метод, добавляющий в новый список ссылок веб-страниц элементы, полученные в результате обхода.
     */

    private void initParseRobotsDotTxt() {
        this.rbts = new ParseRobotsDotTxt(this.unchRobotsList);
        this.rbts.start();
        try {
            this.rbts.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.newPagesList.putAll(this.rbts.getPagesList());
    }

    /**
     * Аналогично с предыдущим методом
     */

    private void initParseSiteMaps() {
        this.smps = new ParseSiteMaps(this.unchSiteMapsList);
        this.smps.start();
        try {
            this.smps.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.newPagesList.putAll(this.smps.getPagesList());
    }

    /**
     * Метод, в результате отработки которого получаем лист популярности личностей.
     */

    private void initParseHTML() {
        this.phtml = new ParseHTML(this.unchHTMLPagesList);
        this.phtml.start();
        try {
            this.phtml.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.newPersonPageRankList = this.phtml.getPersonsPageRank();
    }

}