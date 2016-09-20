package ua.obolon.ponovoy.dao;

import android.content.Context;
import android.content.SharedPreferences;

import ua.obolon.ponovoy.interfaces.ResoursSaver;
import ua.obolon.ponovoy.res.AppKeys;

/**
 * Created by Alexander on 18.09.2016.
 */
public class ResoursSaverImpl implements ResoursSaver {

    Context context;

    public ResoursSaverImpl(Context context) {
        this.context = context;
    }

    @Override
    public void SaveValue(AppKeys key, String value) {
        SharedPreferences shp = context
                .getSharedPreferences("Magento_Caller", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = shp.edit();
        editor.putString(key.toString(),value);
        editor.commit();
    }

    @Override
    public String ReadValue(AppKeys key) {
        String value;
        SharedPreferences shp = context
                .getSharedPreferences("Magento_Caller", Context.MODE_PRIVATE);
        value = shp.getString(key.toString(),AppKeys.NO_VALUE_AVALABLE.toString());
        return value;
    }
}
