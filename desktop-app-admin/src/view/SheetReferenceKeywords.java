package view;

import reference.Data;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

/**
 * Created by Максим on 17.12.2017.
 */
class SheetReferenceKeywords extends SheetReference{

    DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>();
    HashMap<String, Data<String>> hashMapListModelKeywords = new HashMap<>();
    private JComboBox<String> nameOfPerson = new JComboBox<>(comboBoxModel);

    SheetReferenceKeywords(){
        super();
        nameOfPerson.setPrototypeDisplayValue("1 2 3 4 5 6 7 8 9 10 11 12 13 14 15");
        nameOfPerson.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = comboBoxModel.getElementAt(nameOfPerson.getSelectedIndex());
                if (!hashMapListModelKeywords.containsKey(name))  hashMapListModelKeywords.put(name, new Data<>());
                list.setModel(hashMapListModelKeywords.get(name));
            }
        });
        JPanel upperPanel = new JPanel();
        upperPanel.add(nameOfPerson);
        add(upperPanel, 0);
    }

    void toAgree(SheetReference sheetReference){
        DefaultListModel<String> model = (DefaultListModel<String>) sheetReference.list.getModel();
        if (sheetReference.removal.size() != 0){
            for (int i = 0; i < sheetReference.removal.size(); i++) {
                String key = comboBoxModel.getElementAt(sheetReference.removal.get(i));
                hashMapListModelKeywords.remove((key), hashMapListModelKeywords.get(key));
                comboBoxModel.removeElementAt(sheetReference.removal.get(i));
            }
            sheetReference.removal.clear();
        }
        if (!model.isEmpty()) {
            if (model.getSize() > comboBoxModel.getSize()){
                for (int i = comboBoxModel.getSize(); i < model.getSize(); i++) {
                    comboBoxModel.addElement(model.get(i).trim());
                }
            }
            if (model.getSize() == comboBoxModel.getSize()){
                for (int i = 0, j = 0; i < model.getSize(); i++, j++) {
                    if (!model.getElementAt(i).equals(comboBoxModel.getElementAt(j))){
                        String oldKey = comboBoxModel.getElementAt(j);
                        comboBoxModel.removeElementAt(j);
                        String newKey = model.getElementAt(i);
                        comboBoxModel.insertElementAt(model.getElementAt(i), j);
                        hashMapListModelKeywords.put(newKey, hashMapListModelKeywords.get(oldKey));
                        hashMapListModelKeywords.remove(oldKey, hashMapListModelKeywords.get(oldKey));
                    }
                }
            }
        }
    }
}
