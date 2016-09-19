package ua.in.magentocaller.rceivers;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;

import ua.in.magentocaller.dao.DaoImpl;
import ua.in.magentocaller.dao.ResoursSaverImpl;
import ua.in.magentocaller.interfaces.DAO;
import ua.in.magentocaller.interfaces.ResoursSaver;
import ua.in.magentocaller.main.SendMissed;
import ua.in.magentocaller.socket.ClientConnector;
import ua.obolon.ponovoy.res.AppKeys;

/**
 * Created by Alexander on 18.09.2016.
 */
public class MissedReceiver extends BroadcastReceiver {

    final public static String ONE_TIME = "onetime";
    Context cont;

    public MissedReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        cont = context;
        Log.d("Magento_caller", "onReceve");
        Bundle extras = intent.getExtras();
        ResoursSaver rs = new ResoursSaverImpl(context);

        if (isOnline()){
          if(rs.ReadValue(AppKeys.HAVE_MISSED).equals("YES") ){
             new Thread(new SendMissed(context)).start();
          }else{
              CancelAlarm(context);
          }
        }
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) cont.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isAvailable();
    }

    public void SetAlarm(Context context) {
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, MissedReceiver.class);
        intent.putExtra(ONE_TIME, Boolean.FALSE); // Задаем параметр интента
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, 0);
        // Устанавливаем интервал срабатывания в 10 секунд.
        Log.d("Magento_caller", "SetAlarm");
        am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 1000 * 10, pi);
    }

    public void CancelAlarm(Context context) {
        Intent intent = new Intent(context, MissedReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Log.d("Magento_caller", "CancelALARM");
        alarmManager.cancel(sender);
    }
}
