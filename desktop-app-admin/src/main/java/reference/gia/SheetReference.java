package reference.gia;

import reference.Data;

import javax.swing.*;
import javax.xml.ws.WebServiceException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.ConnectException;
import java.util.ArrayList;

public class SheetReference extends JPanel implements ActionListener{

    private Data<String> dataSheet = new Data<>();
    public JList<String> list = new JList<>(dataSheet);

    JButton buttonAdd = new JButton("Добавить");
    private JButton buttonEdit = new JButton("Редактировать");
    private JButton buttonDel = new JButton("Удалить");
    private JButton buttonUpDate = new JButton("Обновить");
    private JButton buttonSave = new JButton("Сохранить");
    private JPanel panelButton = new JPanel();
    ArrayList<Integer> removal = new ArrayList<>();

    SheetReference(){
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        list.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
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
        JLabel labelList = new JLabel("Список");
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

        down.add(buttonUpDate);
        down.add(buttonSave);

        buttonAdd.addActionListener(this);
        buttonEdit.addActionListener(this);
        buttonDel.addActionListener(this);
        buttonUpDate.addActionListener(this);
        buttonSave.addActionListener(this);

        add(centr);
        add(down);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object objEvent = e.getSource();
        if (objEvent == buttonAdd){
            String stringInput = JOptionPane.showInputDialog(panelButton, "Добавить в справочник :", "Добавление", JOptionPane.INFORMATION_MESSAGE);
            if (stringInput != null && !stringInput.equals("")) dataSheet.toAdd(this, stringInput.trim());
        }
        if (objEvent == buttonEdit){
            if (!list.isSelectionEmpty()){
                String stringSelect = list.getSelectedValue();
                String stringInput = JOptionPane.showInputDialog(panelButton, "Редактировать " + list.getSelectedValue(), "Редактирование", JOptionPane.WARNING_MESSAGE);
                if (stringInput != null && !stringInput.equals("")) dataSheet.toModify(this, stringSelect, stringInput.trim());
            }
        }
        if (objEvent == buttonDel){
            if (!list.isSelectionEmpty()){
                int answer= JOptionPane.showConfirmDialog(panelButton, "Удалить " + list.getSelectedValue(), "Удаление", JOptionPane.OK_CANCEL_OPTION);
                if (answer == JOptionPane.OK_OPTION){
                    removal.add(list.getSelectedIndex());
                    dataSheet.toRemove(this, list.getSelectedValue());
                }
            }
        }
        if (objEvent == buttonUpDate){
            try {
                ArrayList<String> arrayList = dataSheet.toUpDate(this);
                if (arrayList.isEmpty()) JOptionPane.showMessageDialog(this, "Таблица пуста. Данных нет", "Ответ сервера", JOptionPane.WARNING_MESSAGE);
            }catch (ConnectException | WebServiceException err){
                dataSheet.clear();
                JOptionPane.showMessageDialog(this, err.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
            }

        }
        if (objEvent == buttonSave){
            try {
                dataSheet.toSave(this);
            }catch (ConnectException | WebServiceException err){
                JOptionPane.showMessageDialog(this, err.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}