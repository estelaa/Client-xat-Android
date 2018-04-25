package com.example.estela.xatclient;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends Activity {

    private Button button;
    private EditText text;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        button = findViewById(R.id.buttonStar);
        text =  (EditText) findViewById(R.id.text);

        text.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if((keyEvent.getAction()==KeyEvent.ACTION_DOWN)&& (i==KeyEvent.KEYCODE_ENTER)){
                    goChat();
                    return true;
                }
                return false;
            }

        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goChat();
            }
        });

    }

    private void goChat(){
        Intent intent = new Intent(LoginActivity.this,ChatActivity.class);
        String nick = text.getText().toString();
        intent.putExtra("user",nick);
        startActivity(intent);

    }


}
