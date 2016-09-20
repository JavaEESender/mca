package ua.obolon.ponovoy.main;

import android.content.Context;
import android.util.Log;

import ua.obolon.ponovoy.dao.DaoImpl;
import ua.obolon.ponovoy.dao.ResoursSaverImpl;
import ua.obolon.ponovoy.interfaces.DAO;
import ua.obolon.ponovoy.interfaces.ResoursSaver;
import ua.obolon.ponovoy.socket.ClientConnector;
import ua.obolon.ponovoy.res.AppKeys;

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
