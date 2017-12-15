package view;

import reference.Data;

import javax.swing.*;
import java.util.Vector;

/**
 * Created by Максим on 15.12.2017.
 */
public class Name extends JTabbedPane{

    Editor namesOfIndividuals = new Editor();
    Editor namesOfSites = new Editor();
    Editor keywords = new Editor();
    Data nameList = new Data();
    Data siteList = new Data();
    Data keywordsList = new Data();
    Vector<String> comboBoxModel = new Vector<>();

    {
        if (nameList.isEmpty()) comboBoxModel.add("пусто");
        else {
            for (int i = 0; i < nameList.getSize(); i++) {
                comboBoxModel.addElement((String) nameList.get(i));
            }
        }
    }

    public Name(){

        namesOfIndividuals.list.setModel(nameList);
        namesOfSites.list.setModel(siteList);
        keywords.list.setModel(keywordsList);
        JComboBox nameOfPerson = new JComboBox(comboBoxModel);
        keywords.panelButton.add(nameOfPerson, 0);
        add("Имена", namesOfIndividuals);
        add("Названия сайтов", namesOfSites);
        add("Ключевые слова", keywords);
    }
}
