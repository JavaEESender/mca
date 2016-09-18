package ua.in.magentocaller.interfaces;

import java.util.List;

/**
 * Created by Alexander on 18.09.2016.
 */
public interface DAO{

    boolean addMissedCall(String number);
    List<Call> getAllMissed();
    int clearMissed();


}
