package com.example.estela.xatclient;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;



/**
 * Created by estel on 15/11/2017.
 */

public class ChatAdapter extends ArrayAdapter<Message>{



    public ChatAdapter(Context context, ArrayList<Message> message){
        super(context,0,message);

    }



    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        Message chatM = getItem(position);
        if (view==null){
            view = LayoutInflater.from(getContext()).inflate(R.layout.message, viewGroup, false);
        }
        TextView user = (TextView) view.findViewById(R.id.user);
        TextView txtmessage = (TextView) view.findViewById(R.id.messagetxt);

        user.setText(chatM.getUser());
        txtmessage.setText(chatM.getMessage());
        return view;
    }

}


