package gia;
import reference.Data;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class SheetReferenceKeywords extends SheetReference{

    DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>();
    private HashMap<String, Data<String>> dataModelKeywords = new HashMap<>();
    private JComboBox<String> nameOfPerson = new JComboBox<>(comboBoxModel);
    public String selectComboBoxModel;

    SheetReferenceKeywords(){
        super();
        nameOfPerson.setPrototypeDisplayValue("1 2 3 4 5 6 7 8 9 10 11 12 13 14 15");
        if (comboBoxModel.getSize() != 0) nameOfPerson.setSelectedItem(comboBoxModel.getElementAt(0));
        nameOfPerson.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectComboBoxModel = comboBoxModel.getElementAt(nameOfPerson.getSelectedIndex());
                if (!dataModelKeywords.containsKey(selectComboBoxModel))  dataModelKeywords.put(selectComboBoxModel, new Data<>());
                list.setModel(dataModelKeywords.get(selectComboBoxModel));
            }
        });
        JPanel upperPanel = new JPanel();
        upperPanel.add(nameOfPerson);
        add(upperPanel, 0);
    }

    void toAgree(SheetReference sheetReference){
        DefaultListModel<String> model = (DefaultListModel<String>) sheetReference.list.getModel();
        if (!model.isEmpty()) {
            toAddItem(model);
            toCompareElements(model);
        }
        if (sheetReference.removal.size() != 0) atDeletingElement(sheetReference);
    }

    private void atDeletingElement(SheetReference sheetReference){
        for (int i = 0; i < sheetReference.removal.size(); i++) {
            String key = comboBoxModel.getElementAt(sheetReference.removal.get(i));
            dataModelKeywords.remove((key), dataModelKeywords.get(key));
            comboBoxModel.removeElement(key);
        }
        sheetReference.removal.clear();
    }

    private void toAddItem(DefaultListModel<String> model){
        if (model.getSize() > comboBoxModel.getSize()){
            for (int i = comboBoxModel.getSize(); i < model.getSize(); i++) {
                comboBoxModel.addElement(model.get(i));
            }
        }
    }

    private void toCompareElements(DefaultListModel<String> model){
        if (model.getSize() == comboBoxModel.getSize()){
            for (int i = 0, j = 0; i < model.getSize(); i++, j++) {
                if (!model.getElementAt(i).equals(comboBoxModel.getElementAt(j))){
                    String oldKey = comboBoxModel.getElementAt(j);
                    comboBoxModel.removeElementAt(j);
                    String newKey = model.getElementAt(i);
                    comboBoxModel.insertElementAt(model.getElementAt(i), j);
                    dataModelKeywords.put(newKey, dataModelKeywords.get(oldKey));
                    dataModelKeywords.remove(oldKey, dataModelKeywords.get(oldKey));
                }
            }
        }
    }

}
