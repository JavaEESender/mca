package ua.magento.caller.interfaces;

import java.util.List;

/**
 * Created by Alexander on 18.09.2016.
 */
public interface Connector {

    boolean sendPhoneCall(String phone);
    boolean sendMissedCalls(List<Call> calls);

}