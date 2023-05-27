package org.dietrich.server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import org.dietrich.handlers.MessageGateway;

public class Server {

    public static void start(int port) {
        ObjectOutputStream out;
        ObjectInputStream in;
        Boolean exit = false;
        String msg = "";

        try {
            ServerSocket server = new ServerSocket(port, 10);
            Socket con;
            while (!exit) {
                System.out.println("Listening on port " + port);
                con = server.accept();
                System.out.println("Connection estabilished with " + con.getInetAddress().getHostAddress());
                out = new ObjectOutputStream(con.getOutputStream());
                out.flush();
                in = new ObjectInputStream(con.getInputStream());

                out.writeObject("Connection successful\n");

                do {
                    try {
                        msg = (String) in.readObject();
                        System.out.println("C: " + msg);
                        String response = new MessageGateway().handleMessage(msg);
                        out.writeObject(response);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } while (!"END".equals(msg));
                System.out.println("Connection ended by client");
                exit = true;
                out.close();
                in.close();
                con.close();
                server.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
