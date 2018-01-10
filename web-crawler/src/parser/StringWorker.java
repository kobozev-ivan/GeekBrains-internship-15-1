/**
 * Работает со строками большой длины, сокращает, вынимая из исходных строк 
 * нужную для обработки информацию
 * @author Anton Lapin, Yury Tweritin
 * @date 29.12.2017
 */
package parser;

import java.util.ArrayList;
import java.util.TreeMap;

public class StringWorker {
    private String[] splitResult;
    private String[] subsplit;
    private ArrayList  pagesfromXML=new ArrayList();
    private int length;

    /**
     * Метод обработки строки; вычленение из неё ссылок веб-страниц
     * @param str
     * @return
     */

    public ArrayList<String> handlingString(String str) {        
        this.splitResult = str.split("<loc>");//делим на куски и кладем их в массив строк
        this.length = this.splitResult.length;//количество кусков
        for (int i = 0; i < this.length; i++) {
            if (this.splitResult[i].contains("</loc>")){                
                this.subsplit=this.splitResult[i].split("</loc>");//делим подстроку на подподстроки
//                System.out.println(this.subsplit[0]);
                this.pagesfromXML.add(this.subsplit[0]);//и кладем первую 
//                подподстроку в коллекцию  (и будет являться ссылкой)          
            }
        }
        this.subsplit=null;
        return this.pagesfromXML;
    }
}
