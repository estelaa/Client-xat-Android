package com.example.estela.xatclient;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;



/**
 * Created by estel on 15/11/2017.
 */
public class ChatActivity extends Activity {

    private final String IP_ADDRESS ="192.168.43.224";
    private final int PORT = 54321;

    private volatile ListView listView;
    private Button buttonSend;
    private EditText textSend;

    private ChatAdapter adapter;
    private ArrayList<Message> messageArray;
    public MySocket mySocket;
    private String user;




    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xat);
        Intent intent = getIntent();
        user = intent.getExtras().getString("user");

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        mySocket= new MySocket(IP_ADDRESS, PORT);
        mySocket.send(user);
        sendWelcomUser();
        initControls();


    }

    private void initControls(){

        buttonSend = (Button) findViewById(R.id.buttonSend);
        textSend =  (EditText) findViewById(R.id.textSend);


        messageArray= new ArrayList<Message>();
        adapter = new ChatAdapter(this, messageArray);
        welcomMessage();
        listView = (ListView) findViewById(R.id.list_xat);
        listView.setAdapter(adapter);


        Thread sendMessage = new Thread(new Runnable() {
            @Override
            public void run() {
                buttonSend.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        String string = String.valueOf(textSend.getText());
                        display(user, string);
                        sendMessage(string, user);
                        textSend.setText(""); //eliminar el text
                    }
                });

                //onUserLeaveHint();
                //onBackPressed();

            }

        });

        Thread reciveMessage = new Thread(new Runnable() {
            String str;
            @Override
            public void run() {
                while ((str = mySocket.readString()) != null) {
                      char [] aux= {str.charAt(0),str.charAt(1)};
                      String auxString = String.valueOf(aux);
                      int size= Integer.parseInt(auxString);

                      char[] line = str.toCharArray();
                      char [] userChar= new char[size];
                      char[] messageChar= new char[str.length()-2-size];
                      System.arraycopy(line,2,userChar,0,size);
                      System.arraycopy(line,size+2,messageChar,0,str.length()-2-size);
                      display(String.valueOf(userChar),String.valueOf(messageChar));
                }
            }
        });

        sendMessage.start();
        reciveMessage.start();

    }

    public void display(String user, String text){
        Message message = new Message();
        message.setUser(user);
        message.setMessage(text);
        messageArray.add(message);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
            }
        });
    }

    public void sendMessage(String msg, String usr){
       mySocket.send(usr+msg);
    }

    // ---------    misatges concrets -------------------//

    private void welcomMessage(){
        Message msg = new Message();
        msg.setMessage("Bienvenido al chat "+ user);
        messageArray.add(msg);
    }

    private void sendWelcomUser(){
        Message message = new Message();
        message.setMessage("Se unio al chat");
        sendMessage(message.getMessage(),user);
    }

    private void sendByeUser(){
        Message message = new Message();
        message.setMessage("Se fue del chat");
        sendMessage(message.getMessage(),user);
    }




}

