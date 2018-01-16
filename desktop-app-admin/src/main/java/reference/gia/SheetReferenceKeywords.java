package reference.gia;
import reference.Data;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SheetReferenceKeywords extends SheetReference{

    private static final String WIDTH_OF_LIST = "1 2 3 4 5 6 7 8 9 10 11 12 13 14 15";
    DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>();
    private HashMap<String, Data<String>> dataModelKeywords = new HashMap<>();
    JComboBox<String> nameOfPerson = new JComboBox<>(comboBoxModel);
    public String selectComboBoxModel;

    SheetReferenceKeywords(){
        super();
        nameOfPerson.setPrototypeDisplayValue(WIDTH_OF_LIST);
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
            if (model.size() > comboBoxModel.getSize()) toAddItem(model);
            if (model.getSize() == comboBoxModel.getSize()) toCompareElements(model);
            if (model.size() < comboBoxModel.getSize()) atDeletingElement(model);
        }else if (comboBoxModel.getSize() != 0){
            comboBoxModel.removeAllElements();
            dataModelKeywords.clear();
        }
    }

    private void atDeletingElement(DefaultListModel<String> model){
        comboBoxModel.removeAllElements();
        toAddItem(model);
        toReconcileData(model);
    }

    private void toReconcileData(DefaultListModel<String> model) {
        Iterator<Map.Entry<String, Data<String>>> iterator = dataModelKeywords.entrySet().iterator();
        pass: while (iterator.hasNext()){
            Map.Entry<String, Data<String>> entry = iterator.next();
            int count = 0;
            String oldKey = entry.getKey();
            if (oldKey == null){
                iterator.remove();
                continue;
            }
            for (int i = 0; i < model.size(); i++) {
                if (!oldKey.equalsIgnoreCase(model.get(i))){
                    count++;
                    if (count == model.getSize()){
                        iterator.remove();
                    }
                }else continue pass;
            }
        }
    }

    private void toAddItem(DefaultListModel<String> model){
        for (int i = 0; i < model.getSize(); i++) {
            if (comboBoxModel.getElementAt(i) == null ||
                    !model.get(i).equalsIgnoreCase(comboBoxModel.getElementAt(i)))
                comboBoxModel.insertElementAt(model.get(i), i);
        }
    }

    private void toCompareElements(DefaultListModel<String> model){
        for (int i = 0, j = 0; i < model.getSize(); i++, j++) {
            if (!model.getElementAt(i).equals(comboBoxModel.getElementAt(j))){
                String oldKey = comboBoxModel.getElementAt(j);
                comboBoxModel.removeElementAt(j);
                comboBoxModel.insertElementAt(model.getElementAt(i), j);
                dataModelKeywords.remove(oldKey, dataModelKeywords.get(oldKey));
            }
        }
    }
}
