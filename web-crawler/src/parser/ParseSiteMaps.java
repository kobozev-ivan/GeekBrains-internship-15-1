/**
 * Получает от Collector ссылки на sitemap-ы конкретных сайтов, запускает загрузку страничек по ссылке
 * в случае, если ссылка на сайтмап имеет расширение, передает в Collector ссылки на HTML - страницы
 * @author Anton Lapin, Yury Tweritin
 * @date 29.12.2017
 */
package parser;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class ParseSiteMaps extends Thread {

    private TreeMap<String, Integer> unchSitemapsList;
    private TreeMap<String, Integer> pagesList = new TreeMap<>();
    private TreeMap<String, Integer> sitemapXMLFilesList = new TreeMap<>();
    private TreeMap<String, Integer> sitemapGZFiles = new TreeMap<>();
    private TreeMap<String, Integer> container;
    private FileManager fileManager;

    /**
     * Конструктор класса
     * @param unchSitemapsList
     */

    public ParseSiteMaps(TreeMap<String, Integer> unchSitemapsList) {
        this.unchSitemapsList = unchSitemapsList;
    }

    /**
     * Основной алгоритм работы
     */

    public void run() {
        sortUncheckedSiteMapsList();

        if (!(this.sitemapGZFiles.isEmpty())) {
            initSitemapsGZFiles();
        }

        if(!(sitemapXMLFilesList.isEmpty())) {
            initSitemapXMlFiles();
        }
    }

    /**
     * Метод, осуществляющий сортировку списка непроверенных ссылок на sitemap-ы
     */

    private void sortUncheckedSiteMapsList() {
        Set<Map.Entry<String, Integer>> set = this.unchSitemapsList.entrySet();
        for (Map.Entry<String, Integer> item: set) {
            String[] key = item.getKey().split(" ");
            if (item.getKey().contains(".gz")) {
                this.sitemapGZFiles.put(key[1], item.getValue());
            } else if(key[1].substring(key[1].length() - 4).equals(".xml")) {
                this.sitemapXMLFilesList.put(key[1], item.getValue());
            } else {
                this.pagesList.put(key[1], item.getValue());
            }
        }
    }

    /**
     * Метод, осуществляющий извлечение, и прочтение содержимого из .gz архивов
     */

    private void initSitemapsGZFiles() {
        this.container = openSitemapGZArchiveFile();
        Set<Map.Entry<String, Integer>> contents = this.container.entrySet();
        for (Map.Entry<String, Integer> cont : contents) {
            String[] splitResult1 = cont.getKey().split(" ");
            for (int i = 0; i < splitResult1.length; i++) {
                this.pagesList.put(splitResult1[i], cont.getValue());
            }
        }
        this.container.clear();
    }

    /**
     * Метод, осуществляющий прочтение содержимого .xml файлов
     */

    private void initSitemapXMlFiles() {
        int count = 1;
        Set<Map.Entry<String, Integer>> pair = this.sitemapXMLFilesList.entrySet();
        for (Map.Entry<String, Integer> item : pair) {
            this.fileManager = new FileManager();
            String xmlFileDir = "d:/forSitemaps/sm" + count + ".xml";
            this.fileManager.downloadUsingStream(item.getKey(), xmlFileDir);
            String result = this.fileManager.openXMLFile(xmlFileDir);
            String[] splitResult1 = result.split(" ");
            for (int i = 0; i < splitResult1.length; i++) {
                this.pagesList.put(splitResult1[i], item.getValue());
            }
            count++;
        }
    }

    /**
     * Метод извлекает содержимое из .gz архива и передает его содержимое
     * @return содержимое архива
     */

    private TreeMap<String, Integer> openSitemapGZArchiveFile(){
        int count = 0;
        String result;
        TreeMap<String, Integer> childGZsitemap = new TreeMap<>();
        Set<Map.Entry<String, Integer>> names = this.sitemapGZFiles.entrySet();
        for (Map.Entry<String, Integer> gzsitemap : names) {
            this.fileManager = new FileManager();
            System.out.println();
            count++;
            String gzFileDir = "d:/forSitemaps/siteMap" + count + ".xml.gz";
            this.fileManager.downloadUsingStream(gzsitemap.getKey(), gzFileDir);
            String xmlFileDir = "d:/forSitemaps/sm" + count + ".xml";
            this.fileManager.decompressGzipFile(gzFileDir, xmlFileDir);
            result = this.fileManager.openXMLFile(xmlFileDir);
            childGZsitemap.put(result, gzsitemap.getValue());
        }
        return childGZsitemap;
    }

    /**
     * Метод, осуществляющий получение списка ссылок веб-страниц
     * @return список ссылок новых страниц
     */

    public TreeMap<String, Integer> getPagesList() {
        return this.pagesList;
    }
}