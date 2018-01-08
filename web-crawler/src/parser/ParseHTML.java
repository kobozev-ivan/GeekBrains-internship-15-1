/**
 * В разработке...
 * @author Anton Lapin, Yury Tweritin
 * @date 29.12.2017
 */
package parser;

import java.util.TreeMap;

public class ParseHTML extends Thread {

    private TreeMap<String, Integer> unchHTMLPagesList = new TreeMap<>();
    //конструктор
    public ParseHTML(TreeMap<String, Integer> unchHTMLPagesList) {
        this.unchHTMLPagesList = unchHTMLPagesList;
    }

    
    public void run() {
        downloadHTML(); 
        parseString();
    }

    private void downloadHTML() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void parseString() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
        
}


