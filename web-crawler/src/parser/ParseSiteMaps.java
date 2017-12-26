package parser;

import downloader.Downloader;

import java.io.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.zip.GZIPInputStream;

public class ParseSiteMaps extends Thread {
    //получает от Collector ссылки на sitemap-ы конкретных сайтов
    //запускает загрузку страничек по ссылке
    //в случае, если ссылка на сайтмап имеет расширение
    //передает в Collector ссылки на HTML - страницы

    private TreeMap<String, Integer> unchSitemapsList = new TreeMap<>();
    private TreeMap<String, Integer> pagesList = new TreeMap<>();
    private TreeMap<String, Integer> sitemapXMLFilesList = new TreeMap<>();
    private TreeMap<String, Integer> sitemapGZFiles = new TreeMap<>();
    private TreeMap<String, Integer> container = new TreeMap<>();

    public ParseSiteMaps(TreeMap<String, Integer> unchSitemapsList) {
        this.unchSitemapsList = unchSitemapsList;
    }

    public void run() {
        System.out.println("parseSiteMaps begin");

        Set<Map.Entry<String, Integer>> set = unchSitemapsList.entrySet();
        for (Map.Entry<String, Integer> item: set) {
            if (item.getKey().contains(".gz")) {
                sitemapGZFiles.put(item.getKey(), item.getValue());
            } else if(item.getKey().substring(item.getKey().length() - 4).equals(".xml")) {
                sitemapXMLFilesList.put(item.getKey(), item.getValue());
            } else {
                pagesList.put(item.getKey(), item.getValue());
            }
        }

        if (!(sitemapGZFiles.isEmpty())) {
            container = openSitemapGZArchiveFile();
            Set<Map.Entry<String, Integer>> contents = container.entrySet();
            for (Map.Entry<String, Integer> cont : contents) {
                String[] splitResult1 = cont.getKey().split(" ");
                for (int i = 0; i < splitResult1.length; i++) {
                    pagesList.put(splitResult1[i], cont.getValue());
                }
            }
            container.clear();
        }

        if(!(sitemapXMLFilesList.isEmpty())) {
            int count = 1;
            Set<Map.Entry<String, Integer>> pair = sitemapXMLFilesList.entrySet();
            for (Map.Entry<String, Integer> item : pair) {
                String xmlFileDir = "d:/forSitemaps/sm" + count + ".xml";
                downloadUsingStream(item.getKey(), xmlFileDir);
                String result = openXMLFile(xmlFileDir);
                String[] splitResult1 = result.split(" ");
                for (int i = 0; i < splitResult1.length; i++) {
                    pagesList.put(splitResult1[i], item.getValue());
                }
                count++;
            }

        }

        System.out.println("parseSiteMaps end");
    }

    private TreeMap<String, Integer> openSitemapGZArchiveFile(){
        int count = 0;
        String result;
        TreeMap<String, Integer> childGZsitemap = new TreeMap<>();

        Set<Map.Entry<String, Integer>> names = sitemapGZFiles.entrySet();
        for (Map.Entry<String, Integer> gzsitemap : names) {
            System.out.println();
            count++;
            String gzFileDir = "d:/forSitemaps/siteMap" + count + ".xml.gz";

            downloadUsingStream(gzsitemap.getKey(), gzFileDir);
            String xmlFileDir = "d:/forSitemaps/sm" + count + ".xml";
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
            byte[] buffer = new byte[16384];
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

    public TreeMap<String, Integer> getPagesList() {
        return pagesList;
    }

}