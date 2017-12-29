package fakeDatabase;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by Максим on 24.12.2017.
 */
class ConnectionWindow extends JFrame implements ActionListener{

    private static final int WIDTH = 400;
    private static final int HEIGHT = 200;
    private static final int POS_X = 1400;
    private static final int POS_Y = 200;

    private final JTextField fieldHost = new JTextField("LocalHost");
    private final JTextField fieldPort = new JTextField("8189");
    private final JButton buttonGreatConnect = new JButton("Создать подключение");
    private final JButton buttonDisConnect = new JButton("Разорвать подключение");
    private final JTextArea textArea = new JTextArea();

    private String nameHost;
    private int port;


    ConnectionWindow(){

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setLocation(POS_X, POS_Y);
        setTitle("Подключение  к серверу БД");
        setResizable(false);
        setAlwaysOnTop(true);

        fieldHost.addActionListener(this);
        fieldPort.addActionListener(this);
        buttonGreatConnect.addActionListener(this);
        buttonDisConnect.addActionListener(this);

        JPanel upperPanel = new JPanel(new GridLayout(3, 1));
        upperPanel.add(fieldHost);
        upperPanel.add(fieldPort);

        JPanel panelButton = new JPanel(new GridLayout(1, 2));
        panelButton.add(buttonGreatConnect);
        panelButton.add(buttonDisConnect);

        upperPanel.add(panelButton);

        textArea.setEditable(false);
        textArea.setAutoscrolls(true);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(textArea);

        add(upperPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object object = e.getSource();
        if (object == fieldHost){
            nameHost = fieldHost.getText().toLowerCase().trim();
            textArea.append(nameHost + "\n");
            String address = null;
            try {
                address = InetAddress.getByName(nameHost).getHostAddress();
            } catch (UnknownHostException e1) {
                e1.printStackTrace();
            }
            textArea.append("IP адрес: " + address + "\n");
        }

        if (object == fieldPort){
            String stringPort = fieldPort.getText().trim();
            port = Integer.parseInt(stringPort);
            textArea.append("Порт подключения: " + stringPort + "\n");
        }
        if (object == buttonGreatConnect){
//            communication = new Communication(nameHost, port, textArea);
            buttonGreatConnect.setEnabled(false); // потом убрать
        }
        if (object == buttonDisConnect){
//            communication.close();
            buttonGreatConnect.setEnabled(true);
        }
    }
}
