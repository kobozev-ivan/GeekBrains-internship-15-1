package gia;

import javax.swing.*;
import java.awt.*;

public class AdminGI extends JFrame{

    private static final int WIDTH = 600;
    private static final int HEIGHT = 400;
    private static final String TITLE = "Справочник";
    private Reference reference = new Reference();
    private JButton buttonOut = new JButton("Закрыть");

    private AdminGI(){
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setTitle(TITLE);
        setResizable(false);
        setAlwaysOnTop(true);
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(buttonOut);
        add(reference, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
        pack();
        setVisible(true);
    }

    public static void main(String[] args){

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new AdminGI();
            }
        });
    }

}


