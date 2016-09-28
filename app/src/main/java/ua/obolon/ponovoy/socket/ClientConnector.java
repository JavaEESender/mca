package ua.obolon.ponovoy.socket;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.util.List;

import ua.obolon.ponovoy.dao.ResoursSaverImpl;
import ua.obolon.ponovoy.interfaces.Call;
import ua.obolon.ponovoy.interfaces.Connector;
import ua.obolon.ponovoy.interfaces.ResoursSaver;
import ua.obolon.ponovoy.res.AppKeys;
import ua.obolon.ponovoy.res.RequestKey;

/**
 * Created by Alexander on 18.09.2016.
 */
public class ClientConnector implements Connector {

    private String host;
    private int port;
    private String login;
    private String password;
    private Context context;


    public ClientConnector(Context context) {
        this.context = context;

    }

    @Override
    public boolean sendPhoneCall(String phone) {
        ResoursSaver res = new ResoursSaverImpl(context);

        if(!res.ReadValue(AppKeys.SERVER).equals(AppKeys.NO_VALUE_AVALABLE.toString()) || !res.ReadValue(AppKeys.PORT).equals(AppKeys.NO_VALUE_AVALABLE.toString())){
            this.host = res.ReadValue(AppKeys.SERVER);
            this.port = Integer.parseInt(res.ReadValue(AppKeys.PORT));
            this.login = res.ReadValue(AppKeys.LOGIN);
            this.password = res.ReadValue(AppKeys.PASSWORD);

            if(isNetworkAvailable()){
                try {
                    SocketChannel channel = SocketChannel.open();
                    channel.socket().connect((new InetSocketAddress(host, port)), 5000);
                    ObjectOutputStream oos = new ObjectOutputStream(channel.socket().getOutputStream());
                    oos.writeObject(RequestKey.CALL_ANDROID);
                    oos.writeObject(login);
                    oos.writeObject(password);
                    oos.writeObject(phone);
                    channel.close();
                    return true;
                } catch (Exception e) {
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

    @Override
    public boolean sendMissedCalls(List<Call> call) {
        ResoursSaver res = new ResoursSaverImpl(context);

        if(!res.ReadValue(AppKeys.SERVER).equals(AppKeys.NO_VALUE_AVALABLE.toString()) || !res.ReadValue(AppKeys.PORT).equals(AppKeys.NO_VALUE_AVALABLE.toString())){
            this.host = res.ReadValue(AppKeys.SERVER);
            this.port = Integer.parseInt(res.ReadValue(AppKeys.PORT));
            this.login = res.ReadValue(AppKeys.LOGIN);
            this.password = res.ReadValue(AppKeys.PASSWORD);

            if(isNetworkAvailable()){
                try {
                    SocketChannel channel = SocketChannel.open();
                    channel.socket().connect((new InetSocketAddress(host, port)), 5000);
                    ObjectOutputStream oos = new ObjectOutputStream(channel.socket().getOutputStream());
                    oos.writeObject(RequestKey.MISSED_CALLS);
                    oos.writeObject(res.ReadValue(AppKeys.LOGIN));
                    oos.writeObject(res.ReadValue(AppKeys.PASSWORD));
                    oos.writeObject(call);
                    channel.close();
                    return true;
                } catch (Exception e) {
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
