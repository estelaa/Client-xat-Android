package com.example.estela.xatclient;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class MySocket {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public MySocket(String host, int port) {
        try {
            socket = new Socket(host, port);
            if (socket!=null){
                try{
                    out = new PrintWriter(socket.getOutputStream(), true);
                    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                }catch (Exception e){
                    Log.d("ClientActivity", "Client out/in Error");
                }
            }
        } catch (IOException e) {
            Log.d("ClientActivity","Server no connected");
            e.printStackTrace();
        }
    }

    public String readString(){
        try{
            String str = in.readLine();
            return str;
        }catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void send(String str){
        out.println(str);
    }


    public void close() {
        try{
            out.close();
            in.close();
            socket.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isInputShutdown(){
        return socket.isInputShutdown();
    }

    public boolean isOutputShutdown(){
        return socket.isOutputShutdown();
    }

    public void shutdownInput(){
        try{
            socket.shutdownInput();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void shutdownOutput(){
        try{
            socket.shutdownOutput();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
