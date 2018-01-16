/**
 * Производит обход html-страниц, подсчитывает совпадения с ключевыми словами
 * @author Anton Lapin, Yury Tweritin
 * @date 29.12.2017
 */
package parser;

import dbworker.KeywordsTableReader;
import downloader.Downloader;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class ParseHTML extends Thread {

    private TreeMap<String, Integer> unchHTMLPagesList;
    private String content;
    private TreeMap<String, Integer> keywordsList;
    private TreeMap<String, Integer> personsPageRank=new TreeMap();
    private KeywordsTableReader ktr;

    /**
     * Конструктор класса
     * @param unchHTMLPagesList
     */

    public ParseHTML(TreeMap<String, Integer> unchHTMLPagesList) {
        this.unchHTMLPagesList = unchHTMLPagesList;
    }

    /**
     * Основной алгоритм класса
     */

    public void run() {
        createKeywordsList();
        rankScore();
    }

    /**
     * Метод для подсчета упоминаний личностей по ключевым словам на html страницах
     */

    private void rankScore() {
        //TODO: упростить подсчет рейтинга, разбить метод на меньшие
       
        //коллекция url и их id
        Set<Map.Entry<String, Integer>> urls = this.unchHTMLPagesList.entrySet();
        System.out.println("Подсчет рейтинга начат");
        for (Map.Entry<String, Integer> url: urls) {
//            String[] splittedKey = url.getKey().line(" ");//делим ссылку на куски
//            Integer pageId = Integer.parseInt(splittedKey[0]);//берем первый кусок это номер????
//            String pageName = splittedKey[1];//второй кусок это имя????

            //коллекция ключевых слов и person_id
            Set<Map.Entry<String, Integer>> keywords = this.keywordsList.entrySet();
            int rank = 0;
            for (Map.Entry<String, Integer> word: keywords) {
                Integer personId = word.getValue();
                String keyword = word.getKey();
                String[] splitContent = getContentFromHTML(url.getKey()).split("\n");
                for (String line:splitContent) {
                    String[] splitLine=line.split(" ");
                    for(String piece :splitLine){
                        if(piece.equals(keyword)) rank++;                   
                    }
                }
//                System.out.println(personId+" "+url.getValue()+" "+rank);
                
                this.personsPageRank.put(personId + " " + url.getValue(), rank);                
            }
        }
        System.out.println("Подсчет рейтинга закончен");
    }

    /**
     * Метод для получения содержания html страницы
     * @param url
     * @return
     */

    private String getContentFromHTML(String url) {
        this.content = new Downloader().download(url);
//        System.out.println(this.content);
        return content;
    }

    /**
     * Метод для формирования спика ключевых слов
     */

    private void createKeywordsList() {
        this.ktr = new KeywordsTableReader();
        this.ktr.start();
        try {
            this.ktr.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            //получаем ключевые слова и PERSON_ID 
            this.keywordsList = this.ktr.getKeywordsList();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Возврат списка популярности личностей по html страницам
     * @return
     */
    //получаем коллекцию составных ключей personID+IDPAGE и рангов
    public TreeMap<String, Integer> getPersonsPageRank() {
        return this.personsPageRank;
    }
}
