package ua.magento.caller.main;

import android.content.Context;
import android.util.Log;

import ua.magento.caller.dao.DaoImpl;
import ua.magento.caller.dao.ResoursSaverImpl;
import ua.magento.caller.interfaces.DAO;
import ua.magento.caller.interfaces.ResoursSaver;
import ua.magento.caller.socket.ClientConnector;
import ua.magento.caller.res.AppKeys;

/**
 * Created by Alexander on 19.09.2016.
 */
public class SendMissed implements Runnable{

    Context context;

    public SendMissed(Context context){
        this.context = context;
    }

    @Override
    public void run() {
        ClientConnector client = new ClientConnector(context);
        DAO dao = new DaoImpl(context);
        if(client.sendMissedCalls(dao.getAllMissed())){
            Log.d("Magento_caller", "SendMissed to server");
            dao.clearMissed();
            Log.d("Magento_caller", "Clear Missed");
            ResoursSaver rs = new ResoursSaverImpl(context);
            rs.SaveValue(AppKeys.HAVE_MISSED, "NO");
        }
    }
}
