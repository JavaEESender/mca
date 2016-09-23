package ua.obolon.ponovoy.main;

import android.content.Context;
import android.util.Log;

import ua.obolon.ponovoy.dao.DaoImpl;
import ua.obolon.ponovoy.dao.ResoursSaverImpl;
import ua.obolon.ponovoy.interfaces.Connector;
import ua.obolon.ponovoy.interfaces.DAO;
import ua.obolon.ponovoy.interfaces.ResoursSaver;
import ua.obolon.ponovoy.receivers.MissedReceiver;
import ua.obolon.ponovoy.res.AppKeys;
import ua.obolon.ponovoy.socket.ClientConnector;

/**
 * Created by Alexander on 18.09.2016.
 */
public class AcceptCall implements Runnable {

    private String phone;
    private Context context;

    public AcceptCall(String phone, Context context){
        this.phone = phone;
        this.context = context;
    }
    @Override
    public void run() {

        Connector connector = new ClientConnector(context);
        if (connector.sendPhoneCall(phone)){

        }else{
            addMissed(phone);
        }

    }

    public void addMissed(String phone){
        DAO dao = new DaoImpl(context);
        ResoursSaver res = new ResoursSaverImpl(context);
        if (!res.ReadValue(AppKeys.HAVE_MISSED).equals("YES")){
            res.SaveValue(AppKeys.HAVE_MISSED,"YES");
            Log.d("Magento_caller", "SetFirst ALARM!");

            MissedReceiver msR = new MissedReceiver();
            msR.SetAlarm(context);
        }
        dao.addMissedCall(phone);
        Log.d("Magento_caller", "ADD missed call!");
    }
}
