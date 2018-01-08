package parser;

import downloader.Downloader;

import java.io.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
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
    private TreeMap<String, Integer>  urlssitemap=new TreeMap<>();
//Конструктор класса
    public ParseSiteMaps(TreeMap<String, Integer> unchSitemapsList) {
        this.unchSitemapsList = unchSitemapsList;
    }

    public void run() {
        System.out.println("parseSiteMaps begin");

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
//обработка списка архивированных файлов
        if (!(sitemapGZFiles.isEmpty())) {
            container = openSitemapGZArchiveFile();
//            Set<Map.Entry<String, Integer>> contents = this.container.entrySet();
//            for (Map.Entry<String, Integer> cont : contents) {
//                String[] splitResult1 = cont.getKey().split(" ");
//                for (int i = 0; i < splitResult1.length; i++) {
                    pagesList.putAll(container);
//                }
//            }
            container.clear();
        }

        if(!(sitemapXMLFilesList.isEmpty())) {
            int count = 1;             
            Set<Map.Entry<String, Integer>> pair = this.sitemapXMLFilesList.entrySet();
            for (Map.Entry<String, Integer> item : pair) {
                String xmlFileDir = "d:/forSitemaps/sm" + count + ".xml";
                //метод скачивания и записи  файла
                downloadUsingStream(item.getKey(), xmlFileDir);                
                urlssitemap = openXMLFile(xmlFileDir);
                System.out.println("файл"+xmlFileDir+"прочитан");
                
                Set<Map.Entry<String, Integer>> urlssm = urlssitemap.entrySet();
                for (Map.Entry<String, Integer> url : urlssm) {                   
                    pagesList.put(url.getKey(),url.getValue());                    
                }
                count++;
            }

        }

        System.out.println("parseSiteMaps end");
    }

    private TreeMap<String, Integer> openSitemapGZArchiveFile(){
        int count = 0;
        TreeMap<String, Integer>  urlsGZsitemap;//коллекция ссылок при обработке
        //конкретного архивированного sitemap
        
        //коллекция полученных ссылок при обработке всех архивированных sitemap'ов
        TreeMap<String, Integer> result = new TreeMap<>();

        Set<Map.Entry<String, Integer>> names = this.sitemapGZFiles.entrySet();
        for (Map.Entry<String, Integer> gzsitemap : names) {
            System.out.println();
            count++;
//формируем имя архивированного файла
            String gzFileDir = "d:/forSitemaps/siteMap" + count + ".xml.gz";
//метод скачивания и записи  архивированного файла
            downloadUsingStream(gzsitemap.getKey(), gzFileDir);
//формируем имя разархивированного файла
            String xmlFileDir = "d:/forSitemaps/sm" + count + ".xml";
//метод разархивирования файла .gz  в файл .xml
            decompressGzipFile(gzFileDir, xmlFileDir);
//метод открытия xml файла, поиска ссылок и возврата их в виде коллекции
            urlsGZsitemap = openXMLFile(xmlFileDir);

//            result.put(urlsGZsitemap, gzsitemap.getValue());
            result.putAll(urlsGZsitemap);
        }
        return result;
    }

    private void downloadUsingStream(String urlStr, String file) {
        BufferedInputStream bis = null;
        FileOutputStream fos = null;
        try {
            URL url = new URL(urlStr);
            bis = new BufferedInputStream(url.openStream());
            fos = new FileOutputStream(file);
            byte[] buffer = new byte[1024];//массив для чтения байтов
            int count = 0;
            while ((count = bis.read(buffer, 0, 1024)) != -1) {/*
                *возвращает количество прочитанных байтов в буфер, если всё
                прочитано, то возвращает -1
                */

                fos.write(buffer, 0, count);//запись из массива в буфер
            }
        } catch (IOException e){
            e.printStackTrace();
        } finally {
            try {
                fos.close();
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
            while((len = gis.read(buffer)) != -1){//пока не закончитися чтение в буфер
                fos.write(buffer, 0, len);//пишем в xml файл
            }
            fos.close();
            gis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private TreeMap<String, Integer> openXMLFile(String file) {
        long t = System.currentTimeMillis();
        String str = "";
        TreeMap<String, Integer> pagesfromXML = new TreeMap();
        ArrayList<String> listPages=new ArrayList();
        try {
            File f = new File(file);
            final int length = (int) f.length();//преобразуем long в int
            if (length != 0) {
                char[] cbuf = new char[length];//массив для чтения из файла
                //поток для чтения из файла
                InputStreamReader isr = new InputStreamReader(new FileInputStream(f), "UTF-8");
                final int read = isr.read(cbuf);//количество считанных символов в массив cbuf из потока
                str = new String(cbuf, 0, read);//формирование строки из массива cbuf
                isr.close();//закрытие потока
            }
            //извлечение из строки ссылок
            listPages = new StringWorker().handlingString(str);
            for (String page:listPages){                            
                pagesfromXML.put(page, 1);//добавление ссылок в коллекцию
            }
            System.out.println("файл "+file+" обработан...");
            System.out.println((System.currentTimeMillis() - t) / 1000 + "s" + (System.currentTimeMillis() - t) % 1000 + "ms");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pagesfromXML;
    }

    public TreeMap<String, Integer> getPagesList() {
        return pagesList;
    }

}