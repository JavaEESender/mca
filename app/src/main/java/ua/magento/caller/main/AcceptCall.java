package ua.magento.caller.main;

import android.content.Context;
import android.util.Log;

import ua.magento.caller.dao.DaoImpl;
import ua.magento.caller.dao.ResoursSaverImpl;
import ua.magento.caller.interfaces.Connector;
import ua.magento.caller.interfaces.DAO;
import ua.magento.caller.interfaces.ResoursSaver;
import ua.magento.caller.receivers.MissedReceiver;
import ua.magento.caller.res.AppKeys;
import ua.magento.caller.socket.ClientConnector;

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
            checkMissed();
        }else{
            addMissed(phone);
        }

    }

    public void addMissed(String phone){
        DAO dao = new DaoImpl(context);
        MissedReceiver msR = new MissedReceiver();
        ResoursSaver res = new ResoursSaverImpl(context);
        if (!res.ReadValue(AppKeys.HAVE_MISSED).equals("YES")){
            res.SaveValue(AppKeys.HAVE_MISSED,"YES");
            Log.d("Magento_caller", "SetFirst ALARM!");
        }
            msR.CancelAlarm(context);
            msR.SetAlarm(context);

        dao.addMissedCall(phone);
        Log.d("Magento_caller", "ADD missed call!");
    }

    public void checkMissed(){
        MissedReceiver msR = new MissedReceiver();
        ResoursSaver res = new ResoursSaverImpl(context);
        Log.d("Magento_caller", "IF Missed alarm!!");
        if (res.ReadValue(AppKeys.HAVE_MISSED).equals("YES")){
            Log.d("Magento_caller", "HAVE MISSED YES");
                msR.CancelAlarm(context);
                msR.SetAlarm(context);

        }
    }
}
