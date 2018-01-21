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
        this.unchHTMLPagesList = unchHTMLPagesList;//treemap: ключ=id+url, значение=site_id
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
        Set<Map.Entry<String, Integer>> urls = this.unchHTMLPagesList.entrySet();//treemap: ключ=id+url, значение=site_id
        System.out.println("Подсчет рейтинга начат");
        for (Map.Entry<String, Integer> url: urls) {
            String[] splittedKey = url.getKey().split(" ");//делим ссылку на куски
            Integer pageId = Integer.parseInt(splittedKey[0]);//берем первый кусок это id страницы
            String pageUrl = splittedKey[1];//второй кусок это url страницы

            //коллекция ключевых слов и person_id(treemap: ключ=name keyword, значение=person_id)
            Set<Map.Entry<String, Integer>> keywords = this.keywordsList.entrySet();
            int rank = 0;
            String content=getContentFromHTML(pageUrl);
            for (Map.Entry<String, Integer> word: keywords) {
                Integer personId = word.getValue();
                String keyword = word.getKey();
                if(content.contains(keyword)){
                    String[] splitContent = content.split("\n");
                    
                    for (String line:splitContent) {  
                        if(line.contains(keyword)){
                            String[] splitLine=line.split(" ");
                            for(String piece :splitLine){
                                if(piece.equals(keyword)) rank++;                   
                            }
                        }
                        
                    }
                    
                }                
                System.out.println(personId+" "+pageId+" "+rank);  
                this.personsPageRank.put(personId + " " + pageId, rank);                
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
            this.keywordsList = this.ktr.getKeywordsList();//treemap: ключ=name keyword, значение=person_id
        } catch (Exception e) {
            e.printStackTrace();
        }
        Set<Map.Entry<String, Integer>> keywords = this.keywordsList.entrySet();
        for (Map.Entry<String, Integer> word : keywords){
                System.out.println(word.getKey()+" "+word.getValue());
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
