package ua.in.magentocaller.socket;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.util.List;

import ua.in.magentocaller.dao.DaoImpl;
import ua.in.magentocaller.dao.ResoursSaverImpl;
import ua.obolon.ponovoy.inerfaces.Call;
import ua.in.magentocaller.interfaces.Connector;
import ua.in.magentocaller.interfaces.DAO;
import ua.in.magentocaller.interfaces.ResoursSaver;
import ua.in.magentocaller.rceivers.MissedReceiver;
import ua.obolon.ponovoy.res.AppKeys;
import ua.obolon.ponovoy.res.RequestKey;

/**
 * Created by Alexander on 18.09.2016.
 */
public class ClientConnector implements Connector {

    private String host;
    private int port;
    private String username;
    private String password;
    private Context context;


    public ClientConnector(Context context) {
        this.context = context;

    }

    @Override
    public boolean sendPhoneCall(String phone) {
        ResoursSaver res = new ResoursSaverImpl(context);

        if(!res.ReadValue(AppKeys.SERVER).equals(AppKeys.NO_VALUE_AVALABLE.toString()) || !res.ReadValue(AppKeys.PORT).equals(AppKeys.NO_VALUE_AVALABLE.toString())){
            host = res.ReadValue(AppKeys.SERVER);
            port = Integer.parseInt(res.ReadValue(AppKeys.PORT));

            if(isNetworkAvailable()){
                try {
                    SocketChannel channel = SocketChannel.open();
                    channel.connect(new InetSocketAddress(host, port));
                    ObjectOutputStream oos = new ObjectOutputStream(channel.socket().getOutputStream());
                    oos.writeObject(RequestKey.CALL_ANDROID);
                    oos.writeObject(res.ReadValue(AppKeys.LOGIN));
                    oos.writeObject(res.ReadValue(AppKeys.PASSWORD));
                    oos.writeObject(phone);
                    channel.close();
                    return true;
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.d("Magento_caller", "Socket failure");
                    return false;
                }
            }else{
                Log.d("Magento_caller", "No Network Avalable!");
                DAO dao = new DaoImpl(context);
                if (res.ReadValue(AppKeys.HAVE_MISSED).equals("NO")){
                    res.SaveValue(AppKeys.HAVE_MISSED,"YES");
                    Log.d("Magento_caller", "SetFirst ALARM!");
                    MissedReceiver msR = new MissedReceiver();
                    msR.SetAlarm(context);
                }
                dao.addMissedCall(phone);
                Log.d("Magento_caller", "ADD missed call!");
                return false;
            }

        }else{
            Log.d("Magento_caller", "NO_VALUE_AVALABLE for server or port!");
            return false;
        }
    }

    @Override
    public boolean sendMissedCalls(List<Call> calls) {
        ResoursSaver res = new ResoursSaverImpl(context);

        if(!res.ReadValue(AppKeys.SERVER).equals(AppKeys.NO_VALUE_AVALABLE.toString()) || !res.ReadValue(AppKeys.PORT).equals(AppKeys.NO_VALUE_AVALABLE.toString())){
            host = res.ReadValue(AppKeys.SERVER);
            port = Integer.parseInt(res.ReadValue(AppKeys.PORT));

            if(isNetworkAvailable()){
                try {
                    SocketChannel channel = SocketChannel.open();
                    channel.connect(new InetSocketAddress(host, port));
                    ObjectOutputStream oos = new ObjectOutputStream(channel.socket().getOutputStream());
                    oos.writeObject(RequestKey.MISSED_CALLS);
                    oos.writeObject(res.ReadValue(AppKeys.LOGIN));
                    oos.writeObject(res.ReadValue(AppKeys.PASSWORD));
                    oos.writeObject(calls);
                    channel.close();
                    return true;
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.d("Magento_caller", "Socket failure");
                    return false;
                }
            }else{
                Log.d("Magento_caller", "No Network Avalable!");
                return false;
            }

        }else{
            Log.d("Magento_caller", "NO_VALUE_AVALABLE for server or port!");
            return false;
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
