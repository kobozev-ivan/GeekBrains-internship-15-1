package parser;

import dbworker.SitesTableWorker;
import downloader.Downloader;

import java.sql.SQLException;
import java.util.ArrayList;

public class ParseRobotsDotTxt {
    //Инициирует запрос в БД через класс dbworker.SitesTableWorker
    //получает список названий сайтов, у которых нет ссылок на HTML - страницы в БД
    //инициирует поиск robots.txt данных сайтов 
    //просматривает robots.txt в поисках ссылок на sitemap
   
    private  ArrayList<String> siteNames;//хранит имена сайтов
    private  ArrayList<String> urlsSitemap;//хранит адреса Sitemap'ов
    
    /*
    метод возвращает список ссылок SiteMap'ов неисследованных сайтов
    */
    public ArrayList<String> parseRobotsDotTxt() {
        urlsSitemap= new ArrayList();
        siteNames= new ArrayList();
        siteNames = initDataBaseQuery();//запрос списка сайтов

        for (String siteName: siteNames) {
            urlsSitemap.add(searchSiteMapReference(siteName));//добавление ссылки SiteMap в список
//            System.out.println(searchSiteMapReference(siteName));//вывод ссылки из SiteMap    
        }
        return urlsSitemap;
    }

    private  ArrayList<String> initDataBaseQuery() {
        ArrayList<String> list = new ArrayList<>();
        SitesTableWorker sitesTableWorker = new SitesTableWorker();
        sitesTableWorker.start();
        try {
            sitesTableWorker.join();//ждем завершения потока sitesTableWorker
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

    
    
    
    private  String searchSiteMapReference(String siteName) {
        String url = null;         
        Downloader downloader = new Downloader();

        String result = downloader.download(siteName + "/robots.txt");
/*
дробим результат на строки и ищем в каждой строке позицию символов Sitemap
Считается, что в файле Robot.txt есть только единственная запись Sitemap
*/
        String[] lines = result.split("\n");//делим на строки и кладем в массив строк
//ищем строку Sitemap: или sitemap: после которой идет его адрес
        for(String line:lines){
            int index=line.indexOf("Sitemap:");
            if (index!=-1){
               url=line.substring(index+8).replace( " ", "" );
            }
            else{
               index=line.indexOf("sitemap:");
               if (index!=-1){
               url=line.substring(index+8).replace( " ", "" );
               }
            }   
        }
        return url;//возравщаем адрес sitemap
    }
}