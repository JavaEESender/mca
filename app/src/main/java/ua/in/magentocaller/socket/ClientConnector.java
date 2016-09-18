package ua.in.magentocaller.socket;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.util.List;

import ua.in.magentocaller.dao.ResoursSaverImpl;
import ua.in.magentocaller.interfaces.Call;
import ua.in.magentocaller.interfaces.Connector;
import ua.in.magentocaller.interfaces.ResoursSaver;
import ua.in.magentocaller.res.AppKeys;
import ua.in.magentocaller.res.RequestKey;

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

        if(res.ReadValue(AppKeys.SERVER).equals(AppKeys.NO_VALUE_AVALABLE.toString()) ||res.ReadValue(AppKeys.PORT).equals(AppKeys.NO_VALUE_AVALABLE.toString())){
            host = res.ReadValue(AppKeys.SERVER);
            port = Integer.parseInt(res.ReadValue(AppKeys.PORT));

            try {
                SocketChannel channel = SocketChannel.open();
                channel.connect(new InetSocketAddress(host, port));
                ObjectOutputStream oos = new ObjectOutputStream(channel.socket().getOutputStream());
                oos.writeObject(RequestKey.CALL_ANDROID);
                oos.writeObject(res.ReadValue(AppKeys.USER));
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
            Log.d("Magento_caller", "NO_VALUE_AVALABLE for server or port!");
            return false;
        }
    }

    @Override
    public boolean sendMissedCalls(List<Call> calls) {

        return false;
    }
}
