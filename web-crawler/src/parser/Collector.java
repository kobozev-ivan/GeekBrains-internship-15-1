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
    private TreeMap<String, Integer> unchecked;//коллекция страниц всех сайтов,
    //которые еще не сканировали
    private int count;//количество непросканированных страниц считанных из БД
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
    private PersonPageRankTableWriter pprtw=new PersonPageRankTableWriter();

    /**
     * Основной алгоритм работы
     */

    public void run() {
        int jump=0;
        initUncheckedSitesList();//поиск непроверенных ссылок страниц сайтов из PAGES
        //в процессе запроса ссылок отбираются из БД таблицы PAGES первые 50 
        //непроверенных ссылок, т.к. в дальнейшем обработка этих ссылок очень ресурсоъемка
        System.out.println("Сортируем ссылки");
        sortUncheckedPages();// сортируем ссылки (robots,sitemap,html)
        while(true){
            if(jump!=0){
                initUncheckedSitesList();//поиск непроверенных ссылок страниц сайтов из PAGES
                System.out.println("Сортируем ссылки повторно");
                sortUncheckedPages();// сортируем ссылки (robots,sitemap,html)
            }
            jump=1;
            if(!(this.unchRobotsList.isEmpty())) {//если коллекция( 
                //treemap: ключ=id+url, значение=site_id) не пустая
                initParseRobotsDotTxt();//анализ ссылок на robots.txt(извлечение 
                //ссылок на sitemap'ы и добавление их в коллекцию newPagesList)
                this.unchRobotsList.clear();
            }
            if(!(this.unchSiteMapsList.isEmpty())) { //treemap: ключ=id+url, значение=site_id
                initParseSiteMaps();
                this.unchSiteMapsList.clear();
            }    
            if(!(this.unchHTMLPagesList.isEmpty())) { //treemap: ключ=id+url, значение=site_id
                
                initParseHTML();
                System.out.println("Парсинг html завершился");
                this.unchHTMLPagesList.clear();
            }
            System.out.println("Вставка всех найденных коллектором ссылок в БД:Начато");
            this.ptw.insertIntoPagesTablePagesListFromCollector(this.newPagesList);//коллекция: ключ-url страницы,значение-site_id
            System.out.println("Вставка всех найденных коллектором ссылок в БД:Закончено");
            this.newPagesList.clear();
        }
    }

    /**
     * Метод, выполняющий с помощью других методов и классов,поиск ссылок 
     * непроверенных веб-страниц (для всех сайтов) 
     */

    private void initUncheckedSitesList() {
        this.ptw = new PagesTableWriter();
        this.ptw.insertIntoPagesTableRobotsTxtFile();//формируем из новых сайтов
        //ссылки на их robots.txt и кладем их в БД (PAGES)
        this.ptr = new PagesTableReader();
        try {
            System.out.println("Считывание из БД всех непроверенных url:Начало");
            this.unchecked = this.ptr.getUncheckedPages();//считываем из PAGES 
            this.count=this.unchecked.size();//количество загруженных страниц из PAGES
            Set<Map.Entry<String, Integer>> urls = this.unchecked.entrySet();
            //все непросканированные ссылки  (treemap: ключ=id+url, значение=site_id)
            System.out.println("Считывание из БД всех непроверенных url:Конец");
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
                } else if (item.getKey().contains("sitemap")||item.getKey().contains("Sitemap")) {
                    //кладем в коллекцию ссылок на sitemap
                    this.unchSiteMapsList.put(item.getKey(), item.getValue());
                } else {
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
        this.newPagesList.putAll(this.rbts.getPagesList());//коллекция: ключ-url страницы,значение-site_id
    }

    /**
     * Аналогично с предыдущим методом
     */

    private void initParseSiteMaps() {
        this.smps = new ParseSiteMaps(this.unchSiteMapsList);//(treemap: ключ=id+url, значение=site_id)
        this.smps.start();
        try {
            this.smps.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.newPagesList.putAll(this.smps.getPagesList());//коллекция: ключ-url страницы,значение-site_id
    }

    /**
     * Метод, в результате отработки которого получаем лист популярности личностей.
     */

    private void initParseHTML() {
        this.phtml = new ParseHTML(this.unchHTMLPagesList);//treemap: ключ=id+url, значение=site_id
        this.phtml.start();
        try {
            this.phtml.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.newPersonPageRankList = this.phtml.getPersonsPageRank(); //treemap: ключ=personId+pageId, значение=rank
        //запись статистики в БД        
        this.pprtw.insertIntoPPRTablePPRListFromCollector(newPersonPageRankList);
    }

}
