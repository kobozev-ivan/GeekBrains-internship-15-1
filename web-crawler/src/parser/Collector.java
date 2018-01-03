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

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class Collector extends Thread {
    private TreeMap<String, Integer> unchecked;//коллекция страниц всех сайтов,
    //которые еще не сканировали
    private TreeMap<String, Integer> unchRobotsList = new TreeMap<>();
    private TreeMap<String, Integer> unchSiteMapsList = new TreeMap<>();
    private TreeMap<String, Integer> unchHTMLPagesList = new TreeMap<>();
    private TreeMap<String, Integer> newPagesList = new TreeMap<>();
    private PagesTableWriter ptw;
    private PagesTableReader ptr;
    private ParseRobotsDotTxt rbts;
    private ParseSiteMaps smps;
    private ParseHTML phtml;

    /**
     * Основной алгоритм работы
     */

    public void run() {
        initUncheckedSitesList();//поиск непроверенных ссылок страниц сайтов
        sortUncheckedPages();// сортируем ссылки (robots,sitemap,html)
        if(!(this.unchRobotsList.isEmpty())) {//если коллекция не пустая
            initParseRobotsDotTxt();//анализ ссылок на robots.txt(чтение 
            //и поиск ссылки на sitemap'ы)
        }
        if(!(this.unchSiteMapsList.isEmpty())) {
            initParseSiteMaps();
        }
        if(!(this.unchHTMLPagesList.isEmpty())) {
            initParseHTML();
        }
        this.ptw.insertIntoPagesTablePagesListFromCollector(this.newPagesList);
    }

    /**
     * Метод, выполняющий с помощью других методов и классов,поиск ссылок 
     * непроверенных веб-страниц (для всех сайтов) 
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
                    //кладем в коллекцию ссылок на robots.txt
                    this.unchRobotsList.put(item.getKey(), item.getValue());
                } else if (item.getKey().contains("sitemap")) {
                    //кладем в коллекцию ссылок на sitemap
                    this.unchSiteMapsList.put(item.getKey(), item.getValue());
                } else if(item.getKey().contains(".html")){
                    //кладем в коллекцию ссылок обычных .html
                    this.unchHTMLPagesList.put(item.getKey(), item.getValue());
                }
            }
        }
    }

    /**
     * Метод, добавляющий в новый список ссылок веб-страниц элементы, 
     * полученные в результате обхода.
     */

    private void initParseRobotsDotTxt() {
        this.rbts = new ParseRobotsDotTxt(this.unchRobotsList);
        this.rbts.start();
        try {
            this.rbts.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //помещаем коллекцию sitemap'ов в newPageList
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
        this.newPagesList.putAll(this.smps.getPagesList());//почему добавляем в
        //ту же коллекцию??????????
    }

    /**
     * В разработке...
     */

    private void initParseHTML() {
        this.phtml = new ParseHTML(this.unchHTMLPagesList);
    }
}
