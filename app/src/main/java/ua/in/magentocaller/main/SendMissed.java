package ua.in.magentocaller.main;

import android.content.Context;
import android.util.Log;

import ua.in.magentocaller.dao.DaoImpl;
import ua.in.magentocaller.dao.ResoursSaverImpl;
import ua.in.magentocaller.interfaces.DAO;
import ua.in.magentocaller.interfaces.ResoursSaver;
import ua.in.magentocaller.socket.ClientConnector;
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
            ResoursSaver rs = new ResoursSaverImpl(context);
            rs.SaveValue(AppKeys.HAVE_MISSED, "NO");
        }
    }
}
