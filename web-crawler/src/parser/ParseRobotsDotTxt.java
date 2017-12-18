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

    private static String sitemap;
    private static ArrayList<String> siteNames;

    public static void main(String[] args) {

        siteNames = initDataBaseQuery();

        for (String siteName: siteNames) {
            System.out.println(searchSiteMapReference(siteName));
        }
    }

    private static ArrayList<String> initDataBaseQuery() {
        ArrayList<String> list = new ArrayList<>();
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

    public static String searchSiteMapReference(String siteName) {

        Downloader downloader = new Downloader();

        String result = downloader.download("http://" + siteName + "/robots.txt");

        String[] splitResult = result.split(" ");

        for(int i = 0; i < splitResult.length; i++){
            if(splitResult[i].equals("Sitemap:") || splitResult[i].equals("sitemap:")){
                sitemap = splitResult[i + 1];
            }
        }

        return sitemap;
    }
}
