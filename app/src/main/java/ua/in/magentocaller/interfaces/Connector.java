package ua.in.magentocaller.interfaces;

import java.util.List;

import ua.obolon.ponovoy.interfaces.Call;

/**
 * Created by Alexander on 18.09.2016.
 */
public interface Connector {

    boolean sendPhoneCall(String phone);
    boolean sendMissedCalls(List<Call> calls);

}