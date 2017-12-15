import view.Name;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Максим on 15.12.2017.
 */
public class AdminGI extends JFrame{

    private static final int WIDTH = 600;
    private static final int HEIGHT = 400;
    private static final String TITLE = "Справочник";
    private Name name = new Name();
    private JButton buttonOut = new JButton("Закрыть");


    AdminGI(){
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setTitle(TITLE);
        setResizable(false);
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(buttonOut);
        add(name, BorderLayout.CENTER);
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
