/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webcrawler;


import java.util.ArrayList;
import parser.ParseRobotsDotTxt;

/**
 *Тестовый класс
 * 
 * @author Yuri Tveritin
 */
public class WebCrawler {

    private static ArrayList<String> urlsSM;//хранит url SiteMap'ов

    public static void main(String[] args) {
        ParseRobotsDotTxt ParseRDT=new ParseRobotsDotTxt();
        urlsSM=ParseRDT.parseRobotsDotTxt();
        for (String url: urlsSM){
            System.out.println(url);
        }
    }
}
    