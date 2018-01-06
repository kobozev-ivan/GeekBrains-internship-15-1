package fakeDatabase;

import javax.swing.*;
import java.awt.*;

public class WindowServer extends JTextArea {

    private static volatile WindowServer instance;

    private WindowServer(){
        setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 18));
        setTabSize(2);
        setEditable(false);
        setAutoscrolls(true);
        setLineWrap(true);
        setWrapStyleWord(true);
    }

    static WindowServer getInstance() {
        WindowServer localInstance = instance;
        if (localInstance == null) {
            synchronized (WindowServer.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new WindowServer();
                }
            }
        }
        return localInstance;
    }
}
