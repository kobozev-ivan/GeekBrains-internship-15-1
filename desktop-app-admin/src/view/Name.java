package view;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.*;
import java.util.HashMap;

/**
 * Created by Максим on 15.12.2017.
 */
public class Name extends JTabbedPane implements ActionListener {

    private SheetReference namesOfIndividuals = new SheetReference();
    private SheetReference namesOfSites = new SheetReference();
    private SheetReference keywords = new SheetReference();
    private DefaultListModel<String> nameList = new DefaultListModel<>();
    private DefaultListModel<String> siteList = new DefaultListModel<>();
//    private DefaultListModel<String> keywordsList = new DefaultListModel<>();
    private DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>();
    private JComboBox<String> nameOfPerson = new JComboBox<>(comboBoxModel);
    private HashMap<String, DefaultListModel<String>> hashMap = new HashMap<>();

    public Name(){

        namesOfIndividuals.list.setModel(nameList);
        namesOfSites.list.setModel(siteList);
//        keywords.list.setModel(keywordsList);
        nameOfPerson.addActionListener(this);
        keywords.panelButton.add(nameOfPerson, 0);

        addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (((JTabbedPane)e.getSource()).getSelectedComponent() == keywords){
                    if (namesOfIndividuals.removal.size() != 0){
                        for (int i = 0; i < namesOfIndividuals.removal.size(); i++) {
                            comboBoxModel.removeElementAt(namesOfIndividuals.removal.get(i));
                        }
                        namesOfIndividuals.removal.clear();
                    }
                    if (!nameList.isEmpty()) {
                        if (nameList.getSize() > comboBoxModel.getSize()){
                            for (int i = comboBoxModel.getSize(); i < nameList.getSize(); i++) {
                                comboBoxModel.addElement(nameList.get(i).trim());
                            }
                        }else if (nameList.getSize() == comboBoxModel.getSize()){
                            for (int i = 0, j = 0; i < nameList.getSize(); i++, j++) {
                                if (!nameList.getElementAt(i).equals(comboBoxModel.getElementAt(j))){
                                    comboBoxModel.removeElementAt(j);
                                    comboBoxModel.insertElementAt(nameList.getElementAt(i), j);
                                }
                            }
                        }
                    }
                }
            }
        });

        add("Имена", namesOfIndividuals);
        add("Названия сайтов", namesOfSites);
        add("Ключевые слова", keywords);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == nameOfPerson){
            String name = comboBoxModel.getElementAt(nameOfPerson.getSelectedIndex());
            hashMap.put(name, new DefaultListModel<>());
        }
    }
}
