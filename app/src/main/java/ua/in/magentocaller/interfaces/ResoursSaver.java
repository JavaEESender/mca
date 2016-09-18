package ua.in.magentocaller.interfaces;

import ua.obolon.ponovoy.res.AppKeys;

/**
 * Created by Alexander on 18.09.2016.
 */
public interface ResoursSaver {

    void SaveValue(AppKeys key, String value);
    String ReadValue(AppKeys key);

}
