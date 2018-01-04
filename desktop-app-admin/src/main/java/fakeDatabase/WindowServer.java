package fakeDatabase;

import javax.swing.*;

public class WindowServer extends JTextArea {

    private static volatile WindowServer instance;

    private WindowServer(){
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
