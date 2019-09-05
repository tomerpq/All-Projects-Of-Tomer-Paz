package com.example.ex4;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Connect {
    private Socket socket;

    private static final Connect ourInstance = new Connect();

    public static Connect getInstance() {
        return ourInstance;
    }

    private Connect() {
    }

    public void connect(String serverAddress, String serverPort) {

        //creating new socket
        try {
            this.socket = new Socket(serverAddress, Integer.parseInt(serverPort));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void disconnect() {
        //disconnect method
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setValue(String field, double value) {
        //set value of field with the specific value we are given
        OutputStreamWriter osw = null;
        String str = null;
        try {
            osw = new OutputStreamWriter(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (field.equals("aileron")) {
            str = "set controls/flight/aileron " + value + " \n\r\n\r";

        } else if (field.equals("elevator")) {
            str = "set controls/flight/elevator " + value + " \n\r\n\r";

        } else {
            try {
                throw new Exception("no such field!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //sending the command to the flight simulator.
        try {
            osw.write(str, 0, str.length());
            osw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}