package ua.in.magentocaller.main;

import android.content.Context;

import ua.in.magentocaller.interfaces.Connector;
import ua.in.magentocaller.socket.ClientConnector;

/**
 * Created by Alexander on 18.09.2016.
 */
public class AcceptCall implements Runnable {

    private String number;
    private Context context;

    public AcceptCall(String number, Context context){
        this.number = number;
        this.context = context;
    }
    @Override
    public void run() {

        Connector connector = new ClientConnector(context);
        connector.sendPhoneCall(number);

    }
}
