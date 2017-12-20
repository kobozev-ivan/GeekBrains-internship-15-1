package fakeDatabase;

import java.sql.*;
import java.util.Scanner;

/**
 * Created by Максим on 18.12.2017.
 */
public class FakeData {

    private static Scanner scanner = new Scanner(System.in);
    private static Connection connection;
    private static boolean  execution = true;
    private static Statement statement;

    public static void main(String[] args) {
        try {
            connect();
            System.out.println("Подключение к базе");
            while (execution){
                exit();
            }
            disconnect();
            System.out.println("Отключение");
        }catch (Exception err){
            err.printStackTrace();
        }
    }

    static void exit(){
        if (scanner.nextLine().equals("Q")){
            execution = false;
            scanner.close();
        }
    }


    private static void connect()throws Exception{
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:fakedata.db");
//        statement = connection.createStatement();
    }

    private static void disconnect(){
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
