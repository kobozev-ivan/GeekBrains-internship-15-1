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
        Set<Map.Entry<String, Integer>> set = this.unchHTMLPagesList.entrySet();
        for (Map.Entry<String, Integer> page: set) {
            String[] splittedKey = page.getKey().split(" ");
            Integer pageId = Integer.parseInt(splittedKey[0]);
            String pageName = splittedKey[1];
            Set<Map.Entry<String, Integer>> set1 = this.keywordsList.entrySet();
            int rank = 0;
            for (Map.Entry<String, Integer> item1: set1) {
                Integer personId = item1.getValue();
                String keyword = item1.getKey();
                String[] splitContent = getContentFromHTML(pageName).split(" ");
                for (int i = 0; i < splitContent.length; i++) {
                    if(splitContent.equals(keyword)) {
                        rank++;
                    }
                }
                personsPageRank.put(personId + " " + pageId, rank);
            }
        }
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
