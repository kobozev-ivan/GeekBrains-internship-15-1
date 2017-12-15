package view;
import reference.Data;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Максим on 15.12.2017.
 */
public class Editor extends JPanel implements ActionListener{

    JList<String> list = new JList<>();

    JTextArea textArea = new JTextArea();
    JLabel labelList = new JLabel("Список");
    JLabel labelText = new JLabel("Поле ввода/редактирования");
    JButton buttonAdd = new JButton("<<< Добавить");
    JButton buttonEdit = new JButton("Редактировать >>");
    JButton buttonDel = new JButton("Удалить");
    JButton buttonSave = new JButton("Сохранить");
    JButton buttonCancel = new JButton("Отмена");
    JPanel panelButton = new JPanel();

    Editor(){
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        list.setSelectionForeground(Color.BLUE);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setLayoutOrientation(JList.VERTICAL);
        list.setAutoscrolls(true);
        JScrollPane scrollList = new JScrollPane(list);
        JScrollPane scrollText = new JScrollPane(textArea);
        JPanel centr  = new JPanel(new GridLayout(1,3));
        JPanel panelList = new JPanel();
        JPanel panelText = new JPanel();
        JPanel down = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        panelList.setLayout(new BoxLayout(panelList,BoxLayout.Y_AXIS));
        panelText.setLayout(new BoxLayout(panelText,BoxLayout.Y_AXIS));
        panelButton.setLayout(new BoxLayout(panelButton,BoxLayout.Y_AXIS));
        panelList.add(labelList);
        panelList.add(scrollList);
        panelText.add(labelText);
        panelText.add(scrollText);

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
        labelText.setAlignmentX(CENTER_ALIGNMENT);
        buttonAdd.setAlignmentX(CENTER_ALIGNMENT);
        buttonEdit.setAlignmentX(CENTER_ALIGNMENT);
        buttonDel.setAlignmentX(CENTER_ALIGNMENT);

        centr.add(panelList);
        centr.add(panelButton);
        centr.add(panelText);

        down.add(buttonSave);
        down.add(buttonCancel);
        buttonSave.setHorizontalAlignment(SwingConstants.RIGHT);
        buttonCancel.setHorizontalAlignment(SwingConstants.RIGHT);

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
            if (textArea.getText() != null || !textArea.getText().equals(" ")){
                Data name = (Data) list.getModel();
                name.addElement(textArea.getText());
                textArea.setText(null);
            }
        }
        if (objEvent == buttonEdit){
            if (!list.isSelectionEmpty()){
                textArea.setText(list.getSelectedValue());
                Data name = (Data) list.getModel();
                name.remove(list.getSelectedIndex());
            }
        }
        if (objEvent == buttonDel){
            if (!list.isSelectionEmpty()){
                Data name = (Data) list.getModel();
                name.remove(list.getSelectedIndex());
            }
        }
    }
}
