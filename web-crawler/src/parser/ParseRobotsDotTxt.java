package parser;

import dbworker.SitesTableWorker;
import downloader.Downloader;

import java.io.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.zip.GZIPInputStream;

public class ParseRobotsDotTxt extends Thread {
    //Инициирует запрос в БД через класс dbworker.SitesTableWorker
    //получает список названий сайтов, у которых нет ссылок на HTML - страницы в БД
    //инициирует поиск robots.txt данных сайтов
    //просматривает robots.txt в поисках ссылок на sitemap

    private TreeMap<Integer, String> siteNames = new TreeMap<>();
    private TreeMap<String, Integer> sitemaps = new TreeMap<>();
    private TreeMap<String,Integer> GZsitemap = new TreeMap<>();

    public void run(){
        System.out.println("parseRobotsDotTxt begin");
        siteNames = initDataBaseQuery();

        Set<Map.Entry<Integer, String>> set = siteNames.entrySet();

        for (Map.Entry<Integer, String> siteName: set) {
            Integer id = siteName.getKey();
            String site = siteName.getValue();
            sitemaps = searchSiteMapReference(site, id);

        }
        System.out.println("parseRobotsDotTxt end");
    }

    public TreeMap<String, Integer> getSitemaps(){
        return sitemaps;
    }

    private TreeMap<Integer, String> initDataBaseQuery() {
        TreeMap<Integer, String> list = new TreeMap<>();
        SitesTableWorker sitesTableWorker = new SitesTableWorker();
        sitesTableWorker.start();
        try {
            sitesTableWorker.join();
        } catch (InterruptedException e){
            e.printStackTrace();
        }

        try {
            list = sitesTableWorker.getNoReferenceSiteNamesList();//возврат списка названий сайтов
        } catch (SQLException e){
            e.printStackTrace();
        }

        return list;
    }

    private String getResultAfterRobotsTxtQuery(String siteName){
        String result = new Downloader().download("http://www." + siteName + "/robots.txt");
        return result;
    }

    private TreeMap<String, Integer> searchSiteMapReference(String siteName, Integer id) {
        TreeMap<String, Integer> sitemap = new TreeMap<>();

        TreeMap<String, Integer> GZsitemap2 = new TreeMap<>();

        String robotsResult = getResultAfterRobotsTxtQuery(siteName);//1

        String[] splitResult = robotsResult.split(" ");

        for(int i = 0; i < splitResult.length; i++){
            if(splitResult[i].equals("Sitemap:") || splitResult[i].equals("sitemap:")){
                if(splitResult[i + 1].contains(".gz")){
                    GZsitemap.put(splitResult[i + 1], id);
                } else {
                    sitemap.put(splitResult[i + 1], id);
                }
            }
        }

        Set<Map.Entry<String, Integer>> names1 = GZsitemap.entrySet();
        for (Map.Entry<String, Integer> gzsitemap : names1) {
            System.out.println(gzsitemap.getKey() + " " + gzsitemap.getValue());
        }

        if (!(GZsitemap.isEmpty())) {
            GZsitemap2 = openGZsitemapArchiveFile();
        }

        GZsitemap.clear();

        Set<Map.Entry<String, Integer>> names = GZsitemap2.entrySet();
        for (Map.Entry<String, Integer> gzsitemap2 : names) {
            System.out.println(gzsitemap2.getKey() + " " + gzsitemap2.getValue());//?

            String[] splitResult1 = gzsitemap2.getKey().split(" ");

            for (int i = 0; i < splitResult1.length; i++) {

                if (splitResult1[i].contains(".gz")) {
                    GZsitemap.put(splitResult1[i], id);
                } else {
                    sitemap.put(splitResult1[i], id);
                }
            }
        }

        System.out.println();

        Set<Map.Entry<String, Integer>> names2 = GZsitemap.entrySet();
        for (Map.Entry<String, Integer> gzsitemap : names2) {
            System.out.println(gzsitemap.getKey() + " " + gzsitemap.getValue());
        }

        GZsitemap2.clear();

        if (!(GZsitemap.isEmpty())) {
            GZsitemap2 = openGZsitemapArchiveFile();
        }

        GZsitemap.clear();

        Set<Map.Entry<String, Integer>> names3 = GZsitemap2.entrySet();
        for (Map.Entry<String, Integer> gzsitemap2 : names3) {
            System.out.println(gzsitemap2.getKey() + " " + gzsitemap2.getValue());//?

            String[] splitResult1 = gzsitemap2.getKey().split(" ");

            for (int i = 0; i < splitResult1.length; i++) {

                if (splitResult1[i].contains(".gz")) {
                    GZsitemap.put(splitResult1[i], id);
                } else {
                    sitemap.put(splitResult1[i], id);
                }
            }
        }

        System.out.println();

        Set<Map.Entry<String, Integer>> names4 = GZsitemap.entrySet();
        for (Map.Entry<String, Integer> gzsitemap : names4) {
            System.out.println(gzsitemap.getKey() + " " + gzsitemap.getValue());
        }

        GZsitemap2.clear();

        return sitemap;
    }

    private TreeMap<String, Integer> openGZsitemapArchiveFile(){
        int count = 0;
        String result;
        TreeMap<String, Integer> childGZsitemap = new TreeMap<>();

        Set<Map.Entry<String, Integer>> names = GZsitemap.entrySet();
        for (Map.Entry<String, Integer> gzsitemap : names) {
            System.out.println();
            count++;
            String gzFileDir = "e:/forSitemaps/siteMap" + count + ".xml.gz";

            downloadUsingStream(gzsitemap.getKey(), gzFileDir);
            String xmlFileDir = "e:/forSitemaps/sm" + count + ".xml";
            decompressGzipFile(gzFileDir, xmlFileDir);

            result = openXMLFile(xmlFileDir);

            childGZsitemap.put(result, gzsitemap.getValue());
        }
        return childGZsitemap;
    }

    private void downloadUsingStream(String urlStr, String file) {
        BufferedInputStream bis = null;
        FileOutputStream fis = null;
        try {
            URL url = new URL(urlStr);
            bis = new BufferedInputStream(url.openStream());
            fis = new FileOutputStream(file);
            byte[] buffer = new byte[1024];
            int count = 0;
            while ((count = bis.read(buffer, 0, 1024)) != -1) {
                fis.write(buffer, 0, count);
            }
        } catch (IOException e){
            e.printStackTrace();
        } finally {
            try {
                fis.close();
                bis.close();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    private void decompressGzipFile(String gzipFile, String newFile) {
        try {
            FileInputStream fis = new FileInputStream(gzipFile);
            GZIPInputStream gis = new GZIPInputStream(fis);
            FileOutputStream fos = new FileOutputStream(newFile);
            byte[] buffer = new byte[1024];
            int len;
            while((len = gis.read(buffer)) != -1){
                fos.write(buffer, 0, len);
            }
            fos.close();
            gis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //метод который открывает файл с сайтмапом.
    private String openXMLFile(String file) {
        long t = System.currentTimeMillis();
        String str = "";
        String hstr = "";
        try {
            File f = new File(file);
            final int length = (int) f.length();
            if (length != 0) {
                char[] cbuf = new char[length];
                InputStreamReader isr = new InputStreamReader(new FileInputStream(f), "UTF-8");
                final int read = isr.read(cbuf);
                str = new String(cbuf, 0, read);
                isr.close();
            }
            hstr = new StringWorker().handlingString(str);
            System.out.println("файл обработан...");
            System.out.println((System.currentTimeMillis() - t) / 1000 + "s" + (System.currentTimeMillis() - t) % 1000 + "ms");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return hstr;
    }
}
