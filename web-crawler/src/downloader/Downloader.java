package downloader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class Downloader {

    private BufferedReader reader = null;
    private String line = null;
    private String addonLine;

    public String download(String url) {
        try {
            URL site = new URL(url);
            reader = new BufferedReader(new InputStreamReader(site.openStream()));
            while ((addonLine = reader.readLine()) != null) {
                line += addonLine + "\n";
            }
            reader.close();
        } catch (IOException e){
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
        return line;
    }

}
