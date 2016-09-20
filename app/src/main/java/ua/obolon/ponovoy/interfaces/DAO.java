package ua.obolon.ponovoy.interfaces;

import java.util.List;

import ua.obolon.ponovoy.interfaces.Call;

/**
 * Created by Alexander on 18.09.2016.
 */
public interface DAO{

    boolean addMissedCall(String number);
    List<Call> getAllMissed();
    int clearMissed();


}
