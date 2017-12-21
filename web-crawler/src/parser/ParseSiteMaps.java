package parser;

import downloader.Downloader;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class ParseSiteMaps extends Thread {
    //получает от ParseRobotsTxt ссылки на sitemap-ы конкретных сайтов
    //запускает загрузку страничек по ссылке
    //сохраняет ссылки на HTML - страицы

    private TreeMap<String, Integer> htmls = new TreeMap<>();
    private TreeMap<String, Integer> sitemaps = new TreeMap<>();

    public static void main(String[] args) {
        long t = System.currentTimeMillis();
        new ParseSiteMaps().run();
        System.out.println("Total time : " + (System.currentTimeMillis() - t) / 1000 + "s");
    }

    public void run() {
        System.out.println("parseSiteMaps begin");
        ParseRobotsDotTxt parseRobotsDotTxt = new ParseRobotsDotTxt();
        parseRobotsDotTxt.start();
        try {
            parseRobotsDotTxt.join();
        } catch (InterruptedException e){
            e.printStackTrace();
        }
        sitemaps = parseRobotsDotTxt.getSitemaps();

        Set<Map.Entry<String, Integer>> names = sitemaps.entrySet();
        for (Map.Entry<String, Integer> smap: names) {
            String sitemap = smap.getKey();
            Integer site_id = smap.getValue();
            System.out.println(smap.getKey() + " " + smap.getValue());

            if(sitemap.contains("sitemap")) {
                htmls.put(searchHTMLReferenece(sitemap), site_id);
            } else {
                htmls.put(sitemap, site_id);
            }
        }

        Set<Map.Entry<String, Integer>> names5 = htmls.entrySet();
        for (Map.Entry<String, Integer> html: names5) {
            System.out.println(html.getKey() + " " + html.getValue());
        }
        System.out.println("parseSiteMaps end");
    }

    private String searchHTMLReferenece(String sitemap){
        String list;
        String result = new Downloader().download(sitemap);
        System.out.println(result);
        list = new StringWorker().handlingString(result);

        return list;
    }

    public TreeMap<String, Integer> getHtmls(){
        return htmls;
    }
}
