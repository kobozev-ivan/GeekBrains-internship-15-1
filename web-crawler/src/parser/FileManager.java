/**
 * Содержит методы для работы с файлами, имеющими расширение .xml и .gz
 * @author Anton Lapin, Yury Tweritin
 * @date 29.12.2017
 */
package parser;

import java.io.*;
import java.net.URL;
import java.util.zip.GZIPInputStream;

public class FileManager {

    /**
     * Метод осуществляет скачивание из Интернета файлов и их запись в физическую долговременную память машины
     * @param urlStr
     * @param file
     */

    public void downloadUsingStream(String urlStr, String file) {
        BufferedInputStream bis = null;
        FileOutputStream fos = null;
        try {
            URL url = new URL(urlStr);
            bis = new BufferedInputStream(url.openStream());
            fos = new FileOutputStream(file);
            byte[] buffer = new byte[1024];
            int count = 0;
            while ((count = bis.read(buffer, 0, 1024)) != -1) {
                fos.write(buffer, 0, count);
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

    /**
     * Метод осуществляет извлечение содержимого из .gz архивов
     * @param gzipFile
     * @param newFile
     */

    public void decompressGzipFile(String gzipFile, String newFile) {
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

    /**
     * Метод осуществляет открытие и прочтение содержимого файла с раширением .xml
     * @param file
     * @return
     */

    public String openXMLFile(String file) {
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
        } catch (IOException e) {
            e.printStackTrace();
        }
        return hstr;
    }
}
