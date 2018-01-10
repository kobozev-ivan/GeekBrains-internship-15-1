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
    private TreeMap<String, Integer> personsPageRank;
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
        Set<Map.Entry<String, Integer>> urls = this.unchHTMLPagesList.entrySet();
        for (Map.Entry<String, Integer> url: urls) {
//            String[] splittedKey = url.getKey().line(" ");//делим ссылку на куски
//            Integer pageId = Integer.parseInt(splittedKey[0]);//берем первый кусок это номер????
//            String pageName = splittedKey[1];//второй кусок это имя????
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
                this.personsPageRank.put(personId + " " + url.getKey(), rank);                
            }
        }
        System.out.println("Подсчет статистики закончен");
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
            this.keywordsList = this.ktr.getKeywordsList();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Возврат списка популярности личностей по html страницам
     * @return
     */

    public TreeMap<String, Integer> getPersonsPageRank() {
        return this.personsPageRank;
    }
}
