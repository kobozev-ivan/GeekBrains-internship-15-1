package reference;

import javax.swing.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by Максим on 19.12.2017.
 */
public class Communication implements Deliverable{

    public static boolean offline = true;
    private Socket socket;
    private JTextArea text;
    private DataOutputStream out;
    private DataInputStream in;
    static Communication communication;

    public Communication(String string, int port, JTextArea textArea){
        text = textArea;
        try {
            socket = new Socket(string, port);
            text.append("Подключился к " + socket.getInetAddress().getHostName() + " " + socket.getPort() + "\n");
            out = new DataOutputStream(socket.getOutputStream());
            in = new DataInputStream(socket.getInputStream());
            offline = false;
        } catch (IOException e) {
            try {
                out.close();
                in.close();
                socket.close();
            } catch (IOException e1) {
                text.append("Ошибочка " + e.getMessage() + "\n");
                offline = true;
                e1.printStackTrace();
            }
            text.append("Ошибочка " + e.getMessage() + "\n");
            offline = true;
            e.printStackTrace();
        }
        communication = this;
    }
    @Override
    public  void toSendData(String message){
        try {
            out.writeUTF(message);
            out.flush();
            text.append("Отправка " + message + "\n");
        } catch (IOException e) {
            text.append("Ошибочка отправки " + e.getMessage() + "\n");
            offline = true;
            e.printStackTrace();

        }
    }
    @Override
    public String toObtainData(){
        String string = null;
        try {
            string = in.readUTF();
            text.append("Прием " + string + "\n");
        } catch (IOException e) {
            text.append("Ошибочка приема " + e.getMessage() + "\n");
            offline = true;
            e.printStackTrace();
        }
        return string;
    }

    public void close(){
        try {
            offline = true;
            socket.close();
            text.append("Закрытие соединения\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }








}
