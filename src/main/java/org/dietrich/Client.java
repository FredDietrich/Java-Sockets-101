package org.dietrich;

import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    
    public static void start(String addr, int port) {
        ObjectOutputStream out;
        ObjectInputStream in;
        Socket con;
        Scanner read = new Scanner(System.in);
        String msg = "";
        try {
            con = new Socket(addr, port);
            System.out.println("Connected to " + addr + "on port " + port);
            System.out.println("Type END to end the connection");
            
            out = new ObjectOutputStream(con.getOutputStream());
            out.flush();
            in = new ObjectInputStream(con.getInputStream());
            msg = (String) in.readObject();
            System.out.println("S: " + msg);

            do {
                try {
                    System.out.print("..: ");
                    msg = read.nextLine();
                    out.writeObject(msg);
                    out.flush();
                    msg = (String) in.readObject();
                    System.out.println("S: " + msg); 
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } while (!"END".equals(msg));

            out.close();
            in.close();
            read.close();
            con.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        Client.start("127.0.0.1", 1333);
    }

}
