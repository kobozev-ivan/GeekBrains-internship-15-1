/**
 * В разработке...
 * @author Anton Lapin, Yury Tweritin
 * @date 29.12.2017
 */
package parser;

import java.util.TreeMap;

public class ParseHTML extends Thread {

    private TreeMap<String, Integer> unchHTMLPagesList = new TreeMap<>();

    public ParseHTML(TreeMap<String, Integer> unchHTMLPagesList) {
        this.unchHTMLPagesList = unchHTMLPagesList;
    }

    public ParseHTML(){

    }

    public void run() {

    }

}
