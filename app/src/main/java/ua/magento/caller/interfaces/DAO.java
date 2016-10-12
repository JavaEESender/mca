package ua.magento.caller.interfaces;

import java.util.List;

/**
 * Created by Alexander on 18.09.2016.
 */
public interface DAO{

    boolean addMissedCall(String number);
    List<Call> getAllMissed();
    int clearMissed();


}
