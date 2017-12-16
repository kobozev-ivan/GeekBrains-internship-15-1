package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by Максим on 15.12.2017.
 */
public class SheetReference extends JPanel implements ActionListener{

    JList<String> list = new JList<>();

    JLabel labelList = new JLabel("Список");
    JButton buttonAdd = new JButton("Добавить");
    JButton buttonEdit = new JButton("Редактировать");
    JButton buttonDel = new JButton("Удалить");
    JButton buttonSave = new JButton("Сохранить");
    JButton buttonCancel = new JButton("Отмена");
    JPanel panelButton = new JPanel();
    ArrayList<Integer> removal = new ArrayList<>();

    SheetReference(){
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        list.setSelectionForeground(Color.BLUE);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setLayoutOrientation(JList.VERTICAL);
        list.setAutoscrolls(true);
        JScrollPane scrollList = new JScrollPane(list);
        JPanel centr  = new JPanel(new GridLayout(1,2));
        JPanel panelList = new JPanel();
        JPanel down = new JPanel();

        panelList.setLayout(new BoxLayout(panelList,BoxLayout.Y_AXIS));
        panelButton.setLayout(new BoxLayout(panelButton,BoxLayout.Y_AXIS));
        panelList.add(labelList);
        panelList.add(scrollList);

        panelButton.add(Box.createVerticalStrut(30));
        panelButton.add(Box.createVerticalGlue());
        panelButton.add(buttonAdd);
        panelButton.add(Box.createVerticalStrut(30));
        panelButton.add(buttonEdit);
        panelButton.add(Box.createVerticalStrut(30));
        panelButton.add(buttonDel);
        panelButton.add(Box.createVerticalGlue());
        panelButton.add(Box.createVerticalStrut(30));

        labelList.setAlignmentX(CENTER_ALIGNMENT);
        buttonAdd.setAlignmentX(CENTER_ALIGNMENT);
        buttonEdit.setAlignmentX(CENTER_ALIGNMENT);
        buttonDel.setAlignmentX(CENTER_ALIGNMENT);

        centr.add(panelList);
        centr.add(panelButton);

        down.add(buttonSave);
        down.add(buttonCancel);

        buttonAdd.addActionListener(this);
        buttonEdit.addActionListener(this);
        buttonDel.addActionListener(this);
        buttonSave.addActionListener(this);
        buttonCancel.addActionListener(this);

        add(centr);
        add(down);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        Object objEvent = e.getSource();
        if (objEvent == buttonAdd){
            String string = JOptionPane.showInputDialog(panelButton, "Добавить в справочник :", "Добавление", JOptionPane.INFORMATION_MESSAGE);
            if (string != null && !string.equals("")){
                DefaultListModel<String> name = (DefaultListModel<String>) list.getModel();
                name.addElement(string.trim());
            }
        }
        if (objEvent == buttonEdit){
            if (!list.isSelectionEmpty()){
                int index = list.getSelectedIndex();
                String string = JOptionPane.showInputDialog(panelButton, "Редактировать " + list.getSelectedValue(), "Редактирование", JOptionPane.WARNING_MESSAGE);
                if (string != null && !string.equals("")){
                    DefaultListModel<String> name = (DefaultListModel<String>) list.getModel();
                    name.remove(list.getSelectedIndex());
                    name.insertElementAt(string.trim(), index);
                }
            }
        }
        if (objEvent == buttonDel){
            if (!list.isSelectionEmpty()){
                int answer= JOptionPane.showConfirmDialog(panelButton, "Удалить " + list.getSelectedValue(), "Удаление", JOptionPane.OK_CANCEL_OPTION);
                if (answer == JOptionPane.OK_OPTION){
                    DefaultListModel<String> name = (DefaultListModel<String>) list.getModel();
                    int index =  list.getSelectedIndex();
                    name.remove(index);
                    removal.add(index);
                }
            }
        }
    }
}
