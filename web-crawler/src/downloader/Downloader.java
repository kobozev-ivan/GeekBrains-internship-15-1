/**
 * Осуществляет загрузку данных из Интернета в виде строки по заданному URL-адресу
 * @author Anton Lapin, Yury Tweritin
 * @date 29.12.2017
 */
package downloader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class Downloader {

    private BufferedReader reader;
    private String line = "";
    private String addonLine;
    private URL site;

    /**
     * Собственно метод, осуществляющий загрузку данных из Интернета
     * @param url
     * @return
     */

    public String download(String url) {
        try {
            this.site = new URL(url);
            this.reader = new BufferedReader(new InputStreamReader(this.site.openStream()));
            while ((this.addonLine = this.reader.readLine()) != null) {
                this.line += this.addonLine + " ";
            }
            this.reader.close();
        } catch (IOException e) {
            System.out.println("страница "+url+" не существует. Ошибка 404");            
            this.line="";
        } finally {
            try {
                this.reader.close();
            } catch (Exception e) {
            }
        }
        return this.line;
    }

}
