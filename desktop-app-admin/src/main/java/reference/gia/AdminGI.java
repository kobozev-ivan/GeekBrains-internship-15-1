package reference.gia;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminGI extends JFrame implements ActionListener {

    private static final int WIDTH = 600;
    private static final int HEIGHT = 400;
    private static final String TITLE = "Справочник";
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
        buttonOut.addActionListener(this);
        Reference reference = new Reference();
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

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == buttonOut){
            System.exit(0);
        }
    }
}


