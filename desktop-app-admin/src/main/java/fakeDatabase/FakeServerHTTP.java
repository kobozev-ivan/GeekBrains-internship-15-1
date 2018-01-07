package fakeDatabase;


import com.sun.net.httpserver.HttpServer;
import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;


import javax.swing.*;
import javax.ws.rs.core.UriBuilder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.URI;
import java.net.UnknownHostException;

public class FakeServerHTTP extends JFrame implements ActionListener{

    private static final int WIDTH = 400;
    private static final int HEIGHT = 800;
    private static final int POS_X = 1400;
    private static final int POS_Y = 200;

    private final JTextField fieldHost = new JTextField("LocalHost");
    private final JTextField fieldPort = new JTextField("8989");
    private final JButton buttonGreatConnect = new JButton("Включение");
    private final JButton buttonDisConnect = new JButton("Остановка");
    private WindowServer textArea = WindowServer.getInstance();

    private String nameHost;
    private int port;
    private URI baseUri;
    private HttpServer server;
    private FakeServerHTTP(){

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setLocation(POS_X, POS_Y);
        setTitle("ФейкСервер с ФейкБД");
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

        JScrollPane scrollPane = new JScrollPane(textArea);

        add(upperPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new FakeServerHTTP();
            }
        });
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
            baseUri = UriBuilder.fromUri("http://" + nameHost + "/").port(port).build();
            textArea.append("Url сервера " + baseUri.toString() + "\n");
        }
        if (object == buttonGreatConnect){
            if (baseUri == null || port == 0) {
                textArea.append("Нет параметров подключения сервера\n");
                return;
            }
            if (server != null){
                textArea.append("Сервер уже работает\n");
                return;
            }
            server = JdkHttpServerFactory.createHttpServer(baseUri, new ResourceConfig(WebService.class));
            textArea.append("Сервер запущен\n\n");
        }
        if (object == buttonDisConnect){
            if (server == null){
                textArea.append("Сервер не работает\n");
                return;
            }
            server.stop(0);
            server = null;
            textArea.append("Сервер выключен\n");
        }
    }
}
